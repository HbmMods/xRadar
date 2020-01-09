package com.hfr.blocks.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityMachineBuilder;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineBuilder extends BlockContainer {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public MachineBuilder(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineBuilder();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":builder_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":builder_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_builder, world, x, y, z);
			return true;
		} else {

			TileEntityMachineBuilder builder = (TileEntityMachineBuilder)world.getTileEntity(x, y, z);
			int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			
			if(builder.preview == null)
				return false;
			
			if(i == 0)
			{
				do {
					builder.oz++;
				} while(builder.doesIntersect());
			}
			if(i == 1)
			{
				do {
					builder.ox--;
				} while(builder.doesIntersect());
			}
			if(i == 2)
			{
				do {
					builder.oz--;
				} while(builder.doesIntersect());
			}
			if(i == 3)
			{
				do {
					builder.ox++;
				} while(builder.doesIntersect());
			}
			
			return true;
		}
	}
	
	@Override
	public int getRenderType(){
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
