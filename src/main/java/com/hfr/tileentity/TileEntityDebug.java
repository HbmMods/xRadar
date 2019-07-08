package com.hfr.tileentity;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDebug extends TileEntity {

	@Override
	public void updateEntity() {

		if(worldObj.isRemote) {

			Random rand = worldObj.rand;
			
			for(int i = 0; i < 15; i++)
				worldObj.spawnParticle("smoke", xCoord + 0.25 + rand.nextDouble() * 0.5, yCoord + 1.25D, zCoord - 3.5D - rand.nextDouble() * 1.25D, 0.0D, 0.1D, -0.1D);

			worldObj.spawnParticle("flame", xCoord + 0.25 + rand.nextDouble() * 0.5, yCoord + 1.35D, zCoord - 3.5D - rand.nextDouble() * 1.25D, 0.0D, 0.0D, 0.0D);
			
			if(rand.nextInt(25) == 0)
				worldObj.spawnParticle("lava", xCoord + 0.25 + rand.nextDouble() * 0.5, yCoord + 1.35D, zCoord - 4.5D, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

}
