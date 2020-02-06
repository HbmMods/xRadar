package com.hfr.entity.grenade;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import java.util.Random;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.items.ItemGrenade;
import com.hfr.items.ModItems;

public class EntityGrenadeGas extends EntityGrenadeBouncyBase {
	private static final String __OBFID = "CL_00001722";
	Random rand = new Random();

	public EntityGrenadeGas(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeGas(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeGas(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.worldObj.isRemote) {
			this.setDead();
			
			worldObj.playSoundEffect(posX, posY, posZ, "random.fizz", 5.0F, 1.0F);
			
			Ownership owner = ClowderTerritory.getOwnerFromInts((int)posX, (int)posZ);
			
			if(owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE || (owner.owner != null && !owner.owner.isRaidable()))
				return;
			
			int r = 10;
			int r2 = r * r;
			int r22 = r2 / 2;
			for (int xx = -r; xx < r; xx++) {
				int X = xx + (int)posX;
				int XX = xx * xx;
				for (int yy = -r; yy < r; yy++) {
					int Y = yy + (int)posY;
					int YY = XX + yy * yy;
					for (int zz = -r; zz < r; zz++) {
						int Z = zz + (int)posZ;
						int ZZ = YY + zz * zz;
						
						if (ZZ < r22 - rand.nextInt(3) && rand.nextInt(3) != 0) {
							if(worldObj.getBlock(X, Y, Z).getMaterial() == Material.air)
								worldObj.setBlock(X, Y, Z, ModBlocks.chlorine_gas);
						}
					}
				}
			}
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_gas);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}

}
