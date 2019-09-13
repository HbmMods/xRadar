package com.hfr.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RBMKElement extends Block {

	public RBMKElement(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	public int getRenderType(){
		return 10000;
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
