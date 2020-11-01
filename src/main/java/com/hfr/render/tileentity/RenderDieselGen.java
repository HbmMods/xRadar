package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderDieselGen extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - 10)
		{
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		bindTexture(ResourceManager.diesel_tex);
		ResourceManager.diesel.renderAll();
		
		GL11.glPopMatrix();
	}
}
