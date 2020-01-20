package com.hfr.entity.projectile;

import java.util.List;

import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ParticleControlPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityPak extends Entity {

	public EntityPak(World p_i1582_1_) {
		super(p_i1582_1_);
        this.setSize(0.25F, 0.25F);
	}
	
	@Override
    public void onUpdate()
    {
        this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
        rotation();

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        
		if (movingobjectposition != null) {

			if (!this.worldObj.isRemote) {
	        	this.setLocationAndAngles(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0, 0);
	        	
	        	int rad = 25;
	        	List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - rad, posY - rad, posZ - rad, posX + rad, posY + rad, posZ + rad));
	        	
	        	for(Entity e : entities) {

        			if(e instanceof EntityPak) continue;
	        		Vec3 vector = Vec3.createVectorHelper(posX - e.posX, (posY - 1) - e.posY, posZ - e.posZ);
	        		
	        		if(vector.lengthVector() <= rad) {
	        			vector = vector.normalize();
		        		e.motionX -= vector.xCoord * 3;
		        		e.motionY -= vector.yCoord * 3;
		        		e.motionZ -= vector.zCoord * 3;
	        			e.attackEntityFrom(DamageSource.setExplosionSource(null), 100);
	        		}
	        	}
	        	
	        	//ExplosionController.registerExplosion(new ExplosionNukeRay(worldObj, (int)posX, (int)posY, (int)posZ, 100, 50));
	        	
				PacketDispatcher.wrapper.sendToAllAround(new ParticleControlPacket(posX - motionX * 2, posY - motionY * 2, posZ - motionZ * 2, 7),  new TargetPoint(this.dimension, posX, posY, posZ, 250));
				//worldObj.playSoundEffect(posX, posY, posZ, "hfr:block.railgunFire", 100.0F, 0.5F);
				worldObj.newExplosion(this, posX, posY, posZ, 10F, false, false);
	        }
			this.setDead();
			
			
			return;
		}
		
		if(worldObj.isRemote) {
			MainRegistry.proxy.howDoIUseTheZOMG(worldObj, posX, posY, posZ, 5);
		}
		
		//gravity needs the sec/tick converter squared since it's in seconds squared
		motionY -= 2.02031D * 0.05 * 0.1;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }
	
	public static EntityPak statFac(World world, EntityPlayer player) {

		EntityPak flare = new EntityPak(world);
		flare.setLocationAndAngles(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		flare.posX -= (double)(MathHelper.cos(flare.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		flare.posY -= 0.10000000149011612D;
		flare.posZ -= (double)(MathHelper.sin(flare.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		flare.setPosition(flare.posX, flare.posY, flare.posZ);
		flare.yOffset = 0.0F;
        float f = 1F;
        flare.motionX = (double)(-MathHelper.sin(flare.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(flare.rotationPitch / 180.0F * (float)Math.PI) * f);
        flare.motionZ = (double)(MathHelper.cos(flare.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(flare.rotationPitch / 180.0F * (float)Math.PI) * f);
        flare.motionY = (double)(-MathHelper.sin((flare.rotationPitch) / 180.0F * (float)Math.PI) * f);
        flare.rotation();
        
        return flare;
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

}
