package com.hfr.main;

import com.hfr.lib.RefStrings;
import com.hfr.render.loader.HFRWavefrontObject;
import com.hfr.render.loader.S_WavefrontObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ResourceManager {

	public static final int id_palisade = 10002;
	public static final int id_wall = 10003;
	public static final int id_berliner = 10004;
	public static final int id_barbed = 10005;
	public static final int id_sandbags = 10006;
	public static final int id_rope = 10007;

	public static final ResourceLocation universal = new ResourceLocation(RefStrings.MODID, "textures/models/deb.png");
	public static final IModelCustom cylinder = new S_WavefrontObject(new ResourceLocation(RefStrings.MODID, "models/cylinder.obj"));
	
	//RADAR AND FF
	public static final IModelCustom radar_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_base.obj"));
	public static final IModelCustom radar_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_head.obj"));
	public static final IModelCustom field_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/forcefield_top.obj"));
	public static final ResourceLocation radar_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_base.png");
	public static final ResourceLocation radar_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_head.png");
	public static final ResourceLocation forcefield_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_base.png");
	public static final ResourceLocation forcefield_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_top.png");

	//VAULT DOOR
	public static final IModelCustom vault_cog = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_cog.obj"));
	public static final IModelCustom vault_frame = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_frame.obj"));
	public static final IModelCustom vault_teeth = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_teeth.obj"));
	public static final IModelCustom vault_label = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_label.obj"));
	public static final ResourceLocation vault_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_cog.png");
	public static final ResourceLocation vault_frame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_frame.png");
	public static final ResourceLocation vault_label_101_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_101.png");
	public static final ResourceLocation vault_label_87_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_87.png");
	public static final ResourceLocation vault_label_106_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_106.png");
	public static final ResourceLocation stable_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_cog.png");
	public static final ResourceLocation stable_label_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_label.png");
	public static final ResourceLocation stable_99_label_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_99_label.png");
	public static final ResourceLocation vault4_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_cog.png");
	public static final ResourceLocation vault4_label_111_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_111.png");
	public static final ResourceLocation vault4_label_81_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_81.png");
	public static final ResourceLocation lvl9_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/lvl9_cog.png");
	public static final ResourceLocation lvl9_label_tex = new ResourceLocation(RefStrings.MODID, "textures/models/lvl9_label.png");

	//MISSILES
	public static final IModelCustom missileV2 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/missileV2.obj"));
	public static final IModelCustom missileStrong = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/missileGeneric.obj"));
	public static final IModelCustom missileNeon = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/missileNeon.obj"));
	public static final ResourceLocation missileV2_AT_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_AT.png");
	public static final ResourceLocation missileV2_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_HE.png");
	public static final ResourceLocation missileV2_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_IN.png");
	public static final ResourceLocation missileAA_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileAA.png");
	public static final ResourceLocation missileStrong_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_HE.png");
	public static final ResourceLocation missileStrong_EMP_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_EMP.png");
	public static final ResourceLocation missileStrong_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_IN.png");
	public static final ResourceLocation missileHuge_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileBurst.png");
	public static final ResourceLocation missileHuge_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileInferno.png");
	public static final ResourceLocation missileNuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileNuclear.png");
	public static final ResourceLocation missile_decoy_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_decoy.png");
	public static final ResourceLocation missile_devon1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileDevon1.png");
	public static final ResourceLocation missile_devon2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileDevon2.png");
	public static final ResourceLocation missile_devon3_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileDevon3.png");
	public static final ResourceLocation missile_cruise_at1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileCruiseAT.png");
	
	//BLAST SPHERES
	public static final IModelCustom sphere_ruv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_ruv.obj"));
	public static final IModelCustom sphere_iuv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_iuv.obj"));
	
	//DEVON LKW
	public static final IModelCustom devon = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/truck_busted.obj"));
	public static final ResourceLocation devon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/truck_busted.png");
	
	//DERRICK
	public static final IModelCustom derrick = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/derrick.obj"));
	public static final ResourceLocation derrick_tex = new ResourceLocation(RefStrings.MODID, "textures/models/derrick.png");
	
	//REFINERY
	public static final IModelCustom refinery = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/refinery.obj"));
	public static final ResourceLocation refinery_tex = new ResourceLocation(RefStrings.MODID, "textures/models/refinery.png");
	
	//PLASMA RAILGUN
	public static final ResourceLocation railgun_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/railgun_base.png");
	public static final ResourceLocation railgun_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/railgun_rotor.png");
	public static final ResourceLocation railgun_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/railgun_main.png");
	public static final IModelCustom railgun_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/railgun_base.obj"));
	public static final IModelCustom railgun_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/railgun_rotor.obj"));
	public static final IModelCustom railgun_main = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/railgun_main.obj"));
	
	//TOM
	public static final IModelCustom tom_main = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/tom_main.obj"));
	public static final IModelCustom tom_flame = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/tom_flame.hmf"));
	public static final ResourceLocation tom_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tom_main.png");
	public static final ResourceLocation tom_flame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tom_flame.png");
	public static final ResourceLocation tom_flame_o_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tom_flame_o.png");

	//TANK
	public static final IModelCustom tank_main = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/fluidtank_main.obj"));
	public static final IModelCustom tank_label = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/fluidtank_label.obj"));
	public static final ResourceLocation tank_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank.png");
	public static final ResourceLocation tank_0_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_0.png");
	public static final ResourceLocation tank_1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_1.png");
	public static final ResourceLocation tank_2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_2.png");
	public static final ResourceLocation tank_3_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_3.png");
	public static final ResourceLocation tank_4_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_4.png");
	public static final ResourceLocation tank_5_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_5.png");
	public static final ResourceLocation tank_empty_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank_empty.png");
	
	//16" NAVAL CANNON
	public static final IModelCustom naval_base = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/naval_base.obj"));
	public static final IModelCustom naval_main = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/naval_main.obj"));
	public static final IModelCustom naval_cannons = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/naval_cannons.obj"));
	public static final ResourceLocation naval_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/naval_base.png");
	public static final ResourceLocation naval_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/naval_main.png");
	public static final ResourceLocation naval_cannons_tex = new ResourceLocation(RefStrings.MODID, "textures/models/naval_cannons.png");
	
	//NOW LOOK AT THIS NET THAT I JUST FOUND
	public static final IModelCustom net = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/net.obj"));
	public static final ResourceLocation net_tex = new ResourceLocation(RefStrings.MODID, "textures/models/net.png");
	public static final ResourceLocation net_jammed_tex = new ResourceLocation(RefStrings.MODID, "textures/models/net_jammed.png");
	
	//UNI
	public static final IModelCustom uni = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/uni.obj"));
	public static final ResourceLocation uni_tex = new ResourceLocation(RefStrings.MODID, "textures/models/uni.png");
	
	//TEMPLE
	public static final IModelCustom temple = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/props/temple.obj"));
	public static final ResourceLocation temple_tex = new ResourceLocation(RefStrings.MODID, "textures/models/temple.png");
	
	//FACTORY
	public static final IModelCustom factory = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/factory.obj"));
	public static final ResourceLocation factory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/factory.png");
	
	//TRADEPORT
	public static final IModelCustom tradeport = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/tradeport.obj"));
	public static final ResourceLocation tradeport_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tradeport.png");

	//BOXCAR
	public static final IModelCustom boxcar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/boxcar.obj"));
	public static final ResourceLocation boxcar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/boxcar.png");
	
	//RBMK
	public static final IModelCustom rbmk_element = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk_element.obj"));
	public static final IModelCustom rbmk_rods_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk_rods_base.obj"));
	public static final IModelCustom rbmk_rods = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk_rods.obj"));
	
	//SLBMs
	public static final IModelCustom slbm_martin = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/slbm_martin.obj"));
	public static final ResourceLocation slbm_martin_tex = new ResourceLocation(RefStrings.MODID, "textures/models/slbm_martin.png");
	public static final IModelCustom slbm_pegasus = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/slbm_pegasus.obj"));
	public static final ResourceLocation slbm_pegasus_tex = new ResourceLocation(RefStrings.MODID, "textures/models/slbm_pegasus.png");
	public static final IModelCustom slbm_spear = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/slbm_spear.obj"));
	public static final ResourceLocation slbm_spear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/slbm_spear.png");
	
	//EMP TOWER
	public static final IModelCustom emp = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/emp.obj"));
	public static final ResourceLocation emp_tex = new ResourceLocation(RefStrings.MODID, "textures/models/emp.png");
	
	//FLAG POLE
	public static final IModelCustom flag = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/flag.obj"));
	public static final ResourceLocation flag_tex = new ResourceLocation(RefStrings.MODID, "textures/models/flag.png");
	public static final ResourceLocation flag_conq_tex = new ResourceLocation(RefStrings.MODID, "textures/models/flag_conq.png");
	
	//CAP POINT
	public static final IModelCustom cap = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/cap.obj"));
	public static final ResourceLocation cap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cap.png");
	public static final ResourceLocation cap_point_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cap_point.png");
	
	//FLAG CHONK
	public static final IModelCustom flag_big = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/flag_big.obj"));
	
	//FLAREGUN
	public static final IModelCustom flaregun = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/flaregun.obj"));
	public static final ResourceLocation flaregun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/flaregun.png");
	public static final ResourceLocation flaregun_barrel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/flaregun_barrel.png");
	
	//PAK
	public static final IModelCustom pakker = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/pakker.obj"));
	public static final IModelCustom pak_rocket = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/pak_rocket.obj"));
	public static final ResourceLocation pakker_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pakker.png");
	public static final ResourceLocation pakker_grey_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pakker_grey.png");
	
	//BLAST FURNACE
	public static final IModelCustom blast_furnace = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blast_furnace.obj"));
	public static final ResourceLocation furn_wall = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_wall.png");
	public static final ResourceLocation furn_wall_inner = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_wall_inner.png");
	public static final ResourceLocation furn_furnace = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_furnace.png");
	public static final ResourceLocation furn_furnace_on = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_furnace_on.png");
	public static final ResourceLocation furn_furnace_off = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_furnace_off.png");
	public static final ResourceLocation furn_roof = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_slab.png");
	public static final ResourceLocation furn_tower = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_tower.png");
	public static final ResourceLocation furn_tower_inner = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/text_tower_inner.png");
	public static final ResourceLocation furn_lava = new ResourceLocation(RefStrings.MODID, "textures/models/blast_furnace/lava.png");
	
	//COAL MINE
	public static final IModelCustom coal_mine = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/coal_mine.obj"));
	public static final ResourceLocation mine_foundation = new ResourceLocation(RefStrings.MODID, "textures/models/coal_mine/foundation.png");
	public static final ResourceLocation mine_steel = new ResourceLocation(RefStrings.MODID, "textures/models/coal_mine/steel.png");
	public static final ResourceLocation mine_steel_dark = new ResourceLocation(RefStrings.MODID, "textures/models/coal_mine/steel_dark.png");
	public static final ResourceLocation mine_hole = new ResourceLocation(RefStrings.MODID, "textures/models/coal_mine/hole.png");
	public static final ResourceLocation mine_hut = new ResourceLocation(RefStrings.MODID, "textures/models/coal_mine/hut.png");
	public static final ResourceLocation mine_roof = new ResourceLocation(RefStrings.MODID, "textures/models/coal_mine/roof.png");
	
	//COAL GENERATOR
	public static final IModelCustom coal_gen = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/coalgen.obj"));
	public static final ResourceLocation coalgen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/coalgen.png");
	
	//WINDMILL
	public static final IModelCustom windmill = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/windmill.obj"));
	public static final ResourceLocation windmill_tex = new ResourceLocation(RefStrings.MODID, "textures/models/windmill.png");
	
	//WATERWHEEL
	public static final IModelCustom waterwheel = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/waterwheel.obj"));
	public static final ResourceLocation waterwheel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/waterwheel.png");
	
	//RIFT
	public static final IModelCustom rift = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rift.obj"));
	public static final ResourceLocation rift_tex = new ResourceLocation(RefStrings.MODID, "textures/models/rift.png");
	
	//TURBINE
	public static final IModelCustom turbine = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turbine.obj"));
	public static final ResourceLocation turbine_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbine.png");
	
	//FOUNDRY
	public static final IModelCustom foundry = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/numachines/foundry.obj"));
	public static final ResourceLocation foundry_tex = new ResourceLocation(RefStrings.MODID, "textures/models/numachines/foundry.png");
	
	//SAWMILL
	public static final IModelCustom sawmill = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/numachines/sawmill.obj"));
	public static final ResourceLocation sawmill_tex = new ResourceLocation(RefStrings.MODID, "textures/models/numachines/sawmill.png");
	
	//EFURNACE
	public static final IModelCustom efurnace = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/numachines/efurnace.obj"));
	public static final ResourceLocation efurnace_tex = new ResourceLocation(RefStrings.MODID, "textures/models/numachines/efurnace.png");
	
	//DIESELGEN
	public static final IModelCustom diesel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/numachines/dieselgen.obj"));
	public static final ResourceLocation diesel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/numachines/dieselgen.png");
	
	//BLAST DOOR
	public static final IModelCustom blast_door_block = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blast_door_block.obj"));
	public static final ResourceLocation blast_door_block_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_block.png");
	public static final IModelCustom blast_door_slider = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blast_door_slider.obj"));
	public static final ResourceLocation blast_door_slider_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_slider.png");
	public static final IModelCustom blast_door_tooth = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blast_door_tooth.obj"));
	public static final ResourceLocation blast_door_tooth_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_tooth.png");
	public static final IModelCustom blast_door_base = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blast_door_base.obj"));
	public static final ResourceLocation blast_door_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_base.png");
	
	//TMT
	public static final IModelCustom statue = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ModelStatue.obj"));
	public static final IModelCustom statue_damaged = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ModelStatueDamaged.obj"));
	public static final ResourceLocation tmt_berliner = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_berliner.png");
	public static final ResourceLocation tmt_brickwall = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_brickwall.png");
	public static final ResourceLocation tmt_greatwall = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_greatwall.png");
	public static final ResourceLocation tmt_hescoblock = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_hescoblock.png");
	public static final ResourceLocation tmt_medtent = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_medtent.png");
	public static final ResourceLocation tmt_palisade = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_palisade.png");
	public static final ResourceLocation tmt_stonewall = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_stonewall.png");
	public static final ResourceLocation tmt_tptent = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Block_tptent.png");
	public static final ResourceLocation tmt_statue = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/SkinSamothrace.png");
	public static final ResourceLocation tmt_mill = new ResourceLocation(RefStrings.MODID, "textures/models/tmt/Skin_Grainmill.png");
	public static final IModelCustom palisade = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/props/palisade.obj"));
	public static final IModelCustom wall = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/props/wall.obj"));
	public static final IModelCustom berliner = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/props/berliner.obj"));
	public static final IModelCustom barbed_wire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/props/barbed_wire.obj"));
	public static final IModelCustom sandbags = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/props/sandbags.obj"));
}
