package com.hfr.render.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.hfr.lib.RefStrings;

public class RenderAccessoryUtility {

    public static ResourceLocation hbm = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeHbm.png");
    public static ResourceLocation fire = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeFire.png");
    public static ResourceLocation test = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeTest.png");

    public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {

        String uuid = player.getUniqueID()
            .toString();
        String name = player.getDisplayName();

        if (uuid.equals("192af5d7-ed0f-48d8-bd89-9d41af8524f8")) {
            return hbm;
        }
        if (uuid.equals("6ea267b5-8981-4213-ac99-3ac0cc79d2e1")) {
            return fire;
        }
        if (name.startsWith("Player")) {
            return test;
        }

        return null;
    }

}
