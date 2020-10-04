package com.hfr.command;

import java.util.ArrayList;
import java.util.List;

import com.hfr.data.CBTData;
import com.hfr.data.CBTData.CBTEntry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.CBTPacket;
import com.hfr.packet.effect.CumPacket;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandXCum extends CommandBase {

	private final List aliases;
	
	public CommandXCum() {
		aliases = new ArrayList();
		aliases.add("xcum");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "xcum";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/xcum [player] <url>";
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
        MinecraftServer minecraftserver = MinecraftServer.getServer();
		
		if(args.length != 2) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
		} else {
			
			EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(args[0]);
			
			if(player == null) {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player not found."));
				return;
			}
			
			PacketDispatcher.wrapper.sendTo(new CumPacket(args[1]), (EntityPlayerMP) player);
		}
	}
	
    public int getRequiredPermissionLevel()
    {
        return 3;
    }
    
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
    {
        return p_71516_2_.length >= 1 ? getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
