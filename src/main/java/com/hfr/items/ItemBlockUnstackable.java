package com.hfr.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import com.hfr.blocks.ModBlocks;

public class ItemBlockUnstackable extends ItemBlock {

    public ItemBlockUnstackable(Block block) {
        super(block);

        if (block == ModBlocks.machine_derrick) this.setMaxStackSize(16);
        else this.setMaxStackSize(1);
    }

}
