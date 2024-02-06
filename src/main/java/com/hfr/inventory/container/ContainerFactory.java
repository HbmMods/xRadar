package com.hfr.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hfr.tileentity.machine.TileEntityMachineFactory;
import com.hfr.util.LockedSlot;

public class ContainerFactory extends Container {

    private TileEntityMachineFactory diFurnace;

    public ContainerFactory(InventoryPlayer invPlayer, TileEntityMachineFactory tedf) {

        diFurnace = tedf;

        // Cogs
        for (int i = 0; i < 4; i++) this.addSlotToContainer(new LockedSlot(tedf, i, 26 + 18 * i, 35));
        // Battery Slo- i mean jammed slot
        this.addSlotToContainer(new LockedSlot(tedf, 4, 116, 35));

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
                if (!this.mergeItemStack(var5, 4, 5, true)) {
                    return null;
                }
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
        return diFurnace.isUseableByPlayer(player);
    }
}
