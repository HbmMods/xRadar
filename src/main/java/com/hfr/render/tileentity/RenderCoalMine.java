package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.blocks.BlockDummyable;
import com.hfr.main.ResourceManager;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCoalMine extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
        
        GL11.glTranslated(1, 0, 0);

		//piece the entire thing together
		GL11.glShadeModel(GL11.GL_SMOOTH);

        bindTexture(ResourceManager.mine_foundation);
        ResourceManager.coal_mine.renderPart("Foundation");
        bindTexture(ResourceManager.mine_hut);
        ResourceManager.coal_mine.renderPart("Hut");
        bindTexture(ResourceManager.mine_roof);
        ResourceManager.coal_mine.renderPart("Roof");
        bindTexture(ResourceManager.mine_steel);
        ResourceManager.coal_mine.renderPart("Frame");
        bindTexture(ResourceManager.mine_steel_dark);
        ResourceManager.coal_mine.renderPart("Wheels");
        bindTexture(ResourceManager.mine_hole);
        ResourceManager.coal_mine.renderPart("Hole");
        ResourceManager.coal_mine.renderPart("Rope");
        
		GL11.glShadeModel(GL11.GL_FLAT);
        
        GL11.glPopMatrix();

	}
}
