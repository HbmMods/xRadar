package com.hfr.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.hbm.blocks.ModBlocks;

/**
 * TileUtil
 */
public class TileUtil {

    public static boolean hasPlatform(World world, int x, int y, int z) {
        for (int X = x - 1; X < x + 1; X++) {
            for (int Z = z - 1; Z < z + 1; Z++) {
                if (!Block.isEqualTo(world.getBlock(X, y, Z), ModBlocks.concrete)) {
                    return false;
                }
            }
        }
        return true;
    }

}
