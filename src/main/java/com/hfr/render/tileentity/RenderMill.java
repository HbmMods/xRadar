package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.blocks.BlockDummyable;
import com.hfr.main.ResourceManager;
import com.hfr.render.tmt.ModelGrainmill;
import com.hfr.tileentity.machine.TileEntityMachineGrainmill;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMill extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        GL11.glRotatef(-90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntityMachineGrainmill mill = (TileEntityMachineGrainmill)tile;
		
		if(mill.operational()) {
			mill.rotation += 0.03F * f;
			
			mill.rotation = mill.rotation % 360;
		}
		
        bindTexture(ResourceManager.tmt_mill);
        ModelGrainmill.instance.render(mill.rotation);

        GL11.glPopMatrix();

	}

}
