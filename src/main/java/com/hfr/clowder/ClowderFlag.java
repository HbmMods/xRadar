package com.hfr.clowder;

import java.util.ArrayList;
import java.util.List;

import com.hfr.lib.RefStrings;

import net.minecraft.util.ResourceLocation;

public enum ClowderFlag {

	//default case, acts like NULL
	NONE("none", false),
	TRICOLOR("tri"),
	TRICOLOR_VERTICAL("vtri"),
	BCROSS("bcross"),
	WCROSS("wcross"),
	USA("usa"),
	VOLTZ("voltz", false),
	PONYCUM("pc", false);

	private String name = "";
	private boolean show = true;
	private ResourceLocation flag;
	private ResourceLocation overlay;

	public static final ResourceLocation WILDERNESS = new ResourceLocation(RefStrings.MODID + ":textures/flags/special_wilderness.png");
	public static final ResourceLocation SAFEZONE = new ResourceLocation(RefStrings.MODID + ":textures/flags/special_safezone.png");
	public static final ResourceLocation WARZONE = new ResourceLocation(RefStrings.MODID + ":textures/flags/special_warzone.png");
	
	private ClowderFlag(String name) {
		this.name = name;

		flag = new ResourceLocation(getLoc() + ".png");
		overlay = new ResourceLocation(getLoc() + "_overlay.png");
	}
	
	private ClowderFlag(String name, boolean show) {
		this.name = name;
		this.show = show;

		flag = new ResourceLocation(getLoc() + ".png");
		overlay = new ResourceLocation(getLoc() + "_overlay.png");
	}
	
	private String getLoc() {
		return RefStrings.MODID + ":textures/flags/flag_" + name;
	}
	
	public ResourceLocation getFlag() {
		return flag;
	}
	
	public ResourceLocation getFlagOverlay() {
		return overlay;
	}
	
	public static ClowderFlag getFromName(String name) {
		
		for(ClowderFlag flag : ClowderFlag.values()) {
			if(flag.name.equals(name))
				return flag;
		}
		
		return NONE;
	}
	
	public static List<String> getFlags() {
		
		List<String> list = new ArrayList();
		
		for(ClowderFlag flag : ClowderFlag.values()) {
			if(flag.show)
				list.add(flag.name);
		}
		
		return list;
	}
}
