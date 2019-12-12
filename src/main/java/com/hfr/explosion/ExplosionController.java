package com.hfr.explosion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.world.World;

public class ExplosionController {
	
	public static List<ExplosionNukeRay> explosions = Collections.synchronizedList(new ArrayList());
	public static Set<int[]> affectedBlocks = Collections.synchronizedSet(new HashSet());
	
	public static Thread demon = null;
	
	public static Thread demonTemplate = new Thread() {
		
		@Override
		public void run() {
			
			System.out.println("DEMON THREAD - STARTUP");

			while(explosions.size() > 0) {
				collectTips();
				processTips();
			}
			
			System.out.println("DEMON THREAD - SHUTTING DOWN");
		}
		
	};
	
	public static void start() {
		
		System.out.println("DEMON THREAD - INVOKE ATTEMPT TAKEN");
		
		demon = new Thread(demonTemplate);
		demon.start();
	}
	
	public static void collectTips() {

		for(ExplosionNukeRay explosion : explosions) {

			if(!explosion.isAusf3Complete) {
				explosion.collectTipMk4_5(1000);
			}
		}
	}
	
	public static void processTips() {
		
		List<ExplosionNukeRay> del = new ArrayList();

		for(ExplosionNukeRay explosion : explosions) {

			if(explosion.isAusf3Complete) {
				
				affectedBlocks.addAll(explosion.processTipCNB(500));
				
				if(explosion.getStoredSize() == 0) {
					del.add(explosion);
				}
			}
		}
		
		explosions.removeAll(del);
	}
	
	public static void registerExplosion(ExplosionNukeRay explosion) {

		explosions.add(explosion);
		System.out.println("DEMON THREAD - EXPLOSION REGISTERED");
		
		if(demon == null || !demon.isAlive())
			start();
	}
	
	public static void automaton(World world) {
		int cap = 500;
		int count = 0;

		Iterator<int[]> iterator = affectedBlocks.iterator();

		while(iterator.hasNext() && count < cap) {
			
			int[] entry = iterator.next();
			
			if(entry[3] == world.provider.dimensionId) {
				world.setBlockToAir(entry[0], entry[1], entry[2]);
				iterator.remove();
				count++;
			}
		}
	}

}
