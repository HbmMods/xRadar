package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.clowder.ClowderFlag;
import com.hfr.main.ResourceManager;
import com.hfr.tileentity.clowder.TileEntityConquerer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderConquerer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
        
        TileEntityConquerer flagpole = (TileEntityConquerer)te;
		
        bindTexture(ResourceManager.flag_conq_tex);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.flag.renderOnly("Pole");
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glDisable(GL11.GL_CULL_FACE);
        
        ClowderFlag flag = flagpole.flag;
        int color = flagpole.color;
        
        if(flag == ClowderFlag.NONE || flag == null) {
        	flag = ClowderFlag.TRICOLOR;
        }

	    int r = ((color & 0xFF0000) >> 16) / 2;
	    int g = ((color & 0xFF00) >> 8) / 2;
	    int b = (color & 0xFF) / 2;

	    GL11.glPushMatrix();
        GL11.glTranslatef(0, -4F, 0);
        GL11.glTranslatef(0, flagpole.height * 4, 0);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        bindTexture(flag.getFlag());
        GL11.glColor3b((byte)r, (byte)g, (byte)b);
        ResourceManager.flag.renderOnly("Flag");

	    bindTexture(flag.getFlagOverlay());
	    GL11.glColor3b((byte)127, (byte)127, (byte)127);
	    ResourceManager.flag.renderOnly("Flag");
	    GL11.glPopMatrix();
	    
	    if(flagpole.height < 1) {
	    	
	        GL11.glPushMatrix();
		    GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        GL11.glDepthMask(false);
			RenderHelper.disableStandardItemLighting();
		    Tessellator tess = Tessellator.instance;
		    
	        GL11.glTranslatef(0, 5F, 0);
	        GL11.glRotated(System.currentTimeMillis() / 10 % 360, 0, 1, 0);
		    
	        double length = 2.5;
	        double height = 0.75;
	        
		    for(int i = 0; i < 2; i++) {
			    tess.startDrawing(GL11.GL_TRIANGLES);
			    tess.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
			    tess.addVertex(0, 0, 0);
			    tess.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.0F);
			    tess.addVertex(length, height, 0);
			    tess.addVertex(length, -height, 0);
			    tess.draw();

			    tess.startDrawing(GL11.GL_TRIANGLES);
			    tess.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
			    tess.addVertex(0, 0, 0);
			    tess.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.0F);
			    tess.addVertex(-length, height, 0);
			    tess.addVertex(-length, -height, 0);
			    tess.draw();
		    }
		    GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glShadeModel(GL11.GL_FLAT);
	        GL11.glDepthMask(true);
		    GL11.glPopMatrix();
	    }
	    
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);

		RenderHelper.enableStandardItemLighting();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();

	}
}
