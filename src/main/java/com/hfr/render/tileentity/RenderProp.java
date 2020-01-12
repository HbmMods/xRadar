package com.hfr.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.main.ResourceManager;
import com.hfr.render.tmt.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderProp extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float p_147500_8_) {
		
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        Block b = tile.getBlockType();

        if(b == ModBlocks.hesco_block) GL11.glRotatef(180, 0F, 1F, 0F);
        if(b == ModBlocks.med_tent) GL11.glRotatef(90, 0F, 1F, 0F);
        if(b == ModBlocks.tp_tent) GL11.glRotatef(90, 0F, 1F, 0F);
        if(b == ModBlocks.statue) GL11.glRotatef(90, 0F, 1F, 0F);

		switch(tile.getBlockMetadata() - BlockDummyable.offset)
		{
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		float p = 0.0625F;

        if(b == ModBlocks.palisade) GL11.glTranslated(-p * 0.5, 0, -0.5 - p * 0.5);
        if(b == ModBlocks.stone_wall) GL11.glTranslated(0, 0, 0.5);
        if(b == ModBlocks.brick_wall) GL11.glTranslated(0, 0, 0.5);
        if(b == ModBlocks.great_wall) GL11.glTranslated(0, 0, 0.5);
        if(b == ModBlocks.berlin_wall) GL11.glTranslated(0, 0, 0.5);
        if(b == ModBlocks.med_tent) GL11.glTranslated(1.5 + p * 2, 0, 2.25 + p * 1.5);
        if(b == ModBlocks.tp_tent) GL11.glTranslated(1.5 + p * 2, 0, 2.25 + p * 1.5);
        if(b == ModBlocks.statue) GL11.glTranslated(0, 0, p * 3);
		
        if(b == ModBlocks.hesco_block) {
            bindTexture(ResourceManager.tmt_hescoblock);
            ModelHescoBlock.instance.render();
        }
		
        /*if(b == ModBlocks.palisade) {
            bindTexture(ResourceManager.tmt_palisade);
            ModelPalisade.instance.render();
            ResourceManager.palisade.renderAll();
        }
		
        if(b == ModBlocks.stone_wall) {
            bindTexture(ResourceManager.tmt_stonewall);
            ModelStoneWall.instance.render();
            ResourceManager.stonewall.renderAll();
        }
		
        if(b == ModBlocks.brick_wall) {
            bindTexture(ResourceManager.tmt_brickwall);
            ModelBrickWall.instance.render();
            ResourceManager.brickwall.renderAll();
        }
		
        if(b == ModBlocks.great_wall) {
            bindTexture(ResourceManager.tmt_greatwall);
            ModelGreatWall.instance.render();
            ResourceManager.stonewall.renderAll();
        }
		
        if(b == ModBlocks.berlin_wall) {
            bindTexture(ResourceManager.tmt_berliner);
            ModelBerliner.instance.render();
        }*/
		
        if(b == ModBlocks.med_tent) {
            bindTexture(ResourceManager.tmt_medtent);
            ModelMedTent.instance.render();
        }
		
        if(b == ModBlocks.tp_tent) {
            bindTexture(ResourceManager.tmt_tptent);
            ModelTpTent.instance.render();
        }
		
        if(b == ModBlocks.statue) {
            bindTexture(ResourceManager.tmt_statue);
            ResourceManager.statue.renderAll();
        }

        GL11.glPopMatrix();
	}

}
