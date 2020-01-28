package com.hfr.tileentity.machine;

import com.hfr.blocks.BlockSpeedy;
import com.hfr.main.MainRegistry;
import com.hfr.util.Location;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineWindmill extends TileEntity implements IEnergyProvider {
	
	public EnergyStorage storage = new EnergyStorage(MainRegistry.windmillProduction * 10, MainRegistry.windmillProduction, MainRegistry.windmillProduction);
	
	@SideOnly(Side.CLIENT)
	public float rotation;

    public void updateEntity() {
    	
    	if(!worldObj.isRemote) {
    		
    		if(operational()) {
    			storage.receiveEnergy(MainRegistry.windmillProduction, false);
    			
    			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
    				
    				if(!canConnectEnergy(dir))
    					continue;
    			
    				Location loc = new Location(worldObj, xCoord, yCoord, zCoord).add(dir).add(dir);
    				TileEntity entity = loc.getTileEntity();
    			
    				if (entity != null && entity instanceof IEnergyReceiver) {
    				
    					IEnergyReceiver receiver = (IEnergyReceiver) entity;
    					
    					int maxExtract = storage.getMaxExtract();
    					int maxAvailable = storage.extractEnergy(maxExtract, true);
    					int energyTransferred = receiver.receiveEnergy(dir.getOpposite(), maxAvailable, false);
    					storage.extractEnergy(energyTransferred, false);
    				}
    			}
    		}
    	}
    }
	
	public boolean operational() {

		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 32, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 32, zCoord + j))
					return false;

		for(int x = -1; x <= 1; x++)
			for(int z = -1; z <= 1; z++)
				if(!(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) instanceof BlockSpeedy))
					return false;
		
		return true;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.DOWN && from != ForgeDirection.UNKNOWN;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		
		if(canConnectEnergy(from))
			return storage.getEnergyStored();
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		
		if(canConnectEnergy(from))
			return storage.getMaxEnergyStored();
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		storage.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		storage.writeToNBT(nbt);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
