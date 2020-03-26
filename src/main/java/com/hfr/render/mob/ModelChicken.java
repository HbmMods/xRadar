package com.hfr.render.mob;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelChicken extends ModelBase {
	
	public ModelRenderer RightLeg2;
	public ModelRenderer LeftLeg2;
	public ModelRenderer Body1;
	public ModelRenderer RightLeg1;
	public ModelRenderer RightLeg3;
	public ModelRenderer LeftLeg1;
	public ModelRenderer LeftLeg3;
	public ModelRenderer Neck;
	public ModelRenderer Body3;
	public ModelRenderer RightWing;
	public ModelRenderer LeftWing;
	public ModelRenderer Neck2;
	public ModelRenderer Head;
	public ModelRenderer Beak1;
	public ModelRenderer Beak2;
	public ModelRenderer Crest;
	public ModelRenderer Crest2;
	public ModelRenderer Body4;
	public ModelRenderer Body5;

	public ModelChicken() {
		
	    this.textureWidth = 64;
	    this.textureHeight = 32;
	    
	    this.LeftLeg3 = new ModelRenderer(this, 0, 29);
	    this.LeftLeg3.setRotationPoint(0.0F, -0.1F, 0.0F);
	    this.LeftLeg3.addBox(-1.0F, 3.0F, -4.0F, 3, 0, 3, 0.0F);
	    setRotateAngle(this.LeftLeg3, 0.3403392F, -0.0F, 0.0F);
	    this.RightWing = new ModelRenderer(this, 54, 0);
	    this.RightWing.setRotationPoint(-3.0F, -4.0F, -3.0F);
	    this.RightWing.addBox(-1.0F, -0.13F, -1.0F, 1, 3, 4, 0.0F);
	    setRotateAngle(this.RightWing, 0.10471976F, -0.13962634F, 0.0F);
	    this.Body1 = new ModelRenderer(this, 40, 20);
	    this.Body1.setRotationPoint(0.0F, 20.0F, 0.0F);
	    this.Body1.addBox(-3.0F, -5.53F, -4.0F, 6, 6, 6, 0.0F);
	    this.LeftWing = new ModelRenderer(this, 54, 0);
	    this.LeftWing.setRotationPoint(3.0F, -4.0F, -3.0F);
	    this.LeftWing.addBox(0.0F, -0.13F, -1.0F, 1, 3, 4, 0.0F);
	    setRotateAngle(this.LeftWing, 0.10471976F, 0.13962634F, 0.0F);
	    this.Head = new ModelRenderer(this, 0, 13);
	    this.Head.setRotationPoint(0.0F, -2.2F, -1.73F);
	    this.Head.addBox(-1.5F, -2.87F, -3.0F, 3, 4, 3, 0.0F);
	    setRotateAngle(this.Head, 0.83775806F, 0.0F, 0.0F);
	    this.Beak2 = new ModelRenderer(this, 0, 10);
	    this.Beak2.setRotationPoint(0.0F, 0.3F, 0.27F);
	    this.Beak2.addBox(-0.5F, -1.2F, -4.6F, 1, 1, 2, 0.0F);
	    this.Crest = new ModelRenderer(this, 0, 0);
	    this.Crest.setRotationPoint(0.0F, -0.1F, 0.77F);
	    this.Crest.addBox(0.0F, -4.07F, -3.87F, 0, 2, 3, 0.0F);
	    setRotateAngle(this.Crest, 0.2443461F, -0.0F, 0.0F);
	    this.RightLeg2 = new ModelRenderer(this, 12, 27);
	    this.RightLeg2.setRotationPoint(-1.0F, 21.0F, 0.0F);
	    this.RightLeg2.addBox(-1.5F, -2.0F, -1.4666667F, 2, 3, 2, 0.0F);
	    setRotateAngle(this.RightLeg2, 0.15707964F, -0.0F, 0.0F);
	    this.Neck2 = new ModelRenderer(this, 0, 20);
	    this.Neck2.setRotationPoint(0.0F, -0.4F, -3.53F);
	    this.Neck2.addBox(-2.0F, -3.13F, -2.93F, 4, 4, 3, 0.0F);
	    setRotateAngle(this.Neck2, -0.36651915F, 0.0F, 0.0F);
	    this.Body4 = new ModelRenderer(this, 22, 0);
	    this.Body4.setRotationPoint(0.0F, 1.0F, 0.5F);
	    this.Body4.addBox(-2.0F, -4.13F, 4.5F, 4, 2, 3, 0.0F);
	    setRotateAngle(this.Body4, 0.12217305F, -0.0F, 0.0F);
	    this.RightLeg1 = new ModelRenderer(this, 20, 26);
	    this.RightLeg1.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightLeg1.addBox(-1.0F, -1.5F, -1.0F, 1, 5, 1, 0.0F);
	    setRotateAngle(this.RightLeg1, -0.4886922F, -0.0F, 0.0F);
	    this.Body3 = new ModelRenderer(this, 36, 0);
	    this.Body3.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Body3.addBox(-2.5F, -4.13F, 1.03F, 5, 4, 4, 0.0F);
	    setRotateAngle(this.Body3, 0.54105204F, -0.0F, 0.0F);
	    this.Neck = new ModelRenderer(this, 46, 11);
	    this.Neck.setRotationPoint(0.0F, -0.9F, -2.6F);
	    this.Neck.addBox(-2.5F, -4.13F, -4.0F, 5, 5, 4, 0.0F);
	    setRotateAngle(this.Neck, -0.4537856F, 0.0F, 0.0F);
	    this.Beak1 = new ModelRenderer(this, 0, 10);
	    this.Beak1.setRotationPoint(0.0F, 0.1F, -0.03F);
	    this.Beak1.addBox(-0.5F, -2.07F, -4.2F, 1, 1, 2, 0.0F);
	    setRotateAngle(this.Beak1, 0.2443461F, -0.0F, 0.0F);
	    this.LeftLeg2 = new ModelRenderer(this, 12, 27);
	    this.LeftLeg2.setRotationPoint(1.0F, 21.0F, 0.0F);
	    this.LeftLeg2.addBox(-0.5F, -2.0F, -1.4666667F, 2, 3, 2, 0.0F);
	    setRotateAngle(this.LeftLeg2, 0.15707964F, -0.0F, 0.0F);
	    this.Crest2 = new ModelRenderer(this, 7, 0);
	    this.Crest2.setRotationPoint(0.0F, -0.1F, 0.17F);
	    this.Crest2.addBox(0.0F, -0.73F, -2.87F, 0, 2, 1, 0.0F);
	    this.LeftLeg1 = new ModelRenderer(this, 20, 26);
	    this.LeftLeg1.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftLeg1.addBox(0.0F, -1.47F, -1.0F, 1, 5, 1, 0.0F);
	    setRotateAngle(this.LeftLeg1, -0.4886922F, -0.0F, 0.0F);
	    this.RightLeg3 = new ModelRenderer(this, 0, 29);
	    this.RightLeg3.setRotationPoint(0.0F, -0.1F, 0.0F);
	    this.RightLeg3.addBox(-2.0F, 3.0F, -4.0F, 3, 0, 3, 0.0F);
	    setRotateAngle(this.RightLeg3, 0.3403392F, -0.0F, 0.0F);
	    this.Body5 = new ModelRenderer(this, 24, 5);
	    this.Body5.setRotationPoint(0.0F, 1.2F, 0.0F);
	    this.Body5.addBox(-1.5F, -0.13F, 4.5F, 3, 1, 3, 0.0F);
	    setRotateAngle(this.Body5, 0.54105204F, -0.0F, 0.0F);
	    this.LeftLeg1.addChild(this.LeftLeg3);
	    this.Body1.addChild(this.RightWing);
	    this.Body1.addChild(this.LeftWing);
	    this.Neck2.addChild(this.Head);
	    this.Head.addChild(this.Beak2);
	    this.Head.addChild(this.Crest);
	    this.Neck.addChild(this.Neck2);
	    this.Body3.addChild(this.Body4);
	    this.RightLeg2.addChild(this.RightLeg1);
	    this.Body1.addChild(this.Body3);
	    this.Body1.addChild(this.Neck);
	    this.Head.addChild(this.Beak1);
	    this.Head.addChild(this.Crest2);
	    this.LeftLeg2.addChild(this.LeftLeg1);
	    this.RightLeg1.addChild(this.RightLeg3);
	    this.Body3.addChild(this.Body5);
	  }

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		this.Neck.rotateAngleX = (p_78087_5_ / 57.295776F - 6.8F);
		this.Neck.rotateAngleY = (p_78087_4_ / 63.661976F);
		this.RightLeg2.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
		this.LeftLeg2.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
		this.RightWing.rotateAngleZ = p_78087_3_;
		this.LeftWing.rotateAngleZ = (-p_78087_3_);
		this.Body1.rotateAngleX = 0.05609987F;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

		GL11.glPushMatrix();
		
		if(((EntityLivingBase)entity).isChild()) {
			GL11.glTranslatef(0F, 0.75F, 0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}
		
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Body1.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		this.RightLeg2.render(f5);
		this.LeftLeg2.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
