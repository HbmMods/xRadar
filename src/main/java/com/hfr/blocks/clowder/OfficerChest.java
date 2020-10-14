package com.hfr.blocks.clowder;

import java.util.Random;

import com.hfr.blocks.ModBlocks;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.clowder.TileEntityOfficerChest;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class OfficerChest extends BlockContainer {

	public OfficerChest() {
        super(Material.wood);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityOfficerChest();
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_chest, world, x, y, z);
			return true;
		} else {
			return true;
		}
	}
	
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess worldObj, int x, int y, int z) {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }
    
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		
		byte b0 = 0;
		int l = MathHelper.floor_double((double) (p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			b0 = 2;
		}

		if (l == 1) {
			b0 = 5;
		}

		if (l == 2) {
			b0 = 3;
		}

		if (l == 3) {
			b0 = 4;
		}

		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, b0, 3);
	}
	
    private final Random field_149933_a = new Random();
	
	@Override
	public void breakBlock(World p_149749_1_, int x, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		
		IInventory tileentityfurnace = (IInventory) p_149749_1_.getTileEntity(x, p_149749_3_, p_149749_4_);

		if (tileentityfurnace != null) {
			
			for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
				
				ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

				if (itemstack != null) {
					
					float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
					float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
					float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						
						int j1 = this.field_149933_a.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(p_149749_1_, x + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem()
									.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) this.field_149933_a.nextGaussian() * f3;
						entityitem.motionY = (float) this.field_149933_a.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.field_149933_a.nextGaussian() * f3;
						p_149749_1_.spawnEntityInWorld(entityitem);
					}
				}
			}

			p_149749_1_.func_147453_f(x, p_149749_3_, p_149749_4_, p_149749_5_);
		}

        super.breakBlock(p_149749_1_, x, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
}
