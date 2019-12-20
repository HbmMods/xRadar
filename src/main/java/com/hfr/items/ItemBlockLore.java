package com.hfr.items;

import java.util.List;

import com.hfr.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockLore extends ItemBlock {
	
	public ItemBlockLore(Block b) {
		super(b);
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(field_150939_a == ModBlocks.uni_foundation) {
			list.add("Increases speed by 15%");
		}
		if(field_150939_a == ModBlocks.asphalt) {
			list.add("Foundation block");
			list.add("Increases speed by 25%");
		}

		if(field_150939_a == ModBlocks.hesco_block) {
			list.add("HESCO MIL brand collapsable gabion");
			list.add("Filled with 100% fair-trade dirt");
		}
		if(field_150939_a == ModBlocks.palisade) {
			list.add("Prehistoric wooden barricade");
			list.add("Made from only the finest oak logs");
		}
		if(field_150939_a == ModBlocks.stone_wall) {
			list.add("High-tech stone-based fortification");
		}
		if(field_150939_a == ModBlocks.brick_wall) {
			list.add("Classic 20th century ugly-as-shit construction");
		}
		if(field_150939_a == ModBlocks.great_wall) {
			list.add("\"Great wall\"");
			list.add("In every way identical to the stone wall");
		}
	}

}
