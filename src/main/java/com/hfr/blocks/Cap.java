package com.hfr.blocks;

import com.hfr.tileentity.TileEntityCap;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Cap extends BlockContainer {

	protected Cap(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCap();
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
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2*f, 1.0F);
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2*f, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			
			TileEntityCap cap = (TileEntityCap)world.getTileEntity(x, y, z);
			
			if(player.capabilities.isCreativeMode && player.getHeldItem() != null && player.getHeldItem().hasTagCompound()) {

				int xCoord = player.getHeldItem().stackTagCompound.getInteger("xCoord");
				int zCoord = player.getHeldItem().stackTagCompound.getInteger("zCoord");
				
				if(xCoord != 0 || zCoord != 0) {
					
					double dist = Math.sqrt(Math.pow(xCoord - x, 2) + Math.pow(zCoord - z, 2));
					
					dist /= 16D; //because we want chunk-distance
					
					cap.radius = (int)dist;
					return true;
				}
			}
			
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

}
