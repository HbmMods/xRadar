package com.hfr.main;

import java.util.List;

import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.VRadarDestructorPacket;
import com.hfr.packet.VRadarPacket;

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
			
			/**
			 * Important: gui always assumes radar range to be 100 to avoid scale data from being sent
			 * -> scale down every blip to 100
			 * example: range is 2000, blip is at X:500, X to send is 25
			 * Y should not be scaled (no rendering on this part)
			 */
			
			//if the player is sitting in a vehicle with radar support
			//if(player.isRiding() && ReflectionEngine.hasValue(player.ridingEntity, Boolean.class, "hasRadar", false)) {
			
				float range = 50;//ReflectionEngine.hasValue(player.ridingEntity, Float.class, "radarRange", 1.0F);
				boolean isPlaneRadar = true;//ReflectionEngine.hasValue(player.ridingEntity, Boolean.class, "hasPlaneRadar", true);
				float altitude = isPlaneRadar ? 55 : 30;
				
				boolean sufficient = altitude <= player.posY;
				
				//directed traffic to avoid spammy broadcast
				PacketDispatcher.wrapper.sendTo(new VRadarDestructorPacket(sufficient), (EntityPlayerMP) player);
				
				if(sufficient) {
					List<Entity> entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - range, altitude, player.posZ - range, player.posX + range, Double.POSITIVE_INFINITY, player.posZ + range));
					
					for(Entity entity : entities) {
						
						//player does not detect himself
						if(entity == player)
							continue;
						
						//only detect other players that are in a flans vehicle, players must not be covered by blocks
						//if(entity.isRiding() && ReflectionEngine.hasValue(Minecraft.getMinecraft().thePlayer.ridingEntity, Float.class, "radarRange", null) != null &&
						//		player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ))) {
							
							//only detect if visible on radar or the radar is on a ground vehicle
							//if(ReflectionEngine.hasValue(player.ridingEntity, Boolean.class, "radarVisbile", false) || !isPlaneRadar) {
								
								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								vec.xCoord *= 100D / range;
								vec.zCoord *= 100D / range;
								
								//only send valid data that fits on the radar screen
								if(Math.sqrt(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord) <= 100) {
									
									PacketDispatcher.wrapper.sendTo(new VRadarPacket((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord), (EntityPlayerMP) player);
								}
							//}
						//}
					}
				}
			//} else {
				
				//if the player does not have a radar up, he will only receive destructor packets that remove all blips and deny radar screens
			//	PacketDispatcher.wrapper.sendTo(new VRadarDestructorPacket(false), (EntityPlayerMP) player);
			//}
		}
	}

}
