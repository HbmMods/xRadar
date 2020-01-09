package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.machine.TileEntityTank;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTank extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		//GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(-90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata())
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.tank_main_tex);
        ResourceManager.tank_main.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);
        
        TileEntityTank tank = (TileEntityTank)tile;
        switch(tank.type) {
        case 0: bindTexture(ResourceManager.tank_0_tex); break;
        case 1: bindTexture(ResourceManager.tank_1_tex); break;
        case 2: bindTexture(ResourceManager.tank_2_tex); break;
        case 3: bindTexture(ResourceManager.tank_3_tex); break;
        case 4: bindTexture(ResourceManager.tank_4_tex); break;
        case 5: bindTexture(ResourceManager.tank_5_tex); break;
        }
        
        if(tank.fill == 0)
        	bindTexture(ResourceManager.tank_empty_tex);
        
        ResourceManager.tank_label.renderAll();

        GL11.glPopMatrix();

	}
}
