//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 28.11.2019 - 14:55:00
// Last changed on: 28.11.2019 - 14:55:00

package com.hfr.render.tmt; //Path where the model is located

import com.hfr.tmt.ModelItemHolder;
import com.hfr.tmt.ModelRendererTurbo;

public class ModelGrainmill extends ModelItemHolder //Same as Filename
{
	public static final ModelGrainmill instance = new ModelGrainmill();
	
	int textureX = 512;
	int textureY = 512;

	public ModelGrainmill() //Same as Filename
	{
		baseModel = new ModelRendererTurbo[82];
		baseModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 1
		baseModel[1] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 4
		baseModel[2] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 5
		baseModel[3] = new ModelRendererTurbo(this, 273, 1, textureX, textureY); // Box 7
		baseModel[4] = new ModelRendererTurbo(this, 329, 1, textureX, textureY); // Box 8
		baseModel[5] = new ModelRendererTurbo(this, 385, 1, textureX, textureY); // Box 9
		baseModel[6] = new ModelRendererTurbo(this, 97, 1, textureX, textureY); // Box 10
		baseModel[7] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 11
		baseModel[8] = new ModelRendererTurbo(this, 441, 1, textureX, textureY); // Box 12
		baseModel[9] = new ModelRendererTurbo(this, 465, 1, textureX, textureY); // Box 13
		baseModel[10] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 14
		baseModel[11] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Box 15
		baseModel[12] = new ModelRendererTurbo(this, 185, 1, textureX, textureY); // Box 16
		baseModel[13] = new ModelRendererTurbo(this, 201, 1, textureX, textureY); // Box 17
		baseModel[14] = new ModelRendererTurbo(this, 257, 1, textureX, textureY); // Box 18
		baseModel[15] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 19
		baseModel[16] = new ModelRendererTurbo(this, 273, 1, textureX, textureY); // Box 20
		baseModel[17] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 21
		baseModel[18] = new ModelRendererTurbo(this, 481, 57, textureX, textureY); // Box 22
		baseModel[19] = new ModelRendererTurbo(this, 313, 1, textureX, textureY); // Box 23
		baseModel[20] = new ModelRendererTurbo(this, 489, 1, textureX, textureY); // Box 24
		baseModel[21] = new ModelRendererTurbo(this, 113, 9, textureX, textureY); // Box 25
		baseModel[22] = new ModelRendererTurbo(this, 425, 81, textureX, textureY); // Box 26
		baseModel[23] = new ModelRendererTurbo(this, 1, 97, textureX, textureY); // Box 27
		baseModel[24] = new ModelRendererTurbo(this, 217, 97, textureX, textureY); // Box 28
		baseModel[25] = new ModelRendererTurbo(this, 281, 97, textureX, textureY); // Box 29
		baseModel[26] = new ModelRendererTurbo(this, 345, 97, textureX, textureY); // Box 30
		baseModel[27] = new ModelRendererTurbo(this, 65, 105, textureX, textureY); // Box 31
		baseModel[28] = new ModelRendererTurbo(this, 409, 113, textureX, textureY); // Box 32
		baseModel[29] = new ModelRendererTurbo(this, 129, 121, textureX, textureY); // Box 33
		baseModel[30] = new ModelRendererTurbo(this, 1, 129, textureX, textureY); // Box 34
		baseModel[31] = new ModelRendererTurbo(this, 281, 1, textureX, textureY); // Box 35
		baseModel[32] = new ModelRendererTurbo(this, 369, 1, textureX, textureY); // Box 36
		baseModel[33] = new ModelRendererTurbo(this, 473, 113, textureX, textureY); // Box 37
		baseModel[34] = new ModelRendererTurbo(this, 329, 1, textureX, textureY); // Box 38
		baseModel[35] = new ModelRendererTurbo(this, 425, 1, textureX, textureY); // Box 39
		baseModel[36] = new ModelRendererTurbo(this, 201, 9, textureX, textureY); // Box 40
		baseModel[37] = new ModelRendererTurbo(this, 1, 9, textureX, textureY); // Box 41
		baseModel[38] = new ModelRendererTurbo(this, 193, 121, textureX, textureY); // Box 42
		baseModel[39] = new ModelRendererTurbo(this, 201, 121, textureX, textureY); // Box 43
		baseModel[40] = new ModelRendererTurbo(this, 209, 121, textureX, textureY); // Box 44
		baseModel[41] = new ModelRendererTurbo(this, 217, 129, textureX, textureY); // Box 45
		baseModel[42] = new ModelRendererTurbo(this, 185, 17, textureX, textureY); // Box 46
		baseModel[43] = new ModelRendererTurbo(this, 185, 25, textureX, textureY); // Box 47
		baseModel[44] = new ModelRendererTurbo(this, 185, 33, textureX, textureY); // Box 48
		baseModel[45] = new ModelRendererTurbo(this, 441, 65, textureX, textureY); // Box 49
		baseModel[46] = new ModelRendererTurbo(this, 441, 73, textureX, textureY); // Box 50
		baseModel[47] = new ModelRendererTurbo(this, 473, 89, textureX, textureY); // Box 51
		baseModel[48] = new ModelRendererTurbo(this, 49, 97, textureX, textureY); // Box 52
		baseModel[49] = new ModelRendererTurbo(this, 81, 97, textureX, textureY); // Box 53
		baseModel[50] = new ModelRendererTurbo(this, 265, 97, textureX, textureY); // Box 54
		baseModel[51] = new ModelRendererTurbo(this, 329, 97, textureX, textureY); // Box 55
		baseModel[52] = new ModelRendererTurbo(this, 393, 97, textureX, textureY); // Box 56
		baseModel[53] = new ModelRendererTurbo(this, 49, 105, textureX, textureY); // Box 57
		baseModel[54] = new ModelRendererTurbo(this, 265, 105, textureX, textureY); // Box 58
		baseModel[55] = new ModelRendererTurbo(this, 329, 105, textureX, textureY); // Box 59
		baseModel[56] = new ModelRendererTurbo(this, 393, 105, textureX, textureY); // Box 60
		baseModel[57] = new ModelRendererTurbo(this, 457, 113, textureX, textureY); // Box 61
		baseModel[58] = new ModelRendererTurbo(this, 457, 121, textureX, textureY); // Box 62
		baseModel[59] = new ModelRendererTurbo(this, 225, 129, textureX, textureY); // Box 63
		baseModel[60] = new ModelRendererTurbo(this, 257, 129, textureX, textureY); // Box 64
		baseModel[61] = new ModelRendererTurbo(this, 289, 129, textureX, textureY); // Box 65
		baseModel[62] = new ModelRendererTurbo(this, 321, 129, textureX, textureY); // Box 66
		baseModel[63] = new ModelRendererTurbo(this, 353, 129, textureX, textureY); // Box 67
		baseModel[64] = new ModelRendererTurbo(this, 49, 137, textureX, textureY); // Box 68
		baseModel[65] = new ModelRendererTurbo(this, 81, 137, textureX, textureY); // Box 69
		baseModel[66] = new ModelRendererTurbo(this, 225, 137, textureX, textureY); // Box 70
		baseModel[67] = new ModelRendererTurbo(this, 257, 137, textureX, textureY); // Box 71
		baseModel[68] = new ModelRendererTurbo(this, 289, 137, textureX, textureY); // Box 72
		baseModel[69] = new ModelRendererTurbo(this, 321, 137, textureX, textureY); // Box 73
		baseModel[70] = new ModelRendererTurbo(this, 353, 137, textureX, textureY); // Box 74
		baseModel[71] = new ModelRendererTurbo(this, 473, 137, textureX, textureY); // Box 75
		baseModel[72] = new ModelRendererTurbo(this, 65, 145, textureX, textureY); // Box 76
		baseModel[73] = new ModelRendererTurbo(this, 97, 145, textureX, textureY); // Box 77
		baseModel[74] = new ModelRendererTurbo(this, 225, 145, textureX, textureY); // Box 78
		baseModel[75] = new ModelRendererTurbo(this, 257, 145, textureX, textureY); // Box 79
		baseModel[76] = new ModelRendererTurbo(this, 289, 145, textureX, textureY); // Box 80
		baseModel[77] = new ModelRendererTurbo(this, 321, 145, textureX, textureY); // Box 81
		baseModel[78] = new ModelRendererTurbo(this, 353, 145, textureX, textureY); // Box 82
		baseModel[79] = new ModelRendererTurbo(this, 385, 145, textureX, textureY); // Box 83
		baseModel[80] = new ModelRendererTurbo(this, 417, 145, textureX, textureY); // Box 84
		baseModel[81] = new ModelRendererTurbo(this, 449, 145, textureX, textureY); // Box 85

		baseModel[0].addShapeBox(0F, 0F, 0F, 40, 76, 14, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 1
		baseModel[0].setRotationPoint(-20F, -76F, -7F);

		baseModel[1].addShapeBox(0F, 0F, 0F, 14, 76, 37, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 4
		baseModel[1].setRotationPoint(-7F, -76F, -20F);

		baseModel[2].addShapeBox(0F, 0F, 0F, 13, 76, 13, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 5
		baseModel[2].setRotationPoint(-20F, -76F, -20F);

		baseModel[3].addShapeBox(0F, 0F, 0F, 13, 76, 13, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F); // Box 7
		baseModel[3].setRotationPoint(-20F, -76F, 7F);

		baseModel[4].addShapeBox(0F, 0F, 0F, 13, 76, 13, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F); // Box 8
		baseModel[4].setRotationPoint(7F, -76F, 7F);

		baseModel[5].addShapeBox(0F, 0F, 0F, 13, 76, 13, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 9
		baseModel[5].setRotationPoint(7F, -76F, -20F);

		baseModel[6].addShapeBox(0F, 0F, 0F, 4, 2, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 10
		baseModel[6].setRotationPoint(-2F, -76F, 17F);

		baseModel[7].addShapeBox(0F, 0F, 0F, 4, 2, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 11
		baseModel[7].setRotationPoint(-2F, -65F, 17F);

		baseModel[8].addShapeBox(0F, 0F, 0F, 5, 55, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 12
		baseModel[8].setRotationPoint(-7F, -76F, 17F);

		baseModel[9].addShapeBox(0F, 0F, 0F, 5, 55, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 13
		baseModel[9].setRotationPoint(2F, -76F, 17F);

		baseModel[10].addShapeBox(0F, 0F, 0F, 4, 2, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.75F, 0F, 0F, -0.75F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.75F, 0F, 0F, -0.75F); // Box 14
		baseModel[10].setRotationPoint(-2F, -65F, 20F);

		baseModel[11].addShapeBox(0F, 0F, 0F, 4, 21, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 15
		baseModel[11].setRotationPoint(-2F, -63F, 17F);

		baseModel[12].addShapeBox(0F, 0F, 0F, 4, 11, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 16
		baseModel[12].setRotationPoint(-2F, -32F, 17F);

		baseModel[13].addShapeBox(0F, 0F, 0F, 4, 3, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 17
		baseModel[13].setRotationPoint(-4.5F, -21F, 17F);

		baseModel[14].addShapeBox(0F, 0F, 0F, 4, 3, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F); // Box 18
		baseModel[14].setRotationPoint(0.5F, -21F, 17F);

		baseModel[15].addShapeBox(0F, 0F, 0F, 2, 2, 2, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.75F, 0F, 0F, -0.75F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, -0.75F, -0.5F, 0F, -0.75F); // Box 19
		baseModel[15].setRotationPoint(-1F, -22F, 19F);

		baseModel[16].addShapeBox(0F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.625F, 0F, 0F, -0.625F, -0.125F, -0.5F, 0F, -0.125F, -0.5F, 0F, -0.125F, -0.5F, -0.625F, -0.125F, -0.5F, -0.625F); // Box 20
		baseModel[16].setRotationPoint(-1F, -22F, 20F);

		baseModel[17].addShapeBox(0F, 0F, 0F, 1, 1, 1, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, -0.5F, -0.25F, -0.25F, -0.5F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, -0.5F, -0.25F, -0.25F, -0.5F); // Box 21
		baseModel[17].setRotationPoint(-0.5F, -21.5F, 20F);

		baseModel[18].addShapeBox(0F, 0F, 0F, 10, 21, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.875F, 0F, 0F, -0.875F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.875F, 0F, 0F, -0.875F); // Box 22
		baseModel[18].setRotationPoint(-5F, -21F, 17F);

		baseModel[19].addShapeBox(0F, 0F, 0F, 1, 2, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.625F, 0F, 0F, -0.625F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.625F, 0F, 0F, -0.625F); // Box 23
		baseModel[19].setRotationPoint(2F, -11F, 17F);

		baseModel[20].addShapeBox(0F, 0F, 0F, 2, 21, 3, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F); // Box 24
		baseModel[20].setRotationPoint(-7F, -21F, 17F);

		baseModel[21].addShapeBox(0F, 0F, 0F, 2, 21, 3, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.5F, 0F, 0F); // Box 25
		baseModel[21].setRotationPoint(5F, -21F, 17F);

		baseModel[22].addShapeBox(0F, 0F, 0F, 15, 15, 15, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -15F, -15F, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F); // Box 26
		baseModel[22].setRotationPoint(-22F, -91F, 7F);

		baseModel[23].addShapeBox(0F, 0F, 0F, 15, 15, 15, 0F, -15F, 0F, -15F, 0F, 0F, -15F, 0F, 0F, 0F, -15F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 27
		baseModel[23].setRotationPoint(-22F, -91F, -22F);

		baseModel[24].addShapeBox(0F, 0F, 0F, 15, 15, 15, 0F, 0F, 0F, -15F, -15F, 0F, -15F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 28
		baseModel[24].setRotationPoint(7F, -91F, -22F);

		baseModel[25].addShapeBox(0F, 0F, 0F, 15, 15, 15, 0F, 0F, 0F, 0F, -15F, 0F, 0F, -15F, 0F, -15F, 0F, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, -5F, 0F, -5F, 0F, 0F, 0F); // Box 29
		baseModel[25].setRotationPoint(7F, -91F, 7F);

		baseModel[26].addShapeBox(0F, 0F, 0F, 14, 15, 15, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -15F, 0F, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 30
		baseModel[26].setRotationPoint(-7F, -91F, 7F);

		baseModel[27].addShapeBox(0F, 0F, 0F, 14, 15, 15, 0F, 0F, 0F, -15F, 0F, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 31
		baseModel[27].setRotationPoint(-7F, -91F, -22F);

		baseModel[28].addShapeBox(0F, 0F, 0F, 15, 15, 14, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 32
		baseModel[28].setRotationPoint(-22F, -91F, -7F);

		baseModel[29].addShapeBox(0F, 0F, 0F, 15, 15, 14, 0F, 0F, 0F, 0F, -15F, 0F, 0F, -15F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 33
		baseModel[29].setRotationPoint(7F, -91F, -7F);

		baseModel[30].addShapeBox(0F, 0F, 0F, 14, 8, 14, 0F, -6.5F, 0F, -6.5F, -6.5F, 0F, -6.5F, -6.5F, 0F, -6.5F, -6.5F, 0F, -6.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 34
		baseModel[30].setRotationPoint(-7F, -99F, -7F);

		baseModel[31].addShapeBox(0F, 0F, 0F, 1, 10, 1, 0F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 35
		baseModel[31].setRotationPoint(-0.5F, -109F, -0.5F);

		baseModel[32].addShapeBox(0F, 0F, 0F, 8, 4, 4, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 36
		baseModel[32].setRotationPoint(-4F, -81F, 17F);

		baseModel[33].addShapeBox(0F, 0F, 0F, 8, 7, 11, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 37
		baseModel[33].setRotationPoint(-4F, -88F, 10F);

		baseModel[34].addShapeBox(0F, 0F, 0F, 4, 7, 1, 0F, 1F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 1F, 0F, 0F, -3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F); // Box 38
		baseModel[34].setRotationPoint(0.5F, -88F, 21F);

		baseModel[35].addShapeBox(0F, 0F, 0F, 4, 7, 1, 0F, -4F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, -4F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F, 0F); // Box 39
		baseModel[35].setRotationPoint(-4.5F, -88F, 21F);

		baseModel[36].addShapeBox(0F, 0F, 0F, 2, 2, 5, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F, -0.25F, -0.25F, 0F); // Box 40
		baseModel[36].setRotationPoint(-1F, -84.5F, 21F);

		baseModel[37].addShapeBox(-1F, -1F, 0F, 2, 2, 3, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 41
		baseModel[37].setRotationPoint(0F, -83.5F, 26F);
		baseModel[37].rotateAngleZ = 0.78539816F;

		baseModel[38].addShapeBox(-1F, -1F, -1F, 2, 75, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 42
		baseModel[38].setRotationPoint(0F, -83.5F, 26F);
		baseModel[38].rotateAngleZ = 0.78539816F;

		baseModel[39].addShapeBox(-1F, -75F, -1F, 2, 75, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 43
		baseModel[39].setRotationPoint(0F, -83.5F, 26F);
		baseModel[39].rotateAngleZ = 0.78539816F;

		baseModel[40].addShapeBox(-1F, -75F, -1F, 2, 75, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 44
		baseModel[40].setRotationPoint(0F, -83.5F, 26F);
		baseModel[40].rotateAngleZ = -0.78539816F;

		baseModel[41].addShapeBox(-1F, -1F, -1F, 2, 75, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 45
		baseModel[41].setRotationPoint(0F, -83.5F, 26F);
		baseModel[41].rotateAngleZ = -0.78539816F;

		baseModel[42].addShapeBox(-6F, 14F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 46
		baseModel[42].setRotationPoint(0F, -83.5F, 26F);
		baseModel[42].rotateAngleZ = 0.78539816F;

		baseModel[43].addShapeBox(-6F, 20F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 47
		baseModel[43].setRotationPoint(0F, -83.5F, 26F);
		baseModel[43].rotateAngleZ = 0.78539816F;

		baseModel[44].addShapeBox(-6F, 32F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 48
		baseModel[44].setRotationPoint(0F, -83.5F, 26F);
		baseModel[44].rotateAngleZ = 0.78539816F;

		baseModel[45].addShapeBox(-6F, 26F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 49
		baseModel[45].setRotationPoint(0F, -83.5F, 26F);
		baseModel[45].rotateAngleZ = 0.78539816F;

		baseModel[46].addShapeBox(-6F, 44F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 50
		baseModel[46].setRotationPoint(0F, -83.5F, 26F);
		baseModel[46].rotateAngleZ = 0.78539816F;

		baseModel[47].addShapeBox(-6F, 38F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 51
		baseModel[47].setRotationPoint(0F, -83.5F, 26F);
		baseModel[47].rotateAngleZ = 0.78539816F;

		baseModel[48].addShapeBox(-6F, 56F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 52
		baseModel[48].setRotationPoint(0F, -83.5F, 26F);
		baseModel[48].rotateAngleZ = 0.78539816F;

		baseModel[49].addShapeBox(-6F, 50F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 53
		baseModel[49].setRotationPoint(0F, -83.5F, 26F);
		baseModel[49].rotateAngleZ = 0.78539816F;

		baseModel[50].addShapeBox(-6F, 68F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 54
		baseModel[50].setRotationPoint(0F, -83.5F, 26F);
		baseModel[50].rotateAngleZ = 0.78539816F;

		baseModel[51].addShapeBox(-6F, 62F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 55
		baseModel[51].setRotationPoint(0F, -83.5F, 26F);
		baseModel[51].rotateAngleZ = 0.78539816F;

		baseModel[52].addShapeBox(-6F, -13F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 56
		baseModel[52].setRotationPoint(0F, -83.5F, 26F);
		baseModel[52].rotateAngleZ = 0.78539816F;

		baseModel[53].addShapeBox(-6F, -19F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 57
		baseModel[53].setRotationPoint(0F, -83.5F, 26F);
		baseModel[53].rotateAngleZ = 0.78539816F;

		baseModel[54].addShapeBox(-6F, -67F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 58
		baseModel[54].setRotationPoint(0F, -83.5F, 26F);
		baseModel[54].rotateAngleZ = 0.78539816F;

		baseModel[55].addShapeBox(-6F, -61F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 59
		baseModel[55].setRotationPoint(0F, -83.5F, 26F);
		baseModel[55].rotateAngleZ = 0.78539816F;

		baseModel[56].addShapeBox(-6F, -55F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 60
		baseModel[56].setRotationPoint(0F, -83.5F, 26F);
		baseModel[56].rotateAngleZ = 0.78539816F;

		baseModel[57].addShapeBox(-6F, -49F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 61
		baseModel[57].setRotationPoint(0F, -83.5F, 26F);
		baseModel[57].rotateAngleZ = 0.78539816F;

		baseModel[58].addShapeBox(-6F, -25F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 62
		baseModel[58].setRotationPoint(0F, -83.5F, 26F);
		baseModel[58].rotateAngleZ = 0.78539816F;

		baseModel[59].addShapeBox(-6F, -31F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 63
		baseModel[59].setRotationPoint(0F, -83.5F, 26F);
		baseModel[59].rotateAngleZ = 0.78539816F;

		baseModel[60].addShapeBox(-6F, -37F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 64
		baseModel[60].setRotationPoint(0F, -83.5F, 26F);
		baseModel[60].rotateAngleZ = 0.78539816F;

		baseModel[61].addShapeBox(-6F, -43F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 65
		baseModel[61].setRotationPoint(0F, -83.5F, 26F);
		baseModel[61].rotateAngleZ = 0.78539816F;

		baseModel[62].addShapeBox(-6F, -13F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 66
		baseModel[62].setRotationPoint(0F, -83.5F, 26F);
		baseModel[62].rotateAngleZ = -0.78539816F;

		baseModel[63].addShapeBox(-6F, -19F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 67
		baseModel[63].setRotationPoint(0F, -83.5F, 26F);
		baseModel[63].rotateAngleZ = -0.78539816F;

		baseModel[64].addShapeBox(-6F, -67F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 68
		baseModel[64].setRotationPoint(0F, -83.5F, 26F);
		baseModel[64].rotateAngleZ = -0.78539816F;

		baseModel[65].addShapeBox(-6F, -61F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 69
		baseModel[65].setRotationPoint(0F, -83.5F, 26F);
		baseModel[65].rotateAngleZ = -0.78539816F;

		baseModel[66].addShapeBox(-6F, -55F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 70
		baseModel[66].setRotationPoint(0F, -83.5F, 26F);
		baseModel[66].rotateAngleZ = -0.78539816F;

		baseModel[67].addShapeBox(-6F, -49F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 71
		baseModel[67].setRotationPoint(0F, -83.5F, 26F);
		baseModel[67].rotateAngleZ = -0.78539816F;

		baseModel[68].addShapeBox(-6F, -25F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 72
		baseModel[68].setRotationPoint(0F, -83.5F, 26F);
		baseModel[68].rotateAngleZ = -0.78539816F;

		baseModel[69].addShapeBox(-6F, -31F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 73
		baseModel[69].setRotationPoint(0F, -83.5F, 26F);
		baseModel[69].rotateAngleZ = -0.78539816F;

		baseModel[70].addShapeBox(-6F, -37F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 74
		baseModel[70].setRotationPoint(0F, -83.5F, 26F);
		baseModel[70].rotateAngleZ = -0.78539816F;

		baseModel[71].addShapeBox(-6F, -43F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 75
		baseModel[71].setRotationPoint(0F, -83.5F, 26F);
		baseModel[71].rotateAngleZ = -0.78539816F;

		baseModel[72].addShapeBox(-6F, 14F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 76
		baseModel[72].setRotationPoint(0F, -83.5F, 26F);
		baseModel[72].rotateAngleZ = -0.78539816F;

		baseModel[73].addShapeBox(-6F, 68F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 77
		baseModel[73].setRotationPoint(0F, -83.5F, 26F);
		baseModel[73].rotateAngleZ = -0.78539816F;

		baseModel[74].addShapeBox(-6F, 62F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 78
		baseModel[74].setRotationPoint(0F, -83.5F, 26F);
		baseModel[74].rotateAngleZ = -0.78539816F;

		baseModel[75].addShapeBox(-6F, 56F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 79
		baseModel[75].setRotationPoint(0F, -83.5F, 26F);
		baseModel[75].rotateAngleZ = -0.78539816F;

		baseModel[76].addShapeBox(-6F, 50F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 80
		baseModel[76].setRotationPoint(0F, -83.5F, 26F);
		baseModel[76].rotateAngleZ = -0.78539816F;

		baseModel[77].addShapeBox(-6F, 44F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 81
		baseModel[77].setRotationPoint(0F, -83.5F, 26F);
		baseModel[77].rotateAngleZ = -0.78539816F;

		baseModel[78].addShapeBox(-6F, 38F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 82
		baseModel[78].setRotationPoint(0F, -83.5F, 26F);
		baseModel[78].rotateAngleZ = -0.78539816F;

		baseModel[79].addShapeBox(-6F, 32F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 83
		baseModel[79].setRotationPoint(0F, -83.5F, 26F);
		baseModel[79].rotateAngleZ = -0.78539816F;

		baseModel[80].addShapeBox(-6F, 26F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 84
		baseModel[80].setRotationPoint(0F, -83.5F, 26F);
		baseModel[80].rotateAngleZ = -0.78539816F;

		baseModel[81].addShapeBox(-6F, 20F, -1F, 12, 1, 1, 0F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F, -0.375F, 0F, 0.125F); // Box 85
		baseModel[81].setRotationPoint(0F, -83.5F, 26F);
		baseModel[81].rotateAngleZ = -0.78539816F;



		translateAll(0F, 0F, 0F);


		flipAll();
	}
	
	public void render(float rot)
	{
		float f5 = 1F / 16F;
		int cap = 36;
		
		for(int i = 0; i < baseModel.length; i++) {
			
			if(i > cap)
				baseModel[i].rotateAngleZ += rot;
			
			baseModel[i].render(f5);
			
			if(i > cap)
				baseModel[i].rotateAngleZ -= rot;
		}
	}
}