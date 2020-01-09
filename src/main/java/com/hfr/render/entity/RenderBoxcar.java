package com.hfr.render.entity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBoxcar extends Render {
	
	public RenderBoxcar() { }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 1, (float)p_76986_6_);

		GL11.glRotatef((System.currentTimeMillis() / 5) % 360, 0F, 1F, 0F);
        
        bindTexture(ResourceManager.boxcar_tex);
        ResourceManager.boxcar.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.boxcar_tex;
	}
}
