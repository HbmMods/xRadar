package com.hfr.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import com.hfr.main.ResourceManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRope extends Block {

	protected BlockRope(Material mat) {
		super(mat);
	}
	
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return ResourceManager.id_rope;
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return true;
    }
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        this.func_149797_b(world.getBlockMetadata(x, y, z));
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    public void func_149797_b(int meta) {
    	
        float f = 0.125F;

        if (meta == 2)
        {
            this.setBlockBounds(0.25F, 0.0F, 1.0F - f, 0.75F, 1.0F, 1.0F);
        }

        if (meta == 3)
        {
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, f);
        }

        if (meta == 4)
        {
            this.setBlockBounds(1.0F - f, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
        }

        if (meta == 5)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.25F, f, 1.0F, 0.75F);
        }
    }
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
    	return !world.isAirBlock(x - 1, y, z) &&
    			!world.isAirBlock(x + 1, y, z) &&
    			!world.isAirBlock(x, y, z - 1) &&
    			!world.isAirBlock(x, y, z + 1);
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
    {
        int j1 = p_149660_9_;

        if ((p_149660_9_ == 0 || p_149660_5_ == 2) && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_, p_149660_4_ + 1, NORTH))
        {
            j1 = 2;
        }

        if ((j1 == 0 || p_149660_5_ == 3) && p_149660_1_.isSideSolid(p_149660_2_, p_149660_3_, p_149660_4_ - 1, SOUTH))
        {
            j1 = 3;
        }

        if ((j1 == 0 || p_149660_5_ == 4) && p_149660_1_.isSideSolid(p_149660_2_ + 1, p_149660_3_, p_149660_4_, WEST))
        {
            j1 = 4;
        }

        if ((j1 == 0 || p_149660_5_ == 5) && p_149660_1_.isSideSolid(p_149660_2_ - 1, p_149660_3_, p_149660_4_, EAST))
        {
            j1 = 5;
        }

        return j1;
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
        int l = world.getBlockMetadata(x, y, z);
        boolean flag = false;

        if (l == 2 && !world.isAirBlock(x, y, z + 1))
        {
            flag = true;
        }

        if (l == 3 && !world.isAirBlock(x, y, z - 1))
        {
            flag = true;
        }

        if (l == 4 && !world.isAirBlock(x + 1, y, z))
        {
            flag = true;
        }

        if (l == 5 && !world.isAirBlock(x - 1, y, z))
        {
            flag = true;
        }
        
        if(this == world.getBlock(x, y + 1, z) && l == world.getBlockMetadata(x, y + 1, z))
        	flag = true;

        if (!flag)
        {
            world.func_147480_a(x, y, z, false);
        }

        super.onNeighborBlockChange(world, x, y, z, block);
    }
    

    public void updateTick(World world, int x, int y, int z, Random rand) {

        world.func_147480_a(x, y, z, false);
    }
}
