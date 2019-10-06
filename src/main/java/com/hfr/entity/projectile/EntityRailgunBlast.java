package com.hfr.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.hfr.entity.IChunkLoader;
import com.hfr.entity.logic.EntityBlast;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityRailgunBlast extends Entity implements IChunkLoader {

	public EntityRailgunBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
    public void onUpdate()
    {
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
        rotation();

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        
		if (movingobjectposition != null) {

			if (!this.worldObj.isRemote) {
	        	this.setLocationAndAngles(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0, 0);

	    		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
	        	worldObj.newExplosion(scapegoat, posX, posY, posZ, 12F, false, true);
	        	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, 45, MainRegistry.railgunDamage, 12, 5, false));
			}
			this.setDead();
			return;
		}

		if (!worldObj.isRemote) {
			loadNeighboringChunks((int) (posX / 16), (int) (posZ / 16));
		}
		
		//gravity needs the sec/tick converter squared since it's in seconds squared
		motionY -= 9.81D * 0.05 * 0.05;
	}
	
	public void rotation() {
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
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }

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

}
