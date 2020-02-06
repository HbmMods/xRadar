package com.hfr.render.entity;

import org.lwjgl.opengl.GL11;

import com.hfr.entity.missile.*;
import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMissileSpear extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
        
        bindTexture(getEntityTexture(p_76986_1_));
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.slbm_spear.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		if(entity instanceof EntityMissileDevon1)
			return ResourceManager.missile_devon1_tex;
		if(entity instanceof EntityMissileDevon2)
			return ResourceManager.missile_devon2_tex;
		if(entity instanceof EntityMissileDevon3)
			return ResourceManager.missile_devon3_tex;
		if(entity instanceof EntityMissileCruise1)
			return ResourceManager.missile_cruise_at1_tex;
		
		return ResourceManager.slbm_spear_tex;
	}
}
