package com.hfr.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockTemporary extends Block {

	protected BlockTemporary(Material p_i45394_1_) {
		super(p_i45394_1_);
		//this.setTickRandomly(true);
	}
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if(!world.isRemote) {
        	world.func_147480_a(x, y, z, false);
        }
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this, 30 * 20);
    }

}
