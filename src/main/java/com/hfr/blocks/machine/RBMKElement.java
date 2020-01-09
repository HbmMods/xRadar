package com.hfr.blocks.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityRBMKElement;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKElement extends BlockContainer {

	public RBMKElement(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	public int getRenderType(){
		return 10000;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_rbmk, world, x, y, z);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityRBMKElement();
	}
}
