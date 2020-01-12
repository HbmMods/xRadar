package com.hfr.render.block;

import com.hfr.blocks.BlockDummyable;
import com.hfr.main.ResourceManager;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderPalisade implements ISimpleBlockRenderingHandler {

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(0, world.getBlockMetadata(x, y, z));
		
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

        if (renderer.hasOverrideBlockTexture())
        {
            iicon = renderer.overrideBlockTexture;
        }
        
        if(world.getBlockMetadata(x, y, z) < 12)
        	return true;
        
        float rot = 0;
		float p = 0.0625F;

		float offX = x - p * 0.5F + 0.5F;
		float offY = y;
		float offZ = z - p * 0.5F + 0.5F;

		switch(world.getBlockMetadata(x, y, z) - BlockDummyable.offset)
		{
		case 2: rot = 90; offX -= 0.5F; break;
		case 4: rot = 180; offZ += 0.5F; break;
		case 3: rot = 270; offX += 0.5F; break;
		case 5: rot = 0; offZ -= 0.5F; break;
		}
        
        tessellator.addTranslation(offX, offY, offZ);
		ObjUtil.renderWithIcon((WavefrontObject) ResourceManager.palisade, iicon, tessellator, (float) (rot * Math.PI / 180F) , true);
        tessellator.addTranslation(-offX, -offY, -offZ);
        
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return ResourceManager.id_palisade;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }
}
