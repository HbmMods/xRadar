package com.hfr.items;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBatteryInfinite extends Item implements IEnergyContainerItem {
	
	public ItemBatteryInfinite() {
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		
		return maxReceive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		
		return maxExtract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		
		return getMaxEnergyStored(container) / 100;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return 100000000;
	}

}
