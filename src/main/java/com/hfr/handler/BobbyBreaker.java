package com.hfr.handler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import net.minecraftforge.common.util.ForgeDirection;
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
		
		if(!world.isRemote && event.explosion.isSmoking) {
			
			List<ChunkPosition> rem = new ArrayList();
			
			double x = event.explosion.explosionX;
			double y = event.explosion.explosionY;
			double z = event.explosion.explosionZ;
			int dim = event.world.provider.dimensionId;
			float strength = event.explosion.explosionSize * 10F;
			
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
			
			//remove all vanilla explosion entries of blocks with BB values
			event.getAffectedBlocks().removeAll(rem);
			
			List<ForgeDirection> offsets = new ArrayList() {{ addAll(Arrays.asList(ForgeDirection.VALID_DIRECTIONS)); }};
			Collections.shuffle(offsets);
			offsets.add(0, ForgeDirection.UNKNOWN);
			
			//randomly offset the proposed explosion location to find space, starting with no offset (best-case scenario)
			for(int i = 0; i < offsets.size(); i++) {
				
				ForgeDirection dir = offsets.get(i);

				int ix = (int)x + dir.offsetX;
				int iy = (int)y + dir.offsetY;
				int iz = (int)z + dir.offsetZ;
				
				if(world.getBlock(ix, iy, iz) == Blocks.air) {
					BobbyExplosion explosion = new BobbyExplosion(world, event.explosion.exploder, ix + 0.5, iy + 0.5, iz + 0.5, strength);
					explosion.doExplosionA();
					explosion.doExplosionB(false);
					return;
				}
			}
			
			//if no space is found, just do it anyway
			BobbyExplosion explosion = new BobbyExplosion(world, event.explosion.exploder, x, y, z, strength);
			explosion.doExplosionA();
			explosion.doExplosionB(false);
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
