package com.hfr.tileentity.machine;

import java.util.List;

import com.hfr.entity.IEmpable;
import com.hfr.items.ModItems;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.AuxElectricityPacket;
import com.hfr.potion.HFRPotion;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineEMP extends TileEntityMachineBase implements IEnergyHandler {

	public int range;
	public static final int maxRange = 500;
	public boolean isOn;
	
	public EnergyStorage storage = new EnergyStorage(100000000, 5000000, 5000000);

	public TileEntityMachineEMP() {
		super(1);
		range = 100;
	}

	@Override
	public String getName() {
		return "container.machineEMP";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(range < 10)
				range = 10;
			if(range > 500)
				range = 500;
			
			if(slots[0] != null && slots[0].getItem() == ModItems.battery)
				storage.setEnergyStored(storage.getMaxEnergyStored());

			if (slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem) {
				IEnergyContainerItem item = (IEnergyContainerItem) slots[0].getItem();
				int extract = (int) Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(),
						item.getEnergyStored(slots[0]));

				int e = item.extractEnergy(slots[0], extract, false);
				storage.setEnergyStored(storage.getEnergyStored() + e);
			}
			
			if(isOn) {
				int required = range * range * 20;
				
				if(storage.getEnergyStored() >= required) {
					storage.setEnergyStored(storage.getEnergyStored() - required);
					
					List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - range, 0, zCoord - range, xCoord + range, 10000, zCoord + range));
					
					for(Entity e : entities) {
						
						Vec3 vec = Vec3.createVectorHelper(e.posX - xCoord, 0, e.posZ - zCoord);
						
						if(vec.lengthVector() <= range) {
							
							if(e instanceof EntityPlayer) {
								((EntityPlayer)e).addPotionEffect(new PotionEffect(HFRPotion.emp.id, 5 * 20, 0, true));
							}
							
							if(e instanceof IEmpable) {
								((IEmpable)e).pulse();
							}
						}
					}
					
				} else {
					isOn = false;
				}
			}

			this.updateGauge(isOn ? 1 : 0, 0, 50);
			this.updateGauge(range, 1, 50);
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, storage.getEnergyStored()));
		}
	}

	@Override
	public void processGauge(int val, int id) {
		
		switch(id) {
		case 0: isOn = (val == 1); break;
		case 1: range = val; break;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		range = nbt.getInteger("range");
		isOn = nbt.getBoolean("isOn");
		storage.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("range", range);
		nbt.setBoolean("isOn", isOn);
		storage.writeToNBT(nbt);
	}

	public int getPowerScaled(int i) {
		
		long buf = storage.getEnergyStored();
		
		buf *= i;
		
		return (int)(buf / storage.getMaxEnergyStored());
	}

	public int getRangeScaled(int i) {
		return (range * i) / maxRange;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
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

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		double toSend = Math.min(storage.getEnergyStored(), storage.getMaxExtract());

		if (!simulate) {
			storage.setEnergyStored(storage.getEnergyStored() - (int) Math.round(toSend));
		}

		return (int) Math.round(toSend);
	}

}
