package com.hfr.blocks;

import com.hfr.blocks.clowder.*;
import com.hfr.blocks.door.*;
import com.hfr.blocks.machine.*;
import com.hfr.blocks.props.*;
import com.hfr.blocks.weapon.*;
import com.hfr.items.*;
import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {
	
	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}

	public static Block concrete;
	public static Block concrete_bricks;
	public static Block concrete_flat;
	public static Block concrete_bolted;
	public static Block hard_stone;
	public static Block hard_mesh;
	public static Block concrete_hazard;
	public static Block concrete_rocks;

	public static Block soil_moon;
	public static Block soil_mud;

	public static Block rice;
	public static Block rope;
	
	public static Block ore_uranium;
	public static Block ore_boron;
	
	public static Block temp;
	public static Block uni_foundation;
	public static Block asphalt;

	public static Block ore_oil;
	public static Block ore_oil_empty;
	public static Block oil_pipe;

	public static Block seal_controller;
	public static Block seal_frame;
	public static Block seal_hatch;

	public static Block vault_door;
	public static Block vault_door_dummy;

	public static Block blast_door;
	public static Block blast_door_dummy;

	public static Block vent_chlorine_seal;
	public static Block chlorine_gas;
	public static Block barbed_wire;

	public static Block hydro_wall;
	public static Block hydro_turbine;
	
	public static Block machine_radar;
	public static final int guiID_radar = 0;
	public static Block machine_siren;
	public static final int guiID_siren = 1;
	public static Block machine_forcefield;
	public static final int guiID_forcefield = 2;
	public static Block launch_pad;
	public static final int guiID_launchpad = 3;
	public static Block machine_derrick;
	public static final int guiID_derrick = 4;
	public static Block machine_refinery;
	public static final int guiID_refinery = 5;
	public static Block machine_tank;
	public static final int guiID_tank = 6;
	public static Block hydro_core;
	public static final int guiID_hydro = 9;
	public static Block machine_net;
	public static final int guiID_net = 10;
	public static Block machine_market;
	public static final int guiID_market = 11;
	public static Block rbmk_element;
	public static final int guiID_rbmk = 12;
	public static Block rbmk_rods;
	public static final int guiID_rods = 13;
	public static Block builder;
	public static final int guiID_builder = 14;
	public static Block machine_uni;
	public static final int guiID_uni = 15;
	public static Block machine_emp;
	public static final int guiID_emp = 16;
	public static Block clowder_flag;
	public static final int guiID_flag = 17;
	public static Block clowder_cap;
	public static final int guiID_cap = 18;
	public static Block clowder_flag_big;
	public static final int guiID_flag_big = 19;
	public static Block machine_grainmill;
	public static final int guiID_grainmill = 20;
	public static Block machine_blastfurnace;
	public static final int guiID_blastfurnace = 21;
	public static Block box;
	public static final int guiID_box = 22;
	public static Block machine_coalmine;
	public static final int guiID_coalmine = 23;
	public static Block machine_coalgen;
	public static final int guiID_coalgen = 24;
	public static Block machine_factory;
	public static final int guiID_factory = 25;
	public static Block machine_tradeport;
	public static final int guiID_tradeport = 32;
	public static Block machine_battery;
	public static final int guiID_battery = 26;
	public static Block machine_windmill;
	public static Block machine_waterwheel;
	public static Block machine_diesel;
	public static final int guiID_diesel = 27;
	public static Block machine_rift;
	public static final int guiID_rift = 28;
	public static Block machine_turbine;
	public static final int guiID_turbine = 29;
	public static Block machine_temple;
	public static final int guiID_temple = 30;
	public static Block clowder_conquerer;
	public static Block officer_chest;
	public static final int guiID_chest = 36;

	public static Block machine_alloy;
	public static final int guiID_alloy = 31;
	public static Block machine_sawmill;
	public static final int guiID_sawmill = 32;
	public static Block machine_crusher;
	public static final int guiID_crusher = 33;
	public static Block machine_efurnace;
	public static final int guiID_efurnace = 34;
	public static Block machine_distillery;
	public static final int guiID_distillery = 35;
	public static Block machine_foundry;
	public static final int guiID_foundry = 37;

	public static Block barricade;

	public static Block teleporter;

	public static Block hesco_block;
	public static Block palisade;
	public static Block stone_wall;
	public static Block brick_wall;
	public static Block great_wall;
	public static Block berlin_wall;
	public static Block med_tent;
	public static Block tp_tent;
	public static Block statue;

	public static Block block_graphite;
	public static Block block_boron;

	public static Block display;

	public static Block oil_duct;
	public static Block gas_duct;

	public static Block railgun_plasma;
	public static final int guiID_railgun = 7;
	public static Block cannon_naval;
	public static final int guiID_naval = 8;

	public static Block debug;
	public static Block steam;
	public static Block oil;
	public static Block gas;
	public static Block petroil;
	public static Block diesel;
	public static Block kerosene;
	public static Block petroleum;

	public static Block.SoundType soundTypeConcrete = new ModSoundType("concrete", 0.25F, 1.0F)
    {
        public String func_150496_b()
        {
            return Block.soundTypeStone.func_150496_b();
        }
    };
	public static Block.SoundType soundTypeMetal = new ModSoundType("metal", 0.5F, 1.0F)
    {
        public String func_150496_b()
        {
            return Block.soundTypeMetal.func_150496_b();
        }
    };
	public static Block.SoundType soundTypeMud = new ModSoundType("mud", 0.5F, 1.0F)
    {
        public String func_150496_b()
        {
            return Block.soundTypeMetal.func_150496_b();
        }
    };

	private static void initializeBlock() {
		
		concrete = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete").setHardness(10.0F).setResistance(1000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete");
		concrete_bricks = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_bricks").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete_bricks");
		concrete_flat = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_flat").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete_flat");
		concrete_bolted = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_bolted").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete_bolted");
		hard_stone = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("hard_stone").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":hard_stone");
		hard_mesh = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("hard_mesh").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":hard_mesh");
		concrete_hazard = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_hazard").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete_hazard");
		concrete_rocks = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_rocks").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete_rocks");
		barricade = new BlockSandbags(Material.rock).setStepSound(soundTypeConcrete).setBlockName("barricade").setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":sandbags");

		soil_moon = new BlockGeneric(Material.sand).setStepSound(Block.soundTypeGravel).setBlockName("soil_moon").setHardness(1.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":soil_moon");
		soil_mud = new BlockMud(Material.ground).setStepSound(soundTypeMud).setBlockName("soil_mud").setHardness(1.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":soil_mud");

		rice = new BlockRice().setBlockName("rice").setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":rice");
		rope = new BlockRope(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("rope").setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":rope");
		
		ore_uranium = new BlockGeneric(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("ore_uranium").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":ore_uranium");
		ore_boron = new BlockGeneric(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("ore_boron").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":ore_boron");

		temp = new BlockTemporary(Material.ground).setStepSound(Block.soundTypeGravel).setBlockName("temp").setHardness(1.0F).setResistance(1.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":dirt_alt");
		uni_foundation = new BlockSpeedy(Material.rock, 0.15D).setStepSound(soundTypeConcrete).setBlockName("uni_foundation").setHardness(5.0F).setResistance(1.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":uni_foundation");
		asphalt = new BlockSpeedy(Material.rock, 0.25D).setStepSound(soundTypeConcrete).setBlockName("asphalt").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":asphalt");

		ore_oil = new BlockGeneric(Material.rock).setBlockName("ore_oil").setHardness(1.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":ore_oil");
		ore_oil_empty = new BlockGeneric(Material.rock).setBlockName("ore_oil_empty").setHardness(1.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":ore_oil_empty");
		oil_pipe = new BlockNoDrop(Material.rock).setBlockName("oil_pipe").setHardness(1.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":oil_pipe");
		
		seal_controller = new BlockSeal(Material.rock).setStepSound(soundTypeMetal).setBlockName("seal_controller").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete");
		seal_frame = new BlockGeneric(Material.rock).setStepSound(soundTypeMetal).setBlockName("seal_frame").setHardness(5.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":seal_frame");
		seal_hatch = new BlockHatch(Material.rock).setStepSound(soundTypeMetal).setBlockName("seal_hatch").setHardness(2.5F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":seal_hatch");
		
		vault_door = new VaultDoor(Material.rock).setStepSound(soundTypeMetal).setBlockName("vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":vault_door");
		vault_door_dummy = new DummyBlockVault(Material.rock).setStepSound(soundTypeMetal).setBlockName("vault_door_dummy").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":concrete");
		blast_door = new BlastDoor(Material.rock).setStepSound(soundTypeMetal).setBlockName("blast_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":blast_door");
		blast_door_dummy = new DummyBlockBlast(Material.rock).setStepSound(soundTypeMetal).setBlockName("blast_door_dummy").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":concrete");
		
		machine_radar = new MachineRadar(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_radar");
		machine_siren = new MachineSiren(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_siren");
		machine_emp = new MachineEMP(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_emp").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_emp");
		launch_pad = new LaunchPad(Material.iron).setStepSound(soundTypeMetal).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		machine_forcefield = new MachineForceField(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_forcefield").setLightLevel(1.0F).setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_forcefield");
		machine_derrick = new MachineDerrick(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_derrick").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_derrick");
		machine_refinery = new MachineRefinery(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_refinery");
		machine_tank = new MachineTank(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":tank");
		machine_market = new MachineMarket(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_market").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":market_side");
		builder = new MachineBuilder(Material.iron).setStepSound(soundTypeMetal).setBlockName("builder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":builder_side");
		box = new Box(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("box").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab);

		teleporter = new BlockTeleporter(Material.rock).setStepSound(soundTypeMetal).setBlockName("teleporter").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":teleporter");
		
		rbmk_element = new RBMKElement(Material.iron).setStepSound(soundTypeMetal).setBlockName("rbmk_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":rbmk_element");
		rbmk_rods = new RBMKRods(Material.iron).setStepSound(soundTypeMetal).setBlockName("rbmk_rods").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":rbmk_rods");

		block_graphite = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("block_graphite").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":block_graphite");
		block_boron = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("block_boron").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":block_boron");
		
		display = new BlockDisplay(Material.iron).setStepSound(soundTypeMetal).setBlockName("display").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":display");
		
		oil_duct = new BlockDuct(Material.iron).setStepSound(soundTypeMetal).setBlockName("oil_duct").setHardness(2.5F).setResistance(1.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":oil_duct_icon");
		gas_duct = new BlockDuct(Material.iron).setStepSound(soundTypeMetal).setBlockName("gas_duct").setHardness(2.5F).setResistance(1.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":gas_duct_icon");
		
		vent_chlorine_seal = new BlockClorineSeal(Material.iron).setStepSound(soundTypeMetal).setBlockName("vent_chlorine_seal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab);
		chlorine_gas = new BlockClorine(Material.cloth).setBlockName("chlorine_gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":chlorine_gas");
		barbed_wire = new BlockBarbedWire(Material.iron).setBlockName("barbed_wire").setHardness(1.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":barbed_wire");
		
		hydro_wall = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("hydro_wall").setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":hydro_wall");
		hydro_turbine = new BlockTurbine(Material.rock).setStepSound(soundTypeMetal).setBlockName("hydro_turbine").setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.tab);
		hydro_core = new BlockHydroCore(Material.rock).setStepSound(soundTypeMetal).setBlockName("hydro_core").setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.tab);
		
		machine_net = new MachineNet(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("machine_net").setHardness(0.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_net");
		machine_uni = new MachineUni(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("machine_uni").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_uni");
		machine_grainmill = new MachineGrainmill(Material.wood).setStepSound(Block.soundTypeWood).setBlockName("machine_grainmill").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_grainmill");
		machine_blastfurnace = new MachineBlastFurnace(Material.rock).setStepSound(soundTypeConcrete).setBlockName("machine_blastfurnace").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_blastfurnace");
		machine_coalmine = new MachineCoalMine(Material.rock).setStepSound(soundTypeMetal).setBlockName("machine_coalmine").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_coalmine");
		machine_coalgen = new MachineCoalGen(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_coalgen").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_coalgen");
		machine_factory = new MachineFactory(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_factory").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_factory");
		machine_tradeport = new MachineTradeport(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_tradeport").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_tradeport");
		machine_battery = new MachineBattery(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_battery").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab);
		machine_windmill = new MachineWindmill(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_windmill").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_windmill");
		machine_waterwheel = new MachineWaterwheel(Material.wood).setStepSound(Block.soundTypeWood).setBlockName("machine_waterwheel").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_waterwheel");
		machine_diesel = new MachineDieselGen(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_diesel").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_diesel");
		machine_rift = new MachineRift(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_rift").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_rift");
		machine_turbine = new MachineTurbine(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_turbine").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_turbine");
		machine_temple = new MachineTemple(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_temple").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_temple");
		machine_alloy = new MachineAlloy(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_alloy").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_alloy");
		machine_sawmill = new MachineSawmill(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_sawmill").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_sawmill");
		machine_crusher = new MachineCrusher(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_crusher").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_crusher");
		machine_efurnace = new MachineEFurnace(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_efurnace").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_efurnace");
		machine_distillery = new MachineDistillery(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_distillery").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_distillery");
		machine_foundry = new MachineFoundry(Material.iron).setStepSound(soundTypeConcrete).setBlockName("machine_foundry").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_foundry");

		railgun_plasma = new RailgunPlasma(Material.iron).setStepSound(soundTypeMetal).setBlockName("railgun_plasma").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":railgun_plasma");
		cannon_naval = new CannonNaval(Material.iron).setStepSound(soundTypeMetal).setBlockName("cannon_naval").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":cannon_naval");
		
		clowder_flag = new Flag(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_flag").setHardness(5.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":clowder_flag");
		clowder_cap = new Cap(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_cap").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":clowder_cap");
		clowder_flag_big = new FlagBig(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_flag_big").setLightLevel(1F).setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":clowder_flag_big");
		clowder_conquerer = new Conquerer(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_conquerer").setHardness(5.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":clowder_conquerer");
		officer_chest = new OfficerChest().setStepSound(Block.soundTypeWood).setBlockName("officer_chest").setResistance(10F).setHardness(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":officer_chest");

		hesco_block = new PropHesco(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("hesco_block").setHardness(1F).setResistance(200F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":hesco_block");
		palisade = new PropPalisade(Material.wood).setStepSound(Block.soundTypeWood).setBlockName("palisade").setHardness(2F).setResistance(5F).setCreativeTab(MainRegistry.tab);
		stone_wall = new PropWall(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("stone_wall").setHardness(2.5F).setResistance(10F).setCreativeTab(MainRegistry.tab);
		brick_wall = new PropWall(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("brick_wall").setHardness(2.5F).setResistance(25F).setCreativeTab(MainRegistry.tab);
		great_wall = new PropWall(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("great_wall").setHardness(2.5F).setResistance(10F).setCreativeTab(MainRegistry.tab);
		berlin_wall = new PropBerlin(Material.rock).setStepSound(soundTypeConcrete).setBlockName("berlin_wall").setHardness(10.0F).setResistance(100F).setCreativeTab(MainRegistry.tab);
		med_tent = new PropTent(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("med_tent").setHardness(1F).setResistance(1.5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":med_tent");
		tp_tent = new PropTent(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("tp_tent").setHardness(1F).setResistance(1.5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":tp_tent");
		statue = new PropStatue(Material.cloth).setStepSound(soundTypeConcrete).setBlockName("statue").setHardness(2.5F).setResistance(10F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":statue");

		debug = new BlockDebug(Material.cloth).setStepSound(soundTypeMetal).setBlockName("debug").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":debug");
		steam = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("steam").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
		oil = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("oil").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
		gas = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
		petroil = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("petroil").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
		diesel = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("diesel").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
		kerosene = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("kerosene").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
		petroleum = new BlockSteam(Material.cloth).setStepSound(Block.soundTypeStone).setBlockName("petroleum").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null);
	}

	private static void registerBlock() {

		GameRegistry.registerBlock(hard_stone, ItemBlockLore.class, hard_stone.getUnlocalizedName());
		GameRegistry.registerBlock(concrete, ItemBlockLore.class, concrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_bricks, ItemBlockLore.class, concrete_bricks.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_flat, ItemBlockLore.class, concrete_flat.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_bolted, ItemBlockLore.class, concrete_bolted.getUnlocalizedName());
		GameRegistry.registerBlock(hard_mesh, ItemBlockLore.class, hard_mesh.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_hazard, ItemBlockLore.class, concrete_hazard.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_rocks, ItemBlockLore.class, concrete_rocks.getUnlocalizedName());
		GameRegistry.registerBlock(barricade, ItemBlockLore.class, barricade.getUnlocalizedName());
		
		GameRegistry.registerBlock(soil_moon, soil_moon.getUnlocalizedName());
		
		GameRegistry.registerBlock(rope, rope.getUnlocalizedName());

		GameRegistry.registerBlock(temp, temp.getUnlocalizedName());
		GameRegistry.registerBlock(uni_foundation, ItemBlockLore.class, uni_foundation.getUnlocalizedName());
		GameRegistry.registerBlock(asphalt, ItemBlockLore.class, asphalt.getUnlocalizedName());

//		GameRegistry.registerBlock(machine_uni, ItemBlockLore.class, machine_uni.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_factory, ItemBlockLore.class, machine_factory.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_tradeport, ItemBlockLore.class, machine_tradeport.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_temple, ItemBlockLore.class, machine_temple.getUnlocalizedName());
		GameRegistry.registerBlock(machine_grainmill, ItemBlockLore.class, machine_grainmill.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_blastfurnace, ItemBlockLore.class, machine_blastfurnace.getUnlocalizedName());

		GameRegistry.registerBlock(machine_sawmill, ItemBlockLore.class, machine_sawmill.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_alloy, ItemBlockLore.class, machine_alloy.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_crusher, ItemBlockLore.class, machine_crusher.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_distillery, ItemBlockLore.class, machine_distillery.getUnlocalizedName());
//		GameRegistry.registerBlock(machine_foundry, machine_foundry.getUnlocalizedName());
		
		GameRegistry.registerBlock(machine_market, ItemBlockUnstackable.class, machine_market.getUnlocalizedName());
		GameRegistry.registerBlock(box, ItemBlockLore.class, box.getUnlocalizedName());

		GameRegistry.registerBlock(display, display.getUnlocalizedName());
		GameRegistry.registerBlock(ore_boron, ore_boron.getUnlocalizedName());
		OreDictionary.registerOre("oreBoron", ModBlocks.ore_boron);
		
		GameRegistry.registerBlock(railgun_plasma, ItemBlockUnstackable.class, railgun_plasma.getUnlocalizedName());
		GameRegistry.registerBlock(cannon_naval, ItemBlockUnstackable.class, cannon_naval.getUnlocalizedName());
		
		GameRegistry.registerBlock(clowder_flag, ItemBlockUnstackable.class, clowder_flag.getUnlocalizedName());
		GameRegistry.registerBlock(clowder_flag_big, ItemBlockUnstackable.class, clowder_flag_big.getUnlocalizedName());
		GameRegistry.registerBlock(clowder_cap, ItemBlockUnstackable.class, clowder_cap.getUnlocalizedName());
		GameRegistry.registerBlock(clowder_conquerer, ItemBlockConqueror.class, clowder_conquerer.getUnlocalizedName());
		GameRegistry.registerBlock(officer_chest, ItemBlockLore.class, officer_chest.getUnlocalizedName());
		
		GameRegistry.registerBlock(hesco_block, ItemBlockLore.class, hesco_block.getUnlocalizedName());
		GameRegistry.registerBlock(palisade, ItemBlockLore.class, palisade.getUnlocalizedName());
		GameRegistry.registerBlock(stone_wall, ItemBlockLore.class, stone_wall.getUnlocalizedName());
		GameRegistry.registerBlock(brick_wall, ItemBlockLore.class, brick_wall.getUnlocalizedName());
		GameRegistry.registerBlock(great_wall, ItemBlockLore.class, great_wall.getUnlocalizedName());
		GameRegistry.registerBlock(berlin_wall, ItemBlockLore.class, berlin_wall.getUnlocalizedName());
		GameRegistry.registerBlock(med_tent, ItemBlockLore.class, med_tent.getUnlocalizedName());
		GameRegistry.registerBlock(tp_tent, ItemBlockLore.class, tp_tent.getUnlocalizedName());
		GameRegistry.registerBlock(statue, ItemBlockLore.class, statue.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radar, ItemBlockLore.class, machine_radar.getUnlocalizedName());
		GameRegistry.registerBlock(debug, ItemBlockUnstackable.class, debug.getUnlocalizedName());
		GameRegistry.registerBlock(steam, steam.getUnlocalizedName());
	}
}
