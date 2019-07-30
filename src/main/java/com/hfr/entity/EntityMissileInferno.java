package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileInferno extends EntityMissileBaseSimple {

	public EntityMissileInferno(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileInferno(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		//ExplosionLarge.explodeFire(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 35.0F, true, true, true);
		//ExplosionChaos.burn(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 10);
		//ExplosionChaos.flameDeath(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25);
		EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
    	worldObj.newExplosion(scapegoat, posX, posY, posZ, 35F, true, true);
    	
		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ));
    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, MainRegistry.t3blast, MainRegistry.t3Damage));
	}

	@Override
	public int getMissileType() {
		return 2;
	}
}
