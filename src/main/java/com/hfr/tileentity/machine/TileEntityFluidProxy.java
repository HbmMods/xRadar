package com.hfr.tileentity.machine;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityFluidProxy extends TileEntityProxyBase implements IFluidHandler {

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return ((IFluidHandler)this.getTE()).fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return ((IFluidHandler)this.getTE()).drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return ((IFluidHandler)this.getTE()).drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return ((IFluidHandler)this.getTE()).canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return ((IFluidHandler)this.getTE()).canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return ((IFluidHandler)this.getTE()).getTankInfo(from);
	}
	
}
