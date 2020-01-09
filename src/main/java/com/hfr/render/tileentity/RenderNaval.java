package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.weapon.TileEntityNaval;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderNaval extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glRotatef(90, 0, 1, 0);

        bindTexture(ResourceManager.naval_base_tex);
        ResourceManager.naval_base.renderAll();
        
        TileEntityNaval gun = (TileEntityNaval)tile;
        float yaw = gun.yaw;
        float pitch = gun.pitch;
        
        if(System.currentTimeMillis() < gun.startTime + gun.cooldownDurationMillis) {
        	float interpolation = (float)(System.currentTimeMillis() - gun.startTime) / (float)gun.cooldownDurationMillis * 100F;
        	
        	float yi = (gun.yaw - gun.lastYaw) * interpolation / 100F;
        	yaw = gun.lastYaw + yi;
        	
        	float pi = (gun.pitch - gun.lastPitch) * interpolation / 100F;
        	pitch = gun.lastPitch + pi;
        }
        
        GL11.glRotatef(yaw, 0, 1, 0);
        bindTexture(ResourceManager.naval_main_tex);
        ResourceManager.naval_main.renderAll();
        
        GL11.glTranslatef(0, 1F, 0);
        GL11.glRotatef(pitch, 1F, 0, 0);
        GL11.glTranslatef(0, -1F, 0);
        bindTexture(ResourceManager.naval_cannons_tex);
        ResourceManager.naval_cannons.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();

	}
}
