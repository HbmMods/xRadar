package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDieselGen extends TileEntityMachineBase implements IEnergyProvider {
	
	public EnergyStorage storage = new EnergyStorage(MainRegistry.dieselProduction * 100, MainRegistry.dieselProduction * 10, MainRegistry.dieselProduction * 10);
	
	public int diesel;
	public static final int maxDiesel = 16000;

	public TileEntityDieselGen() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.dieselGen";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(slots[0] != null && slots[0].getItem() == ModItems.canister_diesel && diesel + 1000 <= maxDiesel) {
				
				if(slots[1] == null) {
					
					slots[1] = new ItemStack(ModItems.canister_empty);
					this.decrStackSize(0, 1);
					diesel += 1000;
				} else if(slots[1].getItem() == ModItems.canister_empty && slots[1].stackSize < slots[1].getMaxStackSize()) {

					slots[1].stackSize++;
					this.decrStackSize(0, 1);
					diesel += 1000;
				}
			}
			
			if(storage.getEnergyStored() < storage.getMaxEnergyStored()) {
				for(int i = 0; i < 10; i++) {
					
					if(diesel > 0) {
						diesel--;
						this.storage.receiveEnergy(MainRegistry.dieselProduction / 10, false);
					}
				}
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

			if (slots[2] != null && slots[2].getItem() instanceof IEnergyContainerItem) {
				
				IEnergyContainerItem item = (IEnergyContainerItem) slots[2].getItem();
				
				int receive = (int) Math.min(storage.getEnergyStored(), item.getMaxEnergyStored(slots[2]) - item.getEnergyStored(slots[2]));

				int r = item.receiveEnergy(slots[2], receive, false);
				storage.setEnergyStored(storage.getEnergyStored() - r);
			}

			this.updateGauge(storage.getEnergyStored(), 0, 50);
			this.updateGauge(diesel, 1, 50);
		} else {
			
			//if(storage.getEnergyStored() < storage.getMaxEnergyStored() && diesel > 0)
			//	worldObj.spawnParticle("smoke", xCoord + 0.75, yCoord + 1, zCoord + 0.375, 0.0, 0.1, 0.0);
		}
	}
	
	public void processGauge(int val, int id) {
		
		if(id == 0)
			storage.setEnergyStored(val);
		if(id == 1)
			diesel = val;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UNKNOWN;
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
		diesel = nbt.getInteger("diesel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		storage.writeToNBT(nbt);
		nbt.setInteger("diesel", diesel);
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
