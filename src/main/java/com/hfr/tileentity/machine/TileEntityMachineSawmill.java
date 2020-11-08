package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineSawmill extends TileEntityMachineBase implements IEnergyHandler {
	
	public int progress = 0;
	public static final int maxProgress = 100;

	public EnergyStorage storage = new EnergyStorage(10000, 1000, 1000);

	public TileEntityMachineSawmill() {
		super(5);
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
		} else {
			
			if(progress > 0) {
				
				Vec3 vec = Vec3.createVectorHelper(-1, 0.875, -0.5);
				
				switch(getBlockMetadata() - 10)
				{
				case 2: vec.rotateAroundY((float)Math.toRadians(0)); break;
				case 4: vec.rotateAroundY((float)Math.toRadians(90)); break;
				case 3: vec.rotateAroundY((float)Math.toRadians(180)); break;
				case 5: vec.rotateAroundY((float)Math.toRadians(270)); break;
				}
				
				worldObj.spawnParticle("smoke", xCoord + 0.5 + vec.xCoord, yCoord + 0.5 + vec.yCoord, zCoord + 0.5 + vec.zCoord, 0.0, 0.0, 0.0);
			}
		}
	}
	
	public void processGauge(int val, int id) {
		switch(id) {
		case 0: storage.setEnergyStored(val);break;
		case 1: progress = val; break;
		}
	}
	
	private void process(ItemStack output) {
		
		if(slots[2] == null) {
			slots[2] = output.copy();
		} else {
			slots[2].stackSize += output.stackSize;
		}
		
		if(slots[3] == null) {
			slots[3] = new ItemStack(ModItems.part_sawdust);
		} else {
			if(slots[3].stackSize < slots[3].getMaxStackSize())
				slots[3].stackSize++;
		}
		
		if(worldObj.rand.nextInt(3) == 0) {
			if(slots[4] == null) {
				slots[4] = new ItemStack(ModItems.part_rubber_drop);
			} else {
				if(slots[4].stackSize < slots[4].getMaxStackSize())
					slots[4].stackSize++;
			}
		}
	}
	
	public boolean hasSpace(ItemStack output) {
		
		if(slots[2] == null)
			return true;
		
		if(slots[2].getItem() == output.getItem() &&
				slots[2].getItemDamage() == output.getItemDamage() &&
				slots[2].stackSize + output.stackSize <= slots[2].getMaxStackSize())
			return true;
		
		return false;
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
