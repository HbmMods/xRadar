package com.hfr.items;

import com.hfr.render.model.ModelM65;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemGasMask extends ItemArmor {
	
	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;

	public ItemGasMask(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		return armorType == 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		
		if (this.modelM65 == null) {
			this.modelM65 = new ModelM65();
		}
		return this.modelM65;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {

		if(stack.getItem() == ModItems.gas_mask_grey)
			return "hfr:textures/armor/ModelM65Grey.png";
		
		if(stack.getItem() == ModItems.gas_mask_black)
			return "hfr:textures/armor/ModelM65Black.png";
		
		return "hfr:textures/armor/ModelM65.png";
	}
}
