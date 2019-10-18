package com.hfr.blocks.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RBMKRods extends Block {

	public RBMKRods(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	public int getRenderType(){
		return 10001;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
