package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileGeneric extends EntityMissileBaseSimple {

	public EntityMissileGeneric(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileGeneric(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		//ExplosionLarge.explode(worldObj, posX, posY, posZ, 10.0F, true, true, true);
		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
    	worldObj.newExplosion(scapegoat, posX, posY, posZ, 10F, false, true);
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t1blast, MainRegistry.t1Damage));
	}

	@Override
	public int getMissileType() {
		return 0;
	}

}
