package com.hfr.render.item;

import org.lwjgl.opengl.GL11;

import com.hfr.items.ItemFlaregun;
import com.hfr.main.ResourceManager;
import com.hfr.util.ParserUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderPak implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		boolean open = ItemFlaregun.getState(item) == 1;
		
		switch(type) {
		case ENTITY:
			GL11.glTranslatef(0, 0.4F, -0.3F);
			GL11.glTranslatef(0, 0, 0.4F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			break;
		case EQUIPPED:
			GL11.glTranslatef(0.4F, 0.4F, -0.1F);
			GL11.glRotatef(15, 0, 1, 0);
			GL11.glRotatef(15, 1, 0, 1);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glTranslatef(1, 0.4F, 0F);
			GL11.glRotatef(25, 0, 0, 1);
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case INVENTORY:
			GL11.glScalef(2.5F, 2.5F, 2.5F);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(0, -3.25F, -3F);
			GL11.glRotatef(45, -1, 0, 0);
			GL11.glScalef(-1, 1, 1);
			break;
		default:
			break;
		
		}

		if(type == ItemRenderType.INVENTORY) GL11.glEnable(GL11.GL_LIGHTING);
		
		int color = 0;
		
		if(item.hasTagCompound())
			color = ParserUtil.parseColor(item.getTagCompound().getString("color"));
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		if(color <= 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.pakker_tex);
		} else {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.pakker_grey_tex);

		    int r = ((color & 0xFF0000) >> 16) / 2;
		    int g = ((color & 0xFF00) >> 8) / 2;
		    int b = (color & 0xFF) / 2;
	        GL11.glColor3b((byte)r, (byte)g, (byte)b);
		}
		
		ResourceManager.pakker.renderAll();
	    GL11.glColor3b((byte)127, (byte)127, (byte)127);
	    
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		if(type == ItemRenderType.INVENTORY) GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();

	}
}
