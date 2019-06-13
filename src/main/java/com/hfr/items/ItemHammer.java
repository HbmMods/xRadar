package com.hfr.items;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHammer extends Item {

	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer) {
		
    	World world = entity.worldObj;

		if (!world.isRemote) {
        	entity.setHealth(0.0F);
        }
        world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 0.1F);
		
		return false;
    }

    @Override
	public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 1000000000, 0));
		return multimap;
    }

}
