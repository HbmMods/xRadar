package com.hfr.main;

import org.lwjgl.input.Keyboard;

import com.hfr.render.hud.RenderRadarScreen;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

public class EventHandlerClient {
	
	public static boolean zoom = false;
	public static boolean lastEnabled = false;
	public static int lastOffset = 0;
	public static int lastRange = 500;
	boolean lock = false;
	
	public void register() {

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
	public void drawHUD(RenderGameOverlayEvent event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(event.type == ElementType.CROSSHAIRS)
		{
			if(!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
				if(Keyboard.isKeyDown(ClientProxy.toggleZoom.getKeyCode())) {
					
					if(!lock) {
						zoom = !zoom;
						Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.5F, 0.75F);
					}
					
					lock = true;
					
				} else if(Keyboard.isKeyDown(ClientProxy.incScale.getKeyCode()) && RenderRadarScreen.scale < 3) {
					
					if(!lock) {
						RenderRadarScreen.scale += 0.1F;
						Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 1.25F);
					}
					
					lock = true;
					
				} else if(Keyboard.isKeyDown(ClientProxy.decScale.getKeyCode()) && RenderRadarScreen.scale > 0.5) {
					
					if(!lock) {
						RenderRadarScreen.scale -= 0.1F;
						Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 1.1F);
					}
					
					lock = true;
					
				} else {
					
					lock = false;
				}
			}
			
			if(lastEnabled) {
				int offset = lastOffset;
				int range = 250;
				
				if(!zoom)
					range = lastRange;
				
				RenderRadarScreen.renderRadar(offset, range, zoom);
			}
		}
	}
	
	/*@SubscribeEvent
	public void getKeyInput(InputEvent.KeyInputEvent event) {

		if(ClientProxy.toggleZoom.isPressed()) {
			zoom = !zoom;
			Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 1.0F, 0.75F);
		}
		if(ClientProxy.incScale.isPressed() && RenderRadarScreen.scale < 2) {
			RenderRadarScreen.scale += 0.1F;
			Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 1.0F, 1F);
		}
		if(ClientProxy.decScale.isPressed() && RenderRadarScreen.scale > 0.1) {
			RenderRadarScreen.scale -= 0.1F;
			Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 1.0F, 1F);
		}
	}*/
}
