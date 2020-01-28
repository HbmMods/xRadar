package com.hfr.tileentity.machine;

import com.hfr.main.MainRegistry;
import com.hfr.util.Location;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWaterWheel extends TileEntity implements IEnergyProvider {
	
	public EnergyStorage storage = new EnergyStorage(MainRegistry.waterwheelProduction * 10, MainRegistry.waterwheelProduction, MainRegistry.waterwheelProduction);
	
	@SideOnly(Side.CLIENT)
	public float rotation;

    public void updateEntity() {
    	
    	if(!worldObj.isRemote) {
    		
    		if(operational()) {
    			
    			storage.receiveEnergy(MainRegistry.waterwheelProduction, false);
    			
    			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
    				
    				if(!canConnectEnergy(dir))
    					continue;
    			
    				Location loc = new Location(worldObj, xCoord, yCoord, zCoord).add(dir);
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
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getRotation(ForgeDirection.UP);
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				
				if(worldObj.getBlock(xCoord + dir.offsetX * j, yCoord + i, zCoord + dir.offsetZ * j).isNormalCube())
					return false;
			}
		}

		if(hasWater(xCoord, yCoord - 1, zCoord)) return true;
		if(hasWater(xCoord, yCoord - 2, zCoord)) return true;
		
		if(hasWater(xCoord + dir.offsetX, yCoord - 1, zCoord + dir.offsetZ)) return true;
		if(hasWater(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ)) return true;
		if(hasWater(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2)) return true;
		if(hasWater(xCoord + dir.offsetX, yCoord + 1, zCoord + dir.offsetZ)) return true;
		
		/*dir = dir.getOpposite();
		
		if(hasWater(xCoord + dir.offsetX, yCoord - 1, zCoord + dir.offsetZ)) return true;
		if(hasWater(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ)) return true;
		if(hasWater(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2)) return true;
		if(hasWater(xCoord + dir.offsetX, yCoord + 1, zCoord + dir.offsetZ)) return true;*/
		
		return false;
	}
	
	public boolean hasWater(int x, int y, int z) {
		
		if((worldObj.getBlock(x, y, z) == Blocks.water || worldObj.getBlock(x, y, z) == Blocks.flowing_water) && worldObj.getBlockMetadata(x, y, z) > 0)
			return true;
		
		return false;
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
