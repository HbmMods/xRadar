package com.hfr.blocks.props;

import com.hfr.blocks.BlockDummyable;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.TileEntityProp;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PropTent extends BlockDummyable {

	public PropTent(Material mat) {
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
		return MultiblockHandler.tent;
	}

	@Override
	public int getOffset() {
		return 2;
	}

}
