package com.hfr.main;

import java.util.ArrayList;
import java.util.List;

import com.hfr.data.AntiMobData;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.SRadarPacket;
import com.hfr.packet.VRadarDestructorPacket;
import com.hfr.packet.VRadarPacket;
import com.hfr.render.hud.RenderRadarScreen.Blip;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommonEventHandler {

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if(!player.worldObj.isRemote) {

			Object vehicle = ReflectionEngine.getVehicleFromSeat(player.ridingEntity);
			
			//if the player is sitting in a vehicle with radar support
			if(vehicle != null && (ReflectionEngine.hasValue(vehicle, Boolean.class, "hasRadar", false) || ReflectionEngine.hasValue(vehicle, Boolean.class, "hasPlaneRadar", false))) {
				
				int delay = ReflectionEngine.hasValue(vehicle, Integer.class, "radarRefreshDelay", 4);
				
				//stop radar operation if the delay isn't ready
				if(player.ticksExisted % delay != 0)
					return;
				
				float range = ReflectionEngine.hasValue(vehicle, Float.class, "radarRange", 1.0F);
				int offset = ReflectionEngine.hasValue(vehicle, Integer.class, "radarPositionOffset", 0);
				boolean isPlaneRadar = ReflectionEngine.hasValue(vehicle, Boolean.class, "hasPlaneRadar", false);
				float altitude = isPlaneRadar ? 55 : 30;
				double des = isPlaneRadar ? altitude : player.posY - 2;
				
				boolean sufficient = altitude <= player.posY;
				List<Blip> blips = new ArrayList();
				
				if(sufficient) {
					List<Entity> entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - range, des, player.posZ - range, player.posX + range, 3000, player.posZ + range));
					
					for(Entity entity : entities) {
						
						//player does not detect himself
						if(entity == player)
							continue;

						double dX = entity.posX - player.posX;
						double dZ = entity.posZ - player.posZ;
						
						//eliminates blips that won't ever appear on radar screen
						if(Math.sqrt(dX * dX + dZ * dZ) > range)
							continue;
						
						//only detect other players that are in a flans vehicle, players and targets must not be covered by blocks
						if(player.worldObj.getHeightValue((int)player.posX, (int)player.posZ) <= player.posY + 2 &&
								player.worldObj.getHeightValue((int)entity.posX, (int)entity.posZ) <= entity.posY + 2) {
							
							Object bogey = ReflectionEngine.getVehicleFromSeat(entity.ridingEntity);
							boolean isRiding = bogey != null;
							
							//only detect if visible on radar or the radar is on a ground vehicle
							if(ReflectionEngine.hasValue(bogey, Boolean.class, "radarVisible", false)) {
								
								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								
								//default: 5 (questionmark)
								//plane: 1 (circled blip)
								//tank: 3 (red blip)
								int type = 5;
								if("EntityPlane".equals(bogey.getClass().getSimpleName()))
									type = 1;
								if("EntityVehicle".equals(bogey.getClass().getSimpleName()))
									type = 3;
								
								blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, type));
							
							} else if(ReflectionEngine.hasValue(entity, Boolean.class, "missileRadarVisible", false)) {
								
								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								int type = 4;
								blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, type));
							}
						}
					}
				}
					
				//directed traffic to avoid spammy broadcast
				PacketDispatcher.wrapper.sendTo(new SRadarPacket(blips.toArray(new Blip[0]), sufficient, true, offset, (int)range), (EntityPlayerMP) player);
				
			} else {
				//if the player does not have a radar up, he will only receive destructor packets that remove all blips and deny radar screens
				PacketDispatcher.wrapper.sendTo(new SRadarPacket(null, false, false, 0, 0), (EntityPlayerMP) player);
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		
		World world = event.world;
		
		if(!world.isRemote) {
			
			List<int[]> list = AntiMobData.getData(world).list;
			
			for(int[] i : list) {
				
				List<EntityMob> entities = world.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(i[0], 0, i[1], i[2] + 1, 255, i[3] + 1));
				
				for(EntityMob entity : entities) {
					entity.setHealth(0);
				}
			}
		}
		
	}

}
