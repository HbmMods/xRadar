package com.hfr.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockNoDrop extends Block {

	protected BlockNoDrop(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int i) {
		return null;
	}

}
