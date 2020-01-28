package com.hfr.blocks.machine;

import com.hfr.blocks.BlockDummyable;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.machine.TileEntityMachineWindmill;
import com.hfr.tileentity.machine.TileEntityProxy;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineWindmill extends BlockDummyable {

	public MachineWindmill(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		//only cores with the "UNKNOWN" metadata can carry a tile entity
		if(meta >= 12)
			return new TileEntityMachineWindmill();
		
		if(meta >= 6)
			return new TileEntityProxy();
		
		return null;
	}
	
	@Override
	public int getRenderType(){
		return -1;
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
	public int[] getDimensions() {
		return MultiblockHandler.windmill;
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);

		if(world.isRemote)
			return;

		int k = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		ForgeDirection dir = ForgeDirection.NORTH;
		
		if(k == 0)
			dir = ForgeDirection.getOrientation(2);
		if(k == 1)
			dir = ForgeDirection.getOrientation(5);
		if(k == 2)
			dir = ForgeDirection.getOrientation(3);
		if(k == 3)
			dir = ForgeDirection.getOrientation(4);

		int xc = x - dir.offsetX * getOffset();
		int zc = z - dir.offsetZ * getOffset();

		this.makeExtra(world, xc + 1, y, zc);
		this.makeExtra(world, xc - 1, y, zc);
		this.makeExtra(world, xc, y, zc + 1);
		this.makeExtra(world, xc, y, zc - 1);
	}
}
