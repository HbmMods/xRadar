//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Sashimono
// Model Creator: 
// Created on: 07.07.2015 - 15:16:48
// Last changed on: 07.07.2015 - 15:16:48

package com.hfr.render.model; //Path where the model is located

import com.hfr.tmt.ModelCustomArmor;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelSashimono extends ModelCustomArmor //Same as Filename
{
	int textureX = 64;
	int textureY = 128;

	public ModelSashimono() //Same as Filename
	{
		bodyModel = new ModelRendererTurbo[4];

		initbodyModel_1();
	}

	private void initbodyModel_1()
	{
		bodyModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 87
		bodyModel[1] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 88
		bodyModel[2] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 89
		bodyModel[3] = new ModelRendererTurbo(this, 9, 17, textureX, textureY); // Box 90

		bodyModel[0].addShapeBox(-0.5F, -31.5F, 4.5F, 1, 40, 1, 0F,0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 87
		bodyModel[0].setRotationPoint(0F, 0F, 0F);

		bodyModel[1].addShapeBox(-0.5F, -31.5F, 5.5F, 1, 1, 12, 0F,0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 88
		bodyModel[1].setRotationPoint(0F, 0F, 0F);

		bodyModel[2].addShapeBox(-0.5F, 0F, 1.5F, 1, 1, 4, 0F,3.3F, 0F, 0.3F, 3.3F, 0F, 0.3F, 0.3F, 0F, 0.3F, 0.3F, 0F, 0.3F, 3.3F, 0F, 0.3F, 3.3F, 0F, 0.3F, 0.3F, 0F, 0.3F, 0.3F, 0F, 0.3F); // Box 89
		bodyModel[2].setRotationPoint(0F, 0F, 0F);

		bodyModel[3].addShapeBox(-0.5F, -30.5F, 5.5F, 1, 64, 24, 0F,-0.3F, 0F, 0F, -0.3F, 0F, 0F, -0.3F, 0F, -12F, -0.3F, 0F, -12F, -0.3F, -34F, 0F, -0.3F, -34F, 0F, -0.3F, -34F, -12F, -0.3F, -34F, -12F); // Box 90
		bodyModel[3].setRotationPoint(0F, 0F, 0F);
	}
}