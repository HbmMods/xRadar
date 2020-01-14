package com.hfr.items;

import java.util.List;

import com.hfr.handler.BobbyBreaker;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemScanner extends Item {
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add("Checks bobby breaker damage values");
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			Block b = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			int health = BobbyBreaker.getResistanceValue(b, meta);
			
			if(health == 0) {
				player.addChatComponentMessage(new ChatComponentText("This block does not have any additional resistance values."));
				return false;
			}
			
			float damage = BobbyBreaker.getValue(x, y, z, world.provider.dimensionId);
			
			if(damage == -1)
				damage = health;

			damage = (int)(damage * 10) / 10F;
			float percent = (int)((damage / health) * 1000) / 10F;

			player.addChatComponentMessage(new ChatComponentText("Health: [" + damage + "/" + health + ".0] ≙ " + percent + "%"));
		}
		
		return true;
    }

}
