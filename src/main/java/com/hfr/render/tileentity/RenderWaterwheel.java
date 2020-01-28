package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.machine.TileEntityWaterWheel;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderWaterwheel extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata())
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntityWaterWheel mill = (TileEntityWaterWheel)tile;
		
		if(mill.operational()) {
			mill.rotation += 3F * f;
			
			mill.rotation = mill.rotation % 360;
		}
		
		GL11.glRotatef(mill.rotation, 1F, 0F, 0F);
        bindTexture(ResourceManager.waterwheel_tex);
        ResourceManager.waterwheel.renderAll();
		
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();

	}
}
