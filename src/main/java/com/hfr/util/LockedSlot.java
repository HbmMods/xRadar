package com.hfr.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LockedSlot extends Slot {

	public LockedSlot(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}
	
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

}
