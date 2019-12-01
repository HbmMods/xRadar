package com.hfr.items;

import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemDebug extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			
			TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(ClowderTerritory.getCoordPair((int)player.posX, (int)player.posZ));
			
			if(meta != null) {
				
				if(meta.owner.zone == Zone.FACTION && meta.owner.owner != null) {
					player.addChatComponentMessage(new ChatComponentText("This turf belongs to " + meta.owner.owner.name));
				} else {
					player.addChatComponentMessage(new ChatComponentText("This turf is part of " + meta.owner.zone.name()));
				}
			} else {

				player.addChatComponentMessage(new ChatComponentText("This turf is part of WILDERNESS"));
			}
		}
		
		return stack;
	}
}
