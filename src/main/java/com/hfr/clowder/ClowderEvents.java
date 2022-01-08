package com.hfr.clowder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.flansmod.common.guns.EntityBullet;
import com.flansmod.common.guns.EntityGrenade;
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
import com.hfr.packet.effect.CumPacket;
import com.hfr.tileentity.clowder.TileEntityFlagBig;
import com.hfr.tileentity.prop.TileEntityProp;
import com.hfr.tileentity.prop.TileEntityStatue;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;

public class ClowderEvents {

	private static final boolean canBuildInWilderness = false;
	
	@SubscribeEvent
	public void clowderLoadEvent(WorldEvent.Load event) {

		if (event.world.provider.dimensionId == 0) {
			ClowderData.getData(event.world);
		}
	}

	@SubscribeEvent
	public void clowderLoadEvent(WorldEvent.Unload event) {

		if (event.world.provider.dimensionId == 0) {
			ClowderData.getData(event.world).markDirty();
		}
	}

	/**
	 * Handles chat events related to clowders, mainly adding the clowder name to a
	 * chat message.
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void handleChatServer(ServerChatEvent event) {

		Clowder clowder = Clowder.getClowderFromPlayer(event.player);

		if (clowder != null) {

			if (event.player.getEntityData().getInteger(CommandClowderChat.CHAT_KEY) == 1) {
				sendToTeam(clowder, event.player, event.message);
				event.setCanceled(true);
				return;
			}
			
			if (event.player.getEntityData().getInteger(CommandClowderChat.CHAT_KEY) == 2) {
				sendToAlliance(clowder, event.player, event.message);
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
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(message));
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
	
	private void sendToAlliance(Clowder clowder, EntityPlayer player, String message) { //for ally chat

		String name = "";

		if (clowder.getPermLevel(player.getDisplayName()) > 2) {
			name += "<Leader> ";
		} else if (clowder.getPermLevel(player.getDisplayName()) > 1) {
			name += "<Officer> ";
		} else if (clowder.getPermLevel(player.getDisplayName()) > 0) {
			name += "<Citizen> ";
		}

		name += "[" + player.getDisplayName() + "]";

		clowder.notifyAll(player.worldObj, new ChatComponentText(CommandClowderChat.LIST + name + " " + message));
		

		System.out.println(name + " " + message);
		
		for (Clowder a : clowder.allies.keySet())
		{
		a.notifyAll(player.worldObj, new ChatComponentText(CommandClowderChat.LIST + "<Ally> [" + player.getDisplayName() + "] " + message)); //send to all allies
		}

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

				if (!canPlace(player, clowder, owner, b, x, y, z) || (!isNotWilderness(event.world, x, z) && !canBuildInWilderness && !player.capabilities.isCreativeMode)) {
					if(!isNotWilderness(event.world, x, z) && !canBuildInWilderness && !player.capabilities.isCreativeMode)
						player.addChatComponentMessage(new ChatComponentText("You cannot build in wilderness"));
					event.setCanceled(true);
					return;
				}

				if (!player.inventory.hasItem(ModItems.debug) && (owner == null || owner.zone == Zone.WILDERNESS)
						&& (b == Blocks.chest || b == Blocks.trapped_chest)) {

					player.addChatMessage(
							new ChatComponentText(CommandClowder.ERROR + "Chests are disabled outside of claims."));
					player.addChatMessage(new ChatComponentText(CommandClowder.ERROR
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

					if (prop != null && prop.operational())
						owner.owner.multPrestige(0.975F, world);
				}
			}

			if (b == ModBlocks.statue) {

				int[] loc = ((BlockDummyable) b).findCoreRec(world, x, y, z);

				if (loc != null) {
					TileEntityStatue prop = (TileEntityStatue) world.getTileEntity(loc[0], loc[1], loc[2]);

					if (prop != null && prop.operational())
						owner.owner.multPrestige(0.975F, world);
				}
			}
		}
	}

	private boolean canBreak(EntityPlayer player, Clowder clowder, Ownership owner, Block b, int x, int y, int z) {

		if (player.inventory.hasItem(ModItems.debug))
			return true;

		if (owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE)
			return false;

		if (owner.zone == Zone.FACTION) {

			if (clowder != owner.owner) {
				if (clowder != null && !clowder.isRaidable())
					return false;

				if (!owner.owner.isRaidable())
					return false;

				if (player.getHeldItem() == null)
					return false;

				if (player.getHeldItem().getItem() == ModItems.mace && ItemMace.breakOverride.contains(b))
					return true;

				return false;
			} else {

				if (player.worldObj.getBlock(x, y, z) == ModBlocks.officer_chest
						&& clowder.getPermLevel(player.getDisplayName()) < 2) {
					player.addChatMessage(new ChatComponentText(
							EnumChatFormatting.RED + "You lack the permissions to destroy this chest."));
					return false;
				}
			}

			if (player.worldObj.getBlock(x, y, z) != ModBlocks.clowder_flag) {
				for (int i = x - 2; i <= x + 2; i++)
					for (int j = z - 2; j <= z + 2; j++)
						if (player.worldObj.getBlock(i, y + 1, j) == ModBlocks.clowder_flag) {

							player.addChatMessage(new ChatComponentText(
									EnumChatFormatting.RED + "Please refrain from breaking the flag's foundation."));
							return false;
						}
			}
		}

		return true;
	}

	private boolean isNotWilderness(World world, int x, int z) {
		TerritoryMeta meta = ClowderTerritory.getMetaFromIntCoords(x, z);
		return meta != null && meta.owner != null && meta.owner.zone == Zone.FACTION;
	}
	
	private boolean canPlace(EntityPlayer player, Clowder clowder, Ownership owner, Block b, int x, int y, int z) {

		if (player.inventory.hasItem(ModItems.debug))
			return true;

		if (owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE)
			return false;

		if (owner.zone == Zone.FACTION) {

			if (ItemMace.placeOverride.contains(b))
				return true;

			/*
			 * if(b.getClass().getName() == "net.minecraft.block.BlockPistonBase") return
			 * true;
			 */

