package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.main.ResourceManager;
import com.hfr.render.util.RenderSparks;
import com.hfr.tileentity.weapon.TileEntityRailgun;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderRailgun extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.railgun_base_tex);
        ResourceManager.railgun_base.renderAll();
        
        TileEntityRailgun gun = (TileEntityRailgun)tile;
        float yaw = gun.yaw;
        float pitch = gun.pitch;
        
        if(System.currentTimeMillis() < gun.startTime + gun.cooldownDurationMillis) {
        	float interpolation = (float)(System.currentTimeMillis() - gun.startTime) / (float)gun.cooldownDurationMillis * 100F;
        	
        	float yi = (gun.yaw - gun.lastYaw) * interpolation / 100F;
        	yaw = gun.lastYaw + yi;
        	
        	float pi = (gun.pitch - gun.lastPitch) * interpolation / 100F;
        	pitch = gun.lastPitch + pi;
        }
        
        int max = 5;
        int count = max - (int)(((gun.fireTime + gun.cooldownDurationMillis) - System.currentTimeMillis()) * max / gun.cooldownDurationMillis);
		
        if(System.currentTimeMillis() < gun.fireTime + gun.cooldownDurationMillis) {
			Vec3 vec = Vec3.createVectorHelper(5.375, 0, 0);
			vec.rotateAroundZ((float) (pitch * Math.PI / 180D));
			vec.rotateAroundY((float) (yaw * Math.PI / 180D));
	
			double fX = vec.xCoord;
			double fY = 1 + vec.yCoord;
			double fZ = vec.zCoord;
	
			GL11.glRotatef(180, 0F, 1F, 0F);
			for(int i = 0; i < count; i++)
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, fX, fY, fZ, 0.75F, 5, 6, 0x0088FF, 0xDFDFFF);
			for(int i = 0; i < count; i++)
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, fX, fY, fZ, 0.75F, 5, 6, 0x0088FF, 0xDFDFFF);
			GL11.glRotatef(180, 0F, 1F, 0F);
        }
        
        GL11.glRotatef(yaw, 0, 1, 0);
        bindTexture(ResourceManager.railgun_rotor_tex);
        ResourceManager.railgun_rotor.renderAll();
        
        GL11.glTranslatef(0, 1F, 0);
        GL11.glRotatef(pitch, 0, 0, 1);
        GL11.glTranslatef(0, -1F, 0);
        bindTexture(ResourceManager.railgun_main_tex);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        ResourceManager.railgun_main.renderAll();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();

	}
}
