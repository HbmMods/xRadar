package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.machine.TileEntityMachineNet;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderNet extends TileEntitySpecialRenderer {


    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 0.0625D, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);

        TileEntityMachineNet net = (TileEntityMachineNet)tileEntity;
        
        if(net.isJammed)
        	bindTexture(ResourceManager.net_jammed_tex);
        else
        	bindTexture(ResourceManager.net_tex);
        
        ResourceManager.net.renderAll();
        
        GL11.glPopMatrix();
    }

}
