package com.hfr.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCan extends Item {

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
    	
    	if(entity instanceof EntityHorse) {
    		
			stack.stackSize--;
    		
			if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.canned_jizz)))
				player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.canned_jizz), true);
    		
    		return true;
    	}
    	
        return false;
    }
}
