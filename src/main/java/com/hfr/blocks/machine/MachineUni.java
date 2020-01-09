package com.hfr.blocks.machine;

import java.util.Random;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.handler.MultiblockHandler;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityMachineUni;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineUni extends BlockDummyable {

	public MachineUni(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		//only cores with the "UNKNOWN" metadata can carry a tile entity
		if(meta >= ForgeDirection.UNKNOWN.ordinal())
			return new TileEntityMachineUni();
		
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

	/*@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}*/
	
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
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_uni, world, pos[0], pos[1], pos[2]);
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
        
		if(i >= ForgeDirection.UNKNOWN.ordinal()) {
			Ownership owner = ClowderTerritory.getOwnerFromInts(x, z);
			
			if(owner != null && owner.zone == Zone.FACTION) {

				TileEntityMachineUni statue = (TileEntityMachineUni)world.getTileEntity(x, y, z);
				
				if(statue.operational())
					owner.owner.addPrestigeGen(-Clowder.tentRate, world);
			}
		}

        super.breakBlock(world, x, y, z, p_149749_5_, i);
    }

	@Override
	public int[] getDimensions() {
		return MultiblockHandler.uni;
	}

	@Override
	public int getOffset() {
		return 4;
	}

}
