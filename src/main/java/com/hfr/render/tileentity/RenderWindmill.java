package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.blocks.BlockDummyable;
import com.hfr.main.ResourceManager;
import com.hfr.tileentity.machine.TileEntityMachineWindmill;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderWindmill extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntityMachineWindmill mill = (TileEntityMachineWindmill)tile;
		
		if(mill.operational()) {
			mill.rotation += 3F * f;
			
			mill.rotation = mill.rotation % 360;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.windmill_tex);
        ResourceManager.windmill.renderPart("Base");
        GL11.glRotatef(15, 0F, 1F, 0F);
        ResourceManager.windmill.renderPart("Pivot");

        GL11.glTranslated(0, 31, 0);
        GL11.glRotatef(mill.rotation, 0F, 0F, -1F);
        GL11.glTranslated(0, -31, 0);
        ResourceManager.windmill.renderPart("Blades");
		GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();

	}
}
