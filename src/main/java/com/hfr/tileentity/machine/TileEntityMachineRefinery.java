package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.AuxElectricityPacket;
import com.hfr.packet.tile.AuxGaugePacket;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRefinery extends TileEntity implements ISidedInventory, IEnergyHandler {

	private ItemStack slots[];

	public EnergyStorage storage = new EnergyStorage(MainRegistry.refineryBuffer, MainRegistry.refineryBuffer / 100, MainRegistry.refineryBuffer / 100);

	public int oil;
	public static final int maxOil = 64000;
	public int heavy;
	public static final int maxHeavy = 16000;
	public int naph;
	public static final int maxNaph = 16000;
	public int light;
	public static final int maxLight = 16000;
	public int petro;
	public static final int maxPetro = 16000;
	
	private static final int[] slots_top = new int[] { 1 };
	private static final int[] slots_bottom = new int[] { 0, 2, 4, 6, 8, 10, 11};
	private static final int[] slots_side = new int[] { 0, 3, 5, 7, 9 };
	
	private String customName;
	
	public TileEntityMachineRefinery() {
		slots = new ItemStack[11];
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineRefinery";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		slots = new ItemStack[getSizeInventory()];
		storage.readFromNBT(nbt);
		this.oil = nbt.getInteger("oil");
		this.heavy = nbt.getInteger("heavy");
		this.naph = nbt.getInteger("naph");
		this.light = nbt.getInteger("light");
		this.petro = nbt.getInteger("petro");
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();

		storage.writeToNBT(nbt);
		nbt.setInteger("oil", oil);
		nbt.setInteger("heavy", heavy);
		nbt.setInteger("naph", naph);
		nbt.setInteger("light", light);
		nbt.setInteger("petro", petro);
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			if(slots[0] != null && slots[0].getItem() == ModItems.battery)
				storage.setEnergyStored(storage.getMaxEnergyStored());

			if (slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[0].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(),
						item.getEnergyStored(slots[0]));

				int e = item.extractEnergy(slots[0], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + extract);
			}
			
			//HEAVY
			if(slots[3] != null && slots[3].getItem() == ModItems.canister_empty && heavy >= 1000) {
				
				if(slots[4] != null && slots[4].getItem() == ModItems.canister_petroil && slots[4].stackSize < slots[4].getMaxStackSize()) {
					
					this.decrStackSize(3, 1);
					slots[4].stackSize++;
					heavy -= 1000;
				} else if(slots[4] == null) {
					
					this.decrStackSize(3, 1);
					slots[4] = new ItemStack(ModItems.canister_petroil);
					heavy -= 1000;
				}
			}
			
			//NAPH
			if(slots[5] != null && slots[5].getItem() == ModItems.canister_empty && naph >= 1000) {
				
				if(slots[6] != null && slots[6].getItem() == ModItems.canister_diesel && slots[6].stackSize < slots[6].getMaxStackSize()) {
					
					this.decrStackSize(5, 1);
					slots[6].stackSize++;
					naph -= 1000;
				} else if(slots[6] == null) {
					
					this.decrStackSize(5, 1);
					slots[6] = new ItemStack(ModItems.canister_diesel);
					naph -= 1000;
				}
			}
			
			//LIGHT
			if(slots[7] != null && slots[7].getItem() == ModItems.canister_empty && light >= 1000) {
				
				if(slots[8] != null && slots[8].getItem() == ModItems.canister_kerosene && slots[8].stackSize < slots[8].getMaxStackSize()) {
					
					this.decrStackSize(7, 1);
					slots[8].stackSize++;
					light -= 1000;
				} else if(slots[8] == null) {
					
					this.decrStackSize(7, 1);
					slots[8] = new ItemStack(ModItems.canister_kerosene);
					light -= 1000;
				}
			}
			
			//PETRO
			if(slots[9] != null && slots[9].getItem() == ModItems.gas_empty && petro >= 1000) {
				
				if(slots[10] != null && slots[10].getItem() == ModItems.gas_petroleum && slots[10].stackSize < slots[10].getMaxStackSize()) {
					
					this.decrStackSize(9, 1);
					slots[10].stackSize++;
					petro -= 1000;
				} else if(slots[10] == null) {
					
					this.decrStackSize(9, 1);
					slots[10] = new ItemStack(ModItems.gas_petroleum);
					petro -= 1000;
				}
			}
			
			//CRUDE
			if(slots[1] != null && slots[1].getItem() == ModItems.canister_oil && oil <= maxOil - 1000) {
				
				if(slots[2] != null && slots[2].getItem() == ModItems.canister_empty && slots[2].stackSize < slots[2].getMaxStackSize()) {
					
					this.decrStackSize(1, 1);
					slots[2].stackSize++;
					oil += 1000;
				} else if(slots[2] == null) {
					
					this.decrStackSize(1, 1);
					slots[2] = new ItemStack(ModItems.canister_empty);
					oil += 1000;
				}
			}
			
			int ho = MainRegistry.refHeavy;
			int nt = MainRegistry.refNaph;
			int lo = MainRegistry.refLight;
			int pe = MainRegistry.refPetro;
			
			if(storage.getEnergyStored() >= MainRegistry.refineryUse && oil >= MainRegistry.refOil &&
					heavy + ho <= maxHeavy && 
					naph + nt <= maxNaph && 
					light + lo <= maxLight && 
					petro + pe <= maxPetro) {

				oil -= MainRegistry.refOil;
				heavy += ho;
				naph += nt;
				light += lo;
				petro += pe;
				
				storage.setEnergyStored(storage.getEnergyStored() - MainRegistry.refineryUse);
			}

			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, storage.getEnergyStored()));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, oil, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, heavy, 1));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, naph, 2));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, light, 3));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, petro, 4));
		}
	}
	
	public long getPowerScaled(long i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	public long getOilScaled(long i) {
		return (oil * i) / maxOil;
	}
	
	public long getHeavyScaled(long i) {
		return (heavy * i) / maxHeavy;
	}
	
	public long getNaphScaled(long i) {
		return (naph * i) / maxNaph;
	}
	
	public long getLightScaled(long i) {
		return (light * i) / maxLight;
	}
	
	public long getPetroScaled(long i) {
		return (petro * i) / maxPetro;
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
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.UNKNOWN;
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

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		double toSend = Math.min(storage.getEnergyStored(), storage.getMaxExtract());

		if (!simulate) {
			storage.setEnergyStored(storage.getEnergyStored() - (int) Math.round(toSend));
		}

		return (int) Math.round(toSend);
	}
}
