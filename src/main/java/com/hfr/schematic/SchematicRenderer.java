package com.hfr.schematic;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;

public class SchematicRenderer {

    private static final FMLControlledNamespacedRegistry<Block> BLOCK_REGISTRY = GameData.getBlockRegistry();
    protected static final RenderBlocks field_94145_f = new RenderBlocks();
	
	public static void render(Schematic schem, float f0, double x, double y, double z, double cap) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0625F * 7, 0.0F);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_SRC_COLOR);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		for(int dx = 0; dx < schem.width; dx++) {
			for(int dy = 0; dy < schem.height; dy++) {
				for(int dz = 0; dz < schem.length; dz++) {

					double cx = x + dx;
					double cy = y + dy;
					double cz = z + dz;
					
					if(Math.sqrt(Math.pow(cx, 2) + Math.pow(cy, 2) + Math.pow(cz, 2)) > cap)
						continue;
		
					Block b = BLOCK_REGISTRY.getObjectById(schem.blocks[dx][dy][dz]);
					if(b == Blocks.air)
						continue;
					
					int meta = schem.metadata[dx][dy][dz];
					
					GL11.glTranslatef(dx, dy, dz);
					GL11.glPushMatrix();
					field_94145_f.renderBlockAsItem(b, meta, 1);
					GL11.glPopMatrix();
					GL11.glTranslatef(-dx, -dy, -dz);
				}
			}
		}
		
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
	}
}
