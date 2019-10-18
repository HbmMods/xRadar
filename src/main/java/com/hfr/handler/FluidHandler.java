package com.hfr.handler;

import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidHandler {

    public static final Fluid STEAM = new Fluid("hfr_steam") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(523);

    public static void init() {
    	FluidRegistry.registerFluid(STEAM);
    }
}
