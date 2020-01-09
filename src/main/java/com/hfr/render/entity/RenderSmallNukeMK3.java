package com.hfr.render.entity;

import org.lwjgl.opengl.GL11;

import com.hfr.entity.EntityNukeCloudSmall;
import com.hfr.lib.RefStrings;
import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSmallNukeMK3 extends Render {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/mush.hmf");
	private IModelCustom blastModel;
    private ResourceLocation blastTexture;
	private static final ResourceLocation ringModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Ring.obj");
	private IModelCustom ringModel;
    private ResourceLocation ringTexture;
	private static final ResourceLocation ringBigModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/RingBig.obj");
	private IModelCustom ringBigModel;
    private ResourceLocation ringBigTexture;
    public float scale = 0;
    public float ring = 0;
    
    public RenderSmallNukeMK3() {
    	blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/fireball.png");
    	ringModel = AdvancedModelLoader.loadModel(ringModelRL);
    	ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	ringBigModel = AdvancedModelLoader.loadModel(ringBigModelRL);
    	ringBigTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	scale = 0;
    	ring = 0;
    }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		render((EntityNukeCloudSmall)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	public void render(EntityNukeCloudSmall cloud, double posX, double posY, double posZ, float fa, float fb) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY + 0.25F, (float)posZ);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);

    	float size = cloud.getDataWatcher().getWatchableObjectFloat(18);
        GL11.glScalef(size, size, size);

		int age = cloud.age;
        int shockScale = age * 4;
        int fireScale = (int)((age - 25) * 1.5);
        
        if(age < 50) {
    		GL11.glPushMatrix();
    		GL11.glColor4f(0.2F, 0.2F, 0.2F, 0.9F);
    		
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	
	        GL11.glScalef(shockScale, shockScale, shockScale);
	        GL11.glScalef(2, 2, 2);
	        
	        for(float i = 0.9F; i <= 1; i += 0.05F) {
	            GL11.glScalef(i, i, i);
	        	ResourceManager.sphere_ruv.renderAll();
	        	ResourceManager.sphere_iuv.renderAll();
	            GL11.glScalef(1/i, 1/i, 1/i);
	        }
	        
			GL11.glDisable(GL11.GL_BLEND);
	        
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
	
	        GL11.glScalef(0.6F, 1F / shockScale * 5, 0.6F);
			ringModel.renderAll();
	        GL11.glScalef(1.1F, 1F, 1.1F);
			ringModel.renderAll();
	        GL11.glScalef(1.1F, 1F, 1.1F);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        if(age >= 50 && age < 150) {
    		GL11.glPushMatrix();
    		GL11.glColor4f(0.4F, 0.15F, 0.0F, 0.9F);
    		
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        GL11.glTranslatef(0, fireScale * 0.75F, 0);
	
	        GL11.glScalef(fireScale * 0.85F, fireScale, fireScale * 0.85F);
	        
	        for(float i = 0.6F; i <= 1; i += 0.2F) {
	            GL11.glScalef(i, i, i);
	        	ResourceManager.sphere_ruv.renderAll();
	            GL11.glScalef(1/i, 1/i, 1/i);
	        }
	        
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        if(age >= 150) {
    		GL11.glPushMatrix();
            bindTexture(blastTexture);
	        GL11.glTranslatef(0, -50, 0);
	        GL11.glScalef(6, 6, 6);
	        GL11.glDisable(GL11.GL_CULL_FACE);
            blastModel.renderAll();
            GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
	        GL11.glScalef(10, 10, 10);
	        float f = 1.8F + (((float)Math.sin(((double)age) / 20 + 90) * 0.25F) * 0.5F);
	        float f1 = 1 + ((float)Math.sin(((double)age) / 10) * 0.15F);
	        GL11.glScalef(f, 1, f);
	        GL11.glTranslatef(0, 3.5F + f1 * 0.25F, 0);
			ringModel.renderAll();
	        GL11.glTranslatef(0, - f1 * 0.25F * 2, 0);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
    		GL11.glPopMatrix();
        }
        
        if(age >= 50) {
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(2, 2, 2);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
			float f = (float)Math.min((age - 50) * 0.5, 20);
	        GL11.glScalef(f, 15, f);
			//ringBigModel.renderAll();
	        GL11.glScalef(1.5F, 1, 1.5F);
	        GL11.glTranslatef(0, -0.15F, 0);
			//ringBigModel.renderAll();
	        GL11.glScalef(1.5F, 1, 1.5F);
	        GL11.glTranslatef(0, -0.15F, 0);
			//ringBigModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
			
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(2, 2, 2);
			GL11.glColor4f(0.6F, 0.6F, 0.6F, 1F);
			float f0 = (float)Math.min((age - 50) * 0.25, 20) * 5F;
	        GL11.glScalef(f0, 15, f0);
	        GL11.glTranslatef(0, 3.5F, 0);
			ringBigModel.renderAll();
	        GL11.glTranslatef(0, 1F, 0);
	        GL11.glScalef(0.65F, 1, 0.65F);
			ringModel.renderAll();
	        //GL11.glTranslatef(0, -2F, 0);
			//ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	//very professional, i love me some null textures /s
	
	//there, fixed it ~bob
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return blastTexture;
	}

}
