package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileStrong extends EntityMissileBaseSimple {

	public EntityMissileStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		//ExplosionLarge.explode(worldObj, posX, posY, posZ, 25.0F, true, true, true);
		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
    	worldObj.newExplosion(scapegoat, posX, posY, posZ, 25F, false, true);
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t2blast));
	}

	@Override
	public int getMissileType() {
		return 1;
	}

}
