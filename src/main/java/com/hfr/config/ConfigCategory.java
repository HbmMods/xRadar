package com.hfr.config;

import java.util.List;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.GuiConfigEntries.CategoryEntry;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public abstract class ConfigCategory extends CategoryEntry {
	
	public ConfigCategory(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
		super(owningScreen, owningEntryList, configElement);
	}

	public GuiScreen buildChildScreen(String categoryName, GuiConfig owningScreen, IConfigElement configElement) {
		Configuration config = MainRegistry.config;
		ConfigElement newElement = new ConfigElement(config.getCategory(categoryName));
		List<IConfigElement> propertiesOnScreen = newElement.getChildElements();
		
		return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID, configElement.requiresWorldRestart()
				|| owningScreen.allRequireWorldRestart, configElement.requiresMcRestart()
				|| owningScreen.allRequireMcRestart, "Cool Config");
	}
}
