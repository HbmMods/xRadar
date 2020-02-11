package com.hfr.tileentity.machine;

import com.hfr.blocks.BlockSpeedy;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineFactory extends TileEntityMachineBase implements IEnergyReceiver {
	
	public EnergyStorage storage = new EnergyStorage(MainRegistry.factoryConsumption * 100, MainRegistry.factoryConsumption * 10, MainRegistry.factoryConsumption * 10);

	public TileEntityMachineFactory() {
		super(6);
	}

	@Override
	public String getName() {
		return "container.machineFactory";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			/*if(slots[4] != null && slots[4].getItem() == ModItems.battery)
				storage.setEnergyStored(storage.getMaxEnergyStored());

			if (slots[4] != null && slots[4].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[4].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(),
						item.getEnergyStored(slots[4]));

				int e = item.extractEnergy(slots[4], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + e);
			}*/
			
			if(operational() && hasSpace() && slots[4] == null) {
				
				storage.setEnergyStored(storage.getEnergyStored() - MainRegistry.factoryConsumption);

				if(worldObj.rand.nextInt(MainRegistry.factoryRate * 20) == 0) {
					
					for(int i = 0; i < 4; i++) {
						if(slots[i] == null) {
							slots[i] = new ItemStack(ModItems.cog);
							break;
						} else if(slots[i].getItem() == ModItems.cog && slots[i].stackSize < slots[i].getMaxStackSize()){
							slots[i].stackSize++;
							break;
						}
					}
				}
				
				if(worldObj.rand.nextInt(MainRegistry.factoryJamRate * 20) == 0) {
					
					int i = worldObj.rand.nextInt(10);
					
					if(i < 5)
						slots[4] = new ItemStack(Items.paper).setStackDisplayName("Worker Strike");
					else if(i < 7)
						slots[4] = new ItemStack(Items.skull).setStackDisplayName("Workplace Accident");
					else if(i < 9)
						slots[4] = new ItemStack(Items.slime_ball).setStackDisplayName("Chemical Spill");
					else
						slots[4] = new ItemStack(Items.potato).setStackDisplayName("Communist Takeover");
				}
			}
			
			this.updateGauge(storage.getEnergyStored(), 0, 50);
		}
	}
	
	public void processGauge(int val, int id) {
		storage.setEnergyStored(val);
	}
	
	public boolean operational() {

		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 4, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 4, zCoord + j))
					return false;

		for(int x = -4; x <= 4; x++)
			for(int z = -4; z <= 4; z++)
				if(!(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) instanceof BlockSpeedy))
					return false;
		
		if(storage.getEnergyStored() < MainRegistry.factoryConsumption)
			return false;
		
		if(slots[4] != null)
			return false;
		
		return true;
	}
	
	public boolean hasSpace() {
		
		for(int i = 0; i < 4; i++) {
			
			if((slots[i] == null || (slots[i] != null && slots[i].getItem() == ModItems.cog && slots[i].stackSize < slots[i].getMaxStackSize())))
				return true;
		}
		
		return false;
	}
	
	private static final int[] access = new int[] { 0, 1, 2, 3, 4 };
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return access;
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

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.DOWN && from != ForgeDirection.UNKNOWN;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
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

}
