package com.hfr.items;

import java.util.List;

import com.hfr.entity.grenade.EntityGrenadeBoxcar;
import com.hfr.entity.grenade.EntityGrenadeGas;
import com.hfr.entity.grenade.EntityGrenadeNuclear;
import com.hfr.entity.grenade.EntityHook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item {
	
	public int fuse = 4;

	public ItemGrenade(int fuse) {
		this.maxStackSize = 16;
		this.fuse = fuse;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (!p_77659_3_.capabilities.isCreativeMode) {
			--p_77659_1_.stackSize;
		}

		p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!p_77659_2_.isRemote) {
			if (this == ModItems.grenade_gas) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeGas(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_nuclear) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeNuclear(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_boxcar) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeBoxcar(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.hook) {
				p_77659_2_.spawnEntityInWorld(new EntityHook(p_77659_2_, p_77659_3_));
			}
		}

		return p_77659_1_;
	}
	
	private String translateFuse() {
		if(fuse == -1)
			return "Impact";
		
		if(fuse == 0)
			return "Instant";
		
		return fuse + "s";
	}
	
	public static int getFuseTicks(Item grenade) {
		return ((ItemGrenade)grenade).fuse * 20;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Fuse: " + translateFuse());
		list.add("");

		if (this == ModItems.grenade_gas) {
			list.add("GAS GAS GAS");
		}
		if (this == ModItems.grenade_nuclear) {
			list.add("(Dash-Algorithm)");
		}
		if (this == ModItems.grenade_boxcar) {
			list.add("The ultimate admin abuse.");
			list.add("Pip's finest. (Config Algorithm)");
		}
	}

}
