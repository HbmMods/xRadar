package com.hfr.items;

import java.util.List;

import com.hfr.entity.projectile.EntityPak;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPakker extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			
			if(player.inventory.hasItem(ModItems.pak_rocket) || player.capabilities.isCreativeMode) {
				
				if(!player.capabilities.isCreativeMode) {
					player.inventory.consumeInventoryItem(ModItems.pak_rocket);
					player.inventoryContainer.detectAndSendChanges();
				}

				world.playSoundEffect(player.posX, player.posY, player.posZ, "hfr:item.launch", 10.0F, 1.0F);
				world.spawnEntityInWorld(EntityPak.statFac(world, player));
			}
		}
		
		return stack;
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		list.add("Admin Tool");
		list.add("");
		list.add("For testing all sorts of things from afar");
		list.add("Current thing being tested:");
		list.add("   Death");
	}
}
