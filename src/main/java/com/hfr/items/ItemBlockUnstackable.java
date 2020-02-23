package com.hfr.items;

import com.hfr.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockUnstackable extends ItemBlock {

	public ItemBlockUnstackable(Block block) {
		super(block);
		
		if(block == ModBlocks.machine_derrick)
			this.setMaxStackSize(16);
		else
			this.setMaxStackSize(1);
	}

}
