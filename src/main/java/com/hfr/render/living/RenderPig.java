package com.hfr.render.living;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

//reusing the cow renderer because it's easier
public class RenderPig extends net.minecraft.client.renderer.entity.RenderCow {
	
    private static final ResourceLocation pigTextures = new ResourceLocation("hfr:textures/entity/pig.png");

	public RenderPig(ModelBase p_i1253_1_, float p_i1253_2_) {
		super(p_i1253_1_, p_i1253_2_);
	}
	
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return pigTextures;
    }
	
}
