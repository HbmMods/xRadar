package com.hfr.schematic;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

public class Schematic {

    private static final FMLControlledNamespacedRegistry<Block> BLOCK_REGISTRY = GameData.getBlockRegistry();
    
	public short[][][] blocks;
	public byte[][][] metadata;
	public int width;
	public int height;
	public int length;
	public String name = "";
	public int value = 1;
    
	public Schematic(int width, int height, int length) {
		
		this.blocks = new short[width][height][length];
        this.metadata = new byte[width][height][length];

        this.width = width;
        this.height = height;
        this.length = length;
	}
	
	public boolean setBlock(final int x, final int y, final int z, final Block block, final int metadata) {
        if (!isValid(x, y, z)) {
            return false;
        }

        final int id = BLOCK_REGISTRY.getId(block);
        if (id == -1) {
            return false;
        }

        this.blocks[x][y][z] = (short) id;
        setBlockMetadata(x, y, z, metadata);
        return true;
    }
	
    public boolean setBlockMetadata(final int x, final int y, final int z, final int metadata) {
        if (!isValid(x, y, z)) {
            return false;
        }

        this.metadata[x][y][z] = (byte) (metadata & 0x0F);
        return true;
    }
	
	private boolean isValid(final int x, final int y, final int z) {
        return !(x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.height || z >= this.length);
    }

}
