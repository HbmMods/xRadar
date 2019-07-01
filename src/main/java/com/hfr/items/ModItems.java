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

	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item detonator;
	public static final int guiID_desingator = 99;

	public static Item missile_he_1;
	public static Item missile_he_2;
	public static Item missile_he_3;
	public static Item missile_incendiary_1;
	public static Item missile_incendiary_2;
	public static Item missile_incendiary_3;
	public static Item missile_emp;
	public static Item missile_ab;
	public static Item missile_decoy;

	public static Item hammer;

	public static void initializeItem()
	{			
		cassette = new ItemCassette().setUnlocalizedName("cassette").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":cassette");
		circuit = new Item().setUnlocalizedName("circuit").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":circuit");
		magnetron = new Item().setUnlocalizedName("magnetron").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":magnetron");
		coil = new Item().setUnlocalizedName("coil").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":coil");
		upgrade_radius = new ItemLore().setUnlocalizedName("upgrade_radius").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_radius");
		upgrade_health = new ItemLore().setUnlocalizedName("upgrade_health").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_health");
		upgrade_bedrock = new ItemLore().setUnlocalizedName("upgrade_bedrock").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_bedrock");

		designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":designator");
		designator_range = new ItemDesingatorRange().setUnlocalizedName("designator_range").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":designator_range");
		designator_manual = new ItemDesingatorManual().setUnlocalizedName("designator_manual").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":designator_manual");
		detonator = new ItemDetonator().setUnlocalizedName("detonator").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":detonator");
		
		missile_he_1 = new Item().setUnlocalizedName("missile_he_1").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_he_1");
		missile_he_2 = new Item().setUnlocalizedName("missile_he_2").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_he_2");
		missile_he_3 = new Item().setUnlocalizedName("missile_he_3").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_he_3");
		missile_incendiary_1 = new Item().setUnlocalizedName("missile_incendiary_1").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_incendiary_1");
		missile_incendiary_2 = new Item().setUnlocalizedName("missile_incendiary_2").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_incendiary_2");
		missile_incendiary_3 = new Item().setUnlocalizedName("missile_incendiary_3").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_incendiary_3");
		missile_emp = new Item().setUnlocalizedName("missile_emp").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_emp");
		missile_ab = new Item().setUnlocalizedName("missile_ab").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_ab");
		missile_decoy = new Item().setUnlocalizedName("missile_decoy").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_decoy");
		
		hammer = new ItemHammer().setUnlocalizedName("hammer").setMaxStackSize(1).setFull3D().setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":hammer");
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(hammer, hammer.getUnlocalizedName());
		
		GameRegistry.registerItem(cassette, cassette.getUnlocalizedName());
		GameRegistry.registerItem(coil, coil.getUnlocalizedName());
		GameRegistry.registerItem(magnetron, magnetron.getUnlocalizedName());
		GameRegistry.registerItem(circuit, circuit.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_radius, upgrade_radius.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_health, upgrade_health.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_bedrock, upgrade_bedrock.getUnlocalizedName());

		GameRegistry.registerItem(designator, designator.getUnlocalizedName());
		GameRegistry.registerItem(designator_range, designator_range.getUnlocalizedName());
		GameRegistry.registerItem(designator_manual, designator_manual.getUnlocalizedName());
		GameRegistry.registerItem(detonator, detonator.getUnlocalizedName());

		GameRegistry.registerItem(missile_he_1, missile_he_1.getUnlocalizedName());
		GameRegistry.registerItem(missile_he_2, missile_he_2.getUnlocalizedName());
		GameRegistry.registerItem(missile_he_3, missile_he_3.getUnlocalizedName());
		GameRegistry.registerItem(missile_incendiary_1, missile_incendiary_1.getUnlocalizedName());
		GameRegistry.registerItem(missile_incendiary_2, missile_incendiary_2.getUnlocalizedName());
		GameRegistry.registerItem(missile_incendiary_3, missile_incendiary_3.getUnlocalizedName());
		GameRegistry.registerItem(missile_emp, missile_emp.getUnlocalizedName());
		GameRegistry.registerItem(missile_ab, missile_ab.getUnlocalizedName());
		GameRegistry.registerItem(missile_decoy, missile_decoy.getUnlocalizedName());
	}
}
