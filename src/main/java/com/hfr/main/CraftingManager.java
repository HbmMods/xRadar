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
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.box, 2), new Object[] { Blocks.chest });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.box, 2), new Object[] { Blocks.trapped_chest });

		GameRegistry.addRecipe(new ItemStack(Items.paper, 3), new Object[] { "##", '#', ModItems.part_sawdust });

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.barricade, 4), new Object[] { Blocks.sand, ModItems.part_rubber });
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.barricade, 6), new Object[] { Blocks.sand, ModItems.part_rubber, ModItems.part_sawdust });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_grainmill, 1), new Object[] { "WWW", "BWB", "BMB", 'W', ModItems.components_wood, 'M', ModItems.components_mechanical, 'B', Blocks.cobblestone });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_blastfurnace, 1), new Object[] { "BSB", "BSB", "BFB", 'S', ModItems.components_scaffold, 'F', Blocks.furnace, 'B', Blocks.stonebrick });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_foundry, 1), new Object[] { "MBM", "SFS", "SDS", 'M', ModItems.components_mechanical, 'B', Items.bucket, 'S', ModItems.components_steel, 'F', Blocks.furnace, 'D', new ItemStack(Blocks.stone_slab, 1, 0) });
//allah		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_sawmill, 1), new Object[] { "MSM", "BFB", "WWW", 'M', ModItems.components_mechanical, 'S', ModItems.ingot_tap_steel, 'B', com.LordWeeder.EconomyPlus.items.ModItems.sawbladeSteel, 'F', ModItems.components_scaffold, 'W', ModItems.components_wood });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_wood), new Object[] { "WSW", "WSW", "WSW", 'W', "logWood", 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_scaffold), new Object[] { "IBI", "ISI", "IBI", 'I', "ingotIron", 'B', Blocks.iron_bars, 'S', Blocks.stone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_steel), new Object[] { "IBI", "ISI", "IBI", 'I', ModItems.ingot_tap_steel, 'B', "ingotIron", 'S', ModItems.components_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_mechanical), new Object[] { "FIF", "IGI", "FIF", 'I', "ingotIron", 'F', Items.flint, 'G', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_electronics), new Object[] { "BRD", "RGR", "DRB", 'R', "dustRedstone", 'G', "ingotGold", 'B', Blocks.stone_button, 'D', Items.repeater }));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.can, 24), new Object[] { "S S", " S ", 'S', ModItems.ingot_tap_steel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canned_spam, 1), new Object[] { Items.fish, ModItems.can });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.canned_jizz, 1), new Object[] { Items.slime_ball, ModItems.can });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.miner_supplies, 4), new Object[] { Items.iron_helmet, Items.iron_pickaxe, Items.gunpowder, Items.bread });
		GameRegistry.addRecipe(new ItemStack(ModItems.canary), new Object[] { "NNN", "NEN", "NNN", 'N', Items.gold_nugget, 'E', Items.egg });
		GameRegistry.addRecipe(new ItemStack(ModItems.clowder_banner), new Object[] { "SS", "WS", "WS", 'W', Blocks.wool, 'S', Items.stick });

//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_revolver, 1), new Object[] { "BSR", "  W", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick });
//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_singleshot, 1), new Object[] { "BGS", "WRW", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear });
//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_smg, 1), new Object[] { "BGS", "RSW", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear });
//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_repeating, 1), new Object[] { "BGP", "RWG", 'B', ModItems.part_rifled_barrel, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear, 'P', ModItems.part_plate });
//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_shotgun, 1), new Object[] { "BGS", "RPW", 'B', ModItems.part_smoothbore_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear, 'P', ModItems.part_plate });
//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_auto, 1), new Object[] { "BGS", "RSG", " PW", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'W', Items.stick, 'G', ModItems.part_gear, 'P', ModItems.part_plate });
//		GameRegistry.addRecipe(new ItemStack(ModItems.kit_hmg, 1), new Object[] { "RGS", "BFP", "RGS", 'B', ModItems.part_rifled_barrel, 'S', ModItems.part_spring, 'R', ModItems.part_rod, 'G', ModItems.part_gear, 'P', ModItems.part_plate, 'F', ModItems.part_frame });
//
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_plating_2, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_plating_1 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_plating_3, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_plating_2 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_hull_2, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_hull_1 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_hull_3, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_hull_2 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_mechanism_2, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_mechanism_1 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_mechanism_3, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_mechanism_2 });
		GameRegistry.addRecipe(new ItemStack(ModItems.part_rubber, 1), new Object[] { " A ", "AAA", " A ", 'A', ModItems.part_raw_rubber });

//		GameRegistry.addRecipe(new ItemStack(ModItems.part_cannon_1, 1), new Object[] { "A  ", " A ", "  A", 'A', ModItems.part_plating_1 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_cannon_2, 1), new Object[] { "A  ", " A ", "  A", 'A', ModItems.part_plating_2 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_cannon_3, 1), new Object[] { "A  ", " A ", "  A", 'A', ModItems.part_plating_3 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_chassis_1, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_plating_1 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_chassis_2, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_plating_2 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_chassis_3, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_plating_3 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_ship_1, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_hull_1 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_ship_2, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_hull_2 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_ship_3, 1), new Object[] { "AAA", "A A", "AAA", 'A', ModItems.part_hull_3 });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_wheel, 1), new Object[] { " B ", "BAB", " B ", 'A', ModItems.part_steel_wheel, 'B', ModItems.part_rubber });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_treads, 1), new Object[] { "BBB", "AAA", "BBB", 'A', ModItems.part_steel_wheel, 'B', ModItems.part_track });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_engine_1, 1), new Object[] { "AGG", "AMM", "ASS", 'A', ModItems.part_plating_1, 'G', ModItems.part_grate, 'M', ModItems.part_mechanism_1, 'S', ModItems.part_suspension });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_engine_2, 1), new Object[] { "AGG", "AMM", "ASS", 'A', ModItems.part_plating_2, 'G', ModItems.part_grate, 'M', ModItems.part_mechanism_2, 'S', ModItems.part_suspension });
//		GameRegistry.addRecipe(new ItemStack(ModItems.part_engine_3, 1), new Object[] { "AGG", "AMM", "ASS", 'A', ModItems.part_plating_3, 'G', ModItems.part_grate, 'M', ModItems.part_mechanism_3, 'S', ModItems.part_suspension });
}
	
	public static void AddSmeltingRec() {

		GameRegistry.addSmelting(ModItems.flour, new ItemStack(Items.bread), 0.5F);
		GameRegistry.addSmelting(Items.bone, new ItemStack(Items.slime_ball), 0.25F);
		GameRegistry.addSmelting(ModItems.mutton_raw, new ItemStack(ModItems.mutton_cooked), 0.5F);
		GameRegistry.addSmelting(ModItems.squid_raw, new ItemStack(ModItems.squid_cooked), 0.5F);
		GameRegistry.addSmelting(ModItems.part_rubber_drop, new ItemStack(ModItems.part_raw_rubber), 0.5F);
	}
}
