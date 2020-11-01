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

		if(field_150939_a == ModBlocks.hard_stone) {
			list.add("[Tier 1 Hard Wall]");
			list.add("Solid stone block");
			list.add("Great for castles!");
		}
		if(field_150939_a == ModBlocks.concrete) {
			list.add("[Tier 2 Hard Wall]");
			list.add("Hardened clay and sand \"concrete\"");
		}
		if(field_150939_a == ModBlocks.concrete_bricks) {
			list.add("[Tier 3 Hard Wall]");
			list.add("Hollow concrete-based bricks");
			list.add("Cheap and fancy");
		}
		if(field_150939_a == ModBlocks.concrete_flat) {
			list.add("[Tier 4 Hard Wall]");
			list.add("20th century concrete");
			list.add("Soviets love it!");
		}
		if(field_150939_a == ModBlocks.concrete_bolted) {
			list.add("[Tier 5 Hard Wall]");
			list.add("Concrete with rebar");
		}
		if(field_150939_a == ModBlocks.hard_mesh) {
			list.add("[Tier 6 Hard Wall]");
			list.add("Ultra-durable alloy mesh");
		}
		if(field_150939_a == ModBlocks.concrete_hazard) {
			list.add("[Bonus Hard Wall]");
			list.add("Modern concrete with industrial aesthetic");
		}
		if(field_150939_a == ModBlocks.concrete_rocks) {
			list.add("[Bonus Hard Wall]");
			list.add("Decorative concrete chunks");
		}
		if(field_150939_a == ModBlocks.barricade) {
			list.add("Can be placed in enemy chunks");
			list.add("if a conquest flag within 8 blocks");
		}

		if(field_150939_a == ModBlocks.uni_foundation) {
			list.add("Increases speed by 15%");
		}
		if(field_150939_a == ModBlocks.asphalt) {
			list.add("Foundation block");
			list.add("Increases speed by 25%");
		}
		if(field_150939_a == ModBlocks.box) {
			list.add("Crappy storage block");
			list.add("Rots after a while");
		}

		if(field_150939_a == ModBlocks.machine_net) {
			list.add("Requires sky access and 3 block deep water");
			list.add("Must be placed on the surface");
		}
		if(field_150939_a == ModBlocks.machine_uni) {
			list.add("Requires sky access and foundation");
			list.add("Generates research points over time");
			list.add("+0.1 prestige gen / hour");
		}
		if(field_150939_a == ModBlocks.machine_grainmill) {
			list.add("Requires sky access and foundation");
			list.add("Grinds wheat into flour");
			list.add("+0.1 prestige gen / hour");
		}
		if(field_150939_a == ModBlocks.machine_blastfurnace) {
			list.add("Requires foundation but no sky access");
			list.add("Smelts iron ingots and ore into steel");
			list.add("+0.1 prestige gen / hour");
		}
		if(field_150939_a == ModBlocks.machine_coalmine) {
			list.add("Requires sky access and foundation");
			list.add("Uses miners and supplies to generate coal");
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
		if(field_150939_a == ModBlocks.berlin_wall) {
			list.add("Comes with functional barbed wire");
		}

		if(field_150939_a == ModBlocks.med_tent) {
			list.add("Requires sky access and foundation");
			list.add("Generates healing aura");
			list.add("+0.1 prestige gen / hour");
		}
		if(field_150939_a == ModBlocks.tp_tent) {
			list.add("Requires sky access and foundation");
			list.add("Allows to create faction TP points nearby");
			list.add("+0.1 prestige gen / hour");
		}
		if(field_150939_a == ModBlocks.statue) {
			list.add("Requires sky access and foundation");
			list.add("Low-level prestige generator");
			list.add("+0.5 prestige gen / hour");
		}

		if(field_150939_a == ModBlocks.machine_coalgen) {
			list.add("Basic combusion generator");
			list.add("Production: 200 RF/t");
			list.add("Buffer: 240000 RF");
			list.add("Connection speed: 2000 RF/t");
			list.add("Connectors: all sides and bottom");
		}

		if(field_150939_a == ModBlocks.machine_factory) {
			list.add("Requires: 300 RF/t");
			list.add("Buffer: 30000 RF");
			list.add("Connection speed: 3000 RF/t");
			list.add("Connectors: lowest blocks on all sides");
		}

		if(field_150939_a == ModBlocks.machine_battery) {
			list.add("Collects energy, releases it when redstone is applied");
			list.add("Energy does not persist when broken");
			list.add("Buffer: 200000 RF");
			list.add("Connection speed: 2000 RF/t");
			list.add("Connectors: all sides, top and bottom");
		}

		if(field_150939_a == ModBlocks.machine_windmill) {
			list.add("Requires sky access and foundation");
			list.add("Generation speed: 500 RF/t");
			list.add("Connectors: visible knobs at the bottom");
			list.add("[plane-shredding functionality pending]");
		}

		if(field_150939_a == ModBlocks.machine_waterwheel) {
			list.add("Requires sky access and flowing water");
			list.add("Surrounding solid blocks obstruct the mill");
			list.add("Generation speed: 100 RF/t");
			list.add("Connectors: axis");
		}

		if(field_150939_a == ModBlocks.machine_diesel) {
			list.add("Burns diesel fuel");
			list.add("Requires: 10 mB diesel/t");
			list.add("Production: 1000 RF/t");
			list.add("Buffer: 100000 RF");
			list.add("Connection speed: 10000 RF/t");
			list.add("Connectors: all sides, top and bottom");
		}

		if(field_150939_a == ModBlocks.machine_turbine) {
			list.add("Converts steam into water and power");
			list.add("Buffer: 100000000 RF");
			list.add("Connection speed: 100000 RF/t");
			list.add("Connectors:");
			list.add("-Energy: back");
			list.add("-Steam: front");
			list.add("-Water: sides");
		}

		if(field_150939_a == ModBlocks.machine_efurnace) {
			list.add("Smelts items quickly");
			list.add("Requires: 500 RF/t");
			list.add("Buffer: 10000 RF");
			list.add("Connection speed: 1000 RF/t");
		}
	}

}
