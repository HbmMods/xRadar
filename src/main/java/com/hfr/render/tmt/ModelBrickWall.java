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

public class ModelBrickWall extends ModelItemHolder //Same as Filename
{
	public static final ModelBrickWall instance = new ModelBrickWall();
	int textureX = 512;
	int textureY = 512;

	public ModelBrickWall() //Same as Filename
	{
		baseModel = new ModelRendererTurbo[36];
		baseModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 1
		baseModel[1] = new ModelRendererTurbo(this, 57, 1, textureX, textureY); // Box 2
		baseModel[2] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 3
		baseModel[3] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 4
		baseModel[4] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 5
		baseModel[5] = new ModelRendererTurbo(this, 281, 1, textureX, textureY); // Box 6
		baseModel[6] = new ModelRendererTurbo(this, 337, 1, textureX, textureY); // Box 7
		baseModel[7] = new ModelRendererTurbo(this, 393, 1, textureX, textureY); // Box 8
		baseModel[8] = new ModelRendererTurbo(this, 449, 1, textureX, textureY); // Box 9
		baseModel[9] = new ModelRendererTurbo(this, 1, 33, textureX, textureY); // Box 10
		baseModel[10] = new ModelRendererTurbo(this, 57, 33, textureX, textureY); // Box 11
		baseModel[11] = new ModelRendererTurbo(this, 113, 33, textureX, textureY); // Box 12
		baseModel[12] = new ModelRendererTurbo(this, 169, 33, textureX, textureY); // Box 13
		baseModel[13] = new ModelRendererTurbo(this, 225, 33, textureX, textureY); // Box 14
		baseModel[14] = new ModelRendererTurbo(this, 281, 33, textureX, textureY); // Box 15
		baseModel[15] = new ModelRendererTurbo(this, 337, 33, textureX, textureY); // Box 16
		baseModel[16] = new ModelRendererTurbo(this, 377, 33, textureX, textureY); // Box 17
		baseModel[17] = new ModelRendererTurbo(this, 417, 33, textureX, textureY); // Box 18
		baseModel[18] = new ModelRendererTurbo(this, 473, 33, textureX, textureY); // Box 19
		baseModel[19] = new ModelRendererTurbo(this, 329, 57, textureX, textureY); // Box 20
		baseModel[20] = new ModelRendererTurbo(this, 369, 57, textureX, textureY); // Box 21
		baseModel[21] = new ModelRendererTurbo(this, 353, 57, textureX, textureY); // Box 22
		baseModel[22] = new ModelRendererTurbo(this, 417, 57, textureX, textureY); // Box 23
		baseModel[23] = new ModelRendererTurbo(this, 465, 57, textureX, textureY); // Box 24
		baseModel[24] = new ModelRendererTurbo(this, 1, 65, textureX, textureY); // Box 25
		baseModel[25] = new ModelRendererTurbo(this, 49, 65, textureX, textureY); // Box 26
		baseModel[26] = new ModelRendererTurbo(this, 97, 65, textureX, textureY); // Box 27
		baseModel[27] = new ModelRendererTurbo(this, 145, 65, textureX, textureY); // Box 28
		baseModel[28] = new ModelRendererTurbo(this, 201, 65, textureX, textureY); // Box 29
		baseModel[29] = new ModelRendererTurbo(this, 257, 65, textureX, textureY); // Box 30
		baseModel[30] = new ModelRendererTurbo(this, 305, 81, textureX, textureY); // Box 31
		baseModel[31] = new ModelRendererTurbo(this, 417, 81, textureX, textureY); // Box 32
		baseModel[32] = new ModelRendererTurbo(this, 1, 89, textureX, textureY); // Box 33
		baseModel[33] = new ModelRendererTurbo(this, 57, 89, textureX, textureY); // Box 34
		baseModel[34] = new ModelRendererTurbo(this, 105, 89, textureX, textureY); // Box 35
		baseModel[35] = new ModelRendererTurbo(this, 153, 89, textureX, textureY); // Box 36

		baseModel[0].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 1
		baseModel[0].setRotationPoint(0F, -9F, 0F);

		baseModel[1].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 2
		baseModel[1].setRotationPoint(0F, -9F, 17F);

		baseModel[2].addShapeBox(0F, 0F, 0F, 8, 8, 17, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 3
		baseModel[2].setRotationPoint(0F, -9F, -18F);

		baseModel[3].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 4
		baseModel[3].setRotationPoint(0F, -18F, -8.5F);

		baseModel[4].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 5
		baseModel[4].setRotationPoint(0F, -18F, 8.5F);

		baseModel[5].addShapeBox(0F, 0F, 0F, 8, 8, 17, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 6
		baseModel[5].setRotationPoint(0F, -27F, -18F);

		baseModel[6].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 7
		baseModel[6].setRotationPoint(0F, -27F, 0F);

		baseModel[7].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 8
		baseModel[7].setRotationPoint(0F, -27F, 17F);

		baseModel[8].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 9
		baseModel[8].setRotationPoint(0F, -54F, -8.5F);

		baseModel[9].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 10
		baseModel[9].setRotationPoint(0F, -54F, 8.5F);

		baseModel[10].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 11
		baseModel[10].setRotationPoint(0F, -45F, 0F);

		baseModel[11].addShapeBox(0F, 0F, 0F, 8, 8, 17, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 12
		baseModel[11].setRotationPoint(0F, -45F, -18F);

		baseModel[12].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 13
		baseModel[12].setRotationPoint(0F, -45F, 17F);

		baseModel[13].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 14
		baseModel[13].setRotationPoint(0F, -36F, -8.5F);

		baseModel[14].addShapeBox(0F, 0F, 0F, 8, 8, 16, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 15
		baseModel[14].setRotationPoint(0F, -36F, 8.5F);

		baseModel[15].addShapeBox(0F, 0F, 0F, 8, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 16
		baseModel[15].setRotationPoint(0F, -18F, -18F);

		baseModel[16].addShapeBox(0F, 0F, 0F, 8, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 17
		baseModel[16].setRotationPoint(0F, -36F, -18F);

		baseModel[17].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 18
		baseModel[17].setRotationPoint(-8.1F, -54F, -18.1F);

		baseModel[18].addShapeBox(0F, 0F, 0F, 8, 8, 8, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 19
		baseModel[18].setRotationPoint(0F, -18F, 25.05F);

		baseModel[19].addShapeBox(0F, 0F, 0F, 8, 8, 8, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 20
		baseModel[19].setRotationPoint(0F, -36F, 25.05F);

		baseModel[20].addShapeBox(0F, 0F, 0F, 8, 8, 8, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 21
		baseModel[20].setRotationPoint(0F, -54F, 25.05F);

		baseModel[21].addBox(0F, 0F, 0F, 6, 54, 50, 0F); // Box 22
		baseModel[21].setRotationPoint(1F, -54F, -17F);

		baseModel[22].addShapeBox(0F, 0F, 0F, 15, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 23
		baseModel[22].setRotationPoint(-8.1F, -45F, -18.1F);

		baseModel[23].addShapeBox(0F, 0F, 0F, 15, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 24
		baseModel[23].setRotationPoint(-8.1F, -36F, -18.1F);

		baseModel[24].addShapeBox(0F, 0F, 0F, 15, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 25
		baseModel[24].setRotationPoint(-8.1F, -27F, -18.1F);

		baseModel[25].addShapeBox(0F, 0F, 0F, 15, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 26
		baseModel[25].setRotationPoint(-8.1F, -18F, -18.1F);

		baseModel[26].addShapeBox(0F, 0F, 0F, 15, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 27
		baseModel[26].setRotationPoint(-8.1F, -9F, -18.1F);

		baseModel[27].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 28
		baseModel[27].setRotationPoint(-8.1F, -45F, 25.5F);

		baseModel[28].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 29
		baseModel[28].setRotationPoint(-8.1F, -54F, 25.5F);

		baseModel[29].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 30
		baseModel[29].setRotationPoint(-8.1F, -36F, 25.5F);

		baseModel[30].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 31
		baseModel[30].setRotationPoint(-8.1F, -27F, 25.5F);

		baseModel[31].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 32
		baseModel[31].setRotationPoint(-8.1F, -18F, 25.5F);

		baseModel[32].addShapeBox(0F, 0F, 0F, 16, 8, 8, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 33
		baseModel[32].setRotationPoint(-8.1F, -9F, 25.5F);

		baseModel[33].addShapeBox(0F, 0F, 0F, 14, 54, 6, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 34
		baseModel[33].setRotationPoint(-7.1F, -54F, -17.1F);

		baseModel[34].addShapeBox(0F, 0F, 0F, 14, 54, 6, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F); // Box 35
		baseModel[34].setRotationPoint(-7F, -54F, 26F);

		baseModel[35].addShapeBox(0F, 0F, 0F, 18, 4, 54, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F); // Box 36
		baseModel[35].setRotationPoint(-9F, -58F, -19.5F);



		translateAll(0F, 0F, 0F);


		flipAll();
	}
}