//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2019 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Sashimono
// Model Creator: 
// Created on: 07.07.2015 - 15:16:48
// Last changed on: 07.07.2015 - 15:16:48

package com.hfr.render.model; //Path where the model is located

import org.lwjgl.opengl.GL11;

import com.hfr.clowder.ClowderFlag;
import com.hfr.items.ModItems;
import com.hfr.main.ResourceManager;
import com.hfr.tmt.ModelCustomArmor;
import com.hfr.tmt.ModelRendererTurbo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ModelSashimono extends ModelCustomArmor //Same as Filename
{
	int textureX = 64;
	int textureY = 128;

	public ModelSashimono() //Same as Filename
	{
		bodyModel = new ModelRendererTurbo[3];

		initbodyModel_1();
	}

	private void initbodyModel_1()
	{
		bodyModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 87
		bodyModel[1] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 88
		bodyModel[2] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 89
		//bodyModel[3] = new ModelRendererTurbo(this, 9, 17, textureX, textureY); // Box 90

		bodyModel[0].addShapeBox(-0.5F, -31.5F, 4.5F, 1, 40, 1, 0F,0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 87
		bodyModel[0].setRotationPoint(0F, 0F, 0F);

		bodyModel[1].addShapeBox(-0.5F, -31.5F, 5.5F, 1, 1, 12, 0F,0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 88
		bodyModel[1].setRotationPoint(0F, 0F, 0F);

		bodyModel[2].addShapeBox(-0.5F, 0F, 1.5F, 1, 1, 4, 0F,3.3F, 0F, 0.3F, 3.3F, 0F, 0.3F, 0.3F, 0F, 0.3F, 0.3F, 0F, 0.3F, 3.3F, 0F, 0.3F, 3.3F, 0F, 0.3F, 0.3F, 0F, 0.3F, 0.3F, 0F, 0.3F); // Box 89
		bodyModel[2].setRotationPoint(0F, 0F, 0F);

		//bodyModel[3].addShapeBox(-0.5F, -30.5F, 5.5F, 1, 64, 24, 0F,-0.3F, 0F, 0F, -0.3F, 0F, 0F, -0.3F, 0F, -12F, -0.3F, 0F, -12F, -0.3F, -34F, 0F, -0.3F, -34F, 0F, -0.3F, -34F, -12F, -0.3F, -34F, -12F); // Box 90
		//bodyModel[3].setRotationPoint(0F, 0F, 0F);
	}
	
	EntityPlayer ref;
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		
		if(entity instanceof EntityPlayer) {
			ref = (EntityPlayer)entity;
		} else {
			ref = null;
		}
	}
	
	@Override
	public void render(ModelRendererTurbo[] models, ModelRenderer bodyPart, float f5, float scale) {
		
		super.render(models, bodyPart, f5, scale);
		
		if(ref == null)
			return;
		
		ItemStack banner = ref.inventory.armorInventory[0];
		
		if(banner == null || !banner.hasTagCompound())
			return;

		ClowderFlag flag = ClowderFlag.values()[banner.stackTagCompound.getInteger("flag")];
		int color = banner.stackTagCompound.getInteger("color");
        
        if(flag == ClowderFlag.NONE || flag == null) {
        	flag = ClowderFlag.TRICOLOR;
        }

	    int r = ((color & 0xFF0000) >> 16) / 2;
	    int g = ((color & 0xFF00) >> 8) / 2;
	    int b = (color & 0xFF) / 2;

	    GL11.glPushMatrix();
	    GL11.glRotatef(180, 0, 1, 0);
	    GL11.glTranslatef(0F, -1.975F, -5.325F);
	    GL11.glRotatef(90, 1, 0, 0);
	    
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(flag.getFlag());
        GL11.glColor3b((byte)r, (byte)g, (byte)b);
        ResourceManager.flag.renderOnly("Flag");

        Minecraft.getMinecraft().renderEngine.bindTexture(flag.getFlagOverlay());
	    GL11.glColor3b((byte)127, (byte)127, (byte)127);
	    ResourceManager.flag.renderOnly("Flag");
	    GL11.glPopMatrix();
	}
}