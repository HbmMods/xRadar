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
    
    public static final Fluid OIL = new Fluid("hfr_oil") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(300);
    
    public static final Fluid GAS = new Fluid("hfr_gas") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(300);
    
    public static final Fluid PETROIL = new Fluid("hfr_petroil") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(300);
    
    public static final Fluid DIESEL = new Fluid("hfr_diesel") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(300);
    
    public static final Fluid KEROSENE = new Fluid("hfr_kersoene") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(300);
    
    public static final Fluid PETROLEUM = new Fluid("hfr_petroleum") {
        @Override
        public String getLocalizedName() {
            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
        }
    }.setGaseous(true).setTemperature(300);

    public static void init() {
    	FluidRegistry.registerFluid(STEAM);
    	FluidRegistry.registerFluid(OIL);
    	FluidRegistry.registerFluid(GAS);
    	FluidRegistry.registerFluid(PETROIL);
    	FluidRegistry.registerFluid(DIESEL);
    	FluidRegistry.registerFluid(KEROSENE);
    	FluidRegistry.registerFluid(PETROLEUM);
    }
}
