package com.hfr.render.mob;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelSheep extends ModelBase {

	private float field_78152_i;
	public ModelRenderer tail;
	public ModelRenderer Neck;
	public ModelRenderer body;
	public ModelRenderer BackRightLeg;
	public ModelRenderer BackLeftLeg;
	public ModelRenderer FrontRightLeg;
	public ModelRenderer FrontLeftLeg;
	public ModelRenderer Udders;
	public ModelRenderer head;
	public ModelRenderer head2;
	public ModelRenderer head3;
	public ModelRenderer head4;
	public ModelRenderer head5;
	public ModelRenderer ear1;
	public ModelRenderer ear2;

	public ModelSheep() {
		
		this.textureHeight = 128;
		this.textureWidth = 128;
		this.body = new ModelRenderer(this, 30, 28);
		this.body.setRotationPoint(1.0F, 8.5F, 3.0F);
		this.body.addBox(-5.0F, -10.0F, -7.0F, 8, 15, 8, 0.0F);
		setRotateAngle(this.body, 1.5707964F, -0.0F, 0.0F);
		this.tail = new ModelRenderer(this, 20, 40);
		this.tail.setRotationPoint(-0.5F, 8.5F, 7.0F);
		this.tail.addBox(0.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
		setRotateAngle(this.tail, 0.5235988F, -0.0F, 0.0F);
		this.head4 = new ModelRenderer(this, 0, 23);
		this.head4.setRotationPoint(-3.0F, 1.0F, -10.7F);
		this.head4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 4, 0.0F);
		setRotateAngle(this.head4, 0.0429351F, -0.0F, 0.0F);
		this.Neck = new ModelRenderer(this, 30, 14);
		this.Neck.setRotationPoint(0.0F, 12.0F, -5.0F);
		this.Neck.addBox(-2.0F, -3.0F, -8.5F, 4, 5, 8, 0.0F);
		setRotateAngle(this.Neck, -0.9599311F, 0.0F, 0.0F);
		this.FrontRightLeg = new ModelRenderer(this, 0, 40);
		this.FrontRightLeg.setRotationPoint(-2.5F, 14.0F, -4.5F);
		this.FrontRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
		this.FrontLeftLeg = new ModelRenderer(this, 0, 40);
		this.FrontLeftLeg.setRotationPoint(2.4F, 14.0F, -4.5F);
		this.FrontLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);

		this.BackLeftLeg = new ModelRenderer(this, 0, 40);
		this.BackLeftLeg.setRotationPoint(2.4F, 14.0F, 6.0F);
		this.BackLeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
		this.head3 = new ModelRenderer(this, 81, 31);
		this.head3.setRotationPoint(-3.0F, -1.5F, -10.8F);
		this.head3.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
		setRotateAngle(this.head3, 0.6609562F, -0.0F, 0.0F);
		this.Udders = new ModelRenderer(this, 12, 0);
		this.Udders.setRotationPoint(-1.5F, 14.5F, 3.0F);
		this.Udders.addBox(0.0F, 0.0F, 0.0F, 3, 2, 3, 0.0F);
		this.ear2 = new ModelRenderer(this, 40, 0);
		this.ear2.setRotationPoint(0.8F, -3.8F, -5.0F);
		this.ear2.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
		setRotateAngle(this.ear2, 0.0F, -0.0F, 0.34906584F);

		this.BackRightLeg = new ModelRenderer(this, 0, 40);
		this.BackRightLeg.setRotationPoint(-2.4F, 14.0F, 6.0F);
		this.BackRightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
		this.head2 = new ModelRenderer(this, 0, 15);
		this.head2.setRotationPoint(-3.5F, -1.6F, -11.2F);
		this.head2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
		setRotateAngle(this.head2, 0.031066861F, -0.0F, 0.0F);

		this.head5 = new ModelRenderer(this, 100, 0);
		this.head5.setRotationPoint(-3.5F, -4.7F, -7.0F);
		this.head5.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, 0.0F);
		this.head = new ModelRenderer(this, 70, 0);
		this.head.setRotationPoint(1.5F, -3.3F, -3.6F);
		this.head.addBox(-4.0F, -4.0F, -8.0F, 5, 6, 6, 0.0F);
		setRotateAngle(this.head, 1.0471976F, 0.0F, 0.0F);
		this.ear1 = new ModelRenderer(this, 40, 0);
		this.ear1.setRotationPoint(-6.7F, -2.8F, -5.0F);
		this.ear1.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1, 0.0F);
		setRotateAngle(this.ear1, 0.0F, -0.0F, -0.34906584F);

		this.head.addChild(this.head4);
		this.head.addChild(this.head3);
		this.head.addChild(this.ear2);
		this.head.addChild(this.head2);
		this.head.addChild(this.head5);
		this.Neck.addChild(this.head);
		this.head.addChild(this.ear1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.body.render(f5);
		this.tail.render(f5);
		this.Neck.render(f5);
		this.FrontRightLeg.render(f5);
		this.FrontLeftLeg.render(f5);
		this.BackLeftLeg.render(f5);
		this.Udders.render(f5);
		this.BackRightLeg.render(f5);
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_,
			float p_78087_6_, Entity p_78087_7_) {
		this.Neck.rotateAngleX = (this.field_78152_i - 7.2F);
		this.Neck.rotateAngleY = (p_78087_4_ / 57.295776F);
		this.FrontRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
		this.FrontLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
		this.BackLeftLeg.rotateAngleX = this.FrontRightLeg.rotateAngleX;
		this.BackRightLeg.rotateAngleX = this.FrontLeftLeg.rotateAngleX;
		this.body.rotateAngleX = 1.5707964F;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public static class ModelSheepWooly extends ModelBase {
		public ModelRenderer WoolBody;
		public ModelRenderer WoollegBackRight;
		public ModelRenderer WoollegBackLeft;
		public ModelRenderer WoollegFrontRight;
		public ModelRenderer WoollegFrontLeft;

		public ModelSheepWooly() {
			this.WoollegFrontLeft = new ModelRenderer(this, 0, 75);
			this.WoollegFrontLeft.setRotationPoint(2.5F, 14.0F, -4.5F);
			this.WoollegFrontLeft.addBox(-2.0F, 1.0F, -2.0F, 4, 4, 4, 0.0F);
			this.WoolBody = new ModelRenderer(this, 75, 75);
			this.WoolBody.setRotationPoint(-4.5F, 16.0F, -7.5F);
			this.WoolBody.addBox(0.0F, 0.0F, 0.0F, 9, 16, 9, 0.0F);
			setRotateAngle(this.WoolBody, 1.5707964F, 0.0F, 0.0F);
			this.WoollegBackRight = new ModelRenderer(this, 0, 75);
			this.WoollegBackRight.setRotationPoint(-2.5F, 14.0F, 6.0F);
			this.WoollegBackRight.addBox(-2.0F, 1.0F, -2.0F, 4, 4, 4, 0.0F);
			this.WoollegBackLeft = new ModelRenderer(this, 0, 75);
			this.WoollegBackLeft.setRotationPoint(2.5F, 14.0F, 6.0F);
			this.WoollegBackLeft.addBox(-2.0F, 1.0F, -2.0F, 4, 4, 4, 0.0F);
			this.WoollegFrontRight = new ModelRenderer(this, 0, 75);
			this.WoollegFrontRight.setRotationPoint(-2.5F, 14.0F, -4.5F);
			this.WoollegFrontRight.addBox(-2.0F, 1.0F, -2.0F, 4, 4, 4, 0.0F);
		}

		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			setRotationAngles(f, f1, f2, f3, f4, f5, entity);
			this.WoollegFrontLeft.render(f5);
			this.WoolBody.render(f5);
			this.WoollegBackRight.render(f5);
			this.WoollegBackLeft.render(f5);
			this.WoollegFrontRight.render(f5);
		}

		public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
			this.WoollegFrontRight.rotateAngleX = (MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_);
			this.WoollegFrontLeft.rotateAngleX = (MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_);
			this.WoollegBackLeft.rotateAngleX = this.WoollegFrontRight.rotateAngleX;
			this.WoollegBackRight.rotateAngleX = this.WoollegFrontLeft.rotateAngleX;

			this.WoolBody.rotateAngleX = 1.5707964F;
		}

		public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
			this.WoollegFrontRight.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
			this.WoollegFrontLeft.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
			this.WoollegBackLeft.rotateAngleX = this.WoollegFrontRight.rotateAngleX;
			this.WoollegBackRight.rotateAngleX = this.WoollegFrontLeft.rotateAngleX;

			this.WoolBody.rotateAngleX = 1.5707964F;
		}

		public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}
	}
}
