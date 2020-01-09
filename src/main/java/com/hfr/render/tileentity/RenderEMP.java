package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.render.util.RenderSparks;
import com.hfr.tileentity.machine.TileEntityMachineEMP;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderEMP extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		
        bindTexture(ResourceManager.emp_tex);

        ResourceManager.emp.renderOnly("Body");
        
        TileEntityMachineEMP emp = (TileEntityMachineEMP)tile;
        boolean on = emp.isOn;

        GL11.glPushMatrix();
        	if(on)
        		GL11.glRotatef(System.currentTimeMillis() / 2 % 360, 0, 1, 0);
	    	
        	float rotors = 3;
	        for(int i = 0; i < rotors; i++) {
	        	ResourceManager.emp.renderOnly("Rotor");
	        	GL11.glRotatef(360 / rotors, 0, 1, 0);
	        }
        GL11.glPopMatrix();

    	if(on) {
			for(int i = 0; i < 5; i++) {
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, 0, 2, 0, 0.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, 0, 2, 0, 0.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
			}
    	}

        GL11.glPopMatrix();

	}
}
