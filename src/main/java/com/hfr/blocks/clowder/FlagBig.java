package com.hfr.blocks.clowder;

import java.util.Random;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.data.ClowderData;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.clowder.TileEntityFlagBig;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FlagBig extends BlockContainer {

	public FlagBig(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFlagBig();
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
	public Item getItemDropped(int i, Random r, int j) {
		return null;
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

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
			
		}else if(!player.isSneaking()) {
			
			ItemStack held = player.getHeldItem();
			
			if(held != null) {
				
				if(held.getItem() == ModItems.hatchet) {
					
					if(held.hasTagCompound()) {
						
						TileEntityFlagBig flag = (TileEntityFlagBig)world.getTileEntity(x, y, z);
						
						if(flag != null) {
							
							NBTTagCompound nbt = held.stackTagCompound;
							
							flag.addClaim(
									nbt.getInteger("xCoord1"),
									nbt.getInteger("zCoord1"),
									nbt.getInteger("xCoord2"),
									nbt.getInteger("zCoord2")
									);
						}
					}
					
					return true;
				}
				
				if(player.inventory.hasItem(ModItems.debug)) {
					
					TileEntityFlagBig flag = (TileEntityFlagBig)world.getTileEntity(x, y, z);
					flag.slots[1] = player.getHeldItem().copy();
					flag.slots[1].stackSize = 1;
					flag.markDirty();
					
					return true;
				}
			}
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_flag_big, world, x, y, z);
			
			return true;
			
		} else {
			return false;
		}
	}
}
