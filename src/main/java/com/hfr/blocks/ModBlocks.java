package com.hfr.blocks;

import com.hfr.items.ItemRadarBlock;
import com.hfr.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {
	
	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}

	public static Block concrete;
	public static Block concrete_bricks;

	public static Block seal_controller;
	public static Block seal_frame;
	public static Block seal_hatch;

	public static Block vault_door;
	public static Block vault_door_dummy;
	
	public static Block machine_radar;
	public static final int guiID_radar = 0;
	public static Block machine_siren;
	public static final int guiID_siren = 1;
	public static Block machine_forcefield;
	public static final int guiID_forcefield = 2;
	public static Block launch_pad;
	public static final int guiID_launchpad = 3;
	

	private static void initializeBlock() {
		
		concrete = new BlockGeneric(Material.rock).setBlockName("concrete").setHardness(5.0F).setResistance(1000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":concrete");
		concrete_bricks = new BlockGeneric(Material.rock).setBlockName("concrete_bricks").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":concrete_bricks");

		seal_controller = new BlockSeal(Material.rock).setBlockName("seal_controller").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":concrete");
		seal_frame = new BlockGeneric(Material.rock).setBlockName("seal_frame").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":seal_frame");
		seal_hatch = new BlockHatch(Material.rock).setBlockName("seal_hatch").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":seal_hatch");

		vault_door = new VaultDoor(Material.rock).setBlockName("vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(CreativeTabs.tabBlock).setBlockTextureName(RefStrings.MODID + ":vault_door");
		vault_door_dummy = new DummyBlockVault(Material.rock).setBlockName("vault_door_dummy").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":concrete");
		
		machine_radar = new MachineRadar(Material.iron).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_radar");
		machine_siren = new MachineSiren(Material.iron).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_siren");
		launch_pad = new LaunchPad(Material.iron).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		machine_forcefield = new MachineForceField(Material.iron).setBlockName("machine_forcefield").setLightLevel(1.0F).setHardness(5.0F).setResistance(100.0F).setCreativeTab(CreativeTabs.tabRedstone).setBlockTextureName(RefStrings.MODID + ":machine_forcefield");
		
	}

	private static void registerBlock() {

		GameRegistry.registerBlock(concrete, concrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_bricks, concrete_bricks.getUnlocalizedName());

		GameRegistry.registerBlock(seal_controller, seal_controller.getUnlocalizedName());
		GameRegistry.registerBlock(seal_frame, seal_frame.getUnlocalizedName());
		GameRegistry.registerBlock(seal_hatch, seal_hatch.getUnlocalizedName());
		
		GameRegistry.registerBlock(vault_door, vault_door.getUnlocalizedName());
		GameRegistry.registerBlock(vault_door_dummy, vault_door_dummy.getUnlocalizedName());
		
		GameRegistry.registerBlock(machine_radar, ItemRadarBlock.class, machine_radar.getUnlocalizedName());
		GameRegistry.registerBlock(machine_siren, machine_siren.getUnlocalizedName());
		GameRegistry.registerBlock(machine_forcefield, ItemRadarBlock.class, machine_forcefield.getUnlocalizedName());

		GameRegistry.registerBlock(launch_pad, ItemRadarBlock.class, launch_pad.getUnlocalizedName());
	}
}
