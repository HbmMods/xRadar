package com.hfr.main;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.hfr.blocks.*;
import com.hfr.clowder.ClowderEvents;
import com.hfr.clowder.ClowderFlag;
import com.hfr.command.*;
import com.hfr.data.StockData;
import com.hfr.data.StockData.Stock;
import com.hfr.entity.*;
import com.hfr.entity.grenade.*;
import com.hfr.entity.logic.*;
import com.hfr.entity.missile.*;
import com.hfr.entity.projectile.*;
import com.hfr.handler.*;
import com.hfr.items.*;
import com.hfr.lib.*;
import com.hfr.packet.*;
import com.hfr.potion.HFRPotion;
import com.hfr.schematic.*;
import com.hfr.tileentity.*;
import com.hfr.tileentity.clowder.*;
import com.hfr.tileentity.machine.*;
import com.hfr.tileentity.prop.*;
import com.hfr.tileentity.weapon.*;
import com.hfr.util.*;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry
{
	@Instance(RefStrings.MODID)
	public static MainRegistry instance;
	
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;
	
	@Metadata
	public static ModMetadata meta;
	
	public static Logger logger;
	
	public static int radarRange = 1000;
	public static int radarBuffer = 30;
	public static int radarAltitude = 55;
	public static int radarConsumption = 50;

	public static int coalgenProduction = 200;

	public static int fPlaneAltitude = 40;
	public static int fTankAltitude = 30;
	public static int fOffset = 2;
	
	public static int fieldBase = 100;
	public static int fieldRange = 50;
	public static int fieldHealth = 25;
	public static int upRange = 16;
	public static int upHealth = 50;
	public static int fieldDet = 25;
	public static int baseCooldown = 100;
	public static int rangeCooldown = 100;

	public static double exSpeed = 1D;
	public static double exWeight = 2D;
	public static int mult = 100;
	public static double flanmult = 1D;
	public static boolean flancalc = true;

	public static int abDelay = 40;
	public static int abRange = 500;
	public static double abSpeed = 0.125D;
	public static int empRadius = 100;
	public static int empDuration = 5 * 60 * 20;
	public static int empParticle = 20;
	public static boolean empSpecial = true;
	public static int padBuffer = 100000000;
	public static int padUse = 50000000;
	public static int mHealth = 15;
	public static int mDespawn = 5000;
	public static int mSpawn = 6000;
	public static int derrickBuffer = 100000;
	public static int derrickUse = 1000;
	public static int derrickLimiter = 250;
	public static int derrickTimer = 50;
	public static int refineryBuffer = 100000;
	public static int refineryUse = 1000;
	public static int refOil = 50;
	public static int refHeavy = 20;
	public static int refNaph = 15;
	public static int refLight = 10;
	public static int refPetro = 5;

	public static int nukeRadius = 100;
	public static int nukeKill = 250;
	public static float nukeStrength = 5F;
	public static int nukeDist = 5;
	public static int nukeStep = 5;
	public static boolean nukeSimple = false;
	public static int nukeDamage = 100;

	public static int mushLife = 15 * 20;
	public static int mushScale = 80;
	public static int fireDuration = 4 * 20;
	public static int t1blast = 50;
	public static int t1Damage = 100;
	public static int t2blast = 100;
	public static int t2Damage = 100;
	public static int t3blast = 150;
	public static int t3Damage = 100;

	public static int superFishrate = 20;
	public static int goodFishrate = 40;
	public static int averageFishrate = 60;
	public static int crapFishrate = 1000000;
	public static int jamRate = 15 * 60;
	public static int whaleChance = 5;

	public static int uniRate = 60 * 3;
	public static int uniJamRate = 60 * 15;

	public static int coalRate = 60;
	public static int coalJamRate = 60 * 30;
	
	public static int navalDamage = 100;
	public static int railgunDamage = 100;
	public static int railgunBuffer = 500000000;
	public static int railgunUse = 250000000;
	
	public static int mlpf = 100;

	public static int caveCap = -10;

	public static int crafting = 0;
	
	public static boolean enableStocks = true;

	public static int warpCost = 25;
	public static int territoryDelay = 5;
	public static int territoryAmount = 50;
	public static int prestigeDelay = 60 * 60 * 20;
	public static boolean disableChests = true;
	public static int mold = 5 * 60 * 60 * 20;

	public static boolean bb_rng = false;
	
	public static boolean freeRadar = false;
	public static boolean sound = true;
	public static boolean comparator = false;
	
	public static int empID = 66;
	
	Random rand = new Random();

	public static DamageSource blast = (new DamageSource("blast")).setExplosion().setDamageBypassesArmor().setDamageIsAbsolute();
	public static DamageSource zyklon = (new DamageSource("zyklon")).setDamageBypassesArmor().setDamageIsAbsolute();
	public static DamageSource wire = (new DamageSource("wire"));
	
	public static CreativeTabs tab = new CreativeTabHFR(CreativeTabs.getNextID(), "tabHFR");
	
	public static float smoothing = 0.0F;
	
	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent)
	{
		if(logger == null)
			logger = PreEvent.getModLog();
		
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		loadConfig(PreEvent);
		CraftingManager.mainRegistry();
		proxy.registerRenderInfo();
		FluidHandler.init();
		HFRPotion.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		
		GameRegistry.registerTileEntity(TileEntityMachineSiren.class, "tileentity_hfr_siren");
		GameRegistry.registerTileEntity(TileEntityMachineRadar.class, "tileentity_hfr_radar");
		GameRegistry.registerTileEntity(TileEntityForceField.class, "tileentity_hfr_field");
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, "tileentity_hfr_vault");
		GameRegistry.registerTileEntity(TileEntityDummy.class, "tileentity_hfr_dummy");
		GameRegistry.registerTileEntity(TileEntityHatch.class, "tileentity_hfr_hatch");
		GameRegistry.registerTileEntity(TileEntityLaunchPad.class, "tileentity_hfr_launchpad");
		GameRegistry.registerTileEntity(TileEntityChlorineSeal.class, "tileentity_hfr_gaschamber");
		GameRegistry.registerTileEntity(TileEntityMachineDerrick.class, "tileentity_hfr_derrick");
		GameRegistry.registerTileEntity(TileEntityDebug.class, "tileentity_hfr_devon_truck");
		GameRegistry.registerTileEntity(TileEntityMachineRefinery.class, "tileentity_hfr_refinery");
		GameRegistry.registerTileEntity(TileEntityRailgun.class, "tileentity_hfr_railgun");
		GameRegistry.registerTileEntity(TileEntityTank.class, "tileentity_hfr_barrel");
		GameRegistry.registerTileEntity(TileEntityNaval.class, "tileentity_hfr_naval");
		GameRegistry.registerTileEntity(TileEntityDuct.class, "tileentity_hfr_duct");
		GameRegistry.registerTileEntity(TileEntityHydro.class, "tileentity_hfr_hydro");
		GameRegistry.registerTileEntity(TileEntityMachineNet.class, "tileentity_hfr_net");
		GameRegistry.registerTileEntity(TileEntityMachineMarket.class, "tileentity_hfr_stonks");
		GameRegistry.registerTileEntity(TileEntityDisplay.class, "tileentity_hfr_display");
		GameRegistry.registerTileEntity(TileEntityMachineBuilder.class, "tileentity_hfr_builder");
		GameRegistry.registerTileEntity(TileEntityMachineUni.class, "tileentity_hfr_university");
		GameRegistry.registerTileEntity(TileEntityRBMKElement.class, "tileentity_hfr_rbmk_fuel");
		GameRegistry.registerTileEntity(TileEntityMachineEMP.class, "tileentity_hfr_emp");
		GameRegistry.registerTileEntity(TileEntityFlag.class, "tileentity_hfr_flag");
		GameRegistry.registerTileEntity(TileEntityCap.class, "tileentity_hfr_cap");
		GameRegistry.registerTileEntity(TileEntityFlagBig.class, "tileentity_hfr_flag_big");
		GameRegistry.registerTileEntity(TileEntityProp.class, "tileentity_hfr_prop");
		GameRegistry.registerTileEntity(TileEntityStatue.class, "tileentity_hfr_statue");
		GameRegistry.registerTileEntity(TileEntityMachineGrainmill.class, "tileentity_hfr_mill");
		GameRegistry.registerTileEntity(TileEntityMachineBlastFurnace.class, "tileentity_hfr_furnace");
		GameRegistry.registerTileEntity(TileEntityBerlin.class, "tileentity_hfr_berlin");
		GameRegistry.registerTileEntity(TileEntityBox.class, "tileentity_hfr_smelly_box");
		GameRegistry.registerTileEntity(TileEntityMachineCoalMine.class, "tileentity_hfr_coalmine");
		GameRegistry.registerTileEntity(TileEntityCoalGen.class, "tileentity_hfr_coalgenerator");

		int id = 0;
	    EntityRegistry.registerModEntity(EntityMissileGeneric.class, "entity_missile_v2", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileIncendiary.class, "entity_missile_v2F", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileStrong.class, "entity_missile_large", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileIncendiaryStrong.class, "entity_missile_largeF", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileBurst.class, "entity_missile_korea", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileInferno.class, "entity_missile_koreaF", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileAntiBallistic.class, "entity_missile_anti", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileEMPStrong.class, "entity_missile_emp", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityNukeCloudSmall.class, "entity_mushroom_cloud", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileDecoy.class, "entity_missile_decoy", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileNuclear.class, "entity_missile_nuclear", id++, this, 1000, 1, true);

	    EntityRegistry.registerModEntity(EntityEMP.class, "entity_lingering_emp", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityBlast.class, "entity_deathblast", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityRailgunBlast.class, "entity_railgun_pellet", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityShell.class, "entity_naval_pellet", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityFlare.class, "entity_flaregun_pellet", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityPak.class, "entity_pak_rocket", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeGas.class, "entity_hfr_gas_grenade", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeNuclear.class, "entity_hfr_nuke_grenade", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeBoxcar.class, "entity_hfr_grb_grenade", id++, this, 1000, 1, true);

	    EntityRegistry.registerModEntity(EntityFarmer.class, "entity_hfr_slave", id++, this, 1000, 1, true);

	    EntityRegistry.registerModEntity(EntityMissileMartin.class, "entity_missile_martin", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissilePegasus.class, "entity_missile_pegasus", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileSpear.class, "entity_missile_spear", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileShell.class, "entity_missile_shell", id++, this, 1000, 1, true);

	    EntityRegistry.registerModEntity(EntityMissileDevon1.class, "entity_missile_devon1", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileDevon2.class, "entity_missile_devon2", id++, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityMissileDevon3.class, "entity_missile_devon3", id++, this, 1000, 1, true);
	
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {
			
	        @Override
	        public void ticketsLoaded(List<Ticket> tickets, World world) {
	            for(Ticket ticket : tickets) {
	            	
	                if(ticket.getEntity() instanceof IChunkLoader) {
	                    ((IChunkLoader)ticket.getEntity()).init(ticket);
	                }
	            }
	        }
	    });

		CommonEventHandler handler = new CommonEventHandler();
		ClowderEvents clowder = new ClowderEvents();
		//WorldController pon4 = new WorldController();
		
		FMLCommonHandler.instance().bus().register(handler);
		FMLCommonHandler.instance().bus().register(clowder);
		//FMLCommonHandler.instance().bus().register(pon4);
		MinecraftForge.EVENT_BUS.register(handler);
		MinecraftForge.EVENT_BUS.register(clowder);
		//MinecraftForge.EVENT_BUS.register(pon4);
	}

	@EventHandler
	public static void load(FMLInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent event)
	{
		//in postload, long after all blocks have been registered, the buffered config is being evaluated and processed.
		processBuffer();
		
		try {
			BobbyBreaker.loadConfiguration(jsonDir);
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void ServerLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandXPlayer());
		event.registerServerCommand(new CommandXDebug());
		event.registerServerCommand(new CommandXMarket());
		event.registerServerCommand(new CommandClowder());
		event.registerServerCommand(new CommandClowderChat());
		event.registerServerCommand(new CommandClowderAdmin());
	}

	public static List<Block> blastShields = new ArrayList();
	public static List<GriefEntry> zombWhitelist = new ArrayList();
	public static List<ControlEntry> controlList = new ArrayList();
	public static List<PotionEntry> potionList = new ArrayList();
	public static List<ImmunityEntry> immunityList = new ArrayList();
	public static String[] twilightBuffer;
	public static List<Integer> t2Buffer = new ArrayList();
	public static boolean skeletonAIDS = false;
	public static float skeletonHIV = 2.5F;
	public static boolean zombAI = true;
	public static boolean creepAI = true;

	public static List<String> u2 = new ArrayList();
	public static List<String> u1 = new ArrayList();
	public static List<String> d1 = new ArrayList();
	public static List<String> d2 = new ArrayList();
	public static boolean u2en = true;
	public static boolean u1en = true;
	public static boolean d1en = true;
	public static boolean d2en = true;
	public static int updateInterval = 10 * 60;
	public static int stockCap = 50;
	
	public static String jsonDir;
	
	public void loadConfig(FMLPreInitializationEvent event)
	{
		if(logger == null)
			logger = event.getModLog();
		
		PacketDispatcher.registerPackets();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		jsonDir = config.getConfigFile().getAbsolutePath().replace("cfg", "json");
		
		config.load();Property propRadarRange = config.get("RADAR", "radarRange", 1000);
        propRadarRange.comment = "Range of the radar, 50 will result in 100x100 block area covered";
        radarRange = propRadarRange.getInt();
        
        Property propRadarBuffer = config.get("RADAR", "radarBuffer", 30);
        propRadarBuffer.comment = "How high entities have to be above the radar to be detected";
        radarBuffer = propRadarBuffer.getInt();
        
        Property propRadarAltitude = config.get("RADAR", "radarAltitude", 55);
        propRadarAltitude.comment = "Y height required for the radar to work";
        radarAltitude = propRadarAltitude.getInt();
        
        Property propRadarConsumption = config.get("RADAR", "radarConsumption", 50);
        propRadarConsumption.comment = "Amount of RF per tick required for the radar to work";
        radarConsumption = propRadarConsumption.getInt();
        
        Property pFPlaneAltitude = config.get("RADAR", "FxR_planeAltitude", 40);
        pFPlaneAltitude.comment = "Minimum altitude for flans planes' radars to work";
        fPlaneAltitude = pFPlaneAltitude.getInt();
        
        Property pFTankAltitude = config.get("RADAR", "FxR_tankAltitude", 30);
        pFTankAltitude.comment = "Minimum altitude for flans non-planes' radars to work";
        fTankAltitude = pFTankAltitude.getInt();
        
        Property pFOffset = config.get("RADAR", "FxR_radarYOffset", 2);
        pFOffset.comment = "Y-axis offset from where the \"is below roof\" measurement is taken (to avoid ship radars from breaking)";
        fOffset = pFOffset.getInt();
        
        Property propCrafting = config.get(Configuration.CATEGORY_GENERAL, "craftingDifficulty", 0);
        propCrafting.comment = "How difficult the crafting recipes are, from 0 - 2 (very easy to hard), values outside this range make most stuff uncraftable";
        crafting = propCrafting.getInt();
        
        Property propFree = config.get(Configuration.CATEGORY_GENERAL, "freeRadar", false).setDefaultValue(false);
        propFree.comment = "Whether or not the radar and shield are free to use, i.e. do not require RF";
        freeRadar = propFree.getBoolean();
        
        Property propSound = config.get("RADAR", "radarPing", true).setDefaultValue(true);
        propSound.comment = "Whether or not the radar makes frequent pinging sounds";
        sound = propSound.getBoolean();
        
        Property propComp = config.get("RADAR", "comparatorOutput", false).setDefaultValue(false);
        propComp.comment = "Whether or not the radar uses a comparator to output it's signal, will directly output otherwise";
        comparator = propComp.getBoolean();
        
        Property propFB = config.get("FORCEFIELD", "fieldBaseConsumption", 100);
        propFB.comment = "Amount of RF per tick required for the forcefield to work";
        fieldBase = propFB.getInt();
        
        Property propFR = config.get("FORCEFIELD", "fieldRangeConsumption", 50);
        propFR.comment = "Amount of RF per tick per forcefield range upgrade";
        fieldRange = propFR.getInt();
        
        Property propFH = config.get("FORCEFIELD", "fieldHealthConsumption", 25);
        propFH.comment = "Amount of RF per tick per forcefield shield upgrade";
        fieldHealth = propFH.getInt();
        
        Property propER = config.get("FORCEFIELD", "fieldRangeUpgradeEffect", 16);
        propER.comment = "The radius increase per forcefield range upgrade";
        upRange = propER.getInt();
        
        Property propEH = config.get("FORCEFIELD", "fieldHealthUpgradeEffect", 50);
        propEH.comment = "The HP increase per forcefield shield upgrade";
        upHealth = propEH.getInt();
        
        Property propMS = config.get("FORCEFIELD", "fieldSpeedImpactExponent", 100);
        propMS.comment = "The exponent of the projectile's speed in the damage equation (100 -> ^1)";
        exSpeed = propMS.getInt() * 0.01;
        
        Property propMM = config.get("FORCEFIELD", "fieldMassImpactExponent", 200);
        propMM.comment = "The exponent of the projectile's mass (hitbox size) in the damage equation (200 -> ^2)";
        exWeight = propMM.getInt() * 0.01;
        
        Property propM = config.get("FORCEFIELD", "fieldImpactMultiplier", 100);
        propM.comment = "The general multiplier of the damage equation (hitbox size ^ massExp * entity speed ^ speedExp * mult)";
        mult = propM.getInt();
        
        Property propFLAN = config.get("FORCEFIELD", "fieldImpactFlanMultiplier", 100);
        propFLAN.comment = "The damage multiplier of flan's mod projectiles. 100 is the normal damage it would do to a player, 200 is double damage, etc.";
        flanmult = propFLAN.getInt() * 0.01;
        
        Property propDet = config.get("FORCEFIELD", "fieldEntityDetectionRange", 25);
        propDet.comment = "Padding of the entity detection range (effective range is this + shield radius), may requires to be increased to detect VERY fast projectiles";
        fieldDet = propDet.getInt();
        
        Property propAF = config.get("FORCEFIELD", "useFlanSpecialCase", true).setDefaultValue(true);
        propAF.comment = "Whether or not the forcefield should use a special function to pull the damage value out of flan's mod projectiles. Utilizes the worst code and the shittiest programming techniques in the universe, but flan's bullets may not behave as expected if this option is turned off";
        flancalc = propAF.getBoolean();
        
        Property propBC = config.get("FORCEFIELD", "fieldBaseCooldown", 300);
        propBC.comment = "Duration of the base cooldown in ticks after the forcefield has been broken";
        baseCooldown = propBC.getInt();
        
        Property propRC = config.get("FORCEFIELD", "fieldRangeCooldown", 3);
        propRC.comment = "Duration of the additional cooldown in ticks per block of radius. Standard radius is 16, the additional cooldown duraion is therefore 48 ticks, or 348 in total. Values below 5 are recommended.";
        rangeCooldown = propRC.getInt();
        
        Property propABDelay = config.get("MISSILE", "antiBallisticDelay", 40);
        propABDelay.comment = "Targeting delay of the AB missile in ticks. The AB will fly straight up ignoring missiles until this much time has passed.";
        abDelay = propABDelay.getInt();
        
        Property propABRadius = config.get("MISSILE", "antiBallisticRange", 500);
        propABRadius.comment = "The detection range of the AB missile.";
        abRange = propABRadius.getInt();
        
        Property propABSpeed = config.get("MISSILE", "antiBallisticSpeed", 0.125D);
        propABSpeed.comment = "The speed of an AB after a target has been found";
        abSpeed = propABSpeed.getDouble();
        
        Property propEMPDura = config.get("MISSILE", "empDuration", 5*60*20);
        propEMPDura.comment = "How long machines will stay disabled after EMP strike";
        empDuration = propEMPDura.getInt();
        
        Property propEMPRange = config.get("MISSILE", "empRange", 100);
        propEMPRange.comment = "The radius of the EMP effect";
        empRadius = propEMPRange.getInt();
        
        Property propEMPPart = config.get("MISSILE", "empParticleDelay", 20);
        propEMPPart.comment = "The average delay between spark particles of disabled machines. Should be above 10. 0 will crash the game, so don't do that.";
        empParticle = propEMPPart.getInt();
        
        Property empSpecialP = config.get("MISSILE", "empHFSpecialFunction", true);
        empSpecialP.comment = "Whether or not the EMP should use a special function to properly set all machine's RF to 0";
        empSpecial = empSpecialP.getBoolean();
        
        Property padBuf = config.get("MISSILE", "launchPadStorage", 100*1000*1000);
        padBuf.comment = "The amount of RF the launch pad can hold.";
        padBuffer = padBuf.getInt();
        
        Property padUseP = config.get("MISSILE", "launchPadRequirement", 50*1000*1000);
        padUseP.comment = "How much RF is required for a rocket launch. Has to be smaller or equal to the buffer size.";
        padUse = padUseP.getInt();
        
        Property mushLifeP = config.get("MISSILE", "fireballLife", 15 * 20);
        mushLifeP.comment = "How many ticks the mushroom cloud will persist";
        mushLife = mushLifeP.getInt();
        
        Property mushScaleP = config.get("MISSILE", "fireballScale", 80);
        mushScaleP.comment = "Scale of the mushroom cloud";
        mushScale = mushScaleP.getInt();
        
        Property fireDurationP = config.get("MISSILE", "fireDuration", 4 * 20);
        fireDurationP.comment = "How long the fire blast will last";
        fireDuration = fireDurationP.getInt();
        
        Property t1blastP = config.get("MISSILE", "tier1Blast", 50);
        t1blastP.comment = "Blast radius(c) of tier 1 missiles";
        t1blast = t1blastP.getInt();
        
        Property t2blastP = config.get("MISSILE", "tier2Blast", 100);
        t2blastP.comment = "Blast radius(c) of tier 2 missiles";
        t2blast = t2blastP.getInt();
        
        Property t3blastP = config.get("MISSILE", "tier3Blast", 150);
        t3blastP.comment = "Blast radius(c) of tier 3 missiles";
        t3blast = t3blastP.getInt();

        t1Damage = createConfigInt(config, "MISSILE", "tier1Damage", "How much damage a tier 1 death blast does per tick", 100);
        t2Damage = createConfigInt(config, "MISSILE", "tier2Damage", "How much damage a tier 2 death blast does per tick", 100);
        t3Damage = createConfigInt(config, "MISSILE", "tier3Damage", "How much damage a tier 3 death blast does per tick", 1000);
        
        Property mHealthP = config.get("MISSILE", "missileHealth", 15);
        mHealthP.comment = "How much beating a missile can take before it goes to commit unlive.";
        mHealth = mHealthP.getInt();
        
        Property mDespawnP = config.get("MISSILE", "simpleMissileDespawn", 5000);
        mDespawnP.comment = "Altitude at which cheapo missiles despawn and teleport to the target";
        mDespawn = mDespawnP.getInt();
        
        Property mSpawnP = config.get("MISSILE", "simpleMissileSpawn", 6000);
        mSpawnP.comment = "Altitude at which cheapo missiles spawn in when teleporting";
        mSpawn = mSpawnP.getInt();
        
        Property drywall = config.get("MISSILE", "blastShields", new String[] {
        		"" + Block.getIdFromBlock(Blocks.obsidian),
        		"" + Block.getIdFromBlock(ModBlocks.concrete),
        		"" + Block.getIdFromBlock(ModBlocks.concrete_bricks),
        		"" + Block.getIdFromBlock(ModBlocks.vault_door),
        		"" + Block.getIdFromBlock(ModBlocks.vault_door_dummy)});
        drywall.comment = "What blocks can block fire blasts (default: obsidian, concrete, concrete bricks, vault door, vault door dummy)";
        String[] vals = drywall.getStringList();
        
        for(String val : vals) {
        	
        	int i = Integer.parseInt(val);
        	
        	if(i != 0)
        		t2Buffer.add(i);
        }
        
        Property nukeRadiusP = config.get("NUKE", "nukeRadius", 100);
        nukeRadiusP.comment = "Maximum radius of a nuclear explosion";
        nukeRadius = nukeRadiusP.getInt();
        
        Property nukeKillP = config.get("NUKE", "nukeKillRadius", 250);
        nukeKillP.comment = "Radius of a nuke's death blast effect";
        nukeKill = nukeKillP.getInt();
        
        Property nukeStrengthP = config.get("NUKE", "nukeStrength", 5F);
        nukeStrengthP.comment = "Maximum radius of a nuclear explosion";
        nukeStrength = (float)nukeStrengthP.getDouble();
        
        Property nukeDistP = config.get("NUKE", "nukeSpacing", 5);
        nukeDistP.comment = "How many blocks between explosions per destruction ring";
        nukeDist = nukeDistP.getInt();
        
        Property nukeStepP = config.get("NUKE", "nukeStep", 5);
        nukeStepP.comment = "How many blocks between destruction rings";
        nukeStep = nukeStepP.getInt();
        
        Property nukeSimpleP = config.get("NUKE", "nukeSimple", false);
        nukeSimpleP.comment = "Simple mode causes the explosion to be totally flat, saving on CPU power";
        nukeSimple = nukeSimpleP.getBoolean();
        
        nukeDamage = createConfigInt(config, "NUKE", "nukeDamage", "How much damage a nuclear death blast does per tick", 1000);
        
        Property dBufferP = config.get("DERRICK", "derrickBuffer", 100000);
        dBufferP.comment = "How much energy the derrick can store";
        derrickBuffer = dBufferP.getInt();
        
        Property dUseP = config.get("DERRICK", "derrickConsumption", 1000);
        dUseP.comment = "How much energy the derrick uses per tick";
        derrickUse = dUseP.getInt();
        
        Property dLimP = config.get("DERRICK", "derrickLimiter", 250);
        dLimP.comment = "How many steps a derrick can take (large numbers can crash the game)";
        derrickLimiter = dLimP.getInt();
        
        derrickTimer = createConfigInt(config, "DERRICK", "derrickPumpDelay", "How many ticks inbetween each derrick operation (pump and drill)", 50);
        
        Property rBufferP = config.get("REFINERY", "refineryBuffer", 100000);
        rBufferP.comment = "How much energy the refinery can store";
        refineryBuffer = rBufferP.getInt();
        
        Property rUseP = config.get("REFINERY", "refineryConsumption", 1000);
        rUseP.comment = "How much energy the refinery uses per tick";
        refineryUse = rUseP.getInt();

        refOil = createConfigInt(config, "REFINERY", "refineryOilConsumption", "How much crude oil the refinery consumes per tick", 50);
        refHeavy = createConfigInt(config, "REFINERY", "refineryOil_Heavy", "How much bunker fuel the refinery creates per tick", 20);
        refNaph = createConfigInt(config, "REFINERY", "refineryOil_Diesel", "How much diesel the refinery creates per tick", 15);
        refLight = createConfigInt(config, "REFINERY", "refineryOil_Kerosene", "How much kerosene the refinery creates per tick", 10);
        refPetro = createConfigInt(config, "REFINERY", "refineryOil_Petroleum", "How much petroleum the refinery creates per tick", 5);

        navalDamage = createConfigInt(config, "RAILGUN", "navalDamage", "How much damage a naval cannon death blast does per tick", 100);
        railgunDamage = createConfigInt(config, "RAILGUN", "railgunDamage", "How much damage a railgun death blast does per tick", 1000);
        railgunBuffer = createConfigInt(config, "RAILGUN", "railgunBuffer", "How much RF the railgun can store", 500000000);
        railgunUse = createConfigInt(config, "RAILGUN", "railgunConsumption", "How much RF the railgun requires per shot", 250000000);

        superFishrate = createConfigInt(config, "FISHING", "superFishrate", "Average amount of seconds for fish in oceans", 20);
        goodFishrate = createConfigInt(config, "FISHING", "goodFishrate", "Average amount of seconds for fish in rivers", 40);
        averageFishrate = createConfigInt(config, "FISHING", "averageFishrate", "Average amount of seconds for fish in most biomes", 60);
        crapFishrate = createConfigInt(config, "FISHING", "crapFishrate", "Average amount of seconds for fish in hills, deserts and savannas", 1000000);
        jamRate = createConfigInt(config, "FISHING", "jamRate", "Average amount of seconds for net to get jammed", 15 * 60);
        whaleChance = createConfigInt(config, "FISHING", "whaleChance", "Chance in percent of chatching whale meat", 5);

        uniRate = createConfigInt(config, "UNIVERSITY", "uniRate", "Average amount of seconds for uni to generate research", 60 * 3);
        uniJamRate = createConfigInt(config, "UNIVERSITY", "uniJamRate", "Average amount of seconds for uni to get jammed", 60 * 15);

        coalRate = createConfigInt(config, "COALMINE", "coalRate", "Average amount of seconds for mine to generate coal", 60);
        coalJamRate = createConfigInt(config, "COALMINE", "accidentRate", "Average amount of seconds for mine to have an accident", 60 * 30);
        
        coalgenProduction = createConfigInt(config, "COALGEN", "coalgenProduction", "How much RF the coal generator produces per tick", 200);
        
        Property pAids = config.get("SKELETON", "explosiveArrows", false).setDefaultValue(false);
        pAids.comment = "Whether or not skeleton arrows should be explosive";
        skeletonAIDS = pAids.getBoolean();
        
        Property pHiv = config.get("SKELETON", "arrowStrength", 1.5).setDefaultValue(1.5);
        pHiv.comment = "How powerful exploding arrows are";
        skeletonHIV = (float)pHiv.getDouble();
        
        /////////////////////////////////////////////////////////////////////////
        Property zombgrief = config.get("ZOMBIE", "griefableBlocks", new String[] { "dirt:1", "grass:1", "planks:2", "cobblestone:3" });
        zombgrief.comment = "What blocks can be griefed by zomberts (syntax: [shortname]:[HP amount])";
        //rather than being processed instantly (and by doing so, missing out half the blocks), the config data is being loaded into a string-based buffer
        twilightBuffer = zombgrief.getStringList();
        
        /*for(String val : zg) {
        	
        	try {
        		
        		String name = val.split(":")[0];
        		//gets block from string with no modifiers (intended for vanilla)
        		Block b = Block.getBlockFromName(name);
        		
        		//in case the block uses the integer id notation
        		if(b == null) {
        			try {
        				b = Block.getBlockById(Integer.parseInt(name));
        			} catch(Exception ex) { }
        		}
        		
        		//if there is no block found, it'll retry with the proper domain format (modid:name)
        		//first _ is replaced with : (intended for modded blocks with the inclusion of the modid)
        		if(b == null)
        			//b = Block.getBlockFromName(name.replaceFirst("_", ":"));
        			b = RegistryUtil.getBlockByNameNoCaseOrPoint(name);
        		
        		//if there is still no block found, it'll retry with the tile. prefix
        		//in addition to the replacement of the _, it'll search for 'tile' and add a period to create 'tile.' (intended for HFR blocks)
        		if(b == null) {
        			String nname = name.replaceFirst("tile", "tile.");
        			//b = Block.getBlockFromName(nname.replaceFirst("_", ":"));
        			b = RegistryUtil.getBlockByNameNoCaseOrPoint(nname);
        		}
        		
	        	int hp = Integer.valueOf(val.split(":")[1]);
	        	
	        	if(b != null)
	        		zombWhitelist.add(new GriefEntry(b, hp));
	        	else
	        		logger.error("Invalid block entry '" + val.split(":")[0] + "'");
        	
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }*/
        /////////////////////////////////////////////////////////////////////////
        Property entcontrol = config.get("ENTITYCONTROL", "entityRestrictions", new String[] { "" });
        entcontrol.comment = "What entities should be regulated (syntax: [entity name]:[new spawn chance])";
        String[] ec = entcontrol.getStringList();
        
        for(String val : ec) {
        	
        	try {
        		
	        	String s = val.split(":")[0];
	        	int chance = Integer.valueOf(val.split(":")[1]);
	        	
	        	controlList.add(new ControlEntry(s, chance));
        	
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }
        /////////////////////////////////////////////////////////////////////////
        Property entfx = config.get("ENTITYCONTROL", "entityEffects", new String[] { "" });
        entfx.comment = "What entities should receive effects (syntax: [entity name]:[potion id]:[level, 0=I, 1=II, 2=III, etc.]:[duration])";
        String[] fx = entfx.getStringList();
        
        for(String val : fx) {
        	
        	try {
        		
	        	String s = val.split(":")[0];
	        	int id = Integer.valueOf(val.split(":")[1]);
	        	int level = Integer.valueOf(val.split(":")[2]);
	        	int dura = Integer.valueOf(val.split(":")[3]);
	        	
	        	potionList.add(new PotionEntry(s, id, dura, level));
        	
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }
        /////////////////////////////////////////////////////////////////////////
        Property entimm = config.get("ENTITYCONTROL", "entityImmunity", new String[] { "" });
        entimm.comment = "What entities should receive damage immunity (syntax: [entity name]:[damage source name])";
        String[] imm = entimm.getStringList();
        
        for(String val : imm) {
        	
        	try {
        		
	        	String s = val.split(":")[0];
	        	String d = val.split(":")[1];
	        	
	        	immunityList.add(new ImmunityEntry(s, d));
        	
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }
        /////////////////////////////////////////////////////////////////////////
        Property stocks = config.get("STOCKMARKET", "stocks", new String[] {
        		"Bobcum Motors:CUM:50:2.5:7.5:10:7.5:2.5",
        		"Bingus International:BIN:50:2.5:7.5:10:7.5:2.5",
        		"Spark Corporation:SPK:50:2.5:7.5:10:7.5:2.5",
        		"FlimFlam Industries:FLIM:50:2.5:7.5:10:7.5:2.5",
        		"Magpie Electricals:MAG:50:2.5:7.5:10:7.5:2.5",
        		"Papa G Softworks:PAPA:50:2.5:7.5:10:7.5:2.5"
        });
        stocks.comment = "NAME:SHORTNAME:STARTING VALUE:U2CHANCE:U1CHANCE:NCHANCE:D1CHANCE:D2CHANCE";
        String[] sto = stocks.getStringList();
        
        for(String val : sto) {
        	
        	try {
        		
	        	String name = val.split(":")[0];
	        	String shortname = val.split(":")[1];
	        	float start = Float.parseFloat(val.split(":")[2]);
	        	float u2 = Float.parseFloat(val.split(":")[3]);
	        	float u1 = Float.parseFloat(val.split(":")[4]);
	        	float n = Float.parseFloat(val.split(":")[5]);
	        	float d1 = Float.parseFloat(val.split(":")[6]);
	        	float d2 = Float.parseFloat(val.split(":")[7]);
	        	
	        	StockData.stocks.add(new Stock(name, shortname, start, u2, u1, n, d1, d2));
        	
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }
        /////////////////////////////////////////////////////////////////////////
        String[] u2 = createConfigStringList(config, "STOCKMARKET", "u2messages", "Broadcast for econ boosts, %s replaces company short", new String[] { "%s's newest product proved to be a smash hit!", "%s is doing very well this quarter!" } );
        
        for(String val : u2) {
        	
        	if(val.contains("%s"))
        		this.u2.add(val);
        	else
        		logger.error("Invalid config entry '" + val + "'");
        }
        
        String[] u1 = createConfigStringList(config, "STOCKMARKET", "u1messages", "Broadcast for small econ boosts, %s replaces company short", new String[] { "%s's newest product was featured in a famous television show!", "Customer ratings for %s's services are on the rise!" } );
        
        for(String val : u1) {
        	
        	if(val.contains("%s"))
        		this.u1.add(val);
        	else
        		logger.error("Invalid config entry '" + val + "'");
        }
        
        String[] d1 = createConfigStringList(config, "STOCKMARKET", "d1messages", "Broadcast for small econ falls, %s replaces company short", new String[] { "%s's newest product was poorly received.", "%s lost a lawsuit over a faulty product." } );
        
        for(String val : d1) {
        	
        	if(val.contains("%s"))
        		this.d1.add(val);
        	else
        		logger.error("Invalid config entry '" + val + "'");
        }
        
        String[] d2 = createConfigStringList(config, "STOCKMARKET", "d2messages", "Broadcast for econ falls, %s replaces company short", new String[] { "%s's newest product was an utter flop.", "Public outrage after a poor advertising campaign made by %s." } );
        
        for(String val : d2) {
        	
        	if(val.contains("%s"))
        		this.d2.add(val);
        	else
        		logger.error("Invalid config entry '" + val + "'");
        }
        
        String[] flags = createConfigStringList(config, "CLOWDER", "flags", "[name of the flag]:[whether it's shown in the listing]:[whether it has a tintable base]:[whether it has a static overlay]",
        		new String[] { "usa:true:true:true" } );
        
        for(String val : flags) {
        	
        	try {
	        	String fname = val.split(":")[0];
	        	boolean vis = Boolean.parseBoolean(val.split(":")[1]);
	        	boolean base = Boolean.parseBoolean(val.split(":")[2]);
	        	boolean over = Boolean.parseBoolean(val.split(":")[3]);
	        	
	        	EnumHelper.addEnum(ClowderFlag.class, fname, new Class[] { String.class, boolean.class, boolean.class, boolean.class }, new Object[] {fname, vis, base, over} );
	        	System.out.println("Successfully added flag " + fname);
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }

        warpCost = createConfigInt(config, "CLOWDER", "warpCost", "How much prestige a warp point costs to create", 25);
        territoryDelay = createConfigInt(config, "CLOWDER", "territoryDelay", "How many ticks inbetween territory validation operations", 5);
        territoryAmount = createConfigInt(config, "CLOWDER", "territoryAmount", "How many chunks are checked eaach operation", 50);
        prestigeDelay = createConfigInt(config, "CLOWDER", "prestigeDelay", "How many ticks inbetween prestige updates (1h per default)", 60 * 60 * 20);
        disableChests = createConfigBool(config, "CLOWDER", "disableChests", "Whether chests should not be placable outside of claims", true);
        mold = createConfigInt(config, "CLOWDER", "mold", "How many ticks cardboard boxes can remain loaded until rotting (5h by default)", 5 * 60 * 60 * 20);

        bb_rng = createConfigBool(config, "BOBBYBREAKER", "enableFineCalc", "Whether or not BB uses exact position values or rounded ones, exact values simulate RNG due to bomb spread and highly varying damage", false);
        
        u2en = createConfigBool(config, "STOCKMARKET", "u2enable", "Whether econ boost messages should be broadcasted", true);
        u1en = createConfigBool(config, "STOCKMARKET", "u1enable", "Whether small econ boost messages should be broadcasted", true);
        d1en = createConfigBool(config, "STOCKMARKET", "d1enable", "Whether small econ fall messages should be broadcasted", true);
        d2en = createConfigBool(config, "STOCKMARKET", "d2enable", "Whether econ fall messages should be broadcasted", true);
        updateInterval = createConfigInt(config, "STOCKMARKET", "updateInterval", "Time in seconds between market updates", 10 * 60);
        stockCap = createConfigInt(config, "STOCKMARKET", "stockCap", "How many shares a player can own per stock", 50);
        
        enableStocks = createConfigBool(config, "STOCKMARKET", "enableStocks", "Enables the stock market", true);
        
        /////////////////////////////////////////////////////////////////////////
        
        mlpf = createConfigInt(config, "ENTITYCONTROL", "MLPF", "How far the multi-layered pathfinder for zombs and creeps reaches", 100);
        caveCap = createConfigInt(config, "ENTITYCONTROL", "caveCap_New", "Sets the maximum Y-coord where cave sickness kick in", -10);

        zombAI = createConfigBool(config, "ENTITYCONTROL", "zombAI", "Enables advanced zombert AI", true);
        creepAI = createConfigBool(config, "ENTITYCONTROL", "creepAI", "Enables advanced creeper AI", true);
        
        config.save();
        
        File schemDir = new File(event.getModConfigurationDirectory() + "/schematics");
        
        if(!schemDir.exists())
        	schemDir.mkdir();
        
        for(File f : schemDir.listFiles()) {
        	if(f.isFile() && f.getName().endsWith(".schematic") && f.getName().split("_").length == 2) {
        		
        		Schematic schem = SchematicLoader.readFromFile(f);
        		
        		int val = Integer.parseInt(f.getName().split("_")[1].replace("_", "").replace(".schematic", ""));
        		
        		if(schem != null) {
        			schem.value = val;
        			schems.add(schem);
        		}
        	}
        }
	}
	
	public static List<Schematic> schems = new ArrayList();
	
	private static int createConfigInt(Configuration config, String category, String name, String comment, int def) {

        Property prop = config.get(category, name, def);
        prop.comment = comment;
        return prop.getInt();
	}
	
	private static boolean createConfigBool(Configuration config, String category, String name, String comment, boolean def) {

        Property prop = config.get(category, name, def);
        prop.comment = comment;
        return prop.getBoolean();
	}
	
	private static String[] createConfigStringList(Configuration config, String category, String name, String comment, String[] def) {

        Property prop = config.get(category, name, def);
        prop.comment = comment;
        return prop.getStringList();
	}
	
	private static void processBuffer() {
		for(String val : twilightBuffer) {
        	
        	try {
        		
        		String name = val.split(":")[0];
        		//gets block from string with no modifiers (intended for vanilla)
        		Block b = Block.getBlockFromName(name);
        		
        		//in case the block uses the integer id notation
        		if(b == null) {
        			try {
        				b = Block.getBlockById(Integer.parseInt(name));
        			} catch(Exception ex) { }
        		}
        		
        		//if there is no block found, it'll retry with the proper domain format (modid:name)
        		//first _ is replaced with : (intended for modded blocks with the inclusion of the modid)
        		if(b == null)
        			//b = Block.getBlockFromName(name.replaceFirst("_", ":"));
        			b = RegistryUtil.getBlockByNameNoCaseOrPoint(name);
        		
        		//if there is still no block found, it'll retry with the tile. prefix
        		//in addition to the replacement of the _, it'll search for 'tile' and add a period to create 'tile.' (intended for HFR blocks)
        		if(b == null) {
        			String nname = name.replaceFirst("tile", "tile.");
        			//b = Block.getBlockFromName(nname.replaceFirst("_", ":"));
        			b = RegistryUtil.getBlockByNameNoCaseOrPoint(nname);
        		}
        		
	        	int hp = Integer.valueOf(val.split(":")[1]);
	        	
	        	if(b != null)
	        		zombWhitelist.add(new GriefEntry(b, hp));
	        	else
	        		logger.error("Invalid block entry '" + val.split(":")[0] + "'");
        	
        	} catch(Exception ex) {
        		logger.error("Invalid config entry '" + val + "'");
        	}
        }
		
		for(Integer i : t2Buffer) {
			
			Block b = Block.getBlockById(i);
			
			if(b != Blocks.air)
				blastShields.add(b);
		}
	}
	
	public static class GriefEntry {
		
		int hp;
		Block block;
		
		public GriefEntry(Block b, int hp) {
			this.block = b;
			this.hp = hp;
		}
		
		public static int getEntry(Block b) {
			
			for(GriefEntry ent : zombWhitelist) {
				if(ent.block == b)
					return ent.hp;
			}
			
			return -1;
		}
	}
	
	public static class ControlEntry {
		
		int chance;
		String entity;
		
		public ControlEntry(String e, int chance) {
			this.entity = e;
			this.chance = chance;
		}
		
		public static int getEntry(Entity e) {
			
			for(ControlEntry ent : controlList) {
				if(ent.entity.equals(EntityList.getEntityString(e)))
					return ent.chance;
			}
			
			return -1;
		}
	}
	
	public static class PotionEntry {

		int potion;
		int duration;
		int amplifier;
		String entity;
		
		public PotionEntry(String e, int potion, int duration, int amplifier) {
			this.entity = e;
			this.potion = potion;
			this.duration = duration;
			this.amplifier = amplifier;
		}
		
		public static int[] getEntry(Entity e) {
			
			for(PotionEntry ent : potionList) {
				if(ent.entity.equals(EntityList.getEntityString(e)))
					return new int[] {ent.potion, ent.duration, ent.amplifier};
			}
			
			return null;
		}
	}
	
	public static class ImmunityEntry {

		String entity;
		String damage;
		
		public ImmunityEntry(String e, String atk) {
			this.entity = e;
			this.damage = atk;
		}
		
		public static List<String> getEntry(Entity e) {
			
			List<String> list = new ArrayList();
			
			for(ImmunityEntry ent : immunityList) {
				if(ent.entity.equals(EntityList.getEntityString(e)))
					list.add(ent.damage);
			}
			
			return list;
		}
	}
}
