package com.hfr.tileentity.machine;

public class TileEntityBox extends TileEntityMachineBase {

	public TileEntityBox() {
		super(15);
	}

	@Override
	public String getName() {
		return "container.box";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		}
		
	}

}
