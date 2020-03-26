package com.hfr.render.living;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends net.minecraft.client.renderer.entity.RenderSheep {
	
	private static final ResourceLocation furTextures = new ResourceLocation("hfr:textures/entity/fleece.png");
	private static final ResourceLocation sheepTextures = new ResourceLocation("hfr:textures/entity/sheep.png");

	public RenderSheep(ModelBase p_i1253_1_, ModelBase p_i1253_2_, float p_i1253_3_) {
		super(p_i1253_1_, p_i1253_2_, p_i1253_3_);
	}
    
	protected int shouldRenderPass(EntitySheep p_77032_1_, int p_77032_2_, float p_77032_3_) {
		
		if ((p_77032_2_ == 0) && (!p_77032_1_.getSheared())) {
			
            int j = p_77032_1_.getFleeceColor();
            GL11.glColor3f(EntitySheep.fleeceColorTable[j][0], EntitySheep.fleeceColorTable[j][1], EntitySheep.fleeceColorTable[j][2]);
			bindTexture(this.furTextures);
			
			return 1;
		}
		bindTexture(this.sheepTextures);
		return -1;
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return getEntityTexture((EntitySheep) p_110775_1_);
	}
	
    protected ResourceLocation getEntityTexture(EntitySheep p_110775_1_)
    {
        return sheepTextures;
    }

}
