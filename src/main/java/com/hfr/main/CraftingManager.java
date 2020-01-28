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
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "ISI", "NCN", "ISI", 'N', Blocks.noteblock, 'C', ModItems.components_electronics, 'S', ModItems.components_scaffold, 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_grainmill, 1), new Object[] { "WWW", "BWB", "BMB", 'W', ModItems.components_wood, 'M', ModItems.components_mechanical, 'B', Blocks.cobblestone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_blastfurnace, 1), new Object[] { "BSB", "BSB", "BFB", 'S', ModItems.components_scaffold, 'F', Blocks.furnace, 'B', Blocks.stonebrick });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_coalmine, 1), new Object[] { "SSM", "SMS", "BCB", 'S', ModItems.components_steel, 'M', ModItems.components_mechanical, 'B', Blocks.stone, 'C', Items.minecart });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_waterwheel, 1), new Object[] { "BSB", "SMS", "BSB", 'B', ModItems.components_wood, 'S', Items.stick, 'M', ModItems.components_mechanical });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_diesel, 1), new Object[] { "MPM", "MSM", "CFC", 'M', ModItems.components_mechanical, 'P', Blocks.piston, 'S', ModBlocks.machine_coalgen, 'C', ModItems.components_electronics, 'F', ModItems.components_steel });
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_wood), new Object[] { "WSW", "WSW", "WSW", 'W', "logWood", 'S', Items.stick }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_scaffold), new Object[] { "IBI", "ISI", "IBI", 'I', "ingotIron", 'B', Blocks.iron_bars, 'S', Blocks.stone }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_steel), new Object[] { "IBI", "ISI", "IBI", 'I', ModItems.ingot_steel, 'B', "ingotIron", 'S', ModItems.components_scaffold }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_mechanical), new Object[] { "FIF", "IGI", "FIF", 'I', "ingotIron", 'F', Items.flint, 'G', "ingotGold" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.components_electronics), new Object[] { "BRD", "RGR", "DRB", 'R', "dustRedstone", 'G', "ingotGold", 'B', Blocks.stone_button, 'D', Items.repeater }));
		
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
	}
	
	public static void AddSmeltingRec() {

		GameRegistry.addSmelting(ModItems.flour, new ItemStack(Items.bread), 0.5F);
		GameRegistry.addSmelting(Items.bone, new ItemStack(Items.slime_ball), 0.25F);
	}
}
