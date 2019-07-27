package com.hfr.blocks;

import java.util.Random;

import com.hfr.items.ModItems;
import com.hfr.tileentity.TileEntityDebug;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDebug extends BlockContainer {

	public BlockDebug(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDebug();
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
	
	/*public Item getItemDropped(int i, Random rand, int j) {
		return ModItems.uranium;
	}
	
	public int quantityDropped(Random rand) {
		return rand.nextInt(8) + 1;
	}*/

}
