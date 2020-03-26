package com.hfr.render.mob;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelPig extends ModelBase {
	public ModelRenderer neck;
	public ModelRenderer body;
	public ModelRenderer BackRightLeg;
	public ModelRenderer BackLeftLeg;
	public ModelRenderer FrontRightLeg;
	public ModelRenderer FrontLeftLeg;
	public ModelRenderer tail;
	public ModelRenderer Udders1;
	public ModelRenderer Udders2;
	public ModelRenderer Udders3;
	public ModelRenderer Udders4;
	public ModelRenderer Udders5;
	public ModelRenderer Udders6;
	public ModelRenderer Udders7;
	public ModelRenderer Udders8;
	public ModelRenderer head;
	public ModelRenderer head2;
	public ModelRenderer head3;
	public ModelRenderer head4;
	public ModelRenderer rightear;
	public ModelRenderer leftear;
	public ModelRenderer rightear2;
	public ModelRenderer leftear2;
	public ModelRenderer tail2;
	public ModelRenderer tail3;

	public ModelPig() {
		this.textureHeight = 128;
		this.textureWidth = 128;
		this.BackRightLeg = new ModelRenderer(this, 100, 29);
		this.BackRightLeg.setRotationPoint(-2.5F, 16.0F, 8.5F);
		this.BackRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
		this.Udders7 = new ModelRenderer(this, 10, 20);
		this.Udders7.setRotationPoint(-2.5F, 15.5F, 2.0F);
		this.Udders7.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.rightear = new ModelRenderer(this, 25, 0);
		this.rightear.mirror = true;
		this.rightear.setRotationPoint(-4.0F, -5.53F, -7.4F);
		this.rightear.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
		setRotateAngle(this.rightear, 0.15707964F, -0.57648224F, 0.68416905F);
		this.FrontLeftLeg = new ModelRenderer(this, 116, 29);
		this.FrontLeftLeg.setRotationPoint(2.5F, 16.0F, -4.5F);
		this.FrontLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
		this.Udders3 = new ModelRenderer(this, 10, 20);
		this.Udders3.setRotationPoint(1.5F, 15.5F, 2.0F);
		this.Udders3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.Udders6 = new ModelRenderer(this, 10, 20);
		this.Udders6.setRotationPoint(-2.5F, 15.5F, 0.0F);
		this.Udders6.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.Udders1 = new ModelRenderer(this, 10, 20);
		this.Udders1.setRotationPoint(1.5F, 15.5F, -2.0F);
		this.Udders1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.head2 = new ModelRenderer(this, 37, 37);
		this.head2.setRotationPoint(1.5F, -1.5F, -3.0F);
		this.head2.addBox(-4.0F, -4.0F, -8.0F, 3, 3, 5, 0.0F);
		setRotateAngle(this.head2, 0.715585F, -0.0F, 0.0F);
		this.tail = new ModelRenderer(this, 44, 0);
		this.tail.setRotationPoint(2.0F, 9.5F, 11.6F);
		this.tail.addBox(-2.5F, 0.0F, -2.0F, 1, 1, 4, 0.0F);
		setRotateAngle(this.tail, -0.9599311F, -0.0F, 0.0F);
		this.rightear2 = new ModelRenderer(this, 25, 10);
		this.rightear2.setRotationPoint(-0.9F, 0.6F, -1.1F);
		this.rightear2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		setRotateAngle(this.rightear2, 0.0F, -0.6981317F, 0.0F);
		this.BackLeftLeg = new ModelRenderer(this, 100, 29);
		this.BackLeftLeg.setRotationPoint(2.5F, 16.0F, 8.5F);
		this.BackLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
		this.Udders2 = new ModelRenderer(this, 10, 20);
		this.Udders2.setRotationPoint(1.5F, 15.5F, 0.0F);
		this.Udders2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.body = new ModelRenderer(this, 28, 54);
		this.body.setRotationPoint(0.5F, 17.4F, -7.5F);
		this.body.addBox(-5.0F, -10.0F, 0.0F, 9, 10, 18, 0.0F);
		this.FrontRightLeg = new ModelRenderer(this, 116, 29);
		this.FrontRightLeg.setRotationPoint(-2.5F, 16.0F, -4.5F);
		this.FrontRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
		this.neck = new ModelRenderer(this, 70, 17);
		this.neck.setRotationPoint(0.0F, 11.6F, -1.2F);
		this.neck.addBox(-3.5F, -4.0F, -8.0F, 7, 8, 8, 0.0F);
		setRotateAngle(this.neck, -0.08726646F, -0.0F, 0.0F);
		this.head4 = new ModelRenderer(this, 78, 37);
		this.head4.setRotationPoint(1.5F, 6.5F, -3.3F);
		this.head4.addBox(-4.0F, -4.0F, -8.0F, 3, 1, 5, 0.0F);
		setRotateAngle(this.head4, 0.06981317F, -0.0F, 0.0F);
		this.leftear2 = new ModelRenderer(this, 25, 10);
		this.leftear2.setRotationPoint(3.4F, 0.6F, 0.3F);
		this.leftear2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
		setRotateAngle(this.leftear2, 0.0F, 0.6981317F, 0.0F);
		this.Udders4 = new ModelRenderer(this, 10, 20);
		this.Udders4.setRotationPoint(1.5F, 15.5F, 4.0F);
		this.Udders4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.Udders5 = new ModelRenderer(this, 10, 20);
		this.Udders5.setRotationPoint(-2.5F, 15.5F, -2.0F);
		this.Udders5.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.Udders8 = new ModelRenderer(this, 10, 20);
		this.Udders8.setRotationPoint(-2.5F, 15.5F, 4.0F);
		this.Udders8.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(1.0F, -0.4F, -4.6F);
		this.head.addBox(-4.0F, -4.0F, -8.0F, 6, 8, 6, 0.0F);
		setRotateAngle(this.head, 0.08726646F, -0.0F, 0.0F);
		this.head3 = new ModelRenderer(this, 55, 37);
		this.head3.setRotationPoint(1.0F, 3.5F, -4.0F);
		this.head3.addBox(-4.0F, -4.0F, -8.0F, 4, 3, 6, 0.0F);
		setRotateAngle(this.head3, 0.08726646F, -0.0F, 0.0F);
		this.tail2 = new ModelRenderer(this, 56, 0);
		this.tail2.setRotationPoint(-2.5F, 0.5F, 1.1F);
		this.tail2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		setRotateAngle(this.tail2, 1.4482743F, -0.0F, 0.0F);
		this.leftear = new ModelRenderer(this, 25, 0);
		this.leftear.mirror = true;
		this.leftear.setRotationPoint(-0.9F, -3.23F, -5.0F);
		this.leftear.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
		setRotateAngle(this.leftear, 0.15707964F, 0.57648224F, -0.68416905F);
		this.tail3 = new ModelRenderer(this, 56, 0);
		this.tail3.setRotationPoint(-2.5F, -3.3F, -0.6F);
		this.tail3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		setRotateAngle(this.tail3, -0.28972465F, -0.0F, 0.0F);
		this.head.addChild(this.rightear);
		this.head.addChild(this.head2);
		this.rightear.addChild(this.rightear2);
		this.head.addChild(this.head4);
		this.leftear.addChild(this.leftear2);
		this.neck.addChild(this.head);
		this.head.addChild(this.head3);
		this.tail.addChild(this.tail2);
		this.head.addChild(this.leftear);
		this.tail.addChild(this.tail3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		GL11.glPushMatrix();
		
		if(((EntityLivingBase)entity).isChild()) {
			GL11.glTranslatef(0F, 0.75F, 0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}
		
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.BackRightLeg.render(f5);
		this.Udders7.render(f5);
		this.FrontLeftLeg.render(f5);
		this.Udders3.render(f5);
		this.Udders6.render(f5);
		this.Udders1.render(f5);
		this.tail.render(f5);
		this.BackLeftLeg.render(f5);
		this.Udders2.render(f5);
		this.body.render(f5);
		this.FrontRightLeg.render(f5);
		this.neck.render(f5);
		this.Udders4.render(f5);
		this.Udders5.render(f5);
		this.Udders8.render(f5);
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		this.neck.rotateAngleX = (p_78087_5_ / 57.295776F - 0.08F);
		this.neck.rotateAngleY = (p_78087_4_ / 159.15494F);
		this.FrontRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
		this.FrontLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
		this.BackLeftLeg.rotateAngleX = this.FrontRightLeg.rotateAngleX;
		this.BackRightLeg.rotateAngleX = this.FrontLeftLeg.rotateAngleX;
		this.body.rotateAngleX = 0.05609987F;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
