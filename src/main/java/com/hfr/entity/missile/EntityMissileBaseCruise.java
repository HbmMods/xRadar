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

public abstract class EntityMissileBaseCruise extends Entity implements IChunkLoader, IEmpable {
	
    private Ticket loaderTicket;
    public float health = MainRegistry.mHealth;

	public EntityMissileBaseCruise(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.dataWatcher.updateObject(8, posX);
		this.dataWatcher.updateObject(9, posZ);
		this.dataWatcher.updateObject(10, (int)posY);
	}

	public EntityMissileBaseCruise(World world, float x, float y, float z, int a, int b) {
		super(world);
		this.ignoreFrustumCheck = true;
		/*this.posX = x;
		this.posY = y;
		this.posZ = z;*/
		this.setLocationAndAngles(x, y, z, 0, 0);
		this.dataWatcher.updateObject(8, a);
		this.dataWatcher.updateObject(9, b);
		this.dataWatcher.updateObject(10, (int)posY);
		this.motionY = 2;

        this.setSize(1.5F, 1.5F);
	}
    
    public boolean canBeCollidedWith()
    {
        return true;
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable())
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
	
	@Override
    public void onUpdate()
    {
		int targetX = this.dataWatcher.getWatchableObjectInt(8);
		int targetZ = this.dataWatcher.getWatchableObjectInt(9);
		
        /// CMTA START ///
        //cruise missile targeting AI
        //generates Y value of the desired 10 block segment
        Vec3 path = Vec3.createVectorHelper(targetX - posX, 0, targetZ - posZ);
        double absPath = path.lengthVector();
        path = path.normalize();
        path.xCoord *= 10;
        path.zCoord *= 10;
        int pX = (int)(posX + path.xCoord);
        int pZ = (int)(posZ + path.zCoord);
        int y = worldObj.getHeightValue(pX, pZ) + 25;
        
        //int y = 255;
        //while(worldObj.getBlock(pX, y, pZ) == Blocks.air && y > 0)
        //	y--;
        
        y += 25;
        
        if(!worldObj.isRemote)
        	this.dataWatcher.updateObject(10, y);
        else
        	y = this.dataWatcher.getWatchableObjectInt(10);
        
        /*if(worldObj.isRemote) {
        	System.out.println("Client: " + targetX + " " + targetZ + " " + y);
        } else {
        	System.out.println("Server: " + targetX + " " + targetZ + " " + y);
        }*/
        
        Vec3 movement = null;
        double speed = 5D;
        
        if(absPath < 1) {
	        
	        //creates the movement vector to said coords
	        movement = Vec3.createVectorHelper(0, -speed, 0);
	        
        } else if(absPath > 10) {
	        
	        //creates the movement vector to said coords
	        movement = Vec3.createVectorHelper(path.xCoord, y - posY, path.zCoord);
	        movement = movement.normalize();
	        path.xCoord *= speed;
	        path.yCoord *= speed;
	        path.zCoord *= speed;
	        
	        //TODO: optional wall avoidance system(tm)
        } else {
        	
        	y -= 25;
	        movement = Vec3.createVectorHelper(targetX - posX, y - posY, targetZ - posZ);
	        movement = movement.normalize();
	        path.xCoord *= speed;
	        path.yCoord *= speed;
	        path.zCoord *= speed;
        }

        this.motionX = movement.xCoord;
        this.motionY = movement.yCoord;
        this.motionZ = movement.zCoord;

        /// CMTA END ///
        
		this.setLocationAndAngles(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);
        
		this.rotation();

		if(this.worldObj.isRemote)
			MainRegistry.proxy.howDoIUseTheZOMG(worldObj, posX, posY, posZ, 5);
		
        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        
		if (movingobjectposition != null) {

			if (!this.worldObj.isRemote) {
	        	this.setLocationAndAngles(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0, 0);
				onImpact();
				this.setDead();
			}
			return;
		}
		
    	if(!worldObj.isRemote) {
    		loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
    	}
		
		if(!worldObj.isRemote) {
			Vec3 mov = Vec3.createVectorHelper(motionX, motionY, motionZ);
			
			if(mov.lengthVector() < 0.1)
				this.setDead();
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

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		motionX = nbt.getDouble("moX");
		motionY = nbt.getDouble("moY");
		motionZ = nbt.getDouble("moZ");
		posX = nbt.getDouble("poX");
		posY = nbt.getDouble("poY");
		posZ = nbt.getDouble("poZ");
		this.dataWatcher.updateObject(8, nbt.getInteger("tX"));
		this.dataWatcher.updateObject(9, nbt.getInteger("tZ"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("moX", motionX);
		nbt.setDouble("moY", motionY);
		nbt.setDouble("moZ", motionZ);
		nbt.setDouble("poX", posX);
		nbt.setDouble("poY", posY);
		nbt.setDouble("poZ", posZ);
		
		int targetX = this.dataWatcher.getWatchableObjectInt(8);
		int targetZ = this.dataWatcher.getWatchableObjectInt(9);
		nbt.setInteger("tX", targetX);
		nbt.setInteger("tZ", targetZ);
	}
    
    private void killMissile() {
    	worldObj.newExplosion(this, posX, posY, posZ, 5F, true, false);
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	public abstract void onImpact();

	public abstract int getMissileType();

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(8, 0);
        this.dataWatcher.addObject(9, 0);
        this.dataWatcher.addObject(10, 0);
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
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

		int targetX = this.dataWatcher.getWatchableObjectInt(8);
		int targetZ = this.dataWatcher.getWatchableObjectInt(9);

		targetX += rand.nextGaussian();
		targetZ += rand.nextGaussian();
		
		this.dataWatcher.updateObject(8, targetX);
		this.dataWatcher.updateObject(9, targetZ);
    }

	//if missile can knock out forcefields
	public abstract boolean getIsBreaching();

	//explosion code explosive to forcefield collisions
	public void onForceImpact() {
	    attackEntityFrom(DamageSource.generic, 1000);
	}

}
