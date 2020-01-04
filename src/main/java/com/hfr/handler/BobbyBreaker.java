package com.hfr.handler;

import java.util.HashMap;

import com.hfr.util.FourInts;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;

public class BobbyBreaker {
	
	public static HashMap<String, Integer> resistance = new HashMap();
	public static HashMap<FourInts, Float> values = new HashMap();
	
	public static void removeEntry(int x, int y, int z, int dim) {
		
		FourInts pos = new FourInts(x, y, z, dim);
		values.remove(pos);
	}
	
	public static void getResistanceValue(Block b) {
		
		int res = resistance.get(b.getUnlocalizedName());
	}
	
	public static void setValue(int x, int y, int z, int dim, float value) {

		FourInts pos = new FourInts(x, y, z, dim);
		values.put(pos, value);
	}
	
	public static void handleExplosionEvent(Detonate event) {
		
		World world = event.world;
		double x = event.explosion.explosionX;
		double y = event.explosion.explosionY;
		double z = event.explosion.explosionZ;
		float strength = event.explosion.explosionSize;
		
		for(ChunkPosition pos : event.getAffectedBlocks()) {
			
			Block b = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
			
			Vec3 vec = Vec3.createVectorHelper(x - (pos.chunkPosX + 0.5), y - (pos.chunkPosY + 0.5), z - (pos.chunkPosZ + 0.5));
			
			float damage = (float) (strength / vec.lengthVector());
			
			if(damage > 2)
				System.out.println(damage);
		}
	}

}
