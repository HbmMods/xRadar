package com.hfr.render.item;

import org.lwjgl.opengl.GL11;

import com.hfr.items.ItemFlaregun;
import com.hfr.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderFlaregun implements IItemRenderer {

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
			/*EntityItem it = ((EntityItem)data[1]);
            float f2 = MathHelper.sin(((float)it.age + MainRegistry.smoothing) / 10.0F + it.hoverStart) * 0.1F + 0.1F;
            float f3 = (((float)it.age + MainRegistry.smoothing) / 20.0F + it.hoverStart) * (180F / (float)Math.PI);*/

			GL11.glTranslatef(0, 0.2F, 0);
			GL11.glTranslatef(0, 0, 0.4F);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			break;
		case EQUIPPED:
			GL11.glTranslatef(0.5F, 0.2F, 0.45F);
			GL11.glRotatef(15, 0, 1, 0);
			GL11.glRotatef(15, 1, 0, 1);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glTranslatef(1, 0.6F, 0);
			GL11.glRotatef(25, 0, 0, 1);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			break;
		case INVENTORY:
			GL11.glScalef(3, 3, 3);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(0, -1.5F, -2);
			GL11.glRotatef(45, -1, 0, 0);
			GL11.glScalef(-1, 1, 1);
			break;
		default:
			break;
		
		}

		if(type == ItemRenderType.INVENTORY) GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flaregun_tex);
		ResourceManager.flaregun.renderPart("Body");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.flaregun_barrel_tex);
		
		if(open)
			GL11.glRotatef(45, 1, 0, 0);
		ResourceManager.flaregun.renderPart("Barrel");
		GL11.glShadeModel(GL11.GL_FLAT);
		
		if(type == ItemRenderType.INVENTORY) GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glPopMatrix();

	}

}
