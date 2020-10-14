package com.hfr.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.math.NumberUtils;

import com.hfr.main.ReflectionEngine;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class LoggingEngine {
	
	private static final List<String> log = new ArrayList();
	
	public static void log(String message) {
		
		//Date now = new Date();
		//String timestamp = String.format("[%s:%s:%s.%s]", Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);
		
		log.add(System.currentTimeMillis() + ":" + message);
	}
	
	private static final int interval = 10000; //10 seconds
	
	public static void flush() {
		
		long now = System.currentTimeMillis();
		
		for(String mesg : log) {
			
			String timestamp = mesg.split(":")[0];
			String tmp = "undefined";
			
			long longTime = Long.parseLong(timestamp);
			
			if(longTime + interval >= now) {
				Date date = new Date(longTime);
				String millis = (longTime % 1000) + "";
				
				while(millis.length() < 3)
					millis = "0" + millis;
				
				tmp = String.format("[%s:%s:%s.%s]", date.getHours(), date.getMinutes(), date.getSeconds(), millis);
				
				String message = mesg.replace(timestamp + ":", tmp + " ");
				System.out.println(message);
			}
		}
		
		log.clear();
	}
	
	public static void survey() {
		
		int i000 = 0;
		
		if(i000 == 0)
			return;
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(player == null) {
			log("Player is NULL. Skipping...");
			return;
		}
		
		World world = player.worldObj;
		
		if(world == null) {
			log("WorldOBJ is NULL. Skipping...");
			return;
		}
		
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		double lastX = player.lastTickPosX;
		double lastY = player.lastTickPosY;
		double lastZ = player.lastTickPosZ;
		double prevX = player.prevPosX;
		double prevY = player.prevPosY;
		double prevZ = player.prevPosZ;
		double servX = player.serverPosX;
		double servY = player.serverPosY;
		double servZ = player.serverPosZ;
		double moX = player.motionX;
		double moY = player.motionY;
		double moZ = player.motionZ;
		Entity ridden = player.ridingEntity;
		
		log("========== Starting profiler for entity tick " + player.getAge() + " ==========");
		
		log("[Player] Logging position for entity");
		log(String.format("[Player] X: %s / %s / %s / %s", x, lastX, prevX, servX));
		log(String.format("[Player] Y: %s / %s / %s / %s", y, lastY, prevY, servY));
		log(String.format("[Player] Z: %s / %s / %s / %s", z, lastZ, prevZ, servZ));
		
		log("[Player] Logging motion for entity");
		log(String.format("[Player] X: %s", moX));
		log(String.format("[Player] Y: %s", moY));
		log(String.format("[Player] Z: %s", moZ));
		
		log("[Ridden] Logging ridden entity data");
		if(ridden == null) {
			log("[Ridden] Ridden entity is NULL. Skipping!");
		} else {
			double ridden_x = ridden.posX;
			double ridden_y = ridden.posY;
			double ridden_z = ridden.posZ;
			double ridden_lastX = ridden.lastTickPosX;
			double ridden_lastY = ridden.lastTickPosY;
			double ridden_lastZ = ridden.lastTickPosZ;
			double ridden_prevX = ridden.prevPosX;
			double ridden_prevY = ridden.prevPosY;
			double ridden_prevZ = ridden.prevPosZ;
			double ridden_servX = ridden.serverPosX;
			double ridden_servY = ridden.serverPosY;
			double ridden_servZ = ridden.serverPosZ;
			double ridden_moX = ridden.motionX;
			double ridden_moY = ridden.motionY;
			double ridden_moZ = ridden.motionZ;
			String name = ridden.getCommandSenderName();
			String className = ridden.getClass().getSimpleName();
			int riddenID = ridden.getEntityId();
			
			log("[Ridden] Logging general data for ridden entity");
			log("[Ridden] Entity name: " + name);
			log("[Ridden] Instance class name: " + className);
			log("[Ridden] Ridden ID: " + riddenID);
			
			log("[Ridden] Logging position for ridden entity");
			log(String.format("[Ridden] X: %s / %s / %s / %s", ridden_x, ridden_lastX, ridden_prevX, ridden_servX));
			log(String.format("[Ridden] Y: %s / %s / %s / %s", ridden_y, ridden_lastY, ridden_prevY, ridden_servY));
			log(String.format("[Ridden] Z: %s / %s / %s / %s", ridden_z, ridden_lastZ, ridden_prevZ, ridden_servZ));
			
			log("[Ridden] Logging motion for ridden entity");
			log(String.format("[Ridden] X: %s", ridden_moX));
			log(String.format("[Ridden] Y: %s", ridden_moY));
			log(String.format("[Ridden] Z: %s", ridden_moZ));
		}
		
		List<Entity> proximity = world.getEntitiesWithinAABBExcludingEntity(player,
				AxisAlignedBB.getBoundingBox(player.posX - 20, player.posY - 20, player.posZ - 20, player.posX + 20, player.posY + 20, player.posZ + 20));
		
		for(Entity proxmox : proximity) {
			double proximity_x = proxmox.posX;
			double proximity_y = proxmox.posY;
			double proximity_z = proxmox.posZ;
			double proximity_moX = proxmox.motionX;
			double proximity_moY = proxmox.motionY;
			double proximity_moZ = proxmox.motionZ;
			String proximity_name = proxmox.getCommandSenderName();
			String proximity_className = proxmox.getClass().getSimpleName();
			int proximity_ID = proxmox.getEntityId();

			log("[Proximity] Logging entity within proximity");
			log(String.format("[Proximity] ID: %s, Name: %s, Class: %s", proximity_ID, proximity_name, proximity_className));
			log(String.format("[Proximity] Position: %s / %s / %s", proximity_x, proximity_y, proximity_z));
			log(String.format("[Proximity] Motion: %s / %s / %s", proximity_moX, proximity_moY, proximity_moZ));
		}
		
		log("========== Ending profiler for entity tick " + player.getAge() + " ==========");
		log("========== Starting profiler for packets tick " + player.getAge() + " ==========");
		
		NetworkManager netman = Minecraft.getMinecraft().getNetHandler().getNetworkManager();
		Queue inboundpackets = ReflectionEngine.hasValue(netman, Queue.class, "receivedPacketsQueue", null);
		
		if(inboundpackets == null)
			inboundpackets = ReflectionEngine.hasValue(netman, Queue.class, "field_150748_i", null);
		
		if(inboundpackets == null) {
			log("[Packets Inbound] CRITICAL ERROR: INBOUND MESSAGE QUEUE NOT REFLECTED.");
		} else {
			
			for(Object o : inboundpackets) {
				
				if(!(o instanceof FMLProxyPacket)) {
					log("[Packets Inbound] Detected packet" + o.getClass().getSimpleName());
					
					if(o instanceof S13PacketDestroyEntities) {
						S13PacketDestroyEntities satan = (S13PacketDestroyEntities)o;
						int[] hitlist = satan.func_149098_c();
						
						for(int i = 0; i < hitlist.length; i++) {
							
							int jfk = hitlist[i];
							Entity deadman = world.getEntityByID(jfk);
							String name = deadman == null ? "NULL" : deadman.getClass().getSimpleName();
							log("[Satan Packet] Found index " + jfk + " which belongs to a " + deadman);
							
							if(ridden != null && ridden.getEntityId() == jfk) {
								log("[Satan Packet] [[[ HIT DETECTED! ]]]");
							}
						}
					}
					if(o instanceof S1BPacketEntityAttach) {
						S1BPacketEntityAttach baphomet = (S1BPacketEntityAttach)o;
						int dat = baphomet.func_149404_c();
						int ent1 = baphomet.func_149403_d();
						int ent2 = baphomet.func_149402_e();

						Entity victim1 = world.getEntityByID(ent1);
						Entity victim2 = world.getEntityByID(ent2);

						String dead1 = victim1 == null ? "NULL" : victim1.getClass().getSimpleName();
						String dead2 = victim2 == null ? "NULL" : victim2.getClass().getSimpleName();
						
						log("[Baphomet Packet] Attempted to mount index " + ent1 + " belonging to " + dead1 + " to index " + ent2 + " belonging to " + dead2);
						
						if(ridden != null && (ridden.getEntityId() == ent1 || ridden.getEntityId() == ent2)) {
							log("[Baphomet Packet] [[[ HIT DETECTED! ]]]");
						}
					}
				}
			}
		}
		
		log("========== Ending profiler for packets tick " + player.getAge() + " ==========");
	}
}
