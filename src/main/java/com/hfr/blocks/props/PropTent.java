package com.hfr.blocks.props;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.prop.TileEntityProp;

import net.minecraft.block.Block;
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
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i)
    {
		if(i >= ForgeDirection.UNKNOWN.ordinal()) {
			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			
			if(owner != null && owner.zone == Zone.FACTION) {

				TileEntityProp tent = (TileEntityProp)world.getTileEntity(x, y, z);
				
				if(this == ModBlocks.tp_tent) {
					
					if(tent != null) {
						owner.owner.warps.remove(tent.warp);
						owner.owner.save(world);
					}
				}
				
				if(tent.operational())
					owner.owner.addPrestigeGen(-Clowder.tentRate, world);
			}
		}
		
		super.breakBlock(world, x, y, z, b, i);
    }

}
