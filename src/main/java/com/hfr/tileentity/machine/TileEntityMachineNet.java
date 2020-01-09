package com.hfr.tileentity.machine;

import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.AuxGaugePacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.biome.BiomeGenBase;

public class TileEntityMachineNet extends TileEntity implements ISidedInventory {

	public ItemStack slots[];
	
	public boolean isJammed = false;
	
	private String customName;
	
	public TileEntityMachineNet() {
		slots = new ItemStack[5];
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineNet";
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
	
	int rate = 1;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(operational() && hasSpace() && slots[4] == null) {
				
				int fishrate = MainRegistry.averageFishrate;
				
				BiomeGenBase b = worldObj.getBiomeGenForCoords(xCoord, zCoord);
				
				if(b == BiomeGenBase.ocean || b == BiomeGenBase.deepOcean)
					fishrate = MainRegistry.superFishrate;
				
				if(b == BiomeGenBase.river)
					fishrate = MainRegistry.goodFishrate;
				
				if(b == BiomeGenBase.desert || b == BiomeGenBase.desertHills ||
						b == BiomeGenBase.mesa || b == BiomeGenBase.mesaPlateau || 
						b == BiomeGenBase.mesaPlateau_F || b == BiomeGenBase.extremeHills ||
						b == BiomeGenBase.savanna || b == BiomeGenBase.savannaPlateau ||
						b == BiomeGenBase.extremeHillsEdge || b == BiomeGenBase.extremeHillsPlus ||
						b == BiomeGenBase.hell)
					fishrate = MainRegistry.crapFishrate;
				
				//1 minute
				if(worldObj.rand.nextInt(fishrate * 20) == 0) {
					
					Item fish = Items.fish;
					
					if(worldObj.rand.nextInt(100) < MainRegistry.whaleChance)
						fish = ModItems.whale_meat;
						
					for(int i = 0; i < 4; i++) {
						if(slots[i] == null) {
							slots[i] = new ItemStack(fish);
							break;
						} else if(slots[i].getItem() == fish && slots[i].stackSize < slots[i].getMaxStackSize()){
							slots[i].stackSize++;
							break;
						}
					}
				}
				
				//15 minutes
				if(worldObj.rand.nextInt(MainRegistry.jamRate * 20) == 0) {
					
					int i = worldObj.rand.nextInt(10);
					
					if(i < 5)
						slots[4] = new ItemStack(Items.stick).setStackDisplayName("Driftwood");
					else if(i < 7)
						slots[4] = new ItemStack(Blocks.sapling);
					else if(i < 9)
						slots[4] = new ItemStack(Blocks.waterlily);
					else
						slots[4] = new ItemStack(Items.bone).setStackDisplayName("Rattle Me Bones");
				}
			}
			
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, slots[4] == null ? 0 : 1, 0));
		}
	}
	
	public boolean operational() {

		for(int i = -2; i <= 2; i++)
			for(int j = -2; j <= 2; j++)
				if(worldObj.getBlock(xCoord + i, yCoord, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord, zCoord + j))
					return false;


		for(int x = -2; x <= 2; x++)
			for(int y = -3; y < 0; y++)
				for(int z = -2; z <= 2; z++)
					if(worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z).getMaterial() != Material.water)
						return false;
		
		return true;
	}
	
	public boolean hasSpace() {
		
		for(int i = 0; i < 4; i++) {
			
			if((slots[i] == null ||
					(slots[i] != null && slots[i].getItem() == Items.fish && slots[i].stackSize < slots[i].getMaxStackSize())) ||
					(slots[i] != null && slots[i].getItem() == ModItems.whale_meat && slots[i].stackSize < slots[i].getMaxStackSize()))
				return true;
		}
		
		return false;
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
