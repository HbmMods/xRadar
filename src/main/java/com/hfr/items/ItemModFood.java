package com.hfr.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemModFood extends ItemFood {

	public ItemModFood(int p_i45340_1_) {
		super(p_i45340_1_, false);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.canned_jizz) {
			list.add("Extra goopy!");
		}
	}

}
