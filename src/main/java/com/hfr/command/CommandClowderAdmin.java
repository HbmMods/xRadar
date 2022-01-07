package com.hfr.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dynmap.forge.DynmapPlugin;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.clowder.events.ClowderPropertyChangedEvent;
import com.hfr.data.ClowderData;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderFlagPacket;
import com.hfr.tileentity.clowder.TileEntityFlagBig;
import com.hfr.util.ParserUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommandClowderAdmin extends CommandBase {

	@Override
	public String getCommandName() {
		return "xclowder";
	}

	@Override
    public List getCommandAliases()
    {
        return new ArrayList() {{ add("xclowder"); add("xc"); }};
    }

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/xclowder help";
	}
	
    public int getRequiredPermissionLevel()
    {
        return 3;
    }
    
    @Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(sender.getEntityWorld().provider.dimensionId != 0) {
			sender.addChatMessage(new ChatComponentText(CRITICAL + "Critical error: CatFac only works in overworld!!"));
		}
		
		if(Clowder.clowders.size() == 0)
			ClowderData.getData(sender.getEntityWorld());
		
		if(args.length < 1) {
			sender.addChatMessage(new ChatComponentText(ERROR + getCommandUsage(sender)));
			return;
		}
		
		String cmd = args[0].toLowerCase();
		switch(cmd) {
		
		case "retreatban":{
			if(args.length > 2)
				cmdRetreatBan(sender, args[1], args[2]);
		}
		
		case "rename": {
			if(args.length > 1) {
				cmdRename(sender, args[1]);
			}
		}break;
		
		case "man":
		case "help": {
			if(args.length > 1) {
				cmdHelp(sender, args[1]);
			} else
				cmdHelp(sender, "1");
		}break;
		
		case "color": {
			if(args.length > 1) {
				cmdColor(sender, args[1]);
			}
		}break;
		
		case "fj":
		case "forcejoin": {
			if(args.length > 1) {
				cmdForcejoin(sender, args[1]);
			}
		}break;
		
		case "lib":
		case "liberate": {
			if(args.length > 1) {
				cmdLiberate(sender, args[1]);
			}
		}break;
		
		case "fd":
		case "forcedisband": {
			if(args.length > 1) {
				cmdForcedisband(sender, args[1]);
			}
		}break;
		
		case "hi":
		case "hijack": {
			cmdHijack(sender);
		}break;
		
		case "updateprestige": {
			cmdUpdatePrestige(sender);
		}break;
		
		case "updatewar": {
			cmdUpdateWar(sender);
		}break;
		
		case "updatewarten": {
			cmdUpdateWarTen(sender);
		}break;

		case "deldat":
		case "deletedata": {
			cmdDeletedata(sender);
		}break;
		
		case "sc":
		case "setclaim": {
			if(args.length > 3) {
				cmdSetclaim(sender, args[1], args[2], args[3]);
			}
		}break;
		
		case "ap":
		case "addprestige": {
			if(args.length > 2) {
				cmdAddPrestige(sender, args[1], args[2]);
			}
		}break;
		
		case "ag":
		case "addgen": {
			if(args.length > 2) {
				cmdAddGen(sender, args[1], args[2]);
			}
		}break;
		
		case "fv":
		case "forcevassal": {
			if(args.length > 2) {
				cmdForceVassal(sender, args[1], args[2]);
			}
		}break;
		
		case "fw":
		case "forcewar": {
			if(args.length > 2) {
				cmdForceWar(sender, args[1], args[2]);
			}
		}break;
		
		case "xcolor": {
			if(args.length > 2) {
				cmdColor(sender, args[1], args[2]);
			}
		}break;
		

		case "pussy": {
			if(args.length > 1) {
				cmdPussy(sender, args[1]);
			}
		}break;
		
		case "forceonline": {
			if(args.length > 1) {
				cmdForceOnline(sender, args[1]);
			}
		}break;
		
		case "treatybreak": {
			if(args.length > 1) {
				cmdTreatyBreak(sender, args[1]);
			}
		}break;
		
		
		case "warday": {
				cmdTotalenKrieg(sender);
			}
		break;
		
		case "pacify": {
			cmdBritain(sender);
		}
	break;
	
		case "britain": {
			cmdGreatBritain(sender);
		}
	break;
		
		case "info": {
			if(args.length > 1) {
				cmdInfo(sender, args[1]);
			} else
				cmdInfo(sender, null);
		}break;
		
		default: {
			sender.addChatMessage(new ChatComponentText(ERROR + getCommandUsage(sender)));
		}break;
		
		//Template
//		case "rename": {
//			if(args.length > 1) {
//				
//			}
//		}break;
		}
	}
	
    private void cmdRetreatBan(ICommandSender sender, String nameFac, String timeS) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(nameFac);
		int time = this.parseInt(sender, timeS);
		
		if(time >= 0) {
			clowder.retreatBan = time;
			sender.addChatMessage(new ChatComponentText(ERROR + nameFac + "'s retreat ban has been set to " + time + " minutes!"));
			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid clowder name!"));
		}
	}
    
    private void cmdRename(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
				
			if(Clowder.getClowderFromName(name) == null) 
			{
				//xddd oops got it backwards
				if(clowder.suzerain == null)
				{
				
				
				if(clowder.getPrestige() > MainRegistry.renameCost) {
					

				if(clowder.getPermLevel(player.getDisplayName()) > 1) {
					clowder.addPrestige(-MainRegistry.renameCost, player.worldObj);
					
					
					MinecraftForge.EVENT_BUS.post(new ClowderPropertyChangedEvent(player.worldObj, clowder, clowder.name, name));
					
					clowder.rename(name, player);
					clowder.save(player.worldObj);
					
					sender.addChatMessage(new ChatComponentText(TITLE + "Renamed faction to " + name + " for " + MainRegistry.renameCost + " prestige!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder, ""), (EntityPlayerMP) player);
				} else 
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to rename this faction!"));
				
				
			}
				else 
					sender.addChatMessage(new ChatComponentText(ERROR + "You need at least " + MainRegistry.renameCost + " prestige to change the clowder name!"));
				
				
			}
				else 
					sender.addChatMessage(new ChatComponentText(ERROR + "Tributaries cannot change their name"));
				

			}
				else 
				sender.addChatMessage(new ChatComponentText(ERROR + "This name is already taken!"));
			
			
		} else 
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		
	}
    
	private void cmdHelp(ICommandSender sender, String page) {
		
		int p = this.parseInt(sender, page);
		int pages = 2;
		
		if(p < 1 || p > pages)
			p = 1;

		sender.addChatMessage(new ChatComponentText(HELP + "/clowder [command] <args...> {optional args...}"));
		sender.addChatMessage(new ChatComponentText(INFO + "Commands [" + p + "/" + pages + "]:"));
		
		if(p == 1) {
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-forcejoin <name>" + TITLE + " - Forcefully joins a faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-forcekick <name>" + TITLE + " - Forcefully kicks a player from his faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-forcedisband <name>" + TITLE + " - Forcefully disbands faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-hijack" + TITLE + " - Forcefully overrides leadership"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-deletedata" + TITLE + " - Deletes all clowder data (CAUTION!!)"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-setclaim <wild/safe/war> <s/c> <radius>" + TITLE + " - Claims chunks in a radius (square or circular)"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-addprestige <name> <amount>" + TITLE + " - Adds prestige (neg values to subtract)"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-create <name>" + TITLE + " - Creates a faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-disband <name>" + TITLE + " - Disbands a faction, name parameter for confirmation"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-rename <name>" + TITLE + " - Renames your faction"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 2")); 
		}

		if(p == 2) {
			//the labjac page
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-updateprestige" + TITLE + " - Forces an hourly prestige update to happen now"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-updatewar" + TITLE + " - Forces a one minute war update to happen now"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-updatewarten" + TITLE + " - Forces ten minute war/declaration time skip"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-forcevassal <name1> <name2>" + TITLE + " - Clowder 2 becomes the vassal of clowder 1"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-liberate <name>" + TITLE + " - Liberates factions that are vassals"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-forcewar <name1> <name2>" + TITLE + " - Clowder 1 declares war agianst clowder 2"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-xcolor <name> <hexadecimal>" + TITLE + " - Sets the faction's color"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-pussy <name>" + TITLE + " - Cancels faction's wars or fabrications"));
			sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-treatybreak <name>" + TITLE + " - Wipes faction's treaties from fabrication attempts"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-info {page}" + TITLE + " - Shows debug info on a faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-warday" + TITLE + " - Toggles total war mode"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-britain" + TITLE + " - Toggles world peace mode"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-pacify" + TITLE + " - Cancels all wars and fabrications in the world at once"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-forceonline <name>" + TITLE + " - Forces factions to be online/offline"));
			//sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-info" + TITLE + " - Shows info on your faction"));
		}
	}
	
	//forces a one hour prestige update for debug purpose
	private void cmdUpdatePrestige(ICommandSender sender) 
	{
		sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "Forced an hourly prestige update!"));
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder.updatePrestige(player.worldObj);
	}
	
	//forces a one minute war update for debug purpose
	private void cmdUpdateWar(ICommandSender sender) 
	{
		sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "Forced a 1 minute war/declaration/fabrication update!"));
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder.updateWars(player.worldObj);
	}
	
	//forces a ten minute war update for debug purpose
	private void cmdUpdateWarTen(ICommandSender sender) 
	{
		sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "Forced a 10 minute war/declaration/fabrication update!"));
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
		Clowder.updateWars(player.worldObj);
	}
	
	//debug info
	private void cmdInfo(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		
		Clowder clowder = name == null ? Clowder.getClowderFromPlayer(player) : Clowder.getClowderFromName(name);
		
		if(clowder != null) {
			
			

			
			//for memory nbt debug
			sender.addChatMessage(new ChatComponentText(LIST + "suzerain string: " + clowder.suzerainS));
			sender.addChatMessage(new ChatComponentText(LIST + "enemy string: " + clowder.enemyS));
			
			//for tributary
			if (clowder.suzerain != null)
			{
				sender.addChatMessage(new ChatComponentText(LIST + "Tributary of: " + clowder.suzerain.name));
				sender.addChatMessage(new ChatComponentText(LIST + "Due to being a tributary, 1/2 of Prestige generation is lost. 1/4 goes to " + clowder.suzerain.name));
			}
			

			sender.addChatMessage(new ChatComponentText("treaty 1: " + clowder.treaty1 + " " + clowder.treatyTime1 + " minutes"));
			sender.addChatMessage(new ChatComponentText("treaty 2: " + clowder.treaty2 + " " + clowder.treatyTime2 + " minutes"));
			sender.addChatMessage(new ChatComponentText("treaty 3: " + clowder.treaty3 + " " + clowder.treatyTime3 + " minutes"));
			sender.addChatMessage(new ChatComponentText("treaty 4: " + clowder.treaty4 + " " + clowder.treatyTime4 + " minutes"));
			sender.addChatMessage(new ChatComponentText("treaty 5: " + clowder.treaty5 + " " + clowder.treatyTime5 + " minutes"));
			sender.addChatMessage(new ChatComponentText("treaty 6: " + clowder.treaty6 + " " + clowder.treatyTime6 + " minutes"));
			sender.addChatMessage(new ChatComponentText("treaty 7: " + clowder.treaty7 + " " + clowder.treatyTime7 + " minutes"));
			sender.addChatMessage(new ChatComponentText("retreatban: " + clowder.retreatBan +  " minutes"));
			sender.addChatMessage(new ChatComponentText("targeted? " + clowder.targeted ));
			
			
	
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	
	
	private void cmdForcejoin(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder == null) {
			
			Clowder tojoin = Clowder.getClowderFromName(name);
				
			if(tojoin != null) {

				tojoin.addMember(player.worldObj, player.getDisplayName());
				sender.addChatMessage(new ChatComponentText(INFO + "You have joined " + tojoin.getDecoratedName() + "!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no faction with this name!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are already in a faction!"));
		}
	}
	
	
	private void cmdLiberate(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
	
			Clowder tofree = Clowder.getClowderFromName(name);
				
			if(tofree != null) 
			{
				if(tofree.bitch)
					tofree.bitch = false;
				if(tofree.suzerain != null)
				{
				tofree.suzerain = null;
				tofree.suzerainS = "nobody2584369";			
				ClowderData.getData(player.worldObj).markDirty();
				sender.addChatMessage(new ChatComponentText(INFO + tofree.name + " is now independent!"));	
				}
				else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This faction is not a vassal!"));
				}
				
			} 
			
			else {
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no faction with this name!"));
			}		
	}
	
	
	private void cmdForcekick(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		EntityPlayer kickee = player.worldObj.getPlayerEntityByName(name);
		Clowder clowder = Clowder.getClowderFromPlayer(kickee);
		
		if(clowder != null) {
			
			if(!clowder.leader.equals(kickee.getDisplayName())) {
				
				clowder.removeMember(player.worldObj, kickee.getDisplayName());
				sender.addChatMessage(new ChatComponentText(INFO + "You have kicked " + kickee.getDisplayName() + " from the faction " + clowder.getDecoratedName() + "!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot kick a leader from his faction!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in a faction!"));
		}
	}
	
	private void cmdForcedisband(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(name);
		
		if(clowder != null) {
			
			clowder.disbandClowder(player.worldObj);
			sender.addChatMessage(new ChatComponentText(INFO + "Faction " + clowder.getDecoratedName() + " has been disbanded!"));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "There is no faction with this name!"));
		}
	}
	
	private void cmdHijack(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(!clowder.leader.equals(player.getDisplayName())) {
				
				clowder.transferOwnership(player.worldObj, player.getDisplayName());
				sender.addChatMessage(new ChatComponentText(INFO + "You have assumed ownership of this faction!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are already this faction's leader!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdDeletedata(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);

		ClowderTerritory.territories.clear();
		
		while(Clowder.clowders.size() > 0)
			Clowder.clowders.get(0).disbandClowder(player.worldObj);
		
		Clowder.inverseMap.clear();
		Clowder.retreating.clear();
		ClowderData.getData(player.worldObj).markDirty();
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.OBFUSCATED + "" + EnumChatFormatting.DARK_PURPLE + "All data has been deleted!"));
	}
	
	private void cmdSetclaim(ICommandSender sender, String zo, String s, String r) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Zone zone = zo.equals("war") ? Zone.WARZONE : zo.equals("safe") ? Zone.SAFEZONE : zo.equals("wild") ? Zone.WILDERNESS : null;
		int shape = s.equals("s") ? 0 : s.equals("c") ? 1 : -1;
		int radius = this.parseInt(sender, r);

		int xCoord = (int)player.posX;
		int zCoord = (int)player.posZ;
		
		if(zone != null) {
			
			if(shape >= 0) {
				
				if(shape == 0)
					radius--;
				
				if(radius < 0 || radius > 25) {
					sender.addChatMessage(new ChatComponentText(ERROR + "Invalid radius! Must be between 1 and 25"));
				} else {
					
					int count = 0;
					
					for(int x = -radius; x <= radius; x++) {
						for(int z = -radius; z <= radius; z++) {

							int posX = xCoord + x * 16;
							int posZ = zCoord + z * 16;
							CoordPair loc = ClowderTerritory.getCoordPair(posX, posZ);
							
							if(shape == 0 || Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) < radius) {
								ClowderTerritory.setZoneForCoord(player.worldObj, loc, zone);
								count++;
							}
						}
					}
					
					sender.addChatMessage(new ChatComponentText(INFO + "Changed " + count + " chunks to " + zone.toString() + "!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "Invalid shape! Applicable: s (square), c (circle)"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid zone! Applicable: wild, safe, war"));
		}
	}
	
	private void cmdAddPrestige(ICommandSender sender, String name, String amount) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(name);
		int am = this.parseInt(sender, amount);
		
		if(clowder != null) {
			
			clowder.addPrestige(am, player.worldObj);
			sender.addChatMessage(new ChatComponentText(INFO + "Added " + am + " prestige to faction " + clowder.getDecoratedName() + "!"));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "There is no faction with this name!"));
		}
	}
	
	private void cmdAddGen(ICommandSender sender, String name, String amount) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(name);
		int am = this.parseInt(sender, amount);
		
		if(clowder != null) {
			
			clowder.addPrestigeGen(am, player.worldObj);
			sender.addChatMessage(new ChatComponentText(INFO + "Added " + am + " prestige generation to faction " + clowder.getDecoratedName() + "!"));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "There is no faction with this name!"));
		}
	}
	
	
	private void cmdForceVassal(ICommandSender sender, String nameMaster, String nameVictim) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder Master = Clowder.getClowderFromName(nameMaster);
		Clowder Victim = Clowder.getClowderFromName(nameVictim);
		
		if(Master != null && Victim != null && Master.suzerain == null) {
			Victim.save(player.worldObj);
			Victim.vassalize(player.worldObj, nameMaster, nameVictim);
			sender.addChatMessage(new ChatComponentText(INFO + Victim.getDecoratedName() + " is now the vassal of " + Master.getDecoratedName() + "!"));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid factions or victim already is a vassal!"));
		}
	}
	
	
	private void cmdColor(ICommandSender sender, String nameFac, String color) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(nameFac);
		
		if(clowder != null) {

			
				int c = ParserUtil.parseColor(color);
				
				if(c < 0) {
					sender.addChatMessage(new ChatComponentText(ERROR + "Incorrect color format! Use Hexadecimals like FF0000!"));
				} else {
					clowder.setColor(c, player);
					sender.addChatMessage(new ChatComponentText(INFO + "Set faction color to " + color + "!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder, ""), (EntityPlayerMP) player);
				}
			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid clowder name!"));
		}
	}
	
	  
    private void cmdColor(ICommandSender sender, String color) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);

		if (clowder != null) {

			if(clowder.canChangeColour) {
				if (clowder.getPermLevel(player.getDisplayName()) > 1) {
					int c = ParserUtil.parseColor(color);
					if (!Clowder.colours.contains(c)) {
						if (c < 0) {
							sender.addChatMessage(new ChatComponentText(ERROR + "Incorrect color format!"));
						} else {
							MinecraftForge.EVENT_BUS.post(new ClowderPropertyChangedEvent(player.worldObj, clowder, clowder.color, c));
							clowder.setColor(c, player);
							sender.addChatMessage(new ChatComponentText(INFO + "Set faction color to " + color + "!"));
							PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder, ""), (EntityPlayerMP) player);
							clowder.canChangeColour = false;
							World world = sender.getEntityWorld();
						}
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This colour is taken! Choose another one!"));
					}

				} else {
					sender.addChatMessage(
							new ChatComponentText(ERROR + "You lack the permissions to change this factiion's color!"));
				}

			} else {
				sender.addChatMessage(
						new ChatComponentText(ERROR + "You have already changed your colour! You can only change your colour once every 24 hours"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdPussy(ICommandSender sender, String nameFac) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(nameFac);
		
		if(clowder != null) {
			clowder.pussy(player.worldObj);
			clowder.targeted = false;
			sender.addChatMessage(new ChatComponentText(ERROR + "War stuff wiped!"));
			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid clowder name!"));
		}
	}
	
	
	private void cmdForceOnline(ICommandSender sender, String nameFac) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(nameFac);
		
		if(clowder != null) {
			if(clowder.forceOnline)
			{
				clowder.forceOnline = false;
				sender.addChatMessage(new ChatComponentText(ERROR + clowder.name + " is no longer forced to be online!"));
			}
			else if(!clowder.forceOnline)
			{
				clowder.forceOnline = true;
				sender.addChatMessage(new ChatComponentText(ERROR + clowder.name + " was set to be raidable by force!"));
			}
			
			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid clowder name!"));
		}
	}
	
	
	private void cmdTreatyBreak(ICommandSender sender, String nameFac) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromName(nameFac);
		
		
		
		if(clowder != null) {
			
			//also clears vassal/bitch treaties
			if(clowder.getPeaceTreaty() > 0)
				clowder.addPeaceTreaty(-clowder.getPeaceTreaty(), player.worldObj);
			
			
			clowder.treaty1 = "nobody2584369";
			clowder.treatyTime1 = 0;
			clowder.treaty2 = "nobody2584369";
			clowder.treatyTime2 = 0;
			clowder.treaty3 = "nobody2584369";
			clowder.treatyTime3 = 0;
			clowder.treaty4 = "nobody2584369";
			clowder.treatyTime4 = 0;
			clowder.treaty5 = "nobody2584369";
			clowder.treatyTime5 = 0;
			clowder.treaty6 = "nobody2584369";
			clowder.treatyTime6 = 0;
			clowder.treaty7 = "nobody2584369";
			clowder.treatyTime7 = 0;
			sender.addChatMessage(new ChatComponentText(ERROR + "treaty stuff wiped!"));
			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid clowder name!"));
		}
	}
	
	private void cmdTotalenKrieg(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(!clowder.totalenKrieg)
			{
				clowder.totalenKrieg = true;
				sender.addChatMessage(new ChatComponentText(LIST + "You have set the world to total war mode!"));	
			}
			
			else if(clowder.totalenKrieg)
			{
				sender.addChatMessage(new ChatComponentText(LIST + "You have disabled total war mode world-wide!"));
				clowder.totalenKrieg = false;
			}
			
			for(Clowder everybody : clowder.clowders)
			{
				if(clowder.totalenKrieg )
				{
					
					player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
					everybody.totalenKrieg = true;
					everybody.notifyAll(player.worldObj, new ChatComponentText(TITLE + "An admin set the world to total war mode!"));
				}
				else if(!clowder.totalenKrieg )
				{
				
					clowder.totalenKrieg = false;
					everybody.totalenKrieg = false;
					everybody.notifyAll(player.worldObj, new ChatComponentText(TITLE + "An admin disabled total war mode!"));
				}
			}
			
			//sender.addChatMessage(new ChatComponentText(ERROR + "treaty stuff wiped!"));
			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You have to be in a clowder so command can access list of all clowders"));
		}
	}
	
	//its because we admire the peace and prosperity of the british isles, no other reason!
	private void cmdBritain(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) 
		
		{
			
			sender.addChatMessage(new ChatComponentText(ERROR + "War stuff wiped!"));
			
			for(Clowder everybody : clowder.clowders)
			{
				if(everybody.getWartime() > 0 || everybody.getCanDeclareTime() > 0 || everybody.getFabricatetime() > 0)
				{
					everybody.pussy(player.worldObj);
					everybody.targeted = false;
					everybody.notifyAll(player.worldObj, new ChatComponentText(TITLE + "An admin wiped all war stuff at once!"));
					sender.addChatMessage(new ChatComponentText(TITLE + everybody.name + " was pacified!"));
				}
			}
			

			} 
			
		 else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You have to be in a clowder so command can access list of all clowders"));
		}
	}
	
		private void cmdGreatBritain(ICommandSender sender) {

			EntityPlayer player = getCommandSenderAsPlayer(sender);
			Clowder clowder = Clowder.getClowderFromPlayer(player);
			
			if(clowder != null) {
				
				if(!clowder.paxBritannica)
				{
					clowder.paxBritannica = true;
					sender.addChatMessage(new ChatComponentText(LIST + "You have set the world to total peace mode!"));	
				}
				
				else if(clowder.paxBritannica)
				{
					sender.addChatMessage(new ChatComponentText(LIST + "You have disabled total peace mode world-wide!"));
					clowder.paxBritannica = false;
				}
				
				for(Clowder everybody : clowder.clowders)
				{
					if(clowder.paxBritannica )
					{
						
						player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiNotification", 2.0F, 1.0F);
						everybody.paxBritannica = true;
						everybody.notifyAll(player.worldObj, new ChatComponentText(TITLE + "An admin set the world to total peace mode!"));
					}
					else if(!clowder.paxBritannica )
					{
					
						clowder.paxBritannica = false;
						everybody.paxBritannica = false;
						everybody.notifyAll(player.worldObj, new ChatComponentText(TITLE + "An admin disabled total peace mode!"));
					}
				}
				
				//sender.addChatMessage(new ChatComponentText(ERROR + "treaty stuff wiped!"));
				} 
				
			 else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You have to be in a clowder so command can access list of all clowders"));
			}
		}
	
	
	private void cmdForceWar(ICommandSender sender, String nameMaster, String nameVictim) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder Master = Clowder.getClowderFromName(nameMaster);
		Clowder Victim = Clowder.getClowderFromName(nameVictim);
		
		if(Master != null && Victim != null) {
			
			Master.enemy = Victim;
			Master.enemyS = Victim.name;
			//different war time if revolt
			if(Master.suzerain == Victim)
			{
				Master.addWarTime(30, player.worldObj);
			}
			else
			{
			Master.addWarTime(60, player.worldObj);
			}
			//Master.endDeclareTime(player.worldObj);
			//sender.addChatMessage(new ChatComponentText(INFO + Victim.getDecoratedName() + " is now the vassal of " + Master.getDecoratedName() + "!"));
			
			
			Master.save(player.worldObj);
			
			for(Clowder everyone : Master.clowders)
			{
				//different message if revolt
				if(Master.suzerain == Victim)
					everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE +"An admin forced " + Master.name + "'s separatist movement from " + Victim.name + " to become an armed rebellion!"));
				else
				{
			everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + Master.name + " has declared war against " + Victim.name + " by admin command!"));
				}
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.hoiWar", 2.0F, 1.0F);
			if(everyone.suzerain == Master)
				everyone.notifyAll(player.worldObj, new ChatComponentText(CommandClowder.TITLE + "Since they are our master, we are automatically involved in their war!"));
	
			
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "Invalid factions!"));
		}
	}
	
	
	/*
	private void cmdCreate(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		
		if(Clowder.getClowderFromPlayer(player) == null) {
			
			if(Clowder.getClowderFromName(name) == null) {
				Clowder.createClowder(player, name);
				sender.addChatMessage(new ChatComponentText(TITLE + "Created faction " + name + "!"));
				sender.addChatMessage(new ChatComponentText(INFO + "Use /c claim to get started!"));
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This name is already taken!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You can not create a new faction while already being in one!"));
		}
	}
	
	
	private void cmdDisband(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
				
			if(name.equals(clowder.name)) {
				
				if(clowder.disbandClowder(player)) {
					sender.addChatMessage(new ChatComponentText(CRITICAL + "Your faction was disbanded!"));
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "Can not disband a faction you do not own!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "Confirmation unsuccessful. Please enter the faction name to disband the faction."));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdRename(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
				
			if(Clowder.getClowderFromName(name) == null) {

				if(clowder.getPermLevel(player.getDisplayName()) > 1) {
					clowder.rename(name, player);
					sender.addChatMessage(new ChatComponentText(TITLE + "Renamed faction to " + name + "!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder, ""), (EntityPlayerMP) player);
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "You lack the permissions to rename this faction!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This name is already taken!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	*/
	


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