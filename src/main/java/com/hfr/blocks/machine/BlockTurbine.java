package com.hfr.blocks.machine;

import com.hfr.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockTurbine extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	protected IIcon iconSide;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":hydro_turbine");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":hydro_wall");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if((meta == 2 || meta == 0) && (side == 2 || side == 3))
			return iconSide;
		
		if(meta == 3 && (side == 4 || side == 5))
			return iconSide;
		
		return blockIcon;
	}

	public BlockTurbine(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.isBlockContainer = false;
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
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

}
