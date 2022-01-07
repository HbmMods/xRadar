package com.hfr.items;

import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
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
	public static Item miner;
	public static Item miner_supplies;
	public static Item canary;

	public static Item rice;
	public static Item rice_seeds;
	public static Item rice_bag;
	public static Item rice_sticky;
	public static Item mutton_raw;
	public static Item mutton_cooked;
	public static Item squid_raw;
	public static Item squid_cooked;

	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item detonator;
	public static Item remote;
	public static final int guiID_desingator = 99;
	public static final int guiID_slbm = 100;

	public static Item missile_at_1;
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
	public static Item missile_cruise_at_1;

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
	public static Item charge_at;
	public static Item charge_railgun;
	public static Item charge_bfg;

	public static Item components_wood;
	public static Item components_scaffold;
	public static Item components_steel;
	public static Item components_mechanical;
	public static Item components_electronics;
	public static Item components_plating;

	public static Item part_rifled_barrel;
	public static Item part_smoothbore_barrel;
	public static Item part_plate;
	public static Item part_gear;
	public static Item part_rod;
	public static Item part_frame;
	public static Item part_spring;
	public static Item part_grate;
	public static Item part_suspension;
	public static Item part_plating_1;
	public static Item part_plating_2;
	public static Item part_plating_3;
	public static Item part_hull_1;
	public static Item part_hull_2;
	public static Item part_hull_3;
	public static Item part_mechanism_1;
	public static Item part_mechanism_2;
	public static Item part_mechanism_3;
	public static Item part_steel_wheel;
	public static Item part_wheel;
	public static Item part_sawblade;
	public static Item part_track;
	public static Item part_treads;
	public static Item part_cannon_1;
	public static Item part_cannon_2;
	public static Item part_cannon_3;
	public static Item part_chassis_1;
	public static Item part_chassis_2;
	public static Item part_chassis_3;
	public static Item part_engine_1;
	public static Item part_engine_2;
	public static Item part_engine_3;
	public static Item part_ship_1;
	public static Item part_ship_2;
	public static Item part_ship_3;
	public static Item part_rubber_drop;
	public static Item part_raw_rubber;
	public static Item part_rubber;
	public static Item part_sawdust;

	public static Item kit_singleshot;
	public static Item kit_repeating;
	public static Item kit_auto;
	public static Item kit_shotgun;
	public static Item kit_revolver;
	public static Item kit_smg;
	public static Item kit_hmg;

	public static Item ingot_boron;
	public static Item ingot_graphite;
	public static Item ingot_graphene;
	
	public static Item tidal_energy;
	public static Item whale_meat;
	public static Item science;
	public static Item science_2;
	public static Item science_3;
	public static Item science_4;
	public static Item science_5;
	public static Item science_6;
	public static Item cog;
	public static Item cog_2;
	public static Item cog_3;
	public static Item cog_4;
	public static Item cog_5;
	public static Item scroll;
	
	public static Item coin;
	public static Item wrench;
	public static Item province_point;
	public static Item flour;
	public static Item ingot_tap_steel;
	public static Item crate;

	public static Item hammer;
	public static Item wand;
	public static Item wand_iron;
	public static Item wand_coal;
	public static Item battery;
	public static Item oil_detector;
	public static Item gas_mask;
	public static Item gas_mask_grey;
	public static Item gas_mask_black;
	public static Item graphene_vest;
	public static Item clowder_banner;
	public static Item lead_boots;
	public static Item grenade_gas;
	public static Item grenade_nuclear;
	public static Item grenade_boxcar;
	public static Item hook;
	public static Item hatter;

	public static Item battery_rc;
	public static Item battery_rc_2;

	public static Item mult_stoneage;
	public static Item mult_roman;
	public static Item mult_steel;
	public static Item mult_alloy;
	
	public static Item mace;
	public static Item hatchet;
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
	public static Item can;
	public static Item canned_spam;
	public static Item canned_jizz;

	public static Item debug;
	public static Item capsule;
	
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
		components_steel = new ItemLore().setUnlocalizedName("components_steel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_steel");
		components_mechanical = new ItemLore().setUnlocalizedName("components_mechanical").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_mechanical");
		components_electronics = new ItemLore().setUnlocalizedName("components_electronics").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_electronics");
		components_plating = new ItemLore().setUnlocalizedName("components_plating").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":components_plating");

		part_rifled_barrel = new ItemLore().setUnlocalizedName("part_rifled_barrel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_rifled_barrel");
		part_smoothbore_barrel = new ItemLore().setUnlocalizedName("part_smoothbore_barrel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_smoothbore_barrel");
		part_plate = new ItemLore().setUnlocalizedName("part_plate").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_plate");
		part_gear = new ItemLore().setUnlocalizedName("part_gear").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_gear");
		part_rod = new ItemLore().setUnlocalizedName("part_rod").setCreativeTab(MainRegistry.tab).setFull3D().setTextureName(RefStrings.MODID + ":part_rod");
		part_frame = new ItemLore().setUnlocalizedName("part_frame").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_frame");
		part_spring = new ItemLore().setUnlocalizedName("part_spring").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_spring");
		part_grate = new ItemLore().setUnlocalizedName("part_grate").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_grate");
		part_suspension = new ItemLore().setUnlocalizedName("part_suspension").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_suspension");
		part_plating_1 = new ItemLore().setUnlocalizedName("part_plating_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_plating_1");
		part_plating_2 = new ItemLore().setUnlocalizedName("part_plating_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_plating_2");
		part_plating_3 = new ItemLore().setUnlocalizedName("part_plating_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_plating_3");
		part_hull_1 = new ItemLore().setUnlocalizedName("part_hull_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_hull_1");
		part_hull_2 = new ItemLore().setUnlocalizedName("part_hull_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_hull_2");
		part_hull_3 = new ItemLore().setUnlocalizedName("part_hull_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_hull_3");
		part_mechanism_1 = new ItemLore().setUnlocalizedName("part_mechanism_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_mechanism_1");
		part_mechanism_2 = new ItemLore().setUnlocalizedName("part_mechanism_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_mechanism_2");
		part_mechanism_3 = new ItemLore().setUnlocalizedName("part_mechanism_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_mechanism_3");
		part_steel_wheel = new ItemLore().setUnlocalizedName("part_steel_wheel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_steel_wheel");
		part_wheel = new ItemLore().setUnlocalizedName("part_wheel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_wheel");
		part_sawblade = new ItemLore().setUnlocalizedName("part_sawblade").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_sawblade");
		part_track = new ItemLore().setUnlocalizedName("part_track").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_track");
		part_treads = new ItemLore().setUnlocalizedName("part_treads").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_treads");
		part_rubber_drop = new ItemLore().setUnlocalizedName("part_rubber_drop").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_rubber_drop");
		part_raw_rubber = new ItemLore().setUnlocalizedName("part_raw_rubber").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_raw_rubber");
		part_rubber = new ItemLore().setUnlocalizedName("part_rubber").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_rubber");
		part_sawdust = new ItemLore().setUnlocalizedName("part_sawdust").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_sawdust");
		part_cannon_1 = new ItemLore().setUnlocalizedName("part_cannon_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_cannon_1");
		part_cannon_2 = new ItemLore().setUnlocalizedName("part_cannon_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_cannon_2");
		part_cannon_3 = new ItemLore().setUnlocalizedName("part_cannon_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_cannon_3");
		part_chassis_1 = new ItemLore().setUnlocalizedName("part_chassis_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_chassis_1");
		part_chassis_2 = new ItemLore().setUnlocalizedName("part_chassis_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_chassis_2");
		part_chassis_3 = new ItemLore().setUnlocalizedName("part_chassis_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_chassis_3");
		part_engine_1 = new ItemLore().setUnlocalizedName("part_engine_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_engine_1");
		part_engine_2 = new ItemLore().setUnlocalizedName("part_engine_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_engine_2");
		part_engine_3 = new ItemLore().setUnlocalizedName("part_engine_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_engine_3");
		part_ship_1 = new ItemLore().setUnlocalizedName("part_ship_1").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_ship_1");
		part_ship_2 = new ItemLore().setUnlocalizedName("part_ship_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_ship_2");
		part_ship_3 = new ItemLore().setUnlocalizedName("part_ship_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":part_ship_3");

		kit_singleshot = new ItemLore().setUnlocalizedName("kit_singleshot").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_singleshot");
		kit_repeating = new ItemLore().setUnlocalizedName("kit_repeating").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_repeating");
		kit_auto = new ItemLore().setUnlocalizedName("kit_auto").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_auto");
		kit_shotgun = new ItemLore().setUnlocalizedName("kit_shotgun").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_shotgun");
		kit_revolver = new ItemLore().setUnlocalizedName("kit_revolver").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_revolver");
		kit_smg = new ItemLore().setUnlocalizedName("kit_smg").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_smg");
		kit_hmg = new ItemLore().setUnlocalizedName("kit_hmg").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":kit_hmg");
		
		ingot_boron = new ItemLore().setUnlocalizedName("ingot_boron").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":ingot_boron");
		ingot_graphite = new ItemLore().setUnlocalizedName("ingot_graphite").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":ingot_graphite");
		ingot_graphene = new ItemLore().setUnlocalizedName("ingot_graphene").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":ingot_graphene");
		
		farmer = new ItemContract().setUnlocalizedName("farmer").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":farmer");
		miner = new ItemLore().setUnlocalizedName("miner").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":miner");
		miner_supplies = new ItemLore().setUnlocalizedName("miner_supplies").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":miner_supplies");
		canary = new ItemLore().setUnlocalizedName("canary").setMaxStackSize(4).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canary");
		
		rice = new ItemLore().setUnlocalizedName("rice").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":rice");
		rice_seeds = new ItemRice().setUnlocalizedName("rice_seeds").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":rice_seeds");
		rice_bag = new ItemLore().setUnlocalizedName("rice_bag").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":rice_bag");
		rice_sticky = new ItemFood(5, false).setUnlocalizedName("rice_sticky").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":rice_sticky");
		mutton_raw = new ItemFood(3, 0.3F, false).setUnlocalizedName("mutton_raw").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mutton_raw");
		mutton_cooked = new ItemFood(8, 0.8F, false).setUnlocalizedName("mutton_cooked").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mutton_cooked");
		squid_raw = new ItemFood(2, 0.2F, false).setUnlocalizedName("squid_raw").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":squid_raw");
		squid_cooked = new ItemFood(6, 0.6F, false).setUnlocalizedName("squid_cooked").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":squid_cooked");
		
		designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":designator");
		designator_range = new ItemDesingatorRange().setUnlocalizedName("designator_range").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":designator_range");
		designator_manual = new ItemDesingatorManual().setUnlocalizedName("designator_manual").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":designator_manual");
		detonator = new ItemDetonator().setUnlocalizedName("detonator").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":detonator");
		remote = new ItemRemote().setUnlocalizedName("remote").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":remote");
		
		missile_at_1 = new ItemLore().setUnlocalizedName("missile_at_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_at_1");
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
		missile_cruise_at_1 = new ItemLore().setUnlocalizedName("missile_cruise_at_1").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":missile_cruise_at_1");

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
		charge_at = new ItemLore().setUnlocalizedName("charge_at").setMaxStackSize(8).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":charge_at");
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
		cog = new ItemLore().setUnlocalizedName("cog").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":cog");
		cog_2 = new ItemLore().setUnlocalizedName("cog_2").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":cog_brass");
		cog_3 = new ItemLore().setUnlocalizedName("cog_3").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":cog_steel");
		cog_4 = new ItemLore().setUnlocalizedName("cog_4").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":cog_4");
		cog_5 = new ItemLore().setUnlocalizedName("cog_5").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":cog_5");
		scroll = new ItemLore().setUnlocalizedName("scroll").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":scroll");
		coin = new ItemLore().setUnlocalizedName("hfr_coin").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":coin");
		wrench = new ItemLore().setUnlocalizedName("hfr_wrench").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":wrench");
		province_point = new ItemLore().setUnlocalizedName("province_point").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":prestige");
		flour = new ItemLore().setUnlocalizedName("flour").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":flour");
		ingot_tap_steel = new ItemLore().setUnlocalizedName("ingot_steel").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":ingot_steel");
		crate = new ItemLore().setUnlocalizedName("crate").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":crate");

		hammer = new ItemHammer().setUnlocalizedName("hammer").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":hammer");
		wand = new ItemWand().setUnlocalizedName("wand").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":wand");
		wand_iron = new ItemWandOre().setUnlocalizedName("wand_iron").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":wand_iron");
		wand_coal = new ItemWandOre().setUnlocalizedName("wand_coal").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":wand_coal");
		oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":oil_detector");
		gas_mask = new ItemGasMask(matGasMask, 5, 0).setUnlocalizedName("gas_mask").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_mask");
		gas_mask_grey = new ItemGasMask(matGasMask, 5, 0).setUnlocalizedName("gas_mask_grey").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_mask_grey");
		gas_mask_black = new ItemGasMask(matGasMask, 5, 0).setUnlocalizedName("gas_mask_black").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":gas_mask_black");
		graphene_vest = new ItemSpecialArmor(matGasMask, 5, 2).setUnlocalizedName("graphene_vest").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":graphene_vest");
		clowder_banner = new ItemBanner(matGasMask, 5, 3).setUnlocalizedName("clowder_banner").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":clowder_banner");
		lead_boots = new ItemSpecialArmor(matGasMask, 5, 3).setUnlocalizedName("lead_boots").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":lead_boots");
		grenade_gas = new ItemGrenade(4).setUnlocalizedName("grenade_gas").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":grenade_gas");
		grenade_nuclear = new ItemGrenade(7).setUnlocalizedName("grenade_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":grenade_nuclear");
		grenade_boxcar = new ItemGrenade(12).setUnlocalizedName("grenade_boxcar").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":grenade_boxcar");
		hook = new ItemGrenade(-1).setUnlocalizedName("hook").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":hook");
		hatter = new ItemHatter().setUnlocalizedName("hatter").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":hat");
		
		battery_rc = new ItemBattery(50000).setUnlocalizedName("battery_rc").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":battery_rc");
		battery_rc_2 = new ItemBattery(400000).setUnlocalizedName("battery_rc_2").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":battery_rc_2");
		battery = new ItemBatteryInfinite().setUnlocalizedName("battery").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":battery");

		mult_stoneage = new ItemMultitool().setUnlocalizedName("mult_stoneage").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_stoneage");
		mult_roman = new ItemMultitool().setUnlocalizedName("mult_roman").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_roman");
		mult_steel = new ItemMultitool().setUnlocalizedName("mult_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_steel");
		mult_alloy = new ItemMultitool().setUnlocalizedName("mult_alloy").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mult_alloy");
		
		mace = new ItemMace().setUnlocalizedName("mace").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":mace");
		hatchet = new ItemHatchet().setUnlocalizedName("hatchet").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":hatchet");
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
		can = new ItemCan().setUnlocalizedName("can").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":can");
		canned_spam = new ItemModFood(5).setUnlocalizedName("canned_spam").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canned_spam");
		canned_jizz = new ItemModFood(10).setUnlocalizedName("canned_jizz").setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":canned_jizz");
		
		debug = new ItemDebug().setUnlocalizedName("debug").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":debug");
		capsule = new ItemCapsule().setUnlocalizedName("capsule").setMaxStackSize(1).setCreativeTab(MainRegistry.tab).setTextureName(RefStrings.MODID + ":capsule");
	}
	
	private static void registerItem() {
		GameRegistry.registerItem(wand, wand.getUnlocalizedName());
		GameRegistry.registerItem(clowder_banner, clowder_banner.getUnlocalizedName());
		
		GameRegistry.registerItem(hook, hook.getUnlocalizedName());
		
		GameRegistry.registerItem(coil, coil.getUnlocalizedName());
		GameRegistry.registerItem(magnetron, magnetron.getUnlocalizedName());
		GameRegistry.registerItem(circuit, circuit.getUnlocalizedName());

		GameRegistry.registerItem(components_wood, components_wood.getUnlocalizedName());
		GameRegistry.registerItem(components_scaffold, components_scaffold.getUnlocalizedName());
		GameRegistry.registerItem(components_steel, components_steel.getUnlocalizedName());
		GameRegistry.registerItem(components_mechanical, components_mechanical.getUnlocalizedName());
		GameRegistry.registerItem(components_electronics, components_electronics.getUnlocalizedName());
		GameRegistry.registerItem(components_plating, components_plating.getUnlocalizedName());

//		GameRegistry.registerItem(part_rifled_barrel, part_rifled_barrel.getUnlocalizedName());
//		GameRegistry.registerItem(part_smoothbore_barrel, part_smoothbore_barrel.getUnlocalizedName());
//		GameRegistry.registerItem(part_plate, part_plate.getUnlocalizedName());
//		GameRegistry.registerItem(part_gear, part_gear.getUnlocalizedName());
//		GameRegistry.registerItem(part_rod, part_rod.getUnlocalizedName());
//		GameRegistry.registerItem(part_frame, part_frame.getUnlocalizedName());
//		GameRegistry.registerItem(part_spring, part_spring.getUnlocalizedName());
//		GameRegistry.registerItem(part_grate, part_grate.getUnlocalizedName());
//		GameRegistry.registerItem(part_suspension, part_suspension.getUnlocalizedName());
//		GameRegistry.registerItem(part_plating_1, part_plating_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_plating_2, part_plating_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_plating_3, part_plating_3.getUnlocalizedName());
//		GameRegistry.registerItem(part_hull_1, part_hull_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_hull_2, part_hull_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_hull_3, part_hull_3.getUnlocalizedName());
//		GameRegistry.registerItem(part_mechanism_1, part_mechanism_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_mechanism_2, part_mechanism_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_mechanism_3, part_mechanism_3.getUnlocalizedName());
//		GameRegistry.registerItem(part_steel_wheel, part_steel_wheel.getUnlocalizedName());
//		GameRegistry.registerItem(part_wheel, part_wheel.getUnlocalizedName());
//		GameRegistry.registerItem(part_sawblade, part_sawblade.getUnlocalizedName());
//		GameRegistry.registerItem(part_track, part_track.getUnlocalizedName());
//		GameRegistry.registerItem(part_treads, part_treads.getUnlocalizedName());
		GameRegistry.registerItem(part_rubber_drop, part_rubber_drop.getUnlocalizedName());
		GameRegistry.registerItem(part_raw_rubber, part_raw_rubber.getUnlocalizedName());
		GameRegistry.registerItem(part_rubber, part_rubber.getUnlocalizedName());
		GameRegistry.registerItem(part_sawdust, part_sawdust.getUnlocalizedName());
//		GameRegistry.registerItem(part_cannon_1, part_cannon_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_cannon_2, part_cannon_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_cannon_3, part_cannon_3.getUnlocalizedName());
//		GameRegistry.registerItem(part_chassis_1, part_chassis_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_chassis_2, part_chassis_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_chassis_3, part_chassis_3.getUnlocalizedName());
//		GameRegistry.registerItem(part_engine_1, part_engine_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_engine_2, part_engine_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_engine_3, part_engine_3.getUnlocalizedName());
//		GameRegistry.registerItem(part_ship_1, part_ship_1.getUnlocalizedName());
//		GameRegistry.registerItem(part_ship_2, part_ship_2.getUnlocalizedName());
//		GameRegistry.registerItem(part_ship_3, part_ship_3.getUnlocalizedName());
//
//		GameRegistry.registerItem(kit_singleshot, kit_singleshot.getUnlocalizedName());
//		GameRegistry.registerItem(kit_repeating, kit_repeating.getUnlocalizedName());
//		GameRegistry.registerItem(kit_auto, kit_auto.getUnlocalizedName());
//		GameRegistry.registerItem(kit_shotgun, kit_shotgun.getUnlocalizedName());
//		GameRegistry.registerItem(kit_revolver, kit_revolver.getUnlocalizedName());
//		GameRegistry.registerItem(kit_smg, kit_smg.getUnlocalizedName());
//		GameRegistry.registerItem(kit_hmg, kit_hmg.getUnlocalizedName());

		GameRegistry.registerItem(mutton_raw, mutton_raw.getUnlocalizedName());
		GameRegistry.registerItem(mutton_cooked, mutton_cooked.getUnlocalizedName());
		GameRegistry.registerItem(squid_raw, squid_raw.getUnlocalizedName());
		GameRegistry.registerItem(squid_cooked, squid_cooked.getUnlocalizedName());

		GameRegistry.registerItem(tidal_energy, tidal_energy.getUnlocalizedName());
		GameRegistry.registerItem(whale_meat, whale_meat.getUnlocalizedName());
		GameRegistry.registerItem(science, science.getUnlocalizedName());
		GameRegistry.registerItem(science_2, science_2.getUnlocalizedName());
		GameRegistry.registerItem(science_3, science_3.getUnlocalizedName());
		GameRegistry.registerItem(science_4, science_4.getUnlocalizedName());
		GameRegistry.registerItem(science_5, science_5.getUnlocalizedName());
		GameRegistry.registerItem(science_6, science_6.getUnlocalizedName());
		GameRegistry.registerItem(cog, cog.getUnlocalizedName());
		GameRegistry.registerItem(cog_2, cog_2.getUnlocalizedName());
		GameRegistry.registerItem(cog_3, cog_3.getUnlocalizedName());
		GameRegistry.registerItem(cog_4, cog_4.getUnlocalizedName());
		GameRegistry.registerItem(cog_5, cog_5.getUnlocalizedName());
		GameRegistry.registerItem(scroll, scroll.getUnlocalizedName());
		GameRegistry.registerItem(coin, coin.getUnlocalizedName());
		GameRegistry.registerItem(wrench, wrench.getUnlocalizedName());
		GameRegistry.registerItem(province_point, province_point.getUnlocalizedName());
		GameRegistry.registerItem(flour, flour.getUnlocalizedName());
		GameRegistry.registerItem(ingot_tap_steel, ingot_tap_steel.getUnlocalizedName());
		GameRegistry.registerItem(crate, crate.getUnlocalizedName());

		GameRegistry.registerItem(farmer, farmer.getUnlocalizedName());
		GameRegistry.registerItem(miner, miner.getUnlocalizedName());
		GameRegistry.registerItem(miner_supplies, miner_supplies.getUnlocalizedName());
		GameRegistry.registerItem(canary, canary.getUnlocalizedName());

		GameRegistry.registerItem(hatchet, hatchet.getUnlocalizedName());
		GameRegistry.registerItem(clowder_map, clowder_map.getUnlocalizedName());
		GameRegistry.registerItem(scanner, scanner.getUnlocalizedName());
		GameRegistry.registerItem(repair_1, repair_1.getUnlocalizedName());
		GameRegistry.registerItem(repair_2, repair_2.getUnlocalizedName());
		GameRegistry.registerItem(repair_3, repair_3.getUnlocalizedName());
		GameRegistry.registerItem(pakker, pakker.getUnlocalizedName());
		GameRegistry.registerItem(pak_rocket, pak_rocket.getUnlocalizedName());

		GameRegistry.registerItem(missile_at_1, missile_at_1.getUnlocalizedName());
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
		GameRegistry.registerItem(missile_cruise_at_1, missile_cruise_at_1.getUnlocalizedName());

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
		GameRegistry.registerItem(charge_at, charge_at.getUnlocalizedName());
		GameRegistry.registerItem(charge_railgun, charge_railgun.getUnlocalizedName());
		GameRegistry.registerItem(charge_bfg, charge_bfg.getUnlocalizedName());

		GameRegistry.registerItem(sexlol, sexlol.getUnlocalizedName());
		GameRegistry.registerItem(can, can.getUnlocalizedName());
		GameRegistry.registerItem(canned_spam, canned_spam.getUnlocalizedName());
		GameRegistry.registerItem(canned_jizz, canned_jizz.getUnlocalizedName());
		
		GameRegistry.registerItem(debug, debug.getUnlocalizedName());
		GameRegistry.registerItem(capsule, capsule.getUnlocalizedName());
	}
}