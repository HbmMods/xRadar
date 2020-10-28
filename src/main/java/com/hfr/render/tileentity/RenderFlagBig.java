package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.clowder.ClowderFlag;
import com.hfr.main.ResourceManager;
import com.hfr.tileentity.clowder.TileEntityFlagBig;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFlagBig extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
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
        
        TileEntityFlagBig flagpole = (TileEntityFlagBig)te;
		
        bindTexture(ResourceManager.flag_tex);
        
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.flag_big.renderOnly("Pole");
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

	    if(!flagpole.isClaimed)
	    	GL11.glTranslatef(0, -8F, 0);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        bindTexture(flag.getFlag());
        GL11.glColor3b((byte)r, (byte)g, (byte)b);
        ResourceManager.flag_big.renderOnly("Flag");

	    bindTexture(flag.getFlagOverlay());
	    GL11.glColor3b((byte)127, (byte)127, (byte)127);
	    ResourceManager.flag_big.renderOnly("Flag");
	    
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        
        GL11.glDisable(GL11.GL_FOG);
        renderFlare(te.getWorldObj(), x, y + 10.25, z, interp);
        GL11.glEnable(GL11.GL_FOG);
	}
	
    private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
	
	private void renderFlare(World world, double x, double y, double z, float interp) {
		
        float f1 = 1F;
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        if (f1 > 0.0F)
        {
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(field_147523_b);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            
            float f2 = (float)world.getTotalWorldTime() + interp;
            float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(127, 255, 255, 32);
            
            double d5 = (double)b0 * 0.1D;
            double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
            double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
            double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
            double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
            double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
            double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
            double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
            double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
            double d23 = (double)(256.0F * f1);
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = (double)(-1.0F + f3);
            double d29 = (double)(256.0F * f1) * (0.5D / d5) + d28;
            
            tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d27, d29);
            tessellator.addVertexWithUV(x + d7, y, z + d9, d27, d28);
            tessellator.addVertexWithUV(x + d11, y, z + d13, d25, d28);
            tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d25, d29);
            tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d27, d29);
            tessellator.addVertexWithUV(x + d19, y, z + d21, d27, d28);
            tessellator.addVertexWithUV(x + d15, y, z + d17, d25, d28);
            tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d25, d29);
            tessellator.addVertexWithUV(x + d11, y + d23, z + d13, d27, d29);
            tessellator.addVertexWithUV(x + d11, y, z + d13, d27, d28);
            tessellator.addVertexWithUV(x + d19, y, z + d21, d25, d28);
            tessellator.addVertexWithUV(x + d19, y + d23, z + d21, d25, d29);
            tessellator.addVertexWithUV(x + d15, y + d23, z + d17, d27, d29);
            tessellator.addVertexWithUV(x + d15, y, z + d17, d27, d28);
            tessellator.addVertexWithUV(x + d7, y, z + d9, d25, d28);
            tessellator.addVertexWithUV(x + d7, y + d23, z + d9, d25, d29);
            
            tessellator.draw();
            
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(false);
            
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(127, 255, 255, 64);
            
            double pixel = 0.0625D;
            
            int inner = 6;
            int outer = 16 - inner;
            
            double d30 = pixel * inner;
            double d4 = pixel * inner;
            double d6 = pixel * outer;
            double d8 = pixel * inner;
            double d10 = pixel * inner;
            double d12 = pixel * outer;
            double d14 = pixel * outer;
            double d16 = pixel * outer;
            double d18 = (double)(256.0F * f1);
            double d20 = 0.0D;
            double d22 = 1.0D;
            double d24 = (double)(-1.0F + f3);
            double d26 = (double)(256.0F * f1) + d24;
            
            tessellator.addVertexWithUV(x + d30, y + d18, z + d4, d22, d26);
            tessellator.addVertexWithUV(x + d30, y, z + d4, d22, d24);
            tessellator.addVertexWithUV(x + d6, y, z + d8, d20, d24);
            tessellator.addVertexWithUV(x + d6, y + d18, z + d8, d20, d26);
            tessellator.addVertexWithUV(x + d14, y + d18, z + d16, d22, d26);
            tessellator.addVertexWithUV(x + d14, y, z + d16, d22, d24);
            tessellator.addVertexWithUV(x + d10, y, z + d12, d20, d24);
            tessellator.addVertexWithUV(x + d10, y + d18, z + d12, d20, d26);
            tessellator.addVertexWithUV(x + d6, y + d18, z + d8, d22, d26);
            tessellator.addVertexWithUV(x + d6, y, z + d8, d22, d24);
            tessellator.addVertexWithUV(x + d14, y, z + d16, d20, d24);
            tessellator.addVertexWithUV(x + d14, y + d18, z + d16, d20, d26);
            tessellator.addVertexWithUV(x + d10, y + d18, z + d12, d22, d26);
            tessellator.addVertexWithUV(x + d10, y, z + d12, d22, d24);
            tessellator.addVertexWithUV(x + d30, y, z + d4, d20, d24);
            tessellator.addVertexWithUV(x + d30, y + d18, z + d4, d20, d26);
            
            tessellator.draw();
            
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }
	}
}
