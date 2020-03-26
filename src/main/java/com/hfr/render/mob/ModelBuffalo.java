package com.hfr.render.mob;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelBuffalo extends ModelBase {

	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer Body1;
	public ModelRenderer Body2;
	public ModelRenderer Body3;
	public ModelRenderer Body4;
	public ModelRenderer Tail1;
	public ModelRenderer Tail2;
	public ModelRenderer Tail3;
	public ModelRenderer Neck;
	public ModelRenderer Neck2;
	public ModelRenderer Head1;
	public ModelRenderer Head2;
	public ModelRenderer Head3;
	public ModelRenderer Head4;
	public ModelRenderer Mouth;
	public ModelRenderer Horns;
	public ModelRenderer RightHorn1;
	public ModelRenderer RightHorn2;
	public ModelRenderer RightHorn3;
	public ModelRenderer LeftHorn1;
	public ModelRenderer LeftHorn2;
	public ModelRenderer LeftHorn3;
	public ModelRenderer LeftEar;
	public ModelRenderer RightEar;

	public ModelBuffalo() {

		this.textureWidth = 128;
		this.textureHeight = 128;
		
		init();
	}

	public void init() {

		this.Body2 = new ModelRenderer(this, 86, 41);
		this.Body2.setRotationPoint(-5.5F, 1.0F, -2.5F);
		this.Body2.addBox(0.0F, 0.0F, 0.0F, 11, 13, 11, 0.0F);
		this.leg3 = new ModelRenderer(this, 0, 51);
		this.leg3.setRotationPoint(-5.5F, 13.0F, -7.0F);
		this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 11, 4, 0.0F);
		this.Neck = new ModelRenderer(this, 44, 27);
		this.Neck.setRotationPoint(-3.0F, -1.0F, -11.5F);
		this.Neck.addBox(0.0F, 0.0F, 0.0F, 6, 8, 8, 0.0F);
		this.setRotateAngle(Neck, -0.5585053563117981F, -0.0F, 0.0F);
		this.RightHorn2 = new ModelRenderer(this, 26, 7);
		this.RightHorn2.setRotationPoint(-6.099999904632568F, 2.4000000953674316F, -13.366666793823242F);
		this.RightHorn2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
		this.setRotateAngle(RightHorn2, 1.1582098901110676F, -0.41177367950627886F, 0.23056631795234075F);
		this.Horns = new ModelRenderer(this, 68, 0);
		this.Horns.setRotationPoint(-4.0F, -2.5F, -13.0F);
		this.Horns.addBox(0.0F, 0.0F, -3.0F, 8, 2, 2, 0.0F);
		this.setRotateAngle(Horns, 0.3839724361896515F, -0.0F, 0.0F);
		this.leg1 = new ModelRenderer(this, 0, 51);
		this.leg1.setRotationPoint(-5.5F, 13.0F, 10.0F);
		this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 11, 4, 0.0F);
		this.LeftHorn3 = new ModelRenderer(this, 26, 12);
		this.LeftHorn3.setRotationPoint(7.400000095367432F, 0.6000000238418579F, -8.600000381469727F);
		this.LeftHorn3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.setRotateAngle(LeftHorn3, -1.4500154843621946F, -0.1584247978807746F, 1.4743293354898541F);
		this.leg4 = new ModelRenderer(this, 0, 51);
		this.leg4.setRotationPoint(2.5F, 13.0F, -7.0F);
		this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 11, 4, 0.0F);
		this.Neck2 = new ModelRenderer(this, 18, 66);
		this.Neck2.setRotationPoint(-0.5F, 2.0F, -10.0F);
		this.Neck2.addBox(0.0F, 0.0F, 0.0F, 1, 6, 10, 0.0F);
		this.setRotateAngle(Neck2, -1.029744267463684F, -0.0F, 0.0F);
		this.Head1 = new ModelRenderer(this, 36, 0);
		this.Head1.setRotationPoint(-3.5F, 0.20000000298023224F, -18.200000762939453F);
		this.Head1.addBox(0.0F, 0.0F, 0.0F, 7, 7, 7, 0.0F);
		this.setRotateAngle(Head1, 0.22689279913902285F, -0.0F, 0.0F);
		this.Tail3 = new ModelRenderer(this, 0, 38);
		this.Tail3.setRotationPoint(-1.5F, 13.0F, 15.800000190734863F);
		this.Tail3.addBox(0.0F, 0.0F, 0.0F, 3, 4, 3, 0.0F);
		this.RightHorn3 = new ModelRenderer(this, 26, 12);
		this.RightHorn3.setRotationPoint(-7.266666889190674F, 2.866666555404663F, -10.699999809265137F);
		this.RightHorn3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
		this.setRotateAngle(RightHorn3, 1.5282503032490322F, -0.16933681435858544F, 0.2125248989922247F);
		this.Head4 = new ModelRenderer(this, 67, 5);
		this.Head4.setRotationPoint(-2.5F, -0.800000011920929F, -16.5F);
		this.Head4.addBox(0.0F, 0.0F, -9.992007221626409E-15F, 5, 1, 4, 0.0F);
		this.setRotateAngle(Head4, 0.296705961227417F, -0.0F, 0.0F);
		this.Head3 = new ModelRenderer(this, 19, 22);
		this.Head3.setRotationPoint(-2.0F, 3.799999952316284F, -21.5F);
		this.Head3.addBox(0.0F, 0.0F, 0.0F, 4, 1, 5, 0.0F);
		this.setRotateAngle(Head3, 0.4886921942234039F, -0.0F, 0.0F);
		this.RightHorn1 = new ModelRenderer(this, 26, 0);
		this.RightHorn1.setRotationPoint(-4.133333206176758F, -2.066666603088379F, -14.466666221618652F);
		this.RightHorn1.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(RightHorn1, 0.6630041895619488F, 0.6964302544029213F, 1.2159331289254682F);
		this.Mouth = new ModelRenderer(this, 41, 19);
		this.Mouth.setRotationPoint(-2.0F, 7.0F, -20.5F);
		this.Mouth.addBox(0.0F, 0.0F, 0.0F, 4, 1, 6, 0.0F);
		this.setRotateAngle(Mouth, 0.296705961227417F, -0.0F, 0.0F);
		this.leg2 = new ModelRenderer(this, 0, 51);
		this.leg2.setRotationPoint(2.5F, 13.0F, 10.0F);
		this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 11, 4, 0.0F);
		this.Body3 = new ModelRenderer(this, 50, 66);
		this.Body3.setRotationPoint(-6.0F, 0.6000000238418579F, 8.0F);
		this.Body3.addBox(0.0F, 0.0F, 0.0F, 12, 14, 7, 0.0F);
		this.Tail2 = new ModelRenderer(this, 0, 68);
		this.Tail2.setRotationPoint(-1.0F, 8.0F, 15.300000190734863F);
		this.Tail2.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
		this.setRotateAngle(Tail2, 0.15358898043632507F, -0.0F, 0.0F);
		this.LeftHorn1 = new ModelRenderer(this, 26, 0);
		this.LeftHorn1.setRotationPoint(6.9666666984558105F, 0.7666666507720947F, -11.933333396911621F);
		this.LeftHorn1.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(LeftHorn1, -0.6953573235293069F, 0.6213183453305696F, 1.8728489773202548F);
		this.LeftEar = new ModelRenderer(this, 0, 21);
		this.LeftEar.setRotationPoint(-3.299999952316284F, 0.0F, -14.5F);
		this.LeftEar.addBox(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
		this.setRotateAngle(LeftEar, 0.04327177256345749F, -0.0F, 0.5410520434379578F);
		this.LeftHorn2 = new ModelRenderer(this, 26, 7);
		this.LeftHorn2.setRotationPoint(7.5F, 2.299999952316284F, -10.366666793823242F);
		this.LeftHorn2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 2, 0.0F);
		this.setRotateAngle(LeftHorn2, -1.4618412766970814F, -0.5425758804265028F, 2.9382560937730284F);
		this.RightEar = new ModelRenderer(this, 0, 21);
		this.RightEar.setRotationPoint(1.600000023841858F, 1.0F, -14.5F);
		this.RightEar.addBox(0.0F, 0.0F, 0.0F, 2, 7, 1, 0.0F);
		this.setRotateAngle(RightEar, 0.0781783610582352F, -0.0F, -0.5410520434379578F);
		this.Tail1 = new ModelRenderer(this, 0, 68);
		this.Tail1.setRotationPoint(-1.0F, 2.0F, 12.800000190734863F);
		this.Tail1.addBox(0.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
		this.setRotateAngle(Tail1, 0.3839724361896515F, -0.0F, 0.0F);
		this.Body1 = new ModelRenderer(this, 14, 44);
		this.Body1.setRotationPoint(-6.0F, 0.699999988079071F, -9.0F);
		this.Body1.addBox(0.0F, 0.0F, 0.0F, 12, 14, 7, 0.0F);
		this.Body4 = new ModelRenderer(this, 1, 83);
		this.Body4.setRotationPoint(-4.0F, -3.0F, -7.0F);
		this.Body4.addBox(0.0F, 0.0F, 0.0F, 8, 6, 7, 0.0F);
		this.setRotateAngle(Body4, -0.2617993950843811F, -0.0F, 0.0F);
		this.Head2 = new ModelRenderer(this, 0, 0);
		this.Head2.setRotationPoint(-2.5F, 3.5F, -22.0F);
		this.Head2.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
		this.setRotateAngle(Head2, 0.22689279913902285F, -0.0F, 0.0F);

		/*this.Neck.addChild(this.Neck2);
		this.Neck.addChild(this.Head1);
		this.Head1.addChild(this.Head4);
		this.Head1.addChild(this.Head3);
		this.Head1.addChild(this.Head2);
		this.Head1.addChild(this.Mouth);
		this.Head1.addChild(this.LeftEar);
		this.Head1.addChild(this.RightEar);
		this.Head1.addChild(this.Horns);
		this.Head1.addChild(this.LeftHorn1);
		this.Head1.addChild(this.LeftHorn2);
		this.Head1.addChild(this.LeftHorn3);
		this.Head1.addChild(this.RightHorn1);
		this.Head1.addChild(this.RightHorn2);
		this.Head1.addChild(this.RightHorn3);
		this.Body1.addChild(this.Body2);
		this.Body2.addChild(this.Body3);
		this.Body3.addChild(this.Body4);
		this.Body1.addChild(this.leg1);
		this.Body1.addChild(this.leg2);
		this.Body4.addChild(this.leg3);
		this.Body4.addChild(this.leg4);
		this.Body4.addChild(this.Tail1);
		this.Tail1.addChild(this.Tail2);
		this.Tail2.addChild(this.Tail3);*/
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

		GL11.glPushMatrix();

		if (((EntityLivingBase) entity).isChild()) {
			GL11.glTranslatef(0F, 0.75F, 0F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
		}

		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Neck.render(f5);
        this.Neck2.render(f5);
        this.Head1.render(f5);
        this.Head2.render(f5);
        this.Head3.render(f5);
        this.Head4.render(f5);
        this.Mouth.render(f5);
        this.LeftEar.render(f5);
        this.RightEar.render(f5);
        this.Horns.render(f5);
        this.LeftHorn1.render(f5);
        this.LeftHorn2.render(f5);
        this.LeftHorn3.render(f5);
        this.RightHorn1.render(f5);
        this.RightHorn2.render(f5);
        this.RightHorn3.render(f5);
        this.Body1.render(f5);
        this.Body3.render(f5);
        this.Body2.render(f5);
        this.Body4.render(f5);
        this.leg1.render(f5);
        this.leg2.render(f5);
        this.leg3.render(f5);
        this.leg4.render(f5);
        this.Tail1.render(f5);
        this.Tail2.render(f5);
        this.Tail3.render(f5);
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {

		//this.Head1.rotateAngleX = (p_78087_5_ / 57.295776F + 0.5F);
		//this.Neck.rotateAngleY = (p_78087_4_ / 57.295776F);
		this.leg1.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_);
		this.leg2.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.6662F + 3.1415927F) * 1.4F * p_78087_2_);
		this.leg3.rotateAngleX = this.leg1.rotateAngleX;
		this.leg4.rotateAngleX = this.leg2.rotateAngleX;
		//this.Body1.rotateAngleX = 0.05609987F;
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
