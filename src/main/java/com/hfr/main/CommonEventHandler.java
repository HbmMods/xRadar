package com.hfr.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hfr.ai.*;
import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.data.AntiMobData;
import com.hfr.data.StockData;
import com.hfr.data.StockData.Stock;
import com.hfr.entity.missile.EntityMissileAntiBallistic;
import com.hfr.entity.missile.EntityMissileBaseSimple;
import com.hfr.handler.SLBMHandler;
import com.hfr.main.MainRegistry.ControlEntry;
import com.hfr.main.MainRegistry.ImmunityEntry;
import com.hfr.main.MainRegistry.PotionEntry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderFlagPacket;
import com.hfr.packet.effect.SLBMOfferPacket;
import com.hfr.packet.tile.SRadarPacket;
import com.hfr.packet.tile.SchemOfferPacket;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;

public class CommonEventHandler {

	//all the serverside crap for vehicle radars
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		EntityPlayer player = event.player;
		
		if(!player.worldObj.isRemote) {

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
					List<Entity> entities = player.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(player.posX - range, des, player.posZ - range, player.posX + range, 3000, player.posZ + range));
					
					for(Entity entity : entities) {
						
						//player does not detect himself
						if(entity == player)
							continue;

						double dX = entity.posX - player.posX;
						double dZ = entity.posZ - player.posZ;
						
						//eliminates blips that won't ever appear on radar screen
						if(Math.sqrt(dX * dX + dZ * dZ) > range)
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
							
							} else if(ReflectionEngine.hasValue(entity, Boolean.class, "missileRadarVisible", false)) {
								
								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								int type = 4;
								blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, type));
								
							} else if(entity instanceof EntityMissileBaseSimple) {

								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);

								int mode = ((EntityMissileBaseSimple)entity).mode;
								
								if(mode == 0) {
									blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, 6));
								} else if(mode == 2) {
									blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, 7));
								}
								
							} else if(entity instanceof EntityMissileAntiBallistic) {

								Vec3 vec = Vec3.createVectorHelper(entity.posX - player.posX, entity.posY, entity.posZ - player.posZ);
								vec.rotateAroundY(player.rotationYaw * (float)Math.PI / 180F);
								
								blips.add(new Blip((float)-vec.xCoord, (float)vec.yCoord, (float)-vec.zCoord, 8));
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
			
			if(vehicle != null && SLBMHandler.getFlightType(vehicle) > 0) {
				PacketDispatcher.wrapper.sendTo(new SLBMOfferPacket(SLBMHandler.getFlightType(vehicle), SLBMHandler.getWarhead(vehicle)), (EntityPlayerMP) player);
			} else {
				PacketDispatcher.wrapper.sendTo(new SLBMOfferPacket(0, 0), (EntityPlayerMP) player);
			}
			
			if(player.posY <= MainRegistry.caveCap && !player.isRiding()) {
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 50, 0));
				player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 50, 1));
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 50, 0));
				player.addPotionEffect(new PotionEffect(Potion.weakness.id, 50, 2));
			}
			
			flagPopup(player.worldObj, player);
			
		} else {

			//particleBorder(player.worldObj, player);
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
			/*MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, player.posX + vec.xCoord, player.posY + 0.4, player.posZ + vec.zCoord, 2);
			MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, player.posX + vec.xCoord, player.posY + 0.6, player.posZ + vec.zCoord, 3);
			MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, player.posX + vec.xCoord, player.posY + 0.8, player.posZ + vec.zCoord, 1);*/
		}
	}
	
	public static final String NBTKEY = "lastClowder";
	private void flagPopup(World world, EntityPlayer player) {

		TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair((int)player.posX, (int)player.posZ));
		
		Ownership owner = meta == null ? ClowderTerritory.WILDERNESS : meta.owner;
		
		String name = owner.zone.toString();
		
		if(owner.zone == Zone.FACTION)
			name = owner.owner.name;
		
		String past = player.getEntityData().getString(NBTKEY);
		
		if(past.isEmpty()) {
			player.getEntityData().setString(NBTKEY, name);
			return;
		}
		
		if(!name.equals(past)) {

			
			if(owner.zone == Zone.FACTION)
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(owner.owner), (EntityPlayerMP) player);
			else
				PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(name), (EntityPlayerMP) player);
		}
		
		player.getEntityData().setString(NBTKEY, name);
	}
	
	private void particleBorder(World world, EntityPlayer player) {

		int oX = (int)player.posX;
		int oZ = (int)player.posZ - 1;

		TerritoryMeta north = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.NORTH.offsetX * 16, oZ + ForgeDirection.NORTH.offsetZ * 16));
		TerritoryMeta south = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.SOUTH.offsetX * 16, oZ + ForgeDirection.SOUTH.offsetZ * 16));
		TerritoryMeta east = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.EAST.offsetX * 16, oZ + ForgeDirection.EAST.offsetZ * 16));
		TerritoryMeta west = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair(oX + ForgeDirection.WEST.offsetX * 16, oZ + ForgeDirection.WEST.offsetZ * 16));
		TerritoryMeta center = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair(oX, (int)player.posZ));

		boolean n = isTerritoryDifferent(north, center);
		boolean s = isTerritoryDifferent(south, center);
		boolean e = isTerritoryDifferent(east, center);
		boolean w = isTerritoryDifferent(west, center);

		int x = (int)player.posX - ((int)player.posX % 16);
		int z = (int)player.posZ - ((int)player.posZ % 16);
		
		if(n) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.NORTH.offsetX * 16 + ForgeDirection.WEST.offsetX * world.rand.nextDouble() * 16 + 16;
				double spanZ = z + ForgeDirection.NORTH.offsetZ * 16 + ForgeDirection.WEST.offsetZ * world.rand.nextDouble() * 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
		if(s) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.SOUTH.offsetX * 16 - ForgeDirection.WEST.offsetX * world.rand.nextDouble() * 16;
				double spanZ = z + ForgeDirection.SOUTH.offsetZ * 16 - ForgeDirection.WEST.offsetZ * world.rand.nextDouble() * 16 - 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
		if(e) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.EAST.offsetX * 16 + ForgeDirection.NORTH.offsetX * world.rand.nextDouble() * 16;
				double spanZ = z + ForgeDirection.EAST.offsetZ * 16 + ForgeDirection.NORTH.offsetZ * world.rand.nextDouble() * 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
		if(w) {
			for(int i = 0; i < 16; i++) {

				double spanX = x + ForgeDirection.WEST.offsetX * 16 + ForgeDirection.NORTH.offsetX * world.rand.nextDouble() * 16 + 16;
				double spanZ = z + ForgeDirection.WEST.offsetZ * 16 + ForgeDirection.NORTH.offsetZ * world.rand.nextDouble() * 16;
				
				double y = world.getHeightValue((int)spanX, (int)spanZ) + world.rand.nextGaussian();
				
				MainRegistry.proxy.howDoIUseTheZOMG(player.worldObj, spanX, y + 1.5, spanZ, 3);
			}
		}
		
	}
	
	private static boolean isTerritoryDifferent(TerritoryMeta one, TerritoryMeta two) {
		
		if(one == null && two != null)
			return true;
		
		if(one != null && two == null)
			return true;
		
		if(one != null && two != null) {
			
			if(one.owner.zone != two.owner.zone)
				return true;
			
			if(one.owner.owner != two.owner.owner)
				return true;
		}
		
		return false;
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
			
			/*if(timer % (60 * 20) == 0) {
				
				CBTData cbtdata = CBTData.getData(world);
		        MinecraftServer minecraftserver = MinecraftServer.getServer();
				
				for(CBTEntry entry : cbtdata.entries) {

		            EntityPlayerMP target = minecraftserver.getConfigurationManager().func_152612_a(entry.player);
		            
		            if(target != null) {
		            	PacketDispatcher.wrapper.sendTo(new CBTPacket(entry.fps, entry.tilt), target);
		            }
				}
			}*/
			
			if(timer % (MainRegistry.updateInterval * 20) == 0) {
				
				StockData data = StockData.getData(world);
				
				for(Stock stock : data.stocks) {
					
					for(int i = 0; i < 14; i++)
						stock.value[i] = stock.value[i + 1];
					
					stock.rollTheDice();
					stock.update();

					//MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(stock.shortname + " " + stock.phase.name()));
					
					data.markDirty();
				}
			}
			
			if(timer <= 100000000)
				timer -= 100000000;

			/// CLOWDER TERRITORYY ADMINISTRATIVE STUFF START ///
			
			ClowderTerritory.persistenceAutomaton(world);
			
			/// CLOWDER TERRITORYY ADMINISTRATIVE STUFF END ///
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
				e.worldObj.newExplosion(((EntityDamageSourceIndirect)dmg).getEntity(), e.posX + r.nextGaussian() * 0.5, e.posY + 1.5, e.posZ + r.nextGaussian() * 0.5, 1.5F, false, false);
			}
		}
	}
	
	//this one prevents block placements and breaking
	@SubscribeEvent
	public void clowderBlockEvent(BlockEvent event) {
		
		if(event instanceof BreakEvent || event instanceof PlaceEvent) {
			int x = event.x;
			int z = event.z;
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(x, z));
			
			if(owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) {
				event.setCanceled(true);
				return;
			}
			
			if(event instanceof BreakEvent) {
				Clowder clowder = Clowder.getClowderFromPlayer(((BreakEvent)event).getPlayer());
				
				if(owner.zone == Zone.FACTION && clowder != owner.owner)
					event.setCanceled(true);
			}
			
			if(event instanceof PlaceEvent) {
				Clowder clowder = Clowder.getClowderFromPlayer(((PlaceEvent)event).player);
				
				if(owner.zone == Zone.FACTION && clowder != owner.owner)
					event.setCanceled(true);
			}
		}
	}
	
	//that one cancels explosions in safe- and warzones
	@SubscribeEvent
	public void clowderExplosionEvent(Detonate event) {
		
		for(int i = 0; i < event.getAffectedBlocks().size(); i++) {
			
			ChunkPosition pos = event.getAffectedBlocks().get(i);
			int x = pos.chunkPosX;
			int z = pos.chunkPosZ;
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(x, z));
			
			if(owner.zone == Zone.SAFEZONE || owner.zone == Zone.WARZONE) {
				event.getAffectedBlocks().remove(i);
				i--;
			}
		}
	}
	
	//this thing prevents players from different facts from interacting with blocks
	@SubscribeEvent
	public void clowderContainerEvent(PlayerInteractEvent event) {

		int x = event.x;
		int y = event.y;
		int z = event.z;
		
		if(event.action == Action.RIGHT_CLICK_BLOCK && event.world.getBlock(x, y, z) != ModBlocks.clowder_flag) {
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(x, z));
			
			if(owner != null) {
				Clowder clowder = Clowder.getClowderFromPlayer(event.entityPlayer);
				
				if(owner.zone == Zone.FACTION && clowder != owner.owner)
					event.setCanceled(true);
			}
		}
	}
}
