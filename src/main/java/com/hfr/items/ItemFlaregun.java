package com.hfr.items;

import java.util.List;

import com.hfr.entity.projectile.EntityFlare;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemFlaregun extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			
			int state = getState(stack);
			
			if(state == 0) {
				setState(stack, 1);
				world.playSoundEffect(player.posX, player.posY, player.posZ, "random.click", 1.0F, 0.75F);
			}
			
			if(state == 1) {
				
				if(player.inventory.hasItem(ModItems.flare) || player.capabilities.isCreativeMode) {
					setState(stack, 2);
					
					if(!player.capabilities.isCreativeMode) {
						player.inventory.consumeInventoryItem(ModItems.flare);
						player.inventoryContainer.detectAndSendChanges();
					}
				} else {
					setState(stack, 0);
				}
				
				world.playSoundEffect(player.posX, player.posY, player.posZ, "random.click", 1.0F, 0.5F);
			}
			
			if(state == 2) {
				world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.ghast.fireball", 1.0F, 1.0F);
				world.spawnEntityInWorld(EntityFlare.statFac(world, player));
				setState(stack, 0);
			}
		}
		
		return stack;
	}
	
	//0 unloaded
	//1 open
	//2 loaded
	public static void setState(ItemStack stack, int state) {
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setInteger("state", state);
	}
	
	public static int getState(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		return stack.getTagCompound().getInteger("state");
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		int state = getState(stack);

		if(state < 2)
			list.add("[UNLOADED]");
		else
			list.add("[LOADED]");
	}
}
