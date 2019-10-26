package com.hfr.clowder;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.ServerChatEvent;

public class ClowderEvents {
	
	@SubscribeEvent
	public void handleChatServer(ServerChatEvent event) {
		
		Clowder clowder = Clowder.getClowderFromPlayer(event.player);
		
		if(clowder != null) {
			
			event.setCanceled(true);
			String message = String.format("%s <%s> %s", clowder.name.replace('_', ' '), event.username, event.message);
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(message));
		}
	}

}
