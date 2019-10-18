package com.hfr.schematic;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class SchematicPronter {

    private static final FMLControlledNamespacedRegistry<Block> BLOCK_REGISTRY = GameData.getBlockRegistry();
	
    //takes a schematic and prints its blocks to the given coordinates
	public static void pront(World world, int x, int y, int z, Schematic schem) {
		
		for(int dx = 0; dx < schem.width; dx++) {
			for(int dy = 0; dy < schem.height; dy++) {
				for(int dz = 0; dz < schem.length; dz++) {
					
					Block b = BLOCK_REGISTRY.getObjectById(schem.blocks[dx][dy][dz]);
					int meta = schem.metadata[dx][dy][dz];
					
					if(b != null) {
						world.setBlock(x + dx, y + dy, z + dz, b, meta, 3);
					}
				}
			}
		}
	}
	
	//takes a schematic and replaces all its blocks at a given coordinate with air
	public static void delet(World world, int x, int y, int z, Schematic schem) {
		
		for(int dx = 0; dx < schem.width; dx++) {
			for(int dy = 0; dy < schem.height; dy++) {
				for(int dz = 0; dz < schem.length; dz++) {
					
					Block b = BLOCK_REGISTRY.getObjectById(schem.blocks[dx][dy][dz]);
					int meta = schem.metadata[dx][dy][dz];
					
					Block wb = world.getBlock(x + dx, y + dy, z + dz);
					int wmeta = world.getBlockMetadata(x + dx, y + dy, z + dz);
					
					if(b != null && b == wb && meta == wmeta) {
						world.setBlockToAir(x + dx, y + dy, z + dz);
					}
				}
			}
		}
	}
}
