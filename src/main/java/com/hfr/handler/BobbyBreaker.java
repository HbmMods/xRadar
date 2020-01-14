package com.hfr.handler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.hfr.util.FourInts;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;

public class BobbyBreaker {

    private static final Gson gson = new Gson();
	public static HashMap<String, HashMap<Integer, Integer>> resistance = new HashMap();
	public static HashMap<FourInts, Float> values = new HashMap();
	
	public static void removeEntry(int x, int y, int z, int dim) {
		
		FourInts pos = new FourInts(x, y, z, dim);
		values.remove(pos);
	}
	
	//defaults to 0, indicating no resistance
	public static int getResistanceValue(Block b, int meta) {
		
		//gets the meta-health pairs for this block
		HashMap<Integer, Integer> map = resistance.get(b.getUnlocalizedName().replace("tile.", ""));

		//if there is no resistances assigned, return 0
		if(map == null)
			return 0;
		
		//tries to fetch the health for this specific meta
		Integer res = map.get(meta);
		
		if(res == null) {
			//if there is none, tries to use universal meta
			res = map.get(-1);
		}
		
		if(res != null) {
			return res;
		}
		
		return 0;
	}
	
	//adds a new value to a position or overrides a present one
	public static void setValue(int x, int y, int z, int dim, float value) {

		FourInts pos = new FourInts(x, y, z, dim);
		values.put(pos, value);
	}
	
	//defaults to -1 indicating no health entry, since 0 implies it's dead
	public static float getValue(int x, int y, int z, int dim) {

		Float val = values.get(new FourInts(x, y, z, dim));
		
		if(val == null)
			return -1;
		
		return val;
	}
	
	public static void handleExplosionEvent(Detonate event) {
		
		World world = event.world;
		
		if(!world.isRemote) {
			
			List<ChunkPosition> rem = new ArrayList();
			
			double x = event.explosion.explosionX;
			double y = event.explosion.explosionY;
			double z = event.explosion.explosionZ;
			int dim = event.world.provider.dimensionId;
			float strength = event.explosion.explosionSize * 10F;
			
			//iterate through all affected blocks
			//for(ChunkPosition pos : event.getAffectedBlocks()) {
				//// using BobbyExplosion, this part becomes useless too ^
				
				
				//// this part gets handled by BobbyExplosion.hanle() ////
				/*Block b = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
				int meta = world.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
				
				int res = getResistanceValue(b, meta);
				
				//does the block have a resistance entry?
				if(res > 0) {
					
					float health = getValue(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, dim);
					
					//if the health value has not been set yet, it'll be assumed to be full
					if(health == -1) {
						health = res;
					}
					
					//damage is based on explosion strength / distance
					Vec3 vec = Vec3.createVectorHelper(x - (pos.chunkPosX + 0.5), y - (pos.chunkPosY + 0.5), z - (pos.chunkPosZ + 0.5));
					float damage = (float) (strength / vec.lengthVector());

					//scale from 1-10 how damaged a block is
					int dmg = (int)(Math.ceil(damage / ((float)health)) * 10);
					
					//this is where the magic happens
					health -= damage;
					
					//if the health is still above 0, it'll add a new entry and prevent the block from breaking
					if(health > 0) {
						setValue(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, dim, health);
						rem.add(pos);
						
						world.destroyBlockInWorldPartially(world.rand.nextInt(10000) + 1000, pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, dmg);
						
					//if not, the entry is deleted
					} else {
						removeEntry(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, dim);
					}
				}*/
				
				
			//}
			
			//removes all affected blocks that were protected by bobbybreaker
			
			/// INSERT /// removes blocks that would otherwise explode but which hold bobbybreaker(tm) resistance values
			Block b;
			int meta;
			int res;
			for(ChunkPosition pos : event.getAffectedBlocks()) {
				b = world.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
				meta = world.getBlockMetadata(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
				res = getResistanceValue(b, meta);
				
				if(res > 0)
					rem.add(pos);
			}
			
			//handles the actual bobbybreaker stuff
			BobbyExplosion explosion = new BobbyExplosion(world, event.explosion.exploder, x, y, z, strength);
			explosion.doExplosionA();
			explosion.doExplosionB(false);
			/// INSERT ///
			
			event.getAffectedBlocks().removeAll(rem);
		}
	}
	
	public static void handleDigEvent(BreakEvent event) {
		
		if(!event.world.isRemote) {
			
			removeEntry(event.x, event.y, event.z, event.world.provider.dimensionId);
		}
	}
	
	public static void loadConfiguration(String name) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		
		resistance.clear();
		values.clear();
		
		System.out.println("Attempting to read bobbybreaker configuration...");
		JsonObject json = gson.fromJson(new FileReader(name), JsonObject.class);
        
        for(Entry<String, JsonElement> child : json.entrySet()) {
        	
        	if(child.getValue().isJsonPrimitive()) {
        		try {

	        		String[] frags = child.getKey().split("\\|");
	        		String block = frags[0];
	        		int meta = -1;

	        		if(frags.length > 1) {
	        			meta = Integer.parseInt(frags[1]);
	        		}
	        		
	        		int health = child.getValue().getAsInt();
	        		Block b = Block.getBlockFromName(block);

	        		if(b == Blocks.air || b == null)
	        			throw new Exception("Failed to read config line, block not found!");
	        		
	        		//this is bad and bad
	        		//b.setResistance(0.0F);
	        		
	        		HashMap<Integer, Integer> map = resistance.get(b.getUnlocalizedName().replace("tile.", ""));
	        		
	        		if(map != null) {
	        			map.put(meta, health);
	        		} else {
	        			map = new HashMap();
	        			map.put(meta, health);
	        			resistance.put(b.getUnlocalizedName().replace("tile.", ""), map);
	        		}
	        		
        		} catch(Exception ex) {
        			System.out.println("Error while reading line! (Is the JSON malformed?)");
        			System.out.println(child.getKey() + " " + child.getValue().toString());
        		}
        	}
        }
		System.out.println("Config loaded without dying. Yay!");
		
		for(String key : resistance.keySet()) {
			HashMap<Integer, Integer> map = resistance.get(key);
			
			System.out.println("Entry for " + key);
			
			for(Integer k : map.keySet()) {
				System.out.println("Meta " + k + ": " + map.get(k) + " health");
			}
		}
	}

}
