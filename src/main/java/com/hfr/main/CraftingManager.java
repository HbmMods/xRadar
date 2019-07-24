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
		
		switch(MainRegistry.crafting) {
		case 0:
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_radar, 1), new Object[] { 
					"GGG", "IRI", "SBS", 
					'G', "ingotGold", 'I', "ingotIron", 'R', Items.repeater, 'S', Blocks.cobblestone, 'B', Blocks.redstone_block }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_forcefield, 1), new Object[] { 
					"GRG", "IOI", "IOI", 
					'G', "ingotGold", 'I', "ingotIron", 'O', Blocks.obsidian, 'R', Blocks.redstone_block }));

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_radius, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', "ingotGold" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_health, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', Blocks.redstone_block }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_bedrock, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', Blocks.diamond_block }));
			break;
		case 1: 
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil, 1), new Object[] { "GIG", "RIR", "GIG", 'G', "ingotGold", 'R', "dustRedstone", 'I', "ingotIron" }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit, 1), new Object[] { "RLG", "LTL", "GLR", 'G', Items.glowstone_dust, 'R', "dustRedstone", 'L', "gemLapis", 'T', Items.repeater }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_radar, 1), new Object[] { 
					"GGG", "IRI", "SBS", 
					'G', ModItems.coil, 'I', "ingotIron", 'R', ModItems.circuit, 'S', Blocks.redstone_block, 'B', Blocks.obsidian }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_forcefield, 1), new Object[] { 
					"GCG", "IOI", "ROR", 
					'G', "ingotGold", 'I', "ingotIron", 'O', Blocks.obsidian, 'R', Blocks.redstone_block, 'C', ModItems.circuit }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_radius, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', "gemDiamond" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_health, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', ModItems.coil }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_bedrock, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', Items.nether_star }));
			break;
		case 2: 
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.coil, 1), new Object[] { "GIG", "RIR", "GIG", 'G', "ingotGold", 'R', "dustRedstone", 'I', "ingotIron" }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.circuit, 1), new Object[] { "RLG", "LTL", "GLR", 'G', Items.glowstone_dust, 'R', "dustRedstone", 'L', "gemLapis", 'T', Items.repeater }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.magnetron, 1), new Object[] { "CCC", "IDI", "CRC", 'C', ModItems.coil, 'I', "ingotIron", 'D', Items.diamond, 'R', Blocks.redstone_block }));
			
			GameRegistry.addRecipe(new ItemStack(ModBlocks.machine_radar, 1), new Object[] { 
					"GGG", "IRI", "SBS", 
					'G', ModItems.magnetron, 'I', ModItems.circuit, 'R', Blocks.glowstone, 'S', Blocks.obsidian, 'B', Blocks.redstone_block });
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_forcefield, 1), new Object[] { 
					"CMC", "MOM", "MRM", 
					'O', Blocks.obsidian, 'R', Blocks.redstone_block, 'C', ModItems.circuit, 'M', ModItems.magnetron }));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_radius, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', ModItems.magnetron }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_health, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', "dustRedstone", '#', ModItems.circuit }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.upgrade_bedrock, 1), new Object[] { 
					"IRI", "R#R", "IRI", 
					'I', "ingotIron", 'R', ModItems.upgrade_health, '#', Items.nether_star }));
			break;
		default: break;
		}

		if(MainRegistry.crafting >= 0 && MainRegistry.crafting < 3) {
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "NIN", "IJI", "NRN", 'N', Blocks.noteblock, 'J', Blocks.jukebox, 'R', "dustRedstone", 'I', "ingotIron" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cassette, 1, 1), new Object[] { "INI", 'I', "ingotIron", 'N', Blocks.noteblock }));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.concrete, 16), new Object[] { "GSG", "SWS", "GSG", 'G', Blocks.gravel, 'S', Blocks.sand, 'W', Items.water_bucket });
			GameRegistry.addRecipe(new ItemStack(ModBlocks.concrete, 16), new Object[] { "SGS", "GWG", "SGS", 'G', Blocks.gravel, 'S', Blocks.sand, 'W', Items.water_bucket });
			GameRegistry.addRecipe(new ItemStack(ModBlocks.concrete_bricks, 4), new Object[] { " C ", "CLC", " C ", 'C', ModBlocks.concrete, 'L', Items.clay_ball });

			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.vault_door, 1), new Object[] { "COC", "ORO", "COC", 'C', ModBlocks.concrete, 'O', Blocks.obsidian, 'R', "blockRedstone" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.seal_frame, 1), new Object[] { "I I", " R ", "I I", 'I', "ingotIron", 'R', "dustRedstone" }));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.seal_controller, 1), new Object[] { "I I", " R ", "I I", 'I', ModBlocks.seal_frame, 'R', "dustRedstone" }));
			
			for(int i = 1; i < TrackType.values().length; i++) {
				int next = i + 1;
				
				if(next >= TrackType.values().length)
					next = 1;
				
				GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cassette, 1, next), new Object[] { new ItemStack(ModItems.cassette, 1, i) });
			}
		}
		
		//GameRegistry.addRecipe(new ItemStack(ModBlocks.debug), new Object[] { "DBB", "PBB", "I I", 'D', ModItems.canister_diesel, 'B', Blocks.iron_block, 'P', Blocks.piston, 'I', Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(ModItems.plutonium), new Object[] { "UUU", "URU", "UUU", 'U', ModItems.uranium, 'R', Items.redstone });
		GameRegistry.addRecipe(new ItemStack(ModItems.missile_nuclear), new Object[] { "PPP", "PMP", "PPP", 'P', ModItems.plutonium, 'M', ModItems.missile_incendiary_3 });
	}
	
	public static void AddSmeltingRec()
	{
	}
}
