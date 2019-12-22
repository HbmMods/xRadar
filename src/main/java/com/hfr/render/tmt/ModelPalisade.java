//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 01.12.2019 - 07:53:59
// Last changed on: 01.12.2019 - 07:53:59

package com.hfr.render.tmt; //Path where the model is located

import com.hfr.tmt.ModelItemHolder;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelPalisade extends ModelItemHolder //Same as Filename
{
	public static final ModelPalisade instance = new ModelPalisade();
	int textureX = 512;
	int textureY = 512;

	public ModelPalisade() //Same as Filename
	{
		baseModel = new ModelRendererTurbo[45];
		baseModel[0] = new ModelRendererTurbo(this, 105, 1, textureX, textureY); // Box 1
		baseModel[1] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Box 2
		baseModel[2] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 3
		baseModel[3] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 4
		baseModel[4] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 5
		baseModel[5] = new ModelRendererTurbo(this, 193, 1, textureX, textureY); // Box 6
		baseModel[6] = new ModelRendererTurbo(this, 209, 1, textureX, textureY); // Box 7
		baseModel[7] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 8
		baseModel[8] = new ModelRendererTurbo(this, 249, 1, textureX, textureY); // Box 9
		baseModel[9] = new ModelRendererTurbo(this, 265, 1, textureX, textureY); // Box 10
		baseModel[10] = new ModelRendererTurbo(this, 281, 1, textureX, textureY); // Box 11
		baseModel[11] = new ModelRendererTurbo(this, 297, 1, textureX, textureY); // Box 12
		baseModel[12] = new ModelRendererTurbo(this, 313, 1, textureX, textureY); // Box 13
		baseModel[13] = new ModelRendererTurbo(this, 337, 1, textureX, textureY); // Box 14
		baseModel[14] = new ModelRendererTurbo(this, 353, 1, textureX, textureY); // Box 15
		baseModel[15] = new ModelRendererTurbo(this, 369, 1, textureX, textureY); // Box 16
		baseModel[16] = new ModelRendererTurbo(this, 385, 1, textureX, textureY); // Box 17
		baseModel[17] = new ModelRendererTurbo(this, 401, 1, textureX, textureY); // Box 18
		baseModel[18] = new ModelRendererTurbo(this, 425, 1, textureX, textureY); // Box 19
		baseModel[19] = new ModelRendererTurbo(this, 441, 1, textureX, textureY); // Box 20
		baseModel[20] = new ModelRendererTurbo(this, 457, 1, textureX, textureY); // Box 21
		baseModel[21] = new ModelRendererTurbo(this, 473, 1, textureX, textureY); // Box 22
		baseModel[22] = new ModelRendererTurbo(this, 489, 1, textureX, textureY); // Box 23
		baseModel[23] = new ModelRendererTurbo(this, 121, 65, textureX, textureY); // Box 24
		baseModel[24] = new ModelRendererTurbo(this, 177, 65, textureX, textureY); // Box 25
		baseModel[25] = new ModelRendererTurbo(this, 137, 65, textureX, textureY); // Box 26
		baseModel[26] = new ModelRendererTurbo(this, 209, 65, textureX, textureY); // Box 27
		baseModel[27] = new ModelRendererTurbo(this, 257, 65, textureX, textureY); // Box 28
		baseModel[28] = new ModelRendererTurbo(this, 153, 65, textureX, textureY); // Box 29
		baseModel[29] = new ModelRendererTurbo(this, 297, 65, textureX, textureY); // Box 30
		baseModel[30] = new ModelRendererTurbo(this, 241, 65, textureX, textureY); // Box 31
		baseModel[31] = new ModelRendererTurbo(this, 353, 65, textureX, textureY); // Box 32
		baseModel[32] = new ModelRendererTurbo(this, 377, 65, textureX, textureY); // Box 33
		baseModel[33] = new ModelRendererTurbo(this, 329, 65, textureX, textureY); // Box 34
		baseModel[34] = new ModelRendererTurbo(this, 441, 65, textureX, textureY); // Box 35
		baseModel[35] = new ModelRendererTurbo(this, 417, 65, textureX, textureY); // Box 37
		baseModel[36] = new ModelRendererTurbo(this, 473, 65, textureX, textureY); // Box 38
		baseModel[37] = new ModelRendererTurbo(this, 105, 73, textureX, textureY); // Box 39
		baseModel[38] = new ModelRendererTurbo(this, 489, 73, textureX, textureY); // Box 40
		baseModel[39] = new ModelRendererTurbo(this, 193, 73, textureX, textureY); // Box 41
		baseModel[40] = new ModelRendererTurbo(this, 225, 73, textureX, textureY); // Box 42
		baseModel[41] = new ModelRendererTurbo(this, 281, 73, textureX, textureY); // Box 43
		baseModel[42] = new ModelRendererTurbo(this, 313, 73, textureX, textureY); // Box 44
		baseModel[43] = new ModelRendererTurbo(this, 1, 113, textureX, textureY); // Box 45
		baseModel[44] = new ModelRendererTurbo(this, 401, 73, textureX, textureY); // Box 46

		baseModel[0].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 1
		baseModel[0].setRotationPoint(1F, -60F, -32F);

		baseModel[1].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 2
		baseModel[1].setRotationPoint(1F, -60F, -33F);

		baseModel[2].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 3
		baseModel[2].setRotationPoint(6F, -60F, -32F);

		baseModel[3].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 4
		baseModel[3].setRotationPoint(0F, -60F, -32F);

		baseModel[4].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 5
		baseModel[4].setRotationPoint(1F, -60F, -27F);

		baseModel[5].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 6
		baseModel[5].setRotationPoint(6F, -60F, -25F);

		baseModel[6].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 7
		baseModel[6].setRotationPoint(1F, -60F, -26F);

		baseModel[7].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 8
		baseModel[7].setRotationPoint(1F, -60F, -25F);

		baseModel[8].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 9
		baseModel[8].setRotationPoint(0F, -60F, -25F);

		baseModel[9].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 10
		baseModel[9].setRotationPoint(1F, -60F, -20F);

		baseModel[10].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 11
		baseModel[10].setRotationPoint(6F, -60F, -18F);

		baseModel[11].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 12
		baseModel[11].setRotationPoint(1F, -60F, -19F);

		baseModel[12].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 13
		baseModel[12].setRotationPoint(1F, -60F, -18F);

		baseModel[13].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 14
		baseModel[13].setRotationPoint(0F, -60F, -18F);

		baseModel[14].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 15
		baseModel[14].setRotationPoint(1F, -60F, -13F);

		baseModel[15].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 16
		baseModel[15].setRotationPoint(6F, -60F, -11F);

		baseModel[16].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 17
		baseModel[16].setRotationPoint(1F, -60F, -12F);

		baseModel[17].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 18
		baseModel[17].setRotationPoint(1F, -60F, -11F);

		baseModel[18].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 19
		baseModel[18].setRotationPoint(0F, -60F, -11F);

		baseModel[19].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 20
		baseModel[19].setRotationPoint(1F, -60F, -6F);

		baseModel[20].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 21
		baseModel[20].setRotationPoint(6F, -60F, -4F);

		baseModel[21].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 22
		baseModel[21].setRotationPoint(1F, -60F, -5F);

		baseModel[22].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 23
		baseModel[22].setRotationPoint(1F, -60F, -4F);

		baseModel[23].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 24
		baseModel[23].setRotationPoint(0F, -60F, -4F);

		baseModel[24].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 25
		baseModel[24].setRotationPoint(1F, -60F, 1F);

		baseModel[25].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 26
		baseModel[25].setRotationPoint(6F, -60F, 3F);

		baseModel[26].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 27
		baseModel[26].setRotationPoint(1F, -60F, 2F);

		baseModel[27].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 28
		baseModel[27].setRotationPoint(1F, -60F, 3F);

		baseModel[28].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 29
		baseModel[28].setRotationPoint(0F, -60F, 3F);

		baseModel[29].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 30
		baseModel[29].setRotationPoint(1F, -60F, 8F);

		baseModel[30].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 31
		baseModel[30].setRotationPoint(6F, -60F, 10F);

		baseModel[31].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 32
		baseModel[31].setRotationPoint(1F, -60F, 9F);

		baseModel[32].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 33
		baseModel[32].setRotationPoint(1F, -60F, 10F);

		baseModel[33].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 34
		baseModel[33].setRotationPoint(0F, -60F, 10F);

		baseModel[34].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 35
		baseModel[34].setRotationPoint(1F, -60F, 15F);

		baseModel[35].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 37
		baseModel[35].setRotationPoint(-1F, -60F, 10F);

		baseModel[36].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 38
		baseModel[36].setRotationPoint(-6F, -60F, 9F);

		baseModel[37].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 39
		baseModel[37].setRotationPoint(-7F, -60F, 10F);

		baseModel[38].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 40
		baseModel[38].setRotationPoint(-6F, -60F, 10F);

		baseModel[39].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 41
		baseModel[39].setRotationPoint(-6F, -60F, 15F);

		baseModel[40].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F); // Box 42
		baseModel[40].setRotationPoint(-1F, -60F, -32F);

		baseModel[41].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 43
		baseModel[41].setRotationPoint(-6F, -60F, -33F);

		baseModel[42].addShapeBox(0F, 0F, 0F, 1, 60, 5, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 44
		baseModel[42].setRotationPoint(-7F, -60F, -32F);

		baseModel[43].addBox(0F, 0F, 0F, 5, 60, 5, 0F); // Box 45
		baseModel[43].setRotationPoint(-6F, -60F, -32F);

		baseModel[44].addShapeBox(0F, 0F, 0F, 5, 60, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F); // Box 46
		baseModel[44].setRotationPoint(-6F, -60F, -27F);



		translateAll(0F, 0F, 0F);


		flipAll();
	}
}