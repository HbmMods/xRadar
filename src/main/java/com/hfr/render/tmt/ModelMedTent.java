//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 07.12.2019 - 20:25:28
// Last changed on: 07.12.2019 - 20:25:28

package com.hfr.render.tmt; //Path where the model is located

import com.hfr.tmt.ModelItemHolder;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelMedTent extends ModelItemHolder //Same as Filename
{
	public static final ModelMedTent instance = new ModelMedTent();
	int textureX = 512;
	int textureY = 512;

	public ModelMedTent() //Same as Filename
	{
		baseModel = new ModelRendererTurbo[16];
		baseModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 4
		baseModel[1] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 1
		baseModel[2] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 31
		baseModel[3] = new ModelRendererTurbo(this, 17, 1, textureX, textureY); // Box 32
		baseModel[4] = new ModelRendererTurbo(this, 33, 1, textureX, textureY); // Box 33
		baseModel[5] = new ModelRendererTurbo(this, 57, 1, textureX, textureY); // Box 34
		baseModel[6] = new ModelRendererTurbo(this, 81, 1, textureX, textureY); // Box 35
		baseModel[7] = new ModelRendererTurbo(this, 97, 1, textureX, textureY); // Box 11
		baseModel[8] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 12
		baseModel[9] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Box 13
		baseModel[10] = new ModelRendererTurbo(this, 153, 1, textureX, textureY); // Box 14
		baseModel[11] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 15
		baseModel[12] = new ModelRendererTurbo(this, 321, 1, textureX, textureY); // Box 18
		baseModel[13] = new ModelRendererTurbo(this, 241, 97, textureX, textureY); // Box 19
		baseModel[14] = new ModelRendererTurbo(this, 1, 121, textureX, textureY); // Box 20
		baseModel[15] = new ModelRendererTurbo(this, 337, 121, textureX, textureY); // Box 21

		baseModel[0].addShapeBox(0F, 0F, 0F, 2, 42, 75, 0F, 15F, 0F, 0F, -8F, 0F, 0F, -8F, 0F, 0F, 15F, 0F, 0F, 0F, 0F, 0F, 8F, 0F, 0F, 8F, 0F, 0F, 0F, 0F, 0F); // Box 4
		baseModel[0].setRotationPoint(2F, -42F, 0F);

		baseModel[1].addShapeBox(0F, 0F, 0F, 2, 42, 75, 0F, -8F, 0F, 0F, 15F, 0F, 0F, 15F, 0F, 0F, -8F, 0F, 0F, 8F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 8F, 0F, 0F); // Box 1
		baseModel[1].setRotationPoint(-56F, -42F, 0F);

		baseModel[2].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 31
		baseModel[2].setRotationPoint(-23F, -60F, 11F);

		baseModel[3].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 32
		baseModel[3].setRotationPoint(-28F, -60F, 10F);

		baseModel[4].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 33
		baseModel[4].setRotationPoint(-28F, -60F, 11F);

		baseModel[5].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 34
		baseModel[5].setRotationPoint(-29F, -60F, 11F);

		baseModel[6].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 35
		baseModel[6].setRotationPoint(-28F, -60F, 16F);

		baseModel[7].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 11
		baseModel[7].setRotationPoint(-23F, -60F, 56F);

		baseModel[8].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 12
		baseModel[8].setRotationPoint(-28F, -60F, 55F);

		baseModel[9].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 13
		baseModel[9].setRotationPoint(-28F, -60F, 56F);

		baseModel[10].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 14
		baseModel[10].setRotationPoint(-29F, -60F, 56F);

		baseModel[11].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 15
		baseModel[11].setRotationPoint(-28F, -60F, 61F);

		baseModel[12].addShapeBox(0F, 0F, 0F, 8, 18, 75, 0F, 0F, 0F, 0F, -7.9F, 0F, 0F, -7.9F, 0F, 0F, 0F, 0F, 0F, 15F, 0F, 0F, -14F, 0F, 0F, -14F, 0F, 0F, 15F, 0F, 0F); // Box 18
		baseModel[12].setRotationPoint(-33F, -60F, 0F);

		baseModel[13].addShapeBox(0F, 0F, 0F, 7, 18, 75, 0F, 0F, 0F, 0F, 0F, 7F, 0F, 0F, 7F, 0F, 0F, 0F, 0F, 6F, 0F, 0F, 0F, -13F, 0F, 0F, -13F, 0F, 6F, 0F, 0F); // Box 19
		baseModel[13].setRotationPoint(-33F, -60F, 0F);

		baseModel[14].addShapeBox(0F, 0F, 0F, 7, 18, 75, 0F, 0F, 7F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 7F, 0F, 0F, -13F, 0F, 6F, 0F, 0F, 6F, 0F, 0F, 0F, -13F, 0F); // Box 20
		baseModel[14].setRotationPoint(-26F, -60F, 0F);

		baseModel[15].addShapeBox(0F, 0F, 0F, 8, 18, 75, 0F, -7.9F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -7.9F, 0F, 0F, -14F, 0F, 0F, 15F, 0F, 0F, 15F, 0F, 0F, -14F, 0F, 0F); // Box 21
		baseModel[15].setRotationPoint(-27F, -60F, 0F);



		translateAll(0F, 0F, 0F);


		flipAll();
	}
}