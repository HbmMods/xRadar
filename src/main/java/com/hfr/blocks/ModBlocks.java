package com.hfr.blocks;

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

public class ModBlocks {
	
	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}

	public static Block concrete;
	public static Block concrete_bricks;
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

	public static Block vent_chlorine_seal;
	public static Block chlorine_gas;

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

	private static void initializeBlock() {
		
		concrete = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete").setHardness(5.0F).setResistance(1000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete");
		concrete_bricks = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("concrete_bricks").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":concrete_bricks");
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
		
		machine_radar = new MachineRadar(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_radar");
		machine_siren = new MachineSiren(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_siren");
		machine_emp = new MachineEMP(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_emp").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_emp");
		launch_pad = new LaunchPad(Material.iron).setStepSound(soundTypeMetal).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		machine_forcefield = new MachineForceField(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_forcefield").setLightLevel(1.0F).setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_forcefield");
		machine_derrick = new MachineDerrick(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_derrick").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_derrick");
		machine_refinery = new MachineRefinery(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_refinery");
		machine_tank = new MachineTank(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":tank");
		machine_market = new MachineMarket(Material.iron).setStepSound(soundTypeMetal).setBlockName("machine_market").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":market_side");
		builder = new MachineBuilder(Material.iron).setStepSound(soundTypeMetal).setBlockName("builder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":builder_side");
		
		rbmk_element = new RBMKElement(Material.iron).setStepSound(soundTypeMetal).setBlockName("rbmk_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":rbmk_element");
		rbmk_rods = new RBMKRods(Material.iron).setStepSound(soundTypeMetal).setBlockName("rbmk_rods").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":rbmk_rods");

		block_graphite = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("block_graphite").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":block_graphite");
		block_boron = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("block_boron").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":block_boron");
		
		display = new BlockDisplay(Material.iron).setStepSound(soundTypeMetal).setBlockName("display").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":display");
		
		oil_duct = new BlockDuct(Material.iron).setStepSound(soundTypeMetal).setBlockName("oil_duct").setHardness(2.5F).setResistance(1.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":oil_duct_icon");
		gas_duct = new BlockDuct(Material.iron).setStepSound(soundTypeMetal).setBlockName("gas_duct").setHardness(2.5F).setResistance(1.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":gas_duct_icon");
		
		vent_chlorine_seal = new BlockClorineSeal(Material.iron).setStepSound(soundTypeMetal).setBlockName("vent_chlorine_seal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab);
		chlorine_gas = new BlockClorine(Material.cloth).setBlockName("chlorine_gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":chlorine_gas");
		
		hydro_wall = new BlockGeneric(Material.rock).setStepSound(soundTypeConcrete).setBlockName("hydro_wall").setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":hydro_wall");
		hydro_turbine = new BlockTurbine(Material.rock).setStepSound(soundTypeMetal).setBlockName("hydro_turbine").setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.tab);
		hydro_core = new BlockHydroCore(Material.rock).setStepSound(soundTypeMetal).setBlockName("hydro_core").setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.tab);
		
		machine_net = new MachineNet(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("machine_net").setHardness(0.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_net");
		machine_uni = new MachineUni(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("machine_uni").setHardness(5.0F).setResistance(0.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":machine_uni");
		
		railgun_plasma = new RailgunPlasma(Material.iron).setStepSound(soundTypeMetal).setBlockName("railgun_plasma").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":railgun_plasma");
		cannon_naval = new CannonNaval(Material.iron).setStepSound(soundTypeMetal).setBlockName("cannon_naval").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":cannon_naval");
		
		clowder_flag = new Flag(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_flag").setHardness(5.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":clowder_flag");
		clowder_cap = new Cap(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_cap").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":clowder_cap");
		clowder_flag_big = new FlagBig(Material.iron).setStepSound(soundTypeMetal).setBlockName("clowder_flag_big").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":clowder_flag_big");
		
		hesco_block = new PropHesco(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("hesco_block").setHardness(1F).setResistance(200F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":hesco_block");
		palisade = new PropPalisade(Material.wood).setStepSound(Block.soundTypeWood).setBlockName("palisade").setHardness(2F).setResistance(5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":palisade");
		stone_wall = new PropWall(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("stone_wall").setHardness(2.5F).setResistance(10F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":stone_wall");
		brick_wall = new PropWall(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("brick_wall").setHardness(2.5F).setResistance(25F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":brick_wall");
		great_wall = new PropWall(Material.rock).setStepSound(Block.soundTypeStone).setBlockName("great_wall").setHardness(2.5F).setResistance(10F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":great_wall");
		berlin_wall = new PropBerlin(Material.rock).setStepSound(soundTypeConcrete).setBlockName("berlin_wall").setHardness(10.0F).setResistance(100F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":berlin_wall");
		med_tent = new PropTent(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("med_tent").setHardness(1F).setResistance(1.5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":med_tent");
		tp_tent = new PropTent(Material.cloth).setStepSound(Block.soundTypeCloth).setBlockName("tp_tent").setHardness(1F).setResistance(1.5F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":tp_tent");
		statue = new PropStatue(Material.cloth).setStepSound(soundTypeConcrete).setBlockName("statue").setHardness(2.5F).setResistance(10F).setCreativeTab(MainRegistry.tab).setBlockTextureName(RefStrings.MODID + ":statue");

		debug = new BlockDebug(Material.cloth).setStepSound(soundTypeMetal).setBlockName("debug").setHardness(0.0F).setResistance(0.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":debug");
		
		Blocks.emerald_block.setResistance(6000000.0F).setBlockUnbreakable();
	}

	private static void registerBlock() {

		GameRegistry.registerBlock(concrete, concrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_bricks, concrete_bricks.getUnlocalizedName());
		GameRegistry.registerBlock(temp, temp.getUnlocalizedName());
		GameRegistry.registerBlock(uni_foundation, ItemBlockLore.class, uni_foundation.getUnlocalizedName());
		GameRegistry.registerBlock(asphalt, ItemBlockLore.class, asphalt.getUnlocalizedName());

		GameRegistry.registerBlock(ore_oil, ore_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil_empty, ore_oil_empty.getUnlocalizedName());
		GameRegistry.registerBlock(oil_pipe, oil_pipe.getUnlocalizedName());

		GameRegistry.registerBlock(seal_controller, seal_controller.getUnlocalizedName());
		GameRegistry.registerBlock(seal_frame, seal_frame.getUnlocalizedName());
		GameRegistry.registerBlock(seal_hatch, seal_hatch.getUnlocalizedName());
		
		GameRegistry.registerBlock(vault_door, ItemBlockUnstackable.class, vault_door.getUnlocalizedName());
		GameRegistry.registerBlock(vault_door_dummy, vault_door_dummy.getUnlocalizedName());

		GameRegistry.registerBlock(hydro_wall, hydro_wall.getUnlocalizedName());
		GameRegistry.registerBlock(hydro_turbine, hydro_turbine.getUnlocalizedName());
		GameRegistry.registerBlock(hydro_core, hydro_core.getUnlocalizedName());

		GameRegistry.registerBlock(machine_net, ItemBlockLore.class, machine_net.getUnlocalizedName());
		GameRegistry.registerBlock(machine_uni, ItemBlockLore.class, machine_uni.getUnlocalizedName());
		
		GameRegistry.registerBlock(vent_chlorine_seal, vent_chlorine_seal.getUnlocalizedName());
		GameRegistry.registerBlock(chlorine_gas, chlorine_gas.getUnlocalizedName());
		
		GameRegistry.registerBlock(machine_radar, ItemBlockUnstackable.class, machine_radar.getUnlocalizedName());
		GameRegistry.registerBlock(machine_siren, machine_siren.getUnlocalizedName());
		GameRegistry.registerBlock(machine_forcefield, ItemBlockUnstackable.class, machine_forcefield.getUnlocalizedName());
		GameRegistry.registerBlock(machine_emp, ItemBlockUnstackable.class, machine_emp.getUnlocalizedName());
		GameRegistry.registerBlock(machine_derrick, ItemBlockUnstackable.class, machine_derrick.getUnlocalizedName());
		GameRegistry.registerBlock(machine_refinery, ItemBlockUnstackable.class, machine_refinery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_tank, ItemTankBlock.class, machine_tank.getUnlocalizedName());
		GameRegistry.registerBlock(machine_market, ItemBlockUnstackable.class, machine_market.getUnlocalizedName());
		GameRegistry.registerBlock(builder, ItemBlockUnstackable.class, builder.getUnlocalizedName());

		GameRegistry.registerBlock(rbmk_element, rbmk_element.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_rods, rbmk_rods.getUnlocalizedName());

		GameRegistry.registerBlock(block_graphite, block_graphite.getUnlocalizedName());
		GameRegistry.registerBlock(block_boron, block_boron.getUnlocalizedName());
		
		GameRegistry.registerBlock(display, display.getUnlocalizedName());
		
		GameRegistry.registerBlock(oil_duct, oil_duct.getUnlocalizedName());
		GameRegistry.registerBlock(gas_duct, gas_duct.getUnlocalizedName());

		GameRegistry.registerBlock(launch_pad, ItemBlockUnstackable.class, launch_pad.getUnlocalizedName());
		GameRegistry.registerBlock(railgun_plasma, ItemBlockUnstackable.class, railgun_plasma.getUnlocalizedName());
		GameRegistry.registerBlock(cannon_naval, ItemBlockUnstackable.class, cannon_naval.getUnlocalizedName());
		
		GameRegistry.registerBlock(clowder_flag, ItemBlockUnstackable.class, clowder_flag.getUnlocalizedName());
		GameRegistry.registerBlock(clowder_flag_big, ItemBlockUnstackable.class, clowder_flag_big.getUnlocalizedName());
		GameRegistry.registerBlock(clowder_cap, ItemBlockUnstackable.class, clowder_cap.getUnlocalizedName());
		
		GameRegistry.registerBlock(hesco_block, ItemBlockLore.class, hesco_block.getUnlocalizedName());
		GameRegistry.registerBlock(palisade, ItemBlockLore.class, palisade.getUnlocalizedName());
		GameRegistry.registerBlock(stone_wall, ItemBlockLore.class, stone_wall.getUnlocalizedName());
		GameRegistry.registerBlock(brick_wall, ItemBlockLore.class, brick_wall.getUnlocalizedName());
		GameRegistry.registerBlock(great_wall, ItemBlockLore.class, great_wall.getUnlocalizedName());
		GameRegistry.registerBlock(berlin_wall, ItemBlockLore.class, berlin_wall.getUnlocalizedName());
		GameRegistry.registerBlock(med_tent, ItemBlockLore.class, med_tent.getUnlocalizedName());
		GameRegistry.registerBlock(tp_tent, ItemBlockLore.class, tp_tent.getUnlocalizedName());
		GameRegistry.registerBlock(statue, ItemBlockLore.class, statue.getUnlocalizedName());

		GameRegistry.registerBlock(debug, ItemBlockUnstackable.class, debug.getUnlocalizedName());
	}
}
