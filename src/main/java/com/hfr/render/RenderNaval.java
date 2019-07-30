package com.hfr.render;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.tileentity.TileEntityNaval;
import com.hfr.tileentity.TileEntityRailgun;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderNaval extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);

        bindTexture(ResourceManager.universal);
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
        ResourceManager.naval_main.renderAll();
        
        GL11.glTranslatef(2.5F, 1.75F, 0);
        GL11.glRotatef(pitch, 0, 0, 1);
        GL11.glTranslatef(-2.5F, -1.75F, 0);
        ResourceManager.naval_cannons.renderAll();

        GL11.glPopMatrix();

	}
}
