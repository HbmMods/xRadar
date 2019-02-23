package com.hfr.entity;

import java.util.ArrayList;
import java.util.List;

import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.ParticleBurstPacket;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
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
		
		if (te != null && te instanceof IEnergyProvider) {
			machines.add(new int[] { x, y, z });
		}
	}
	
	private void emp(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x,y,z);
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		boolean flag = false;
		
		if (te != null && te instanceof IEnergyProvider) {

			((IEnergyHandler)te).extractEnergy(ForgeDirection.UP, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.UP), false);
			((IEnergyHandler)te).extractEnergy(ForgeDirection.DOWN, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.DOWN), false);
			((IEnergyHandler)te).extractEnergy(ForgeDirection.NORTH, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.NORTH), false);
			((IEnergyHandler)te).extractEnergy(ForgeDirection.SOUTH, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.SOUTH), false);
			((IEnergyHandler)te).extractEnergy(ForgeDirection.EAST, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.EAST), false);
			((IEnergyHandler)te).extractEnergy(ForgeDirection.WEST, ((IEnergyHandler)te).getEnergyStored(ForgeDirection.WEST), false);
			flag = true;
		}
		
		if(flag && rand.nextInt(MainRegistry.empParticle) == 0) {
			
			PacketDispatcher.wrapper.sendToAll(new ParticleBurstPacket(x, y, z, Block.getIdFromBlock(Blocks.stained_glass), 3));
	        
		}
		
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }

}
