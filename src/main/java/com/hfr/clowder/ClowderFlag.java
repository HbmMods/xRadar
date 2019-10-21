package com.hfr.clowder;

import com.hfr.lib.RefStrings;

import net.minecraft.util.ResourceLocation;

public enum ClowderFlag {

	//default case, acts like NULL
	NONE("none"),
	TRICOLOR("tri"),
	TRICOLOR_VERTICAL("vtri"),
	BCROSS("bcross"),
	WCROSS("wcross"),
	USA("usa"),
	PONYCUM("pc");
	
	String name;
	
	private ClowderFlag(String name) {
		this.name = name;
	}
	
	private String getLoc() {
		return RefStrings.MODID + ":textures/flags/flag_" + name;
	}
	
	public ResourceLocation getFlag() {
		return new ResourceLocation(getLoc() + ".png");
	}
	
	public ResourceLocation getFlagOverlay() {
		return new ResourceLocation(getLoc() + "_overlay.png");
	}
	
	public ClowderFlag getFromName(String name) {
		
		for(ClowderFlag flag : ClowderFlag.values()) {
			if(flag.name.equals(name))
				return flag;
		}
		
		return NONE;
	}
}
