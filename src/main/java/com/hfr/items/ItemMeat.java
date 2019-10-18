package com.hfr.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemMeat extends ItemFood {

	public ItemMeat() {
		super(20, true);
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.ITALIC + "[Japan wants to know your location]");
	}

}
