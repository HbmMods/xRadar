package com.hfr.tileentity.machine;

public class TileEntityMachineDistillery extends TileEntityMachineBase {

    public TileEntityMachineDistillery() {
        super(4);
    }

    @Override
    public String getName() {
        return "container.machineDistillery";
    }

    @Override
    public void updateEntity() {}
}
