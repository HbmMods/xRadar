package com.hfr.pon4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hfr.util.FourInts;

import net.minecraft.world.World;

public class ExplosionController {

	public static final int memCap = 10000;
	public static final int collectCap = 1000;
	public static final int convertCap = 500;
	public static final int processCap = 1000;
	
	public static List<ExplosionNukeRay> explosions = Collections.synchronizedList(new ArrayList());
	public static Set<FourInts> affectedBlocks = Collections.synchronizedSet(new HashSet());
	
	public static Thread demon = null;
	
	public static Thread demonTemplate = new Thread() {
		
		@Override
		public void run() {
			
			this.setPriority(3);
			this.setName("PON4_EXPLOSION_THREAD");
			
			System.out.println("PON4 THREAD - STARTUP");

			while(explosions.size() > 0) {
				collectTips();
				processTips();
			}
			
			System.out.println("PON4 THREAD - SHUTTING DOWN");
		}
		
	};
	
	public static void start() {
		
		System.out.println("PON4 THREAD - INVOKE ATTEMPT TAKEN");
		
		demon = new Thread(demonTemplate);
		demon.start();
	}
	
	public static void collectTips() {

		for(ExplosionNukeRay explosion : explosions) {

			if(!explosion.isAusf3Complete) {
				explosion.collectTipMk4_5(collectCap);
			}
		}
	}
	
	public static void processTips() {
		
		List<ExplosionNukeRay> del = new ArrayList();

		for(ExplosionNukeRay explosion : explosions) {

			if(explosion.isAusf3Complete && affectedBlocks.size() < memCap) {
				
				affectedBlocks.addAll(explosion.processTipCNB(convertCap));
				
				if(explosion.getStoredSize() == 0) {
					del.add(explosion);
				}
			}
		}
		
		explosions.removeAll(del);
	}
	
	public static void registerExplosion(ExplosionNukeRay explosion) {

		explosions.add(explosion);
		System.out.println("PON4 THREAD - EXPLOSION REGISTERED");
		
		if(demon == null || !demon.isAlive())
			start();
	}
	
	public static void automaton(World world) {
		int cap = 1000;
		int count = 0;
		
		if(affectedBlocks.size() == 0)
			return;
		
		synchronized(affectedBlocks) {
			
			Iterator<FourInts> iterator = affectedBlocks.iterator();
			
			while(iterator.hasNext() && count < cap) {
				
				FourInts entry = iterator.next();
				
				if(entry.w == world.provider.dimensionId) {
					world.setBlockToAir(entry.x, entry.y, entry.z);
					iterator.remove();
					count++;
				}
			}
		}
	}

}
