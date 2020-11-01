package com.hfr.blocks.props;

import com.hfr.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSandbags extends Block {
	
	public BlockSandbags(Material mat) {
        super(mat);
    }

    public boolean isOpaqueCube() {
    	
        return false;
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

        float f = 0.0625F;
        
        if(world.getBlockMetadata(x, y, z) < 4) {
	        this.setBlockBounds(4*f, 0.0F, 0.0F, 12*f, 1.0F, 1.0F);
        } else {
	        this.setBlockBounds(0.0F, 0.0F, 4*f, 1.0F, 1.0F, 12*f);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

        float f = 0.0625F;
        
        if(world.getBlockMetadata(x, y, z) < 4) {
	        this.setBlockBounds(4*f, 0.0F, 0.0F, 12*f, 1.0F, 1.0F);
			return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
        } else {
	        this.setBlockBounds(0.0F, 0.0F, 4*f, 1.0F, 1.0F, 12*f);
			return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
        }
    }

    public int getRenderType() {
    	
        return ResourceManager.id_sandbags;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}
}
