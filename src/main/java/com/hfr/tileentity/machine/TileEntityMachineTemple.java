package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import net.minecraft.item.ItemStack;

public class TileEntityMachineTemple extends TileEntityMachineBase {

	public TileEntityMachineTemple() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.temple";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			if(worldObj.rand.nextInt(MainRegistry.temple * 20) == 0) {
				
				if(slots[0] == null) {
					slots[0] = new ItemStack(ModItems.scroll);
					
				} else if(slots[0].getItem() == ModItems.scroll && slots[0].stackSize < 4) {
					slots[0].stackSize++;
				}
			}
		}
	}

}
