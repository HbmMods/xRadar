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
		
		
		
		if(this == ModItems.scroll) {
			list.add("In old times, ancient knowledge was just called knowledge.");
		}
		
		

		if(this == ModItems.miner) {
			list.add("For use in coal mines");
		}
		if(this == ModItems.miner_supplies) {
			list.add("For use in coal mines");
		}
		if(this == ModItems.canary) {
			list.add("For use in coal mines");
			list.add("Dies easily, but prevents accidents");
		}
		
		

		if(this == ModItems.components_wood) {
			list.add("Wood, but long");
		}
		if(this == ModItems.components_scaffold) {
			list.add("If only my self-worth were");
			list.add("as stable as this scaffold");
		}
		if(this == ModItems.components_mechanical) {
			list.add("Stainless steel cogs and");
			list.add("stained steel hinges");
		}
		if(this == ModItems.components_electronics) {
			list.add("Now with 20% more random transistors");
		}
		
		

		if(this == ModItems.drum) {
			list.add("Contains 30 stacks of gunpowder");
		}
		if(this == ModItems.charge_naval) {
			list.add("Naval cannon raiding ammo");
		}
		if(this == ModItems.charge_at) {
			list.add("Naval cannon ammo");
		}
		if(this == ModItems.charge_railgun) {
			list.add("Railgun ammo");
		}
		if(this == ModItems.charge_bfg) {
			list.add("BFG 10K ammo");
		}
		
		
		
		if(this == ModItems.tidal_energy) {
			list.add("Hydroelectric energy currency");
		}
		
		
		
		if(this == ModItems.missile_at_1) {
			list.add("Non-raiding V2 variant");
		}
		if(this == ModItems.missile_he_1) {
			list.add("V2 missile (Aggregat 4)");
		}
		if(this == ModItems.missile_he_3) {
			list.add("BM missile assembly with HMX warhead");
		}
		if(this == ModItems.missile_incendiary_1) {
			list.add("V2 with modified incendiary warhead");
		}
		if(this == ModItems.missile_emp) {
			list.add("Creates powerful EMP that");
			list.add("jams machines for 5 minutes");
		}
		if(this == ModItems.missile_ab) {
			list.add("Homes in on other missiles");
		}
		if(this == ModItems.missile_decoy) {
			list.add("Harmless missile that appears");
			list.add("as a tier 3 one on radars");
		}
		if(this == ModItems.missile_nuclear) {
			list.add("OpFo's \"The Package\" all over again");
			list.add("(Config Algorithm)");
		}
		
		

		if(this == ModItems.missile_devon_1) {
			list.add("Devon Corporation Land-Attack Cruise Missile");
			list.add("Compact RDX warhead");
		}
		if(this == ModItems.missile_devon_2) {
			list.add("Devon Corporation Land-Attack Cruise Missile");
			list.add("Experimental miniature ONC warhead");
		}
		if(this == ModItems.missile_devon_3) {
			list.add("Devon Corporation Land-Attack Cruise Missile");
			list.add("N² warhead");
		}
		if(this == ModItems.missile_cruise_at_1) {
			list.add("Like a Devon cruise missile, but shittier!");
		}
		


		if(this == ModItems.slbm_martin_1) {
			list.add("Conventional warhead");
			list.add("Ballistic missile (arcing)");
		}
		if(this == ModItems.slbm_martin_2) {
			list.add("Incendiary warhead");
			list.add("Ballistic missile (arcing)");
		}
		if(this == ModItems.slbm_martin_3) {
			list.add("Nuclear warhead");
			list.add("Ballistic missile (arcing)");
		}
		if(this == ModItems.slbm_pegasus_1) {
			list.add("Conventional warhead");
			list.add("Ballistic missile (high-range)");
		}
		if(this == ModItems.slbm_pegasus_2) {
			list.add("Incendiary warhead");
			list.add("Ballistic missile (high-range)");
		}
		if(this == ModItems.slbm_pegasus_3) {
			list.add("Nuclear warhead");
			list.add("Ballistic missile (high-range)");
		}
		if(this == ModItems.slbm_spear_1) {
			list.add("Conventional warhead");
			list.add("Cruise missile");
		}
		if(this == ModItems.slbm_spear_2) {
			list.add("Incendiary warhead");
			list.add("Cruise missile");
		}
		if(this == ModItems.slbm_spear_3) {
			list.add("Nuclear warhead");
			list.add("Cruise missile");
		}
		if(this == ModItems.slbm_pepper_1) {
			list.add("Conventional warhead");
			list.add("Artillery Shell");
		}
		if(this == ModItems.slbm_pepper_2) {
			list.add("Incendiary warhead");
			list.add("Artillery Shell");
		}
		if(this == ModItems.slbm_pepper_3) {
			list.add("Nuclear warhead");
			list.add("Artillery Shell");
		}
		

		
		if(this == ModItems.sexlol) {
			list.add("go fuck yourself");
		}
		if(this == ModItems.coin) {
			list.add("Buy high, sell low");
		}
		if(this == ModItems.province_point) {
			list.add("Province currency");
			/*list.add("it's a literal bitcoin");
			list.add("");
			list.add("...");
			list.add("please don't hit me");*/
		}
	}
}
