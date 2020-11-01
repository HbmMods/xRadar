package com.hfr.main;

import com.hfr.blocks.ModBlocks;
import com.hfr.items.ItemCassette.TrackType;
import com.hfr.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CraftingManager {
	
	public static void mainRegistry()
	{
		AddCraftingRec();
		AddSmeltingRec();
	}

	public static void AddCraftingRec()
	{
		for(int i = 1; i < TrackType.values().length; i++) {
			int next = i + 1;
			
			if(next >= TrackType.values().length)
				next = 1;
			
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cassette, 1, next), new Object[] { new ItemStack(ModItems.cassette, 1, i) });
		}

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.machine_tank), new Object[] { ModBlocks.machine_tank });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.box, 2), new Object[] { Blocks.chest });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.box, 2), new Object[] { Blocks.trapped_chest });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_boron, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_boron });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_graphite, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_graphite });
		GameRegistry.addRecipe(new ItemStack(ModItems.rice_bag, 1), new Object[] { "###", "###", "###", '#', ModItems.rice });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_boron, 9), new Object[] { "#", '#', ModBlocks.block_boron });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_graphite, 9), new Object[] { "#", '#', ModBlocks.block_graphite });
		GameRegistry.addRecipe(new ItemStack(ModItems.rice, 9), new Object[] { "#", '#', ModItems.rice_bag });
		GameRegistry.addRecipe(new ItemStack(Items.paper, 3), new Object[] { "##", '#', ModItems.part_sawdust });

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.barricade, 4), new Object[] { Blocks.sand, ModItems.part_rubber });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.barricade, 6), new Object[] { Blocks.sand, ModItems.part_rubber, ModItems.part_sawdust });

		GameRegistry.addRecipe(new ItemStack(ModItems.graphene_vest, 1), new Object[] { "# #", "###", "###", '#', ModItems.ingot_graphene });

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "ISI", "NCN", "ISI", 'N', Blocks.noteblock, 'C', ModItems.components_electronics, 'S', ModItems.components_scaffold, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_grainmill, 1), new Object[] { "WWW", "BWB", "BMB", 'W', ModItems.components_wood, 'M', ModItems.components_mechanical, 'B', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_blastfurnace, 1), new Object[] { "BSB", "BSB", "BFB", 'S', ModItems.components_scaffold, 'F', Blocks.furnace, 'B', Blocks.stonebrick });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_coalmine, 1), new Object[] { "SSM", "SMS", "BCB", 'S', ModItems.components_steel, 'M', ModItems.components_mechanical, 'B', Blocks.stone, 'C', Items.minecart });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_waterwheel, 1), new Object[] { "BSB", "SMS", "BSB", 'B', ModItems.components_wood, 'S', Items.stick, 'M', ModItems.components_mechanical });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_diesel, 1), new Object[] { "MPM", "MSM", "CFC", 'M', ModItems.components_mechanical, 'P', Blocks.piston, 'S', ModBlocks.machine_coalgen, 'C', ModItems.components_electronics, 'F', ModItems.components_steel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_foundry, 1), new Object[] { "MBM", "SFS", "SDS", 'M', ModItems.components_mechanical, 'B', Items.bucket, 'S', ModItems.components_steel, 'F', Blocks.furnace, 'D', new ItemStack(Blocks.stone_slab, 1, 0) });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_sawmill, 1), new Object[] { "MSM", "BFB", "WWW", 'M', ModItems.components_mechanical, 'S', ModItems.ingot_steel, 'B', ModItems.part_sawblade, 'F', ModItems.components_scaffold, 'W', ModItems.components_wood });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_efurnace, 1), new Object[] { "CGC", "PFP", "RRR", 'C', ModItems.components_electronics, 'G', ModItems.part_grate, 'P', ModItems.part_plating_1, 'F', Blocks.furnace, 'R', ModItems.part_rod });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_wood), new Object[] { "WSW", "WSW", "WSW", 'W', "logWood", 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_scaffold), new Object[] { "IBI", "ISI", "IBI", 'I', "ingotIron", 'B', Blocks.iron_bars, 'S', Blocks.stone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_steel), new Object[] { "IBI", "ISI", "IBI", 'I', ModItems.ingot_steel, 'B', "ingotIron", 'S', ModItems.components_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_mechanical), new Object[] { "FIF", "IGI", "FIF", 'I', "ingotIron", 'F', Items.flint, 'G', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_electronics), new Object[] { "BRD", "RGR", "DRB", 'R', "dustRedstone", 'G', "ingotGold", 'B', Blocks.stone_button, 'D', Items.repeater }));
		GameRegistry.addRecipe(new ItemStack(ModItems.components_plating), new Object[] { "GSG", "SBS", "GSG", 'G', Items.gold_nugget, 'S', ModItems.ingot_steel, 'B', ModItems.ingot_boron });
		
		GameRegistry.addRecipe(new ItemStack(ModItems.can, 24), new Object[] { "S S", " S ", 'S', ModItems.ingot_steel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canned_spam, 1), new Object[] { Items.fish, ModItems.can });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canned_jizz, 1), new Object[] { Items.slime_ball, ModItems.can });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.miner_supplies, 4), new Object[] { Items.iron_helmet, Items.iron_pickaxe, Items.gunpowder, Items.bread });
		GameRegistry.addRecipe(new ItemStack(ModItems.canary), new Object[] { "NNN", "NEN", "NNN", 'N', Items.gold_nugget, 'E', Items.egg });
		GameRegistry.addRecipe(new ItemStack(ModItems.clowder_banner), new Object[] { "SS", "WS", "WS", 'W', Blocks.wool, 'S', Items.stick });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_coalgen, 1), new Object[] { " C ", "MSM", " F ", 'C', ModItems.components_electronics, 'M', ModItems.components_mechanical, 'S', ModItems.components_scaffold, 'F', Blocks.furnace });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_battery, 1), new Object[] { "RRR", "BCB", "BSB", 'R', Items.redstone, 'B', ModItems.battery_rc, 'C', ModItems.components_electronics, 'S', ModItems.components_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_rc, 1), new Object[] { "GI", "CI", "RI", 'G', Items.gold_ingot, 'I', Items.iron_ingot, 'C', Items.coal, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_rc, 1), new Object[] { "GI", "RI", "CI", 'G', Items.gold_ingot, 'I', Items.iron_ingot, 'C', Items.coal, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(ModItems.battery_rc_2, 1), new Object[] { "RCR", "RBR", "RBR", 'R', Items.redstone, 'C', ModItems.components_electronics, 'B', ModBlocks.machine_battery });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.rbmk_element, 1), new Object[] { "PSP", "PSP", "PSP", 'P', ModItems.components_plating, 'S', ModItems.components_steel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.rbmk_rods, 1), new Object[] { "CBC", "PBP", "MSM", 'C', ModItems.components_electronics, 'B', ModItems.ingot_boron, 'P', ModItems.components_plating, 'S', ModItems.components_steel, 'M', ModItems.components_mechanical });

		GameRegistry.addRecipe(new ItemStack(ModItems.kit_revolver, 1), new Object[] { "BSR", "  W", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick });
		GameRegistry.addRecipe(new ItemStack(ModItems.kit_singleshot, 1), new Object[] { "BGS", "WRW", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear });
		GameRegistry.addRecipe(new ItemStack(ModItems.kit_smg, 1), new Object[] { "BGS", "RSW", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear });
		GameRegistry.addRecipe(new ItemStack(ModItems.kit_repeating, 1), new Object[] { "BGP", "RWG", 'B', ModItems.part_rifled_barrel, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear, 'P', ModItems.part_plate });
		GameRegistry.addRecipe(new ItemStack(ModItems.kit_shotgun, 1), new Object[] { "BGS", "RPW", 'B', ModItems.part_smoothbore_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear, 'P', ModItems.part_plate });
		GameRegistry.addRecipe(new ItemStack(ModItems.kit_auto, 1), new Object[] { "BGS", "RSG", " PW", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear, 'P', ModItems.part_plate });
		GameRegistry.addRecipe(new ItemStack(ModItems.kit_hmg, 1), new Object[] { "RGS", "BFP", "RGS", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'G', ModItems.part_gear, 'P', ModItems.part_plate, 'F', ModItems.part_frame });

		GameRegistry.addRecipe(new ItemStack(ModItems.part_plating_2, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_plating_1 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_plating_3, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_plating_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_hull_2, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_hull_1 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_hull_3, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_hull_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_mechanism_2, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_mechanism_1 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_mechanism_3, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_mechanism_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_rubber, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_raw_rubber });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_rubber, 4), new Object[] { " A ", "ABA", " A ", 'A', ModItems.part_raw_rubber, 'B', ModItems.gas_petroleum });

		GameRegistry.addRecipe(new ItemStack(ModItems.part_cannon_1, 1), new Object[] { "A  ", " A ", "  A", 'A', ModItems.part_plating_1 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_cannon_2, 1), new Object[] { "A  ", " A ", "  A", 'A', ModItems.part_plating_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_cannon_3, 1), new Object[] { "A  ", " A ", "  A", 'A', ModItems.part_plating_3 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_chassis_1, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_plating_1 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_chassis_2, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_plating_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_chassis_3, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_plating_3 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_ship_1, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_hull_1 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_ship_2, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_hull_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_ship_3, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_hull_3 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_wheel, 1), new Object[] { " B ", "BAB", " B ", 'A', ModItems.part_steel_wheel, 'B', ModItems.part_rubber });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_treads, 1), new Object[] { "BBB", "AAA", "BBB", 'A', ModItems.part_steel_wheel, 'B', ModItems.part_track });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_engine_1, 1), new Object[] { "AGG", "AMM", "ASS", 'A', ModItems.part_plating_1, 'G', ModItems.part_grate, 'M', ModItems.part_mechanism_1, 'S', ModItems.part_suspension });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_engine_2, 1), new Object[] { "AGG", "AMM", "ASS", 'A', ModItems.part_plating_2, 'G', ModItems.part_grate, 'M', ModItems.part_mechanism_2, 'S', ModItems.part_suspension });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_engine_3, 1), new Object[] { "AGG", "AMM", "ASS", 'A', ModItems.part_plating_3, 'G', ModItems.part_grate, 'M', ModItems.part_mechanism_3, 'S', ModItems.part_suspension });
}
	
	public static void AddSmeltingRec() {

		GameRegistry.addSmelting(ModItems.flour, new ItemStack(Items.bread), 0.5F);
		GameRegistry.addSmelting(Items.bone, new ItemStack(Items.slime_ball), 0.25F);
		GameRegistry.addSmelting(ModBlocks.ore_boron, new ItemStack(ModItems.ingot_boron), 3F);
		GameRegistry.addSmelting(Blocks.coal_block, new ItemStack(ModItems.ingot_graphite), 3F);
		GameRegistry.addSmelting(ModItems.rice, new ItemStack(ModItems.rice_sticky), 0.5F);
		GameRegistry.addSmelting(ModItems.mutton_raw, new ItemStack(ModItems.mutton_cooked), 0.5F);
		GameRegistry.addSmelting(ModItems.squid_raw, new ItemStack(ModItems.squid_cooked), 0.5F);
		GameRegistry.addSmelting(ModItems.part_rubber_drop, new ItemStack(ModItems.part_raw_rubber), 0.5F);
	}
}
