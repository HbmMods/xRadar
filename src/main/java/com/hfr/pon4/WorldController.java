package com.hfr.pon4;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;

public class WorldController {

	private static ConcurrentHashMap<Integer, CachedWorld> cache = new ConcurrentHashMap<Integer, CachedWorld>();
	public static Lock cacheLock = new ReentrantLock();

	public static CachedWorld getWorld(int dimension) {
		
		return cache.get(dimension);
	}

	@SubscribeEvent
	public void WorldUnloadEvent(Unload event) {
		
		if(event.world.isRemote)
			return;
		
		if(event.world.provider.dimensionId == 0)
			ExplosionController.affectedBlocks.clear();
		
		cache.remove(event.world.provider.dimensionId);
	}

	@SubscribeEvent
	public void WorldLoadEvent(Load event) {
		
		if(event.world.isRemote)
			return;
		
		cache.put(event.world.provider.dimensionId, new CachedWorld(event.world));
	}

	@SubscribeEvent
	public void ChunkLoadEvent(net.minecraftforge.event.world.ChunkEvent.Load event) {
		
		if(event.world.isRemote)
			return;
		
		CachedWorld world = cache.get(event.world.provider.dimensionId);
		
		if(world == null) {
			world = new CachedWorld(event.world);
			cache.put(event.world.provider.dimensionId, world);
		}
		
		world.addChunk(event.getChunk());
	}

	@SubscribeEvent
	public void ChunkUnLoadEvent(net.minecraftforge.event.world.ChunkEvent.Unload event) {
		
		if(event.world.isRemote)
			return;
		
		CachedWorld world = cache.get(event.world.provider.dimensionId);
		
		if(world != null)
			world.removeChunk(event.getChunk());
	}
	
	//TODO: add manual boolean lock to all operations involving the world cache
	//buffer chunks to (un)load in list and add them to worlds in the automaton instead of outright
	//use lock boolean to check for thread safety

}
