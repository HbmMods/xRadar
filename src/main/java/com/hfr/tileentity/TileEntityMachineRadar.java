package com.hfr.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.hfr.entity.EntityMissileAntiBallistic;
import com.hfr.entity.EntityMissileBaseAdvanced;
import com.hfr.entity.EntityMissileBaseSimple;
import com.hfr.main.MainRegistry;
import com.hfr.packet.AuxElectricityPacket;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.TERadarDestructorPacket;
import com.hfr.packet.TERadarPacket;
import com.hfr.packet.TESRadarPacket;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRadar extends TileEntity implements IEnergyHandler {

	public List<RadarEntry> nearbyMissiles = new ArrayList();
	int pingTimer = 0;
	final static int maxTimer = 40;
	public int mode = 0;
	int scanTimer = 0;
	int maxScan = 5;
	
	public EnergyStorage storage = new EnergyStorage(MainRegistry.radarConsumption * 1000, MainRegistry.radarConsumption * 10, MainRegistry.radarConsumption * 10);

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		storage.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		storage.writeToNBT(nbt);
	}

	@Override
	public void updateEntity() {
		
		if(this.yCoord < MainRegistry.radarAltitude)
			return;
		
		/*if(!worldObj.isRemote && storage.getEnergyStored() == 0)
			nearbyMissiles.clear();

		if(!worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAll(new TERadarDestructorPacket(xCoord, yCoord, zCoord, mode));
		}*/
		
		if(storage.getEnergyStored() > 0) {

			if(!worldObj.isRemote) {
				
				scanTimer++;
				if(scanTimer == maxScan) {
					scanTimer = 0;
					allocateMissiles();
					sendMissileData();
				}
			}
			
			storage.setEnergyStored(storage.getEnergyStored() - MainRegistry.radarConsumption);
			if(storage.getEnergyStored() < 0)
				storage.setEnergyStored(0);
		} else {
			PacketDispatcher.wrapper.sendToAll(new TESRadarPacket(null,xCoord, yCoord, zCoord, mode));
		}
		
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
		
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, storage.getEnergyStored()));
		
		pingTimer++;
		
		if(storage.getEnergyStored() > 0 && pingTimer >= maxTimer && MainRegistry.sound) {
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.sonarPing", 2.0F, 1.0F);
			pingTimer = 0;
		}
		
		if(MainRegistry.freeRadar)
			this.storage.setEnergyStored(storage.getMaxEnergyStored());
	}
	
	private void allocateMissiles() {
		
		nearbyMissiles.clear();
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5 - MainRegistry.radarRange, 0, zCoord + 0.5 - MainRegistry.radarRange, xCoord + 0.5 + MainRegistry.radarRange, 5000, zCoord + 0.5 + MainRegistry.radarRange));

		for(Entity e : list) {
			
			if(e.posY >= this.yCoord + MainRegistry.radarBuffer) {

				if(mode == 0 || mode == 2) {
					if(e instanceof EntityPlayer)
						nearbyMissiles.add(new RadarEntry((int)e.posX, (int)e.posY, (int)e.posZ, ((EntityPlayer)e).getDisplayName()));
				}
				if(mode == 0 || mode == 1) {
					if(e instanceof EntityMissileBaseAdvanced)
						nearbyMissiles.add(new RadarEntry((int)e.posX, (int)e.posY, (int)e.posZ, "Tier " + (((EntityMissileBaseAdvanced)e).getMissileType() + 1) + " Missile"));
					if(e instanceof EntityMissileBaseSimple)
						nearbyMissiles.add(new RadarEntry((int)e.posX, (int)e.posY, (int)e.posZ, "Tier " + (((EntityMissileBaseSimple)e).getMissileType() + 1) + " Missile"));
					if(e instanceof EntityMissileAntiBallistic)
						nearbyMissiles.add(new RadarEntry((int)e.posX, (int)e.posY, (int)e.posZ, "Anti-Ballistic Missile"));
				}
			}
		}
	}
	
	public int getRedPower() {
		
		if(!nearbyMissiles.isEmpty()) {
			
			double maxRange = MainRegistry.radarRange * Math.sqrt(2D);
			
			int power = 0;
			
			for(int i = 0; i < nearbyMissiles.size(); i++) {
				
				RadarEntry j = nearbyMissiles.get(i);
				double dist = Math.sqrt(Math.pow(j.posX - xCoord, 2) + Math.pow(j.posZ - zCoord, 2));
				int p = 15 - (int)Math.floor(dist / maxRange * 15);
				
				if(p > power)
					power = p;
			}
			
			return power;
		}
		
		return 0;
	}
	
	private void sendMissileData() {
		
		/*for(RadarEntry e : this.nearbyMissiles) {
			PacketDispatcher.wrapper.sendToAll(new TERadarPacket(xCoord, yCoord, zCoord, e.posX, e.posY, e.posZ, e.name));
		}*/

		PacketDispatcher.wrapper.sendToAll(new TESRadarPacket(nearbyMissiles.toArray(new RadarEntry[0]),xCoord, yCoord, zCoord, mode));
	}
	
	public long getPowerScaled(long i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from != ForgeDirection.UP && from != ForgeDirection.UNKNOWN;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
	
	public static class RadarEntry {
		
		public int posX;
		public int posY;
		public int posZ;
		public String name;
		
		public RadarEntry(int x, int y, int z, String s) {
			posX = x;
			posY = y;
			posZ = z;
			name = s;
		}
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		double toSend = Math.min(storage.getEnergyStored(), storage.getMaxExtract());

		if (!simulate) {
			storage.setEnergyStored(storage.getEnergyStored() - (int) Math.round(toSend));
		}

		return (int) Math.round(toSend);
	}
}
