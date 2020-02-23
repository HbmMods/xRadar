package com.hfr.tileentity.machine;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTeleporter extends TileEntity {

	public int tX = -1;
	public int tY = -1;
	public int tZ = -1;

	@Override
    public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(tY > -1) {
				
				List<EntityPlayerMP> players = worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(xCoord + 0.25F, yCoord + 1, zCoord + 0.25F, xCoord + 0.75F, yCoord + 2, zCoord + 0.75F));
				
				for(EntityPlayerMP player : players) {

		            worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
					player.mountEntity(null);
					player.playerNetServerHandler.setPlayerLocation(tX + 0.5D, tY + 1, tZ + 0.5D, player.rotationYaw, player.rotationPitch);
		            worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
				}
			}
			
		} else {
			
			for(int i = 0; i < 3; i++) {
				double x = xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				double y = yCoord + 1 + worldObj.rand.nextDouble() * 2D;
				double z = zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				
				worldObj.spawnParticle("reddust", x, y, z, 0.4F, 0.8F, 1F);
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tX = nbt.getInteger("tX");
		tY = nbt.getInteger("tY");
		tZ = nbt.getInteger("tZ");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("tX", tX);
		nbt.setInteger("tY", tY);
		nbt.setInteger("tZ", tZ);
	}

}
