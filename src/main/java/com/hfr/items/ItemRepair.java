package com.hfr.items;

import java.util.List;

import com.hfr.handler.BobbyBreaker;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemRepair extends Item {
	
	int heal = 10;
	
	public ItemRepair(int heal) {
		this.heal = heal;
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {

		if(this == ModItems.repair_1)
			list.add("It's like a band-aid, but for walls!");
		if(this == ModItems.repair_2)
			list.add("Basically rock glue");
		if(this == ModItems.repair_3)
			list.add("Comes with enough glue to make the plate stick");
		
		list.add("+" + heal + " HP for damaged blocks");
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			
			Block b = world.getBlock(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			
			int health = BobbyBreaker.getResistanceValue(b, meta);
			
			if(health == 0) {
				player.addChatComponentMessage(new ChatComponentText("This block cannot be repaired."));
				return false;
			}
			
			float damage = BobbyBreaker.getValue(x, y, z, world.provider.dimensionId);
			
			if(damage == -1) {
				player.addChatComponentMessage(new ChatComponentText("This block is already at full health."));
				return false;
			}

			damage += heal;
			
			if(damage >= health) {
				player.addChatComponentMessage(new ChatComponentText("This block has been fully repaired."));
				BobbyBreaker.removeEntry(x, y, z, world.provider.dimensionId);
			} else {
				BobbyBreaker.setValue(x, y, z, world.provider.dimensionId, damage);
			}

			int key = 1;
			key = 31 * key + x;
			key = 31 * key + y;
			key = 31 * key + z;

			int dmg = 10 - (int)(Math.ceil(damage / (float)health * 10));
			world.destroyBlockInWorldPartially(key, x, y, z, dmg);
			
			stack.stackSize--;
			if(stack.stackSize == 0)
				stack = null;
		}
		
		return true;
    }

}
