package com.hfr.potion;

import java.lang.reflect.Field;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class HFRPotion extends Potion {

	public static HFRPotion emp;
	
	public HFRPotion(int id, boolean isBad, int color) {
		super(id, isBad, color);
	}

	public static void init() {
		emp = registerPotion(MainRegistry.empID, true, 0x0000FF, "potion.hfr_emp", 0, 0);
	}

	public static HFRPotion registerPotion(int id, boolean isBad, int color, String name, int x, int y) {

		if (id >= Potion.potionTypes.length) {

			Potion[] newArray = new Potion[Math.max(256, id)];
			System.arraycopy(Potion.potionTypes, 0, newArray, 0, Potion.potionTypes.length);
			
			Field field = ReflectionHelper.findField(Potion.class, new String[] { "field_76425_a", "potionTypes" });
			field.setAccessible(true);
			
			try {
				
				Field modfield = Field.class.getDeclaredField("modifiers");
				modfield.setAccessible(true);
				modfield.setInt(field, field.getModifiers() & 0xFFFFFFEF);
				field.set(null, newArray);
				
			} catch (Exception e) {
				
			}
		}
		
		HFRPotion effect = new HFRPotion(id, isBad, color);
		effect.setPotionName(name);
		effect.setIconIndex(x, y);
		
		return effect;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		ResourceLocation loc = new ResourceLocation("hfr","textures/gui/potions.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
		return super.getStatusIconIndex();
	}

	public void performEffect(EntityLivingBase entity, int level) {

		if(this == emp) {
		}
	}

	public boolean isReady(int par1, int par2) {

		return false;
	}
}
