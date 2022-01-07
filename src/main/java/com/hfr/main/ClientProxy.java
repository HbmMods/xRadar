 package com.hfr.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.particle.EntityLavaFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.AdvancedModelLoader;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.hfr.effect.ParticleContrail;
import com.hfr.effect.ParticleMush;
import com.hfr.entity.*;
import com.hfr.entity.grenade.*;
import com.hfr.entity.logic.*;
import com.hfr.entity.missile.*;
import com.hfr.entity.projectile.*;
import com.hfr.handler.ExplosionSound;
import com.hfr.items.ModItems;
import com.hfr.loader.HmfModelLoader;
import com.hfr.render.block.*;
import com.hfr.render.entity.*;
import com.hfr.render.hud.*;
import com.hfr.render.hud.RenderRadarScreen.Blip;
import com.hfr.render.item.RenderFlaregun;
import com.hfr.render.item.RenderPak;
import com.hfr.render.living.*;
import com.hfr.render.mob.*;
import com.hfr.render.mob.ModelSheep.ModelSheepWooly;
import com.hfr.render.tileentity.*;
import com.hfr.rvi.RVICommon.Indicator;
import com.hfr.tileentity.*;
import com.hfr.tileentity.clowder.*;
import com.hfr.tileentity.machine.*;
import com.hfr.tileentity.prop.*;
import com.hfr.tileentity.weapon.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientProxy extends ServerProxy
{
	public static KeyBinding toggleZoom = new KeyBinding("Toggle Radar Zoom", 33, "xRadar");
	public static KeyBinding incScale = new KeyBinding("Increase Radar Scale", 78, "xRadar");
	public static KeyBinding decScale = new KeyBinding("Decrease Radar Scale", 74, "xRadar");
	public static KeyBinding hideCoord = new KeyBinding("Hides radar target coords", 144, "xRadar");	
	public static KeyBinding slbm = new KeyBinding("Access SLBM Menu", 0, "xRadar");
	public static KeyBinding filter = new KeyBinding("Toggle chat filter", 62, "xRadar");
	public static KeyBinding markers = new KeyBinding("Toggle resource markers", 0, "xRadar");
	public static KeyBinding flushLog = new KeyBinding("Flush extended debugging log", 0, "xRadar");
	
	@Override
	public void registerRenderInfo()
	{
		new EventHandlerClient().register();
		
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		RenderingRegistry.addNewArmourRendererPrefix("5");

		RenderingRegistry.registerBlockHandler(new RenderRBMK());
		RenderingRegistry.registerBlockHandler(new RenderControlRods());
		RenderingRegistry.registerBlockHandler(new RenderPalisade());
		RenderingRegistry.registerBlockHandler(new RenderWall());
		RenderingRegistry.registerBlockHandler(new RenderBerliner());
		RenderingRegistry.registerBlockHandler(new RenderBarbedWire());
		RenderingRegistry.registerBlockHandler(new RenderSandbags());
		RenderingRegistry.registerBlockHandler(new RenderRope());

		ClientRegistry.registerKeyBinding(toggleZoom);
		ClientRegistry.registerKeyBinding(incScale);
		ClientRegistry.registerKeyBinding(decScale);
		ClientRegistry.registerKeyBinding(hideCoord);
		ClientRegistry.registerKeyBinding(slbm);
		ClientRegistry.registerKeyBinding(filter);
		ClientRegistry.registerKeyBinding(markers);
		ClientRegistry.registerKeyBinding(flushLog);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceField.class, new RenderMachineForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlastDoor.class, new RenderBlastDoor());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineUni.class, new RenderUni());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineEMP.class, new RenderEMP());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlag.class, new RenderFlag());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCap.class, new RenderCap());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlagBig.class, new RenderFlagBig());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityProp.class, new RenderProp());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStatue.class, new RenderProp());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGrainmill.class, new RenderMill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineBlastFurnace.class, new RenderBlastFurnace());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCoalMine.class, new RenderCoalMine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoalGen.class, new RenderCoalGen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFactory.class, new RenderFactory());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineWindmill.class, new RenderWindmill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWaterWheel.class, new RenderWaterwheel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRift.class, new RenderRift());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineTurbine.class, new RenderTurbine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineTemple.class, new RenderTemple());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConquerer.class, new RenderConquerer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOfficerChest.class, new RenderChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFoundry.class, new RenderFoundry());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSawmill.class, new RenderSawmill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDieselGen.class, new RenderDieselGen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineEFurnace.class, new RenderEFurnace());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileAT.class, new RenderMissileGeneric());
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

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileMartin.class, new RenderMissileMartin());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissilePegasus.class, new RenderMissilePegasus());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileSpear.class, new RenderMissileSpear());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileShell.class, new RenderTom());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDevon1.class, new RenderMissileSpear());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDevon2.class, new RenderMissileSpear());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDevon3.class, new RenderMissileSpear());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileCruise1.class, new RenderMissileSpear());

		RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK3());
		RenderingRegistry.registerEntityRenderingHandler(EntityRailgunBlast.class, new RenderTom());
		RenderingRegistry.registerEntityRenderingHandler(EntityShell.class, new RenderTom());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlare.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityPak.class, new RenderPakRocket());

		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGas.class, new RenderSnowball(ModItems.grenade_gas));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeNuclear.class, new RenderSnowball(ModItems.grenade_nuclear));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeBoxcar.class, new RenderBoxcar());
		RenderingRegistry.registerEntityRenderingHandler(EntityHook.class, new RenderSnowball(ModItems.hook));

		RenderingRegistry.registerEntityRenderingHandler(EntityFarmer.class, new RenderFarmer());

		RenderingRegistry.registerEntityRenderingHandler(EntityCow.class, new RenderCow(new ModelBuffalo(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPig.class, new RenderPig(new ModelPig(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySheep.class, new RenderSheep(new ModelSheep(), new ModelSheepWooly(), 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.5F));

		MinecraftForgeClient.registerItemRenderer(ModItems.flaregun, new RenderFlaregun());
		MinecraftForgeClient.registerItemRenderer(ModItems.pakker, new RenderPak());
	}
	
	@Override
	public boolean isPressed(int id) {
		
		switch(id) {
		case 0: return toggleZoom.isPressed();
		case 1: return incScale.isPressed();
		case 2: return decScale.isPressed();
		case 3: return slbm.isPressed();
		case 4: return filter.isPressed();
		case 5: return markers.isPressed();
		case 6: return hideCoord.isPressed();
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
		case 5:
			EntityCloudFX fx4 = new EntityCloudFX(world, posX, posY, posZ, 0, 0, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx4);
			break;
		case 6:
			EntityReddustFX fx5 = new EntityReddustFX(world, posX, posY, posZ, 0, 0, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx5);
			break;
		case 7:
			ParticleMush mush = new ParticleMush(Minecraft.getMinecraft().getTextureManager(), world, posX, posY, posZ);
			Minecraft.getMinecraft().effectRenderer.addEffect(mush);
			break;
			
		default: break;
		
		}
	}
	
	//sfx stands for special effects, not sound effects
	@Override
	public void spawnSFX(World world, double posX, double posY, double posZ, int type, Object payload) {
		
		int part = Minecraft.getMinecraft().gameSettings.particleSetting;

		switch(type) {
		
		/// RAILGUN BLAST ///
		case SFX_RAILGUN:
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
			
		/// CLOWDER BORDER ///
		case SFX_BORDER:
			if(!(payload instanceof int[]))
				return;
			
			int[] pl = (int[]) payload;
			
			if(pl.length != 5)
				return;

			int x1 = pl[0];
			int z1 = pl[1];
			int x2 = pl[2];
			int z2 = pl[3];
			int color = pl[4];

		    /*float r = Math.max(((color & 0xFF0000) >> 16) / 256F, 0.01F);
		    float g = Math.max(((color & 0xFF00) >> 8) / 256F, 0.01F);
		    float b = Math.max((color & 0xFF) / 256F, 0.01F);*/
			
			/*int a = 5;
			
			if(part == 1)
				a = 2;
			
			if(part == 2)
				a = 1;*/
			
			int a = 1;
			
			for(int i = 0; i < a; i++) {
				double xs = x1 + (x2 - x1) * world.rand.nextDouble();
				double zs = z1 + (z2 - z1) * world.rand.nextDouble();
				double ys = world.getHeightValue((int)xs, (int)zs - 1) + 0.25;// + 1.5D + world.rand.nextGaussian();
				
				/*EntityReddustFX fx = new EntityReddustFX(world, xs, ys, zs, r, g, b);
				fx.motionY = 0.1;
				Minecraft.getMinecraft().effectRenderer.addEffect(fx);*/
				
				EntityFireworkSparkFX blast = new EntityFireworkSparkFX(world, xs, ys, zs, 0.0, 0.4 * world.rand.nextGaussian() + 0.6, 0.0, Minecraft.getMinecraft().effectRenderer);
				blast.setColour(color);
				Minecraft.getMinecraft().effectRenderer.addEffect(blast);
			}
			
			break;
			
			/// CUMMIES ///
			case 3:
				
				EntityBreakingFX cum = new EntityBreakingFX(world, posX, posY, posZ, Items.snowball);
				cum.motionX = ((double[])payload)[0];
				cum.motionY = ((double[])payload)[1];
				cum.motionZ = ((double[])payload)[2];
				Minecraft.getMinecraft().effectRenderer.addEffect(cum);
				break;
			
		default: break;
		
		}
	}
	
	public void spawnEFX(double posX, double posY, double posZ, float pow) {
		ExplosionSound.handleClient(Minecraft.getMinecraft().thePlayer, (int)posX, (int)posY, (int)posZ, pow);
	}
	
	@Override
	public void addBlip(float x, float y, float z, float posX, float posZ, int type) {
		
		RenderRadarScreen.blips.add(new Blip(x, y, z, posX, posZ, type));
	}
	
	@Override
	public void addRVIs(List<Indicator> indicators) {
		RenderRVIOverlay.indicators = indicators;
	}
	
	@Override
	public void clearBlips(boolean sufficient, boolean enabled, int offset, int range) {
		
		RenderRadarScreen.blips.clear();
		RenderRadarScreen.sufficient = sufficient;
		EventHandlerClient.lastEnabled = enabled;
		EventHandlerClient.lastOffset = offset;
		EventHandlerClient.lastRange = range;
	}

	@Override
	public void updateFlag(ResourceLocation flag, ResourceLocation overlay, int color, String name, String title) {
		RenderFlagOverlay.flag = flag;
		RenderFlagOverlay.overlay = overlay;
		RenderFlagOverlay.color = color;
		RenderFlagOverlay.title = name;
		RenderFlagOverlay.zone = title;
		RenderFlagOverlay.startingTime = System.currentTimeMillis();
	}
	
	@Override
	public void openURL(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) { }
	}

	public void effectNT(NBTTagCompound nbt) {
		
		String type = nbt.getString("type");
		World world = Minecraft.getMinecraft().theWorld;
		
		if(type.equals("clowderNotif")) {
			
			EventHandlerClient.lookup.put(nbt.getString("player"), nbt.getString("clowder"));
			return;
		}
		
		if(type.equals("resources")) {
			
			int count = nbt.getInteger("count");
			
			for(int i = 0; i < count; i++) {

				int minX = nbt.getInteger("minX" + i);
				int minZ = nbt.getInteger("minZ" + i);
				int maxX = nbt.getInteger("maxX" + i);
				int maxZ = nbt.getInteger("maxZ" + i);
				int color = nbt.getInteger("color" + i);
				
				EventHandlerClient.resourceBorders.add(new int[] {minX, minZ, maxX, maxZ, color});
			}
		} else if(type.equals("explosion")) {
			
			try {
				float strength = nbt.getFloat("strength");
				double x = nbt.getDouble("posX");
				double y = nbt.getDouble("posY") + 0.5;
				double z = nbt.getDouble("posZ");
				
				for(int i = 0; i < strength * 10; i++) {
					
					EntityFlameFX flames = new EntityFlameFX(world, x, y, z,
							world.rand.nextGaussian() * 0.1 * strength,
							world.rand.nextGaussian() * 0.1 * strength,
							world.rand.nextGaussian() * 0.1 * strength);
					
					ReflectionHelper.setPrivateValue(EntityFX.class, flames, 100, "particleMaxAge", "field_70547_e");
					
					Minecraft.getMinecraft().effectRenderer.addEffect(flames);
				}
				
				for(int i = 0; i < strength * 25; i++) {
					
					EntityLavaFX flames = new EntityLavaFX(world, x, y, z);
	
					ReflectionHelper.setPrivateValue(EntityFX.class, flames, 200, "particleMaxAge", "field_70547_e");
					ReflectionHelper.setPrivateValue(Entity.class, flames, world.rand.nextGaussian() * 0.1 * strength, "motionX", "field_70159_w");
					ReflectionHelper.setPrivateValue(Entity.class, flames, world.rand.nextDouble() * 0.25 * strength, "motionY", "field_70181_x");
					ReflectionHelper.setPrivateValue(Entity.class, flames, world.rand.nextGaussian() * 0.1 * strength, "motionZ", "field_70179_y");
					
					Minecraft.getMinecraft().effectRenderer.addEffect(flames);
				}
				
				for(int i = 0; i < strength * 50; i++) {
					
					EntityCloudFX flames = new EntityCloudFX(world, x, y, z,
							world.rand.nextGaussian() * 0.025 * strength,
							world.rand.nextGaussian() * 0.025 * strength,
							world.rand.nextGaussian() * 0.025 * strength);
	
					ReflectionHelper.setPrivateValue(EntityFX.class, flames, 100, "particleMaxAge", "field_70547_e");
					ReflectionHelper.setPrivateValue(EntityFX.class, flames, 10, "particleScale", "field_70544_f");
					ReflectionHelper.setPrivateValue(EntityCloudFX.class, flames, 10, "field_70569_a");
					
					Minecraft.getMinecraft().effectRenderer.addEffect(flames);
				}
			} catch(Exception ex) {}
		}
	}
}

