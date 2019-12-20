package com.hfr.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockUnstackable extends ItemBlock {

	public ItemBlockUnstackable(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setMaxStackSize(1);
	}

}
