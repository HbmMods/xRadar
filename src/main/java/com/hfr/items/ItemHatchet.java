package com.hfr.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemHatchet extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote)
			return true;
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		if(player.isSneaking()) {
			stack.stackTagCompound.setInteger("xCoord2", x);
			stack.stackTagCompound.setInteger("zCoord2", z);
			player.addChatComponentMessage(new ChatComponentText("Set second position!"));
		} else {
			stack.stackTagCompound.setInteger("xCoord1", x);
			stack.stackTagCompound.setInteger("zCoord1", z);
			player.addChatComponentMessage(new ChatComponentText("Set first position!"));
		}
		
		world.playSoundAtEntity(player, "hfr:item.techBoop", 1.0F, 1.0F);
		
        return true;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Right-click block to set first position");
		list.add("Sneak-click block to set second position");
		list.add("");
		list.add("Click on a province flag to add chunks");
		list.add("Adding chunks will also regenerate the claim");
		
		if(itemstack.stackTagCompound != null)
		{
			list.add("");
			list.add("POS:");
			list.add("X1: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord1")));
			list.add("Z1: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord1")));
			list.add("X2: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord2")));
			list.add("Z2: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord2")));
		}
	}

}
