package com.hfr.command;

import com.hfr.clowder.Clowder;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.PlayerDataPacket;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandClowderChat extends CommandBase {

	@Override
	public String getCommandName() {
		return "cc";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/cc <message>";
	}
	
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public void processCommand(ICommandSender sender, String[] args) {

		EntityPlayer player = getCommandSenderAsPlayer(sender);
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder == null) {
			player.getEntityData().setInteger(CHAT_KEY, 0);
			sender.addChatMessage(new ChatComponentText(CRITICAL + "You are not in any faction, chat settings have been reset!"));
			return;
		}
		
		if(args.length > 0) {
			
			if(args[0].equals("public") || args[0].equals("p")) {
				player.getEntityData().setInteger(CHAT_KEY, 0);
				sender.addChatMessage(new ChatComponentText(INFO + "Chat mode set to public!"));
				return;
			} else if(args[0].equals("faction") || args[0].equals("f")) {
				player.getEntityData().setInteger(CHAT_KEY, 1);
				sender.addChatMessage(new ChatComponentText(INFO + "Chat mode set to faction!"));
				return;
			} else if(args[0].equals("mute") || args[0].equals("m")) {
				player.getEntityData().setInteger(MUTE_KEY, 1);
				PacketDispatcher.wrapper.sendTo(new PlayerDataPacket(MUTE_KEY, 1), (EntityPlayerMP) player);
				sender.addChatMessage(new ChatComponentText(INFO + "Public chat has been muted!"));
				return;
			} else if(args[0].equals("unmute") || args[0].equals("u")) {
				PacketDispatcher.wrapper.sendTo(new PlayerDataPacket(MUTE_KEY, 0), (EntityPlayerMP) player);
				sender.addChatMessage(new ChatComponentText(INFO + "Public chat has been unmuted!"));
				return;
			} else {
				sender.addChatMessage(new ChatComponentText(CRITICAL + "Invalid arguments!"));
				sender.addChatMessage(new ChatComponentText(CRITICAL + "Try: 'public', 'faction' or none"));
				return;
			}
		}
		
		int mode = player.getEntityData().getInteger(CHAT_KEY);
		
		if(mode == 0) {
			player.getEntityData().setInteger(CHAT_KEY, 1);
			sender.addChatMessage(new ChatComponentText(INFO + "Chat mode set to faction!"));
			return;
		}
		
		if(mode == 1) {
			player.getEntityData().setInteger(CHAT_KEY, 0);
			sender.addChatMessage(new ChatComponentText(INFO + "Chat mode set to public!"));
			return;
		}
		
		/*if(args.length == 0) {
			sender.addChatMessage(new ChatComponentText(ERROR + "You can't message your faction without a message! (duh)"));
			return;
		}
		
		if(clowder == null) {
			sender.addChatMessage(new ChatComponentText(ERROR + "You are not in any faction!"));
			return;
		}
		
		String message = "";
		
		for(int i = 0; i < args.length; i++) {
			
			if(i > 0)
				message += " ";
			
			message += args[i];
		}
		
		String name = "";*/
	}

	public static final String CHAT_KEY = "clowderChat";
	public static final String MUTE_KEY = "clowderMute";

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
