package com.hfr.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hfr.entity.IChunkLoader;
import com.hfr.entity.IEmpable;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class EntityMissileAntiBallistic extends Entity implements IChunkLoader, IEmpable {
	
	int activationTimer;
	boolean blind;

	public EntityMissileAntiBallistic(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
    public void onUpdate() {

    	if(!worldObj.isRemote) {
    		loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
    	}
		
		if(activationTimer < MainRegistry.abDelay) {
			activationTimer++;
			
			motionY = 1.5D;

			this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
	        this.rotation();
			
		} else {
			
			if(activationTimer == MainRegistry.abDelay) {
				//ExplosionLarge.spawnParticlesRadial(worldObj, posX, posY, posZ, 15);
				activationTimer = MainRegistry.abDelay + 10;
			}

			for(int i = 0; i < 5; i++) {

				targetMissile();

				this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		        this.rotation();

				List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(posX - 5, posY - 5, posZ - 5, posX + 5, posY + 5, posZ + 5));

				for(Entity e : list) {
					if(e instanceof EntityMissileBaseSimple) {
						//ExplosionLarge.explode(worldObj, posX, posY, posZ, 15F, true, false, true);
						worldObj.newExplosion(this, posX, posY, posZ, 15F, true, false);
						this.setDead();
						return;
					}
				}
			}
		}

        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air && 
    			this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.water && 
    			this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.flowing_water) {
    	
			if(!this.worldObj.isRemote)
			{
				//ExplosionLarge.explode(worldObj, posX, posY, posZ, 10F, true, true, true);
				worldObj.newExplosion(this, posX, posY, posZ, 10F, true, false);
			}
			this.setDead();
			return;
    	}
		
		if(!worldObj.isRemote) {
			Vec3 movement = Vec3.createVectorHelper(motionX, motionY, motionZ);
			
			if(movement.lengthVector() < 0.1 || this.ticksExisted > 30 * 20)
				this.setDead();
		}
		
		if(this.worldObj.isRemote) {
			MainRegistry.proxy.howDoIUseTheZOMG(worldObj, posX, posY, posZ, 0);
		}

    }
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	private void targetMissile() {
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(posX - MainRegistry.abRange, 0, posZ - MainRegistry.abRange, posX + MainRegistry.abRange, 6000, posZ + MainRegistry.abRange));
		
		Entity target = null;
		double closest = MainRegistry.abRange * Math.sqrt(2);
		
		for(Entity e : list) {
			if(e instanceof EntityMissileBaseSimple && ((EntityMissileBaseSimple)e).mode == 2) {
				double dis = Math.sqrt(Math.pow(e.posX - posX, 2) + Math.pow(e.posY - posY, 2) + Math.pow(e.posZ - posZ, 2));
				
				if(dis < closest) {
					closest = dis;
					target = e;
				}
			}
		}
		
		if(target != null) {
			
			Vec3 vec = Vec3.createVectorHelper(target.posX - posX, target.posY - posY, target.posZ - posZ);

			vec = vec.normalize();
			
			this.motionX = vec.xCoord * MainRegistry.abSpeed * 10;
			this.motionY = vec.yCoord * MainRegistry.abSpeed * 10;
			this.motionZ = vec.zCoord * MainRegistry.abSpeed * 10;
		} else {
			
			if(posY > 5000 && !worldObj.isRemote)
				this.setDead();
		}
	}

	@Override
	protected void entityInit() {

        //mode, unused but keeps it from crashing due to sloppy renderer
        this.dataWatcher.addObject(9, Integer.valueOf(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		nbt.setBoolean("blind", blind);
		nbt.setInteger("age", ticksExisted);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		this.blind = nbt.getBoolean("blind");
		this.ticksExisted = nbt.getInteger("age");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

    private Ticket loaderTicket;
    
	public void init(Ticket ticket) {
		if(!worldObj.isRemote) {
			
            if(ticket != null) {
            	
                if(loaderTicket == null) {
                	
                	loaderTicket = ticket;
                	loaderTicket.bindEntity(this);
                	loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
            }
        }
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

    public void loadNeighboringChunks(int newChunkX, int newChunkZ)
    {
        if(!worldObj.isRemote && loaderTicket != null)
        {
            for(ChunkCoordIntPair chunk : loadedChunks)
            {
                ForgeChunkManager.unforceChunk(loaderTicket, chunk);
            }

            loadedChunks.clear();
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ - 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ - 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ + 1));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ));
            loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ - 1));

            for(ChunkCoordIntPair chunk : loadedChunks)
            {
                ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
    }

	@Override
	public void pulse() {
		blind = true;
	}

}
