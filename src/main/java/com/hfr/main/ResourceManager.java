package com.hfr.main;

import com.hfr.lib.RefStrings;
import com.hfr.render.loader.HFRWavefrontObject;
import com.hfr.render.loader.S_WavefrontObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ResourceManager {

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
	public static final ResourceLocation vault4_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_cog.png");
	public static final ResourceLocation vault4_label_111_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_111.png");
	public static final ResourceLocation vault4_label_81_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_81.png");

	//MISSILES
	public static final IModelCustom missileV2 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileV2.obj"));
	public static final IModelCustom missileStrong = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileGeneric.obj"));
	public static final IModelCustom missileNeon = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileNeon.obj"));
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
	
	//BLAST SPHERES
	public static final IModelCustom sphere_ruv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_ruv.obj"));
	public static final IModelCustom sphere_iuv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_iuv.obj"));
	
	//DEVON LKW
	public static final IModelCustom devon = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/truck_busted.obj"));
	public static final ResourceLocation devon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/truck_busted.png");
	
	//DERRICK
	public static final IModelCustom derrick = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/derrick.obj"));
	public static final ResourceLocation derrick_tex = new ResourceLocation(RefStrings.MODID, "textures/models/derrick.png");
	
	//REFINERY
	public static final IModelCustom refinery = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/refinery.obj"));
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
	public static final IModelCustom naval_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/naval_base.obj"));
	public static final IModelCustom naval_main = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/naval_main.obj"));
	public static final IModelCustom naval_cannons = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/naval_cannons.obj"));
}
