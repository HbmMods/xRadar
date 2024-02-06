package com.hfr.main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.hfr.blocks.ModBlocks;

public class CreativeTabHFR extends CreativeTabs {

    public CreativeTabHFR(int p_i1853_1_, String p_i1853_2_) {
        super(p_i1853_1_, p_i1853_2_);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ModBlocks.machine_radar);
    }

}
