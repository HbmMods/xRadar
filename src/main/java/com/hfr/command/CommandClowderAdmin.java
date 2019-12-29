package com.hfr.command;

import java.util.ArrayList;
import java.util.List;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.data.ClowderData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

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
		
		if(cmd.equals("help") || cmd.equals("man")) {
			
			if(args.length > 1)
				cmdHelp(sender, args[1]);
			else
				cmdHelp(sender, "1");
			return;
		}
		
		if((cmd.equals("forcejoin") || cmd.equals("fj")) && args.length > 1) {
			
			cmdForcejoin(sender, args[1]);
			return;
		}
		
		if((cmd.equals("forcedisband") || cmd.equals("fd")) && args.length > 1) {
			
			cmdForcedisband(sender, args[1]);
			return;
		}
		
		if(cmd.equals("hijack") || cmd.equals("hi")) {
			
			cmdHijack(sender);
			return;
		}
		
		if(cmd.equals("deletedata") || cmd.equals("deldat")) {
			
			cmdDeletedata(sender);
			return;
		}
		
		if((cmd.equals("setclaim") || cmd.equals("sc")) && args.length > 3) {
			
			cmdSetclaim(sender, args[1], args[2], args[3]);
			return;
		}
		
		if((cmd.equals("addprestige") || cmd.equals("ap")) && args.length > 2) {
			
			cmdAddPrestige(sender, args[1], args[2]);
			return;
		}
		
		sender.addChatMessage(new ChatComponentText(ERROR + getCommandUsage(sender)));
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
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 2"));
		}

		if(p == 2) {
			//sender.addChatMessage(new ChatComponentText(COMMAND_ADMIN + "-info" + TITLE + " - Shows info on your faction"));
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
		Clowder clowder = Clowder.getClowderFromName(player.getDisplayName());
		
		if(clowder != null) {
			
			if(!clowder.leader.equals(player.getDisplayName())) {
				
				clowder.transferOwnership(player);
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
