package com.hfr.items;

import java.util.List;

import com.hfr.blocks.weapon.LaunchPad;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDesingatorRange extends Item {

    @Override
	public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {
    	p_77622_1_.stackTagCompound = new NBTTagCompound();
    	p_77622_1_.stackTagCompound.setInteger("xCoord", 0);
    	p_77622_1_.stackTagCompound.setInteger("zCoord", 0);
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(itemstack.stackTagCompound != null)
		{
			list.add("Target Coordinates:");
			list.add("X: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord")));
			list.add("Z: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord")));
		} else {
			list.add("Please select a target.");
		}
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		MovingObjectPosition pos = rayTrace(player, 300, 1);
		int x = pos.blockX;
		int y = pos.blockY;
		int z = pos.blockZ;
		
		if(!(world.getBlock(x, y, z) instanceof LaunchPad))
		{
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			
			stack.stackTagCompound.setInteger("xCoord", x);
			stack.stackTagCompound.setInteger("zCoord", z);
			
	        if(world.isRemote)
			{
	        	player.addChatMessage(new ChatComponentText("Position set to X:" + x + ", Z:" + z));
			}
	        
        	world.playSoundAtEntity(player, "hfr:item.techBleep", 1.0F, 1.0F);
        	
	        return stack;
		}
    	
        return stack;
    }
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double d, float f) {
        Vec3 vec3 = getPosition(f, player);
        vec3.yCoord += player.eyeHeight;
        Vec3 vec31 = player.getLook(f);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d, vec31.yCoord * d, vec31.zCoord * d);
        return player.worldObj.func_147447_a(vec3, vec32, false, false, true);
	}
	
    public static Vec3 getPosition(float par1, EntityPlayer player) {
        if (par1 == 1.0F)
        {
            return Vec3.createVectorHelper(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        }
        else
        {
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * par1;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * par1 + (player.getEyeHeight() - player.getDefaultEyeHeight());
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * par1;
            return Vec3.createVectorHelper(d0, d1, d2);
        }
    }
}
