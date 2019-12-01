package com.hfr.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockSpeedy extends Block {
	
	protected BlockSpeedy(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity e)
	{
		if(e instanceof EntityPlayer) {

			double tan = Math.atan2(e.motionX, e.motionZ);
			
			e.motionX += Math.sin(tan) * 0.15;
			e.motionZ += Math.cos(tan) * 0.15;
		}
	}

}
