package com.hfr.items;

import com.hfr.render.model.ModelSashimono;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemBanner extends ItemArmor {

	@SideOnly(Side.CLIENT)
	private ModelSashimono model;
	
	public ItemBanner(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if (this.model == null) {
			this.model = new ModelSashimono();
		}
		return this.model;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return "hfr:textures/armor/ModelSashimono.png";
	}

}
