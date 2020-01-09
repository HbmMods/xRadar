package com.hfr.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hfr.data.StockData;
import com.hfr.data.StockData.Stock;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.StockPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineMarket extends TileEntity implements ISidedInventory {

	public ItemStack slots[];
	
	private String customName;
	
	public static List<Stock> stocks = new ArrayList();
	
	public TileEntityMachineMarket() {
		slots = new ItemStack[6];
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineMarket";
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
		return true;
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
        return new int[] { 0 };
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
			
			StockData data = StockData.getData(worldObj);
			
			List<EntityPlayer> list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 10, yCoord - 10, zCoord - 10, xCoord + 10, yCoord + 10, zCoord + 10));
			
			for(EntityPlayer player : list) {
				
				for(int i = 0; i < data.stocks.size(); i++) {
					Stock stock = data.stocks.get(i);
					int shares = data.getShares(player.getDisplayName(), stock);
					PacketDispatcher.wrapper.sendTo(new StockPacket(stock.name, stock.shortname, stock.value, shares, i), (EntityPlayerMP) player);
				}
			}
		}
	}
	
	public void buyStock(Stock stock, String player) {
		
		int price = (int) Math.ceil(stock.value[14]);
		
		int coins = 0;
		
		for(int i = 0; i < 3; i++)
			if(slots[i] != null && slots[i].getItem() == ModItems.coin)
				coins += slots[i].stackSize;
		
		if(coins < price)
			return;
		
		StockData data = StockData.getData(worldObj);
		int stocks = data.getShares(player, stock);
		
		if(stocks >= MainRegistry.stockCap)
			return;
		
		data.setShares(player, stock, stocks + 1);
		
		for(int i = 0; i < price; i++) {
			for(int j = 0; j < 3; j++) {
				if(slots[j] != null && slots[j].getItem() == ModItems.coin) {
					this.decrStackSize(j, 1);
					break;
				}
			}
		}
	}
	
	public void sellStock(Stock stock, String player) {
		
		int price = (int) Math.floor(stock.value[14]);
		
		int space = 0;
		
		for(int i = 3; i < 6; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.coin)
				space += 64 - slots[i].stackSize;
			
			if(slots[i] == null)
				space += 64;
		}
		
		if(space < price)
			return;
		
		StockData data = StockData.getData(worldObj);
		int stocks = data.getShares(player, stock);
		
		if(stocks == 0)
			return;
		
		data.setShares(player, stock, stocks - 1);
		
		for(int i = 0; i < price; i++) {
			for(int j = 3; j < 6; j++) {
				if(slots[j] != null && slots[j].getItem() == ModItems.coin && slots[j].stackSize < 64) {
					this.decrStackSize(j, -1);
					break;
				} else if(slots[j] == null) {
					slots[j] = new ItemStack(ModItems.coin);
					break;
				}
			}
		}
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
