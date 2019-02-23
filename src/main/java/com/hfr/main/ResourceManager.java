package com.hfr.main;

import com.hfr.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ResourceManager {

	public static final ResourceLocation universal = new ResourceLocation(RefStrings.MODID, "textures/models/deb.png");
	
	public static final IModelCustom radar_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_base.obj"));
	public static final IModelCustom radar_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_head.obj"));
	public static final IModelCustom field_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/forcefield_top.obj"));
	public static final ResourceLocation radar_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_base.png");
	public static final ResourceLocation radar_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_head.png");
	public static final ResourceLocation forcefield_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_base.png");
	public static final ResourceLocation forcefield_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_top.png");

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
}
