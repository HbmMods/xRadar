package com.hfr.tileentity.machine;

import com.hfr.main.MainRegistry;
import com.hfr.util.Location;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCoalGen extends TileEntityMachineBase implements IEnergyProvider {
	
	public EnergyStorage storage = new EnergyStorage(MainRegistry.coalgenProduction * 1000, MainRegistry.coalgenProduction * 10, MainRegistry.coalgenProduction * 10);
	
	public int burnTime = 0;

	public TileEntityCoalGen() {
		super(2);
	}

	@Override
	public String getName() {
		return "container.coalGen";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			//if the furnace is burning, reduce the burn time and generate power
			if(burnTime > 0) {
				burnTime--;
				storage.setEnergyStored(storage.getEnergyStored() + MainRegistry.coalgenProduction);
			}
			
			//there's no <else> here so the flame-out and the re-ignition can happen in the same tick
			
			//if the furnace isn't burning, there's still room or energy and the fuel item is combustable, the item will be ignited
			if(burnTime == 0 && storage.getEnergyStored() < storage.getMaxEnergyStored() && slots[0] != null && TileEntityFurnace.getItemBurnTime(slots[0]) > 0) {
				burnTime = TileEntityFurnace.getItemBurnTime(slots[0]);
				this.decrStackSize(0, 1);
			}

			if (slots[1] != null && slots[1].getItem() instanceof IEnergyContainerItem) {
				
				IEnergyContainerItem item = (IEnergyContainerItem) slots[1].getItem();
				
				int receive = (int) Math.min(storage.getEnergyStored(), item.getMaxEnergyStored(slots[1]) - item.getEnergyStored(slots[1]));

				int r = item.receiveEnergy(slots[1], receive, false);
				storage.setEnergyStored(storage.getEnergyStored() - r);
			}
			
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				if(!canConnectEnergy(dir))
					continue;
			
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

			this.updateGauge(storage.getEnergyStored(), 0, 50);
			this.updateGauge(burnTime, 1, 50);
		} else {
			
			if(burnTime > 0) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				ForgeDirection dur = dir.getRotation(ForgeDirection.UP);

				double dx = xCoord + 0.5D + dir.offsetX * 0.25D + dur.offsetX * 0.25D;
				double dy = yCoord + 2D;
				double dz = zCoord + 0.5D + dir.offsetZ * 0.25D + dur.offsetZ * 0.25D;
				
				worldObj.spawnParticle("smoke", dx, dy, dz, 0.0, 0.05, 0.0);
			}
		}
	}
	
	public void processGauge(int val, int id) {
		
		switch(id) {
		case 0: storage.setEnergyStored(val); break;
		case 1: burnTime = val; break;
		}
	}
	
	public int getPowerScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return i == 0 && TileEntityFurnace.getItemBurnTime(itemStack) > 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	int[] access = new int[] { 0 };
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return access;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i != 0)
			return true;
		
		return TileEntityFurnace.getItemBurnTime(itemStack) > 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.UNKNOWN;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		
		if(canConnectEnergy(from))
			return storage.getEnergyStored();
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		
		if(canConnectEnergy(from))
			return storage.getMaxEnergyStored();
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		storage.readFromNBT(nbt);
		burnTime = nbt.getInteger("burnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		storage.writeToNBT(nbt);
		nbt.setInteger("burnTime", burnTime);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
