package com.hfr.items;

import com.hfr.main.MainRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class ItemCan extends Item {

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
    	
    	if(entity instanceof EntityHorse) {
    		
    		((EntityHorse)entity).makeHorseRearWithSound();

    		Vec3 vec = entity.getLookVec();
    		vec.yCoord = 0.5;
    		
    		for(int i = 0; i < 25; i++) {
    			double vel = player.getRNG().nextDouble();
    			MainRegistry.proxy.spawnSFX(player.worldObj, entity.posX, entity.posY + 0.75F, entity.posZ, 3, new double[] { vec.xCoord * vel, vec.yCoord * vel, vec.zCoord * vel });
    		}
    		
	        player.worldObj.playSoundAtEntity(player, "hfr:item.milk", 1.0F, 1.0F);
    		
			stack.stackSize--;
    		
			if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.canned_jizz)))
				player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.canned_jizz), true);
    		
    		return true;
    	}
    	
        return false;
    }
}
