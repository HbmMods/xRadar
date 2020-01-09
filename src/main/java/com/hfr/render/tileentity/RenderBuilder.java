package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.schematic.SchematicRenderer;
import com.hfr.tileentity.machine.TileEntityMachineBuilder;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderBuilder extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f0) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.0625D, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);

        TileEntityMachineBuilder builder = (TileEntityMachineBuilder)te;

        GL11.glTranslated(builder.ox, 0, builder.oz);
        
        bindTexture(TextureMap.locationBlocksTexture);
        if(builder.preview != null)
        	SchematicRenderer.render(builder.preview, 1F, x + builder.ox, y, z + builder.oz, 25);
        
        GL11.glPopMatrix();
	}

}
