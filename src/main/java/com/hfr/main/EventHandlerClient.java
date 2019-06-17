package com.hfr.main;

import com.hfr.render.hud.RenderRadarScreen;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

public class EventHandlerClient {
	
	public static boolean zoom = false;
	
	public void register() {

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
	public void drawHUD(RenderGameOverlayEvent event) {
		
		if(event.type == ElementType.CROSSHAIRS)// && ReflectionEngine.hasValue(Minecraft.getMinecraft().thePlayer.ridingEntity, Integer.class, "radarPositionOffset", null) != null)
		{
			int offset = 0/*(int)ReflectionEngine.hasValue(Minecraft.getMinecraft().thePlayer.ridingEntity, Integer.class, "radarPositionOffset", null)*/;
			
			RenderRadarScreen.renderRadar(offset, zoom);
		}
		
	}
	
	@SubscribeEvent
	public void getKeyInput(TickEvent.ClientTickEvent event) {

		if(ClientProxy.toggleZoom.isPressed()) {
			zoom = !zoom;
			Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 1.0F, 0.75F);
		}
	}
}
