package com.hfr.items;

import com.hfr.lib.RefStrings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModItems {
	
	public static void mainRegistry()
	{
		initializeItem();
		registerItem();
	}

	public static Item cassette;
	public static Item circuit;
	public static Item magnetron;
	public static Item coil;
	public static Item upgrade_radius;
	public static Item upgrade_health;
	public static Item upgrade_bedrock;

	public static void initializeItem()
	{			
		cassette = new ItemCassette().setUnlocalizedName("cassette").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":cassette");
		circuit = new Item().setUnlocalizedName("circuit").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":circuit");
		magnetron = new Item().setUnlocalizedName("magnetron").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":magnetron");
		coil = new Item().setUnlocalizedName("coil").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":coil");
		upgrade_radius = new ItemLore().setUnlocalizedName("upgrade_radius").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_radius");
		upgrade_health = new ItemLore().setUnlocalizedName("upgrade_health").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_health");
		upgrade_bedrock = new ItemLore().setUnlocalizedName("upgrade_bedrock").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_bedrock");
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(cassette, cassette.getUnlocalizedName());
		GameRegistry.registerItem(coil, coil.getUnlocalizedName());
		GameRegistry.registerItem(magnetron, magnetron.getUnlocalizedName());
		GameRegistry.registerItem(circuit, circuit.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_radius, upgrade_radius.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_health, upgrade_health.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_bedrock, upgrade_bedrock.getUnlocalizedName());
	}
}
