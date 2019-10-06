package com.hfr.handler;

import com.hfr.entity.EntityNukeCloudSmall;
import com.hfr.entity.logic.EntityBlast;
import com.hfr.entity.logic.EntityNuclearBlast;
import com.hfr.entity.missile.EntityMissileMartin;
import com.hfr.entity.missile.EntityMissilePegasus;
import com.hfr.entity.missile.EntityMissileSpear;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.main.ReflectionEngine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class SLBMHandler {
	
	public static void tryLaunch(World world, EntityPlayer player, int xCoord, int zCoord) {
		
		if(!world.isRemote/* && hasSLBM(player)*/) {
			
			//Object vehicle = getVehicle(player);
			int type = 2;//getFlightType(vehicle);
			int range = 1000;//getRange(vehicle);
			int strength = 10;//getStrength(vehicle);
			int warhead = 2;//getWarhead(vehicle);

			float posX = (float) player.posX;
			float posY = (float) player.posY;
			float posZ = (float) player.posZ;
			
			Item missile = ModItems.missile_decoy;//getMissile(type, warhead);
			
			if(player.inventory.hasItem(missile)) {
				
				double dist = Math.sqrt(Math.pow(player.posX - xCoord, 2) + Math.pow(player.posZ - zCoord, 2));
				
				if(dist <= range) {
					
					switch(type) {
					//Martin-2
					case 0:
						EntityMissileMartin martin = new EntityMissileMartin(world, posX, posY, posZ, xCoord, zCoord, strength, warhead);
						world.spawnEntityInWorld(martin);
						break;
					//Tempest
					case 1:
						EntityMissilePegasus tempest = new EntityMissilePegasus(world, posX, posY, posZ, xCoord, zCoord, strength, warhead);
						world.spawnEntityInWorld(tempest);
						break;
					//Spear
					case 2:
						EntityMissileSpear spear = new EntityMissileSpear(world, posX, posY, posZ, xCoord, zCoord, strength, warhead);
						world.spawnEntityInWorld(spear);
						break;
					}
					
					player.inventory.consumeInventoryItem(missile);
					world.playSoundEffect(posX, posY, posZ, "hfr:block.buttonYes", 1.0F, 1.0F);
					return;
				}
			}

			world.playSoundEffect(posX, posY, posZ, "hfr:block.buttonNo", 1.0F, 1.0F);
		}
	}
	
	//returns true if the player is sitting in an SLBM-compatible vehicle
	public static boolean hasSLBM(EntityPlayer player) {
		
		Object vehicle = getVehicle(player);
		return getFlightType(vehicle) > 0;
	}
	
	//just a wrapper for the reflection engine
	public static Object getVehicle(EntityPlayer player) {

		Object vehicle = ReflectionEngine.getVehicleFromSeat(player.ridingEntity);
		return vehicle;
	}
	
	public static int getFlightType(Object vehicle) {
		
		return getInteger(vehicle, "slbmFlightType");
	}
	
	public static int getDelay(Object vehicle) {
		
		return getInteger(vehicle, "slbmDelay");
	}
	
	public static int getRange(Object vehicle) {
		
		return getInteger(vehicle, "slbmRange");
	}
	
	public static int getStrength(Object vehicle) {
		
		return getInteger(vehicle, "slbmStrength");
	}
	
	public static int getWarhead(Object vehicle) {
		
		return getInteger(vehicle, "slbmWarheadType");
	}
	
	public static int getInteger(Object vehicle, String name) {
		
		if(vehicle != null) {
			
			int value = ReflectionEngine.hasValue(vehicle, Integer.class, name, 0);
			return value;
		}
		
		return 0;
	}
	
	public static Item getMissileFromPlayer(EntityPlayer player) {

		Object vehicle = getVehicle(player);
		
		if(vehicle != null) {
			int flight = getFlightType(vehicle);
			int warhead = getWarhead(vehicle);
			
			return getMissile(flight, warhead);
		}
		
		return ModItems.missile_decoy;
	}
	
	public static Item getMissile(int flightType, int warhead) {
		
		if(flightType == 0) {
			
			if(warhead == 0)
				return ModItems.slbm_martin_1;
			else if(warhead == 1)
				return ModItems.slbm_martin_2;
			else if(warhead == 2)
				return ModItems.slbm_martin_3;
			
		} else if(flightType == 1) {
			
			if(warhead == 0)
				return ModItems.slbm_pegasus_1;
			else if(warhead == 1)
				return ModItems.slbm_pegasus_2;
			else if(warhead == 2)
				return ModItems.slbm_pegasus_3;
			
		} else if(flightType == 2) {
			
			if(warhead == 0)
				return ModItems.slbm_spear_1;
			else if(warhead == 1)
				return ModItems.slbm_spear_2;
			else if(warhead == 2)
				return ModItems.slbm_spear_3;
			
		}
		
		return ModItems.missile_decoy;
	}
	
	public static void explode(Entity missile, int strength, int type) {
		
		World worldObj = missile.worldObj;
		double posX = missile.posX;
		double posY = missile.posY;
		double posZ = missile.posZ;
		
		if(type == 0 || type == 1) {

			EntityTNTPrimed scapegoat = new EntityTNTPrimed(worldObj);
	    	worldObj.newExplosion(scapegoat, posX, posY, posZ, strength, type == 1, true);
	    	
	    	worldObj.spawnEntityInWorld(EntityBlast.statFac(worldObj, posX, posY, posZ, strength * 2, 100, 0, 0, false));
	    	
	    	if(strength >= 35)
	    		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ));
		}
		
		if(type == 2) {

			worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFac(worldObj, posX, posY, posZ).scaleMulti(2.5F));
	    	worldObj.spawnEntityInWorld(EntityNuclearBlast.statFac(worldObj, posX, posY, posZ, strength, MainRegistry.nukeStrength, MainRegistry.nukeDist, MainRegistry.nukeKill));
		}
	}
}
