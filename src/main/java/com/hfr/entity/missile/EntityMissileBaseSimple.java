package com.hfr.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hfr.entity.IChunkLoader;
import com.hfr.entity.IEmpable;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public abstract class EntityMissileBaseSimple extends Entity implements IChunkLoader, IEmpable {
	
	int startX;
	int startZ;
	int startY;
	int targetX;
	int targetZ;
    private Ticket loaderTicket;
    public float health = MainRegistry.mHealth;
    
    //0: ascent
    //1: pause
    //2: descent
    public int mode = 0;
    //when the timer runs out, it sets the mode from 1 to 2
    int timer;

	public EntityMissileBaseSimple(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startY = (int) posY;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
	}
	
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable() || mode == 1)
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isRemote)
            {
            	health -= p_70097_2_;
            	
                if (this.health <= 0)
                {
                    this.setDead();
                    this.killMissile();
                }
            }

            return true;
        }
    }
    
    private void killMissile() {
        //ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
    	worldObj.newExplosion(this, posX, posY, posZ, 5F, true, false);
    }

	public EntityMissileBaseSimple(World world, float x, float y, float z, int a, int b) {
		super(world);
		this.ignoreFrustumCheck = true;
		/*this.posX = x;
		this.posY = y;
		this.posZ = z;*/
		this.setLocationAndAngles(x, y, z, 0, 0);
		startX = (int) x;
		startY = (int) y;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 0.1;

        this.setSize(1.5F, 1.5F);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
        this.dataWatcher.addObject(8, Float.valueOf(this.health));
        //mode
        this.dataWatcher.addObject(9, Integer.valueOf(0));
        //timer
        this.dataWatcher.addObject(10, Integer.valueOf(0));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		/*motionX = nbt.getDouble("moX");
		motionY = nbt.getDouble("moY");
		motionZ = nbt.getDouble("moZ");
		posX = nbt.getDouble("poX");
		posY = nbt.getDouble("poY");
		posZ = nbt.getDouble("poZ");*/
		targetX = nbt.getInteger("tX");
		targetZ = nbt.getInteger("tZ");
		startX = nbt.getInteger("sX");
		startY = nbt.getInteger("sY");
		startZ = nbt.getInteger("sZ");
		mode = nbt.getInteger("mode");
		timer = nbt.getInteger("timer");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		/*nbt.setDouble("moX", motionX);
		nbt.setDouble("moY", motionY);
		nbt.setDouble("moZ", motionZ);
		nbt.setDouble("poX", posX);
		nbt.setDouble("poY", posY);
		nbt.setDouble("poZ", posZ);*/
		nbt.setInteger("tX", targetX);
		nbt.setInteger("tZ", targetZ);
		nbt.setInteger("sX", startX);
		nbt.setInteger("sY", startY);
		nbt.setInteger("sZ", startZ);
		nbt.setInteger("mode", mode);
		nbt.setInteger("timer", timer);
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
	
	@Override
    public void onUpdate()
    {
        this.dataWatcher.updateObject(8, Float.valueOf(this.health));
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        //HANLDE MODE-SPECIFIC ACTIONS
        if(mode == 0) {
        	if(motionY < 7.5)
        		motionY += 0.025;
        	
        	if(posY > MainRegistry.mDespawn) {
        		timer = getDelay();
        		mode = 1;
        		
        		Vec3 move = getS2T();
        		move = move.normalize();
        		double offset = getOffset();
        		move.xCoord *= offset;
        		move.zCoord *= offset;
        		
        		this.setLocationAndAngles(targetX - move.xCoord, MainRegistry.mSpawn, targetZ - move.zCoord, 0, 0);
        		
        		Vec3 aim = Vec3.createVectorHelper(posX - targetX, posY - startY, posZ - targetZ);
        		aim = aim.normalize();
        		motionX = aim.xCoord * -5;
        		motionY = aim.yCoord * -5;
        		motionZ = aim.zCoord * -5;
        	}
        	
        } else if(mode == 1) {
        	timer--;
        	
        	if(dataWatcher.getWatchableObjectInt(10) <= 0)
        		mode = 2;
        	
        } else {
        	
        	//to pull deflected missiles back to earth
        	if(Vec3.createVectorHelper(motionX, motionY, motionZ).lengthVector() < 5)
        		motionY -= 0.1;
        }
        
        if(!worldObj.isRemote) {
        	this.dataWatcher.updateObject(9, mode);
        	this.dataWatcher.updateObject(10, timer);
        }
        
        if(worldObj.isRemote) {
        	mode = dataWatcher.getWatchableObjectInt(9);
        	timer = dataWatcher.getWatchableObjectInt(10);
        }
        
        //END
		
        if(mode != 1)
        	this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
        
		this.rotation();

		if (this.worldObj.isRemote && this.dataWatcher.getWatchableObjectInt(9) != 1) {
			MainRegistry.proxy.howDoIUseTheZOMG(worldObj, posX, posY, posZ, 0);
		}

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        
		if (movingobjectposition != null) {

			if (!this.worldObj.isRemote) {
	        	this.setLocationAndAngles(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0, 0);
				onImpact();
			}
			this.setDead();
			return;
		}

		if (!worldObj.isRemote) {
			loadNeighboringChunks((int) (posX / 16), (int) (posZ / 16));
		}

		if (!worldObj.isRemote) {
			Vec3 movement = Vec3.createVectorHelper(motionX, motionY, motionZ);

			if (movement.lengthVector() < 0.1)
				this.setDead();
		}
	}
	
	private Vec3 getS2T() {
		return Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
	}
	
	private int getDelay() {
		
		Vec3 vec = getS2T();
		
		double length = vec.lengthVector();
		
		//4 ticks per 10 blocks
		//40 ticks per 100 blocks
		//2 seconds per 100 blocks
		int dlay = (int)(length / 10D * 4D);
		
		return dlay;
	}
	
	private double getOffset() {
		
		Vec3 vec = getS2T();
		
		double length = vec.lengthVector();
		
		//8m per 50m traveled
		//800m per 5000m traveled
		double offset = length / 50D * 8D;
		if(offset > 800)
			offset = 800;
		
		return offset;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

    //explosion code for when missiles impact
	public abstract void onImpact();

	//what tier should be displayed on radars
	public abstract int getMissileType();

	//if missile can knock out forcefields
	public abstract boolean getIsBreaching();

	//explosion code explosive to forcefield collisions
	public void onForceImpact() {
	    attackEntityFrom(DamageSource.generic, 1000);
	}
	
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
        	
        	List<ChunkCoordIntPair> oldChunks = new ArrayList<ChunkCoordIntPair>(loadedChunks);

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
            
            //only unforce chunks that won't be forced again right away
            oldChunks.removeAll(loadedChunks);
        	
            for(ChunkCoordIntPair chunk : oldChunks)
            {
                ForgeChunkManager.unforceChunk(loaderTicket, chunk);
            }

            //only force chunks that have not yet been forced
            for(ChunkCoordIntPair chunk : loadedChunks)
            {
            	if(!loaderTicket.getChunkList().contains(chunk))
            		ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
    }
    
    @Override
    public void setDead() {
    	super.setDead();

        if(!worldObj.isRemote && loaderTicket != null) {
        	
        	//unforce all chunks for this entity when it dies
            for(ChunkCoordIntPair chunk : loadedChunks)
            {
                ForgeChunkManager.unforceChunk(loaderTicket, chunk);
            }
        }
    }
    
    @Override
    public void pulse() {

    	if(mode == 0) {
    		targetX += rand.nextGaussian();
    		targetZ += rand.nextGaussian();
    	}
    	
    	if(mode == 2) {
        	motionX += rand.nextGaussian() * 0.01;
        	motionZ += rand.nextGaussian() * 0.01;
    	}
    }
}
