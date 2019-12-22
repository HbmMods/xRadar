package com.hfr.command;

import java.util.ArrayList;
import java.util.List;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderFlag;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.data.ClowderData;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ClowderFlagPacket;
import com.hfr.util.ParserUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandClowder extends CommandBase {

	@Override
	public String getCommandName() {
		return "clowder";
	}

	@Override
    public List getCommandAliases()
    {
        return new ArrayList() {{ add("clowder"); add("c"); }};
    }

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/clowder help";
	}
	
    public int getRequiredPermissionLevel()
    {
        return 0;
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
		
		if(cmd.equals("create") && args.length > 1) {
			cmdCreate(sender, args[1]);
			return;
		}
		
		if(cmd.equals("disband") && args.length > 1) {
			cmdDisband(sender, args[1]);
			return;
		}
		
		if(cmd.equals("comrades")) {
			cmdComrades(sender);
			return;
		}
		
		if(cmd.equals("color") && args.length > 1) {
			cmdColor(sender, args[1]);
			return;
		}
		
		if(cmd.equals("info")) {
			cmdInfo(sender);
			return;
		}
		
		if(cmd.equals("rename") && args.length > 1) {
			cmdRename(sender, args[1]);
			return;
		}
		
		if(cmd.equals("list")) {
			cmdList(sender);
			return;
		}
		
		if(cmd.equals("motd") && args.length > 1) {
			cmdMOTD(sender, args);
			return;
		}
		
		if(cmd.equals("owner") && args.length > 1) {
			cmdOwner(sender, args[1]);
			return;
		}
		
		if(cmd.equals("apply") && args.length > 1) {
			cmdApply(sender, args[1]);
			return;
		}
		
		if(cmd.equals("leave")) {
			cmdLeave(sender);
			return;
		}
		
		if(cmd.equals("accept") && args.length > 1) {
			cmdAccept(sender, args[1]);
			return;
		}
		
		if(cmd.equals("deny") && args.length > 1) {
			cmdDeny(sender, args[1]);
			return;
		}
		
		if(cmd.equals("applicants")) {
			cmdApplicants(sender);
			return;
		}
		
		if(cmd.equals("kick") && args.length > 1) {
			cmdKick(sender, args[1]);
			return;
		}
		
		if(cmd.equals("listflags")) {
			cmdListflags(sender);
			return;
		}
		
		if(cmd.equals("flag") && args.length > 1) {
			cmdFlag(sender, args[1]);
			return;
		}
		
		/*if(cmd.equals("admin")) {

            MinecraftServer server = MinecraftServer.getServer();
            server.initiateShutdown();
		}*/
		
		if(cmd.equals("retreat")) {
			cmdRetreat(sender);
			return;
		}
		
		if(cmd.equals("sethome")) {
			cmdSethome(sender);
			return;
		}
		
		if(cmd.equals("home")) {
			cmdHome(sender);
			return;
		}
		
		if((cmd.equals("addwarp") || cmd.equals("setwarp")) && args.length > 1) {
			cmdAddWarp(sender, args[1]);
			return;
		}
		
		if(cmd.equals("delwarp") && args.length > 1) {
			cmdDelWarp(sender, args[1]);
			return;
		}
		
		if(cmd.equals("warp") && args.length > 1) {
			cmdWarp(sender, args[1]);
			return;
		}
		
		if(cmd.equals("warps")) {
			cmdWarps(sender);
			return;
		}
		
		if(cmd.equals("balance")) {
			cmdBalance(sender);
			return;
		}
		
		if(cmd.equals("deposit") && args.length > 1) {
			cmdDeposit(sender, args[1]);
			return;
		}
		
		if(cmd.equals("withdraw") && args.length > 1) {
			cmdWithdraw(sender, args[1]);
			return;
		}
		
		sender.addChatMessage(new ChatComponentText(ERROR + getCommandUsage(sender)));
	}
	
	private void cmdHelp(ICommandSender sender, String page) {
		
		int p = this.parseInt(sender, page);
		int pages = 4;
		
		if(p < 1 || p > pages)
			p = 1;

		sender.addChatMessage(new ChatComponentText(HELP + "/clowder [command] <args...> {optional args...}"));
		sender.addChatMessage(new ChatComponentText(INFO + "Commands [" + p + "/" + pages + "]:"));
		
		if(p == 1) {
			sender.addChatMessage(new ChatComponentText(COMMAND + "-help {page}" + TITLE + " - The thing you just used"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-create <name>" + TITLE + " - Creates a faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-disband <name>" + TITLE + " - Disbands a faction, name parameter for confirmation"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-owner <player>" + TITLE + " - Transfers faction ownership"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-comrades" + TITLE + " - Shows all members of your faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-color <hexadecimal>" + TITLE + " - Sets the faction's color"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-motd <MotD>" + TITLE + " - Sets the faction's MotD"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-rename <name>" + TITLE + " - Renames your faction"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 2"));
		}

		if(p == 2) {
			sender.addChatMessage(new ChatComponentText(COMMAND + "-info" + TITLE + " - Shows info on your faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-list" + TITLE + " - Lists all factions (page functin pending)"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-apply <name>" + TITLE + " - Sends an application to a faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-leave" + TITLE + " - Leaves the faction"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-accept <name>" + TITLE + " - Accepts a player's application"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-deny <name>" + TITLE + " - Denies a player's application"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-applicants" + TITLE + " - Lists applying players"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-kick <player>" + TITLE + " - Removes player from faction"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 3"));
		}

		if(p == 3) {
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-flag <flag>" + TITLE + " - Changes faction flag"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-listflags" + TITLE + " - Lists availible flags"));
			sender.addChatMessage(new ChatComponentText(COMMAND_LEADER + "-sethome" + TITLE + " - Sets the clowder's home point"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-home" + TITLE + " - Teleports to the clowder's home"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-addwarp <name>" + TITLE + " - Creates a warp"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-delwarp <name>" + TITLE + " - Removes a warp"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-warp <name>" + TITLE + " - Teleports to a warp point"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-warps" + TITLE + " - Lists all warps"));
			sender.addChatMessage(new ChatComponentText(INFO + "/clowder help 4"));
		}

		if(p == 4) {
			sender.addChatMessage(new ChatComponentText(COMMAND + "-retreat" + TITLE + " - Reatreats after 10 minutes"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-balance" + TITLE + " - Displays how much prestige the faction has"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-deposit <amount>" + TITLE + " - Turns prestige items into digiprestige"));
			sender.addChatMessage(new ChatComponentText(COMMAND + "-withdraw <amount>" + TITLE + " - Withdraws digiprestige as prestige items"));
		}
	}
	
	private void cmdCreate(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		
		if(Clowder.getClowderFromPlayer(player) == null) {
			
			if(Clowder.getClowderFromName(name) == null) {
				Clowder.createClowder(player, name);
				sender.addChatMessage(new ChatComponentText(TITLE + "Created faction " + name + "!"));
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
	
	private void cmdComrades(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + clowder.name));
			
			for(String s : clowder.members.keySet())
				sender.addChatMessage(new ChatComponentText(LIST + s));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdColor(ICommandSender sender, String color) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {
				int c = ParserUtil.parseColor(color);
				
				if(c < 0) {
					sender.addChatMessage(new ChatComponentText(ERROR + "Incorrect color format!"));
				} else {
					clowder.setColor(c, player);
					sender.addChatMessage(new ChatComponentText(INFO + "Set faction color to " + color + "!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder), (EntityPlayerMP) player);
				}
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "Can not change the color of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdInfo(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + clowder.name));
			sender.addChatMessage(new ChatComponentText(TITLE + clowder.motd));
			sender.addChatMessage(new ChatComponentText(LIST + "Owner: " + clowder.leader));
			sender.addChatMessage(new ChatComponentText(LIST + "Members: " + clowder.members.size()));
			sender.addChatMessage(new ChatComponentText(LIST + "Color: " + Integer.toHexString(clowder.color).toUpperCase()));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdRename(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
				
			if(Clowder.getClowderFromName(name) == null) {

				if(clowder.leader.equals(player.getDisplayName())) {
					clowder.rename(name, player);
					sender.addChatMessage(new ChatComponentText(TITLE + "Renamed faction to " + name + "!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder), (EntityPlayerMP) player);
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "Can not rename a faction you do not own!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This name is already taken!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdList(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		
		for(Clowder c : Clowder.clowders) {

			sender.addChatMessage(new ChatComponentText(TITLE + c.name + " - " + c.motd));
			sender.addChatMessage(new ChatComponentText(LIST + c.members.size() + " members"));
		}
		
		if(Clowder.clowders.isEmpty()) {
			sender.addChatMessage(new ChatComponentText(TITLE + "There are no factions as of now. Use /clowder create <name>"));
			sender.addChatMessage(new ChatComponentText(TITLE + "to start your own faction!"));
		}
	}
	
	private void cmdMOTD(ICommandSender sender, String[] motd) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			String stitched = "";
			
			for(int i = 1; i < motd.length; i++)
				stitched += motd[i] + " ";
			
			stitched = stitched.trim();
			
			clowder.setMotd(stitched, player);
			sender.addChatMessage(new ChatComponentText(TITLE + "Set faction MotD to " + stitched + "!"));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdOwner(ICommandSender sender, String owner) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {

				if(clowder.members.get(owner) != null) {
					EntityPlayer newLeader = player.worldObj.getPlayerEntityByName(owner);
					
					if(owner != null) {
						clowder.transferOwnership(newLeader);
						sender.addChatMessage(new ChatComponentText(INFO + "Transfered leadership to player " + owner + "!"));
						clowder.notifyLeader(player.worldObj, new ChatComponentText(INFO + "You are now this faction's new leader!"));
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is not online right now!"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your faction!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not change the color of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdApply(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder == null) {
			
			Clowder toApply = Clowder.getClowderFromName(name);
				
			if(toApply != null) {

				sender.addChatMessage(new ChatComponentText(INFO + "Sent application to " + toApply.name + "!"));
				toApply.applications.add(player.getDisplayName());
				toApply.notifyLeader(player.worldObj, new ChatComponentText(INFO + "Player " + sender.getCommandSenderName() + " would like to join your faction!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "There is no faction with this name!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are already in a faction!"));
		}
	}
	
	private void cmdLeave(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(!clowder.leader.equals(player.getDisplayName())) {
				
				clowder.removeMember(player.worldObj, player.getDisplayName());
				sender.addChatMessage(new ChatComponentText(CRITICAL + "You left this faction!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not leave a faction you own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdAccept(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {
				
				if(clowder.applications.contains(name)) {
					
					if(Clowder.getClowderFromName(name) == null) {
						clowder.addMember(player.worldObj, name);
						sender.addChatMessage(new ChatComponentText(INFO + "Added player " + name + " to your faction!"));
						clowder.notifyPlayer(player.worldObj, name, new ChatComponentText(INFO + "You have been accepted into " + clowder.name + "!"));
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is already in another faction!"));
					}
					
					clowder.applications.remove(name);
					
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player has no active application!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not manage invites of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdDeny(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {
				
				if(clowder.applications.contains(name)) {
					
					if(Clowder.getClowderFromName(name) == null) {
						sender.addChatMessage(new ChatComponentText(INFO + "Denied player " + sender.getCommandSenderName() + "'s application!"));
					} else {
						sender.addChatMessage(new ChatComponentText(ERROR + "This player is already in another faction!"));
					}
					
					clowder.applications.remove(name);
					
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player has no active application!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not manage invites of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdApplicants(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {
				
				sender.addChatMessage(new ChatComponentText(TITLE + "Applicants:"));
				int cnt = 0;
				
				for (String key : clowder.applications) {
					sender.addChatMessage(new ChatComponentText(LIST + "-" + key));
					cnt++;
				}
				
				if(cnt == 0)
					sender.addChatMessage(new ChatComponentText(LIST + "None!"));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not view the applicants of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdKick(ICommandSender sender, String kickee) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {

				if(clowder.members.get(kickee) != null) {
					
					if(player.getDisplayName().equals(kickee)) {

						sender.addChatMessage(new ChatComponentText(CRITICAL + "You can not kick yourself, idiot!"));
						
					} else {
						clowder.notifyPlayer(player.worldObj, kickee, new ChatComponentText(CRITICAL + "You have been kicked from your faction!"));
						clowder.removeMember(player.worldObj, kickee);
						sender.addChatMessage(new ChatComponentText(INFO + "Kicked player " + kickee + "!"));
					}
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This player is not in your faction!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not change the color of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdListflags(ICommandSender sender) {

		sender.addChatMessage(new ChatComponentText(TITLE + "List of availible flags:"));
		
		for(String flag : ClowderFlag.getFlags())
			sender.addChatMessage(new ChatComponentText(LIST + "-" + flag));
		
	}
	
	private void cmdFlag(ICommandSender sender, String flag) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {

				ClowderFlag f = ClowderFlag.getFromName(flag.toLowerCase());
				
				if(f != ClowderFlag.NONE) {
					
					clowder.flag = f;
					sender.addChatMessage(new ChatComponentText(INFO + "Changed flag to " + flag + "!"));
					PacketDispatcher.wrapper.sendTo(new ClowderFlagPacket(clowder), (EntityPlayerMP) player);
					
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "This flag does not exist!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not change the color of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdRetreat(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(!Clowder.retreating.contains(player.getDisplayName())) {
				
				clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Player " + player.getDisplayName() + " is retreating!"));
				Clowder.retreating.add(player.getDisplayName());
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You are already retreating!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdSethome(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.leader.equals(player.getDisplayName())) {
				
				Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair((int)player.posX, (int)player.posZ));
				
				if(owner != null && owner.zone == Zone.FACTION && owner.owner == clowder) {

					clowder.setHome(player.posX, player.posY, player.posZ, player);
					clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Home set!"));
				} else {
					sender.addChatMessage(new ChatComponentText(ERROR + "You can not set the home outside of your claimed land!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "You can not set the home of a faction you do not own!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdHome(ICommandSender sender) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair((int)player.posX, (int)player.posZ));
			
			if(owner != null && (owner.zone == Zone.WARZONE || (owner.zone == Zone.FACTION && owner.owner != clowder))) {

				sender.addChatMessage(new ChatComponentText(ERROR + "You can not teleport home in foreign territory!"));
				
			} else {
				
				player.mountEntity(null);
				player.playerNetServerHandler.setPlayerLocation(clowder.homeX + 0.5D, clowder.homeY, clowder.homeZ + 0.5D, player.rotationYaw, player.rotationPitch);
				clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Teleporting home..."));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdAddWarp(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(clowder.warps.containsKey(name)) {
				sender.addChatMessage(new ChatComponentText(ERROR + "This warp already exists!"));
				return;
			}
			
			if(clowder.prestige < MainRegistry.warpCost) {
				sender.addChatMessage(new ChatComponentText(ERROR + "You need at least " + MainRegistry.warpCost + " prestige to create a warp!"));
				return;
			}
			
			int code = clowder.tryAddWarp(player, (int)player.posX, (int)player.posY, (int)player.posZ, name);
			
			if(code == 0) {
				clowder.notifyAll(player.worldObj, new ChatComponentText(INFO + "Created warp " + name + "!"));
				clowder.prestige -= MainRegistry.warpCost;
				clowder.save(player.worldObj);
			} else if(code == 1) {
				sender.addChatMessage(new ChatComponentText(ERROR + "Cannot create warp outside of your territory!"));
			} else if(code == 2) {
				sender.addChatMessage(new ChatComponentText(ERROR + "No nearby warp tents!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdDelWarp(ICommandSender sender, String name) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(clowder.warps.containsKey(name)) {
				clowder.warps.remove(name);
				clowder.save(player.worldObj);
				sender.addChatMessage(new ChatComponentText(INFO + "Deleted warp!"));
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This warp does not exist!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdWarp(ICommandSender sender, String name) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {
			
			if(clowder.warps.containsKey(name)) {
				
				int[] warp = clowder.warps.get(name);
				player.mountEntity(null);
				player.playerNetServerHandler.setPlayerLocation(warp[0] + 0.5D, warp[1], warp[2] + 0.5D, player.rotationYaw, player.rotationPitch);
				sender.addChatMessage(new ChatComponentText(INFO + "Warping..."));
				
			} else {
				sender.addChatMessage(new ChatComponentText(ERROR + "This warp does not exist!"));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdWarps(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			sender.addChatMessage(new ChatComponentText(TITLE + "Availible warps:"));
			
			for(String s : clowder.warps.keySet()) {
				int[] pos = clowder.warps.get(s);
				sender.addChatMessage(new ChatComponentText(LIST + s));
				sender.addChatMessage(new ChatComponentText(LIST + " x:" + pos[0] + " y:" + pos[1] + " z:" + pos[2]));
			}
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdBalance(ICommandSender sender) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder != null) {

			if(clowder.prestige > 0)
				sender.addChatMessage(new ChatComponentText(INFO + "Current prestige balance: " + LIST + clowder.prestige));
			else
				sender.addChatMessage(new ChatComponentText(INFO + "It seems like you're bankrupt."));
			
		} else {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdDeposit(ICommandSender sender, String a) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		int amount = parseInt(sender, a);
		
		if(clowder != null) {
			
			if(amount <= 0) {
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot deposit 0 or less prestige!"));
				return;
			}
			
			for(int i = 0; i < amount; i++) {
				
				if(player.inventory.hasItem(ModItems.province_point)) {
					player.inventory.consumeInventoryItem(ModItems.province_point);
					clowder.prestige++;
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
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
		}
	}
	
	private void cmdWithdraw(ICommandSender sender, String a) {

		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		int amount = parseInt(sender, a);
		
		if(clowder != null) {
			
			if(amount <= 0) {
				sender.addChatMessage(new ChatComponentText(ERROR + "You cannot withdraw 0 or less prestige!"));
				return;
			}
			
			amount = Math.min(amount, clowder.prestige);
			
			if(clowder.prestige == 0) {
				sender.addChatMessage(new ChatComponentText(INFO + "It seems like you're bankrupt."));
				return;
			}
			
			clowder.prestige -= amount;
			
			for(int i = 0; i < amount; i++) {
				
				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.province_point))) {
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
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
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
