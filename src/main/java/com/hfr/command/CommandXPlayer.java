package com.hfr.command;

import java.util.ArrayList;
import java.util.List;

import com.hfr.data.CBTData;
import com.hfr.data.CBTData.CBTEntry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.CBTPacket;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandXPlayer extends CommandBase {

	private final List aliases;
	
	public CommandXPlayer() {
		aliases = new ArrayList();
		aliases.add("xplayer");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "xplayer";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/xplayer <mode> [player]";
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

			if(!"cbt".equals(args[0]) && !"ramranch".equals(args[0]) && !"fps".equals(args[0]) && !"tilt".equals(args[0]) && !"shader".equals(args[0]) && !"vomit".equals(args[0])) {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Availible modes:"));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "  -cbt OR fps (5 FPS mode)"));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "  -ramranch OR tilt (screen gymnastics)"));
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "  -vomit OR shader (rapidly alternaating shaders)"));
				return;
			}
			
            EntityPlayerMP target = minecraftserver.getConfigurationManager().func_152612_a(args[1]);
            
            if(target != null) {

				CBTData data = CBTData.getData(sender.getEntityWorld());
				CBTEntry entry = data.getEntryByPlayerName(args[1]);
				
				if(entry == null) {
					entry = new CBTEntry(args[1]);
					data.entries.add(entry);
				}
				
				if("cbt".equals(args[0]) || "fps".equals(args[0])) {
					entry.fps = !entry.fps;
					
					if(entry.fps)
						sender.addChatMessage(new ChatComponentText("Enabled low-FPS mode for player " + args[1]));
					else
						sender.addChatMessage(new ChatComponentText("Disabled low-FPS mode for player " + args[1]));
					
				} else if("ramranch".equals(args[0]) || "tilt".equals(args[0])) {
					entry.tilt = !entry.tilt;
					
					if(entry.tilt)
						sender.addChatMessage(new ChatComponentText("Enabled tilt mode for player " + args[1]));
					else
						sender.addChatMessage(new ChatComponentText("Disabled tilt mode for player " + args[1]));
				} else if("vomit".equals(args[0]) || "shader".equals(args[0])) {
					entry.shader = !entry.shader;
					
					if(entry.shader)
						sender.addChatMessage(new ChatComponentText("Enabled shader mode for player " + args[1]));
					else
						sender.addChatMessage(new ChatComponentText("Disabled shader mode for player " + args[1]));
				}

            	PacketDispatcher.wrapper.sendTo(new CBTPacket(entry.fps, entry.tilt, entry.shader), target);
            	
				data.markDirty();
            	
            } else {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player not found."));
				return;
            }
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
