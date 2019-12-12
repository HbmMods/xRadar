package com.hfr.clowder;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.command.CommandClowder;
import com.hfr.items.ItemMace;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderBorderPacket;
import com.hfr.packet.effect.ClowderFlagPacket;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;

public class ClowderEvents {
	
	/**
	 * Handles chat events related to clowders, mainly adding the clowder name to a chat message.
	 * @param event
	 */
	@SubscribeEvent
	public void handleChatServer(ServerChatEvent event) {
		
		Clowder clowder = Clowder.getClowderFromPlayer(event.player);
		
		if(clowder != null) {
			
			event.setCanceled(true);
			String message = String.format("%s <%s> %s", clowder.name.replace('_', ' '), event.username, event.message);
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(message));
		}
	}
	
	/**
	 * Prevents breaking/placing blocks on foreign clowder territory.
	 * @param event
	 */
	@SubscribeEvent
	public void clowderBlockEvent(BlockEvent event) {
		
		if(event instanceof BreakEvent || event instanceof PlaceEvent) {
			int x = event.x;
			int y = event.y;
			int z = event.z;
			
			Block b = event.world.getBlock(x, y, z);
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(x, z));
			
			if(owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) {
				event.setCanceled(true);
				return;
			}
			
			if(event instanceof BreakEvent) {
				
				EntityPlayer player = ((BreakEvent)event).getPlayer();
				Clowder clowder = Clowder.getClowderFromPlayer(player);
				
				if(owner.zone == Zone.FACTION && clowder != owner.owner) {
					
					if(!(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.mace && ItemMace.breakOverride.contains(b)))
						event.setCanceled(true);
				}
			}
			
			if(event instanceof PlaceEvent) {
				
				EntityPlayer player = ((PlaceEvent)event).player;
				Clowder clowder = Clowder.getClowderFromPlayer(player);
				
				if(owner.zone == Zone.FACTION && clowder != owner.owner)
					event.setCanceled(true);
			}
		}
	}
	
	/**
	 * Prevents explosive damage on clowder territory under certain conditions.
	 * @param event
	 */
	@SubscribeEvent
	public void clowderExplosionEvent(Detonate event) {
		
		for(int i = 0; i < event.getAffectedBlocks().size(); i++) {
			
			ChunkPosition pos = event.getAffectedBlocks().get(i);
			int x = pos.chunkPosX;
			int z = pos.chunkPosZ;
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(x, z));
			
			if(owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) {
				event.getAffectedBlocks().remove(i);
				i--;
			}
		}
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
		
		if(event.action == Action.RIGHT_CLICK_BLOCK && b != ModBlocks.clowder_flag) {
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(x, z));
			
			if(owner != null) {
				Clowder clowder = Clowder.getClowderFromPlayer(event.entityPlayer);
				
				if(owner.zone == Zone.FACTION && clowder != owner.owner) {
					
					if(!(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.mace && ItemMace.breakOverride.contains(b)))
						event.setCanceled(true);
				}
			}
		}
	}
	
	public static final String NBTKEY = "lastClowder";
	
	/**
	 * Sends a flag popup packet to a player if he enters a new territory and a clowder alert if a raider appears.
	 * @param world
	 * @param player
	 */
	private void flagPopup(World world, EntityPlayer player) {

		Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair((int)player.posX, (int)player.posZ - 1));
		
		String name = owner.zone.toString();
		
		if(owner.zone == Zone.FACTION)
			name = owner.owner.name;
		
		String past = player.getEntityData().getString(NBTKEY);
		
		if(past.isEmpty()) {
			player.getEntityData().setString(NBTKEY, name);
			return;
		}
		
		if(!name.equals(past)) {

			
			if(owner.zone == Zone.FACTION) {
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(owner.owner), (EntityPlayerMP) player);
				
				if(player.inventory.hasItem(ModItems.mace))
					owner.owner.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "A raider has just entered your territory!"));
				
			} else {
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(name), (EntityPlayerMP) player);
			}
		}
		
		player.getEntityData().setString(NBTKEY, name);
	}
	
	/**
	 * Creates a clientside particle border around territories (heavily WIP).
	 * @param world
	 * @param player
	 */
	private void particleBorder(World world, EntityPlayer player) {

		int oX = (int)player.posX;
		int oZ = (int)player.posZ - 1;

		Ownership north = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.NORTH.offsetX * 16, oZ + ForgeDirection.NORTH.offsetZ * 16));
		Ownership south = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.SOUTH.offsetX * 16, oZ + ForgeDirection.SOUTH.offsetZ * 16));
		Ownership east = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.EAST.offsetX * 16, oZ + ForgeDirection.EAST.offsetZ * 16));
		Ownership west = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.WEST.offsetX * 16, oZ + ForgeDirection.WEST.offsetZ * 16));
		Ownership center = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(oX, (int)player.posZ));

		boolean n = isTerritoryDifferent(north, center);
		boolean s = isTerritoryDifferent(south, center);
		boolean e = isTerritoryDifferent(east, center);
		boolean w = isTerritoryDifferent(west, center);

		int x = (int)player.posX - ((int)player.posX % 16);
		int z = (int)player.posZ - ((int)player.posZ % 16);
		
		if(n) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.NORTH.offsetX * 16 + ForgeDirection.WEST.offsetX * world.rand.nextDouble() * 16 + 16;
				double spanZ = z + ForgeDirection.NORTH.offsetZ * 16 + ForgeDirection.WEST.offsetZ * world.rand.nextDouble() * 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
		if(s) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.SOUTH.offsetX * 16 - ForgeDirection.WEST.offsetX * world.rand.nextDouble() * 16;
				double spanZ = z + ForgeDirection.SOUTH.offsetZ * 16 - ForgeDirection.WEST.offsetZ * world.rand.nextDouble() * 16 - 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
		if(e) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.EAST.offsetX * 16 + ForgeDirection.NORTH.offsetX * world.rand.nextDouble() * 16;
				double spanZ = z + ForgeDirection.EAST.offsetZ * 16 + ForgeDirection.NORTH.offsetZ * world.rand.nextDouble() * 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
		if(w) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.WEST.offsetX * 16 + ForgeDirection.NORTH.offsetX * world.rand.nextDouble() * 16 + 16;
				double spanZ = z + ForgeDirection.WEST.offsetZ * 16 + ForgeDirection.NORTH.offsetZ * world.rand.nextDouble() * 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
	}
	
	/**
	 * Mk.2 of the particle border, now optimized to work server-side!
	 * @param world
	 * @param player
	 */
	private static void particleBorder2(World world, EntityPlayer player) {

		int ox = (int)player.posX - ((int)player.posX % 16);
		int oz = (int)player.posZ - ((int)player.posZ % 16);
		
		int range = 4;

		for(int x = -range; x < range; x++) {
			for(int z = -range; z < range; z++) {

				Ownership center = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(ox + x * 16, oz + z * 16));
				Ownership north = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(ox + (x + ForgeDirection.NORTH.offsetX) * 16, oz + (z + ForgeDirection.NORTH.offsetZ) * 16));
				Ownership west = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(ox + (x + ForgeDirection.WEST.offsetX) * 16, oz + (z + ForgeDirection.WEST.offsetZ) * 16));

				Ownership none = ClowderTerritory.WILDERNESS;
				boolean n = isTerritoryDifferent(north, center);
				boolean w = isTerritoryDifferent(west, center);

				int nc = ((center != none ? center.getColor() : (north != none ? north.getColor() : 0x000000)) + (north != none ? north.getColor() : (center != none ? center.getColor() : 0x000000))) / 2;
				int wc = ((center != none ? center.getColor() : (west != none ? west.getColor() : 0x000000)) + (west != none ? west.getColor() : (center != none ? center.getColor() : 0x000000))) / 2;
				
				if(n)
					PacketDispatcher.wrapper.sendTo(new ClowderBorderPacket(ox + x * 16, oz + z * 16, ox + (x - ForgeDirection.WEST.offsetX) * 16, oz + (z - ForgeDirection.WEST.offsetZ) * 16, nc), (EntityPlayerMP) player);
				if(w)
					PacketDispatcher.wrapper.sendTo(new ClowderBorderPacket(ox + x * 16, oz + z * 16, ox + (x - ForgeDirection.NORTH.offsetX) * 16, oz + (z - ForgeDirection.NORTH.offsetZ) * 16, wc), (EntityPlayerMP) player); 
			}
		}
	}
	
	/**
	 * Compares two ownership instances.
	 * @param one
	 * @param two
	 * @return
	 */
	private static boolean isTerritoryDifferent(Ownership one, Ownership two) {
		
		if(one == null && two != null)
			return true;
		
		if(one != null && two == null)
			return true;
		
		if(one != null && two != null) {
			
			if(one.zone != two.zone)
				return true;
			
			if(one.owner != two.owner)
				return true;
		}
		
		return false;
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

			flagPopup(player.worldObj, player);
			
			Clowder clowder = Clowder.getClowderFromPlayer(player);
			
			if(clowder != null && clowder.members.get(name) != null) {
				
				if(!Clowder.retreating.contains(name)) {
					
					//10 minutes
					long time = 60 * 10 * 1000;
					//updates the time on the online timer until the player is retreating
					clowder.members.put(player.getDisplayName(), System.currentTimeMillis() + time);
					
				} else {
					
					Long l = clowder.members.get(name);
					
					//retreats if the time is up
					if(l < System.currentTimeMillis()) {
						
						EntityPlayerMP mp = (EntityPlayerMP)player;
						clowder.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.INFO + "Player " + name + " has just retreated!"));
						Clowder.retreating.remove(name);
						mp.playerNetServerHandler.kickPlayerFromServer("You have just retreated!");
					}
				}
			} else {
				
				if(Clowder.retreating.contains(name)) {
					Clowder.retreating.remove(name);
				}
			}
			
			particleBorder2(player.worldObj, player);
		}
	}
	
	int delay = 10;
	/**
	 * Handles world ticks for clowders, mainly the claim decay automaton.
	 * @param event
	 */
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		
		World world = event.world;
		
		if(world.isRemote)
			return;
		
		if(delay > 0) {
			delay--;
			return;
		}
		
		/// CLOWDER TERRITORYY ADMINISTRATIVE STUFF START ///
		ClowderTerritory.persistenceAutomaton(world);
		/// CLOWDER TERRITORYY ADMINISTRATIVE STUFF END ///
	}

}
