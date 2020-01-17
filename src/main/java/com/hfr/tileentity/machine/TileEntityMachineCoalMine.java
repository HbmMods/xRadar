package com.hfr.tileentity.machine;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.BlockSpeedy;
import com.hfr.handler.MultiblockHandler;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCoalMine extends TileEntityMachineBase {
	
	public int supplies = 0;
	public static final int maxSupplies = 20;

	public TileEntityMachineCoalMine() {
		super(12);
	}

	@Override
	public String getName() {
		return "container.coalMine";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			boolean dirty = false;
			
			if(slots[5] != null && slots[5].getItem() == ModItems.miner_supplies && supplies < maxSupplies) {
				this.decrStackSize(5, 1);
				supplies++;
				dirty = true;
			}

			int workforce = getWorkforce();
			
			if(workforce > 0 && operational() && hasSpace()) {
				
				if(worldObj.rand.nextInt(MainRegistry.coalJamRate * 20) == 0) {
					
					if(hasCanary()) {
						this.decrStackSize(6, 1);
					} else {
						killMiners();
						worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 1, false, false);
					}
					
					dirty = true;
					
				} else if(worldObj.rand.nextInt(MainRegistry.coalRate * 20 / workforce) == 0) {
					
					addCoal();
					
					if(worldObj.rand.nextInt(15) == 0)
						supplies--;

					dirty = true;
				}
			}
			
			if(dirty)
				this.markDirty();
			
			this.updateGauge(supplies, 0, 50);
		}
	}
	
	public void processGauge(int val, int id) {
		this.supplies = val;
	}

	public int getSuppliesScaled(int i) {
		return (supplies * i) / maxSupplies;
	}
	
	public boolean operational() {
		
		BlockDummyable dummy = (BlockDummyable) this.getBlockType();
		
		int[] dim = MultiblockHandler.rotate(dummy.getDimensions(), ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset));
		int n = dim[2];
		int s = dim[3];
		int w = dim[4];
		int e = dim[5];

		for(int i = -w; i <= e; i++)
			for(int j = -n; j <= s; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 4, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 4, zCoord + j))
					return false;

		for(int x = -w; x <= e; x++)
			for(int z = -n; z <= s; z++)
				if(!(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) instanceof BlockSpeedy))
					return false;
		
		return true;
	}
	
	public int getMiners() {
		
		int count = 0;
		
		for(int i = 0; i < 5; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.miner) {
				count++;
			}
		}
		
		return count;
	}
	
	public void killMiners() {
		
		for(int i = 0; i < 5; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.miner && worldObj.rand.nextInt(4) == 0) {
				this.decrStackSize(i, 1);
			}
		}
	}
	
	public boolean hasSpace() {
		
		for(int i = 7; i < 12; i++) {
			
			if(slots[i] == null)
				return true;
			
			if(slots[i].getItem() == Items.coal && slots[i].stackSize < slots[i].getMaxStackSize())
				return true;
		}
		
		return false;
	}
	
	public boolean hasCanary() {
		
		return slots[6] != null && slots[6].getItem() == ModItems.canary;
	}
	
	public void addCoal() {
		
		for(int i = 7; i < 12; i++) {
			
			if(slots[i] == null) {
				slots[i] = new ItemStack(Items.coal);
				return;
			}
			
			if(slots[i].getItem() == Items.coal && slots[i].stackSize < slots[i].getMaxStackSize()) {
				slots[i].stackSize++;
				return;
			}
		}
	}
	
	public int getWorkforce() {
		return Math.min(supplies, getMiners());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.supplies = nbt.getInteger("supplies");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("supplies", supplies);
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
