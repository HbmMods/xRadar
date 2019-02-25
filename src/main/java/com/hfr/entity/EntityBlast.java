package com.hfr.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;
import com.hfr.main.ReflectionEngine;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.ParticleBurstPacket;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityBlast extends Entity {
	
	int life = MainRegistry.fireDuration;
	int size;

	public EntityBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			
			fire();
			
			if(this.ticksExisted > life)
				this.setDead();
		}
	}
	
	private void fire() {
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size));
		
		for(Entity e : list) {
			
			if(e instanceof EntityPlayer || !(e instanceof EntityLivingBase)) {
				
				double dist = Math.sqrt(
						Math.pow(e.posX - posX, 2) +
						Math.pow(e.posY - posY, 2) +
						Math.pow(e.posZ - posZ, 2));
				
				if(dist <= size) {
					e.setFire(5);
					e.attackEntityFrom(MainRegistry.blast, 1000.0F);
				}
			}
		}
	}
	
	public static EntityBlast statFac(World world, double posX, double posY, double posZ, int size) {
		
		EntityBlast blast = new EntityBlast(world);
		blast.posX = posX;
		blast.posY = posY;
		blast.posZ = posZ;
		blast.size = size;
		return blast;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("age");
		this.size = nbt.getInteger("size");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", this.ticksExisted);
		nbt.setInteger("size", this.size);
	}

}
