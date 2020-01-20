package com.hfr.command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hfr.clowder.Clowder;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandXDebug extends CommandBase {

	@Override
	public String getCommandName() {
		return "xdebug";
	}
	
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/xdebug <param>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
        MinecraftServer minecraftserver = MinecraftServer.getServer();
		
		if(args.length != 1) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
		} else {
        	
        	Clowder clowder = Clowder.getClowderFromPlayerName(args[0]);
        	
        	if(clowder != null) {
        		
        		Long time = clowder.members.get(args[0]);
        		
        		if(time != null) {
        			
        			long c = System.currentTimeMillis();
        			
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        			Date logout = new Date(time);
        			Date current = new Date(c);
        			
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "" + time + " /logout"));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "" + c + " /current"));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "========================================================="));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "" + sdf.format(logout) + " /logout"));
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "" + sdf.format(current) + " /current"));
	        		
	        	} else {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Time not found (???)"));
	        	}
	        	
	        } else {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Clowder not found."));
	        }
		}
	}
    
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
    {
        return p_71516_2_.length >= 1 ? getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
    {
        return true;
    }

}
