package com.hfr.tileentity.machine;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hfr.util.TileUtil;

import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMachineWindmill extends TileEntityLoadedBase implements IEnergyGenerator {

    final static public long maxPower = 500_000;
    private long power;

    @SideOnly(Side.CLIENT)
    public float rotation;

    public void updateEntity() {

        if (!worldObj.isRemote && operational()) {
            power += 2_250;
            if (power > maxPower) power = maxPower;
        }
    }

    public boolean operational() {

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) if (worldObj.getBlock(xCoord + i, yCoord + 32, zCoord + j)
                .getMaterial() != Material.air && !(i == 0 && j == 0)
                || !worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 32, zCoord + j)) return false;

        if (!TileUtil.hasPlatform(worldObj, xCoord, yCoord, zCoord)) {
            return false;
        }

        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        power = nbt.getLong("power");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setLong("power", power);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public void setPower(long arg0) {
        power = arg0;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
    }

    @Override
    public long getPower() {
        return power;
    }

}
