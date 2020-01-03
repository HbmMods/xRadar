package com.hfr.command;

import com.hfr.clowder.Clowder;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
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
		
		if(args.length == 0) {
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
		
		String name = "";

		if(clowder.getPermLevel(player.getDisplayName()) > 2) {
			name += "<Leader> ";
		}
		if(clowder.getPermLevel(player.getDisplayName()) > 1) {
			name += "<Officer> ";
		}
		if(clowder.getPermLevel(player.getDisplayName()) > 0) {
			name += "<Citizen> ";
		}
		
		name += "[" + player.getDisplayName() + "]";
		
		clowder.notifyAll(player.worldObj, new ChatComponentText(HELP + name + " " + message));
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
