package com.hfr.handler;

import java.util.HashSet;
import java.util.Iterator;

import com.hfr.clowder.ClowderEvents;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BobbyExplosion extends Explosion {
	
    private int grid = 16;
    private World worldObj;

	public BobbyExplosion(World world, Entity exploder, double x, double y, double z, float power) {
		super(world, exploder, x, y, z, power);
		this.worldObj = world;
	}
	
    public void doExplosionA()
    {
        float f = this.explosionSize;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;

        for (i = 0; i < this.grid; ++i)
        {
            for (j = 0; j < this.grid; ++j)
            {
                for (k = 0; k < this.grid; ++k)
                {
                    if (i == 0 || i == this.grid - 1 || j == 0 || j == this.grid - 1 || k == 0 || k == this.grid - 1)
                    {
                        double d0 = (double)((float)i / ((float)this.grid - 1.0F) * 2.0F - 1.0F);
                        double d1 = (double)((float)j / ((float)this.grid - 1.0F) * 2.0F - 1.0F);
                        double d2 = (double)((float)k / ((float)this.grid - 1.0F) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        d5 = this.explosionX;
                        d6 = this.explosionY;
                        d7 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
                        {
                            int j1 = MathHelper.floor_double(d5);
                            int k1 = MathHelper.floor_double(d6);
                            int l1 = MathHelper.floor_double(d7);
                            Block block = this.worldObj.getBlock(j1, k1, l1);
                            int meta = this.worldObj.getBlockMetadata(j1, k1, l1);

                            if (block.getMaterial() != Material.air)
                            {
                                float f3 = block.getExplosionResistance(this.exploder, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3F) * f2;
                            }
                            
                            int health = BobbyBreaker.getResistanceValue(block, meta);

                            if (/*f1 > 0.0F &&*/ health > 0)
                            {
                                hashset.add(new ChunkPosition(j1, k1, l1));
                                continue;
                            }

                            d5 += d0 * (double)f2;
                            d6 += d1 * (double)f2;
                            d7 += d2 * (double)f2;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);

        //this.explosionSize = f;
    }

    public void doExplosionB(boolean something) {
    	
        Iterator iterator;
        ChunkPosition chunkposition;
        int x;
        int y;
        int z;
        Block block;
		iterator = this.affectedBlockPositions.iterator();

		while (iterator.hasNext()) {
			chunkposition = (ChunkPosition) iterator.next();
			x = chunkposition.chunkPosX;
			y = chunkposition.chunkPosY;
			z = chunkposition.chunkPosZ;

			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			if(ClowderEvents.canExplode(owner, worldObj, x, y, z)) {
				block = this.worldObj.getBlock(x, y, z);
				int meta = this.worldObj.getBlockMetadata(x, y, z);
	
				boolean result = handle(worldObj, block, meta, x, y, z);
	
				if (block.getMaterial() != Material.air && !result) {
					block.onBlockExploded(this.worldObj, x, y, z, this);
				}
			}
		}
    }
    
    //returns: true if the entry should be removed, false if not
    public boolean handle(World world, Block b, int meta, int posX, int posY, int posZ) {
		
    	int dim = world.provider.dimensionId;
		int res = BobbyBreaker.getResistanceValue(b, meta);
		
		//does the block have a resistance entry?
		if(res > 0) {
			
			float health = BobbyBreaker.getValue(posX, posY, posZ, dim);
			
			//if the health value has not been set yet, it'll be assumed to be full
			if(health == -1) {
				health = res;
			}

			double x = explosionX;
			double y = explosionY;
			double z = explosionZ;
			
			if(!MainRegistry.bb_rng) {
				x = (int)x;
				y = (int)y;
				z = (int)z;
			}
			
			//damage is based on explosion strength / distance
			Vec3 vec = Vec3.createVectorHelper(this.explosionX - (posX + 0.5), this.explosionY - (posY + 0.5), this.explosionZ - (posZ + 0.5));
			float damage = (float) (explosionSize / vec.lengthVector());
			
			damage = Math.min(damage, explosionSize * 2);
			
			//this is where the magic happens
			health -= damage;

			//scale from 1-10 how damaged a block is
			int dmg = 10 - (int)(Math.ceil(health / (float)res * 10));

			if(dmg < 1)
				dmg = 1;
			if(dmg > 9)
				dmg = 9;
			
			//if the health is still above 0, it'll add a new entry and prevent the block from breaking
			if(health > 0) {
				BobbyBreaker.setValue(posX, posY, posZ, dim, health);

				int key = 1;
				key = 31 * key + posX;
				key = 31 * key + posY;
				key = 31 * key + posZ;
				
				world.destroyBlockInWorldPartially(key, posX, posY, posZ, dmg);
				
				return true;
				
			//if not, the entry is deleted
			} else {
				BobbyBreaker.removeEntry(posX, posY, posZ, dim);
			}
		}
		
		return false;
    }

}
