package com.hfr.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemFuel extends Item {
	
	public static final int maxLife = 72000000;
	
	public static int getDura(ItemStack stack) {
		return getTag(stack, "dura");
	}
	
	public static void setDura(ItemStack stack, int value) {
		setTag(stack, value, "dura");
	}
	
	public static int getPoison(ItemStack stack) {
		return getTag(stack, "xenon");
	}
	
	public static void setPoison(ItemStack stack, int value) {
		setTag(stack, value, "xenon");
	}
	
	public static int getTag(ItemStack stack, String name) {
		
		if(stack.hasTagCompound()) {
			return stack.stackTagCompound.getInteger(name);
		} else {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("dura", maxLife);
			return maxLife;
		}
	}
	
	public static void setTag(ItemStack stack, int dura, String name) {

		if(stack.hasTagCompound()) {
			stack.stackTagCompound.setInteger(name, dura);
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Enrichment: " + (getDura(itemstack) / maxLife * 100) + "%");
		list.add("Xenon poison: " + getPoison(itemstack) + "%");
	}

}
