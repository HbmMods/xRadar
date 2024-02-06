package com.hfr.clowder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import com.hfr.lib.RefStrings;

public enum ClowderFlag {

    // default case, acts like NULL
    NONE("none", false),
    TRICOLOR("tri"),
    TRICOLOR_VERTICAL("vtri"),
    BCROSS("bcross"),
    WCROSS("wcross"),
    JULIA("julia"),
    USSR("ussr"),
    ASTERISK("asterisk"),
    PONYCUM("pc", false);

    public String name = "";
    public boolean show = true;
    private ResourceLocation flag;
    private ResourceLocation overlay;

    public static final ResourceLocation WILDERNESS = new ResourceLocation(
        RefStrings.MODID + ":textures/flags/special_wilderness.png");
    public static final ResourceLocation SAFEZONE = new ResourceLocation(
        RefStrings.MODID + ":textures/flags/special_safezone.png");
    public static final ResourceLocation WARZONE = new ResourceLocation(
        RefStrings.MODID + ":textures/flags/special_warzone.png");

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

        flag = base ? new ResourceLocation(getCustomLoc() + ".png")
            : new ResourceLocation(RefStrings.MODID + ":textures/flags/flag_blank.png");
        overlay = over ? new ResourceLocation(getCustomLoc() + "_overlay.png")
            : new ResourceLocation(RefStrings.MODID + ":textures/flags/flag_blank.png");
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

        for (ClowderFlag flag : ClowderFlag.values()) {
            if (flag.name.toLowerCase()
                .equals(name.toLowerCase())) return flag;
        }

        return NONE;
    }

    public static List<String> getFlags() {

        List<String> list = new ArrayList();

        for (ClowderFlag flag : ClowderFlag.values()) {
            if (flag.show) list.add(flag.name);
        }

        return list;
    }
}
