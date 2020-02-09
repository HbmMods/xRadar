package com.hfr.tileentity.machine;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityComboProxy extends TileEntityProxyBase implements IEnergyHandler, IFluidHandler {

	public boolean connectEnergy = true;
	public boolean connectFluid = true;

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		
		if(!connectEnergy)
			return false;
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).canConnectEnergy(from);
		
		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).canConnectEnergy(from);
		
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		
		if(!connectEnergy)
			return 0;
		
		TileEntity te = getTE();

		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).receiveEnergy(from, maxReceive, simulate);
		
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		
		if(!connectEnergy)
			return 0;
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).extractEnergy(from, maxExtract, simulate);
		
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		
		if(!connectEnergy)
			return 0;
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).getEnergyStored(from);
		
		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).getEnergyStored(from);
		
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		
		if(!connectEnergy)
			return 0;
		
		TileEntity te = getTE();
		
		if(te instanceof IEnergyProvider)
			return ((IEnergyProvider)te).getMaxEnergyStored(from);
		
		if(te instanceof IEnergyReceiver)
			return ((IEnergyReceiver)te).getMaxEnergyStored(from);
		
		return 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		
		if(!connectFluid)
			return 0;
		
		return ((IFluidHandler)this.getTE()).fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		
		if(!connectFluid)
			return null;
		
		return ((IFluidHandler)this.getTE()).drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		
		if(!connectFluid)
			return null;
		
		return ((IFluidHandler)this.getTE()).drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		
		if(!connectFluid)
			return false;
		
		return ((IFluidHandler)this.getTE()).canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		
		if(!connectFluid)
			return false;
		
		return ((IFluidHandler)this.getTE()).canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		
		if(!connectFluid)
			return new FluidTankInfo[0];
		
		return ((IFluidHandler)this.getTE()).getTankInfo(from);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		connectEnergy = nbt.getBoolean("conEn");
		connectFluid = nbt.getBoolean("conFl");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("conEn", connectEnergy);
		nbt.setBoolean("conFl", connectFluid);
	}

}
