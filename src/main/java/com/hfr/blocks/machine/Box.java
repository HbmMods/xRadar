package com.hfr.blocks.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityBox;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class Box extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	protected IIcon iconSide;
	@SideOnly(Side.CLIENT)
	protected IIcon iconTop;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":cardboard_side");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":cardboard_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":cardboard_base_riffled");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(side == 1)
			return iconTop;
		
		if(side == 2 || side == 3)
			return iconSide;
		
		return blockIcon;
	}

	public Box(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBox();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_box, world, x, y, z);

			return true;
		} else {
			return false;
		}
	}

}
