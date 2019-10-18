package com.hfr.entity.grenade;

import com.hfr.entity.EntityNukeCloudSmall;
import com.hfr.entity.logic.EntityNuclearBlast;
import com.hfr.items.ItemGrenade;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeNuclear extends EntityGrenadeBouncyBase {

	public EntityGrenadeNuclear(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeNuclear(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeNuclear(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.worldObj.isRemote) {
			this.setDead();
			
			worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ).scaleMulti(0.75F));
	    	worldObj.spawnEntityInWorld(EntityNuclearBlast.statFac(worldObj, posX, posY, posZ, 35, MainRegistry.nukeStrength, MainRegistry.nukeDist, 70).setSimple(true));
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_nuclear);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
