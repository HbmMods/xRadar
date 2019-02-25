package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileBurst extends EntityMissileBaseAdvanced {

	public EntityMissileBurst(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileBurst(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		for(int i = 0; i < 5; i++)
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 50.0F, true);
		
		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ));
		System.out.println(MainRegistry.t3blast);
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t3blast));
	}

	@Override
	public int getMissileType() {
		return 2;
	}
}
