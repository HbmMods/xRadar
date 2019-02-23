package com.hfr.main;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.hfr.blocks.ModBlocks;
import com.hfr.handler.GUIHandler;
import com.hfr.items.ModItems;
import com.hfr.lib.RefStrings;
import com.hfr.packet.PacketDispatcher;
import com.hfr.tileentity.TileEntityDummy;
import com.hfr.tileentity.TileEntityForceField;
import com.hfr.tileentity.TileEntityHatch;
import com.hfr.tileentity.TileEntityMachineRadar;
import com.hfr.tileentity.TileEntityMachineSiren;
import com.hfr.tileentity.TileEntityVaultDoor;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
	
	public static int crafting = 0;
	
	public static boolean freeRadar = false;
	public static boolean sound = true;
	public static boolean comparator = false;
	
	Random rand = new Random();
	
	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent)
	{
		if(logger == null)
			logger = PreEvent.getModLog();
		
		loadConfig(PreEvent);
		
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		CraftingManager.mainRegistry();
		proxy.registerRenderInfo();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		
		GameRegistry.registerTileEntity(TileEntityMachineSiren.class, "tileentity_hfr_siren");
		GameRegistry.registerTileEntity(TileEntityMachineRadar.class, "tileentity_hfr_radar");
		GameRegistry.registerTileEntity(TileEntityForceField.class, "tileentity_hfr_field");
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, "tileentity_hfr_vault");
		GameRegistry.registerTileEntity(TileEntityDummy.class, "tileentity_hfr_dummy");
		GameRegistry.registerTileEntity(TileEntityHatch.class, "tileentity_hfr_hatch");
	}

	@EventHandler
	public static void load(FMLInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent)
	{
	}
	
	public void loadConfig(FMLPreInitializationEvent event)
	{
		if(logger == null)
			logger = event.getModLog();
		
		PacketDispatcher.registerPackets();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
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
        
        config.save();
	}
}
