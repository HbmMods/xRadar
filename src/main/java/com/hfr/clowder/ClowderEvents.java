package com.hfr.clowder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.event.world.WorldEvent;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder.ScheduledTeleport;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.command.CommandClowder;
import com.hfr.command.CommandClowderChat;
import com.hfr.data.ClowderData;
import com.hfr.handler.BobbyBreaker;
import com.hfr.handler.ExplosionSound;
import com.hfr.items.ItemMace;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderBorderPacket;
import com.hfr.packet.effect.ClowderFlagPacket;
import com.hfr.tileentity.prop.TileEntityProp;
import com.hfr.tileentity.prop.TileEntityStatue;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ClowderEvents {

    @SubscribeEvent
    public void clowderLoadEvent(WorldEvent.Load event) {

        if (event.world.provider.dimensionId == 0) {
            ClowderData.getData(event.world);
        }
    }

    @SubscribeEvent
    public void clowderLoadEvent(WorldEvent.Unload event) {

        if (event.world.provider.dimensionId == 0) {
            ClowderData.getData(event.world)
                .markDirty();
        }
    }

    /**
     * Handles chat events related to clowders, mainly adding the clowder name to a chat message.
     * 
     * @param event
     */
    @SubscribeEvent
    public void handleChatServer(ServerChatEvent event) {

        Clowder clowder = Clowder.getClowderFromPlayer(event.player);

        if (clowder != null) {

            if (event.player.getEntityData()
                .getInteger(CommandClowderChat.CHAT_KEY) == 1) {
                sendToTeam(clowder, event.player, event.message);
                event.setCanceled(true);
                return;
            }

            String name = clowder.getDecoratedName();
            String message = EnumChatFormatting.DARK_GREEN + "[ " + name + " Citizen ]";
            if (clowder.getPermLevel(event.player.getDisplayName()) > 1) {
                message = EnumChatFormatting.BLUE + "[ " + name + " Officer ]";
            }
            if (clowder.getPermLevel(event.player.getDisplayName()) > 2) {
                message = EnumChatFormatting.GOLD + "[ " + name + " Leader ]";
            }
            MinecraftServer.getServer()
                .getConfigurationManager()
                .sendChatMsg(new ChatComponentText(message));
        }
    }

    private void sendToTeam(Clowder clowder, EntityPlayer player, String message) {

        String name = "";

        if (clowder.getPermLevel(player.getDisplayName()) > 2) {
            name += "<Leader> ";
        } else if (clowder.getPermLevel(player.getDisplayName()) > 1) {
            name += "<Officer> ";
        } else if (clowder.getPermLevel(player.getDisplayName()) > 0) {
            name += "<Citizen> ";
        }

        name += "[" + player.getDisplayName() + "]";

        clowder.notifyAll(player.worldObj, new ChatComponentText(CommandClowderChat.HELP + name + " " + message));
        System.out.println(name + " " + message);

    }

    /**
     * Prevents breaking/placing blocks on foreign clowder territory.
     * 
     * @param event
     */
    @SubscribeEvent
    public void clowderBlockEvent(BlockEvent event) {

        if (event instanceof BreakEvent || event instanceof PlaceEvent) {
            int x = event.x;
            int y = event.y;
            int z = event.z;

            Block b = event.world.getBlock(x, y, z);

            Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);

            if (event instanceof BreakEvent) {

                EntityPlayer player = ((BreakEvent) event).getPlayer();
                Clowder clowder = Clowder.getClowderFromPlayer(player);

                if (canBreak(player, clowder, owner, b, x, y, z)) {

                    onBreak(event.world, b, x, y, z, owner);

                    BobbyBreaker.handleDigEvent((BreakEvent) event);

                } else {
                    event.setCanceled(true);
                    return;
                }
            }

            if (event instanceof PlaceEvent) {

                EntityPlayer player = ((PlaceEvent) event).player;
                Clowder clowder = Clowder.getClowderFromPlayer(player);

                if (!canPlace(player, clowder, owner, b, x, y, z)) {
                    event.setCanceled(true);
                    return;
                }

                if (!player.inventory.hasItem(ModItems.debug) && (owner == null || owner.zone == Zone.WILDERNESS)
                    && (b == Blocks.chest || b == Blocks.trapped_chest)) {

                    player.addChatMessage(
                        new ChatComponentText(CommandClowder.ERROR + "Chests are disabled outside of claims."));
                    player.addChatMessage(
                        new ChatComponentText(
                            CommandClowder.ERROR
                                + "For temporary storage, use cardboard boxes (crafable with chests)"));
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }

    private void onBreak(World world, Block b, int x, int y, int z, Ownership owner) {

        if (owner != null && owner.zone == Zone.FACTION && owner.owner != null) {

            if (b == ModBlocks.med_tent || b == ModBlocks.med_tent) {

                int[] loc = ((BlockDummyable) b).findCoreRec(world, x, y, z);

                if (loc != null) {
                    TileEntityProp prop = (TileEntityProp) world.getTileEntity(loc[0], loc[1], loc[2]);

                    if (prop != null && prop.operational()) owner.owner.multPrestige(0.975F, world);
                }
            }

            if (b == ModBlocks.statue) {

                int[] loc = ((BlockDummyable) b).findCoreRec(world, x, y, z);

                if (loc != null) {
                    TileEntityStatue prop = (TileEntityStatue) world.getTileEntity(loc[0], loc[1], loc[2]);

                    if (prop != null && prop.operational()) owner.owner.multPrestige(0.975F, world);
                }
            }
        }
    }

    private boolean canBreak(EntityPlayer player, Clowder clowder, Ownership owner, Block b, int x, int y, int z) {

        if (player.inventory.hasItem(ModItems.debug)) return true;

        if (owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) return false;

        if (owner.zone == Zone.FACTION) {

            if (clowder != owner.owner) {
                if (clowder != null && !clowder.isRaidable()) return false;

                if (!owner.owner.isRaidable()) return false;

                if (player.getHeldItem() == null) return false;

                if (player.getHeldItem()
                    .getItem() == ModItems.mace && ItemMace.breakOverride.contains(b)) return true;

                return false;
            } else {

                if (player.worldObj.getBlock(x, y, z) == ModBlocks.officer_chest
                    && clowder.getPermLevel(player.getDisplayName()) < 2) {
                    player.addChatMessage(
                        new ChatComponentText(
                            EnumChatFormatting.RED + "You lack the permissions to destroy this chest."));
                    return false;
                }
            }

            if (player.worldObj.getBlock(x, y, z) != ModBlocks.clowder_flag) {
                for (int i = x - 2; i <= x + 2; i++) for (int j = z - 2; j <= z + 2; j++)
                    if (player.worldObj.getBlock(i, y + 1, j) == ModBlocks.clowder_flag) {

                        player.addChatMessage(
                            new ChatComponentText(
                                EnumChatFormatting.RED + "Please refrain from breaking the flag's foundation."));
                        return false;
                    }
            }
        }

        return true;
    }

    private boolean canPlace(EntityPlayer player, Clowder clowder, Ownership owner, Block b, int x, int y, int z) {

        if (player.inventory.hasItem(ModItems.debug)) return true;

        if (owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) return false;

        if (owner.zone == Zone.FACTION) {

            if (ItemMace.placeOverride.contains(b)) return true;

            if (clowder != owner.owner && !clowder.isRaidable()) return false;

            if (player.worldObj.getBlock(x, y, z) != ModBlocks.clowder_flag) {
                for (int i = x - 2; i <= x + 2; i++) {
                    for (int j = z - 2; j <= z + 2; j++) {

                        int h = player.worldObj.getHeightValue(i, j);

                        if (player.worldObj.getBlock(i, h, j) == ModBlocks.clowder_flag
                            || player.worldObj.getBlock(i, h - 2, j) == ModBlocks.clowder_flag) {
                            player.addChatMessage(
                                new ChatComponentText(
                                    EnumChatFormatting.RED + "Please refrain from obstructing the flag."));
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Prevents explosive damage on clowder territory under certain conditions.
     * 
     * @param event
     */
    @SubscribeEvent
    public void clowderExplosionEvent(Detonate event) {

        boolean bb = true;

        /*
         * for(int i = 0; i < event.getAffectedBlocks().size(); i++) {
         * ChunkPosition pos = event.getAffectedBlocks().get(i);
         * int x = pos.chunkPosX;
         * int y = pos.chunkPosY;
         * int z = pos.chunkPosZ;
         * Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
         * if(!canExplode(owner, event.world, x, y, z)) {
         * event.getAffectedBlocks().remove(i);
         * i--;
         * bb = false;
         * }
         * }
         */

        int x = (int) event.explosion.explosionX;
        int y = (int) event.explosion.explosionY;
        int z = (int) event.explosion.explosionZ;
        Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);

        if (!canExplode(owner, event.world, x, y, z)) {
            event.getAffectedBlocks()
                .clear();
            bb = false;
        }

        // TEMP
        // if(bb)
        // BobbyBreaker.handleExplosionEvent(event);

        ExplosionSound.handleExplosion(event.world, event.explosion);
    }

    public static boolean canExplode(Ownership owner, World world, int x, int y, int z) {

        if (owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) {
            return false;
        }

        if (owner.zone == Zone.FACTION) {

            if (!owner.owner.isRaidable()) {
                return false;
            }

            for (int i = x - 2; i <= x + 2; i++) for (int j = z - 2; j <= z + 2; j++)
                if (world.getBlock(i, y + 1, j) == ModBlocks.clowder_flag) return false;
        }

        return true;
    }

    /**
     * Prevents the interaction with blocks in clowder territory unless a war has been declared.
     * 
     * @param event
     */
    @SubscribeEvent
    public void clowderContainerEvent(PlayerInteractEvent event) {

        int x = event.x;
        int y = event.y;
        int z = event.z;
        EntityPlayer player = event.entityPlayer;
        Block b = event.world.getBlock(x, y, z);

        if (event.action == Action.RIGHT_CLICK_BLOCK) {

            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == ModItems.debug) return;

            Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);

            if (owner != null) {
                Clowder clowder = Clowder.getClowderFromPlayer(event.entityPlayer);

                if (!canInteract(player, clowder, owner, b, event)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private boolean canInteract(EntityPlayer player, Clowder clowder, Ownership owner, Block b,
        PlayerInteractEvent event) {

        if (player.inventory.hasItem(ModItems.debug)) return true;

        if (owner.zone == Zone.FACTION && clowder != owner.owner) {

            if (clowder == null || !clowder.isRaidable() || !owner.owner.isRaidable()) return false;

            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == ModItems.mace && ItemMace.interactOverride.contains(b) && owner.owner.isRaidable()) {
                return true;
            }

            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == Item.getItemFromBlock(ModBlocks.clowder_conquerer)) {
                // event.useBlock = Result.DENY;
                return true;
            }

            if (player.getHeldItem() != null && player.getHeldItem()
                .getItem() == Item.getItemFromBlock(ModBlocks.barricade)) {

                int x = event.x;
                x = x - 8;
                int y = event.y;
                y = y - 8;
                int z = event.z;
                z = z - 8;

                for (int u = x; u < x + 16; u++) {
                    for (int v = y; v < y + 16; v++) {
                        for (int w = z; w < z + 16; w++) {

                            if (player.worldObj.getBlock(u, v, w) == ModBlocks.clowder_conquerer) return true;
                        }
                    }
                }
            }

            return false;

            /*
             * if(b instanceof BlockContainer || b instanceof BlockButton || b instanceof BlockDoor || b instanceof
             * BlockAnvil ||
             * b instanceof BlockBed || b instanceof BlockCake || b instanceof BlockFenceGate || b instanceof BlockLever
             * ||
             * b instanceof BlockRedstoneDiode || b instanceof BlockTNT)
             * return false;
             */
        }

        return true;
    }

    public static final String NBTKEY = "lastClowder";

    /**
     * Sends a flag popup packet to a player if he enters a new territory and a clowder alert if a raider appears.
     * 
     * @param world
     * @param player
     */
    private void flagPopup(World world, EntityPlayer player) {

        TerritoryMeta meta = ClowderTerritory.getMetaFromIntCoords((int) player.posX, (int) player.posZ - 1);

        Ownership owner;

        if (meta == null) owner = ClowderTerritory.WILDERNESS;
        else owner = meta.owner;

        String name = owner.zone.toString();

        if (owner.zone == Zone.FACTION) name = owner.owner.name;

        if (meta != null) name += meta.name;

        String past = player.getEntityData()
            .getString(NBTKEY);

        if (past.isEmpty()) {
            player.getEntityData()
                .setString(NBTKEY, name);
            return;
        }

        if (!name.equals(past)) {

            if (owner.zone == Zone.FACTION) {

                String title = meta == null ? "" : meta.name;
                PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(owner.owner, title), (EntityPlayerMP) player);

                Clowder mine = Clowder.getClowderFromPlayer(player);

                if (player.inventory.hasItem(ModItems.mace) && mine != owner.owner) owner.owner.notifyAll(
                    player.worldObj,
                    new ChatComponentText(CommandClowder.ERROR + "A raider has just entered your territory!"));

            } else {
                PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(name), (EntityPlayerMP) player);
            }
        }

        player.getEntityData()
            .setString(NBTKEY, name);
    }

    /**
     * Mk.2 of the particle border, now optimized to work server-side!
     * 
     * @param world
     * @param player
     */
    private static void particleBorder2(World world, EntityPlayer player) {

        if (world.rand.nextInt(3) != 0) // let's reduce that a little
            return;

        int ox = ((int) player.posX / 16) * 16;
        int oz = ((int) player.posZ / 16) * 16;

        int range = 4;

        for (int x = -range; x < range; x++) {
            for (int z = -range; z < range; z++) {

                Ownership center = ClowderTerritory.getOwnerFromInts(ox + x * 16 + 1, oz + z * 16);
                Ownership north = ClowderTerritory.getOwnerFromInts(
                    ox + (x + ForgeDirection.NORTH.offsetX) * 16 + 1,
                    oz + (z + ForgeDirection.NORTH.offsetZ) * 16);
                Ownership west = ClowderTerritory.getOwnerFromInts(
                    ox + (x + ForgeDirection.WEST.offsetX) * 16 + 1,
                    oz + (z + ForgeDirection.WEST.offsetZ) * 16);

                Ownership none = ClowderTerritory.WILDERNESS;
                boolean n = isTerritoryDifferent(north, center);
                boolean w = isTerritoryDifferent(west, center);

                int nc = ((center != none ? center.getColor() : (north != none ? north.getColor() : 0x000000))
                    + (north != none ? north.getColor() : (center != none ? center.getColor() : 0x000000))) / 2;
                int wc = ((center != none ? center.getColor() : (west != none ? west.getColor() : 0x000000))
                    + (west != none ? west.getColor() : (center != none ? center.getColor() : 0x000000))) / 2;

                if (n) PacketDispatcher.wrapper.sendTo(
                    new ClowderBorderPacket(
                        ox + x * 16,
                        oz + z * 16,
                        ox + (x - ForgeDirection.WEST.offsetX) * 16,
                        oz + (z - ForgeDirection.WEST.offsetZ) * 16,
                        nc),
                    (EntityPlayerMP) player);
                if (w) PacketDispatcher.wrapper.sendTo(
                    new ClowderBorderPacket(
                        ox + x * 16,
                        oz + z * 16,
                        ox + (x - ForgeDirection.NORTH.offsetX) * 16,
                        oz + (z - ForgeDirection.NORTH.offsetZ) * 16,
                        wc),
                    (EntityPlayerMP) player);
            }
        }
    }

    /**
     * Compares two ownership instances.
     * 
     * @param one
     * @param two
     * @return
     */
    private static boolean isTerritoryDifferent(Ownership one, Ownership two) {

        if (one == null && two != null) return true;

        if (one != null && two == null) return true;

        if (one != null && two != null) {

            if (one.zone != two.zone) return true;

            if (one.owner != two.owner) return true;
        }

        return false;
    }

    @SubscribeEvent
    public void onEntityHurt(LivingAttackEvent event) {

        EntityLivingBase e = event.entityLiving;
        DamageSource dmg = event.source;

        Ownership owner = ClowderTerritory.getOwner((int) e.posX, (int) e.posZ);

        if (e instanceof EntityPlayer && owner != null && owner.zone == Zone.SAFEZONE) event.setCanceled(true);
    }

    /**
     * Handles player ticks for clowders, mainly the flag popup and online times (with retreat kick)
     * 
     * @param event
     */
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        EntityPlayer player = event.player;
        String name = player.getDisplayName();

        if (!player.worldObj.isRemote) {

            Ownership owner = ClowderTerritory.getOwnerFromInts((int) player.posX, (int) player.posZ - 1);
            flagPopup(player.worldObj, player);

            Clowder clowder = Clowder.getClowderFromPlayer(player);

            if (clowder != null && clowder.members.get(name) != null) {

                EntityPlayerMP mp = (EntityPlayerMP) player;

                if (!mp.playerNetServerHandler.netManager.isChannelOpen()) {
                    System.out.println(
                        "Player " + player.getDisplayName() + " has been ticked, even though they are disconnected!");
                    return;
                }

                if (!Clowder.retreating.contains(name)) {

                    // 10 minutes
                    long time = 60 * 10 * 1000;
                    // updates the time on the online timer until the player is retreating
                    clowder.members.put(player.getDisplayName(), System.currentTimeMillis() + time);

                } else {

                    Long l = clowder.members.get(name);

                    // retreats if the time is up
                    if (l < System.currentTimeMillis()) {
                        mp.playerNetServerHandler.kickPlayerFromServer("You have just retreated!");
                    }
                }

                // is not in any clowder
            } else {

                if (Clowder.retreating.contains(name)) {
                    Clowder.retreating.remove(name);
                }

                if (owner != null && owner.zone == Zone.FACTION) {
                    player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 2));
                    player.addPotionEffect(new PotionEffect(Potion.weakness.id, 20, 2));
                    player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
                }
            }

            particleBorder2(player.worldObj, player);

            if (player.inventory.armorInventory[0] != null
                && player.inventory.armorInventory[0].getItem() == ModItems.clowder_banner) {
                ItemStack banner = player.inventory.armorInventory[0];

                if (clowder != null) {

                    if (!banner.hasTagCompound()) banner.stackTagCompound = new NBTTagCompound();

                    banner.stackTagCompound.setInteger("flag", clowder.flag.ordinal());
                    banner.stackTagCompound.setInteger("color", clowder.color);
                }
            }
        }
    }

    @SubscribeEvent
    public void myBonesHurtEvent(PlayerLoggedOutEvent event) {

        EntityPlayer player = event.player;
        String name = player.getDisplayName();
        Clowder clowder = Clowder.getClowderFromPlayer(player);

        if (clowder != null && clowder.retreating.contains(name)) {

            Long l = clowder.members.get(name);

            if (l != null && l < System.currentTimeMillis()) {
                clowder.notifyAll(
                    player.worldObj,
                    new ChatComponentText(CommandClowder.INFO + "Player " + name + " has just retreated!"));
                clowder.members.put(name, System.currentTimeMillis());
            }

            clowder.retreating.remove(name);
        }
    }

    int delay = 0;
    int hour = 0;

    /**
     * Handles world ticks for clowders, mainly the claim decay automaton.
     * 
     * @param event
     */
    @SubscribeEvent
    public void onWorldTick(WorldTickEvent event) {

        World world = event.world;

        if (world.isRemote || world.provider.dimensionId != 0 || event.phase == Phase.END) return;

        if (hour > 0) {
            hour--;
        } else {
            hour = MainRegistry.prestigeDelay;
            Clowder.updatePrestige(world);
            MainRegistry.logger.info("Updated clowder prestige levels!");
        }

        if (delay > 0) {
            delay--;
            return;
        } else {
            delay = MainRegistry.territoryDelay;
        }

        List<Long> rem = new ArrayList();
        for (Long time : Clowder.teleports.keySet()) {

            ScheduledTeleport tp = Clowder.teleports.get(time);
            EntityPlayer player = world.getPlayerEntityByName(tp.player);

            if (player == null) continue;

            if (time < System.currentTimeMillis()) {

                Ownership owner = ClowderTerritory.getOwnerFromInts(tp.posX, tp.posZ);
                Clowder me = Clowder.getClowderFromPlayer(player);

                if (owner == null || owner.zone != Zone.FACTION || owner.owner != me) {

                    player.addChatMessage(
                        new ChatComponentText(
                            CommandClowder.ERROR + "Warp destination appears to be outside of your territory."));
                    player.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "Warp aborted."));

                } else if (player instanceof EntityPlayerMP) {

                    EntityPlayerMP playermp = (EntityPlayerMP) player;
                    playermp.mountEntity(null);
                    playermp.playerNetServerHandler.setPlayerLocation(
                        tp.posX + 0.5D,
                        tp.posY,
                        tp.posZ + 0.5D,
                        player.rotationYaw,
                        player.rotationPitch);

                    if (!tp.home) {
                        me.notifyAll(
                            world,
                            new ChatComponentText(
                                CommandClowder.INFO + "Player "
                                    + player.getDisplayName()
                                    + " is warping to "
                                    + tp.warp
                                    + "!"));
                    } else {
                        me.notifyAll(
                            world,
                            new ChatComponentText(
                                CommandClowder.INFO + "Player " + player.getDisplayName() + " is warping home!"));
                        playermp.addPotionEffect(new PotionEffect(Potion.resistance.id, 60, 9));
                    }

                }
                rem.add(time);

            } else {

                if (player.posX != player.lastTickPosX || player.posZ != player.lastTickPosZ) {
                    rem.add(time);
                    player.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "Warp aborted!"));
                }
            }
        }

        for (Long time : rem) {
            Clowder.teleports.remove(time);
        }

        /// CLOWDER TERRITORYY ADMINISTRATIVE STUFF START ///
        // ClowderTerritory.persistenceAutomaton(world);
        /// CLOWDER TERRITORYY ADMINISTRATIVE STUFF END ///
    }

    @SubscribeEvent
    public void yeetEvent(ItemTossEvent event) {

        try {

            if (event.entityItem.getEntityItem()
                .getItem() == ModItems.capsule) {
                event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.capsule));
                event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.capsule));
                event.setCanceled(true);
            }

        } catch (NullPointerException ex) {}
    }

}
