package com.hfr.render.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;

public class ParticleRenderUtil {

	//Basically just leftover code from yesterday's shit buffet.
	//Code lifted from MCR and *slightly* modified

	public static void doRender(Entity p_76986_1_, double x, double y, double z, Tessellator tessellator) {
	}

	private static void func_77026_a(Tessellator tess, double x, double y, double z) {

		//GL11.glRotatef(180.0F - Minecraft.getMinecraft().renderGlobal.playerViewY, 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(-this.renderManager.playerViewX + 180, 1.0F, 0.0F, 0.0F);

		//tess.startDrawingQuads();
		tess.addVertexWithUV(x - 1, y - 1, z, 1, 0);
		tess.addVertexWithUV(x - 1, y + 1, z, 0, 0);
		tess.addVertexWithUV(x + 1, y + 1, z, 0, 1);
		tess.addVertexWithUV(x + 1, y - 1, z, 1, 1);
		//tess.draw();
	}

}
