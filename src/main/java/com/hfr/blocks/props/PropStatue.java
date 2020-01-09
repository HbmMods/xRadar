package com.hfr.blocks.props;

import com.hfr.blocks.BlockDummyable;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.prop.TileEntityStatue;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PropStatue extends BlockDummyable {

	public PropStatue(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= ForgeDirection.UNKNOWN.ordinal())
			return new TileEntityStatue();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return MultiblockHandler.statue;
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i)
    {
		if(i >= ForgeDirection.UNKNOWN.ordinal()) {
			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			
			if(owner != null && owner.zone == Zone.FACTION) {

				TileEntityStatue statue = (TileEntityStatue)world.getTileEntity(x, y, z);
				
				if(statue.operational())
					owner.owner.addPrestigeGen(-Clowder.statueRate, world);
			}
		}
		
		super.breakBlock(world, x, y, z, b, i);
    }
}
