package com.hfr.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MultiCloudRenderer extends Render {
	private static final String __OBFID = "CL_00001008";

	public MultiCloudRenderer() {
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void func_76986_a(T entity, double d, double d1, double d2, float f,
	 * float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		/*if (p_76986_1_ instanceof EntityModFX) {
			EntityModFX fx = (EntityModFX) p_76986_1_;

			ResourceLocation tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke1.png");
			
			if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke8.png");
			}

			if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke7.png");
			}

			if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke6.png");
			}

			if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke5.png");
			}

			if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke4.png");
			}

			if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke3.png");
			}

			if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke2.png");
			}

			if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
				tex = new ResourceLocation(RefStrings.MODID + ":" + "textures/fx/smoke1.png");
			}

			if (tex != null) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				
				////
				Random randy = new Random(p_76986_1_.hashCode());
				double d = randy.nextInt(10) * 0.05;
				GL11.glColor3d(1 - d, 1 - d, 1 - d);
				////
				
				Random rand = new Random(100);
				
				for(int i = 0; i < 5; i++) {

					double dX = (rand.nextGaussian() - 1D) * 0.15D;
					double dY = (rand.nextGaussian() - 1D) * 0.15D;
					double dZ = (rand.nextGaussian() - 1D) * 0.15D;
					double size = rand.nextDouble() * 0.5D + 0.25D;
					
					GL11.glTranslatef((float) dX, (float) dY, (float) dZ);
					GL11.glScaled(size, size, size);

					GL11.glPushMatrix();
					this.bindTexture(tex);
					Tessellator tessellator = Tessellator.instance;
					this.func_77026_a(tessellator);
					GL11.glPopMatrix();

					GL11.glScaled(1/size, 1/size, 1/size);
					GL11.glTranslatef((float) -dX, (float) -dY, (float) -dZ);
				}
				
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopMatrix();
			}
		}*/
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TextureMap.locationItemsTexture;
	}

	private void func_77026_a(Tessellator tess) {
		
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX + 180, 1.0F, 0.0F, 0.0F);
		
		tess.startDrawingQuads();
		tess.addVertexWithUV(-1, -1, 0, 1, 0);
		tess.addVertexWithUV(-1, 1, 0, 0, 0);
		tess.addVertexWithUV(1, 1, 0, 0, 1);
		tess.addVertexWithUV(1, -1, 0, 1, 1);
		tess.draw();
	}
}
