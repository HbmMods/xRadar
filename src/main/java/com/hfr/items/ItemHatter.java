package com.hfr.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHatter extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		ItemStack slot = player.inventory.getStackInSlot(0);
		
		if(slot != null && slot.getItem() instanceof ItemBlock) {
			player.inventory.armorInventory[3] = slot.copy();
	    	world.playSoundAtEntity(player, "hfr:item.techBleep", 1.0F, 1.0F);
		}
		
		return stack;
	}
}
