 package com.hfr.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;

import com.hfr.blocks.TileEntityDuct;
import com.hfr.effect.ParticleContrail;
import com.hfr.entity.*;
import com.hfr.items.ModItems;
import com.hfr.loader.HmfModelLoader;
import com.hfr.render.*;
import com.hfr.render.block.RenderControlRods;
import com.hfr.render.block.RenderRBMK;
import com.hfr.render.hud.RenderRadarScreen;
import com.hfr.render.hud.RenderRadarScreen.Blip;
import com.hfr.tileentity.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends ServerProxy
{
	public static KeyBinding toggleZoom = new KeyBinding("Toggle Radar Zoom", 33, "xRadar");
	public static KeyBinding incScale = new KeyBinding("Increase Radar Scale", 78, "xRadar");
	public static KeyBinding decScale = new KeyBinding("Decrease Radar Scale", 74, "xRadar");
	//public static KeyBinding toggleDebug = new KeyBinding("Decrease Radar Scale", 83, "xRadar");
	
	@Override
	public void registerRenderInfo()
	{
		new EventHandlerClient().register();
		
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		RenderingRegistry.addNewArmourRendererPrefix("5");

		RenderingRegistry.registerBlockHandler(new RenderRBMK());
		RenderingRegistry.registerBlockHandler(new RenderControlRods());

		ClientRegistry.registerKeyBinding(toggleZoom);
		ClientRegistry.registerKeyBinding(incScale);
		ClientRegistry.registerKeyBinding(decScale);
		//ClientRegistry.registerKeyBinding(toggleDebug);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceField.class, new RenderMachineForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunch());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDebug.class, new RenderDebug());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineDerrick.class, new RenderDerrick());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRefinery.class, new RenderRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRailgun.class, new RenderRailgun());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new RenderTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNaval.class, new RenderNaval());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDuct.class, new RenderDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineNet.class, new RenderNet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplay.class, new RenderDisplay());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineBuilder.class, new RenderBuilder());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileGeneric.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiary.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileAntiBallistic.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDecoy.class, new RenderMissileGeneric());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileStrong.class, new RenderMissileStrong());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiaryStrong.class, new RenderMissileStrong());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMPStrong.class, new RenderMissileStrong());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBurst.class, new RenderMissileHuge());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileInferno.class, new RenderMissileHuge());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileNuclear.class, new RenderMissileHuge());

		RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK3());
		RenderingRegistry.registerEntityRenderingHandler(EntityRailgunBlast.class, new RenderTom());
		RenderingRegistry.registerEntityRenderingHandler(EntityShell.class, new RenderTom());

		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGas.class, new RenderSnowball(ModItems.grenade_gas));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeNuclear.class, new RenderSnowball(ModItems.grenade_nuclear));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBoxcar.class, new RenderBoxcar());
	}
	
	@Override
	public boolean isPressed(int id) {
		
		switch(id) {
		case 0: return toggleZoom.isPressed();
		case 1: return incScale.isPressed();
		case 2: return decScale.isPressed();
		}
		
		return false;
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
		case 1:
			EntityReddustFX fx = new EntityReddustFX(world, posX, posY, posZ, 51F/256F, 64F/256F, 119F/256F);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			break;
		case 2:
			EntityReddustFX fx1 = new EntityReddustFX(world, posX, posY, posZ, 106F/256F, 41F/256F, 143F/256F);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx1);
			break;
		case 3:
			EntityReddustFX fx2 = new EntityReddustFX(world, posX, posY, posZ, 223F/256F, 55F/256F, 149F/256F);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx2);
			break;
		case 4:
			EntityLargeExplodeFX fx3 = new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), world, posX, posY, posZ, 0, 0, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx3);
			break;
			
		default: break;
		
		}
	}
	
	//sfx stands for special effects, not sound effects
	@Override
	public void spawnSFX(World world, double posX, double posY, double posZ, int type, Object payload) {

		switch(type) {
		
		case 0:
			int pow = 250;
			float angle = 25;
			float base = 0.5F;
			for(int i = 0; i < pow; i++) {

				float momentum = base * world.rand.nextFloat();
				float sway = (pow - i) / (float)pow;
				Vec3 vec = Vec3.createVectorHelper(((Vec3)payload).xCoord, ((Vec3)payload).yCoord, ((Vec3)payload).zCoord);
				vec.rotateAroundZ((float) (angle * world.rand.nextGaussian() * sway * Math.PI / 180D));
				vec.rotateAroundY((float) (angle * world.rand.nextGaussian() * sway * Math.PI / 180D));
				
				EntityFireworkSparkFX blast = new EntityFireworkSparkFX(world, posX, posY, posZ, vec.xCoord * momentum, vec.yCoord * momentum, vec.zCoord * momentum, Minecraft.getMinecraft().effectRenderer);
				
				if(world.rand.nextBoolean())
					blast.setColour(0x0088EA);
				else
					blast.setColour(0x52A8E6);
				
				Minecraft.getMinecraft().effectRenderer.addEffect(blast);
			}
			break;
			
		default: break;
		
		}
	}
	
	@Override
	public void addBlip(float x, float y, float z, int type) {
		
		RenderRadarScreen.blips.add(new Blip(x, y, z, type));
	}
	
	@Override
	public void clearBlips(boolean sufficient, boolean enabled, int offset, int range) {
		
		RenderRadarScreen.blips.clear();
		RenderRadarScreen.sufficient = sufficient;
		EventHandlerClient.lastEnabled = enabled;
		EventHandlerClient.lastOffset = offset;
		EventHandlerClient.lastRange = range;
	}
}

