package com.hfr.render;

import org.lwjgl.opengl.GL11;

import com.hfr.clowder.ClowderFlag;
import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderFlag extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double x, double y, double z, float p_147500_8_) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		
        bindTexture(ResourceManager.flag_tex);
        ResourceManager.flag.renderOnly("Pole");

        GL11.glDisable(GL11.GL_CULL_FACE);
        
        ClowderFlag flag = ClowderFlag.TRICOLOR;
        int color = 0xFF00FF;

	    int r = ((color & 0xFF0000) >> 16) / 2;
	    int g = ((color & 0xFF00) >> 8) / 2;
	    int b = (color & 0xFF) / 2;

        bindTexture(flag.getFlag());
        GL11.glColor3b((byte)r, (byte)g, (byte)b);
        ResourceManager.flag.renderOnly("Flag");
        bindTexture(flag.getFlagOverlay());
        GL11.glColor3b((byte)127, (byte)127, (byte)127);
        ResourceManager.flag.renderOnly("Flag");

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();

	}
}
