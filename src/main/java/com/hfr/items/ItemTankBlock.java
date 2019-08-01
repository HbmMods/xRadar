package com.hfr.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemTankBlock extends ItemBlock {

	public ItemTankBlock(Block p_i45328_1_) {
		super(p_i45328_1_);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		if(stack.hasTagCompound()) {
			
			int fill = stack.stackTagCompound.getInteger("fill");
			
			if(fill > 0) {
				String s = "";
				
				switch(stack.stackTagCompound.getInteger("type")) {
				case 0: s = "Crude Oil"; break;
				case 1: s = "Natural Gas"; break;
				case 2: s = "Bunker Fuel"; break;
				case 3: s = "Dieles"; break;
				case 4: s = "Jet Fuel"; break;
				case 5: s = "Petroleum Gas"; break;
				}
				
				list.add("Type: " + s);
				list.add("Amount: " + fill);
			} else {
				list.add("Empty");
			}
		} else {
			list.add("Empty");
		}
	}

}
