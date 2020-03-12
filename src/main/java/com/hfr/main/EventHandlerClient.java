package com.hfr.main;

import org.lwjgl.input.Keyboard;

import com.hfr.handler.SLBMHandler;
import com.hfr.items.ModItems;
import com.hfr.render.hud.RenderFlagOverlay;
import com.hfr.render.hud.RenderRadarScreen;
import com.hfr.render.util.RenderAccessoryUtility;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

public class EventHandlerClient {
	
	public static boolean zoom = false;
	public static boolean lastEnabled = false;
	public static int lastOffset = 0;
	public static int lastRange = 500;
	boolean lock = false;
	
	public static boolean fps = false;
	public static boolean tilt = false;
	public static boolean shader = false;
	
	public void register() {

		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
	public void drawHUD(RenderGameOverlayEvent event) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(event.type == ElementType.CROSSHAIRS)
		{
			//anti-ragex mechanism, do not delete - oops i deleted it
			
			
			/// START KEYBINDS ///
			if(/*!FMLClientHandler.instance().isGUIOpen(GuiChat.class)*/ Minecraft.getMinecraft().currentScreen == null) {
				
				//flans radar zoom
				if(Keyboard.isKeyDown(ClientProxy.toggleZoom.getKeyCode())) {
					
					if(!lock) {
						zoom = !zoom;
						Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.5F, 0.75F);
					}
					
					lock = true;
					
				//flans radar scale +
				} else if(Keyboard.isKeyDown(ClientProxy.incScale.getKeyCode()) && RenderRadarScreen.scale < 3) {
					
					if(!lock) {
						RenderRadarScreen.scale += 0.1F;
						Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 1.25F);
					}
					
					lock = true;
					
				//flans radar scale -
				} else if(Keyboard.isKeyDown(ClientProxy.decScale.getKeyCode()) && RenderRadarScreen.scale > 0.5) {
					
					if(!lock) {
						RenderRadarScreen.scale -= 0.1F;
						Minecraft.getMinecraft().thePlayer.playSound("hfr:item.toggle", 0.25F, 1.1F);
					}
					
					lock = true;
					
				//slbm launch screen
				} else if(Keyboard.isKeyDown(ClientProxy.slbm.getKeyCode())/* && Minecraft.getMinecraft().currentScreen != null*/) {
					
					if(!lock && SLBMHandler.flight > 0 && player.isRiding()) {
						player.openGui(MainRegistry.instance, ModItems.guiID_slbm, player.worldObj, 0, 0, 0);
					}
					
					lock = true;
					
				} else {
					
					lock = false;
				}
			}
			/// END KEYBINDS ///
			
			if(lastEnabled) {
				int offset = lastOffset;
				int range = 250;
				
				if(!zoom)
					range = lastRange;
				
				RenderRadarScreen.renderRadar(offset, range, zoom);
			}
			
			RenderFlagOverlay.drawFlag();
		}
		
		if(fps) {
			Minecraft.getMinecraft().gameSettings.limitFramerate = 5;
		}
		
		if(tilt) {
			Minecraft.getMinecraft().entityRenderer.debugViewDirection = 5;
		}
		
		if(shader && player.getRNG().nextInt(500) == 0) {
			Minecraft.getMinecraft().entityRenderer.activateNextShader();
		}
	}
	
	@SubscribeEvent
	public void preRenderEvent(RenderPlayerEvent.Pre event) {
		
		RenderPlayer renderer = event.renderer;
		AbstractClientPlayer player = (AbstractClientPlayer)event.entityPlayer;
		
		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);
		
		if(cloak != null)
			player.func_152121_a(Type.CAPE, cloak);
		
		if(player.getHeldItem() != null) {
			Item item = player.getHeldItem().getItem();
			
			if(item == ModItems.flaregun || item == ModItems.pakker) {
				renderer.modelBipedMain.aimedBow = true;
			}
		}
	}
	
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event) {
		
		if(event.phase == TickEvent.Phase.START)
			MainRegistry.smoothing = event.renderTickTime;
	}
}
