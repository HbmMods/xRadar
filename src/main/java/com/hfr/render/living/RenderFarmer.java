package com.hfr.render.living;

import com.hfr.lib.RefStrings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderFarmer extends RenderBiped {

	private static final ResourceLocation loc0 = new ResourceLocation(RefStrings.MODID, "textures/entity/european.png");
	private static final ResourceLocation loc1 = new ResourceLocation(RefStrings.MODID, "textures/entity/vietnamese.png");
	private static final ResourceLocation loc2 = new ResourceLocation(RefStrings.MODID, "textures/entity/african.png");

	public RenderFarmer() {
		super(new ModelBiped(0), 0);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		int i = entity.getDataWatcher().getWatchableObjectInt(16);
		
		if(i == 0)
			return loc0;
		else if(i == 1)
			return loc1;
		else
			return loc2;
	}

    protected void renderEquippedItems(EntityZombie p_77029_1_, float p_77029_2_)
    {
        super.renderEquippedItems((EntityLiving)p_77029_1_, p_77029_2_);
    }

}
