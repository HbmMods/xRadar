package com.hfr.entity.missile;

import com.hfr.handler.SLBMHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMissileSpear extends EntityMissileBaseCruise {

	public int strength;
	public int type;

	public EntityMissileSpear(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileSpear(World world, float x, float y, float z, int a, int b, int strength, int type) {
		super(world, x, y, z, a, b);
		this.strength = strength;
		this.type = type;
	}

	@Override
	public void onImpact() {
		SLBMHandler.explode(this, strength, type);
	}

	@Override
	public int getMissileType() {
		return 1;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		strength = nbt.getInteger("strength");
		type = nbt.getInteger("type");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		nbt.setInteger("strength", strength);
		nbt.setInteger("type", type);
	}

	@Override
	public boolean getIsBreaching() {
		return false;
	}

}
