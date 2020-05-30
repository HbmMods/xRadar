package com.hfr.config;

import java.util.ArrayList;
import java.util.List;

import com.hfr.lib.RefStrings;

import cpw.mods.fml.client.config.DummyConfigElement.DummyCategoryElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.IConfigEntry;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;

public class XRConfigGUI extends GuiConfig {
		
	public XRConfigGUI(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), RefStrings.MODID, false, false, "Cool Options");
	}
	
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList();
		list.add(categoryElement("CHATFILTER", CategoryChatFilter.class));
		return list;
	}
	
	private static DummyCategoryElement categoryElement(String categoryName, Class<? extends IConfigEntry> categoryClass) {
		return new DummyCategoryElement(categoryName, categoryName, categoryClass);
	}
	
	public static class CategoryChatFilter extends ConfigCategory {
		
		public CategoryChatFilter(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
			super(owningScreen, owningEntryList, configElement);
		}
		
		@Override
		protected GuiScreen buildChildScreen() {
			return super.buildChildScreen("CHATFILTER", owningScreen, configElement);
		}
	}
}