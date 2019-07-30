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
import net.minecraftforge.common.util.EnumHelper;
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
	public static Item uranium;
	public static Item plutonium;
	public static Item upgrade_radius;
	public static Item upgrade_health;
	public static Item upgrade_bedrock;

	public static Item canister_empty;
	public static Item canister_oil;
	public static Item canister_petroil;
	public static Item canister_diesel;
	public static Item canister_kerosene;
	public static Item gas_empty;
	public static Item gas_natural;
	public static Item gas_petroleum;

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
	public static Item missile_nuclear;

	public static Item drum;
	public static Item charge_naval;
	public static Item charge_railgun;
	public static Item charge_bfg;

	public static Item hammer;
	public static Item wand;
	public static Item battery;
	public static Item oil_detector;
	public static Item gas_mask;
	
	public static ArmorMaterial matGasMask = EnumHelper.addArmorMaterial("GASMASK", 10, new int[] {0, 0, 0, 0}, 0);

	public static void initializeItem()
	{			
		cassette = new ItemCassette().setUnlocalizedName("cassette").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":cassette");
		circuit = new Item().setUnlocalizedName("circuit").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":circuit");
		magnetron = new Item().setUnlocalizedName("magnetron").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":magnetron");
		coil = new Item().setUnlocalizedName("coil").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":coil");
		uranium = new Item().setUnlocalizedName("uranium").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":uranium");
		plutonium = new Item().setUnlocalizedName("plutonium").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":plutonium");
		upgrade_radius = new ItemLore().setUnlocalizedName("upgrade_radius").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_radius");
		upgrade_health = new ItemLore().setUnlocalizedName("upgrade_health").setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_health");
		upgrade_bedrock = new ItemLore().setUnlocalizedName("upgrade_bedrock").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":upgrade_bedrock");

		canister_empty = new ItemLore().setUnlocalizedName("canister_empty").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":canister_empty");
		canister_oil = new ItemLore().setUnlocalizedName("canister_oil").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":canister_oil");
		canister_petroil = new ItemLore().setUnlocalizedName("canister_petroil").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":canister_petroil");
		canister_diesel = new ItemLore().setUnlocalizedName("canister_diesel").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":canister_diesel");
		canister_kerosene = new ItemLore().setUnlocalizedName("canister_kerosene").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":canister_kerosene");
		gas_empty = new ItemLore().setUnlocalizedName("gas_empty").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":gas_empty");
		gas_natural = new ItemLore().setUnlocalizedName("gas_natural").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":gas_natural");
		gas_petroleum = new ItemLore().setUnlocalizedName("gas_petroleum").setCreativeTab(CreativeTabs.tabMaterials).setTextureName(RefStrings.MODID + ":gas_petroleum");

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
		missile_nuclear = new Item().setUnlocalizedName("missile_nuclear").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":missile_nuclear");
		
		drum = new ItemLore().setUnlocalizedName("drum").setMaxStackSize(3).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":drum");
		charge_naval = new ItemLore().setUnlocalizedName("charge_naval").setMaxStackSize(3).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":charge_naval");
		charge_railgun = new ItemLore().setUnlocalizedName("charge_railgun").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":charge_railgun");
		charge_bfg = new ItemLore().setUnlocalizedName("charge_bfg").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":charge_bfg");

		hammer = new ItemHammer().setUnlocalizedName("hammer").setMaxStackSize(1).setFull3D().setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":hammer");
		wand = new ItemWand().setUnlocalizedName("wand").setMaxStackSize(1).setFull3D().setCreativeTab(CreativeTabs.tabTools).setTextureName(RefStrings.MODID + ":wand");
		battery = new ItemLore().setUnlocalizedName("battery").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabRedstone).setTextureName(RefStrings.MODID + ":battery");
		oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabTools).setTextureName(RefStrings.MODID + ":oil_detector");
		gas_mask = new ItemGasMask(matGasMask, 5, 0).setUnlocalizedName("gas_mask").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":gas_mask");
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(hammer, hammer.getUnlocalizedName());
		GameRegistry.registerItem(wand, wand.getUnlocalizedName());
		GameRegistry.registerItem(battery, battery.getUnlocalizedName());
		GameRegistry.registerItem(oil_detector, oil_detector.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask, gas_mask.getUnlocalizedName());
		
		GameRegistry.registerItem(cassette, cassette.getUnlocalizedName());
		GameRegistry.registerItem(coil, coil.getUnlocalizedName());
		GameRegistry.registerItem(magnetron, magnetron.getUnlocalizedName());
		GameRegistry.registerItem(circuit, circuit.getUnlocalizedName());
		GameRegistry.registerItem(uranium, uranium.getUnlocalizedName());
		GameRegistry.registerItem(plutonium, plutonium.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_radius, upgrade_radius.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_health, upgrade_health.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_bedrock, upgrade_bedrock.getUnlocalizedName());

		GameRegistry.registerItem(canister_empty, canister_empty.getUnlocalizedName());
		GameRegistry.registerItem(canister_oil, canister_oil.getUnlocalizedName());
		GameRegistry.registerItem(canister_petroil, canister_petroil.getUnlocalizedName());
		GameRegistry.registerItem(canister_diesel, canister_diesel.getUnlocalizedName());
		GameRegistry.registerItem(canister_kerosene, canister_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(gas_empty, gas_empty.getUnlocalizedName());
		GameRegistry.registerItem(gas_natural, gas_natural.getUnlocalizedName());
		GameRegistry.registerItem(gas_petroleum, gas_petroleum.getUnlocalizedName());

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
		GameRegistry.registerItem(missile_nuclear, missile_nuclear.getUnlocalizedName());

		GameRegistry.registerItem(drum, drum.getUnlocalizedName());
		GameRegistry.registerItem(charge_naval, charge_naval.getUnlocalizedName());
		GameRegistry.registerItem(charge_railgun, charge_railgun.getUnlocalizedName());
		GameRegistry.registerItem(charge_bfg, charge_bfg.getUnlocalizedName());
	}
}
