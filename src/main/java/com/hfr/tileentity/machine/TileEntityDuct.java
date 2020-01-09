package com.hfr.tileentity.machine;

import com.hfr.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDuct extends TileEntity {
	
	public ForgeDirection[] connections = new ForgeDirection[6];
	
	public TileEntityDuct() { }
	
	@Override
	public void updateEntity() {
		
		this.updateConnections();
	}
	
	public void updateConnections() {
		if(isConnectable(worldObj.getBlock(xCoord, yCoord + 1, zCoord))) connections[0] = ForgeDirection.UP;
		else connections[0] = null;
		
		if(isConnectable(worldObj.getBlock(xCoord, yCoord - 1, zCoord))) connections[1] = ForgeDirection.DOWN;
		else connections[1] = null;
		
		if(isConnectable(worldObj.getBlock(xCoord, yCoord, zCoord - 1))) connections[2] = ForgeDirection.NORTH;
		else connections[2] = null;
		
		if(isConnectable(worldObj.getBlock(xCoord + 1, yCoord, zCoord))) connections[3] = ForgeDirection.EAST;
		else connections[3] = null;
		
		if(isConnectable(worldObj.getBlock(xCoord, yCoord, zCoord + 1))) connections[4] = ForgeDirection.SOUTH;
		else connections[4] = null;
		
		if(isConnectable(worldObj.getBlock(xCoord - 1, yCoord, zCoord))) connections[5] = ForgeDirection.WEST;
		else connections[5] = null;
	}
	
	public boolean isConnectable(Block b) {
		
		if(this.getBlockType() == ModBlocks.oil_duct) {
			
			return (b == ModBlocks.oil_duct || b == ModBlocks.machine_tank || b == ModBlocks.machine_derrick || b == ModBlocks.machine_refinery);
			
		} else {

			return (b == ModBlocks.gas_duct || b == ModBlocks.machine_tank || b == ModBlocks.machine_derrick);
		}
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
