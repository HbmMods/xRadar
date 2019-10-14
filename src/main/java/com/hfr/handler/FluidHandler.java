package com.hfr.handler;

import net.minecraft.init.Blocks;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHandler {

    public static final Fluid STEAM = new Fluid("hfr_steam") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    };

    public static void init() {
    	FluidRegistry.registerFluid(STEAM);
    }
}
