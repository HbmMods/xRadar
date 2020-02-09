package com.hfr.blocks.machine;

import java.util.Random;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.handler.MultiblockHandler;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityComboProxy;
import com.hfr.tileentity.machine.TileEntityMachineTurbine;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTurbine extends BlockDummyable {

	public MachineTurbine(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		//only cores with the "UNKNOWN" metadata can carry a tile entity
		if(meta >= 12)
			return new TileEntityMachineTurbine();
		
		if(meta >= 6)
			return new TileEntityComboProxy();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return MultiblockHandler.turbine;
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_turbine, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
	
    private final Random field_149933_a = new Random();
	private static boolean keepInventory;
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int i)
    {
		if(i < 12) {
	        super.breakBlock(world, x, y, z, p_149749_5_, i);
			return;
		}
		
        if (!keepInventory)
        {
        	ISidedInventory tileentityfurnace = (ISidedInventory)world.getTileEntity(x, y, z);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.field_149933_a.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float)this.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)this.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)this.field_149933_a.nextGaussian() * f3;
                            world.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, p_149749_5_);
            }
        }

        super.breakBlock(world, x, y, z, p_149749_5_, i);
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
		

		ForgeDirection dir2 = dir.getRotation(ForgeDirection.UP);
		
		this.makeExtra(world, x + dir.offsetX * -4, y, z + dir.offsetZ * -4);
		
		TileEntity te1 = world.getTileEntity(x + dir.offsetX * -4, y, z + dir.offsetZ * -4);
		if(te1 instanceof TileEntityComboProxy) {
			((TileEntityComboProxy)te1).connectEnergy = true;
			((TileEntityComboProxy)te1).connectFluid = false;
			te1.markDirty();
		}
		
		this.makeExtra(world, x, y, z);
		
		TileEntity te2 = world.getTileEntity(x, y, z);
		if(te2 instanceof TileEntityComboProxy) {
			((TileEntityComboProxy)te2).connectEnergy = false;
			((TileEntityComboProxy)te2).connectFluid = true;
			te2.markDirty();
		}

		int xc = x - dir.offsetX;
		int zc = z - dir.offsetZ;
		
		this.makeExtra(world, xc + dir2.offsetX, y, zc + dir2.offsetZ);
		
		TileEntity te3 = world.getTileEntity(xc + dir2.offsetX, y, zc + dir2.offsetZ);
		if(te3 instanceof TileEntityComboProxy) {
			((TileEntityComboProxy)te3).connectEnergy = false;
			((TileEntityComboProxy)te3).connectFluid = true;
			te3.markDirty();
		}
		
		this.makeExtra(world, xc - dir2.offsetX, y, zc - dir2.offsetZ);
		
		TileEntity te4 = world.getTileEntity(xc - dir2.offsetX, y, zc - dir2.offsetZ);
		if(te4 instanceof TileEntityComboProxy) {
			((TileEntityComboProxy)te4).connectEnergy = false;
			((TileEntityComboProxy)te4).connectFluid = true;
			te4.markDirty();
		}
	}

}
