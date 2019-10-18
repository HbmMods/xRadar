package com.hfr.entity.missile;

import com.hfr.handler.SLBMHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMissileShell extends EntityMissileBaseAdvanced {

	public int strength;
	public int type;

	public EntityMissileShell(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileShell(World world, float x, float y, float z, int a, int b, int strength, int type) {
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
		return type;
	}
	
	@Override
	protected boolean trailing() {
		return false;
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

}
