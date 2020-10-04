package com.hfr.tileentity.machine;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineCrusher extends TileEntityMachineBase {

	public TileEntityMachineCrusher() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.machineCrusher";
	}

	@Override
	public void updateEntity() {
	}
}
