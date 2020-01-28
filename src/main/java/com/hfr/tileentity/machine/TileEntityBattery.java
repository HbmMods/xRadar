package com.hfr.tileentity.machine;

import com.hfr.util.Location;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityBattery extends TileEntityMachineBase implements IEnergyHandler {
	
	public EnergyStorage storage = new EnergyStorage(200000, 2000, 2000);

	public TileEntityBattery() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.battery";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			if(slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[0].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(), item.getEnergyStored(slots[0]));

				int e = item.extractEnergy(slots[0], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + e);
			}

			if (slots[1] != null && slots[1].getItem() instanceof IEnergyContainerItem) {
				
				IEnergyContainerItem item = (IEnergyContainerItem) slots[1].getItem();
				
				int receive = (int) Math.min(storage.getEnergyStored(), item.getMaxEnergyStored(slots[1]) - item.getEnergyStored(slots[1]));

				int r = item.receiveEnergy(slots[1], receive, false);
				storage.setEnergyStored(storage.getEnergyStored() - r);
			}
			
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
					Location loc = new Location(worldObj, xCoord, yCoord, zCoord).add(dir);
					TileEntity entity = loc.getTileEntity();
				
					if (entity != null && entity instanceof IEnergyReceiver) {
					
						IEnergyReceiver receiver = (IEnergyReceiver) entity;
						
						int maxExtract = storage.getMaxExtract();
						int maxAvailable = storage.extractEnergy(maxExtract, true);
						int energyTransferred = receiver.receiveEnergy(dir.getOpposite(), maxAvailable, false);
	
						storage.extractEnergy(energyTransferred, false);
					}
				}
			}

			this.updateGauge(storage.getEnergyStored(), 0, 50);
		}
	}
	
	public void processGauge(int val, int id) {
		storage.setEnergyStored(val);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UNKNOWN;
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
	
	public int getPowerScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		storage.readFromNBT(nbt);
		
		NBTTagList list = nbt.getTagList("items", 10);
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		storage.writeToNBT(nbt);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

}
