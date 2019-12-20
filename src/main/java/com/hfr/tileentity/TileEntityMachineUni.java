package com.hfr.tileentity;

import com.hfr.blocks.ModBlocks;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineUni extends TileEntityMachineBase {

	public TileEntityMachineUni() {
		super(6);
	}

	@Override
	public String getName() {
		return "container.machineUni";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			
			if(operational() && hasSpace() && slots[4] == null) {

				if(worldObj.rand.nextInt(MainRegistry.uniRate * 20) == 0) {
					
					for(int i = 0; i < 4; i++) {
						if(slots[i] == null) {
							slots[i] = new ItemStack(ModItems.science);
							break;
						} else if(slots[i].getItem() == ModItems.science && slots[i].stackSize < slots[i].getMaxStackSize()){
							slots[i].stackSize++;
							break;
						}
					}
				}
				
				if(worldObj.rand.nextInt(MainRegistry.uniJamRate * 20) == 0) {
					
					int i = worldObj.rand.nextInt(10);
					
					if(i < 5)
						slots[4] = new ItemStack(Items.paper).setStackDisplayName("Student Strike");
					else if(i < 7)
						slots[4] = new ItemStack(Items.gunpowder).setStackDisplayName("Bomb Threat");
					else if(i < 9)
						slots[4] = new ItemStack(Items.skull).setStackDisplayName("Workplace Accident");
					else
						slots[4] = new ItemStack(Items.bone).setStackDisplayName("Skeleton Attack");
				}
			}
		}
	}
	
	public boolean operational() {

		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 4, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 4, zCoord + j))
					return false;

		for(int x = -4; x <= 4; x++)
			for(int z = -4; z <= 4; z++)
				if(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) != ModBlocks.uni_foundation)
					return false;
		
		return true;
	}
	
	public boolean hasSpace() {
		
		for(int i = 0; i < 4; i++) {
			
			if((slots[i] == null || (slots[i] != null && slots[i].getItem() == ModItems.science && slots[i].stackSize < slots[i].getMaxStackSize())))
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

}
