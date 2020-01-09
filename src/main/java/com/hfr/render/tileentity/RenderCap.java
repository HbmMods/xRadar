package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.clowder.TileEntityCap;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCap extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        TileEntityCap point = (TileEntityCap)te;
        
        int color = point.color;

	    int r = ((color & 0xFF0000) >> 16) / 2;
	    int g = ((color & 0xFF00) >> 8) / 2;
	    int b = (color & 0xFF) / 2;
        
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.cap_tex);
        ResourceManager.cap.renderOnly("Base");
        
        GL11.glColor3b((byte)r, (byte)g, (byte)b);
        bindTexture(ResourceManager.cap_point_tex);
        ResourceManager.cap.renderOnly("Point");
	    GL11.glColor3b((byte)127, (byte)127, (byte)127);
        
        GL11.glShadeModel(GL11.GL_FLAT);
        
        GL11.glPopMatrix();
	}

}
