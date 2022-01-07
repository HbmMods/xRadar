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
	JULIA("julia"),
	USSR("ussr"),
	CRESCENT("islammoon"),
	ASTERISK("asterisk"),
	AFGHANISTAN("afghanistan"),
	ALBANIA("albania"),
	ALGERIA("algeria"),
	AMERICA("america"),
	AMERICACALIPHATE("americaCaliphate"),
	ARMENIYIKES("armeniyikes"),
	AZERBAIJAN("azerbaijan"),
	BANGLADESH("bangladesh"),
	BRITAIN("britbong"),
	LONDONISTAN("britishCaliphate"),
	CHAD("chad"),
	CHECHNYA("chechen"),
	UYGHERS("chinaMuslim"),
	EGYPTKINGDOM("egyptKingdom"),
	MAMLUK("egyptMedeieval"),
	EGYPTMODERN("egyptRepublic"),
	EU("euCaliphate"),
	FRANCE("france"),
	FRENCHSUDAN("franceSudan"),
	GERMANY("germany"),
	GERMANISTAN("germanyCaliphate"),
	GREEK("grekoid"),
	GREEKALLAH("grekoidCaliphate"),
	HEZBOLLAH("hezbollah"),
	INDIA("india"),
	MUGHAL("indiaMughalMuslim"),
	IRAN("iran"),
	ACHAEMENID("iranAncient"),
	SASSANID("iranClassic"),
	SHAH("iranImperial"),
	SADDAM("iraqBaathist"),
	IRAQKINGDOM("iraqKingdom"),
	IRAQ("iraqModern"),
	ISIS("isis"),
	ISRAEL("israel"),
	JUDEA("israelAncient"),
	ISRAELCIVIL("israelCivil"),
	ISRAELISTAN("israelMuslim"),
	ISRAELNAVY("israelNavy"),
	MOSSAD("israelPresident"),
	ITALY("italy"),
	ITALISTAN("italyCaliphate"),
	JORDAN("jordan"),
	KAZAKHSTAN("kazakhstan"),
	TRANISTAN("kurdistan"),
	KUWAIT("kuwait"),
	KRYGYZSTAN("kyrgyzstan"),
	LEBANON("lebanon"),
	LIBYA("libya"),
	MALAYSIA("malaysia"),
	MALI("mali"),
	MAURITANIA("mauritania"),
	MOROCCO("morocco"),
	NIGGER("niger"),
	PAKISTAN("pakistan"),
	FATAH("palestineFatah"),
	HAMAS("palestineHamas"),
	QATAR("qatar"),
	RUSSIA("russia"),
	SOVIET("russiaSoviet"),
	USSRALLAH("russiaSovietCaliphate"),
	SAUDI("saudi"),
	SHAHADA("shahada"),
	SINGAPORE("singapore"),
	SOMALIA("somalia"),
	SPAIN("spain"),
	GRANADA("spainGranadaMuslim"),
	SYRIA("syria"),
	SYRIAFAKE("syriaRebel"),
	TAJIKISTAN("tajikistan"),
	TALIBAN("taliban"),
	TEXAS("texasCaliphate"),
	TUNISIA("tunisia"),
	TURKIYE("turkey"),
	TURKMENISTAN("turkmenistan"),
	UKRAINE("ukraine"),
	GOLDENHORDE("ukraineMongolMuslim"),
	UAE("unitedArabEmirates"),
	UZBEKISTAN("uzbekistan"),
	YEMENKINGDOM("yemenKingdom"),
	YEMEN("yemenRepublic"),
	PONYCUM("pc", false);
	
	//too lazy to find out how to fix custom flags
	
	

	public String name = "";
	public boolean show = true;
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
	
	private ClowderFlag(String name, boolean show, boolean base, boolean over) {
		this.name = name;
		this.show = show;

		flag = base ? new ResourceLocation(getCustomLoc() + ".png") : new ResourceLocation(RefStrings.MODID + ":textures/flags/flag_blank.png");
		overlay = over ? new ResourceLocation(getCustomLoc() + "_overlay.png") : new ResourceLocation(RefStrings.MODID + ":textures/flags/flag_blank.png");
	}
	
	private String getLoc() {
		return RefStrings.MODID + ":textures/flags/flag_" + name;
	}
	
	private String getCustomLoc() {
		return RefStrings.MODID + ":textures/customflags/flag_" + name;
	}
	
	public ResourceLocation getFlag() {
		return flag;
	}
	
	public ResourceLocation getFlagOverlay() {
		return overlay;
	}
	
	public static ClowderFlag getFromName(String name) {
		
		for(ClowderFlag flag : ClowderFlag.values()) {
			if(flag.name.toLowerCase().equals(name.toLowerCase()))
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
