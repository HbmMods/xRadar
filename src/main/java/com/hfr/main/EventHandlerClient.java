package com.hfr.main;

import com.hfr.render.hud.RenderRadarScreen;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class EventHandlerClient {
	
	@SubscribeEvent
	public void drawHUD(RenderGameOverlayEvent event) {
		
		if(event.type == ElementType.CROSSHAIRS)
			RenderRadarScreen.renderRadar();
		
	}

}
