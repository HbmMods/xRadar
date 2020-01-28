package com.hfr.items;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBattery extends Item implements IEnergyContainerItem {
	
	public int capacity;
	
	public ItemBattery(int cap) {
		this.capacity = cap;
		this.setMaxDamage(100);
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		
		if(simulate)
			return 0;
		
		if(container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		int stored = getEnergyStored(container);
		int receive = Math.min(Math.min(capacity - stored, capacity / 100), maxReceive);
		
		stored += receive;
		container.stackTagCompound.setInteger("charge", stored);
		
		return receive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		
		if(simulate)
			return 0;
		
		if(container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		int stored = getEnergyStored(container);
		int extract = Math.min(Math.min(stored, capacity / 100), maxExtract);
		
		stored -= extract;
		container.stackTagCompound.setInteger("charge", stored);
		
		return extract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		
		if(container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return container.stackTagCompound.getInteger("charge");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}
	
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
    
    public double getDurabilityForDisplay(ItemStack stack)
    {

		if (stack.stackTagCompound == null) {
			return 1D;
		}
		
		return 1 - (getEnergyStored(stack) * 100 / capacity) / 100D;
    }
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(getEnergyStored(stack) + "/" + capacity + "RF");
	}
	
    public boolean isRepairable()
    {
        return false;
    }

}
