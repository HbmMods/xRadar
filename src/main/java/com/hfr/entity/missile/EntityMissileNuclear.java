package com.hfr.entity.missile;

import com.hfr.entity.EntityNukeCloudSmall;
import com.hfr.entity.logic.EntityNuclearBlast;
import com.hfr.main.MainRegistry;

import net.minecraft.world.World;

public class EntityMissileNuclear extends EntityMissileBaseSimple {

	public EntityMissileNuclear(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileNuclear(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ).scaleMulti(2.5F));
    	worldObj.spawnEntityInWorld(EntityNuclearBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.nukeRadius, MainRegistry.nukeStrength, MainRegistry.nukeDist, MainRegistry.nukeKill));
	}

	@Override
	public void onForceImpact() {
		super.onForceImpact();
		onImpact();
	}

	@Override
	public int getMissileType() {
		return 2;
	}

	@Override
	public boolean getIsBreaching() {
		return true;
	}
}
