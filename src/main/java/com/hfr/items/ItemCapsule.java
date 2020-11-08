package com.hfr.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCapsule extends Item {
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
    	
    	int time = 200 - getTimer(stack);
    	
    	list.add((time / 20) + "s");
    }
    
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	
    	if(!world.isRemote) {
    		
    		int timer = getTimer(stack) + 1;
    		
    		if(timer <= 200)
    			setTimer(stack, timer);
    		
    		if(timer == 200) {
    			stack.stackSize--;
    			world.createExplosion(null, entity.posX, entity.posY + entity.height / 2, entity.posZ, 10, false);
    			
    			for(int k = 0; k < 15; k++) {
        			world.createExplosion(null, entity.posX + entity.worldObj.rand.nextGaussian(), entity.posY + entity.worldObj.rand.nextDouble() + entity.height, entity.posZ + entity.worldObj.rand.nextGaussian(), 2, false);
    			}
    			
    			if(entity instanceof EntityPlayer) {
    				((EntityPlayer) entity).inventory.dropAllItems();
    			}
    		}
    	} else {
    		if(entity.worldObj.rand.nextInt(10) == 0)
    			world.spawnParticle("lava", entity.posX, entity.posY, entity.posZ, 0, 0, 0);
    	}
    }
    
    private void setTimer(ItemStack stack, int time) {
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	stack.stackTagCompound.setInteger("timer", time);
    }
    
    private int getTimer(ItemStack stack) {
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	return stack.stackTagCompound.getInteger("timer");
    }
    
    public boolean onEntityItemUpdate(EntityItem entityItem) {
    	entityItem.setDead();
        return true;
    }
}
