package com.hfr.entity.grenade;

import com.hfr.blocks.ModBlocks;
import com.hfr.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityHook extends EntityThrowable {

	public EntityHook(World world) {
		super(world);
	}

	public EntityHook(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		
		if(worldObj.isRemote)
			return;
		
		this.setDead();
		
		if(pos.typeOfHit == MovingObjectType.ENTITY) {
			pos.entityHit.attackEntityFrom(DamageSource.generic, 0.0F);
			this.dropItem(ModItems.hook, 1);
			
		} else if(pos.typeOfHit == MovingObjectType.ENTITY) {
			this.dropItem(ModItems.hook, 1);
			
		} else {
			
			ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit);
			
			if(dir == ForgeDirection.UP || dir == ForgeDirection.DOWN || dir == ForgeDirection.UNKNOWN) {
				this.dropItem(ModItems.hook, 1);
				return;
			}
			
			int x = pos.blockX;
			int y = pos.blockY;
			int z = pos.blockZ;
			
			if(worldObj.getBlock(x, y, z) == ModBlocks.rope) {
				this.dropItem(ModItems.hook, 1);
				return;
			}
			
			x += dir.offsetX;
			y += dir.offsetY;
			z += dir.offsetZ;
			
			if(!worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z)) {
				this.dropItem(ModItems.hook, 1);
				return;
			}
			
			int meta = dir.ordinal();
			
			for(int i = 0; i < 10; i ++) {
				
				if(worldObj.getBlock(x, y - i, z).isReplaceable(worldObj, x, y - 1, z)) {
					worldObj.setBlock(x, y - i, z, ModBlocks.rope, meta, 3);
					worldObj.scheduleBlockUpdate(x, y - i, z, ModBlocks.rope, 400);
				} else {
					break;
				}
			}
		}
	}
}
