package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.machine.TileEntityFoundry;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderFoundry extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		
		TileEntityFoundry foundry = (TileEntityFoundry)tile;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - 10)
		{
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-0.5, 0, -0.25);
		
		bindTexture(ResourceManager.foundry_tex);
		ResourceManager.foundry.renderPart("Scaffold");
		
		float tilt = foundry.lastTilt + (foundry.tilt - foundry.lastTilt) * interp;
		float pixel = 0.0625F;

		double dheight = 3.75D;
		double doffset = 0.425D;
		GL11.glPushMatrix();
		GL11.glTranslated(doffset, dheight, 0);
		GL11.glRotatef(-tilt, 0, 0, 1);
		GL11.glTranslated(-doffset, -dheight, 0);
		ResourceManager.foundry.renderPart("Crucible");
		GL11.glPopMatrix();
		
		if(tilt >= 30F) {
			
			Tessellator tess = Tessellator.instance;
			bindTexture(ResourceManager.furn_lava);

			GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	        
	        float offset = 1 - pixel * 1.5F;
	        float height = 2.5F;
	        float min = 5 * pixel;
	        
	        float slide = (System.currentTimeMillis() % 1000) * -0.001F;
	        
	        tess.startDrawingQuads();
	        tess.addVertexWithUV(offset + pixel, height, -pixel, slide, 0);
	        tess.addVertexWithUV(offset + pixel, height, pixel, slide, 0.125);
	        tess.addVertexWithUV(offset + pixel, min, pixel, slide + 1, 0.125);
	        tess.addVertexWithUV(offset + pixel, min, -pixel, slide + 1, 0);

	        tess.addVertexWithUV(offset, height, pixel, slide, 0.125);
	        tess.addVertexWithUV(offset, height, -pixel, slide, 0);
	        tess.addVertexWithUV(offset, min, -pixel, slide + 1, 0);
	        tess.addVertexWithUV(offset, min, pixel, slide + 1, 0.125);

	        tess.addVertexWithUV(offset + pixel, height, pixel, slide, 0.1875);
	        tess.addVertexWithUV(offset, height, pixel, slide, 0.125);
	        tess.addVertexWithUV(offset, min, pixel, slide + 1, 0.125);
	        tess.addVertexWithUV(offset + pixel, min, pixel, slide + 1, 0.1875);

	        tess.addVertexWithUV(offset, height, -pixel, slide, 0.125);
	        tess.addVertexWithUV(offset + pixel, height, -pixel, slide, 0.1875);
	        tess.addVertexWithUV(offset + pixel, min, -pixel, slide + 1, 0.1875);
	        tess.addVertexWithUV(offset, min, -pixel, slide + 1, 0.125);
	        
	        float size = 8;
	        tess.addVertexWithUV(offset - pixel * size, min, -pixel * size, 0, 0);
	        tess.addVertexWithUV(offset - pixel * size, min, pixel * size, 0, 1);
	        tess.addVertexWithUV(offset + pixel  * size, min, pixel * size, 1, 1);
	        tess.addVertexWithUV(offset + pixel  * size, min, -pixel * size, 1, 0);
	        tess.draw();
	        
			GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glPopAttrib();
			GL11.glPopMatrix();
	        
			bindTexture(ResourceManager.foundry_tex);
		}
		
		GL11.glPopMatrix();
	}

}
