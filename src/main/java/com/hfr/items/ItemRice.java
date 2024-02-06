package com.hfr.items;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;

import com.hfr.blocks.ModBlocks;

public class ItemRice extends ItemSeeds {

    public ItemRice() {
        super(ModBlocks.rice, Blocks.farmland);
    }

}
