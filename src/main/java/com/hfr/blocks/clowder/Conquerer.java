package com.hfr.blocks.clowder;

import com.hfr.clowder.Clowder;
import com.hfr.tileentity.clowder.TileEntityConquerer;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Conquerer extends BlockContainer {

	public Conquerer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityConquerer();
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		if(player instanceof EntityPlayer && !world.isRemote) {
			TileEntityConquerer flag = (TileEntityConquerer)world.getTileEntity(x, y, z);
			
			Clowder clowder = Clowder.getClowderFromPlayer((EntityPlayer)player);
			
			if(clowder != null && flag.canSeeSky()) {
				flag.owner = clowder;
				flag.markDirty();
			}
		}

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}

}
