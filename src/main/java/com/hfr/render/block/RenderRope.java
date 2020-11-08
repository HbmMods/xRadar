package com.hfr.render.block;

import com.hfr.main.ResourceManager;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderRope implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) { }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
        Tessellator tessellator = Tessellator.instance;
        IIcon iicon = block.getIcon(0, 0);

        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        double d0 = (double)iicon.getMinU();
        double d1 = (double)iicon.getMinV();
        double d2 = (double)iicon.getMaxU();
        double d3 = (double)iicon.getMaxV();
        int l = world.getBlockMetadata(x, y, z);
        double d4 = 0.0D;
        double d5 = 0.05000000074505806D;

        if (l == 5)
        {
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d0, d1);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d0, d3);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d2, d3);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d2, d1);

            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d0, d3);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d0, d1);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d2, d1);
            tessellator.addVertexWithUV((double)x + d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d2, d3);
        }

        if (l == 4)
        {
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d2, d3);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d2, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d0, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d0, d3);
            
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 1) + d4, d2, d1);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 1) + d4, d2, d3);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 0) - d4, (double)(z + 0) - d4, d0, d3);
            tessellator.addVertexWithUV((double)(x + 1) - d5, (double)(y + 1) + d4, (double)(z + 0) - d4, d0, d1);
        }

        if (l == 3)
        {
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)z + d5, d2, d3);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)z + d5, d2, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)z + d5, d0, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)z + d5, d0, d3);
            
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)z + d5, d2, d1);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)z + d5, d2, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)z + d5, d0, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)z + d5, d0, d1);
        }

        if (l == 2)
        {
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d0, d1);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d0, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d2, d3);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d2, d1);

            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d0, d3);
            tessellator.addVertexWithUV((double)(x + 1) + d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d0, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 1) + d4, (double)(z + 1) - d5, d2, d1);
            tessellator.addVertexWithUV((double)(x + 0) - d4, (double)(y + 0) - d4, (double)(z + 1) - d5, d2, d3);
        }

        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return ResourceManager.id_rope;
	}
}
