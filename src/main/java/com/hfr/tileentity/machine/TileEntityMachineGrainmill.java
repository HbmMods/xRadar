package com.hfr.tileentity.machine;

import com.hfr.blocks.BlockSpeedy;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineGrainmill extends TileEntityMachineBase {
	
	public Clowder owner = null;
	
	public static final int maxProgress = 60;
	public int progress = 0;
	
	@SideOnly(Side.CLIENT)
	public float rotation;

	public TileEntityMachineGrainmill() {
		super(6);
	}

	@Override
	public String getName() {
		return "container.grainmill";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			if(operational()) {
				
				Ownership o = ClowderTerritory.getOwnerFromInts(xCoord, zCoord);
				
				if(owner != o.owner) {
					
					if(o != null && o.zone == Zone.FACTION) {
						if(owner != null)
							owner.addPrestigeGen(-Clowder.tentRate, worldObj);
						owner = o.owner;
						owner.addPrestigeGen(Clowder.tentRate, worldObj);
						
					} else {
						
						if(owner != null)
							owner.addPrestigeGen(-Clowder.tentRate, worldObj);
						
						owner = null;
					}
					
					this.markDirty();
				}
				
				if(hasWheat() && hasSpace()) {
					
					progress++;
					
					if(progress >= maxProgress) {
						remWheat();
						addFlour();
						progress = 0;
						this.markDirty();
					}
					
				} else {
					progress = 0;
				}
				
			} else {
				progress = 0;
				
				if(owner != null) {
					owner.addPrestigeGen(-Clowder.tentRate, worldObj);
					owner = null;
				}
			}
		}
	}
	
	public boolean operational() {

		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 6, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 6, zCoord + j))
					return false;

		for(int x = -1; x <= 1; x++)
			for(int z = -1; z <= 1; z++)
				if(!(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) instanceof BlockSpeedy))
					return false;
		
		return true;
	}
	
	public boolean hasWheat() {
		
		for(int i = 0; i < 3; i++) {
			
			if((slots[i] != null && slots[i].getItem() == Items.wheat))
				return true;
		}
		
		return false;
	}
	
	public void remWheat() {
		
		for(int i = 0; i < 3; i++) {
			
			if((slots[i] != null && slots[i].getItem() == Items.wheat)) {
				this.decrStackSize(i, 1);
				return;
			}
		}
	}
	
	public boolean hasSpace() {
		
		for(int i = 3; i < 6; i++) {
			
			if((slots[i] == null || (slots[i] != null && slots[i].getItem() == ModItems.flour && slots[i].stackSize < slots[i].getMaxStackSize())))
				return true;
		}
		
		return false;
	}
	
	public void addFlour() {
		
		for(int i = 3; i < 6; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.flour && slots[i].stackSize < slots[i].getMaxStackSize()) {
				slots[i].stackSize++;
				return;
			} else if(slots[i] == null) {
				slots[i] = new ItemStack(ModItems.flour);
				return;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		owner = Clowder.getClowderFromName("owner");
		
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
		
		if(owner != null)
			nbt.setString("owner", owner.name);

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
