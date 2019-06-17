 package com.hfr.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

import com.hfr.effect.ParticleContrail;
import com.hfr.entity.*;
import com.hfr.loader.HmfModelLoader;
import com.hfr.render.*;
import com.hfr.render.hud.RenderRadarScreen;
import com.hfr.render.hud.RenderRadarScreen.Blip;
import com.hfr.tileentity.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends ServerProxy
{
	public static KeyBinding toggleZoom = new KeyBinding("Toggle Radar Zoom", 33, "xRadar");
	
	@Override
	public void registerRenderInfo()
	{
		new EventHandlerClient().register();
		
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		
		ClientRegistry.registerKeyBinding(toggleZoom);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceField.class, new RenderMachineForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunch());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileGeneric.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiary.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileAntiBallistic.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDecoy.class, new RenderMissileGeneric());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileStrong.class, new RenderMissileStrong());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiaryStrong.class, new RenderMissileStrong());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMPStrong.class, new RenderMissileStrong());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBurst.class, new RenderMissileHuge());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileInferno.class, new RenderMissileHuge());

		RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK3());
	}
	
	@Override
	public void registerTileEntitySpecialRenderer() { }
	
	//please fucking end my life i'm begging you
	//everything is pain
	//make it stop
	@Override
	public void howDoIUseTheZOMG(World world, double posX, double posY, double posZ, int type) {

		switch(type) {
		
		case 0:
			ParticleContrail contrail = new ParticleContrail(Minecraft.getMinecraft().getTextureManager(), world, posX, posY, posZ);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
			break;
			
		default: break;
		
		}
	}
	
	@Override
	public void addBlip(float x, float y, float z) {
		
		RenderRadarScreen.blips.add(new Blip(x, y, z));
	}
	
	@Override
	public void clearBlips(boolean sufficient) {
		
		RenderRadarScreen.blips.clear();
		RenderRadarScreen.sufficient = sufficient;
	}
}

