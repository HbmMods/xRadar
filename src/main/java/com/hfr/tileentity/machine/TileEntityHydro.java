package com.hfr.tileentity.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.items.ModItems;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.AuxElectricityPacket;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityHydro extends TileEntity implements ISidedInventory {

	public ItemStack slots[];
	
	public int power;
	public static final int maxPower = 10000;
	
	private String customName;
	
	public TileEntityHydro() {
		slots = new ItemStack[1];
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
		return this.hasCustomInventoryName() ? this.customName : "container.hydro";
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
		return true;
	}
	
	int rate = 1;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			int walls = getWallNr();
			int turbines = getTurbineNr();
			
			if(walls > 0 && turbines > 0) {
				power += getRate(walls, turbines);
			}
			
			if(power >= maxPower) {
				power -= maxPower;
				
				if(slots[0] == null) {
					slots[0] = new ItemStack(ModItems.tidal_energy);
					
				} else if(slots[0].getItem() == ModItems.tidal_energy && slots[0].stackSize < 64){
					slots[0].stackSize++;
				}
			}
			
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
		}
	}
	
	public int getRate(int walls, int turbines) {
		
		double bonus = 0;
		
		for(int i = 0; i < turbines; i++) {
			
			bonus += 1D / Math.pow(2, i);
		}
		
		return (int) (walls * rate * bonus * 0.25D);
	}
	
	int maxHeight = 5;
	int maxWidth = 9;
	
	public int getWallNr() {
		
		int counter = 0;
		
		for(int i = -maxWidth; i <= maxWidth; i++) {
			for(int j = -maxHeight; j <= maxHeight; j++) {
				
				Block b = null;
				
				if(this.getBlockMetadata() == 3) {
					
					b = worldObj.getBlock(xCoord, yCoord + j, zCoord + i);
					
					if(b == ModBlocks.hydro_core && !(i == 0 && j == 0)) {
						worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
						return 0;
						
					} else if(b == ModBlocks.hydro_wall || b == ModBlocks.hydro_turbine || b == ModBlocks.hydro_core) {
						
						if(worldObj.getBlock(xCoord + 1, yCoord + j, zCoord + i).getMaterial() == Material.water && validFront(worldObj.getBlock(xCoord - 1, yCoord + j, zCoord + i), i % 2 == 0))
							counter++;
						else if(validFront(worldObj.getBlock(xCoord + 1, yCoord + j, zCoord + i), i % 2 == 0) && worldObj.getBlock(xCoord - 1, yCoord + j, zCoord + i).getMaterial() == Material.water)
							counter++;
					}
					
				} else if(this.getBlockMetadata() == 2) {
					
					b = worldObj.getBlock(xCoord + i, yCoord + j, zCoord);
					
					if(b == ModBlocks.hydro_core && !(i == 0 && j == 0)) {
						worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
						return 0;
						
					} else if(b == ModBlocks.hydro_wall || b == ModBlocks.hydro_turbine || b == ModBlocks.hydro_core) {
						
						if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + 1).getMaterial() == Material.water && validFront(worldObj.getBlock(xCoord + i, yCoord + j, zCoord - 1), i % 2 == 0))
							counter++;
						else if(validFront(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + 1), i % 2 == 0) && worldObj.getBlock(xCoord + i, yCoord + j, zCoord - 1).getMaterial() == Material.water)
							counter++;
					}
				}
			}
		}
		
		return counter;
	}
	
	public int getTurbineNr() {
		
		int counter = 0;
		
		if(this.getBlockMetadata() == 3) {
			
			for(int i = 1; i < maxWidth; i++) {
				
				Block b = worldObj.getBlock(xCoord, yCoord, zCoord + i);
				
				if(b != ModBlocks.hydro_wall && b != ModBlocks.hydro_turbine)
					break;
				
				if(b == ModBlocks.hydro_wall)
					continue;
				
				if(i % 2 == 0 && b == ModBlocks.hydro_turbine) {
					
					if(checkTurbineSeg(xCoord, zCoord + i))
						counter++;
				}
			}
			
			for(int i = 1; i < maxWidth; i++) {
				
				Block b = worldObj.getBlock(xCoord, yCoord, zCoord - i);
				
				if(b != ModBlocks.hydro_wall && b != ModBlocks.hydro_turbine)
					break;
				
				if(b == ModBlocks.hydro_wall)
					continue;
				
				if(i % 2 == 0 && b == ModBlocks.hydro_turbine) {
					
					if(checkTurbineSeg(xCoord, zCoord - i))
						counter++;
				}
			}
			
		} else if(this.getBlockMetadata() == 2) {
			
			for(int i = 1; i < maxWidth; i++) {
				
				Block b = worldObj.getBlock(xCoord + i, yCoord, zCoord);
				
				if(b != ModBlocks.hydro_wall && b != ModBlocks.hydro_turbine)
					break;
				
				if(b == ModBlocks.hydro_wall)
					continue;
				
				if(i % 2 == 0 && b == ModBlocks.hydro_turbine) {
					
					if(checkTurbineSeg(xCoord + i, zCoord))
						counter++;
				}
			}
			
			for(int i = 1; i < maxWidth; i++) {
				
				Block b = worldObj.getBlock(xCoord - i, yCoord, zCoord);
				
				if(b != ModBlocks.hydro_wall && b != ModBlocks.hydro_turbine)
					break;
				
				if(b == ModBlocks.hydro_wall)
					continue;
				
				if(i % 2 == 0 && b == ModBlocks.hydro_turbine) {
					
					if(checkTurbineSeg(xCoord - i, zCoord))
						counter++;
				}
			}
		}
		
		return counter;
	}
	
	private boolean validFront(Block b, boolean seg) {
		
		if(b.getMaterial() == Material.air)
			return true;
		
		if(seg && b.getMaterial() == Material.water)
			return true;
		
		return false;
	}
	
	public boolean checkTurbineSeg(int x, int z) {

		Block b0 = worldObj.getBlock(x, yCoord, z);
		Block b1 = worldObj.getBlock(x, yCoord + 1, z);
		Block b2 = worldObj.getBlock(x, yCoord + 2, z);
		
		if(b0 == ModBlocks.hydro_turbine && b1.getMaterial() == Material.water && b2 == ModBlocks.hydro_wall) {
			return true;
		}
		
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
}
