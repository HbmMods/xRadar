package com.hfr.blocks.props;

import com.hfr.blocks.BlockDummyable;
import com.hfr.handler.MultiblockHandler;
import com.hfr.lib.RefStrings;
import com.hfr.main.ResourceManager;
import com.hfr.tileentity.prop.TileEntityBerlin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PropBerlin extends BlockDummyable {

	public PropBerlin(Material mat) {
		super(mat);
        this.isBlockContainer = false;
	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon iconModel;

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= ForgeDirection.UNKNOWN.ordinal())
			return new TileEntityBerlin();
		
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconModel = iconRegister.registerIcon(RefStrings.MODID + ":berlin_wall");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":berlin_wall_icon");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(side == 0)
			return iconModel;
		
		return blockIcon;
	}

	@Override
	public int[] getDimensions() {
		return MultiblockHandler.berlin;
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	@Override
	public int getRenderType(){
		return ResourceManager.id_berliner;
	}

}
