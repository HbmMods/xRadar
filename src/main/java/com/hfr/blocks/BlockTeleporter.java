package com.hfr.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hfr.tileentity.machine.TileEntityTeleporter;

public class BlockTeleporter extends BlockContainer {

    protected BlockTeleporter(Material material) {
        super(material);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityTeleporter();
    }

}
