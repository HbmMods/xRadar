package com.hfr.render.tileentity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import com.hfr.lib.RefStrings;
import com.hfr.tileentity.machine.TileEntityDisplay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderDisplay extends TileEntitySpecialRenderer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/models/display.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        float pixel = 0.0625F;

		GL11.glRotatef(90, 0F, 1F, 0F);
		
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
        GL11.glTranslated(0, 0, 0.5 - pixel);

	    GL11.glDisable(GL11.GL_LIGHTING);
        TileEntityDisplay display = (TileEntityDisplay)te;

        GL11.glTranslated(pixel * -81, 0, 0);
        Tessellator tessellator = Tessellator.instance;
        bindTexture(texture);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, pixel * 54, 0, 1, 0);
        tessellator.addVertexWithUV(pixel * 162, pixel * 54, 0, 0, 0);
        tessellator.addVertexWithUV(pixel * 162, 0, 0, 0, 1);
        tessellator.addVertexWithUV(0, 0, 0, 1, 1);
        tessellator.draw();
        
        if(display.stock == null) {
            GL11.glPopMatrix();
            return;
        }
        
		Minecraft minecraft = Minecraft.getMinecraft();
        
        float vals[] = display.stock.value;
        float render[] = new float[15];

        List<Float> floats = Arrays.asList(ArrayUtils.toObject(vals));
        
        float min = Collections.min(floats);
        float max = Collections.max(floats);
        
        int padding = 5;
        int range = 52 - padding * 2;
        float d = max - min;
        
        if(d == 0)
        	d = 1F;
        
        for(int i = 0; i < render.length; i++) {
        	render[i] = (vals[i] - min) * range / d;
        }

        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.5, 0);
        GL11.glScalef(-pixel, -pixel, -pixel);
        GL11.glTranslated(-168.5, -81, 1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(3F);

        float last = render[0];
        
        for(int i = 1; i < render.length; i++) {
        	
            tessellator.startDrawing(3);
            tessellator.setColorOpaque_I(0xFFFFFF);
            
            tessellator.addVertex(8 + 10 * (i - 1), 88 - last - padding, 0);
            tessellator.addVertex(8 + 10 * (i - 1) + 10, 88 - render[i] - padding, 0);
            tessellator.draw();
            
            last = render[i];
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        float fontScale = 2F;
        
		GL11.glScalef(1 / fontScale, 1 / fontScale, 1 / fontScale);

		String name = display.stock.name + " (" + display.stock.shortname + ")";
		
	    minecraft.fontRenderer.drawString("" + round(max), (int) ((9) * fontScale), (int) ((37) * fontScale), 0xFFFFFF);
	    minecraft.fontRenderer.drawString("" + name, (int) ((120 - minecraft.fontRenderer.getStringWidth(name) / 2) * fontScale), (int) ((36) * fontScale), 0xFFFFFF);
	    minecraft.fontRenderer.drawString("" + round(min), (int) ((9) * fontScale), (int) ((83.5) * fontScale), 0xFFFFFF);

	    float diff = (vals[14] - vals[13]) / vals[13] * 100;
	    
	    int color = 0xFF0000;
	    
	    if(diff > 0)
	    	color = 0x00FF00;
	    if(diff == 0)
	    	color = 0xFFFF00;
	    
	    minecraft.fontRenderer.drawString((diff >= 0 ? "+" : "") + round(diff) + "%", (int) ((149.5) * fontScale), (int) ((38.5) * fontScale), color);
	    minecraft.fontRenderer.drawString("=" + round(vals[14]), (int) ((149.5) * fontScale), (int) ((48.5) * fontScale), 0xFFFFFF);

	    
		GL11.glScalef(fontScale, fontScale, fontScale);
		
	    GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
        GL11.glPopMatrix();
	}
	
	public float round(float f) {
		
		f *= 100F;
		f = (int)f;
		f /= 100F;
		
		return f;
	}

}
