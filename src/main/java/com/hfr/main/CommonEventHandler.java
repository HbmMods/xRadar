package com.hfr.main;

import java.util.List;

import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.VRadarDestructorPacket;
import com.hfr.packet.VRadarPacket;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class CommonEventHandler {

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if(!player.worldObj.isRemote && true /** is in vehicle with radar? */) { //TODO: grab dynamically
			
			/**
			 * Important: gui always assumes radar range to be 100 to avoid scale data from being sent
			 * -> scale down every blip to 100
			 * example: range is 2000, blip is at X:500, X to send is 25
			 * Y should not be scaled (no rendering on this part)
			 */
			
			double range = 50; //TODO: grab dynamically
			double altitude = 70;
			
			boolean sufficient = altitude <= player.posY;
			
			//directed traffic to avoid spammy broadcast
			PacketDispatcher.wrapper.sendTo(new VRadarDestructorPacket(sufficient), (EntityPlayerMP) player);
			
			if(sufficient) {
				List<Entity> entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - range, 0, player.posZ - range, player.posX + range, Double.POSITIVE_INFINITY, player.posZ + range));
				
				for(Entity entity : entities) {
					
					if(entity.width * entity.height < 0.5 || entity == player)
						continue;
					
					Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
					vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
					vec.xCoord *= 100D / range;
					vec.zCoord *= 100D / range;
					
					if(Math.sqrt(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord) <= 100) {
						
						PacketDispatcher.wrapper.sendTo(new VRadarPacket((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord), (EntityPlayerMP) player);
					}
				}
			}
		}
	}

}
