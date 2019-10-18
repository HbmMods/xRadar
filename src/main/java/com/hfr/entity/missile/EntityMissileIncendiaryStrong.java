package com.hfr.entity.missile;

import com.hfr.entity.logic.EntityBlast;
import com.hfr.main.MainRegistry;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class EntityMissileIncendiaryStrong extends EntityMissileBaseSimple {

	public EntityMissileIncendiaryStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileIncendiaryStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		//ExplosionLarge.explodeFire(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 25.0F, true, true, true);
		//ExplosionChaos.flameDeath(this.worldObj, (int)((float)this.posX + 0.5F), (int)((float)this.posY + 0.5F), (int)((float)this.posZ + 0.5F), 25);
		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
    	worldObj.newExplosion(scapegoat, posX, posY, posZ, 25F, true, true);
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t2blast, MainRegistry.t2Damage, 10, 10, true));
	}

	@Override
	public void onForceImpact() {
		super.onForceImpact();
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t2blast, MainRegistry.t2Damage, 0, 0, false));
	}

	@Override
	public int getMissileType() {
		return 1;
	}

	@Override
	public boolean getIsBreaching() {
		return false;
	}
}
