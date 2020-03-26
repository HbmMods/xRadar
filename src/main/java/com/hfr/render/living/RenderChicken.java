package com.hfr.render.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderChicken extends net.minecraft.client.renderer.entity.RenderChicken {
	
    private static final ResourceLocation chickenTextures = new ResourceLocation("hfr:textures/entity/chicken.png");

	public RenderChicken(ModelBase p_i1253_1_, float p_i1253_2_) {
		super(p_i1253_1_, p_i1253_2_);
	}
	
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return chickenTextures;
    }
}
