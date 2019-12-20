package com.hfr.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockSpeedy extends Block {
	
	double speed = 0D;
	
	protected BlockSpeedy(Material p_i45394_1_, double speed) {
		super(p_i45394_1_);
		this.speed = speed;
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity e)
	{
		if(e instanceof EntityPlayer) {

			double tan = Math.atan2(e.motionX, e.motionZ);
			
			e.motionX += Math.sin(tan) * speed;
			e.motionZ += Math.cos(tan) * speed;
		}
	}

}
