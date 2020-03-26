package com.hfr.items;

import com.hfr.render.model.ModelBoots;
import com.hfr.render.model.ModelGraphene;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemSpecialArmor extends ItemArmor {

	@SideOnly(Side.CLIENT)
	private ModelGraphene model;
	@SideOnly(Side.CLIENT)
	private ModelBoots boots;

	public ItemSpecialArmor(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
		this.setMaxDamage(0);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == this.armorType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if(this == ModItems.graphene_vest) {
			if (this.model == null) {
				this.model = new ModelGraphene();
			}
			return this.model;
		}
		
		if(this == ModItems.lead_boots) {
			if (this.boots == null) {
				this.boots = new ModelBoots();
			}
			return this.boots;
		}
		
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		
		if(this == ModItems.graphene_vest)
			return "hfr:textures/armor/graphene_model.png";
		if(this == ModItems.lead_boots)
			return "hfr:textures/armor/lead_boots.png";
		
		return null;
	}

}
