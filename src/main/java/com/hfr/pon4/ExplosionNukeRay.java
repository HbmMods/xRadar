package com.hfr.pon4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hfr.util.FourInts;

import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ExplosionNukeRay {
	
	List<FloatTriplet> affectedBlocks = new ArrayList();
	int posX;
	int posY;
	int posZ;
	Random rand = new Random();
	CachedWorld world;
	int strength;
	int processed;
	int length;
	int startY;
	int startCir;
	public boolean isAusf3Complete = false;
	
	public ExplosionNukeRay(World world, int x, int y, int z, int strength, int length) {
		this.world = WorldController.getWorld(world.provider.dimensionId);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.strength = strength;
		this.length = length;
		this.startY = 0;
		this.startCir = 0;
	}
	
	public List<FourInts> processTipCNB(int count) {
		
		int processedBlocks = 0;
		int braker = 0;
		
		List<FourInts> poss = new ArrayList();
		
		for(int l = 0; l < Integer.MAX_VALUE; l++) {
			
			//System.out.println("lööp");

			if(processedBlocks >= count) {
				return poss;
			}
			
			if(braker >= count * 50) {
				return poss;
			}

			
			//System.out.println("aff -1");
            if(l > affectedBlocks.size() - 1) {
            	break;
            }
    		
    		//System.out.println("post");
            
            if(affectedBlocks.isEmpty()) {
            	return poss;
            }
            
            int in = affectedBlocks.size() - 1;
            
			float x = affectedBlocks.get(in).xCoord;
			float y = affectedBlocks.get(in).yCoord;
			float z = affectedBlocks.get(in).zCoord;
			
			poss.add(new FourInts((int)x, (int)y, (int)z, world.dimension));
			
			Vec3 vec = Vec3.createVectorHelper(x - this.posX, y - this.posY, z - this.posZ);
			double pX = vec.xCoord / vec.lengthVector();
			double pY = vec.yCoord / vec.lengthVector();
			double pZ = vec.zCoord / vec.lengthVector();
			
			for(int i = 0; i < vec.lengthVector(); i ++) {
				int x0 = (int)(posX + pX * i);
				int y0 = (int)(posY + pY * i);
				int z0 = (int)(posZ + pZ * i);
				
				if(!world.isAirBlock(x0, y0, z0)) {
					poss.add(new FourInts((int)x0, (int)y0, (int)z0, world.dimension));
					processedBlocks++;
				}
				
				braker++;
			}
			
			affectedBlocks.remove(in);
		}
		
		processed += count;
		
		return poss;
	}
	
	//currently used by mk4
	public void collectTipMk4_5(int count) {
		
		int amountProcessed = 0;
		
		double bow = Math.PI * this.strength;
		double bowCount = Math.ceil(bow);
		
		//Axial
		//StartY starts at this.length
		for(int v = startY; v <= bowCount; v++) {
			
			float part = (float) (Math.PI/bow);
			float rot = part * -v;
			
			Vec3 heightVec = Vec3.createVectorHelper(0, -strength, 0);
			heightVec.rotateAroundZ(rot);
			
			double y = heightVec.yCoord;
			
			double sectionRad = Math.sqrt(Math.pow(strength, 2) - Math.pow(y, 2));
			double circumference = 2 * Math.PI * sectionRad;
			
			//if(y < 2 && y > -2)
			//	circumference *= 1.25D;
			
			//circumference = Math.ceil(circumference);
			
			//Radial
			//StartCir starts at circumference
			for(int r = startCir; r < circumference; r ++) {
				
				Vec3 vec = Vec3.createVectorHelper(sectionRad, y, 0);
				vec = vec.normalize();
				/*if(y > 0)
					vec.rotateAroundZ((float) (y / sectionRad) * 0.15F);*/
				/*if(y < 0)
					vec.rotateAroundZ((float) (y / sectionRad) * 0.15F);*/
				vec.rotateAroundY((float) (360 / circumference * r));
				
				int length = (int)Math.ceil(strength);
				
				float res = strength;
				
				FloatTriplet lastPos = null;
				
				for(int i = 0; i < length; i ++) {
					
					if(i > this.length)
						break;
					
					float x0 = (float) (posX + (vec.xCoord * i));
					float y0 = (float) (posY + (vec.yCoord * i));
					float z0 = (float) (posZ + (vec.zCoord * i));
					
					double fac = 100 - ((double) i) / ((double) length) * 100;
					fac *= 0.07D;
					
					if(!world.getBlock((int)x0, (int)y0, (int)z0).getMaterial().isLiquid())
						res -= Math.pow(world.getBlock((int)x0, (int)y0, (int)z0).getExplosionResistance(null), 7.5D - fac);
					else
						res -= Math.pow(Blocks.air.getExplosionResistance(null), 7.5D - fac);
	
					if(res > 0 && world.getBlock((int)x0, (int)y0, (int)z0) != Blocks.air) {
						lastPos = new FloatTriplet(x0, y0, z0);
					}
					
					if(res <= 0 || i + 1 >= this.length) {
						if(affectedBlocks.size() < Integer.MAX_VALUE - 100 && lastPos != null) {
							affectedBlocks.add(new FloatTriplet(lastPos.xCoord, lastPos.yCoord, lastPos.zCoord));
						}
						break;
					}
				}
				
				amountProcessed++;
				
				if(amountProcessed >= count) {
					startY = v;
					startCir = startCir + 1;
					return;
				}
			}
		}
		
		isAusf3Complete = true;
	}
	
	public void deleteStorage() {
		this.affectedBlocks.clear();
	}
	
	public int getStoredSize() {
		return this.affectedBlocks.size();
	}
	
	public int getProgress() {
		return this.processed;
	}
	
	public class FloatTriplet {
		public float xCoord;
		public float yCoord;
		public float zCoord;
		
		public FloatTriplet(float x, float y, float z) {
			xCoord = x;
			yCoord = y;
			zCoord = z;
		}
	}

}
