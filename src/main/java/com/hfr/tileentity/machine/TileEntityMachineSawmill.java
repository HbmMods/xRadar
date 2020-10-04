package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineSawmill extends TileEntityMachineBase implements IEnergyHandler {
	
	public int progress = 0;
	public static final int maxProgress = 100;

	public EnergyStorage storage = new EnergyStorage(10000, 1000, 1000);

	public TileEntityMachineSawmill() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.machineSawmill";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(slots[1] != null && slots[1].getItem() == ModItems.battery)
				storage.setEnergyStored(storage.getMaxEnergyStored());

			if (slots[1] != null && slots[1].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[1].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(),
						item.getEnergyStored(slots[1]));

				int e = item.extractEnergy(slots[1], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + e);
			}
			
			ItemStack recipe = getRecipe(slots[0]);
			
			if(recipe != null && hasSpace(recipe) && storage.getEnergyStored() >= 50) {
				
				progress++;
				
				if(progress >= maxProgress) {
					process(recipe);
					this.decrStackSize(0, 1);
					
					progress = 0;
					this.markDirty();
				}
				
				storage.setEnergyStored(storage.getEnergyStored() - 50);
				
			} else {
				progress = 0;
			}
			
			this.updateGauge(storage.getEnergyStored(), 0, 50);
			this.updateGauge(progress, 1, 50);
		}
	}
	
	public void processGauge(int val, int id) {
		switch(id) {
		case 0: storage.setEnergyStored(val);break;
		case 1: progress = val; break;
		}
	}
	
	private void process(ItemStack output) {
		
		int toAdd = output.stackSize;
		
		for(int i = 2; i <= 3; i++) {
			
			if(slots[i] == null) {
				slots[i] = new ItemStack(output.getItem(), toAdd, output.getItemDamage());
				return;
			}
			
			if(slots[i].getItem() == output.getItem() && slots[i].getItemDamage() == output.getItemDamage()) {
				
				int canAdd = Math.min(slots[i].getMaxStackSize() - slots[i].stackSize, toAdd);
				toAdd -= canAdd;
				slots[i].stackSize += canAdd;
				
				if(toAdd <= 0)
					return;
			}
		}
	}
	
	public boolean hasSpace(ItemStack output) {
		
		int toAdd = output.stackSize;
		
		for(int i = 2; i <= 3; i++) {
			
			if(slots[i] == null)
				return true;
			
			if(slots[i].getItem() == output.getItem() && slots[i].getItemDamage() == output.getItemDamage())
				toAdd -= (slots[i].getMaxStackSize() - slots[i].stackSize);
		}
		
		return toAdd <= 0;
	}
	
	public static ItemStack getRecipe(ItemStack stack) {
		
		if(stack == null)
			return null;
		
		if(stack.getItem() == Item.getItemFromBlock(Blocks.log)) {
			return new ItemStack(Blocks.planks, 6, stack.getItemDamage());
		}
		
		if(stack.getItem() == Item.getItemFromBlock(Blocks.log2)) {
			return new ItemStack(Blocks.planks, 6, stack.getItemDamage() + 4);
		}
		
		if(stack.getItem() == Item.getItemFromBlock(Blocks.planks)) {
			return new ItemStack(Items.stick, 6);
		}
		
		return null;
	}
	
	public int getProgressScaled(int i) {
		return progress * i / maxProgress;
	}

	public int getPowerScaled(int i) {
		return storage.getEnergyStored() * i / storage.getMaxEnergyStored();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
}
