package com.hfr.items;

import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

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
	public static Item uranium_fuel;
	public static Item uranium_depleted;
	public static Item plutonium;
	public static Item americium;
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

	public static Item farmer;

	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item detonator;
	public static final int guiID_desingator = 99;
	public static final int guiID_slbm = 100;

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

	public static Item missile_devon_1;
	public static Item missile_devon_2;
	public static Item missile_devon_3;

	public static Item slbm_martin_1;
	public static Item slbm_martin_2;
	public static Item slbm_martin_3;
	public static Item slbm_pegasus_1;
	public static Item slbm_pegasus_2;
	public static Item slbm_pegasus_3;
	public static Item slbm_spear_1;
	public static Item slbm_spear_2;
	public static Item slbm_spear_3;
	public static Item slbm_pepper_1;
	public static Item slbm_pepper_2;
	public static Item slbm_pepper_3;

	public static Item drum;
	public static Item charge_naval;
	public static Item charge_railgun;
	public static Item charge_bfg;

	public static Item components_wood;
	public static Item components_scaffold;
	public static Item components_mechanical;
	public static Item components_electronics;
	
	public static Item tidal_energy;
	public static Item whale_meat;
	public static Item science;
	public static Item science_2;
	public static Item science_3;
	public static Item science_4;
	public static Item science_5;
	public static Item science_6;
	public static Item coin;
	public static Item wrench;
	public static Item province_point;
	public static Item flour;
	public static Item ingot_steel;

	public static Item hammer;
	public static Item wand;
	public static Item battery;
	public static Item oil_detector;
	public static Item gas_mask;
	public static Item clowder_banner;
	public static Item grenade_gas;
	public static Item grenade_nuclear;
	public static Item grenade_boxcar;
	public static Item hatter;

	public static Item mult_stoneage;
	public static Item mult_roman;
	public static Item mult_steel;
	public static Item mult_alloy;
	
	public static Item mace;
	public static Item clowder_map;
	public static Item flaregun;
	public static Item flare;
	public static Item pakker;
	public static Item pak_rocket;
	public static Item scanner;
	public static Item repair_1;
	public static Item repair_2;
	public static Item repair_3;

	public static Item sexlol;
	public static Item canned_spam;
	public static Item canned_jizz;

	public static Item debug;
	
	public static ArmorMaterial matGasMask = EnumHelper.addArmorMaterial("GASMASK", 10, new int[] {0, 0, 0, 0}, 0);

	public static void initializeItem()
	{			
		cassette = new ItemCassette().setUnlocalizedName("cassette").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":cassette");
		circuit = new Item().setUnlocalizedName("circuit").setCreativeTab(null).setTextureName(RefStrings.MODID + ":circuit");
		magnetron = new Item().setUnlocalizedName("magnetron").setCreativeTab(null).setTextureName(RefStrings.MODID + ":magnetron");
		coil = new Item().setUnlocalizedName("coil").setCreativeTab(null).setTextureName(RefStrings.MODID + ":coil");
		uranium = new Item().setUnlocalizedName("uranium").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":uranium");
		uranium_fuel = new ItemFuel().setUnlocalizedName("uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":uranium_fuel");
		uranium_depleted = new Item().setUnlocalizedName("uranium_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":uranium_depleted");
		plutonium = new Item().setUnlocalizedName("plutonium").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":plutonium");
		americium = new Item().setUnlocalizedName("americium").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":americium");
		upgrade_radius = new ItemLore().setUnlocalizedName("upgrade_radius").setMaxStackSize(16).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":upgrade_radius");
		upgrade_health = new ItemLore().setUnlocalizedName("upgrade_health").setMaxStackSize(16).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":upgrade_health");
		upgrade_bedrock = new ItemLore().setUnlocalizedName("upgrade_bedrock").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":upgrade_bedrock");

		canister_empty = new ItemLore().setUnlocalizedName("canister_empty").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canister_empty");
		canister_oil = new ItemLore().setUnlocalizedName("canister_oil").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canister_oil");
		canister_petroil = new ItemLore().setUnlocalizedName("canister_petroil").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canister_petroil");
		canister_diesel = new ItemLore().setUnlocalizedName("canister_diesel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canister_diesel");
		canister_kerosene = new ItemLore().setUnlocalizedName("canister_kerosene").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canister_kerosene");
		gas_empty = new ItemLore().setUnlocalizedName("gas_empty").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_empty");
		gas_natural = new ItemLore().setUnlocalizedName("gas_natural").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_natural");
		gas_petroleum = new ItemLore().setUnlocalizedName("gas_petroleum").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_petroleum");

		components_wood = new ItemLore().setUnlocalizedName("components_wood").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_wood");
		components_scaffold = new ItemLore().setUnlocalizedName("components_scaffold").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_scaffold");
		components_mechanical = new ItemLore().setUnlocalizedName("components_mechanical").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_mechanical");
		components_electronics = new ItemLore().setUnlocalizedName("components_electronics").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_electronics");
		
		farmer = new ItemContract().setUnlocalizedName("farmer").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":farmer");
		
		designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":designator");
		designator_range = new ItemDesingatorRange().setUnlocalizedName("designator_range").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":designator_range");
		designator_manual = new ItemDesingatorManual().setUnlocalizedName("designator_manual").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":designator_manual");
		detonator = new ItemDetonator().setUnlocalizedName("detonator").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":detonator");
		
		missile_he_1 = new ItemLore().setUnlocalizedName("missile_he_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_he_1");
		missile_he_2 = new Item().setUnlocalizedName("missile_he_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_he_2");
		missile_he_3 = new ItemLore().setUnlocalizedName("missile_he_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_he_3");
		missile_incendiary_1 = new ItemLore().setUnlocalizedName("missile_incendiary_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_incendiary_1");
		missile_incendiary_2 = new Item().setUnlocalizedName("missile_incendiary_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_incendiary_2");
		missile_incendiary_3 = new Item().setUnlocalizedName("missile_incendiary_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_incendiary_3");
		missile_emp = new ItemLore().setUnlocalizedName("missile_emp").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_emp");
		missile_ab = new ItemLore().setUnlocalizedName("missile_ab").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_ab");
		missile_decoy = new ItemLore().setUnlocalizedName("missile_decoy").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_decoy");
		missile_nuclear = new ItemLore().setUnlocalizedName("missile_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_nuclear");

		missile_devon_1 = new ItemLore().setUnlocalizedName("missile_devon_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_devon_1");
		missile_devon_2 = new ItemLore().setUnlocalizedName("missile_devon_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_devon_2");
		missile_devon_3 = new ItemLore().setUnlocalizedName("missile_devon_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_devon_3");

		slbm_martin_1 = new ItemLore().setUnlocalizedName("slbm_martin_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_martin_1");
		slbm_martin_2 = new ItemLore().setUnlocalizedName("slbm_martin_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_martin_2");
		slbm_martin_3 = new ItemLore().setUnlocalizedName("slbm_martin_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_martin_3");
		slbm_pegasus_1 = new ItemLore().setUnlocalizedName("slbm_pegasus_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_pegasus_1");
		slbm_pegasus_2 = new ItemLore().setUnlocalizedName("slbm_pegasus_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_pegasus_2");
		slbm_pegasus_3 = new ItemLore().setUnlocalizedName("slbm_pegasus_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_pegasus_3");
		slbm_spear_1 = new ItemLore().setUnlocalizedName("slbm_spear_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_spear_1");
		slbm_spear_2 = new ItemLore().setUnlocalizedName("slbm_spear_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_spear_2");
		slbm_spear_3 = new ItemLore().setUnlocalizedName("slbm_spear_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_spear_3");
		slbm_pepper_1 = new ItemLore().setUnlocalizedName("slbm_pepper_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_pepper_1");
		slbm_pepper_2 = new ItemLore().setUnlocalizedName("slbm_pepper_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_pepper_2");
		slbm_pepper_3 = new ItemLore().setUnlocalizedName("slbm_pepper_3").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":slbm_pepper_3");

		drum = new ItemLore().setUnlocalizedName("drum").setMaxStackSize(4).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":drum");
		charge_naval = new ItemLore().setUnlocalizedName("charge_naval").setMaxStackSize(2).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":charge_naval");
		charge_railgun = new ItemLore().setUnlocalizedName("charge_railgun").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":charge_railgun");
		charge_bfg = new ItemLore().setUnlocalizedName("charge_bfg").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":charge_bfg");
		
		tidal_energy = new ItemLore().setUnlocalizedName("tidal_energy").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":tidal_energy");
		whale_meat = new ItemMeat().setUnlocalizedName("whale_meat").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":whale_meat");
		science = new ItemLore().setUnlocalizedName("science").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":science");
		science_2 = new ItemLore().setUnlocalizedName("science_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":science_2");
		science_3 = new ItemLore().setUnlocalizedName("science_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":science_3");
		science_4 = new ItemLore().setUnlocalizedName("science_4").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":science_4");
		science_5 = new ItemLore().setUnlocalizedName("science_5").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":science_5");
		science_6 = new ItemLore().setUnlocalizedName("science_6").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":science_6");
		coin = new ItemLore().setUnlocalizedName("hfr_coin").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":coin");
		wrench = new ItemLore().setUnlocalizedName("hfr_wrench").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":wrench");
		province_point = new ItemLore().setUnlocalizedName("province_point").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":prestige");
		flour = new ItemLore().setUnlocalizedName("flour").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":flour");
		ingot_steel = new ItemLore().setUnlocalizedName("ingot_steel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":ingot_steel");

		hammer = new ItemHammer().setUnlocalizedName("hammer").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":hammer");
		wand = new ItemWand().setUnlocalizedName("wand").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":wand");
		battery = new ItemLore().setUnlocalizedName("battery").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":battery");
		oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":oil_detector");
		gas_mask = new ItemGasMask(matGasMask, 5, 0).setUnlocalizedName("gas_mask").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_mask");
		clowder_banner = new ItemBanner(matGasMask, 5, 3).setUnlocalizedName("clowder_banner").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":clowder_banner");
		grenade_gas = new ItemGrenade(4).setUnlocalizedName("grenade_gas").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":grenade_gas");
		grenade_nuclear = new ItemGrenade(7).setUnlocalizedName("grenade_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":grenade_nuclear");
		grenade_boxcar = new ItemGrenade(12).setUnlocalizedName("grenade_boxcar").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":grenade_boxcar");
		hatter = new ItemHatter().setUnlocalizedName("hatter").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":hat");

		mult_stoneage = new ItemMultitool().setUnlocalizedName("mult_stoneage").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_stoneage");
		mult_roman = new ItemMultitool().setUnlocalizedName("mult_roman").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_roman");
		mult_steel = new ItemMultitool().setUnlocalizedName("mult_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_steel");
		mult_alloy = new ItemMultitool().setUnlocalizedName("mult_alloy").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_alloy");
		
		mace = new ItemMace().setUnlocalizedName("mace").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mace");
		clowder_map = new ItemMap().setUnlocalizedName("clowder_map").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":clowder_map");
		flaregun = new ItemFlaregun().setUnlocalizedName("flaregun").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":debug");
		flare = new ItemLore().setUnlocalizedName("flare").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":flare");
		pakker = new ItemPakker().setUnlocalizedName("pakker").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":debug");
		pak_rocket = new ItemLore().setUnlocalizedName("pak_rocket").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":pak_rocket");
		scanner = new ItemScanner().setUnlocalizedName("scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":scanner");
		repair_1 = new ItemRepair(10).setUnlocalizedName("repair_1").setMaxStackSize(16).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":repair_1");
		repair_2 = new ItemRepair(25).setUnlocalizedName("repair_2").setMaxStackSize(16).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":repair_2");
		repair_3 = new ItemRepair(100).setUnlocalizedName("repair_3").setMaxStackSize(16).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":repair_3");
		
		sexlol = new ItemLore().setUnlocalizedName("sexlol").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":sexlol");
		canned_spam = new ItemModFood(5).setUnlocalizedName("canned_spam").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canned_spam");
		canned_jizz = new ItemModFood(10).setUnlocalizedName("canned_jizz").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canned_jizz");
		
		debug = new ItemDebug().setUnlocalizedName("debug").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":debug");
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(hammer, hammer.getUnlocalizedName());
		GameRegistry.registerItem(wand, wand.getUnlocalizedName());
		GameRegistry.registerItem(battery, battery.getUnlocalizedName());
		GameRegistry.registerItem(oil_detector, oil_detector.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask, gas_mask.getUnlocalizedName());
		GameRegistry.registerItem(clowder_banner, clowder_banner.getUnlocalizedName());
		
		GameRegistry.registerItem(grenade_gas, grenade_gas.getUnlocalizedName());
		GameRegistry.registerItem(grenade_nuclear, grenade_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(grenade_boxcar, grenade_boxcar.getUnlocalizedName());
		
		GameRegistry.registerItem(hatter, hatter.getUnlocalizedName());
		
		GameRegistry.registerItem(cassette, cassette.getUnlocalizedName());
		GameRegistry.registerItem(coil, coil.getUnlocalizedName());
		GameRegistry.registerItem(magnetron, magnetron.getUnlocalizedName());
		GameRegistry.registerItem(circuit, circuit.getUnlocalizedName());
		GameRegistry.registerItem(uranium, uranium.getUnlocalizedName());
		GameRegistry.registerItem(uranium_fuel, uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(uranium_depleted, uranium_depleted.getUnlocalizedName());
		GameRegistry.registerItem(plutonium, plutonium.getUnlocalizedName());
		GameRegistry.registerItem(americium, americium.getUnlocalizedName());
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

		GameRegistry.registerItem(components_wood, components_wood.getUnlocalizedName());
		GameRegistry.registerItem(components_scaffold, components_scaffold.getUnlocalizedName());
		GameRegistry.registerItem(components_mechanical, components_mechanical.getUnlocalizedName());
		GameRegistry.registerItem(components_electronics, components_electronics.getUnlocalizedName());

		GameRegistry.registerItem(tidal_energy, tidal_energy.getUnlocalizedName());
		GameRegistry.registerItem(whale_meat, whale_meat.getUnlocalizedName());
		GameRegistry.registerItem(science, science.getUnlocalizedName());
		GameRegistry.registerItem(science_2, science_2.getUnlocalizedName());
		GameRegistry.registerItem(science_3, science_3.getUnlocalizedName());
		GameRegistry.registerItem(science_4, science_4.getUnlocalizedName());
		GameRegistry.registerItem(science_5, science_5.getUnlocalizedName());
		GameRegistry.registerItem(science_6, science_6.getUnlocalizedName());
		GameRegistry.registerItem(coin, coin.getUnlocalizedName());
		GameRegistry.registerItem(wrench, wrench.getUnlocalizedName());
		GameRegistry.registerItem(province_point, province_point.getUnlocalizedName());
		GameRegistry.registerItem(flour, flour.getUnlocalizedName());
		GameRegistry.registerItem(ingot_steel, ingot_steel.getUnlocalizedName());

		GameRegistry.registerItem(farmer, farmer.getUnlocalizedName());

		GameRegistry.registerItem(mace, mace.getUnlocalizedName());
		GameRegistry.registerItem(clowder_map, clowder_map.getUnlocalizedName());
		GameRegistry.registerItem(scanner, scanner.getUnlocalizedName());
		GameRegistry.registerItem(repair_1, repair_1.getUnlocalizedName());
		GameRegistry.registerItem(repair_2, repair_2.getUnlocalizedName());
		GameRegistry.registerItem(repair_3, repair_3.getUnlocalizedName());
		GameRegistry.registerItem(flaregun, flaregun.getUnlocalizedName());
		GameRegistry.registerItem(flare, flare.getUnlocalizedName());
		GameRegistry.registerItem(pakker, pakker.getUnlocalizedName());
		GameRegistry.registerItem(pak_rocket, pak_rocket.getUnlocalizedName());

		GameRegistry.registerItem(mult_stoneage, mult_stoneage.getUnlocalizedName());
		GameRegistry.registerItem(mult_roman, mult_roman.getUnlocalizedName());
		GameRegistry.registerItem(mult_steel, mult_steel.getUnlocalizedName());
		GameRegistry.registerItem(mult_alloy, mult_alloy.getUnlocalizedName());
		
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

		GameRegistry.registerItem(missile_devon_1, missile_devon_1.getUnlocalizedName());
		GameRegistry.registerItem(missile_devon_2, missile_devon_2.getUnlocalizedName());
		GameRegistry.registerItem(missile_devon_3, missile_devon_3.getUnlocalizedName());

		GameRegistry.registerItem(slbm_martin_1, slbm_martin_1.getUnlocalizedName());
		GameRegistry.registerItem(slbm_martin_2, slbm_martin_2.getUnlocalizedName());
		GameRegistry.registerItem(slbm_martin_3, slbm_martin_3.getUnlocalizedName());
		GameRegistry.registerItem(slbm_pegasus_1, slbm_pegasus_1.getUnlocalizedName());
		GameRegistry.registerItem(slbm_pegasus_2, slbm_pegasus_2.getUnlocalizedName());
		GameRegistry.registerItem(slbm_pegasus_3, slbm_pegasus_3.getUnlocalizedName());
		GameRegistry.registerItem(slbm_spear_1, slbm_spear_1.getUnlocalizedName());
		GameRegistry.registerItem(slbm_spear_2, slbm_spear_2.getUnlocalizedName());
		GameRegistry.registerItem(slbm_spear_3, slbm_spear_3.getUnlocalizedName());
		GameRegistry.registerItem(slbm_pepper_1, slbm_pepper_1.getUnlocalizedName());
		GameRegistry.registerItem(slbm_pepper_2, slbm_pepper_2.getUnlocalizedName());
		GameRegistry.registerItem(slbm_pepper_3, slbm_pepper_3.getUnlocalizedName());

		GameRegistry.registerItem(drum, drum.getUnlocalizedName());
		GameRegistry.registerItem(charge_naval, charge_naval.getUnlocalizedName());
		GameRegistry.registerItem(charge_railgun, charge_railgun.getUnlocalizedName());
		GameRegistry.registerItem(charge_bfg, charge_bfg.getUnlocalizedName());

		GameRegistry.registerItem(sexlol, sexlol.getUnlocalizedName());
		GameRegistry.registerItem(canned_spam, canned_spam.getUnlocalizedName());
		GameRegistry.registerItem(canned_jizz, canned_jizz.getUnlocalizedName());
		
		GameRegistry.registerItem(debug, debug.getUnlocalizedName());
	}
}
