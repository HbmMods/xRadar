package com.hfr.blocks;

import com.hfr.tileentity.TileEntityFlagBig;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FlagBig extends BlockContainer {

	public FlagBig(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFlagBig();
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			
			TileEntityFlagBig cap = (TileEntityFlagBig)world.getTileEntity(x, y, z);
			
			for(int i = 0; i < cap.slots.length; i++) {
				
				if(cap.slots[i] != null) {
					
					int stack = cap.slots[i].stackSize;
					
					for(int j = 0; j < stack; j++) {
						
						//tries to give the player one item at a time. if it fails, it will skip to the next slot and drop all
						//remaining items of the stack at once.
						if(!player.inventory.addItemStackToInventory(new ItemStack(cap.slots[i].getItem()))) {
							player.dropPlayerItemWithRandomChoice(new ItemStack(cap.slots[i].getItem(), stack - j), false);
							cap.decrStackSize(i, stack - j);
							break;
						}
						
						cap.decrStackSize(i, 1);
					}
				}
			}
			
			player.inventoryContainer.detectAndSendChanges();
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}

}