			// land mine exception
			if (b.getClass().getName().contains("Mayin") || b.getClass().getName().contains("Patlayici"))
				return true;

			/*
			 * kept causing crashes if(clowder != owner.owner && !clowder.isRaidable())
			 * return false;
			 */

			if (player.worldObj.getBlock(x, y, z) != ModBlocks.clowder_flag) {
				for (int i = x - 2; i <= x + 2; i++) {
					for (int j = z - 2; j <= z + 2; j++) {

						int h = player.worldObj.getHeightValue(i, j);

						if (player.worldObj.getBlock(i, h, j) == ModBlocks.clowder_flag
								|| player.worldObj.getBlock(i, h - 2, j) == ModBlocks.clowder_flag) {
							player.addChatMessage(new ChatComponentText(
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
	 * @param event
	 */
	@SubscribeEvent
	public void clowderExplosionEvent(Detonate event) {
		
		boolean bb = true;
		
		boolean flansplode = true;
		
		/*for(int i = 0; i < event.getAffectedBlocks().size(); i++) {
			
			ChunkPosition pos = event.getAffectedBlocks().get(i);
			int x = pos.chunkPosX;
			int y = pos.chunkPosY;
			int z = pos.chunkPosZ;
			
			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			
			if(!canExplode(owner, event.world, x, y, z)) {
				event.getAffectedBlocks().remove(i);
				i--;
				bb = false;
			}
		}*/

		int x = (int)event.explosion.explosionX;
		int y = (int)event.explosion.explosionY;
		int z = (int)event.explosion.explosionZ;
		Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
		
		EntityPlayer shooter = null;
		
		Boolean AtWar = false;
		
		if(event.explosion.exploder instanceof EntityBullet)
		{
				
			//System.out.println("flan bullet succesfully detected");
			EntityBullet Raiderino = (EntityBullet) event.explosion.exploder;
			if (Raiderino.owner != null)
			{
				shooter = (EntityPlayer) Raiderino.owner;
				String name = shooter.getCommandSenderName();
				Clowder shooterFac = Clowder.getClowderFromPlayer(shooter);
				
				//re-coded it as false unless specific conditions meant
				
				
				
				
				
				//generic null check, can explode check and no wilderness/safezone/warzone  also no bombing yourself
				if(shooterFac != null && owner.owner != null && canExplode(owner, event.world, x, y, z) && owner.owner != shooterFac )
				{
					
					//no war on master to offline bomb vassals exploit
					if(owner.owner.suzerain != null && shooterFac.enemy == owner.owner.suzerain && !shooterFac.vassalTarget && !owner.owner.isRaidable())
					{
						flansplode = false;
						shooter.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "/c fabricate on a vassal/bitch if you want to bomb them."));
						shooter.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "Fabricating directly against master means offline vassals are safe!"));
					}
					else
					
					//specific conditions of clowder war - now including vassals      case 1 victim bombs attacker land   ||    case 2 victim bombs attackers vassal land - important for bitch war    ||     case 3 attacker bombs victim land or attacker bombs victim's vassal land             case 4 attacker's vassal bombs attacker's victim       ||      case 5 victims vassal bombs attacker land - important for bitch wars   - who cares if vassals can bomb each other. let them direct war if they want to!
					if(   (  owner.owner.enemy!=null && owner.owner.getWartime() > 0 && shooterFac == owner.owner.enemy)  || owner.owner.suzerain != null && owner.owner.suzerain.enemy != null && owner.owner.suzerain.getWartime() > 0 && shooterFac == owner.owner.suzerain.enemy       || (shooterFac.enemy != null && shooterFac.getWartime() > 0 && (owner.owner == shooterFac.enemy || (owner.owner.suzerain!=null && owner.owner.suzerain == shooterFac.enemy)))    ||   shooterFac.suzerain != null && shooterFac.suzerain.enemy != null && shooterFac.suzerain.getWartime()>0 && owner.owner == shooterFac.suzerain.enemy    ||   shooterFac.suzerain!=null && owner.owner.enemy!=null && shooterFac.suzerain == owner.owner.enemy && owner.owner.getWartime() > 0)
					{
						flansplode = true;
					}
					else flansplode = false;
					
				}
				else flansplode = false;
				
				
				//old autistic way of true by default and making up a bunch of false conditions
				
				/*
				if (shooterFac != null)
				System.out.println("explosion owner is " + name + " from the clowder " + shooterFac.name);
				else
					System.out.println("explosion owner is " + name + " and he is factionless");
							
				if(shooterFac != null && owner.owner != null)
				{
				//i think this change will fix only attacker being able to grief raid. nvm fuck it ill recode this as false by default unless specifically at war	
					if(shooterFac.getWartime()==0 && owner.owner == shooterFac.enemy || owner.owner.getWartime()==0 && shooterFac == owner.owner.enemy)
					{event.getAffectedBlocks().clear();
					bb = false;
					}
					
					
				if(owner.owner.valid() && shooterFac.valid())	
				if(owner.owner.enemy != null && shooterFac.enemy != null)
				{ //allow raidable stuff to apply to explosions (but not flag conquests!)
				 if (shooterFac.enemy == owner.owner && shooterFac.getWartime() <= 0 && !canExplode(owner, event.world, x, y, z) || owner.owner.enemy == shooterFac.enemy && owner.owner.getWartime() <= 0  && !canExplode(owner, event.world, x, y, z))
				{
					event.getAffectedBlocks().clear();
				bb = false;
				}
				
				}
				
				}
				
				*/
				
			}
			else flansplode = false;
			
			
			
		}
		//end of flan bullet griefing handler
		
		
		
		
		
		//dont explode if non raidable and not atwar
		if(!canExplode(owner, event.world, x, y, z) || !flansplode) {
			event.getAffectedBlocks().clear();
			bb = false;
		}
		
		//TEMP
		//if(bb)
		//	BobbyBreaker.handleExplosionEvent(event);
		
		ExplosionSound.handleExplosion(event.world, event.explosion);
	}
	
	public static boolean canExplode(Ownership owner, World world, int x, int y, int z) {
		
		if(owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE || owner.zone == Zone.WILDERNESS ) {
			return false;
		}
		
		if(owner.zone == Zone.FACTION) {
			
			if(!owner.owner.isRaidable() && owner.owner.suzerain == null && !owner.owner.vassalTarget) {  //vassals/bitches and people targeting vassals/bitches can be bombed
				return false;
			}


			
			//prevent flags from exploding - allah notes
			for(int i = x - 2; i <= x + 2; i++)
				for(int j = z - 2; j <= z + 2; j++)
					if(world.getBlock(i, y + 1, j) == ModBlocks.clowder_flag)
						return false;
		}
		
		return true;
	}
	
	/**
	 * Prevents the interaction with blocks in clowder territory unless a war has been declared.
	 * @param event
	 */
	@SubscribeEvent
	public void clowderContainerEvent(PlayerInteractEvent event) {

		int x = event.x;
		int y = event.y;
		int z = event.z;
		EntityPlayer player = event.entityPlayer;
		Block b = event.world.getBlock(x, y, z);
		
		if(event.action == Action.RIGHT_CLICK_BLOCK) {
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.debug)
				return;
			
			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.debug)
				return;
			
			
			if(owner != null) {
				Clowder clowder = Clowder.getClowderFromPlayer(event.entityPlayer);
				
				//for landmine bypass
				if(player.getHeldItem() != null)
				{
					String s = player.getHeldItem().getItem().getClass().getName();
					//System.out.println(player.getHeldItem().getItem().getClass().getSuperclass().getName());
					//	if(player.getHeldItem().getClass().getName().endsWith("MayinZemin"))
			//	if(player.getHeldItem().getClass().getSuperclass().getName().endsWith("Mayin"))
					//if(b.getClass().getName() == "net.minecraft.block.BlockPistonBase")
					//if(player.getHeldItem().getItem().getClass().getName().endsWith("BlockPistonBase"))
					//	event.setCanceled(false);
					if(s.contains("Mayin")||s.contains("Patlayici"))
						event.setCanceled(false);
				else if(!canInteract(player, clowder, owner, b, event)  && !!canPlace(player, clowder, owner, b, x, y, z) ) {
					event.setCanceled(true);
				}
				}
				
				
				
				//include bypass so landmines cna be placed in enemy land failed attempt
				//    if(!canInteract(player, clowder, owner, b, event) && !(b.getClass().getName() == "net.minecraft.block.BlockPistonBase"))   with meddling to allow right clicking pistons 
				else if(!canInteract(player, clowder, owner, b, event) ) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	private boolean canInteract(EntityPlayer player, Clowder clowder, Ownership owner, Block b, PlayerInteractEvent event) {
		
		if(player.inventory.hasItem(ModItems.debug))
			return true;
		
		if(owner.zone == Zone.FACTION && clowder != owner.owner) {
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Item.getItemFromBlock(ModBlocks.clowder_conquerer)) {
				//event.useBlock = Result.DENY;
				return true;
			}
			
			if(clowder == null || !clowder.isRaidable() || !owner.owner.isRaidable())
				return false;
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.mace && ItemMace.interactOverride.contains(b) && owner.owner.isRaidable()) {
				return true;
			}
			
			

			//landmine test
			/*
			if(player.getHeldItem() != null && (b.getClass().getName() == "net.minecraft.block.BlockPistonBase")) {
				

								return true;	
			}
			*/
			
			
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Item.getItemFromBlock(ModBlocks.barricade)) {
				
				int x = event.x; x = x - 8;
				int y = event.y; y = y - 8;
				int z = event.z; z = z - 8;
				
				for(int u = x; u < x + 16; u++) {
					for(int v = y; v < y + 16; v++) {
						for(int w = z; w < z + 16; w++) {
							
							if(player.worldObj.getBlock(u, v, w) == ModBlocks.clowder_conquerer)
								return true;
						}
					}
				}
			}
			
			return false;
			
			/*if(b instanceof BlockContainer || b instanceof BlockButton || b instanceof BlockDoor || b instanceof BlockAnvil ||
					b instanceof BlockBed || b instanceof BlockCake || b instanceof BlockFenceGate || b instanceof BlockLever ||
					b instanceof BlockRedstoneDiode || b instanceof BlockTNT)
				return false;*/
		}
		
		return true;
	}

