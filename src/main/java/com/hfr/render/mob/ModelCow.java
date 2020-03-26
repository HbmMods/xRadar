package com.hfr.render.mob;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelCow extends ModelBase {
	public ModelRenderer Body2;
	public ModelRenderer Neck;
	public ModelRenderer Body1;
	public ModelRenderer Body3;
	public ModelRenderer FrontLeftLeg;
	public ModelRenderer FrontRightLeg;
	public ModelRenderer Tail1;
	public ModelRenderer BackRightLeg;
	public ModelRenderer BackLeftLeg;
	public ModelRenderer Tail2;
	public ModelRenderer Tail3;
	public ModelRenderer Head1;
	public ModelRenderer Head2;
	public ModelRenderer Head3;
	public ModelRenderer Head4;
	public ModelRenderer Mouth;
	public ModelRenderer RightEar;
	public ModelRenderer LeftEar;
	public ModelRenderer Horns;
	public ModelRenderer RightHorn;
	public ModelRenderer LeftHorn;

	public ModelCow() {
		this.textureHeight = 128;
		this.textureWidth = 128;
		this.Neck = new ModelRenderer(this, 30, 26);
		this.Neck.setRotationPoint(0.0F, 9.0F, -5.0F);
		this.Neck.addBox(-3.0F, -7.0F, -10.0F, 6, 7, 10, 0.0F);
		setRotateAngle(this.Neck, -0.36651915F, -0.0F, 0.0F);
		this.Head1 = new ModelRenderer(this, 36, 0);
		this.Head1.setRotationPoint(-0.5F, -7.5F, -9.5F);
		this.Head1.addBox(-3.0F, 0.0F, -7.0F, 7, 7, 7, 0.0F);
		setRotateAngle(this.Head1, 0.5934119F, 0.0F, 0.0F);
		this.FrontLeftLeg = new ModelRenderer(this, 0, 51);
		this.FrontLeftLeg.setRotationPoint(9.0F, 12.4F, 4.0F);
		this.FrontLeftLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 11, 4, 0.0F);
		this.LeftHorn = new ModelRenderer(this, 26, 0);
		this.LeftHorn.setRotationPoint(7.7F, -2.7F, -4.5F);
		this.LeftHorn.addBox(-3.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		setRotateAngle(this.LeftHorn, 0.2617994F, -0.0F, 0.5061455F);
		this.Tail3 = new ModelRenderer(this, 0, 38);
		this.Tail3.setRotationPoint(-0.5F, 5.7F, -0.5F);
		this.Tail3.addBox(0.0F, 0.0F, 0.0F, 3, 4, 3, 0.0F);
		setRotateAngle(this.Tail3, -0.15358898F, 0.0F, 0.0F);
		this.BackLeftLeg = new ModelRenderer(this, 0, 51);
		this.BackLeftLeg.setRotationPoint(9.0F, 12.4F, 4.0F);
		this.BackLeftLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 11, 4, 0.0F);
		this.FrontRightLeg = new ModelRenderer(this, 0, 51);
		this.FrontRightLeg.setRotationPoint(2.0F, 12.4F, 4.0F);
		this.FrontRightLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 11, 4, 0.0F);
		this.Body3 = new ModelRenderer(this, 50, 44);
		this.Body3.setRotationPoint(-0.5F, -0.3F, 10.5F);
		this.Body3.addBox(0.0F, 0.0F, 0.0F, 11, 13, 7, 0.0F);
		this.Body1 = new ModelRenderer(this, 14, 44);
		this.Body1.setRotationPoint(-0.5F, -0.3F, -6.5F);
		this.Body1.addBox(0.0F, 0.0F, 0.0F, 11, 13, 7, 0.0F);
		this.Head4 = new ModelRenderer(this, 67, 5);
		this.Head4.setRotationPoint(1.0F, -0.6F, -5.0F);
		this.Head4.addBox(-3.0F, 0.0F, -0.0F, 5, 1, 4, 0.0F);
		setRotateAngle(this.Head4, 0.06981317F, -0.0F, 0.0F);
		this.Mouth = new ModelRenderer(this, 41, 19);
		this.Mouth.setRotationPoint(1.5F, 6.1F, -10.8F);
		this.Mouth.addBox(-3.0F, 0.0F, 0.0F, 4, 1, 6, 0.0F);
		setRotateAngle(this.Mouth, 0.06981317F, -0.0F, 0.0F);
		this.LeftEar = new ModelRenderer(this, 0, 22);
		this.LeftEar.setRotationPoint(6.0F, -0.4F, -2.8F);
		this.LeftEar.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
		setRotateAngle(this.LeftEar, 0.0F, 0.0F, -0.20943952F);
		this.Body2 = new ModelRenderer(this, 86, 41);
		this.Body2.setRotationPoint(-5.0F, 1.0F, -2.5F);
		this.Body2.addBox(0.0F, 0.0F, 0.0F, 10, 12, 11, 0.0F);
		this.Tail1 = new ModelRenderer(this, 0, 68);
		this.Tail1.setRotationPoint(4.5F, 0.9F, 4.8F);
		this.Tail1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
		setRotateAngle(this.Tail1, 0.38397244F, -0.0F, 0.0F);

		this.BackRightLeg = new ModelRenderer(this, 0, 51);
		this.BackRightLeg.setRotationPoint(2.0F, 12.4F, 4.0F);
		this.BackRightLeg.addBox(-1.5F, 0.0F, -2.0F, 3, 11, 4, 0.0F);
		this.Head3 = new ModelRenderer(this, 19, 22);
		this.Head3.setRotationPoint(1.5F, 2.6F, -10.3F);
		this.Head3.addBox(-3.0F, 0.0F, 0.0F, 4, 1, 5, 0.0F);
		setRotateAngle(this.Head3, 0.2617994F, 0.0F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 0, 68);
		this.Tail2.setRotationPoint(0.0F, 6.5F, 0.1F);
		this.Tail2.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
		setRotateAngle(this.Tail2, -0.23038346F, -0.0F, 0.0F);
		this.Head2 = new ModelRenderer(this, 0, 0);
		this.Head2.setRotationPoint(0.0F, 1.0F, -5.9F);
		this.Head2.addBox(-2.0F, 1.33F, -5.5F, 5, 4, 6, 0.0F);
		this.RightEar = new ModelRenderer(this, 0, 31);
		this.RightEar.setRotationPoint(-2.0F, 0.4F, -2.8F);
		this.RightEar.addBox(-3.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
		setRotateAngle(this.RightEar, 0.0F, -0.0F, 0.20943952F);
		this.RightHorn = new ModelRenderer(this, 26, 0);
		this.RightHorn.setRotationPoint(-2.2F, -5.2F, -4.6F);
		this.RightHorn.addBox(-3.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		setRotateAngle(this.RightHorn, 0.2617994F, 0.0F, -0.5061455F);
		this.Horns = new ModelRenderer(this, 68, 0);
		this.Horns.setRotationPoint(0.0F, -1.2F, -3.7F);
		this.Horns.addBox(-3.0F, 0.0F, 0.0F, 7, 1, 1, 0.0F);
		setRotateAngle(this.Horns, 0.2443461F, 0.0F, 0.0F);
		this.Neck.addChild(this.Head1);
		this.Body1.addChild(this.FrontLeftLeg);
		this.Head1.addChild(this.LeftHorn);
		this.Tail2.addChild(this.Tail3);
		this.Body3.addChild(this.BackLeftLeg);
		this.Body1.addChild(this.FrontRightLeg);
		this.Body2.addChild(this.Body3);
		this.Body2.addChild(this.Body1);
		this.Head1.addChild(this.Head4);
		this.Head1.addChild(this.Mouth);
		this.Head1.addChild(this.LeftEar);
		this.Body3.addChild(this.Tail1);
		this.Body3.addChild(this.BackRightLeg);
		this.Head1.addChild(this.Head3);
		this.Tail1.addChild(this.Tail2);
		this.Head1.addChild(this.Head2);
		this.Head1.addChild(this.RightEar);
		this.Head1.addChild(this.RightHorn);
		this.Head1.addChild(this.Horns);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		GL11.glPushMatrix();
		
		if(((EntityLivingBase)entity).isChild()) {
			GL11.glTranslatef(0F, 0.75F, 0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}
		
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Neck.render(f5);
		this.Body2.render(f5);
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		
		this.Head1.rotateAngleX = (p_78087_5_ / 57.295776F + 0.5F);
		this.Neck.rotateAngleY = (p_78087_4_ / 57.295776F);
		this.FrontRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
		this.FrontLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
		this.BackLeftLeg.rotateAngleX = this.FrontRightLeg.rotateAngleX;
		this.BackRightLeg.rotateAngleX = this.FrontLeftLeg.rotateAngleX;
		this.Body1.rotateAngleX = 0.05609987F;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
