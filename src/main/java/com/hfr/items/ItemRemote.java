package com.hfr.items;

import com.hfr.blocks.ModBlocks;
import com.hfr.tileentity.machine.TileEntityTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemRemote extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		
		if(world.getBlock(x, y, z) == ModBlocks.teleporter) {
			
			if(stack.stackTagCompound == null)
				return false;
			
			TileEntityTeleporter port = (TileEntityTeleporter)world.getTileEntity(x, y, z);
			port.tX = stack.stackTagCompound.getInteger("x");
			port.tY = stack.stackTagCompound.getInteger("y");
			port.tZ = stack.stackTagCompound.getInteger("z");
			port.markDirty();
			world.playSoundAtEntity(player, "hfr:item.techBleep", 2.0F, 1.0F);
			
			return true;
			
		} else {

			if (stack.stackTagCompound == null) {
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);
			world.playSoundAtEntity(player, "hfr:item.techBoop", 2.0F, 1.0F);
			
			return true;
		}
	}

}