	public static final String NBTKEY = "lastClowder";

	/**
	 * Sends a flag popup packet to a player if he enters a new territory and a
	 * clowder alert if a raider appears.
	 * 
	 * @param world
	 * @param player
	 */

	// this is the void that activates when you enter a territory to display epic
	// graphical flag! - important allah note
	private void flagPopup(World world, EntityPlayer player) {

		TerritoryMeta meta = ClowderTerritory.getMetaFromIntCoords((int) player.posX, (int) player.posZ - 1);

		Ownership owner;

		if (meta == null)
			owner = ClowderTerritory.WILDERNESS;
		else
			owner = meta.owner;

		String name = owner.zone.toString();

		if (owner.zone == Zone.FACTION)
			name = owner.owner.name;
		TileEntity flag = null;
		
		if(meta != null) 
			flag = world.getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
		if(flag != null && flag instanceof TileEntityFlagBig)
			name += ((TileEntityFlagBig)flag).provinceName;

		String past = player.getEntityData().getString(NBTKEY);

		if (past.isEmpty()) {
			player.getEntityData().setString(NBTKEY, name);
			return;
		}

		if (!name.equals(past)) {

			if (owner.zone == Zone.FACTION) {
				String title = "";
				if(meta != null) 
					flag = world.getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
				if(flag != null && flag instanceof TileEntityFlagBig)
					title = ((TileEntityFlagBig)flag).provinceName;
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(owner.owner, title), (EntityPlayerMP) player);

				Clowder mine = Clowder.getClowderFromPlayer(player);

				// allah notes the brown owner means owner of flag, and the second owner is for
				// the faction itself. xddd bob you cheeky bastard you jerry rigged the flag
				// display code for this

			} else if (meta != null && flag != null && flag instanceof TileEntityFlagBig && !((TileEntityFlagBig)flag).provinceName.equals("")) {
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(Zone.WILDERNESS.toString(), ((TileEntityFlagBig)flag).provinceName),
						(EntityPlayerMP) player);
			} else {
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(name), (EntityPlayerMP) player);
			}
		}

		player.getEntityData().setString(NBTKEY, name);
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
				Ownership north = ClowderTerritory.getOwnerFromInts(ox + (x + ForgeDirection.NORTH.offsetX) * 16 + 1,
						oz + (z + ForgeDirection.NORTH.offsetZ) * 16);
				Ownership west = ClowderTerritory.getOwnerFromInts(ox + (x + ForgeDirection.WEST.offsetX) * 16 + 1,
						oz + (z + ForgeDirection.WEST.offsetZ) * 16);

				Ownership none = ClowderTerritory.WILDERNESS;
				boolean n = isTerritoryDifferent(north, center);
				boolean w = isTerritoryDifferent(west, center);

				int nc = ((center != none ? center.getColor() : (north != none ? north.getColor() : 0x000000))
						+ (north != none ? north.getColor() : (center != none ? center.getColor() : 0x000000))) / 2;
				int wc = ((center != none ? center.getColor() : (west != none ? west.getColor() : 0x000000))
						+ (west != none ? west.getColor() : (center != none ? center.getColor() : 0x000000))) / 2;

				if (n)
					PacketDispatcher.wrapper.sendTo(new ClowderBorderPacket(ox + x * 16, oz + z * 16,
							ox + (x - ForgeDirection.WEST.offsetX) * 16, oz + (z - ForgeDirection.WEST.offsetZ) * 16,
							nc), (EntityPlayerMP) player);
				if (w)
					PacketDispatcher.wrapper.sendTo(new ClowderBorderPacket(ox + x * 16, oz + z * 16,
							ox + (x - ForgeDirection.NORTH.offsetX) * 16, oz + (z - ForgeDirection.NORTH.offsetZ) * 16,
							wc), (EntityPlayerMP) player);
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

		if (one == null && two != null)
			return true;

		if (one != null && two == null)
			return true;

		if (one != null && two != null) {

			if (one.zone != two.zone)
				return true;

			if (one.owner != two.owner)
				return true;
		}

		return false;
	}

	@SubscribeEvent
	public void onEntityHurt(LivingAttackEvent event) {

		EntityLivingBase e = event.entityLiving;
		DamageSource dmg = event.source;

		Ownership owner = ClowderTerritory.getOwner((int) e.posX, (int) e.posZ);

		if (e instanceof EntityPlayer && owner != null && owner.zone == Zone.SAFEZONE)
			event.setCanceled(true);
	}

	@SubscribeEvent	
	public void onEntityKilled(LivingDeathEvent event)  
	{
		EntityLivingBase entity = event.entityLiving;
		Entity souceEntity = event.source.getSourceOfDamage();
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer human = (EntityPlayer) entity;
			Clowder clowder = Clowder.getClowderFromPlayer(human);
			//war ends faster if an attacker dies during a war    now its 0.2 minutes    allah bookmark
			if (clowder != null)
			{
				if (clowder.getWartime() > 0 && clowder.enemy != clowder.suzerain)
					clowder.addWarTime(-0.5f, human.worldObj);
				//death si worth 1 minute if revolt
				if (clowder.getWartime() > 0 && clowder.enemy == clowder.suzerain)
					clowder.addWarTime(-1f, human.worldObj);
				
			}
			
		
		Clowder Murderers = null;
		EntityPlayer killer = null;
		
		//check grenade kills
		if(souceEntity instanceof EntityGrenade)
		{
			killer = (EntityPlayer) ((EntityGrenade) souceEntity).thrower;
			Murderers = Clowder.getClowderFromPlayer(killer);
		}
		//check bullet kills
		else if(souceEntity instanceof EntityBullet)
		{
		killer = (EntityPlayer) ((EntityBullet) souceEntity).owner;
		Murderers = Clowder.getClowderFromPlayer(killer);
		}
		
		//weeder fix
			if(Murderers != null && Murderers.valid() && killer != null)
		{
			//a whole world where we can analyze the murderer's faction  allah bookmark
			
			//during a revolt killing a master makes war score go up by 3
			if (Murderers.suzerain == clowder && Murderers.enemy == clowder && Murderers.getWartime() > 0)
			{
				Murderers.addWarTime(3f, human.worldObj);
				//System.out.println("Debug remove later: game acknowledged a rebel killing a master +3 minutes");
			}
				
		}		
		
	}
		
	}
	

	
	/**
	 * Handles player ticks for clowders, mainly the flag popup and online times (with retreat kick)
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		String name = player.getDisplayName();
		
		if(!player.worldObj.isRemote) {

			Ownership owner = ClowderTerritory.getOwnerFromInts((int)player.posX, (int)player.posZ - 1);
			flagPopup(player.worldObj, player);
			
			Clowder clowder = Clowder.getClowderFromPlayer(player);
			

			
			
			
			if(clowder != null && clowder.members.get(name) != null) {
				
				EntityPlayerMP mp = (EntityPlayerMP)player;
				
				if(!mp.playerNetServerHandler.netManager.isChannelOpen()) {
					System.out.println("Player " + player.getDisplayName() + " has been ticked, even though they are disconnected!");
					return;
				}
				
				
				//retreat ban manual tempban - retconned    also it turned out this did nothing wrong, ti was the members list system that caused boo boos
				if(clowder.retreatBan > 0)
				{					
					//mp.playerNetServerHandler.kickPlayerFromServer("Your faction is in retreat mode! You can log in again in " + clowder.retreatBan + " minutes!");
					
				//	MinecraftServer minecraftserver = MinecraftServer.getServer();
					
				//	 ICommandManager icommandmanager = minecraftserver.getCommandManager();
			          // icommandmanager.executeCommand((ICommandSender) this, "/weather clear");
					 
					// MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "weather clear");
					// MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "say " + mp.getDisplayName() + " is a nigger");

				}
				
				
				if(!Clowder.retreating.contains(name)) {
					
					//10 minutes
					//long time = 60 * 10 * 1000;
					
					//one minute temporarily for testing. actually fuck it lets be permanenet >:)   fuck it mark 2, its now ten seconds
					long time = 10 * 1 * 1000;
					//updates the time on the online timer until the player is retreating
					clowder.members.put(player.getDisplayName(), System.currentTimeMillis() + time);
					
				} 
				
				/*   //moving this shit directly to command clowder. fuck this retreat timer bullshit
				else 
				{
					
					Long l = clowder.members.get(name);
					
					//retreats if the time is up
					if(l < System.currentTimeMillis()) {
						
						//removes targeted status, used to fuck over enemy fabrications
						if(clowder.targeted)
							clowder.targeted=false;
						
							
							
						
							
							
							
							//due this shit to all enemies who were fabricating against the retreatee
							for(Clowder everyone : clowder.clowders)
							{
								if (everyone.enemy == clowder && everyone.getWartime() == 0 && everyone.getCanDeclareTime() == 0)
								{
									//notifies them of cancellation
									everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has retreated, cancelling our war fabrication attempt!"));
									everyone.notifyAll(player.worldObj, new ChatComponentText("Because of this act of cowardice, we earn back the prestige we spent!"));
									//give back prestige cost of fabrication
									everyone.addPrestige(MainRegistry.fabricateCost, player.worldObj);
									//cancels fabrications
									everyone.pussy(player.worldObj);
								}
								
								//retrating during war
								if (everyone.enemy == clowder && everyone.getWartime() > 0 && clowder.retreatBan == 0)
								{
									//just ban EVERYONE in the faction
									for(String s : clowder.members.keySet())
										Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 6h You are tempbanned because you retreated!", s));	
									
									//Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 6h You are tempbanned because you retreated!", mp.getDisplayName()));	
									clowder.retreatBanHammer(player, clowder.name, 360);
									//mp.playerNetServerHandler.kickPlayerFromServer("You have just retreated! You cannot log back in for 6 hours you coward!");
									//MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "tempban " + mp.getDisplayName() + " 6h You have just retreated! You are tempbanned for 6 hours you coward!");
									
									if(everyone.getWartime() > 10)
									{
										everyone.freeWarTime(player.worldObj);
									everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has retreated! We get 10 minutes to capture as much as we want for free!"));
									}
									else
										everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has retreated! Quick capture as much land as possible before the timer runs out!"));
								}
								
								//attacker retreats
								if (clowder.getWartime() > 0 && clowder.retreatBan == 0)
								{
									//just ban EVERYONE in the faction
									for(String s : clowder.members.keySet())
										Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 6h You are tempbanned because you retreated!", s));	
									//Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 6h You are tempbanned because you retreated!", mp.getDisplayName()));	
									clowder.retreatBanHammer(player, clowder.name, 360);
									//mp.playerNetServerHandler.kickPlayerFromServer("We underestimated our target. We must lick our wounds for at least 6 hours!");
								//	MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "tempban " + mp.getDisplayName() + " 6h We underestimated our target. We must lick our wounds for at least 6 hours!");
		
									if(clowder.getWartime() > 10)
									{
										clowder.freeWarTime(player.worldObj);
									clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has called off their invasion! We get 10 minutes to take as much land as we want!"));
									}
									else
										everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has given up on their invasion! Quick capture as much land as possible before the timer runs out!"));
								}
								
							}
						
						
						//for the ban system. 240 minute ban if not at war
						if(clowder.retreatBan == 0)
						clowder.retreatBanHammer(player, clowder.name, 240);
						//just ban EVERYONE in the faction
						
						for(String s : clowder.members.keySet())
							Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 4h You are tempbanned because you retreated!", s));	
						//Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 4h You are tempbanned because you retreated!", mp.getDisplayName()));	
						// MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "tempban " + mp.getDisplayName() + " 4h You have just retreated! You cannot log back in for 4 hours you coward!");
						//mp.playerNetServerHandler.kickPlayerFromServer("You have just retreated! You cannot log back in for 4 hours you coward!");
			

						
					}
				}
				*/
				
			//is not in any clowder
			} else {
				
				if(Clowder.retreating.contains(name)) {
					Clowder.retreating.remove(name);
				}
				
				
				//this is the thing where no faction people get sick if in clowder land  allah bookmark
				if(owner != null && owner.zone == Zone.FACTION) {
					player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20, 2));
					player.addPotionEffect(new PotionEffect(Potion.weakness.id, 20, 2));
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
				}
			}
			
			particleBorder2(player.worldObj, player);
			
			if(player.inventory.armorInventory[0] != null && player.inventory.armorInventory[0].getItem() == ModItems.clowder_banner) {
				ItemStack banner = player.inventory.armorInventory[0];
			    
				if(clowder != null) {
					
					if(!banner.hasTagCompound())
						banner.stackTagCompound = new NBTTagCompound();

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
		
		if(clowder != null && clowder.retreating.contains(name)) {
			
			Long l = clowder.members.get(name);
			
			if(l != null && l < System.currentTimeMillis()) {
				clowder.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.INFO + "Player " + name + " has just retreated!"));
				clowder.members.put(name, System.currentTimeMillis());
			}
			
			clowder.retreating.remove(name);
		}
	}

	int delay = 0;
	int hour = 0;
	
	//for war declaration
	int minute = 0;
	
	//for initialization
	int startup = 0;
	
	//for restocking markets
	int lastHour = 0;
	
	/**
	 * Handles world ticks for clowders, mainly the claim decay automaton.
	 * @param event
	 */
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		
		World world = event.world;
		
		
		
		
		
		if(world.isRemote || world.provider.dimensionId != 0 || event.phase == Phase.END)
			return;
		
		//labjac one time initialization of diplomatic stuff
		if(startup < 20)
			startup++;
		
		if(startup == 15)
		{
			Clowder.initializeDiplomacy(world);
		}
		
		//reset all market histories at 0:00 EST
		int currentHour = Calendar.getInstance(TimeZone.getTimeZone("EST")).get(Calendar.HOUR_OF_DAY);
		if(currentHour == 0 && lastHour == 23) {
			for(Clowder clowder : Clowder.clowders) {
				clowder.restockMarkets();
			}
		}
		
		lastHour = currentHour;
		
		//this is an hour timer! "prestigeDelay" is set to 1 hour in ticks by default! - allah notes
	
		if(hour > 0) {
			hour--;
		} else {
			hour = MainRegistry.prestigeDelay;
			Clowder.updatePrestige(world);
			MainRegistry.logger.info("Updated clowder prestige levels!");
			//the update prestige thing is THE WORLD telling it to update! - allah bookmark
		}
		
		
		if(minute > 0) {
			minute--;
		} else {
			minute = MainRegistry.warDelay;
			Clowder.updateWars(world); //wip update war and fabrication timers
		}
		
		//i will move mystring to diplomacy memory fixers here!
		
		
		
		if(delay > 0) {
			delay--;
			return;
		} else {
			delay = MainRegistry.territoryDelay;
		}
		
		List<Long> rem = new ArrayList();
		for(Long time : Clowder.teleports.keySet()) {
			
			ScheduledTeleport tp = Clowder.teleports.get(time);
			EntityPlayer player = world.getPlayerEntityByName(tp.player);
			
			if(player == null)
				continue;
			
			if(time < System.currentTimeMillis()) {
				
				Ownership owner = ClowderTerritory.getOwnerFromInts(tp.posX, tp.posZ);
				Clowder me = Clowder.getClowderFromPlayer(player);
				
				if(!tp.rendezvous && (owner == null || owner.zone != Zone.FACTION || owner.owner != me)) {

					player.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "Warp destination appears to be outside of your territory."));
					player.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "Warp aborted."));
					
				} else if(player instanceof EntityPlayerMP) {
					
					EntityPlayerMP playermp = (EntityPlayerMP)player;
					playermp.mountEntity(null);
					playermp.playerNetServerHandler.setPlayerLocation(tp.posX + 0.5D, tp.posY, tp.posZ + 0.5D, player.rotationYaw, player.rotationPitch);
					
					if(tp.rendezvous)
					{
						me.notifyAll(world, new ChatComponentText(CommandClowder.INFO + "Player " + player.getDisplayName() + " is warping to an ally rendezvous point!"));
						Clowder ally = Clowder.getClowderFromName(tp.allyName);
						if (ally != null)
							ally.notifyAll(world, new ChatComponentText(CommandClowder.INFO + "Player " + player.getDisplayName() + " from allied clowder " + me.name + " is warping to our ally rendezvous point!"));
					}
					else if(!tp.home) {
						me.notifyAll(world, new ChatComponentText(CommandClowder.INFO + "Player " + player.getDisplayName() + " is warping to " + tp.warp + "!"));
					} else {
						me.notifyAll(world, new ChatComponentText(CommandClowder.INFO + "Player " + player.getDisplayName() + " is warping home!"));
						playermp.addPotionEffect(new PotionEffect(Potion.resistance.id, 60, 9)); 
					}
					
				}
				rem.add(time);
				
			} else {
				
				if(player.posX != player.lastTickPosX || player.posZ != player.lastTickPosZ) {
					rem.add(time);
					player.addChatMessage(new ChatComponentText(CommandClowder.ERROR + "Warp aborted!"));
				}
			}
		}
		
		for(Long time : rem) {
			Clowder.teleports.remove(time);
		}
		
		/// CLOWDER TERRITORYY ADMINISTRATIVE STUFF START ///
		//ClowderTerritory.persistenceAutomaton(world);
		/// CLOWDER TERRITORYY ADMINISTRATIVE STUFF END ///
	}
	
	@SubscribeEvent
	public void yeetEvent(ItemTossEvent event) {
		
		try {
			
			if(event.entityItem.getEntityItem().getItem() == ModItems.capsule) {
				event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.capsule));
				event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.capsule));
				event.setCanceled(true);
			}
			
		} catch(NullPointerException ex) { }
	}

}