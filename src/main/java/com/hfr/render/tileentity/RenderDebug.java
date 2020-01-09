package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderDebug extends TileEntitySpecialRenderer {
	
	/*public static DynamicTexture text;
	
	static {
		text = new DynamicTexture(3, 3);
		int[] data = text.getTextureData();
		data[0] = 0xffff0080;
		data[1] = 0xfff00000;
		data[2] = 0xffd00000;
		data[3] = 0xffb00000;
		data[4] = 0xff900000;
		data[5] = 0xff700000;
		data[6] = 0xff500000;
		data[7] = 0xff300000;
		data[8] = 0xff100000;
		text.updateDynamicTexture();
	}*/

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.devon_tex);

        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, text.getGlTextureId());
        
        ResourceManager.devon.renderAll();

        GL11.glPopMatrix();

	}

}
