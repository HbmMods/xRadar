package com.hfr.tileentity.machine;

import com.hfr.data.StockData;
import com.hfr.data.StockData.Stock;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.StockDisplayPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityDisplay extends TileEntity {

	public int selected = 0;
	public Stock stock;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		selected = nbt.getInteger("selected");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("selected", selected);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			StockData data = StockData.getData(worldObj);
			
			int i = selected % data.stocks.size();
			
			Stock stock = data.getByInt(i);
			
			PacketDispatcher.wrapper.sendToAllAround(new StockDisplayPacket(xCoord, yCoord, zCoord, stock.name, stock.shortname, stock.value, i), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 100));
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
