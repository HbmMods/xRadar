package com.hfr.items;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMap extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			
			int size = 7;
			
			printSep(size, player);
			
			String part = "";
			
			for(int z = -size; z <= size; z++) {
				
				for(int x = -size; x <= size; x++) {
					
					CoordPair coords = ClowderTerritory.getCoordPair((int)player.posX + x * 16, (int)player.posZ + z * 16);
					TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(coords);
					
					String character = "#";
					
					if(x == 0 && z == 0)
						character = "☺";
					
					if(meta != null) {

						if(meta.owner.zone == Zone.SAFEZONE)
							part += EnumChatFormatting.GREEN + character;
						else if(meta.owner.zone == Zone.WARZONE)
							part += EnumChatFormatting.DARK_RED + character;
						if(meta.owner.zone == Zone.FACTION) {
							
							Clowder c = Clowder.getClowderFromPlayer(player);
							
							if(meta.owner.owner == c)
								part += EnumChatFormatting.DARK_BLUE + character;
							else
								part += EnumChatFormatting.GOLD + character;
						}
						
					} else {
						part += EnumChatFormatting.RESET + character;
					}
				}
				
				player.addChatComponentMessage(new ChatComponentText(part));
				part = "";
			}
			
			printSep(size, player);
		}
		
		return stack;
	}
	
	private void printSep(int size, EntityPlayer player) {
		
		String part = "";
		
		for(int i = -size; i <= size; i++)
			part += "=";
		
		player.addChatComponentMessage(new ChatComponentText(part));
	}

}
