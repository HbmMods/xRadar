package com.hfr.entity.projectile;

import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderBorderPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFlare extends Entity {

	public EntityFlare(World p_i1582_1_) {
		super(p_i1582_1_);
        this.setSize(0.25F, 0.25F);
	}
	
	@Override
    public void onUpdate()
    {
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        
		if (movingobjectposition != null) {

			if (!this.worldObj.isRemote) {
	        	this.setLocationAndAngles(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 0, 0);

	        	for(int i = 0; i < 10; i++) {
	        		PacketDispatcher.wrapper.sendToAllAround(new ClowderBorderPacket((int)posX, (int)posZ + 16, (int)posX, (int)posZ - 16, 0xFF0000), new TargetPoint(dimension, posX, posY, posZ, 100));
	        		PacketDispatcher.wrapper.sendToAllAround(new ClowderBorderPacket((int)posX - 16, (int)posZ, (int)posX + 16, (int)posZ, 0xFF0000), new TargetPoint(dimension, posX, posY, posZ, 100));
	        	}
	        	
	        	for(int i = 0; i < 5; i++) {
		        	EntityShell shell = new EntityShell(worldObj);
		        	shell.posX = posX + rand.nextGaussian() * (i == 0 ? 0 : 10);
		        	shell.posZ = posZ + rand.nextGaussian() * (i == 0 ? 0 : 10);
		        	shell.posY = 1000;
		        	shell.motionY = -(rand.nextDouble() * 0.5 + 1);
		        	worldObj.spawnEntityInWorld(shell);
	        	}
	        }
			this.setDead();
			return;
		}
		
		if(worldObj.isRemote) {
			MainRegistry.proxy.howDoIUseTheZOMG(worldObj, posX, posY, posZ, 6);
		}
		
		//gravity needs the sec/tick converter squared since it's in seconds squared
		motionY -= 2.02031D * 0.05 * 0.05;
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
	
	public static EntityFlare statFac(World world, EntityPlayer player) {

		EntityFlare flare = new EntityFlare(world);
		flare.setLocationAndAngles(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ, player.rotationYaw, player.rotationPitch);
		flare.posX -= (double)(MathHelper.cos(flare.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		flare.posY -= 0.10000000149011612D;
		flare.posZ -= (double)(MathHelper.sin(flare.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		flare.setPosition(flare.posX, flare.posY, flare.posZ);
		flare.yOffset = 0.0F;
        float f = 0.6F;
        flare.motionX = (double)(-MathHelper.sin(flare.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(flare.rotationPitch / 180.0F * (float)Math.PI) * f);
        flare.motionZ = (double)(MathHelper.cos(flare.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(flare.rotationPitch / 180.0F * (float)Math.PI) * f);
        flare.motionY = (double)(-MathHelper.sin((flare.rotationPitch) / 180.0F * (float)Math.PI) * f);
        
        return flare;
	}

}
