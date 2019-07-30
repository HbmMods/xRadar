package com.hfr.items;

import java.util.List;

import com.hfr.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemLore extends Item {
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.upgrade_radius) {
			list.add("Increases field radius by " + MainRegistry.upRange + " blocks");
			list.add("Increases energy consumption by " + MainRegistry.fieldRange + " RF/t");
			list.add("");
			list.add("Stacks to 16");
		}
		if(this == ModItems.upgrade_health) {
			list.add("Increases shield strength by " + MainRegistry.upHealth + "HP");
			list.add("Increases energy consumption by " + MainRegistry.fieldHealth + " RF/t");
			list.add("");
			list.add("Stacks to 16");
		}
		if(this == ModItems.upgrade_bedrock) {
			list.add("Makes shield unbreakable");
			list.add("Has no downsides");
			list.add("");
			list.add("Doesn't stack, because why would it?");
		}
		
		
		
		if(this == ModItems.canister_oil) {
			list.add("Freedom juice");
		}
		if(this == ModItems.gas_natural) {
			list.add("Methane and ethane gas");
		}
		if(this == ModItems.canister_petroil) {
			list.add("Low-quality fuel oil");
		}
		if(this == ModItems.canister_diesel) {
			list.add("Standard low-sulfur diesel fuel");
		}
		if(this == ModItems.canister_kerosene) {
			list.add("Jet A kerosene-based aviation fuel");
		}
		if(this == ModItems.gas_petroleum) {
			list.add("Liquefied propane and buthane gas");
		}
		
		

		if(this == ModItems.drum) {
			list.add("Contains 30 stacks of gunpowder");
		}
		if(this == ModItems.charge_naval) {
			list.add("Naval cannon ammo");
		}
		if(this == ModItems.charge_railgun) {
			list.add("Railgun ammo");
		}
		if(this == ModItems.charge_bfg) {
			list.add("BFG 10K ammo");
		}
	}
}
