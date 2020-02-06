package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.lib.RefStrings;
import com.hfr.main.ResourceManager;
import com.hfr.tileentity.weapon.TileEntityLaunchPad;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderLaunch extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/missilePad.obj");
	private IModelCustom padModel;
    private ResourceLocation padTexture;
	
	public RenderLaunch()
    {
		padModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		padTexture = new ResourceLocation(RefStrings.MODID, "textures/models/missilePad.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		/*switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}*/

		//GL11.glScalef(1.5F, 1.0F, 1.5F);
        bindTexture(padTexture);
        padModel.renderAll();
		//GL11.glScalef(2/3F, 1.0F, 2/3F);

        GL11.glDisable(GL11.GL_CULL_FACE);
        int state = 0;
        
        if(tileEntity instanceof TileEntityLaunchPad)
        	state = ((TileEntityLaunchPad)tileEntity).state;
        
	        GL11.glTranslated(0, 1, 0);
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        
			if(state == 1)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(ResourceManager.missileV2_HE_tex);
				ResourceManager.missileV2.renderAll();
			}
			if(state == 2)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileStrong_HE_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if(state == 3)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(ResourceManager.missileHuge_HE_tex);
				ResourceManager.missileNeon.renderAll();
			}
			if(state == 4)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(ResourceManager.missileV2_IN_tex);
				ResourceManager.missileV2.renderAll();
			}
			if(state == 5)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileStrong_IN_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if(state == 6)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(ResourceManager.missileHuge_IN_tex);
				ResourceManager.missileNeon.renderAll();
			}
			if(state == 7)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileStrong_EMP_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if(state == 8)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(ResourceManager.missileAA_tex);
				ResourceManager.missileV2.renderAll();
			}
			if(state == 9)
			{
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(ResourceManager.missile_decoy_tex);
				ResourceManager.missileV2.renderAll();
			}
			if(state == 10)
			{
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				bindTexture(ResourceManager.missileNuclear_tex);
				ResourceManager.missileNeon.renderAll();
			}
			if(state == 11)
			{
				bindTexture(ResourceManager.missile_devon1_tex);
				ResourceManager.slbm_spear.renderAll();
			}
			if(state == 12)
			{
				bindTexture(ResourceManager.missile_devon2_tex);
				ResourceManager.slbm_spear.renderAll();
			}
			if(state == 13)
			{
				bindTexture(ResourceManager.missile_devon3_tex);
				ResourceManager.slbm_spear.renderAll();
			}
			if(state == 14)
			{
				bindTexture(ResourceManager.missileV2_AT_tex);
				ResourceManager.missileV2.renderAll();
			}
			if(state == 15)
			{
				bindTexture(ResourceManager.missile_cruise_at1_tex);
				ResourceManager.slbm_spear.renderAll();
			}
			
	        GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }

}
