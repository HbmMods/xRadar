package com.hfr.items;

import java.util.ArrayList;
import java.util.List;

import com.hfr.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;

public class ItemMace extends ItemPickaxe {
	
	protected ItemMace() {
		super(ToolMaterial.IRON);
	}

	public static final List<Block> interactOverride = new ArrayList() {{
		add(Blocks.chest);
		add(Blocks.trapped_chest);
		add(Blocks.dispenser);
		add(Blocks.dropper);
		add(Blocks.hopper);
		add(ModBlocks.machine_derrick);
		add(ModBlocks.machine_refinery);
		add(ModBlocks.machine_tank);
		add(ModBlocks.machine_radar);
		add(ModBlocks.machine_emp);
		add(ModBlocks.machine_uni);
		add(ModBlocks.rbmk_element);
		add(ModBlocks.rbmk_rods);

		add(ModBlocks.clowder_flag);
	}};

	public static final List<Block> breakOverride = new ArrayList() {{
		add(Blocks.chest);
		add(Blocks.trapped_chest);
		add(Blocks.dispenser);
		add(Blocks.dropper);
		add(Blocks.hopper);
		add(ModBlocks.machine_derrick);
		add(ModBlocks.machine_refinery);
		add(ModBlocks.machine_tank);
		add(ModBlocks.machine_radar);
		add(ModBlocks.machine_emp);
		add(ModBlocks.machine_uni);
		add(ModBlocks.rbmk_element);
		add(ModBlocks.rbmk_rods);
	}};

}
