package com.hfr.entity.missile;

import com.hfr.entity.logic.EntityEMP;

import net.minecraft.world.World;

public class EntityMissileEMPStrong extends EntityMissileBaseSimple {

	public EntityMissileEMPStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileEMPStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {

    	worldObj.newExplosion(this, posX, posY, posZ, 5F, false, false);
    	
		EntityEMP emp = new EntityEMP(worldObj);
		emp.posX = posX;
		emp.posY = posY;
		emp.posZ = posZ;
		
		worldObj.spawnEntityInWorld(emp);
	}

	@Override
	public void onForceImpact() {
		super.onForceImpact();
		onImpact();
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
