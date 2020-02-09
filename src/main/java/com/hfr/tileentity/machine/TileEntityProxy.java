package com.hfr.tileentity.machine;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityProxy extends TileEntityProxyBase implements IEnergyHandler {
	
    public boolean canUpdate()
    {
        return false;
    }

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).canConnectEnergy(from);
		
		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).canConnectEnergy(from);
		
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		
		TileEntity te = getTE();

		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).receiveEnergy(from, maxReceive, simulate);
		
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).extractEnergy(from, maxExtract, simulate);
		
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).getEnergyStored(from);
		
		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).getEnergyStored(from);
		
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).getMaxEnergyStored(from);
		
		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).getMaxEnergyStored(from);
		
		return 0;
	}

}
