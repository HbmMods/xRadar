//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 02.12.2019 - 09:00:05
// Last changed on: 02.12.2019 - 09:00:05

package com.hfr.render.tmt; //Path where the model is located

import com.hfr.tmt.ModelItemHolder;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelHescoBlock extends ModelItemHolder //Same as Filename
{
	public static final ModelHescoBlock instance = new ModelHescoBlock();
	int textureX = 512;
	int textureY = 512;

	public ModelHescoBlock() //Same as Filename
	{
		baseModel = new ModelRendererTurbo[2];
		baseModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 4
		baseModel[1] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 37

		baseModel[0].addShapeBox(0F, 0F, 0F, 32, 42, 32, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 4
		baseModel[0].setRotationPoint(-8F, -42F, -8F);

		baseModel[1].addShapeBox(0F, 0F, 0F, 30, 2, 30, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 37
		baseModel[1].setRotationPoint(-7F, -42.1F, -7F);



		translateAll(0F, 0F, 0F);


		flipAll();
	}
}