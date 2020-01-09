package com.hfr.blocks.props;

import com.hfr.blocks.BlockDummyable;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.prop.TileEntityProp;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PropHesco extends BlockDummyable {

	public PropHesco(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= ForgeDirection.UNKNOWN.ordinal())
			return new TileEntityProp();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return MultiblockHandler.hesco;
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	
        float f = 0.0625F;
        
    	if(world.getBlockMetadata(x, y, z) == ForgeDirection.UP.ordinal() && world.getBlock(x, y + 1, z) != this) {
    		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + f * 10, z + 1);
    	} else {
    		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    	}
    }
	
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

        float f = 0.0625F;
        
    	if(world.getBlockMetadata(x, y, z) == ForgeDirection.UP.ordinal() && world.getBlock(x, y + 1, z) != this) {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f * 10F, 1.0F);
    	} else {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
}
