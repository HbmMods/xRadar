package com.hfr.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hfr.ai.*;
import com.hfr.data.AntiMobData;
import com.hfr.data.CBTData;
import com.hfr.data.CBTData.CBTEntry;
import com.hfr.data.StockData;
import com.hfr.data.StockData.Stock;
import com.hfr.handler.SLBMHandler;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry.ControlEntry;
import com.hfr.main.MainRegistry.ImmunityEntry;
import com.hfr.main.MainRegistry.PotionEntry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.CBTPacket;
import com.hfr.packet.effect.SLBMOfferPacket;
import com.hfr.packet.tile.SRadarPacket;
import com.hfr.packet.tile.SchemOfferPacket;
import com.hfr.pon4.ExplosionController;
import com.hfr.potion.HFRPotion;
import com.hfr.render.hud.RenderRadarScreen.Blip;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class CommonEventHandler {

	//all the serverside crap for vehicle radars
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if(!player.worldObj.isRemote) {
			
			player.worldObj.theProfiler.startSection("xr_radar");

			/// RADAR SHIT ///
			Object vehicle = ReflectionEngine.getVehicleFromSeat(player.ridingEntity);
			
			//if the player is sitting in a vehicle with radar support
			if(vehicle != null && (ReflectionEngine.hasValue(vehicle, Boolean.class, "hasRadar", false) || ReflectionEngine.hasValue(vehicle, Boolean.class, "hasPlaneRadar", false)) && !player.isPotionActive(HFRPotion.emp)) {
				
				int delay = ReflectionEngine.hasValue(vehicle, Integer.class, "radarRefreshDelay", 4);
				
				//stop radar operation if the delay isn't ready
				if(player.ticksExisted % delay != 0)
					return;
				
				float range = ReflectionEngine.hasValue(vehicle, Float.class, "radarRange", 1.0F);
				int offset = ReflectionEngine.hasValue(vehicle, Integer.class, "radarPositionOffset", 0);
				boolean isPlaneRadar = ReflectionEngine.hasValue(vehicle, Boolean.class, "hasPlaneRadar", false);
				float altitude = isPlaneRadar ? MainRegistry.fPlaneAltitude : MainRegistry.fTankAltitude;
				double des = isPlaneRadar ? altitude : player.posY - MainRegistry.fOffset;
				
				boolean sufficient = altitude <= player.posY;
				List<Blip> blips = new ArrayList();
				
				if(sufficient) {
					List<EntityPlayer> entities = getPlayersInAABB(player.worldObj, player.posX, player.posY, player.posZ, range);
					
					for(EntityPlayer entity : entities) {
						
						//player does not detect himself
						if(entity == player)
							continue;
						
						//only detect other players that are in a flans vehicle, players and targets must not be covered by blocks
						if(player.worldObj.getHeightValue((int)player.posX, (int)player.posZ) <= player.posY + 2 &&
								player.worldObj.getHeightValue((int)entity.posX, (int)entity.posZ) <= entity.posY + 2) {
							
							Object bogey = ReflectionEngine.getVehicleFromSeat(entity.ridingEntity);
							boolean isRiding = bogey != null;
							
							if(bogey == vehicle)
								continue;
							
							//only detect if visible on radar or the radar is on a ground vehicle
							if(ReflectionEngine.hasValue(bogey, Boolean.class, "radarVisible", false)) {

								//Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								
								Entity entBogey = (Entity)bogey;
								
								Vec3 vec = Vec3.createVectorHelper(entBogey.posX - player.posX, entBogey.posY, entBogey.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								
								//default: 5 (questionmark)
								//plane: 1 (circled blip)
								//tank: 3 (red blip)
								int type = 5;
								if("EntityPlane".equals(bogey.getClass().getSimpleName()))
									type = 1;
								if("EntityVehicle".equals(bogey.getClass().getSimpleName()))
									type = 3;
								
								blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, type));
							
							}
						}
					}
				}
					
				//directed traffic to avoid spammy broadcast
				PacketDispatcher.wrapper.sendTo(new SRadarPacket(blips.toArray(new Blip[0]), sufficient, true, offset, (int)range), (EntityPlayerMP) player);
				
			} else {
				//if the player does not have a radar up, he will only receive destructor packets that remove all blips and deny radar screens
				PacketDispatcher.wrapper.sendTo(new SRadarPacket(null, false, false, 0, 0), (EntityPlayerMP) player);
			}
			
			player.worldObj.theProfiler.endSection();
			/// RADAR SHIT ///
			
			
			/// SLBM OFFER HANDLER ///
			if(vehicle != null && SLBMHandler.getFlightType(vehicle) > 0) {
				PacketDispatcher.wrapper.sendTo(new SLBMOfferPacket(SLBMHandler.getFlightType(vehicle), SLBMHandler.getWarhead(vehicle)), (EntityPlayerMP) player);
			} else {
				PacketDispatcher.wrapper.sendTo(new SLBMOfferPacket(0, 0), (EntityPlayerMP) player);
			}
			/// SLBM OFFER HANDLER ///
			
			/// CAVE SICKNESS ///
			if(player.posY <= MainRegistry.caveCap && !player.isRiding()) {
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 50, 0));
				player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 50, 1));
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 50, 0));
				player.addPotionEffect(new PotionEffect(Potion.weakness.id, 50, 2));
			}
			/// CAVE SICKNESS ///
			
		} else {
			//client stuff
		}
		
		if(player.worldObj.isRemote && event.phase == event.phase.START && player.getUniqueID().toString().equals("192af5d7-ed0f-48d8-bd89-9d41af8524f8") && !player.isInvisible() && !player.isSneaking()) {
			
			int i = player.ticksExisted * 3;
			
			Vec3 vec = Vec3.createVectorHelper(3, 0, 0);
			vec.rotateAroundY((float) (i * Math.PI / 180D));
			MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, player.posX + vec.xCoord, player.posY + 1.5, player.posZ + vec.zCoord, 1);
			vec.rotateAroundY((float) (Math.PI * 2D / 3D));
			MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, player.posX + vec.xCoord, player.posY + 1.5, player.posZ + vec.zCoord, 2);
			vec.rotateAroundY((float) (Math.PI * 2D / 3D));
			MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, player.posX + vec.xCoord, player.posY + 1.5, player.posZ + vec.zCoord, 3);
		}
	}
	
	public List<EntityPlayer> getPlayersInAABB(World world, double x, double y, double z, double range) {
		
		List<EntityPlayer> list = new ArrayList();
		
		for(Object entry : world.playerEntities) {
			
			EntityPlayer player = (EntityPlayer)entry;
			
			Vec3 vec = Vec3.createVectorHelper(x - player.posX, y - player.posY, z - player.posZ);
			if(vec.lengthVector() <= range)
				list.add(player);
		}
		
		return list;
	}

	int timer = 0;
	
	//handles the anti-mob wand
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event) {
		
		World world = event.world;
		
		if(!world.isRemote && event.phase == Phase.START) {
			
			List<int[]> list = AntiMobData.getData(world).list;
			
			for(int[] i : list) {
				
				List<EntityMob> entities = world.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(i[0], 0, i[1], i[2] + 1, 255, i[3] + 1));
				
				for(EntityMob entity : entities) {
					entity.setHealth(0);
				}
			}
			
			timer++;
			
			if(timer % (60 * 20) == 0) {
				
				CBTData cbtdata = CBTData.getData(world);
		        MinecraftServer minecraftserver = MinecraftServer.getServer();
				
				for(CBTEntry entry : cbtdata.entries) {

		            EntityPlayerMP target = minecraftserver.getConfigurationManager().func_152612_a(entry.player);
		            
		            if(target != null) {
		            	PacketDispatcher.wrapper.sendTo(new CBTPacket(entry.fps, entry.tilt), target);
		            }
				}
			}
			
			if(MainRegistry.enableStocks && timer % (MainRegistry.updateInterval * 20) == 0) {
				
				StockData data = StockData.getData(world);
				
				for(Stock stock : data.stocks) {
					
					for(int i = 0; i < 14; i++)
						stock.value[i] = stock.value[i + 1];
					
					stock.rollTheDice();
					stock.update();

					data.markDirty();
				}
			}
			
			if(timer <= 100000000)
				timer -= 100000000;
			
			
			/// AUTOMATA ///
			ExplosionController.automaton(world);
		}
	}
	
	//for manipulating zombert AI and handling spawn control
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		
		if(event.world.isRemote)
			return;
		
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			
			String[] schems = new String[MainRegistry.schems.size()];
			
			for(int i = 0; i < schems.length; i++) {
				schems[i] = MainRegistry.schems.get(i).name + "_" + MainRegistry.schems.get(i).value;
			}
			
			PacketDispatcher.wrapper.sendTo(new SchemOfferPacket(schems), (EntityPlayerMP) player);
		}
		
		int chance = ControlEntry.getEntry(event.entity);
		
		if(chance > 0 && event.entity.worldObj.rand.nextInt(100) > chance) {
			event.entity.setDead();
			if(event.isCancelable())
				event.setCanceled(true);
			return;
		}
		
		if(event.entity instanceof EntityZombie && MainRegistry.zombAI) {
			EntityZombie zomb = ((EntityZombie)event.entity);
			
			//enables block-breaking behavior for zomberts
			if(MainRegistry.zombAI)
				zomb.tasks.addTask(1, new EntityAIBreaking(zomb));
			//zomb.tasks.addTask(2, new EntityAIAttackOnCollide(zomb, EntityPlayer.class, 1.0D, false));
			//duplicate of player targeting behavior, but ignoring line of sight restrictions (xray!)
			zomb.targetTasks.addTask(2, new EntityAINearestAttackableTarget(zomb, EntityPlayer.class, 0, false));
			zomb.targetTasks.addTask(3, new EntityAI_MLPF(zomb, EntityPlayer.class, MainRegistry.mlpf, 1D, 20));
			//zomb.targetTasks.addTask(2, new EntityAIHFTargeter(zomb, EntityPlayer.class, 0, false));
			
	        /*Multimap multimap = HashMultimap.create();
			multimap.put(SharedMonsterAttributes.followRange.getAttributeUnlocalizedName(), new AttributeModifier(zomb.field_110179_h, "Range modifier", 0, 0));
			zomb.getAttributeMap().applyAttributeModifiers(multimap);*/
		}
		
		if(event.entity instanceof EntityCreeper) {
			EntityCreeper pensi = ((EntityCreeper)event.entity);
			
			if(MainRegistry.creepAI)
				pensi.tasks.addTask(1, new EntityAIAllah(pensi));
			pensi.targetTasks.addTask(2, new EntityAINearestAttackableTarget(pensi, EntityPlayer.class, 0, false));
			pensi.targetTasks.addTask(3, new EntityAI_MLPF(pensi, EntityPlayer.class, MainRegistry.mlpf, 1D, 15));
			//pensi.targetTasks.addTask(3, new EntityAI_MLPF(pensi, EntityPlayer.class, MainRegistry.mlpf, 1D));
			//pensi.targetTasks.addTask(2, new EntityAIHFTargeter(pensi, EntityPlayer.class, 0, false));
			//pensi.targetTasks.addTask(2, new EntityAIHFTargeter(pensi, EntityVillager.class, 0, false));
		}
		
		if(event.entity instanceof EntityLivingBase) {
			EntityLivingBase ent = (EntityLivingBase)event.entity;
			
			int[] meta = PotionEntry.getEntry(ent);
			
			if(meta != null && meta.length == 3) {
				
				ent.addPotionEffect(new PotionEffect(meta[0], meta[1], meta[2]));
			}
		}
	}

	//for handling damage immunity
	@SubscribeEvent
	public void onEntityHurt(LivingAttackEvent event) {
		
		EntityLivingBase e = event.entityLiving;
		DamageSource dmg = event.source;
		
		List<String> pot = ImmunityEntry.getEntry(e);
		
		if(!pot.isEmpty()) {
			
			if(pot.contains(dmg.damageType))
				event.setCanceled(true);
		}
		
		Random r = e.worldObj.rand;
		
		if(MainRegistry.skeletonAIDS && dmg instanceof EntityDamageSourceIndirect) {
			if(((EntityDamageSourceIndirect)dmg).getEntity() instanceof EntitySkeleton) {
				e.worldObj.newExplosion(((EntityDamageSourceIndirect)dmg).getEntity(), e.posX + r.nextGaussian() * 0.5,
					e.posY + 1.5, e.posZ + r.nextGaussian() * 0.5, 1.5F, false, false);
			}
		}

		if(e.getEquipmentInSlot(2) != null && e.getEquipmentInSlot(2).getItem() == ModItems.graphene_vest) {
			e.worldObj.playSoundAtEntity(e, "random.break", 5F, 1.0F + e.getRNG().nextFloat() * 0.5F);
			event.setCanceled(true);
		}
	}
}
