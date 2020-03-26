package com.hfr.render.model;

import org.lwjgl.opengl.GL11;

import com.hfr.tmt.ModelCustomArmor;
import com.hfr.tmt.ModelRendererTurbo;

import net.minecraft.entity.Entity;

public class ModelBoots extends ModelCustomArmor {

	public ModelBoots()
	{
		leftLegModel = new ModelRendererTurbo[3];
		rightLegModel = new ModelRendererTurbo[3];
		
		textureWidth = 512;
		textureHeight = 512;
		
		float scale = 0.1F;

		leftLegModel[0] = new ModelRendererTurbo(this, 161, 17); // Import_Box107
		leftLegModel[0].addBox(0F - 2F, 21F - 12F, -2.75F, 4, 1, 1, scale, 1);
		leftLegModel[0].setRotationPoint(0F, 0F, 0F);
		leftLegModel[0].setTextureSize(512, 512);
		leftLegModel[0].mirror = true;
		leftLegModel[1] = new ModelRendererTurbo(this, 169, 17); // Import_Box114
		leftLegModel[1].addBox(0F - 2F, 19.05F - 12F, -2F, 4, 5, 4, scale, 1);
		leftLegModel[1].setRotationPoint(0F, 0F, 0F);
		leftLegModel[1].setTextureSize(512, 512);
		leftLegModel[1].mirror = true;
		leftLegModel[2] = new ModelRendererTurbo(this, 185, 17); // Import_Box78
		leftLegModel[2].addBox(0F - 2F, 22F - 12F, -2.75F, 4, 2, 1, scale, 1);
		leftLegModel[2].setRotationPoint(0F, 0F, 0F);
		leftLegModel[2].setTextureSize(512, 512);
		leftLegModel[2].mirror = true;
		rightLegModel[0] = new ModelRendererTurbo(this, 217, 17); // Import_Box115
		rightLegModel[0].addBox(-4F + 2F, 19.05F - 12F, -2F, 4, 5, 4, scale, 1);
		rightLegModel[0].setRotationPoint(0F, 0F, 0F);
		rightLegModel[0].setTextureSize(512, 512);
		rightLegModel[0].mirror = true;
		rightLegModel[1] = new ModelRendererTurbo(this, 209, 17); // Import_Box76
		rightLegModel[1].addBox(-4F + 2F, 22F - 12F, -2.75F, 4, 2, 1, scale, 1);
		rightLegModel[1].setRotationPoint(0F, 0F, 0F);
		rightLegModel[1].setTextureSize(512, 512);
		rightLegModel[1].mirror = true;
		rightLegModel[2] = new ModelRendererTurbo(this, 233, 17); // Import_Box79
		rightLegModel[2].addBox(-4F + 2F, 21F - 12F, -2.75F, 4, 1, 1, scale, 1);
		rightLegModel[2].setRotationPoint(0F, 0F, 0F);
		rightLegModel[2].setTextureSize(512, 512);
		rightLegModel[2].mirror = true;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glPushMatrix();
		super.render(entity, f, f1, f2, f3, f4, f5);
		GL11.glPopMatrix();
	}
}
