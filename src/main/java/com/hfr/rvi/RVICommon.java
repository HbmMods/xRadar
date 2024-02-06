package com.hfr.rvi;

import net.minecraft.util.ResourceLocation;

import com.hfr.lib.RefStrings;

public class RVICommon {

    private static final ResourceLocation hudVehicle = new ResourceLocation(
        RefStrings.MODID + ":textures/hud/hudVehicle.png");
    private static final ResourceLocation hudShip = new ResourceLocation(
        RefStrings.MODID + ":textures/hud/hudShip.png");
    private static final ResourceLocation hudPlane = new ResourceLocation(
        RefStrings.MODID + ":textures/hud/hudPlane.png");
    private static final ResourceLocation hudHeli = new ResourceLocation(
        RefStrings.MODID + ":textures/hud/hudHeli.png");
    private static final ResourceLocation hudFriend = new ResourceLocation(
        RefStrings.MODID + ":textures/hud/hudSquareFriend.png");
    private static final ResourceLocation hudEnemy = new ResourceLocation(
        RefStrings.MODID + ":textures/hud/hudSquareEnemy.png");

    public static class Indicator {

        public double x;
        public double y;
        public double z;
        public RVIType type;

        public Indicator(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.type = RVIType.GENERIC;
        }

        public Indicator(double x, double y, double z, RVIType type) {
            this(x, y, z);
            this.type = type;
        }
    }

    public static enum RVIType {

        GENERIC(new ResourceLocation(RefStrings.MODID + ":textures/hud/toaster.png")),
        VEHICLE(hudVehicle),
        SHIP(hudShip),
        PLANE(hudPlane),
        HELI(hudHeli),
        FRIEND(hudFriend),
        ENEMY(hudEnemy);

        public ResourceLocation texture;

        private RVIType(ResourceLocation texture) {
            this.texture = texture;
        }
    }
}
