package com.hfr.tileentity.machine;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineAlloy extends TileEntityMachineBase {
	
	public int progress = 0;
	public static final int maxProgress = 600;
	public int fuel = 0;
	public static final int coalValue = 4;
	public static final int maxFuel = 64 * coalValue;

	public TileEntityMachineAlloy() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.machineAlloy";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			while(slots[2] != null && slots[2].getItem() == Items.coal && fuel <= maxFuel - coalValue) {
				this.decrStackSize(2, 1);
				fuel += coalValue;
			}
			while(slots[2] != null && slots[2].getItem() == Item.getItemFromBlock(Blocks.coal_block) && fuel <= maxFuel - coalValue * 10) {
				this.decrStackSize(2, 1);
				fuel += coalValue * 10;
			}
		}
	}
	
	public int getProgressScaled(int i) {
		
		return progress * i / maxProgress;
	}
	
	public int getFuelScaled(int i) {
		return fuel * i / maxFuel;
	}
}
