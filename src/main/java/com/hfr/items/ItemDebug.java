package com.hfr.items;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.tileentity.clowder.TileEntityFlag;

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
