package com.hfr.items;

import com.hfr.blocks.ModBlocks;
import com.hfr.dim.MoonTeleporter;
import com.hfr.tileentity.clowder.TileEntityFlag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemDebug extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote && player instanceof EntityPlayerMP) {
			
			EntityPlayerMP thePlayer = (EntityPlayerMP) player;
			
			if(!player.isSneaking())
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, 15, new MoonTeleporter(thePlayer.getServerForPlayer()));
			else
				System.out.println(player.dimension);
		}
		
		return stack;
	}
	
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
    	if(!world.isRemote) {
    		
    		if(world.getBlock(x, y, z) == ModBlocks.clowder_flag) {
    			
    			TileEntityFlag flag = (TileEntityFlag)world.getTileEntity(x, y, z);
    			player.addChatMessage(new ChatComponentText("Is this surrounded? " + !flag.bordersWilderness()));
    			
    			return true;
    		}
    		
    		world.createExplosion(null, x + 0.1, y + 0.1, z + 0.1, 10, true);
    	}
    	
        return true;
    }
}
