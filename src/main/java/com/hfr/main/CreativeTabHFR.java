package com.hfr.main;

import com.hfr.blocks.ModBlocks;
import com.hfr.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabHFR extends CreativeTabs {

	public CreativeTabHFR(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
	}

	@Override
	public Item getTabIconItem() {
		if(Item.getItemFromBlock(ModBlocks.machine_radar) != null)
			return Item.getItemFromBlock(ModBlocks.machine_radar);
		else return ModItems.sexlol;
	}

}
