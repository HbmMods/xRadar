//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 02.12.2019 - 09:47:26
// Last changed on: 02.12.2019 - 09:47:26

package com.hfr.render.tmt; //Path where the model is located

import com.hfr.tmt.ModelItemHolder;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelBerliner extends ModelItemHolder //Same as Filename
{
	public static final ModelBerliner instance = new ModelBerliner();
	int textureX = 512;
	int textureY = 512;

	public ModelBerliner() //Same as Filename
	{
		baseModel = new ModelRendererTurbo[13];
		baseModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 22
		baseModel[1] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 34
		baseModel[2] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 35
		baseModel[3] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 3
		baseModel[4] = new ModelRendererTurbo(this, 353, 1, textureX, textureY); // Box 4
		baseModel[5] = new ModelRendererTurbo(this, 161, 57, textureX, textureY); // Box 5
		baseModel[6] = new ModelRendererTurbo(this, 249, 65, textureX, textureY); // Box 6
		baseModel[7] = new ModelRendererTurbo(this, 337, 73, textureX, textureY); // Box 8
		baseModel[8] = new ModelRendererTurbo(this, 393, 89, textureX, textureY); // Box 9
		baseModel[9] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 10
		baseModel[10] = new ModelRendererTurbo(this, 73, 1, textureX, textureY); // Box 11
		baseModel[11] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 12
		baseModel[12] = new ModelRendererTurbo(this, 297, 1, textureX, textureY); // Box 13

		baseModel[0].addBox(0F, 0F, 0F, 7, 97, 50, 0F); // Box 22
		baseModel[0].setRotationPoint(1F, -96F, -17F);

		baseModel[1].addShapeBox(0F, 0F, 0F, 14, 97, 6, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.05F, 0F, 0F, 0.05F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.05F, 0F, 0F, 0.05F); // Box 34
		baseModel[1].setRotationPoint(-7.1F, -96F, -17.1F);

		baseModel[2].addShapeBox(0F, 0F, 0F, 14, 98, 6, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.05F, 0F, 0F, 0.05F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.05F, 0F, 0F, 0.05F); // Box 35
		baseModel[2].setRotationPoint(-7F, -96F, 27F);

		baseModel[3].addShapeBox(0F, 0F, 0F, 14, 3, 50, 0F, 0F, 0F, -0.05F, -7F, 0F, -0.05F, -7F, 0F, -0.05F, 0F, 0F, -0.05F, 0F, 0F, -0.05F, 0F, 0F, -0.05F, 0F, 0F, -0.05F, 0F, 0F, -0.05F); // Box 3
		baseModel[3].setRotationPoint(1F, -3F, -17F);

		baseModel[4].addShapeBox(0F, 0F, 0F, 15, 15, 50, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 4
		baseModel[4].setRotationPoint(-4F, -116F, -17F);

		baseModel[5].addShapeBox(0F, 0F, 0F, 15, 2, 50, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 5
		baseModel[5].setRotationPoint(-4F, -118F, -17F);

		baseModel[6].addShapeBox(0F, 0F, 0F, 15, 2, 50, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F); // Box 6
		baseModel[6].setRotationPoint(-4F, -101F, -17F);

		baseModel[7].addShapeBox(0F, 0F, 0F, 2, 15, 50, 0F, 0F, -4F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F); // Box 8
		baseModel[7].setRotationPoint(-6F, -116F, -17F);

		baseModel[8].addShapeBox(0F, 0F, 0F, 2, 15, 50, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 0F, 0F); // Box 9
		baseModel[8].setRotationPoint(11F, -116F, -17F);

		baseModel[9].addShapeBox(0F, 0F, 0F, 15, 5, 2, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F); // Box 10
		baseModel[9].setRotationPoint(-4F, -101F, -13F);

		baseModel[10].addShapeBox(0F, 0F, 0F, 15, 5, 2, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F); // Box 11
		baseModel[10].setRotationPoint(-4F, -101F, -9F);

		baseModel[11].addShapeBox(0F, 0F, 0F, 15, 5, 2, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F); // Box 12
		baseModel[11].setRotationPoint(-4F, -101F, 27F);

		baseModel[12].addShapeBox(0F, 0F, 0F, 15, 5, 2, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F); // Box 13
		baseModel[12].setRotationPoint(-4F, -101F, 23F);



		translateAll(0F, 0F, 0F);


		flipAll();
	}
}