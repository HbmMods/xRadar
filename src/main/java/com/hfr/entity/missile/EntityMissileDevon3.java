package com.hfr.entity.missile;

import com.hfr.entity.EntityNukeCloudSmall;
import com.hfr.entity.logic.EntityBlast;
import com.hfr.main.MainRegistry;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class EntityMissileDevon3 extends EntityMissileBaseCruise {

	public EntityMissileDevon3(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileDevon3(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
		for(int i = 0; i < 5; i++)
			this.worldObj.createExplosion(scapegoat, this.posX, this.posY, this.posZ, 50.0F, true);
		
		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ));
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t3blast, MainRegistry.t3Damage, 15, 20, false));
	}

	@Override
	public void onForceImpact() {
		super.onForceImpact();
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t3blast, MainRegistry.t3Damage, 0, 0, false));
	}

	@Override
	public int getMissileType() {
		return 2;
	}

	@Override
	public boolean getIsBreaching() {
		return false;
	}

}
