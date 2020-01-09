package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.AuxGaugePacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTank extends TileEntity implements ISidedInventory {

	private ItemStack slots[];
	
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };

	public int type;
	public int fill;
	public static final int maxFill = 2000*1000;

	public static final int FLUID_OIL = 0;
	public static final int FLUID_GAS = 1;
	public static final int FLUID_HEAVY = 2;
	public static final int FLUID_DIESEL = 3;
	public static final int FLUID_KEROSENE = 4;
	public static final int FLUID_PETROLEUM = 5;
	
	private String customName;
	
	public TileEntityTank() {
		slots = new ItemStack[4];
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
		return this.hasCustomInventoryName() ? this.customName : "container.tank";
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
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
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
		this.fill = nbt.getInteger("fill");
		this.type = nbt.getInteger("type");
		
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
		nbt.setInteger("fill", fill);
		nbt.setInteger("type", type);
		
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
		
		if(!worldObj.isRemote) {
			
			if(fill == 0 && slots[0] != null) {

				if(slots[0].getItem() == ModItems.canister_oil)
					type = FLUID_OIL;
				if(slots[0].getItem() == ModItems.gas_natural)
					type = FLUID_GAS;
				if(slots[0].getItem() == ModItems.canister_petroil)
					type = FLUID_HEAVY;
				if(slots[0].getItem() == ModItems.canister_diesel)
					type = FLUID_DIESEL;
				if(slots[0].getItem() == ModItems.canister_kerosene)
					type = FLUID_KEROSENE;
				if(slots[0].getItem() == ModItems.gas_petroleum)
					type = FLUID_PETROLEUM;
			}
			
			Item full = ModItems.canister_oil;
			Item empty = ModItems.canister_empty;
			
			switch(type) {
			case FLUID_GAS: full = ModItems.gas_natural; empty = ModItems.gas_empty; break;
			case FLUID_PETROLEUM: full = ModItems.gas_petroleum; empty = ModItems.gas_empty; break;
			case FLUID_HEAVY: full = ModItems.canister_petroil; break;
			case FLUID_DIESEL: full = ModItems.canister_diesel; break;
			case FLUID_KEROSENE: full = ModItems.canister_kerosene; break;
			}
			
			//add fluid
			if(slots[0] != null && slots[0].getItem() == full && fill + 1000 <= maxFill) {
				
				if(slots[1] != null && slots[1].getItem() == empty && slots[1].stackSize < slots[1].getMaxStackSize()) {
					
					this.decrStackSize(0, 1);
					slots[1].stackSize++;
					fill += 1000;
				} else if(slots[1] == null) {
					
					this.decrStackSize(0, 1);
					slots[1] = new ItemStack(empty);
					fill += 1000;
				}
			}
			
			//remove fluid
			if(fill >= 1000 && slots[2] != null && slots[2].getItem() == empty) {
				
				if(slots[3] != null && slots[3].getItem() == full && slots[3].stackSize < slots[3].getMaxStackSize()) {
					
					this.decrStackSize(2, 1);
					slots[3].stackSize++;
					fill -= 1000;
				}
				
				if(slots[3] == null) {
					
					this.decrStackSize(2, 1);
					slots[3] = new ItemStack(full);
					fill -= 1000;
				}
			}
			
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, fill, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, type, 1));
		}
	}
	
	public long getFillScaled(long i) {
		return (fill * i) / maxFill;
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
