package com.hfr.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.flansmod.common.FlansMod;
import com.flansmod.common.driveables.EntityDriveable;
import com.flansmod.common.driveables.EntitySeat;
import com.flansmod.common.driveables.ItemPlane;
import com.flansmod.common.driveables.ItemVehicle;
import com.flansmod.common.driveables.mechas.ItemMecha;
import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.Clowder.ScheduledTeleport;
import com.hfr.clowder.ClowderFlag;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.clowder.events.RegionOwnershipChangedEvent;
import com.hfr.data.ClowderData;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.main.ReflectionEngine;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderFlagPacket;
import com.hfr.tileentity.clowder.ITerritoryProvider;
import com.hfr.tileentity.clowder.TileEntityFlagBig;
import com.hfr.tileentity.prop.TileEntityProp;
import com.hfr.util.ParserUtil;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventBus;
import hardcorequesting.QuestingData;
import hardcorequesting.Team;
import hardcorequesting.Team.PlayerEntry;
import hardcorequesting.Team.UpdateType;
import hardcorequesting.TeamStats;
import hardcorequesting.quests.Quest;
import hardcorequesting.quests.QuestData;
import hardcorequesting.reputation.Reputation;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class CommandClowder extends CommandBase {

	private static final CoordPair wbpospos = new CoordPair(4274, 1335);
	private static final CoordPair wbnegneg = new CoordPair(2278, 4);

	private boolean insideBorders(CoordPair flagLoc) {
		return flagLoc.x >= wbnegneg.x && flagLoc.x <= wbpospos.x && flagLoc.z >= wbnegneg.z && flagLoc.z <= wbpospos.z;
	}

	@Override
	public String getCommandName() {
		return "clowder";
	}

	@Override
	public List getCommandAliases() {
		return new ArrayList() {
			{
				add("clowder");
				add("c");
			}
		};
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/clowder help";
	}

	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

		if (sender.getEntityWorld().provider.dimensionId != 0) {
			sender.addChatMessage(new ChatComponentText(CRITICAL + "Critical error: CatFac only works in overworld!!"));
		}

		if (Clowder.clowders.size() == 0)
			ClowderData.getData(sender.getEntityWorld());

		if (args.length < 1) {
			sender.addChatMessage(new ChatComponentText(ERROR + getCommandUsage(sender)));
			return;
		}

		String cmd = args[0].toLowerCase();
		switch (cmd) {
			
			case "supress":{
				if (args.length > 1)
					cmdSuppress(sender, args[1]);
			}break;
			/*  retconned to prevent bitch transfer abuse
			case "bitchpass":{
				if (args.length > 1)
					cmdBitchPass(sender, args[1], args[2]);
			}break;
			*/
		
			case "help":
			case "man": {
				if (args.length > 1)
					cmdHelp(sender, args[1]);
				else
					cmdHelp(sender, "1");
			}break;
			
			case "create":{
				if(args.length > 1)
					cmdCreate(sender, args[1]);
			}break;
			
			case "disembark":{
				if(args.length > 1)
					cmdDisembark(sender, args[1]);
			}break;
			
			case "disband":{
				if(args.length > 1)
					cmdDisband(sender, args[1]);
			}break;
			
			case "comrades":{
				cmdComrades(sender);
			}break;
			
			case "alliance":{
				cmdAlliance(sender);
			}break;
			
			case "info":{
				if (args.length > 1)
					cmdInfo(sender, args[1]);
				else
					cmdInfo(sender, null);
			}break;
			
			case "list":{
				cmdList(sender);
			}break;
			
			case "motd":{
				if (args.length > 1)
					cmdMOTD(sender, args);
			}break;
			
			case "owner":{
				if (args.length > 1)
					cmdOwner(sender, args[1]);
			}break;
			
			case "apply":{
				if (args.length > 1)
					cmdApply(sender, args[1]);
			}break;
			
			case "suckoff":{
				if (args.length > 1)
					cmdSuckoff(sender, args[1]);
			}break;
			
			case "accepttribute":{
				if (args.length > 1)
					cmdAcceptTribute(sender, args[1]);
			}break;
			
			case "befriend":{
				if (args.length > 1)
					cmdBefriend(sender, args[1]);
			}break;
			
			case "acceptfriend":{
				if (args.length > 1)
					cmdAcceptFriend(sender, args[1]);
			}break;
			
			case "leave":{
				cmdLeave(sender);
			}break;
			
			case "accept":{
				if (args.length > 1)
					cmdAccept(sender, args[1]);
			}break;
			
			case "deny":{
				if (args.length > 1)
					cmdDeny(sender, args[1]);
			}break;
			
			case "applicants":{
					cmdApplicants(sender);
			}break;
			
			case "kick":{
				if (args.length > 1)
					cmdKick(sender, args[1]);
			}break;
			
			case "unfriend":{
				if (args.length > 1)
					cmdUnfriend(sender, args[1]);
			}break;

			case "listflags":{
				if (args.length > 1)
					cmdListflags(sender, args[1]);
				else
					cmdListflags(sender, "1");
			}break;
			
			case "flag":{
				if (args.length > 1)
					cmdFlag(sender, args[1]);
			}break;
			
			case "retreat":{
				cmdRetreat(sender);
			}break;
			
			case "fabricate":{
				if (args.length > 1)
					cmdFabricate(sender, args[1]);
			}break;
			
			// victims of war fabrications can take the initiative and do a preemptive
			// strike
			case "preemptive":{
				if (args.length > 1)
					cmdPreemptive(sender, args[1]);
			}break;
			
			case "release":{
				if (args.length > 1)
					cmdRelease(sender, args[1]);
			}break;
			
			case "suppress":{
				if (args.length > 1)
					cmdSuppress(sender, args[1]);
			}break;
			
			case "overtime":{
				cmdOverTime(sender);
			}break;
			
			case "revolt":{
				cmdRevolt(sender);
			}break;
			
			case "declare":{
				cmdDeclareWar(sender);
			}break;
			
			case "abort":{
				cmdAbort(sender);
			}break;
			
			case "sethome":{
				cmdSethome(sender);
			}break;
			case "setallywarp":{
				cmdSetAllyWarp(sender);
			}break;
			
			case "home":{
				cmdHome(sender);
			}break;
			case "allywarp":{
				cmdAllyWarp(sender, args[1]);
			}break;
			
			case "addwarp":
			case "setwarp":{
				if (args.length > 1)
					cmdAddWarp(sender, args[1]);
			}break;
			
			case "delwarp":{
				if (args.length > 1)
					cmdDelWarp(sender, args[1]);
			}break;
			
			case "warp":{
				if (args.length > 1)
					cmdWarp(sender, args[1]);
			}break;
			
			case "warps":{
				cmdWarps(sender);
			}break;
			
			case "balance":{
				cmdBalance(sender);
			}break;
			
			case "deposit":{
				if (args.length > 1)
					cmdDeposit(sender, args[1]);
			}break;
			
			case "withdraw":{
				sender.addChatMessage(new ChatComponentText(CRITICAL + "This command is currently disabled!"));
			}break;
			
			case "claim":{
				cmdClaim(sender);
			}break;
			
			case "unclaim":{
				cmdUnclaim(sender);
			}break;
			
			case "promote":{
				if (args.length > 1)
					cmdPromote(sender, args[1]);
			}break;
			
			case "demote":{
				if (args.length > 1)
					cmdDemote(sender, args[1]);
			}break;
			
			default: {
				sender.addChatMessage(new ChatComponentText(ERROR + getCommandUsage(sender)));
			}break;
//			template
//			case " ":{
//				if (args.length > 1)
//					
//			}break;
		}
	}

	private void cmdRetreat(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			if(clowder.getPermLevel(player.getDisplayName()) > 1) 
			{
			if(!clowder.totalenKrieg) // no retreat on war day
			{
				
				if(!clowder.bitch) // no retreat on war day
				{
			
	
				//if attacker did not declare war, redirect them to /abort
				if (clowder.getWartime() == 0 && (clowder.getFabricatetime() > 0 || clowder.getCanDeclareTime() > 0) && clowder.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "You don't have to retreat during YOUR OWN fabrication attempt. use /c abort!"));
				else

				clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Player " + player.getDisplayName() + " issued a retreat order!"));
				//sender.addChatMessage(new ChatComponentText(INFO + "You will be automatically kicked in 10 minutes!"));

				//beginning of clowderevent stuff i stole
 
				{
			
						//removes targeted status, used to fuck over enemy fabrications
						if(clowder.targeted)
							clowder.targeted=false;
						
	
							
							//due this shit to all enemies who were fabricating against the retreatee
							for(Clowder everyone : clowder.clowders)
							{
								//retreat during declare available time
								if (everyone.enemy == clowder && everyone.getWartime() == 0 && everyone.getCanDeclareTime() > 0)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "It's too late to retreat without risking land loss!"));
									sender.addChatMessage(new ChatComponentText(INFO + "We must either wait for the enemy to declare war or"));
									sender.addChatMessage(new ChatComponentText(INFO + "use /c preemptive *enemy* to start the war ourselves"));
									sender.addChatMessage(new ChatComponentText(INFO + "Then we can either fight or /c retreat and lose 10 minutes of land!"));
									
								}
								else
			
								
								//retreating during fabrication (not declare time!)
								if (everyone.enemy == clowder && everyone.getWartime() == 0 && everyone.getCanDeclareTime() == 0)
								{
									//notifies them of cancellation
									everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has retreated, cancelling our war fabrication attempt!"));
									everyone.notifyAll(player.worldObj, new ChatComponentText("Because of this act of cowardice, we earn back the prestige we spent!"));
									everyone.notifyAll(player.worldObj, new ChatComponentText("Also they have become our(?) bitch!"));
									//give back prestige cost of fabrication
									everyone.addPrestige(MainRegistry.fabricateCost, player.worldObj);
									//cancels fabrications
									everyone.pussy(player.worldObj);
									//becomes their bitch
									clowder.suzerain = everyone;
									clowder.suzerainS = everyone.name;
									clowder.bitch = true;
									//first bitched 12 hour treaty
									clowder.longPeace(player.worldObj);
									
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Enemy war fabrication will be cancelled, however "));
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "we will become their bitch!"));
									
									//bitch and vassal transfer
									for(Clowder bitches : clowder.clowders)
									{
										if(bitches.suzerain == clowder)
										{
											bitches.suzerain = everyone;
											bitches.suzerainS = everyone.name;
											bitches.notifyAll(player.worldObj, new ChatComponentText("Our former master has been subjugated!"));
											bitches.notifyAll(player.worldObj, new ChatComponentText("Our new master is " + everyone.name));
										}
											
									}
									
								}
								else
								
								
								
								
								//retrating during war
								if (everyone.enemy == clowder && everyone.getWartime() > 0 )
								{
	
									clowder.retreatBanHammer(player, clowder.name, 11);
									

									
									if(everyone.getWartime() > 10)
									{
										everyone.freeWarTime(player.worldObj);
									everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has retreated! We get 10 minutes to capture as much as we want for free!"));
									everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "Also, they become our bitch when the 10 minutes end!"));
									}
									else
									{
										everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has retreated! Quick capture as much land as possible before the timer runs out!"));
									everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + " Also, they become our bitch when the timer runs out!"));
									}
									
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "The war ends soon but we will become our enemies' bitch,"));
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "and they get 10 minutes to take land unopposed!"));
									
									
									//bitch and vassal transfer - causes boo boos during 10-min free conquest so i will move bitchifcation to war end and banhammer still ticking
									
									
									
									//just ban EVERYONE in the faction
									//do the tempban last so i can test the rest of the code without null pointer cancel
									try {
										for(String s : clowder.members.keySet())
											Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 10m You are tempbanned because you retreated!", s));	
									} catch (Exception e) {
									}
									
								}
								else
								
								//attacker retreats
								if (clowder.getWartime() > 0 && clowder.retreatBan == 0)
								{
	
									clowder.retreatBanHammer(player, clowder.name, 11);
									
									//bitch and vassal transfer - causes boo boos during 10-min free conquest so i will move bitchifcation to war end and banhammer still ticking
									/*
									//becomes their bitch
									clowder.suzerain = everyone;
									clowder.suzerainS = everyone.name;
									clowder.bitch = true;
									//first bitched 12 hour treaty
									clowder.longPeace(player.worldObj);
									*/

									if(clowder.getWartime() > 10)
									{
										clowder.freeWarTime(player.worldObj);
									clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has called off their invasion! We get 10 minutes to take as much land as we want!"));
									clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + " Also, they are now our bitch!"));
									}
									else
									{
										everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has given up on their invasion! Quick capture as much land as possible before the timer runs out!"));
										clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + " Also, they are now our bitch!"));
									}
									
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "We bit off more than we could chew!"));
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "We will officially become a bitch after our 'victim' "));
									clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "gets 10 free minutes to capture some of our land unopposed!"));
									
									/*
									//bitch and vassal transfer - attacker to victim edition
									for(Clowder bitches : clowder.clowders)
									{
										if(bitches.suzerain == clowder)
										{
											bitches.suzerain = clowder.enemy;
											bitches.suzerainS = clowder.enemy.name;
											bitches.notifyAll(player.worldObj, new ChatComponentText("Our former master has been subjugated!"));
											bitches.notifyAll(player.worldObj, new ChatComponentText("Our new master is " + clowder.enemy.name));
										}
											
									}
									*/
									
									//just ban EVERYONE in the faction
									//do the tempban last so i can test the rest of the code without null pointer cancel
									try {
										for(String s : clowder.members.keySet())
											Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)String.format("tempban %s 10m You are tempbanned because you retreated!", s));	
									} catch (Exception e) {
									}
								}	
							}	
							
							
					}
				//end of stolen from clowder events
				
			}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "No retreating for bitches!"));
			}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no retreating during total war!"));
			}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permisions to order a retreat!"));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	//fabricate war command
	private void cmdFabricate(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder attacker = Clowder.getClowderFromPlayer(player);		
		Clowder victim = name == null ? Clowder.getClowderFromPlayer(player) : Clowder.getClowderFromName(name);
		
		
	
		if(victim != null && attacker != null) 
		{	
			
			if(attacker.getPermLevel(player.getDisplayName()) > 1) {
				
				
			if (victim == attacker)
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot fabricate a war against yourself"));
			
				
				//anti freshly made 1-man fac unending war
			else if (attacker.getPrestigeGen()<20)
				{
					sender.addChatMessage(new ChatComponentText(ERROR + "You need to buildup your clowder to 20 prestige gen before you can start wars"));
				}

				else if (attacker.bitch)
				sender.addChatMessage(new ChatComponentText(ERROR + "Bitches cannot start wars other than revolts!"));
			
			
				else if (attacker.enemy!=null)
				sender.addChatMessage(new ChatComponentText(ERROR + "You already have a target"));
			
			
			/*  retconned so you can target vassals
			else if (victim.suzerain != null)
			{
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot directly target " +victim.name +" because they are a vassal!"));
				sender.addChatMessage(new ChatComponentText(ERROR + "You must target their master, " + victim.suzerain.name + "!"));
			}
			*/
			
			else if (victim.suzerain != null && attacker.suzerain != null && victim.suzerain == attacker.suzerain)
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot target a fellow vassal of our shared master!"));
			
			//war day mode
			else	if(attacker.totalenKrieg && attacker.getWartime() == 0 && !attacker.bitch && !attacker.paxBritannica)
			{
				
				if (attacker.suzerain != null && attacker.suzerain == victim) //revolt redirects to /c revolt
					sender.addChatMessage(new ChatComponentText(ERROR + "Use /c revolt if you want to betray your master!"));
				else
					
				if(victim.suzerain != null && victim.suzerain != attacker || victim.suzerain == null)
				{
				sender.addChatMessage(new ChatComponentText(TITLE + "Because the world is in a state of total war, fabrications are not needed!"));
				
				attacker.enemy = victim;
				attacker.enemyS = victim.name;
				
				if(victim.suzerain != null) //if targetting a vassal, auto declare on the master!
				{			
					attacker.enemy = victim.suzerain;
					attacker.enemyS = victim.suzerainS;
					attacker.vassalTarget = true;
					sender.addChatMessage(new ChatComponentText(TITLE + "Because our target is a vassal/bitch, we will auto-target their master!"));
					sender.addChatMessage(new ChatComponentText(TITLE + "This will let us take land from any of their vassals!"));					
				}
				
				if(attacker.suzerain == null || (attacker.suzerain != null && victim != attacker.suzerain) ) //for non revolt
				{
				attacker.addWarTime(60, player.worldObj);
				victim.targeted = true;
				if(attacker.vassalTarget)
				
				for(Clowder everyone : attacker.clowders)
				{
				everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + attacker.name + " has declared war against " + attacker.enemy.name + "!"));
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
				if(everyone.suzerain == attacker)
					everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "Since they are our master, we are automatically involved in their war!"));	
				}		
			}
	
			}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You cannot declare war against your own vassal unless you /c release them!"));
			}
			//end of war day mode block
			
			
			else if (victim.isRaidable() )
			{
				
				//expansion limit. if not enough prestige to handle your land, then you cannot fabricate wars
				if(attacker.getPrestigeReq() < attacker.getPrestige())
				{
				
				
				if(attacker.getPrestige() > MainRegistry.fabricateCost)
				{
					
					if(victim.suzerain != attacker)
					{
						
						if(attacker.suzerain != victim)
						{
							
							
							if(!attacker.paxBritannica)
							{
							
							//the treaty overflow checker
							if( !(attacker.treaty1 != "nobody2584369" && attacker.treaty2 != "nobody2584369" && attacker.treaty3 != "nobody2584369" && attacker.treaty4 != "nobody2584369" && attacker.treaty5 != "nobody2584369" && attacker.treaty6 != "nobody2584369" && attacker.treaty7 != "nobody2584369") )		
							 {
								/*
								//im sorry its hideous
							 if(attacker.treaty1 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime1 + " minutes!"));
								}
								else if(attacker.treaty2 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime2 + " minutes!"));
								}
								else if(attacker.treaty3 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime3 + " minutes!"));
								}
								else if(attacker.treaty4 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime4 + " minutes!"));
								}
								else if(attacker.treaty5 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime5 + " minutes!"));
								}
								else if(attacker.treaty6 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime6 + " minutes!"));
								}
								else if(attacker.treaty7 == victim.name)
								{
									sender.addChatMessage(new ChatComponentText(ERROR + "You have already tried fabricating against " + victim.name + " recently!"));
									sender.addChatMessage(new ChatComponentText(ERROR + "Try again in " + attacker.treatyTime7 + " minutes!"));
								}
								else
								*/
								
						 if(attacker.getFabricatetime() == 0 && attacker.getWartime() == 0 && attacker.getCanDeclareTime() == 0) //for when the command successfully works
				{
							
							
							
							
						if (attacker.markTreaty(player, attacker.name, victim.name));
						//moved the shit to clowder.java because command hates recognizing treaty numbers
							
				}
				
				else if (attacker.getFabricatetime() > 0 && attacker.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "You are already fabricating a war! Use /c info to see the remaining time and target!"));
				else if (attacker.getWartime() > 0  && attacker.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "You are already at war!"));
				else if (attacker.getCanDeclareTime() > 0  && attacker.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "War declaration is already complete! Use /c declare to begin the war!"));
				
							
				
						}
						else
						sender.addChatMessage(new ChatComponentText(ERROR + "Calm down, you have attempted to fabricate on seven clowders within the past few hours. Take a break"));
						
						}
						else
						sender.addChatMessage(new ChatComponentText(ERROR + "World peace mode is enabled!"));
						}
					else
					{
						sender.addChatMessage(new ChatComponentText(ERROR + "You cannot declare war against your master! You must start a revolt using '/c revolt'!"));
						sender.addChatMessage(new ChatComponentText(TITLE + "Revolting costs double the prestige of a normal war declaration!"));
					}
						
				}
					else
						sender.addChatMessage(new ChatComponentText(ERROR + "You cannot declare war against your tributary! You must cancel the tribute using '/c release'!"));
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You do not have enough prestige ("+ MainRegistry.fabricateCost +") to fabricate a war!"));
			
				
			}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "Your clowder does not have enough prestige to handle its vast territories!"));
			
			}
			else 
			sender.addChatMessage(new ChatComponentText(ERROR + "Your target does not have enough members online to be considered online!"));

			
		}
		else 
		sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to fabricate wars!"));
			
		}
		else
		sender.addChatMessage(new ChatComponentText(ERROR + "You need to be in a clowder and have a valid target to fabricate wars!"));
	}
	
	private void cmdOverTime(ICommandSender sender) 
	{

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			if(clowder.getPermLevel(player.getDisplayName()) > 1) 
			{
				
				
				
				
				if(clowder.enemy != null)
				{
					if(clowder.getFabricatetime() > 1 || clowder.getCanDeclareTime() > 1)
						sender.addChatMessage(new ChatComponentText(ERROR + "You have not yet declared war!"));
					else
					{
						
					if (clowder.getCanDeclareTime()<=0 && clowder.getWartime() > 0)
					{
						
						if(clowder.enemy.retreatBan == 0)
						{
						if(clowder.getPrestige()>MainRegistry.fabricateCost*3)
						{
						if(!clowder.overtime)
						{
						clowder.addWarTime(30, player.worldObj);
						clowder.overtime = true;
						clowder.addPrestige(-MainRegistry.fabricateCost*3, player.worldObj);
						clowder.save(player.worldObj);
						
						clowder.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "An officer has extended the war by 30 minutes!"));
						clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + clowder.name + " has extended the war by 30 minutes!"));
						clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "They can only do this once per war, and it costed them triple prestige!"));
						}
						else
							sender.addChatMessage(new ChatComponentText(ERROR + "Your clowder has already used over-time during this war!"));
						}
						else
							sender.addChatMessage(new ChatComponentText(ERROR + "You do not have enough prestige to enter over-time!"));	
						}
						else
							sender.addChatMessage(new ChatComponentText(ERROR + "The enemy has retreated, no extending the free conquest bonus-time!"));	
					}
						
						
					}
					
						
					
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You are not in or planning an offensive war!"));
		
		}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to extend wars!"));
		}
		else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	
	private void cmdHelp(ICommandSender sender, String page) {

		int p = this.parseInt(sender, page);
		int pages = 6;

		if (p < 1 || p > pages)
			p = 1;

		sender.addChatMessage(new ChatComponentText(HELP + "/clowder [command] <args...> {optional args...}"));
		sender.addChatMessage(new ChatComponentText(INFO + "Commands [" + p + "/" + pages + "]:"));

		if (p == 1) {
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-fabricate <name>" + TITLE
					+ " - Fabricates a war against a clowder, costs " + MainRegistry.fabricateCost + " prestige"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-declare" + TITLE
					+ " - Declares war against your target if you have already fabricated a war against them"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-preemptive <name>" + TITLE
					+ " - If an enemy has fabricated a war against you, allows you to start the war yourself"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-overtime" + TITLE + " - Extends an active war by 30 minutes, costs "
							+ MainRegistry.fabricateCost * 3 + " prestige; usable once per war"));
			/* retconned to prevent bitch transfer abuse
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-bitchpass <name1> <name2>" + TITLE + " - Gifts a bitch (name1) to (name2), can be used to end vassal-targeting wars!"));
			*/
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-suckoff <name>" + TITLE + " - Sends a tribute offer to a clowder"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-accepttribute <playername>" + TITLE + " - Accepts a player's tribute offer"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-release <name>" + TITLE
					+ " - Grants independence to a tributary. (Works during revolts)"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-revolt" + TITLE + " - If you are a tributary, starts an independence movement"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-suppress <name>" + TITLE
					+ " - If <name> is your tributary and is seeking independence, escalates into an armed revolt"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 2"));
		}

		if (p == 2) {
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-help {page}" + TITLE + " - The thing you just used"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-create <name>" + TITLE + " - Creates a clowder"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-disband <name>" + TITLE
					+ " - Disbands a clowder, name parameter for confirmation"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_ADMIN + "-owner <player>" + TITLE + " - Transfers clowder ownership"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-comrades" + TITLE + " - Shows all members of your clowder"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-color <hexadecimal>" + TITLE + " - Sets the clowder's color"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND_LEADER + "-motd <MotD>" + TITLE + " - Sets the clowder's MotD"));
			sender.addChatMessage(
					new ChatComponentText(ERROR + "WARNING" + TITLE + " - Renaming might release your tributaries"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 3"));
		}

		if (p == 3) {
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-info {page}" + TITLE + " - Shows info on a clowder"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-list" + TITLE + " - Lists all clowders (page functin pending)"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-apply <name>" + TITLE + " - Sends an application to a clowder"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-leave" + TITLE + " - Leaves the clowder"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-accept <name>" + TITLE + " - Accepts a player's application"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-deny <name>" + TITLE + " - Denies a player's application"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND_LEADER + "-applicants" + TITLE + " - Lists applying players"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-kick <player>" + TITLE + " - Removes player from clowder"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 4"));
		}

		if (p == 4) {
			sender.addChatMessage(
					new ChatComponentText(COMMAND_LEADER + "-flag <flag>" + TITLE + " - Changes clowder flag"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-listflags" + TITLE + " - Lists availible flags"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND_LEADER + "-sethome" + TITLE + " - Sets the clowder's home point"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-home" + TITLE + " - Teleports to the clowder's home"));

			sender.addChatMessage(new ChatComponentText(COMMAND + "-addwarp <name>" + TITLE + " - Creates a warp"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-delwarp <name>" + TITLE + " - Removes a warp"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-warp <name>" + TITLE + " - Teleports to a warp point"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-warps" + TITLE + " - Lists all warps"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 5"));
		}

		if (p == 5) {
			sender.addChatMessage(new ChatComponentText(COMMAND + "-retreat" + TITLE
					+ " - Protects entire clowder from enemy fabrications by retreating after 10 minutes"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-retreat" + TITLE
					+ " - Retreat wont work if the enemy declared a war, so do it quick!"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-claim" + TITLE + " - Creates a new flag"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND + "-balance" + TITLE + " - Displays how much prestige the clowder has"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND + "-deposit <amount>" + TITLE + " - Turns prestige items into digiprestige"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND + "-withdraw <amount>" + TITLE + " - Withdraws digiprestige as prestige items"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_ADMIN + "-promote <amount>" + TITLE + " - Promotes a member to officer"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_ADMIN + "-demote <amount>" + TITLE + " - Demotes an officer to member"));
		}
		
		if (p == 6) {
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-befriend <name>" + TITLE + " - Sends an alliance offer to a clowder"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-acceptfriend <playername>" + TITLE + " - Accepts a player's alliance offer and signs a treaty"));
			sender.addChatMessage(new ChatComponentText(
					COMMAND_LEADER + "-unfriend <name>" + TITLE + " - Cancels an alliance with a clowder"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND_LEADER + "-setallywarp" + TITLE + " - Sets the clowder's alliance rally-point"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-allywarp <name>" + TITLE + " - Teleports to an ally rally-point"));
			sender.addChatMessage(
					new ChatComponentText(COMMAND + "-alliance" + TITLE + " - Shows name of all allied clowders"));
		}
	}

	public static void cmdCreate(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);

		if (Clowder.getClowderFromPlayer(player) == null) {

			if (Clowder.getClowderFromName(name) == null) {
				Clowder.createClowder(player, name);
				// modid instead of modname
				if (Loader.isModLoaded("HardcoreQuesting")) {
					Team team = getTeam(sender.getCommandSenderName());
					String teamName = name;
					QuestingData.addTeam(team);
					team.name = teamName;
					team.refreshTeamData(UpdateType.ONLY_MEMBERS);
					team.declineAll(sender.getCommandSenderName());
					TeamStats.refreshTeam(team);
					team.setReputation(0, (int) Clowder.getClowderFromName(name).getPrestige());
				}
				sender.addChatMessage(new ChatComponentText(TITLE + "Created clowder " + name + "!"));
				sender.addChatMessage(new ChatComponentText(INFO + "Use /c claim to get started!"));
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This name is already taken!"));
			}

		} else {
			sender.addChatMessage(
					new ChatComponentText(ERROR + "You can not create a new clowder while already being in one!"));
		}
	}

	public static Team getTeamByName(String name) {
		List<Team> teams = QuestingData.getTeams();
		for (Team a : teams)
			if (a != null && a.name != null && a.getName().equals(name))
				return a;
		return null;
	}

	private static Team getTeam(String playerName) {
		if (QuestingData.getQuestingData(playerName) == null)
			return null;
		return QuestingData.getQuestingData(playerName).getTeam();
	}

	public static void cmdDisband(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {
			if (clowder.canDisband) {
				if (name.equals(clowder.name)) {

					if (clowder.disbandClowder(player)) {
						// modid instead of modname
						try {
							if (Loader.isModLoaded("HardcoreQuesting")) {
								Team team = getTeam(sender.getCommandSenderName());
								team.deleteTeam();
								for (Quest a : Quest.getQuests())
									team.resetProgress(a);
								team.setReputation(0, 0);
								TeamStats.refreshTeam(team);
							}
						} catch (IndexOutOfBoundsException e) {

						}
						sender.addChatMessage(new ChatComponentText(CRITICAL + "Your clowder was disbanded!"));
						clowder.colours.remove(clowder.color);
					} else {
						sender.addChatMessage(
								new ChatComponentText(ERROR + "Can not disband a clowder you do not own!"));
					}

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR
							+ "Confirmation unsuccessful. Please enter the clowder name to disband the clowder."));
				}
			} else {
				sender.addChatMessage(new ChatComponentText(
						ERROR + "You can only disband every " + (MainRegistry.disbandDelay / 60 / 60 / 20) + " hours"));
			}
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdComrades(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + clowder.getDecoratedName()));

			for (String s : clowder.members.keySet())
				sender.addChatMessage(new ChatComponentText(LIST + s));

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	private void cmdAlliance(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + clowder.getDecoratedName() + " Alliance:"));

			 
			for (Clowder s : clowder.allies.keySet())
				sender.addChatMessage(new ChatComponentText(LIST + s.name));

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdInfo(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);

		Clowder clowder = name == null ? Clowder.getClowderFromPlayer(player) : Clowder.getClowderFromName(name);

		if (clowder != null) {

			
			
			sender.addChatMessage(new ChatComponentText(TITLE + clowder.getDecoratedName()));
			sender.addChatMessage(new ChatComponentText(TITLE + clowder.motd));
			if(clowder.totalenKrieg && !clowder.paxBritannica)
				sender.addChatMessage(new ChatComponentText(ERROR + "Total war mode is enabled!"));
			if(clowder.paxBritannica)
				sender.addChatMessage(new ChatComponentText(CRITICAL + "World Peace mode is enabled!"));
			if(clowder.forceOnline)
				sender.addChatMessage(new ChatComponentText(CRITICAL + "An admin set this fac to be 'raidable' even if members offline!"));
			sender.addChatMessage(new ChatComponentText(LIST + "Owner: " + clowder.leader));
			sender.addChatMessage(new ChatComponentText(LIST + "Players considered online: "
					+ clowder.getPlayersOnline() + "/" + clowder.members.keySet().size()));
			sender.addChatMessage(new ChatComponentText(LIST + "Raidable? " + clowder.isRaidable()));
			sender.addChatMessage(new ChatComponentText(LIST + "Members: " + clowder.members.size()));
			sender.addChatMessage(new ChatComponentText(LIST + "Vassals: " + clowder.getVassals(clowder)));
			sender.addChatMessage(new ChatComponentText(LIST + "Bitches: " + clowder.getBitches(clowder)));
			sender.addChatMessage(new ChatComponentText(LIST + "Prestige: " + clowder.round(clowder.getPrestige())));
			String warning = (clowder.getPrestigeReq() < clowder.getPrestigeGen() && 0 <= clowder.getPrestige())? "The claims are safe." : (0 <= clowder.getPrestige()) ? ("Safe for "+ (int) Math.floor(clowder.getPrestige() / (clowder.getPrestigeReq() - clowder.getPrestigeGen()))+ " more hours") : (ERROR + "Can be overclaimed!");
			sender.addChatMessage(new ChatComponentText(LIST + warning));
			sender.addChatMessage(new ChatComponentText(LIST + " -generating: " + clowder.round(clowder.getPrestigeGen())+ " per hour (x" + clowder.round((float) Math.pow(0.99, clowder.getPrestige())) + ")"));
			sender.addChatMessage(new ChatComponentText(LIST + " -consumes: " + clowder.round(clowder.getPrestigeReq()) + " per hour"));
			sender.addChatMessage(new ChatComponentText(LIST + "Color: " + Integer.toHexString(clowder.color).toUpperCase()));

			///for peace treaty so vassal cannot infinite revolt, and so master cannot instant release-kill
			if(clowder.getPeaceTreaty()>0)
				sender.addChatMessage(new ChatComponentText(LIST + "Treaty time with master: " + clowder.getPeaceTreaty() + " minutes" ));
			
			//for memory nbt debug
			//sender.addChatMessage(new ChatComponentText(LIST + "suzerain string: " + clowder.suzerainS));
			//sender.addChatMessage(new ChatComponentText(LIST + "enemy string: " + clowder.enemyS));
			
			//for tributary
			if (clowder.suzerain != null)
			{
				if (clowder.bitch)
				{
					sender.addChatMessage(new ChatComponentText(LIST + "Bitch of: " + clowder.suzerain.name));
					sender.addChatMessage(new ChatComponentText(LIST + "Due to being a bitch, 1/2 of Prestige generation is lost. 1/10 goes to " + clowder.suzerain.name));
				}
				else
				{
				sender.addChatMessage(new ChatComponentText(LIST + "Vassal of: " + clowder.suzerain.name));
				sender.addChatMessage(new ChatComponentText(LIST + "Due to being a vassal, 1/5 of Prestige generation is lost. 1/10 goes to " + clowder.suzerain.name));
				}
			}
			
			//for war declaration
			if (clowder.enemy != null)
			{
			sender.addChatMessage(new ChatComponentText(LIST + "Current target: " + clowder.enemy.name));
			if (clowder.getFabricatetime() > 0 && clowder.enemy != clowder.suzerain)
			sender.addChatMessage(new ChatComponentText(LIST + "War can be declared in " + (int)(clowder.getFabricatetime()-1) + " minutes"));
			if (clowder.getFabricatetime() > 0 && clowder.enemy == clowder.suzerain)
				sender.addChatMessage(new ChatComponentText(LIST + "Independence will be gained peacefully in " + (int)(clowder.getFabricatetime()-1) + " minutes"));
			if (clowder.getCanDeclareTime() > 0 && (clowder.enemy)!=null)
				sender.addChatMessage(new ChatComponentText(LIST + "You have " + clowder.getCanDeclareTime() + " minutes to declare the war against " + clowder.enemy.name + "!"));
			if (clowder.getWartime() > 0)
			sender.addChatMessage(new ChatComponentText(LIST + "War will end in " + clowder.getWartime() + " minutes"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	//revolt against suzerain
	private void cmdRevolt(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder attacker = Clowder.getClowderFromPlayer(player);		
		
		
		
	
		if(attacker.suzerain != null && attacker != null) 
		{	
			
			Clowder victim = attacker.suzerain;
			
			if(attacker.getPermLevel(player.getDisplayName()) > 1) {
				
				
			if (victim == attacker)
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot fabricate a war against yourself"));
			
			else if (attacker.enemy!=null)
				sender.addChatMessage(new ChatComponentText(ERROR + "You already have a target"));
			
			
			else if (attacker.totalenKrieg && !attacker.paxBritannica)
			{
				//vassal/bitch peace treaties are still enforced during war time (not true for normal war grace periods)
				if(attacker.getPeaceTreaty() <= 0)
				{
				
				for(Clowder everyone : attacker.clowders)
				{
				everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + attacker.name + " has started an armed revolt against " + victim.name + "!"));
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);	
				}
				attacker.enemy = victim;
				attacker.enemyS = victim.name;
				attacker.addWarTime(30, player.worldObj);
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You cannot revolt until the treaty expires! ("+ attacker.getPeaceTreaty() +" minutes)"));
				
			}
			//end of total war block
			

			
			else if (victim.isRaidable() )
			{
				
				//no revolt during treaty
				if(attacker.getPeaceTreaty()<= 0)
				{
				
				//costs extra to revolt
				if(attacker.getPrestige() > MainRegistry.fabricateCost * 2)
				{
					
					//world peace check
					if(!attacker.paxBritannica)
					{
									
				
				if(attacker.getFabricatetime() == 0 && attacker.getWartime() == 0 && attacker.getCanDeclareTime() == 0) //for when the command successfully works
				{
					sender.addChatMessage(new ChatComponentText(TITLE + "Revolt preperations are being made!"));
				attacker.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "Allah be with us! We will soon be free of " + victim.name + "'s tyranny! Use /c info to see remaining time!"));
				player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiFabrication", 2.0F, 1.0F);
			//	Minecraft.getMinecraft().thePlayer.playSound("hfr:item.hoiFabrication", 0.5F, 1.0F);
				victim.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.ERROR + "WARNING! " + attacker.name + " is preparing a revolution!"));
				victim.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "Use /c suppress " + attacker.name + " to stop them by force! (This will start a civil-war)"));
				victim.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "Use /c release " + attacker.name + " to peacefully surrender to their demands!"));
				
				//prestige price toll paid
				attacker.addPrestige(-MainRegistry.fabricateCost*2, player.worldObj);
				
				//60 minute ultimatum for master to strike back or not
				attacker.addFabricateTime(60, player.worldObj);
				//marks your target as your faction's enemy
				attacker.enemy = victim;
				//hopefully server will memorize your enemy now
				attacker.markEnemy(player.worldObj, attacker.name, victim.name);
				//mark the victim as being targeted unless they pussy out
				
				
				}
				
				else if (attacker.getFabricatetime() > 0 && attacker.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "You are already planning a revolt! Use /c info to see the remaining time and target!"));
				else if (attacker.getWartime() > 0  && attacker.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "You are already revolting!"));
				else if (attacker.getCanDeclareTime() > 0  && attacker.enemy != null)
					sender.addChatMessage(new ChatComponentText(ERROR + "The revolt is ready! Use /c declare to rise up!"));
				


				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "World peace mode is enabled!"));
					
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You do not have enough prestige ("+ MainRegistry.fabricateCost*2 +") to start a revolution!"));
				
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You cannot revolt until the treaty expires! ("+ attacker.getPeaceTreaty() +" minutes)"));
			
				
			}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "Your target does not have enough members online to be considered online!"));
				

			
		}
		else 
		sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to start revolts!"));
			
		}
		else
		sender.addChatMessage(new ChatComponentText(ERROR + "You need to be in a clowder that is a vassal!"));
	}
	
	
	
	
	//war declare command preemptive strike
		private void cmdPreemptive(ICommandSender sender, String name) 
		{			
			EntityPlayer player = getCommandSenderAsPlayer(sender);
			Clowder clowder = Clowder.getClowderFromPlayer(player);		
			Clowder victim = name == null ? Clowder.getClowderFromPlayer(player) : Clowder.getClowderFromName(name);
			
			if(clowder != null) {
				if(clowder.getPermLevel(player.getDisplayName()) > 1) 
				{
					
					if(!victim.isRaidable())
					{
						sender.addChatMessage(new ChatComponentText(ERROR + "The aggressors are not considered 'online'!"));
					sender.addChatMessage(new ChatComponentText(ERROR + "Keep an eye on their /c info for our chance to strike back!"));
					}
						
					
					else if(victim.enemy == clowder && victim.getCanDeclareTime() > 0 && victim.getWartime() <= 0)
					{
	
						
						//poorly coded treaty thing removal
						if (Clowder.getClowderFromName(clowder.treaty1) == clowder.enemy)
						{
							clowder.treatyTime1 = 0;
							clowder.treaty1 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty2) == clowder.enemy)
						{
							clowder.treatyTime2 = 0;
							clowder.treaty2 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty3) == clowder.enemy)
						{
							clowder.treatyTime3 = 0;
							clowder.treaty3 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty4) == clowder.enemy)
						{
							clowder.treatyTime4 = 0;
							clowder.treaty4 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty5) == clowder.enemy)
						{
							clowder.treatyTime5 = 0;
							clowder.treaty5 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty6) == clowder.enemy)
						{
							clowder.treatyTime6 = 0;
							clowder.treaty6 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty7) == clowder.enemy)
						{
							clowder.treatyTime7 = 0;
							clowder.treaty7 = "nobody2584369";
						}
						
						clowder.save(player.worldObj);
						
						
							//forces enemy to declare war now
							victim.addWarTime(60, player.worldObj);
							victim.endDeclareTime(player.worldObj);
							
							//spam entire server with war declaration
							for(Clowder everyone : clowder.clowders)
							{
							everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has declared a preemptive war against " + victim.name + "!"));
							player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
							
							/*
							EntityPlayer victim = null;
							for(String combatants : everyone.members.keySet())
							victim = player.worldObj.getPlayerEntityByName(combatants);
							player.worldObj.playSoundEffect(victim.posX, victim.posY, victim.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
							//play war declaration sound to the world
							//everyone.soundAll(player.worldObj, "hfr:item.hoiWar");
							*/
							
							
							
						}
	
					}
					else
						sender.addChatMessage(new ChatComponentText(ERROR + "That clowder is not enough of a threat!"));	
					

			
			}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to declare a preemptive war!"));
			}
			else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
			}
		}
		
		
		//suppress revolt by force command
		private void cmdSuppress(ICommandSender sender, String name) 
		{			
			EntityPlayer player = getCommandSenderAsPlayer(sender);
			Clowder clowder = Clowder.getClowderFromPlayer(player);		
			Clowder victim = name == null ? Clowder.getClowderFromPlayer(player) : Clowder.getClowderFromName(name);
			
			if(clowder != null) {
				if(clowder.getPermLevel(player.getDisplayName()) > 1) 
				{
					
					// rebels are expected to be online
						
					
					 if(victim.enemy == clowder && victim.suzerain == clowder && victim.getFabricatetime() > 0 && victim.getWartime() <= 0)
					{
	
							//rebellion turns to 30 minute war
							victim.addWarTime(30, player.worldObj);
							victim.endDeclareTime(player.worldObj);
							
							//spam entire server with war declaration
							for(Clowder everyone : clowder.clowders)
							{
							everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + victim.name + "'s separatist movement from " + clowder.name + " has become an armed rebellion!"));
							player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
							
							/*
							EntityPlayer victim = null;
							for(String combatants : everyone.members.keySet())
							victim = player.worldObj.getPlayerEntityByName(combatants);
							player.worldObj.playSoundEffect(victim.posX, victim.posY, victim.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
							//play war declaration sound to the world
							//everyone.soundAll(player.worldObj, "hfr:item.hoiWar");
							*/		
						}
	
					}
					else
						sender.addChatMessage(new ChatComponentText(ERROR + "That clowder is either not your vassal or is not revolting!"));	
					

			
			}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to order a rebel crack-down!"));
			}
			else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
			}
		}
		
		/*
		//give away a bitch/vassal to prevent a war
		private void cmdBitchPass(ICommandSender sender, String name, String client) 
		{			
			EntityPlayer player = getCommandSenderAsPlayer(sender);
			Clowder clowder = Clowder.getClowderFromPlayer(player);		
			Clowder victim = Clowder.getClowderFromName(name);		
			Clowder patron = Clowder.getClowderFromName(client);		
			
			if(clowder != null) {
				if(victim != null && victim.suzerain == clowder) {
					if(patron != null) {
				if(clowder.getPermLevel(player.getDisplayName()) > 1) 
				{
					victim.suzerain = patron;
					victim.suzerainS = patron.name;
					//set them to a bitch even if they used to be a vassal xddd
					victim.bitch = true;
					sender.addChatMessage(new ChatComponentText(HELP + "You have given ownership of " + victim.name + " to " + patron.name + "!"));
					victim.notifyAll(player.worldObj, new ChatComponentText(ERROR + "Our master has sold our freedom to " + patron.name));
					patron.notifyAll(player.worldObj, new ChatComponentText(TITLE + clowder.name + " has pimped out " + victim.name + " to us as a new slave!"));
					//also gives a standard no revolt treaty
					victim.threePeace(player.worldObj);
					
					//ends vassal target wars
					if(patron.getWartime() > 0 && patron.vassalTarget && patron.enemy == clowder)
					{
								
						//gets a standard treaty so they dont rob all vassals
						if(patron.treaty1 == "nobody2584369" && patron.treaty1 != patron.enemy.name)
						{
							patron.treaty1 = patron.enemy.name;
							patron.treatyTime1 = 90;
							//System.out.println("it tried to treaty1 puppy");
						}
						else if(patron.treaty2 == "nobody2584369" && patron.treaty2 != patron.enemy.name)
						{
							patron.treaty2 = patron.enemy.name;
							patron.treatyTime2 = 90;
						}
						else if(patron.treaty3 == "nobody2584369" && patron.treaty3 != patron.enemy.name)
						{
							patron.treaty3 = patron.enemy.name;
							patron.treatyTime3 = 90;
						}
						else if(patron.treaty4 == "nobody2584369" && patron.treaty4 != patron.enemy.name)
						{
							patron.treaty4 = patron.enemy.name;
							patron.treatyTime4 = 90;
						}
						else if(patron.treaty5 == "nobody2584369" && patron.treaty5 != patron.enemy.name)
						{
							patron.treaty5 = patron.enemy.name;
							patron.treatyTime5 = 90;
						}
						else if(patron.treaty6 == "nobody2584369" && patron.treaty6 != patron.enemy.name)
						{
							patron.treaty6 = patron.enemy.name;
							patron.treatyTime6 = 90;
						}
						else if(patron.treaty7 == "nobody2584369" && patron.treaty7 != patron.enemy.name)
						{
							patron.treaty7 = patron.enemy.name;
							patron.treatyTime7 = 90;
						}
						
						//standard war end shit and dialogues
						patron.endWarTime(player.worldObj);
						patron.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + " has pimped out " + victim.name + " as a new bitch for us!"));
						victim.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + " has pimped us out to " + patron.name + " to end the war!"));
						patron.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.CRITICAL + clowder.name + " also gets a 1.5 hour grace period while we consumate the union!"));
						patron.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.CRITICAL + patron.name + "'s war against us has ended thanks to our bitch-tribute!"));
						patron.enemy.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.CRITICAL + "We also get a 1.5 hour treaty!"));
						//unmarks victim as being targeted
						patron.enemy.targeted = false;
						//unmarks being a vassal targeting war
						patron.vassalTarget = false;
						//unmarks victim as being the attacker's target
						patron.enemy = null;
						patron.enemyS = "nobody2584369";
						if(patron.suzerain != null)
						{
							patron.suzerain.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.CRITICAL + "Our vassal, " + patron.name + ", has finished their war!"));
						}
						//resets overtime
						patron.overtime = false;
						//and reset bonus minutes
						patron.bonusPoints = 0;
						patron.vassalTarget = false;
					}
					//end of vassaltarget-war cancelling block
		

				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to pimp out bitches!"));
			}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "That is not a valid clowder to pimp the bitch to!"));
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "That is not a valid vassal!"));
			}
			else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
			}
		}
		*/
		
	
	//cancel war command
		private void cmdAbort(ICommandSender sender) 
		{

			EntityPlayer player = getCommandSenderAsPlayer(sender);
			Clowder clowder = Clowder.getClowderFromPlayer(player);
			
			if(clowder != null) {
				if(clowder.getPermLevel(player.getDisplayName()) > 1) 

				{
					
					
					
					if(clowder.getWartime()>0)
					{
						sender.addChatMessage(new ChatComponentText(LIST + "It's too late. The war will go on unless we use /c retreat!"));
						sender.addChatMessage(new ChatComponentText(LIST + "However, retreating will lose us land and we'll become our 'victim's' bitch!"));
					}
					
					
					else if(clowder.getCanDeclareTime()>0 || clowder.getFabricatetime()>0)
					{
						clowder.notifyAll(player.worldObj, new ChatComponentText(LIST + "Our war justification was shamefully cancelled!"));
						clowder.notifyAll(player.worldObj, new ChatComponentText(ERROR + "We only get half of our prestige back..."));
						clowder.enemy.notifyAll(player.worldObj, new ChatComponentText(LIST + "Merely a bluff! " + clowder.name + " has cancelled their war goal against us!" ));

						clowder.pussy(player.worldObj);
						//only give back half of fabrication cost
						clowder.addPrestige(MainRegistry.fabricateCost*0.5f, player.worldObj);
						
						if(clowder.suzerain != null)
						clowder.suzerain.notifyAll(player.worldObj, new ChatComponentText(LIST + "Our vassal, " + clowder.name + " has cancelled their war goal. Perhaps we should aid their next endeavor?" ));
					}
					
					
					else
						sender.addChatMessage(new ChatComponentText(ERROR + "We have no wargoals to cancel!"));
					
					
					
					
					
				}		
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to declare war!"));
			}
			else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
			}
		}
		
		
		//liberate your vassal
		private void cmdRelease(ICommandSender sender, String name) 
		{

			EntityPlayer player = getCommandSenderAsPlayer(sender);
			Clowder clowder = Clowder.getClowderFromPlayer(player);
			Clowder vassal = Clowder.getClowderFromName(name);
			
			if(clowder != null) {
				if(clowder.getPermLevel(player.getDisplayName()) > 1) 

				{
					
					
					
					if(clowder.getWartime()>0 )
					{
						sender.addChatMessage(new ChatComponentText(ERROR + "You cannot release vassals while you are in an offensive war"));
					}
					
					
					else if(vassal.suzerain == clowder)
					{
						if(vassal.getPeaceTreaty()<= 0)
						{
						vassal.suzerain = null;
						vassal.suzerainS = "nobody2584369";
						//unbitches the bitch
						vassal.bitch = false;
						ClowderData.getData(player.worldObj).markDirty();
						
						
						clowder.notifyAll(player.worldObj, new ChatComponentText(LIST + "Our vassal " + vassal.name + " has been released!"));
					
						vassal.notifyAll(player.worldObj, new ChatComponentText(LIST + clowder.name + " has voluntarily granted us independence" ));
						
						//if the command was done during a revolt war, the war will end and be considered a surrender
						if( (vassal.getFabricatetime() > 0 && vassal.enemy == clowder) || (vassal.getWartime() > 0 && vassal.enemy == clowder) )
						{
							vassal.pussy(player.worldObj);
							vassal.addPrestige(MainRegistry.fabricateCost*2, player.worldObj);
							vassal.notifyAll(player.worldObj, new ChatComponentText(LIST + "We also get back the prestige we spent on the planned revolt!" ));
						}
						}
						else
							sender.addChatMessage(new ChatComponentText(ERROR + "You cannot release them until after the treaty expires! (" + vassal.getPeaceTreaty() + " minutes)"));

					}
					
				}
					
		
					
					
				}		
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to release vassals!"));
			
			
		}
		
		
	//war declare command
	private void cmdDeclareWar(ICommandSender sender) 
	{

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			if(clowder.getPermLevel(player.getDisplayName()) > 1) 
			{
				
				if(clowder.enemy.isRaidable() || clowder.vassalTarget) //you can declare on offline master if you were targetting vassals
				{
				
				
				if(clowder.enemy != null)
				{
					if(clowder.getFabricatetime() > 1)
						sender.addChatMessage(new ChatComponentText(ERROR + "You are still fabricating the war declaration!"));
					else
					{
					if (clowder.getCanDeclareTime()>0 && clowder.getWartime() <= 0)
					{
						clowder.addWarTime(60, player.worldObj);
						clowder.endDeclareTime(player.worldObj);
						
						
						//poorly coded treaty thing removal
						if (Clowder.getClowderFromName(clowder.treaty1) == clowder.enemy)
						{
							clowder.treatyTime1 = 0;
							clowder.treaty1 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty2) == clowder.enemy)
						{
							clowder.treatyTime2 = 0;
							clowder.treaty2 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty3) == clowder.enemy)
						{
							clowder.treatyTime3 = 0;
							clowder.treaty3 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty4) == clowder.enemy)
						{
							clowder.treatyTime4 = 0;
							clowder.treaty4 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty5) == clowder.enemy)
						{
							clowder.treatyTime5 = 0;
							clowder.treaty5 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty6) == clowder.enemy)
						{
							clowder.treatyTime6 = 0;
							clowder.treaty6 = "nobody2584369";
						}
						else if (Clowder.getClowderFromName(clowder.treaty7) == clowder.enemy)
						{
							clowder.treatyTime7 = 0;
							clowder.treaty7 = "nobody2584369";
						}
						
						clowder.save(player.worldObj);
						
						
						
						//spam entire server with war declaration
						for(Clowder everyone : clowder.clowders)
						{
						everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + clowder.name + " has declared war against " + clowder.enemy.name + "!"));
						player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
						if(everyone.suzerain == clowder)
							everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "Since they are our master, we are automatically involved in their war!"));
						
						/*
						EntityPlayer victim = null;
						for(String combatants : everyone.members.keySet())
						victim = player.worldObj.getPlayerEntityByName(combatants);
						player.worldObj.playSoundEffect(victim.posX, victim.posY, victim.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
						//play war declaration sound to the world
						//everyone.soundAll(player.worldObj, "hfr:item.hoiWar");
						*/
						
						}
						
					}
						
						
					}
					
						
					
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + "You have no valid target!"));
				
				}
				else
				{
					sender.addChatMessage(new ChatComponentText(ERROR + "Our target is not considered 'online'!"));
					sender.addChatMessage(new ChatComponentText(ERROR + "Keep an eye on their /c info and declare if they show up!"));
				}
			
			
		}
			else
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to declare war!"));
		}
		else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	/*private void cmdInfo2(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(name);
		
		if(clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + clowder.getDecoratedName()));
			sender.addChatMessage(new ChatComponentText(TITLE + clowder.motd));
			sender.addChatMessage(new ChatComponentText(LIST + "Owner: " + clowder.leader));
			sender.addChatMessage(new ChatComponentText(LIST + "Members: " + clowder.members.size()));
			sender.addChatMessage(new ChatComponentText(LIST + "Prestige: " + clowder.round(clowder.getPrestige())));
			sender.addChatMessage(new ChatComponentText(LIST + "Color: " + Integer.toHexString(clowder.color).toUpperCase()));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "This faction does not exist!"));
		}
	}*/
	
	private void cmdSuckoff(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(clowder.suzerain == null)
			{
			
			if(clowder.getPermLevel(player.getDisplayName()) > 1) {
			
			Clowder toApply = Clowder.getClowderFromName(name);
				
			if(toApply != null) {

				clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + sender.getCommandSenderName() + " sent tribute offer to " + toApply.getDecoratedName() + "!"));
				toApply.dickSuckers.add(player.getDisplayName());
				toApply.notifyAll(player.worldObj, new ChatComponentText(INFO + "Player " + sender.getCommandSenderName() + " of " + clowder.name + " is offering to pay us tribute to avoid our wrath!"));
				toApply.notifyAll(player.worldObj, new ChatComponentText(INFO + " Use /c accepttribute " + sender.getCommandSenderName() + " to accept the offer."));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no clowder with this name!"));
			}
			}
			else
			{
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions for foreign diplomacy!"));
			}
		} else 
		{
			sender.addChatMessage(new ChatComponentText(ERROR + "You are already a vassal!"));
		}
		}
			else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You need to be in a clowder!"));
		}
	}
	
	private void cmdAcceptTribute(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.getPermLevel(player.getDisplayName()) > 1) {
				
				if(clowder.dickSuckers.contains(name)) 
				{ //checks if the name of the guy you typed in command actually applied to become your slave
					
					if(Clowder.getClowderFromPlayerName(name) != null) 
					{
						
						Clowder slave = Clowder.getClowderFromPlayerName(name); //clowder of guy who offered to suck you off
						
						
						if (slave != clowder) //prevent becoming your own tributary
						{
						//clowder.addMember(player.worldObj, name);
						sender.addChatMessage(new ChatComponentText(INFO + "We accepted " + name + "'s offer to make " + slave.name + " our tributary!"));
						slave.notifyAll(player.worldObj, new ChatComponentText(INFO +  clowder.name + " accepted our offer. We are now their tributary."));
						
						//actually set us as the suzerain of our tributary. They can only have one suzerain, but we can have infinite tributaries
						slave.suzerain = clowder;
						slave.suzerainS = clowder.name;
						slave.addPeaceTreaty(60, player.worldObj);
						slave.vassalize(player.worldObj, clowder.name, slave.name);
						
						//for cancelling wars against the tributary
						if(clowder.enemy == slave)
						{
							clowder.pussy(player.worldObj);
							slave.notifyAll(player.worldObj, new ChatComponentText(INFO + "Because " + clowder.name + " accepted our offer, their war goals against us were cancelled."));
							clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Because " + slave.name + " is now our tributary, our war goals against them have been cancelled."));					
						}
				
						}
						else
							sender.addChatMessage(new ChatComponentText(ERROR + "We cannot become our own tributary"));
					}
					else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in another clowder!"));
					}
					
					clowder.dickSuckers.remove(name);
					
				}
				else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player has no active application!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to manage applications!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	private void cmdBefriend(ICommandSender sender, String name) {

		EntityPlayer envoy = getCommandSenderAsPlayer(sender);
		Clowder diplomat = Clowder.getClowderFromPlayer(envoy);
		
		if(diplomat != null) {
			
			if(diplomat.suzerain == null)
			{
			
			if(diplomat.getPermLevel(envoy.getDisplayName()) > 1) {
			
			Clowder toApply = Clowder.getClowderFromName(name);
				
			if(toApply != null) {
				
				if(diplomat.allies.get(toApply) == null)
				{

				diplomat.notifyAll(envoy.worldObj, new ChatComponentText(INFO + sender.getCommandSenderName() + " sent an alliance offer to " + toApply.getDecoratedName() + "!"));
				toApply.potentialFriends.add(envoy.getDisplayName());
				toApply.notifyAll(envoy.worldObj, new ChatComponentText(INFO + "Player " + sender.getCommandSenderName() + " of " + diplomat.name + " wishes to form an alliance!"));
				toApply.notifyAll(envoy.worldObj, new ChatComponentText(INFO + " Use /c acceptfriend " + sender.getCommandSenderName() + " to accept the offer."));
				
				} else 
					sender.addChatMessage(new ChatComponentText(ERROR + "We are already allies!"));				
				
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no clowder with this name!"));
			}
			}
			else
			{
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions for foreign diplomacy!"));
			}
		} else 
		{
			sender.addChatMessage(new ChatComponentText(ERROR + "Tributaries cannot form alliances!"));
		}
		}
			else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You need to be in a clowder!"));
		}
	}
	
	private void cmdAcceptFriend(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.suzerain == null) {
				
			
			if(clowder.getPermLevel(player.getDisplayName()) > 1) {
				
				if(clowder.potentialFriends.contains(name)) 
				{ //checks if the name of the guy you typed in command actually applied to become your slave
					
					if(Clowder.getClowderFromPlayerName(name) != null) 
					{
						
						Clowder friend = Clowder.getClowderFromPlayerName(name); //clowder of guy who offered to suck you off
						
						
						if (friend != clowder) //prevent becoming your own tributary
						{
						sender.addChatMessage(new ChatComponentText(INFO + "We accepted " + name + "'s offer to make " + friend.name + " our ally!"));
						friend.notifyAll(player.worldObj, new ChatComponentText(INFO +  clowder.name + " accepted our offer. We are now their ally."));
						

						
						//allah bookmark - install the actual ally shit here
						clowder.addAlly(player.worldObj, friend);
						friend.addAlly(player.worldObj, clowder);
						//friend.addPeaceTreaty(60, player.worldObj);
						
						//for cancelling wars against the tributary
						if(clowder.enemy == friend)
						{
							clowder.pussy(player.worldObj);
							friend.notifyAll(player.worldObj, new ChatComponentText(INFO + "Because " + clowder.name + " accepted our alliance offer, their war goals against us were cancelled."));
							clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Because " + friend.name + " is now our ally, our war goals against them have been cancelled."));
							
						}
						
						
						
						
						
						}
						else
							sender.addChatMessage(new ChatComponentText(ERROR + "We cannot become our own ally"));
					}
					else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in another clowder!"));
					}
					
					clowder.potentialFriends.remove(name);
					
				}
				else 
					sender.addChatMessage(new ChatComponentText(ERROR + "This player has no active application!"));							
			} else 
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to manage applications!"));			
			}
			 else 
					sender.addChatMessage(new ChatComponentText(ERROR + "Tributaries cannot form alliances!"));			 
		} else 
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
	}

	
	
	private void cmdList(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);

		for (Clowder c : Clowder.clowders) {

			sender.addChatMessage(new ChatComponentText(TITLE + c.getDecoratedName() + " - " + c.motd));
			sender.addChatMessage(new ChatComponentText(LIST + c.members.size() + " members"));
		}

		if (Clowder.clowders.isEmpty()) {
			sender.addChatMessage(
					new ChatComponentText(TITLE + "There are no clowders as of now. Use /clowder create <name>"));
			sender.addChatMessage(new ChatComponentText(TITLE + "to start your own clowder!"));
		}
	}

	private void cmdMOTD(ICommandSender sender, String[] motd) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				String stitched = "";

				for (int i = 1; i < motd.length; i++)
					stitched += motd[i] + " ";

				stitched = stitched.trim();

				clowder.setMotd(stitched, player);
				sender.addChatMessage(new ChatComponentText(TITLE + "Set clowder MotD to " + stitched + "!"));
			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to change this clowder's MOTD!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	public void cmdOwner(ICommandSender sender, String owner) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 2) {

				if (clowder.members.get(owner) != null) {

					if (Loader.isModLoaded("Hardcore Questing")) {
						Team team = getTeam(sender.getCommandSenderName());
						if (team == null) {
							team = new Team(sender.getCommandSenderName());
							QuestingData.addTeam(team);
						}
						PlayerEntry a = team.getEntry(clowder.leader);
						team.getPlayers().remove(a);
						team.getPlayers().add(new PlayerEntry(a.getName(), true, false));
						a = team.getEntry(owner);
						team.getPlayers().remove(a);
						team.getPlayers().add(new PlayerEntry(a.getName(), true, true));
					}

					clowder.transferOwnership(player.worldObj, owner);

					sender.addChatMessage(
							new ChatComponentText(INFO + "Transfered leadership to player " + owner + "!"));
					clowder.notifyLeader(player.worldObj,
							new ChatComponentText(INFO + "You are now this clowder's new leader!"));

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your clowder!"));
				}

			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are not the owner of the clowder!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdApply(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder == null) {

			Clowder toApply = Clowder.getClowderFromName(name);

			if (toApply != null) {

				sender.addChatMessage(
						new ChatComponentText(INFO + "Sent application to " + toApply.getDecoratedName() + "!"));
				toApply.applications.add(player.getDisplayName());
				toApply.notifyLeader(player.worldObj, new ChatComponentText(
						INFO + "Player " + sender.getCommandSenderName() + " would like to join your clowder!"));

			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no clowder with this name!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are already in a clowder!"));
		}
	}
	
	public static void cmdLeave(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {
			
			//bitches cannot escape
			if (!clowder.bitch)
			{

			if (clowder.getPermLevel(player.getDisplayName()) < 3) {

				clowder.removeMember(player.worldObj, player.getDisplayName());

				if (Loader.isModLoaded("HardcoreQuesting")) {
					Team team = getTeam(sender.getCommandSenderName());
					if (team != null) {
						team.removePlayer(sender.getCommandSenderName());
					}
				}

				sender.addChatMessage(new ChatComponentText(CRITICAL + "You left this clowder!"));

			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not leave a clowder you own!"));
			}
			
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "Bitches can not leave a clowder!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	public void cmdAccept(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				if (clowder.applications.contains(name)) {

					if (Clowder.getClowderFromName(name) == null) { //checks if they are indeed factionless
						clowder.addMember(player.worldObj, name);
						// modid instead of modname
						if (Loader.isModLoaded("HardcoreQuesting")) {
							final Team inviteTeam = getTeamByName(clowder.name);
							Team team = getTeam(name);
							int id = 0;
							String playerName = name;

							// invite
							PlayerEntry pE = team.getPlayers().get(0);
							pE = new PlayerEntry(playerName, false, false);
							inviteTeam.getPlayers().add(pE);
							inviteTeam.refreshTeamData(UpdateType.ONLY_MEMBERS);
							QuestingData.getQuestingData(pE.getName()).getTeam()
									.refreshTeamData(UpdateType.ONLY_MEMBERS);

							// accept
							for (int abcd = 0; abcd < inviteTeam.getPlayers().size(); abcd++) {
								PlayerEntry entry2 = inviteTeam.getPlayers().get(abcd);
								if (entry2.isInTeam()) {
									++id;
								} else {
									if (entry2.getName().equals(playerName)) {
										entry2.setBookOpen(team.getPlayers().get(0).isBookOpen());
										inviteTeam.getPlayers().remove(entry2);
										entry2 = new PlayerEntry(entry2.getName(), false, false);
										inviteTeam.getPlayers().add(entry2);
										QuestingData.getQuestingData(entry2.getName()).setTeam(inviteTeam);
										team.setId(inviteTeam.getId());
										for (int i = 0; i < inviteTeam.questData.size(); ++i) {
											final QuestData joinData = team.questData.get(i);
											final QuestData questData = inviteTeam.questData.get(i);
											if (questData != null) {
												final boolean[] old = questData.reward;
												questData.reward = new boolean[old.length + 1];
												for (int j = 0; j < questData.reward.length; ++j) {
													if (j == id) {
														questData.reward[j] = joinData.reward[0];
													} else if (j < id) {
														questData.reward[j] = old[j];
													} else {
														questData.reward[j] = old[j - 1];
													}
												}
											}
										}
										for (int i = 0; i < inviteTeam.questData.size(); ++i) {
											final QuestData joinData = team.questData.get(i);
											final QuestData questData = inviteTeam.questData.get(i);
											if (questData != null && Quest.getQuest(i) != null) {
												Quest.getQuest(i).mergeProgress(playerName, questData, joinData);
											}
										}
										for (int i = 0; i < Reputation.size(); ++i) {
											final Reputation reputation = Reputation.getReputation(i);
											if (reputation != null) {
												final int joinValue = team.getReputation(reputation);
												final int teamValue = inviteTeam.getReputation(reputation);
												int targetValue;
												if (Math.abs(joinValue) > Math.abs(teamValue)) {
													targetValue = joinValue;
												} else {
													targetValue = teamValue;
												}
												team.setReputation(reputation, targetValue);
											}
										}
										inviteTeam.refreshData();
										inviteTeam.refreshTeamData(UpdateType.ALL);
										inviteTeam.declineAll(playerName);
										if (QuestingData.getQuestingData(playerName) == null)
											System.out.println("questingdata is null");
										else if (QuestingData.getQuestingData(playerName).getTeam() == null)
											System.out.println("team is null");
										else if (QuestingData.getQuestingData(playerName).getTeam()
												.getEntry(playerName) == null)
											System.out.println("entry is null");
										else if (QuestingData.getQuestingData(playerName).getTeam().getEntry(playerName)
												.isBookOpen())
											System.out.println("book open");
										else
											System.out.println("book not open");
										// TeamStats.refreshTeam(team);
										if (!inviteTeam.players.contains(new PlayerEntry(name, true, false)))
											inviteTeam.players.add(new PlayerEntry(name, true, false));
									}
								}
							}
						}
						sender.addChatMessage(
								new ChatComponentText(INFO + "Added player " + name + " to your clowder!"));
						clowder.notifyPlayer(player.worldObj, name, new ChatComponentText(
								INFO + "You have been accepted into " + clowder.getDecoratedName() + "!"));
					} else {
						sender.addChatMessage(
								new ChatComponentText(ERROR + "This player is already in another clowder!"));
					}

					clowder.applications.remove(name);

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player has no active application!"));
				}

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to manage applications!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	public void cmdDeny(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				if (clowder.applications.contains(name)) {

					if (Clowder.getClowderFromName(name) == null) {
						sender.addChatMessage(new ChatComponentText(
								INFO + "Denied player " + sender.getCommandSenderName() + "'s application!"));
						// modid instead of modname
						if (Loader.isModLoaded("HardcoreQuesting")) {
							Team team = getTeamByName(name);
							if (team != null) {
								team.invite(name);
								Team team2 = getTeam(name);
								team2.decline();
							}
						}
					} else {
						sender.addChatMessage(
								new ChatComponentText(ERROR + "This player is already in another clowder!"));
					}

					clowder.applications.remove(name);

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player has no active application!"));
				}

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to manage applications!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdApplicants(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				sender.addChatMessage(new ChatComponentText(TITLE + "Applicants:"));
				int cnt = 0;

				for (String key : clowder.applications) {
					sender.addChatMessage(new ChatComponentText(LIST + "-" + key));
					cnt++;
				}

				if (cnt == 0)
					sender.addChatMessage(new ChatComponentText(LIST + "None!"));

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to manage applications!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdKick(ICommandSender sender, String kickee) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {
			
			//no escape for bitches
			if (!clowder.bitch) {

			if (!clowder.targeted) {

				if (clowder.getPermLevel(player.getDisplayName()) > 1) {

					if (clowder.members.get(kickee) != null) {

						if (player.getDisplayName().equals(kickee)) {

							sender.addChatMessage(
									new ChatComponentText(CRITICAL + "You can not kick yourself, idiot!"));

						} else {
							clowder.notifyPlayer(player.worldObj, kickee,
									new ChatComponentText(CRITICAL + "You have been kicked from your clowder!"));
							clowder.removeMember(player.worldObj, kickee);
							// modid instead of modname
							if (Loader.isModLoaded("HardcoreQuesting")) {
								Team team = getTeam(sender.getCommandSenderName());
								if (team != null) {
									team.removePlayer(kickee);
									team.refreshData();
								}
								team.refreshTeamData(UpdateType.ONLY_OWNER);
								QuestingData.getQuestingData(kickee).getTeam().refreshTeamData(UpdateType.ONLY_MEMBERS);
								team = getTeam(kickee);
								PlayerEntry a = team.getPlayers().get(0);
								team.getPlayers().remove(a);
								a = new PlayerEntry(kickee, false, false);
								team.getPlayers().add(a);
							}
							sender.addChatMessage(new ChatComponentText(INFO + "Kicked player " + kickee + "!"));
						}
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your clowder!"));
					}

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to kick members!"));
				}

			} else
				sender.addChatMessage(new ChatComponentText(
						ERROR + "You cannot kick members when you are being targeted by enemy war goals!"));
			} else
				sender.addChatMessage(new ChatComponentText(
						ERROR + "Bitches cannot kick clowder members!"));

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	
	private void cmdUnfriend(ICommandSender sender, String kickee) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		Clowder formerFriend = Clowder.getClowderFromName(kickee);

		if (clowder != null) {
			
			//no escape for bitches
			if (!clowder.bitch) {


				if (clowder.getPermLevel(player.getDisplayName()) > 1) {

					if (formerFriend != null) {

						if (clowder == formerFriend) {

							sender.addChatMessage(
									new ChatComponentText(CRITICAL + "You can not unfriend yourself, idiot!"));

						} else {
							formerFriend.notifyAll(player.worldObj, new ChatComponentText(INFO + clowder.name + " has cancelled our alliance!"));
							clowder.removeAlly(player.worldObj, kickee);
							formerFriend.removeAlly(player.worldObj, clowder.name);
							// modid instead of modname

							clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Friendship with " + kickee + " has ended!"));
						}
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This action does not exist."));
					}

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to cancel alliances!"));
				}


			} else
				sender.addChatMessage(new ChatComponentText(
						ERROR + "Bitches cannot perform diplomacy"));

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdFlag(ICommandSender sender, String flag) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				ClowderFlag f = ClowderFlag.getFromName(flag.toLowerCase());

				if (f != ClowderFlag.NONE) {

					clowder.flag = f;
					sender.addChatMessage(new ChatComponentText(INFO + "Changed flag to " + flag + "!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder, ""), (EntityPlayerMP) player);

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This flag does not exist!"));
				}

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to change this clowder's flag!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdSethome(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {
			// level 1 member level 2 officer level 3 leader
			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				Ownership owner = ClowderTerritory.getOwnerFromInts((int) player.posX, (int) player.posZ);

				if (owner != null && owner.zone == Zone.FACTION && owner.owner == clowder) {
					
					if (clowder.sethomeDelay <= 0)
					{
					clowder.setHome(player.posX, player.posY, player.posZ, player);
					clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Home set!"));
					clowder.addSethomeDelay(10, player.worldObj); //10 minute delay
					}
					else {
						sender.addChatMessage(
								new ChatComponentText(ERROR + "Please wait " + (int)clowder.sethomeDelay + " minutes to set home again!"));
					}
					
					
				} else {
					sender.addChatMessage(
							new ChatComponentText(ERROR + "You can not set the home outside of your claimed land!"));
				}

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to set this clowder's home point!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	private void cmdSetAllyWarp(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {
			// level 1 member level 2 officer level 3 leader
			if (clowder.getPermLevel(player.getDisplayName()) > 1) {

				Ownership owner = ClowderTerritory.getOwnerFromInts((int) player.posX, (int) player.posZ);

				if (owner != null && owner.zone == Zone.FACTION && owner.owner == clowder) {

					
					
					if (clowder.sethomeDelay <= 0)
					{
						clowder.setAllyWarp(player.posX, player.posY, player.posZ, player);
						clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Ally Warp set!"));
						clowder.addSethomeDelay(10, player.worldObj); //10 minute delay
					}
					else {
						sender.addChatMessage(
								new ChatComponentText(ERROR + "Please wait " + (int)clowder.sethomeDelay + " minutes to move the alliance rally-point!"));
					}
					
					
					
				} else {
					sender.addChatMessage(
							new ChatComponentText(ERROR + "You can not set the Ally Warp outside of your claimed land!"));
				}

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You lack the permissions to set this clowder's Ally Warp point!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdHome(ICommandSender sender) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			Ownership owner = ClowderTerritory.getOwnerFromInts((int) player.posX, (int) player.posZ);

			if (owner != null
					//&& (owner.zone == Zone.WARZONE || (owner.zone == Zone.FACTION && owner.owner != clowder))) {
				&& (owner.zone == Zone.WARZONE || (owner.zone == Zone.FACTION && (owner.owner != clowder && clowder.allies.get(owner.owner) == null) ) ) ) {  //allow warp from allied territory

				sender.addChatMessage(new ChatComponentText(ERROR + "You can not teleport home f territory!"));

			} else {

				sender.addChatMessage(new ChatComponentText(INFO + "Please stand still for 10 seconds!"));
				clowder.teleports.put(System.currentTimeMillis() + 10000L, new ScheduledTeleport(clowder.homeX,
						clowder.homeY, clowder.homeZ, player.getDisplayName(), true));

			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	private void cmdAllyWarp(ICommandSender sender, String name) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		Clowder ally = Clowder.getClowderFromName(name);

		if (clowder != null) {
			if (ally != null) {

			Ownership owner = ClowderTerritory.getOwnerFromInts((int) player.posX, (int) player.posZ);

			if (owner != null
					&& (owner.zone == Zone.WARZONE || (owner.zone == Zone.FACTION && (owner.owner != clowder && clowder.allies.get(owner.owner) == null) ) ) ) {

				System.out.println("alliesS get owner name: " + clowder.alliesS.get(owner.owner.name) + " owner.owner name: " + owner.owner.name);
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not teleport to an Ally Warp from unfriendly territory!"));

			} else 
			{
				//area where ally warp teleportation is executed. put ally restrictions here
				
				if(ally.allies.get(clowder) != null)
				{
					
					if(ally.allyWarpX != 0 && ally.allyWarpY != 0 && ally.allyWarpZ != 0)
					{
				sender.addChatMessage(new ChatComponentText(INFO + "Please stand still for 10 seconds!"));
				clowder.teleports.put(System.currentTimeMillis() + 10000L, new ScheduledTeleport(ally.allyWarpX,
						ally.allyWarpY, ally.allyWarpZ, player.getDisplayName(), true, true, ally.name));
					}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + name + " did not set an alliance rally-point!"));
				
				}
				else
					sender.addChatMessage(new ChatComponentText(ERROR + name + " is not our ally!"));
			}

		}
			
			else {
			sender.addChatMessage(new ChatComponentText(ERROR + name + " is not a valid clowder!"));
		}
		}
			else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	

	private void cmdAddWarp(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.warps.containsKey(name)) {
				sender.addChatMessage(new ChatComponentText(ERROR + "This warp already exists!"));
				return;
			}

			if (clowder.getPrestige() < MainRegistry.warpCost) {
				sender.addChatMessage(new ChatComponentText(
						ERROR + "You need at least " + MainRegistry.warpCost + " prestige to create a warp!"));
				return;
			}

			int code = clowder.tryAddWarp(player, (int) player.posX, (int) player.posY, (int) player.posZ, name);

			if (code == 0) {
				clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Created warp " + name + "!"));
				clowder.addPrestige(-MainRegistry.warpCost, player.worldObj);
				clowder.save(player.worldObj);
			} else if (code == 1) {
				sender.addChatMessage(new ChatComponentText(ERROR + "Cannot create warp outside of your territory!"));
			} else if (code == 2) {
				sender.addChatMessage(new ChatComponentText(ERROR + "No nearby warp tents!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdDelWarp(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.warps.containsKey(name)) {
				clowder.warps.remove(name);
				clowder.save(player.worldObj);
				sender.addChatMessage(new ChatComponentText(INFO + "Deleted warp!"));
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This warp does not exist!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdWarp(ICommandSender sender, String name) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.warps.containsKey(name)) {

				Ownership owner = ClowderTerritory.getOwnerFromInts((int) player.posX, (int) player.posZ);

				if (owner != null
						//&& (owner.zone == Zone.WARZONE || (owner.zone == Zone.FACTION && owner.owner != clowder))) {
					&& (owner.zone == Zone.WARZONE || (owner.zone == Zone.FACTION && (owner.owner != clowder && clowder.allies.get(owner.owner) == null) ) ) ) { //allow warp from allied territory

					sender.addChatMessage(new ChatComponentText(ERROR + "You can not warp in unfriendly territory!"));
					return;
				}

				int[] warp = clowder.warps.get(name);

				if (warp == null) {
					return;
				}

				IChunkProvider provider = player.worldObj.getChunkProvider();

				for (int i = 2; i <= 5; i++) {

					ForgeDirection dir = ForgeDirection.getOrientation(i);

					provider.loadChunk((warp[0] + dir.offsetX * 2) >> 4, (warp[2] + dir.offsetZ * 2) >> 4);

					int tentX = warp[0] + dir.offsetX * 2;
					int tentZ = warp[2] + dir.offsetZ * 2;

					Block block = player.worldObj.getBlock(tentX, warp[1], tentZ);

					if (block == ModBlocks.tp_tent) {

						int[] pos = ((BlockDummyable) ModBlocks.tp_tent).findCore(player.worldObj, tentX, warp[1],
								tentZ);

						if (pos != null) {

							provider.loadChunk(pos[0] >> 4, pos[2] >> 4);
							TileEntityProp tent = (TileEntityProp) player.worldObj.getTileEntity(pos[0], pos[1],
									pos[2]);

							if (tent.warp.equals(name) && tent.operational()) {

								sender.addChatMessage(
										new ChatComponentText(INFO + "Please stand still for 10 seconds!"));
								clowder.teleports.put(System.currentTimeMillis() + 10000L, new ScheduledTeleport(
										warp[0], warp[1], warp[2], player.getDisplayName(), name));

								return;
							}
						}
					}
				}

				sender.addChatMessage(new ChatComponentText(
						ERROR + "Warp tent not found! Make sure it still exists or remove this warp!"));

			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This warp does not exist!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdWarps(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + "Availible warps:"));

			for (String s : clowder.warps.keySet()) {
				int[] pos = clowder.warps.get(s);
				sender.addChatMessage(new ChatComponentText(LIST + s));
				sender.addChatMessage(new ChatComponentText(LIST + " x:" + pos[0] + " y:" + pos[1] + " z:" + pos[2]));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdBalance(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPrestige() > 0)
				sender.addChatMessage(
						new ChatComponentText(INFO + "Current prestige balance: " + LIST + clowder.getPrestige()));
			else
				sender.addChatMessage(new ChatComponentText(INFO + "It seems like you're bankrupt."));

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdDeposit(ICommandSender sender, String a) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		int amount = parseInt(sender, a);

		if (clowder != null) {

			if (amount <= 0) {
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot deposit 0 or less prestige!"));
				return;
			}

			for (int i = 0; i < amount; i++) {

				if (player.inventory.hasItem(ModItems.province_point)) {
					player.inventory.consumeInventoryItem(ModItems.province_point);
					clowder.addPrestige(1, player.worldObj);
				} else {
					sender.addChatMessage(new ChatComponentText(INFO + "Deposited " + i + " prestige!"));
					clowder.save(player.worldObj);
					player.inventoryContainer.detectAndSendChanges();
					return;
				}
			}

			sender.addChatMessage(new ChatComponentText(INFO + "Deposited " + amount + " prestige!"));
			clowder.save(player.worldObj);
			player.inventoryContainer.detectAndSendChanges();

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdWithdraw(ICommandSender sender, String a) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		int amount = parseInt(sender, a);

		if (clowder != null) {

			if (amount <= 0) {
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot withdraw 0 or less prestige!"));
				return;
			}

			amount = Math.min(amount, (int) clowder.getPrestige());

			if (clowder.getPrestige() == 0) {
				sender.addChatMessage(new ChatComponentText(INFO + "It seems like you're bankrupt."));
				return;
			}

			clowder.addPrestige(-1, player.worldObj);

			for (int i = 0; i < amount; i++) {

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.province_point))) {
					sender.addChatMessage(new ChatComponentText(INFO + "Withdrew " + i + " prestige!"));
					clowder.save(player.worldObj);
					player.inventoryContainer.detectAndSendChanges();
					return;
				}
			}

			sender.addChatMessage(new ChatComponentText(INFO + "Withdrew " + amount + " prestige!"));
			clowder.save(player.worldObj);
			player.inventoryContainer.detectAndSendChanges();

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	private void cmdListflags(ICommandSender sender, String page) {

		int fpp = 20;

		int p = this.parseInt(sender, page);
		int pages = (int) Math.ceil(((double) ClowderFlag.getFlags().size()) / fpp);

		if (p < 1 || p > pages)
			p = 1;

		sender.addChatMessage(new ChatComponentText(TITLE + "[" + p + "/" + pages + "] List of available flags:"));

		for (int i = (p - 1) * fpp; (i < p * fpp) && (i < ClowderFlag.values().length); i++) {
			if (ClowderFlag.values()[i].show)
				sender.addChatMessage(new ChatComponentText(LIST + "-" + ClowderFlag.values()[i].name));
		}

	}

	private void cmdClaim(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		if (clowder != null) {
			if(!clowder.bitch)
			{
			if (clowder.getPermLevel(player.getDisplayName()) > 1) {
				TerritoryMeta meta = ClowderTerritory.territories
						.get(ClowderTerritory.coordsToCode(new CoordPair(player.chunkCoordX, player.chunkCoordZ)));
				if (meta != null) {
					if (meta.owner.zone == Zone.WILDERNESS
							|| (meta.owner.zone == Zone.FACTION && meta.owner.owner.getPrestige() <= 0
									&& meta.owner.owner.getPrestigeGen() - meta.owner.owner.getPrestigeReq() < 0)) {
						TileEntity te = sender.getEntityWorld().getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
						if (te != null && te instanceof TileEntityFlagBig) {
							TileEntityFlagBig flag = (TileEntityFlagBig) te;
							if (insideBorders(new CoordPair(flag.xCoord / 16, flag.zCoord / 16))) {
								if (clowder.getPrestige() >= flag.getCost()) {
									// Handling prestige
									clowder.addPrestige((float) (-flag.getCost()), sender.getEntityWorld());
									clowder.addPrestigeReq((float) flag.getCost(), sender.getEntityWorld());

									Ownership oldOwner = meta.owner;
									// Setting the owner of the flag and the chunks, making the flag cappable
									flag.owner = clowder;
									flag.markDirty();
									for (CoordPair a : flag.claim)
										ClowderTerritory.setOwnerForCoord(sender.getEntityWorld(), a, clowder,
												flag.xCoord, flag.yCoord, flag.zCoord, flag.provinceName);
									flag.isCappable = true;
									
									MinecraftForge.EVENT_BUS.post(new RegionOwnershipChangedEvent(oldOwner,meta.owner,flag.provinceName));
									
									flag.markDirty();
									ClowderData.getData(sender.getEntityWorld()).markDirty();
								} else {
									sender.addChatMessage(new ChatComponentText(ERROR
											+ "You already claimed to your capacity. Get more prestige and make sure you have enough to maintain your claims!"));
								}
							} else {
								sender.addChatMessage(new ChatComponentText(ERROR + "This province is out of bounds."));
							}
						} else {
							sender.addChatMessage(
									new ChatComponentText(ERROR + "Wait.. there is no flag! Let an admin know!"));
						}
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "You cannot claim here"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR
							+ "You are not standing in any region (most likely you are in the ocean or out of the map)"));
				}
			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "Your authority is not high enough to manage territory"));}
			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "Bitches cannot claim land"));}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder"));
		}
	}

	private void cmdUnclaim(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		if (clowder != null) {
			//bitches cannot escape
			if (!clowder.bitch)
			{
			if (clowder.getPermLevel(player.getDisplayName()) > 1) {
				TerritoryMeta meta = ClowderTerritory.territories
						.get(ClowderTerritory.coordsToCode(new CoordPair(player.chunkCoordX, player.chunkCoordZ)));
				if (meta != null && meta.owner != new Ownership(Zone.WILDERNESS)) {
					boolean warDeclared = (clowder.getWartime() > 0
							|| (clowder.enemy != null && clowder.enemy.getWartime() > 0) || clowder.targeted);
					if (!warDeclared || meta.owner.owner != clowder) {
						if (meta.owner.zone == Zone.FACTION
								&& (meta.owner.owner == clowder || meta.owner.owner.getPrestige() <= 0)) {
							TileEntity te = sender.getEntityWorld().getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
							if (te != null && te instanceof TileEntityFlagBig) {
								TileEntityFlagBig flag = (TileEntityFlagBig) te;

								// Handling prestige
								clowder.addPrestigeReq((float) -flag.getCost(), sender.getEntityWorld());
								
								// Setting the owner of the flag and the chunks, making the flag uncappable
								flag.owner = null;

								Ownership to = new Ownership(Zone.WILDERNESS);
								
								//Posting an event so the
								MinecraftForge.EVENT_BUS.post(new RegionOwnershipChangedEvent(meta.owner, to, flag.provinceName));
								
								for (CoordPair a : flag.claim) {
									ClowderTerritory.setWildernessForCoord(sender.getEntityWorld(), a);
								}

								flag.isCappable = false;

								flag.markDirty();
								ClowderData.getData(sender.getEntityWorld()).markDirty();
							} else {
								sender.addChatMessage(
										new ChatComponentText(ERROR + "Wait.. there is no flag! Let an admin know!"));
							}
						} else {
							sender.addChatMessage(new ChatComponentText(ERROR + "You cannot unclaim here"));
						}
					} else {
						sender.addChatMessage(new ChatComponentText(
								ERROR + "You cannot unclaim your own regions while at war you moron"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "Why would you unclaim wilderness?"));
				}
			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "Your authority is not high enough to manage territory"));
			}
		}
			else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Bitches cannot unclaim. Wouldn't want you transfering land to an alt!"));}
		}else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder"));
		}
	}

	private void cmdPromote(ICommandSender sender, String promotee) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 2) {

				if (clowder.members.get(promotee) != null) {

					if (clowder.getPermLevel(promotee) == 1) {

						clowder.promote(player.worldObj, promotee);

					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is already promoted!"));
					}

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your clowder!"));
				}

			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to promote members!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}
	
	
	//for tping to transport vehicles
	private void cmdDisembark(ICommandSender sender, String driverName) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		EntityPlayer driver = player.worldObj.getPlayerEntityByName(driverName);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {


			if (driver != null)
			{
			
				if (clowder.members.get(driverName) != null) {
					
					
					boolean vehicleDetected = false;
						 for (int i = 0; i < player.inventory.mainInventory.length; ++i) //check if you have a vehicle. Do not allow teleporting to a vehicle if you brought more vehicles (prevents infinite warps)
					        {
					            if (player.inventory.mainInventory[i] != null && ( (player.inventory.mainInventory[i].getItem() instanceof ItemPlane) || (player.inventory.mainInventory[i].getItem() instanceof ItemVehicle) || (player.inventory.mainInventory[i].getItem() instanceof ItemMecha) ))
					            {
					            	vehicleDetected = true;
					            }
					        }
					
						 if(!vehicleDetected)
						 {
					
					if (driver.ridingEntity != null && driver.ridingEntity instanceof EntitySeat)
					{
						
					
					EntityDriveable vehicle = ((EntitySeat)(driver.ridingEntity)).driveable;
					Integer WarpLimit = vehicle.driveableData.WarpLimit;
					
					

					if (vehicle.getDriveableType().transport && vehicle != null && WarpLimit != null) {

						if(WarpLimit > 0)
						{
						MinecraftServer.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "tp " + player.getDisplayName() + " " + driverName);
						//driver.addChatMessage(new ChatComponentText(ERROR + player.getDisplayName() + " is disembarking"));
						clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + player.getDisplayName() + " is disembarking from " + driverName + "'s transport!"));
						vehicle.driveableData.WarpLimit -= 1; //take away from limit
						}
					 else 
						sender.addChatMessage(new ChatComponentText(ERROR + "This vehicle's warp limit has been exceeded! Try again later or try another transport!"));

					} else 
						sender.addChatMessage(new ChatComponentText(ERROR + driverName + " is not driving a valid transport vehicle!"));
					
					
				} else 
					sender.addChatMessage(new ChatComponentText(ERROR + driverName + " is not driving a vehicle!"));
					
						 } else 
								sender.addChatMessage(new ChatComponentText(ERROR + "You may NOT disembark when a vehicle is in your inventory!"));
					
				} else 
				sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your clowder!"));
				


				
			} else 
				sender.addChatMessage(new ChatComponentText(ERROR + driverName + " does not exist?"));

			

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	
	private void cmdDemote(ICommandSender sender, String demotee) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if (clowder.getPermLevel(player.getDisplayName()) > 2) {

				if (clowder.members.get(demotee) != null) {

					if (demotee.equals(player.getDisplayName())) {
						sender.addChatMessage(new ChatComponentText(ERROR + "You can't demote yourself!"));
						return;
					}

					if (clowder.getPermLevel(demotee) == 2) {

						clowder.demote(player.worldObj, demotee);

					} else if (clowder.getPermLevel(demotee) != 3) {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is already demoted!"));
					} else {
						sender.addChatMessage(new ChatComponentText(
								ERROR + "Are you seriously trying to demote the clowder's leader?"));
					}

				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your clowder!"));
				}

			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to demote members!"));
			}

		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any clowder!"));
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}

	public static final String ERROR = EnumChatFormatting.RED.toString();
	public static final String CRITICAL = EnumChatFormatting.DARK_RED.toString();
	public static final String TITLE = EnumChatFormatting.GOLD.toString();
	public static final String LIST = EnumChatFormatting.BLUE.toString();
	public static final String HELP = EnumChatFormatting.DARK_GREEN.toString();
	public static final String INFO = EnumChatFormatting.GREEN.toString();
	public static final String COMMAND = EnumChatFormatting.RED.toString();
	public static final String COMMAND_LEADER = EnumChatFormatting.DARK_RED.toString();
	public static final String COMMAND_ADMIN = EnumChatFormatting.DARK_PURPLE.toString();
}
