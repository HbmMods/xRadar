package com.hfr.util;

import java.util.Iterator;

import net.minecraft.block.Block;

public class RegistryUtil {
	
	//uses the name format [modid]_[unlocalizedname without periods or case sensitivity]
	public static Block getBlockByNameNoCaseOrPoint(String name) {
		
		Iterator itr = Block.blockRegistry.getKeys().iterator();
		name = name.toLowerCase();
		
		while(itr.hasNext()) {
			String original = (String) itr.next();
			String s = original.toLowerCase().replaceFirst(":", "_").replaceAll("\\.", "");
			
			if(name.equals(s))
				return (Block) Block.blockRegistry.getObject(original);
		}
		
		return null;
	}

}
