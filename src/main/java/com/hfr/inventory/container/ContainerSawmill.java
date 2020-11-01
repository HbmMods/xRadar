package com.hfr.inventory.container;

import com.hfr.tileentity.machine.TileEntityMachineSawmill;
import com.hfr.util.LockedSlot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSawmill extends Container {

	private TileEntityMachineSawmill sawmill;

	public ContainerSawmill(InventoryPlayer invPlayer, TileEntityMachineSawmill te) {

		sawmill = te;
		
		//Ingredient
		this.addSlotToContainer(new Slot(te, 0, 62, 35));
		//Battery
		this.addSlotToContainer(new Slot(te, 1, 26, 53));
		//Results
		this.addSlotToContainer(new LockedSlot(te, 2, 134, 26));
		this.addSlotToContainer(new LockedSlot(te, 3, 134, 44));
		this.addSlotToContainer(new LockedSlot(te, 4, 152, 35));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 <= 4) {
				if (!this.mergeItemStack(var5, 5, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				if (!this.mergeItemStack(var5, 0, 2, false))
					return null;
			}

			if (var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return sawmill.isUseableByPlayer(player);
	}
}
