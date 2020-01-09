package com.hfr.render.entity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMissilePegasus extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		if(p_76986_1_.getDataWatcher().getWatchableObjectInt(9) == 1)
			return;
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
        
        bindTexture(ResourceManager.slbm_pegasus_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.slbm_pegasus.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.slbm_pegasus_tex;
	}
}
