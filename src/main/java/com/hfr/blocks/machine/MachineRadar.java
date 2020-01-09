package com.hfr.blocks.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineRadar extends BlockContainer {

	public MachineRadar(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineRadar();
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityMachineRadar entity = (TileEntityMachineRadar) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_radar, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    public boolean canProvidePower()
    {
        return !MainRegistry.comparator;
    }

    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int m)
    {
    	if(MainRegistry.comparator)
    		return 0;
    		
		TileEntityMachineRadar entity = (TileEntityMachineRadar) world.getTileEntity(x, y, z);
        return entity.getRedPower();
    }

    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int m)
    {
    	if(MainRegistry.comparator)
    		return 0;
    	
        return isProvidingWeakPower(world, x, y, z, m);
    }
	
	
    public boolean hasComparatorInputOverride()
    {
        return MainRegistry.comparator;
    }

    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    public int getComparatorInputOverride(World world, int x, int y, int z, int m)
    {
    	if(!MainRegistry.comparator)
    		return 0;
    	
    	TileEntityMachineRadar entity = (TileEntityMachineRadar) world.getTileEntity(x, y, z);
    	return entity.getRedPower();
    }
}
