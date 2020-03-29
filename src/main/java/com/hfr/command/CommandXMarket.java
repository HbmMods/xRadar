package com.hfr.command;

import java.util.ArrayList;
import java.util.List;

import com.hfr.data.StockData;
import com.hfr.data.StockData.EnumStockPhase;
import com.hfr.data.StockData.Stock;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandXMarket extends CommandBase {

	private final List aliases;
	
	public CommandXMarket() {
		aliases = new ArrayList();
		aliases.add("xmarket");
	}
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "xmarket";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/xmarket [setstock|triggerstock|setshares|getshares]";
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
        MinecraftServer minecraftserver = MinecraftServer.getServer();
		
		if(args.length > 0) {
			
			StockData data = StockData.getData(sender.getEntityWorld());
			
			if(args[0].equals("setstock")) {
				
				if(args.length != 3) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "/xmarket setstock [shortname] [value]"));
				} else {
					
					Stock setstock = data.getStockByShort(args[1]);
					
					if(setstock == null) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + args[1] + "' is not a valid stock!"));
						return;
					}
					
					float setamount = (float)parseDouble(sender, args[2]);
					
					if(setamount > 0 && setamount < 100) {
						
						setstock.value[14] = setamount;
						data.markDirty();
						
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + args[1] + " has been set to " + setamount + "!"));
						
					} else {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + setamount + "' is out of range or invalid!"));
					}
				}
			} else if(args[0].equals("triggerstock")) {
				
				if(args.length != 3) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "/xmarket triggerstock [shortname] [N|U2|U1|D1|D2]"));
				} else {
					
					Stock triggerstock = data.getStockByShort(args[1]);
					
					if(triggerstock == null) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + args[1] + "' is not a valid stock!"));
						return;
					}
					
					EnumStockPhase phase = EnumStockPhase.valueOf(args[2]);
					
					if(phase != null) {
						
						triggerstock.phase = phase;
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "" + args[1] + "'s phase has been set to " + args[2] + "!"));
						
					} else {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + args[2] + "' is not a valid phase!"));
					}
				}
			} else if(args[0].equals("setshares")) {
				
				if(args.length != 4) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "/xmarket setshares [shortname] [player] [amount]"));
				} else {
					
					Stock setshares = data.getStockByShort(args[1]);
					
					if(setshares == null) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + args[1] + "' is not a valid stock!"));
						return;
					}
						
			        EntityPlayerMP target = minecraftserver.getConfigurationManager().func_152612_a(args[2]);
			        
			        if(target == null) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player not found."));
						return;
		            }
			        
			        int shares = parseInt(sender, args[3]);
			        
			        if(shares < 0) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + shares + "' is out of range or invalid!"));
						return;
			        }
			        
			        data.setShares(target.getDisplayName(), setshares, shares);
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Player " + target.getDisplayName() + "'s shares of " + setshares.shortname + " have been set to " + shares + "!"));
				}
			} else if(args[0].equals("getshares")) {
				
				if(args.length != 3) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "/xmarket getshares [shortname] [player]"));
				} else {
					
					Stock getshares = data.getStockByShort(args[1]);
					
					if(getshares == null) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "'" + args[1] + "' is not a valid stock!"));
						return;
					}
						
			        EntityPlayerMP target = minecraftserver.getConfigurationManager().func_152612_a(args[2]);
			        
			        if(target == null) {
						sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Player not found."));
						return;
		            }
			        
			        int shares = data.getShares(target.getDisplayName(), getshares);
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Player " + target.getDisplayName() + " owns " + shares + " shares of " + getshares.shortname + "!"));
				}
				
			} else {
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
			}
		} else {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + getCommandUsage(sender)));
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
