package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineEFurnace extends TileEntityMachineBase implements IEnergyHandler {

	public EnergyStorage storage = new EnergyStorage(10000, 1000, 1000);
	
	public int progress;
	public static final int processingSpeed = 100;

	public TileEntityMachineEFurnace() {
		super(7);
	}

	@Override
	public String getName() {
		return "container.machineEFurnace";
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}
	
	public long getPowerRemainingScaled(long i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	public boolean hasPower() {
		return storage.getEnergyStored() >= 500;
	}
	
	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	public boolean canProcess() {
		if(slots[1] == null)
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
        
		if(itemStack == null)
		{
			return false;
		}
		
		if(slots[2] == null)
		{
			return true;
		}
		
		if(!slots[2].isItemEqual(itemStack)) {
			return false;
		}
		
		if(slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize()) {
			return true;
		}else{
			return slots[2].stackSize < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
			
			if(slots[2] == null)
			{
				slots[2] = itemStack.copy();
			}else if(slots[2].isItemEqual(itemStack)) {
				slots[2].stackSize += itemStack.stackSize;
			}
			
			for(int i = 1; i < 2; i++)
			{
				if(slots[i].stackSize <= 0)
				{
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				}else{
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0)
				{
					slots[i] = null;
				}
			}
		}
	}
	
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			if (slots[0] != null && slots[0].getItem() == ModItems.battery)
				storage.setEnergyStored(storage.getMaxEnergyStored());

			if (slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[0].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(),
						item.getEnergyStored(slots[0]));

				int e = item.extractEnergy(slots[0], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + e);
			}

			if (hasPower() && canProcess()) {

				progress++;

				storage.setEnergyStored(storage.getEnergyStored() - 500);

				if (this.progress == processingSpeed) {
					this.progress = 0;
					this.processItem();
					this.markDirty();
				}
			} else {
				progress = 0;
			}

			this.updateGauge(progress, 0, 50);
			this.updateGauge(storage.getEnergyStored(), 1, 50);
		} else {
			
			if(progress > 0) {
				
				Vec3 vec = Vec3.createVectorHelper(-0.75, 1.3, 0.25);
				
				switch(getBlockMetadata() - 10)
				{
				case 2: vec.rotateAroundY((float)Math.toRadians(0)); break;
				case 4: vec.rotateAroundY((float)Math.toRadians(90)); break;
				case 3: vec.rotateAroundY((float)Math.toRadians(180)); break;
				case 5: vec.rotateAroundY((float)Math.toRadians(270)); break;
				}

				worldObj.spawnParticle("smoke", xCoord + 0.5 + vec.xCoord, yCoord + 0.5 + vec.yCoord, zCoord + 0.5 + vec.zCoord, 0.0, 0.0, 0.0);
				worldObj.spawnParticle("smoke", xCoord + 0.5 + vec.xCoord, yCoord + 0.5 + vec.yCoord, zCoord + 0.5 + vec.zCoord, 0.0, 0.05, 0.0);
				worldObj.spawnParticle("flame", xCoord + 0.5 + vec.xCoord, yCoord + 0.5 + vec.yCoord, zCoord + 0.5 + vec.zCoord, 0.0, 0.0, 0.0);
			}
		}
	}
	
	public void processGauge(int val, int id) {
		if(id == 0)
			this.progress = val;
		if(id == 1)
			this.storage.setEnergyStored(val);
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.progress = nbt.getInteger("progress");
		this.storage.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("progress", progress);
		this.storage.writeToNBT(nbt);
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
