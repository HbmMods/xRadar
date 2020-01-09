package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.blocks.BlockDummyable;
import com.hfr.main.ResourceManager;
import com.hfr.tileentity.machine.TileEntityMachineBlastFurnace;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBlastFurnace extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glRotatef(-90, 0F, 1F, 0F);
        
        TileEntityMachineBlastFurnace furnace = (TileEntityMachineBlastFurnace) tile;
        float gauge = 4F * furnace.meter;

        GL11.glPushMatrix();
		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		//piece the entire thing together
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
        bindTexture(ResourceManager.furn_wall);
        ResourceManager.blast_furnace.renderPart("Wall");
        bindTexture(ResourceManager.furn_wall_inner);
        ResourceManager.blast_furnace.renderPart("WallInner");
        bindTexture(ResourceManager.furn_furnace);
        ResourceManager.blast_furnace.renderPart("Crubicle");
        bindTexture(ResourceManager.furn_roof);
        ResourceManager.blast_furnace.renderPart("Roof");
        bindTexture(ResourceManager.furn_tower);
        ResourceManager.blast_furnace.renderPart("Tower");
        bindTexture(ResourceManager.furn_tower_inner);
        ResourceManager.blast_furnace.renderPart("TowerInner");
        
        if(gauge > 0)
        	bindTexture(ResourceManager.furn_furnace_on);
        else
        	bindTexture(ResourceManager.furn_furnace_off);
        ResourceManager.blast_furnace.renderPart("Furnace");
        
		GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();

        //draw lava
        if(gauge > 0) {
            GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
    		
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
            
            GL11.glRotatef(-90, 0F, 1F, 0F);
    		Tessellator t = Tessellator.instance;
    		float size = 0.75F;
            
	        GL11.glTranslatef(0.0F, gauge + 0.5F, 0.0F);
	        bindTexture(ResourceManager.furn_lava);
			t.startDrawingQuads();
			t.addVertexWithUV(-size, 0, -size, 0, 0);
			t.addVertexWithUV(-size, 0, size, 0, 1);
			t.addVertexWithUV(size, 0, size, 1, 1);
			t.addVertexWithUV(size, 0, -size, 1, 0);
			t.draw();
			
	        GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        
	        GL11.glPopAttrib();
	        GL11.glPopMatrix();
        }
		
		//done
        GL11.glPopMatrix();

	}
}
