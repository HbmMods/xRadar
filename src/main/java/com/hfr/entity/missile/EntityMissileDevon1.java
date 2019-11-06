package com.hfr.entity.missile;

import com.hfr.entity.logic.EntityBlast;
import com.hfr.main.MainRegistry;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class EntityMissileDevon1 extends EntityMissileBaseCruise {

	public EntityMissileDevon1(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileDevon1(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
    	worldObj.newExplosion(scapegoat, posX, posY, posZ, 10F, false, true);
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t1blast, MainRegistry.t1Damage, 5, 5, false));
	}

	@Override
	public void onForceImpact() {
		super.onForceImpact();
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t1blast, MainRegistry.t1Damage, 0, 0, false));
	}

	@Override
	public int getMissileType() {
		return 0;
	}

	@Override
	public boolean getIsBreaching() {
		return false;
	}

}
