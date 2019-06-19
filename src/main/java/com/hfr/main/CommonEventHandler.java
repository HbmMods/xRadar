package com.hfr.main;

import java.util.ArrayList;
import java.util.List;

import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.SRadarPacket;
import com.hfr.packet.VRadarDestructorPacket;
import com.hfr.packet.VRadarPacket;
import com.hfr.render.hud.RenderRadarScreen.Blip;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class CommonEventHandler {

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if(!player.worldObj.isRemote) {
			
			Object vehicle = ReflectionEngine.getVehicleFromSeat(player.ridingEntity);
			
			//if the player is sitting in a vehicle with radar support
			if(vehicle != null && (ReflectionEngine.hasValue(vehicle, Boolean.class, "hasRadar", false) || ReflectionEngine.hasValue(vehicle, Boolean.class, "hasPlaneRadar", false))) {
			
				//System.out.println("Radar kicked in!!!");
				float range = ReflectionEngine.hasValue(vehicle, Float.class, "radarRange", 1.0F);
				int offset = ReflectionEngine.hasValue(vehicle, Integer.class, "radarPositionOffset", 0);
				boolean isPlaneRadar = ReflectionEngine.hasValue(vehicle, Boolean.class, "hasPlaneRadar", false);
				float altitude = isPlaneRadar ? 55 : 30;
				
				boolean sufficient = altitude <= player.posY;
				List<Blip> blips = new ArrayList();
				
				if(sufficient) {
					double des = isPlaneRadar ? altitude : player.posY - 2;
					List<Entity> entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - range, des, player.posZ - range, player.posX + range, Double.POSITIVE_INFINITY, player.posZ + range));
					
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
						if(entity.isRiding() && ReflectionEngine.hasValue(vehicle, Float.class, "radarRange", null) != null &&
								player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ)) &&
								player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ))) {
							//only detect if visible on radar or the radar is on a ground vehicle
							if(ReflectionEngine.hasValue(vehicle, Boolean.class, "radarVisbile", false)) {
								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								int type = 1;
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

}
