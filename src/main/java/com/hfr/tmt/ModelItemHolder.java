package com.hfr.tmt;

import net.minecraft.client.model.ModelBase;

public class ModelItemHolder extends ModelBase 
{
	public ModelRendererTurbo[] baseModel;
	
	public void render()
	{
		float f5 = 1F / 16F;
		
		for(ModelRendererTurbo model : baseModel)
			model.render(f5);
	}
	
	/** Flips the model. Generally only for backwards compatibility */
	public void flipAll()
	{
		flip(baseModel);
	}	
	
	protected void flip(ModelRendererTurbo[] model)
	{
		for(ModelRendererTurbo part : model)
		{
			part.doMirror(false, true, true);
			part.setRotationPoint(part.rotationPointX, - part.rotationPointY, - part.rotationPointZ);
		}
	}
	
	/** Translates the model */
	public void translateAll(float x, float y, float z)
	{
		translate(baseModel, x, y, z);
	}
	
	protected void translate(ModelRendererTurbo[] model, float x, float y, float z)
	{
		for(ModelRendererTurbo mod : model)
		{
			mod.rotationPointX += x;
			mod.rotationPointY += y;
			mod.rotationPointZ += z;
		}
	}
}
