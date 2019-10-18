package com.hfr.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;
import com.hfr.main.ReflectionEngine;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ParticleBurstPacket;

import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityEMP extends Entity {
	
	List<int[]> machines;
	int life = MainRegistry.empDuration;

	public EntityEMP(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			if(machines == null) {
				allocate();
			} else {
				shock();
			}
			
			if(this.ticksExisted > life)
				this.setDead();
		}
	}
	
	private void allocate() {
		
		machines = new ArrayList();
		
		int radius = MainRegistry.empRadius;
		
		for(int x = -radius; x <= radius; x++) {
			
			int x2 = (int) Math.pow(x, 2);
			
			for(int y = -radius; y <= radius; y++) {
				
				int y2 = (int) Math.pow(y, 2);
				
				for(int z = -radius; z <= radius; z++) {
					
					int z2 = (int) Math.pow(z, 2);
					
					if(Math.sqrt(x2 + y2 + z2) <= radius) {
						add((int)posX + x, (int)posY + y, (int)posZ + z);
					}
				}
			}
		}
	}
	
	private void shock() {
		
		for(int i = 0; i < machines.size(); i++) {
			emp(
					machines.get(i)[0], 
					machines.get(i)[1], 
					machines.get(i)[2]
				);
		}
	}
	
	private void add(int x, int y, int z) {
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if (!MainRegistry.empSpecial && te != null && te instanceof IEnergyProvider) {
			machines.add(new int[] { x, y, z });
		}
		
		if(MainRegistry.empSpecial &&
				te instanceof IEnergyProvider ||
				te instanceof IEnergyReceiver ||
				te instanceof IEnergyHandler ||
				te instanceof IEnergyStorage ||
				te instanceof IEnergyConnection) {
			machines.add(new int[] { x, y, z });
		}
	}
	
	private void emp(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x,y,z);
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		boolean flag = false;
		
		if (te != null && te instanceof IEnergyHandler) {

			for(int i = 0; i < 10; i++) {
				((IEnergyHandler)te).extractEnergy(ForgeDirection.UP, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.UP), false);
				((IEnergyHandler)te).extractEnergy(ForgeDirection.DOWN, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.DOWN), false);
				((IEnergyHandler)te).extractEnergy(ForgeDirection.NORTH, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.NORTH), false);
				((IEnergyHandler)te).extractEnergy(ForgeDirection.SOUTH, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.SOUTH), false);
				((IEnergyHandler)te).extractEnergy(ForgeDirection.EAST, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.EAST), false);
				((IEnergyHandler)te).extractEnergy(ForgeDirection.WEST, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.WEST), false);
			}
			flag = true;
		}
		
		if(MainRegistry.empSpecial && te != null) {
			
			//electricityStored
			ReflectionEngine.setDoubleToZero(te, "electricityStored");
			flag = true;
		}
		
		/*if(MainRegistry.empSpecial && te != null) {
			
			//System.out.println("Preccesing start!");
			
			if(te instanceof IEnergyProvider ||
					te instanceof IEnergyReceiver ||
					te instanceof IEnergyHandler ||
					te instanceof IEnergyStorage ||
					te instanceof IEnergyConnection) {

				//System.out.println("Type valid!");

				List<Field> fields = ReflectionEngine.crackOpenAColdOne(EnergyStorage.class, te.getClass());

				//System.out.println("Fields found: " + fields.size());
				
				List batteries = ReflectionEngine.pryObjectsFromFieldList(fields, te);

				//System.out.println("Fields converted: " + batteries.size());
				
				for(Object o : batteries) {
					
					//System.out.println("Field casted!");
					
					if(o instanceof EnergyStorage) {
						EnergyStorage bat = (EnergyStorage)o;
						bat.setEnergyStored(0);
						flag = true;
					}
				}
			}
		}*/
		
		if(flag && rand.nextInt(MainRegistry.empParticle) == 0) {
			
			PacketDispatcher.wrapper.sendToAllAround(new ParticleBurstPacket(x, y, z, Block.getIdFromBlock(Blocks.stained_glass), 3), new TargetPoint(this.dimension, x, y, z, 10));
	        
		}
		
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("age");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", this.ticksExisted);
	}

}
