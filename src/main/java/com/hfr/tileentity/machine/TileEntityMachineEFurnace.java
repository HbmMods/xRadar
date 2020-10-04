package com.hfr.tileentity.machine;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineEFurnace extends TileEntityMachineBase {

	public TileEntityMachineEFurnace() {
		super(7);
	}

	@Override
	public String getName() {
		return "container.machineEFurnace";
	}

	@Override
	public void updateEntity() {
	}
}
