package com.hfr.render.hud;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hfr.rvi.RVICommon;
import com.hfr.rvi.RVICommon.Indicator;
import com.hfr.rvi.RVICommon.RVIType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

public class RenderRVIOverlay {
	
	public static List<Indicator> indicators = new ArrayList();
	
	public static void renderIndicators(float interpolation) {
		
		GL11.glPushMatrix();
		Minecraft minecraft = Minecraft.getMinecraft();

		
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), minecraft.displayWidth, minecraft.displayHeight);
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

		double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * interpolation;
		double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * interpolation;
		double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * interpolation;
		
		for(Indicator ind : indicators) {
			
			GL11.glPushMatrix();
			
			if(ind.type == RVIType.FRIEND || ind.type == RVIType.ENEMY)
			{ //see sprite through walls if digital radar
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDisable(2929);
				GL11.glDepthMask(false);
			}
			
			minecraft.getTextureManager().bindTexture(ind.type.texture);
			Vec3 vec = Vec3.createVectorHelper(x - ind.x, y - ind.y, z - ind.z);
			vec.rotateAroundY((float) Math.toRadians(1));
			
			double dist = vec.lengthVector();

			double yaw = Math.toDegrees(Math.atan2(vec.xCoord, vec.zCoord));
			
			if(yaw < 0.0D) {
				yaw += 360.0D;
			}
			
			double pitch = Math.toDegrees(Math.tan(vec.yCoord / dist));
			
			double pYaw = Math.abs(player.rotationYaw);
			
			if(pYaw < 0.0D) {
				pYaw += 360.0D;
			}
			
			double diff = 0;//(yaw - pYaw) * 0.01;
			
			if(pYaw > yaw)
				yaw += diff;
			else
				yaw -= diff;

			int max = 250;
			
			if(dist > max)
				GL11.glTranslated(-vec.xCoord / dist * max, -vec.yCoord / dist * max, -vec.zCoord / dist * max);
			else
				GL11.glTranslated(-vec.xCoord, -vec.yCoord, -vec.zCoord);
			
			GL11.glRotated(yaw + 180, 0, 1, 0);
			GL11.glRotated(pitch, 1, 0, 0);
			
			renderScaled(0, 0, 0, -5 * (1 - (1 / dist * 0.5)));
			
			//bigger sprite if its a ship
			if(ind.type == RVIType.SHIP)
			renderScaled(0, 0, 0, -9 * (1 - (1 / dist * 0.5)));

			GL11.glPopMatrix();
			
			
			if(ind.type == RVIType.FRIEND || ind.type == RVIType.ENEMY)
			{
				//allow digital radar sprites to be seen through walls
		        GL11.glDepthMask(true);
		        GL11.glEnable(2929);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}

		//GL11.glEnable(GL11.GL_FOG);
		
		

		GL11.glPopMatrix();
	}
	
	public static void renderScaled(double x, double y, double z, double scale) {

		Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x - scale, y + scale, z, 0, 1);
        tessellator.addVertexWithUV(x + scale, y + scale, z, 1, 1);
        tessellator.addVertexWithUV(x + scale, y - scale, z, 1, 0);
        tessellator.addVertexWithUV(x - scale, y - scale, z, 0, 0);
        tessellator.draw();
	}
}
