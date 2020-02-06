package com.hfr.blocks.weapon;

import java.util.Random;

import com.hfr.blocks.ModBlocks;
import com.hfr.entity.missile.*;
import com.hfr.items.ModItems;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.weapon.TileEntityLaunchPad;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LaunchPad extends BlockContainer {

	public TileEntityLaunchPad tetn = new TileEntityLaunchPad();
	public static boolean keepInventory = false;
    private final static Random field_149933_a = new Random();

	public LaunchPad(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLaunchPad();
	}
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	TileEntityLaunchPad tileentityfurnace = (TileEntityLaunchPad)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = LaunchPad.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = LaunchPad.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = LaunchPad.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = LaunchPad.field_149933_a.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float)LaunchPad.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)LaunchPad.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)LaunchPad.field_149933_a.nextGaussian() * f3;
                            p_149749_1_.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityLaunchPad entity = (TileEntityLaunchPad) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_launchpad, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z) && !p_149695_1_.isRemote)
        {
        	this.explode(p_149695_1_, x, y, z);
        }
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
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}
    
	public void explode(World p_149695_1_, int x, int y, int z)
    {
		int re = MainRegistry.padUse;
		
		TileEntityLaunchPad entity = (TileEntityLaunchPad) p_149695_1_.getTileEntity(x, y, z);
        //
        {
        	if(entity.slots[1] != null && (entity.slots[1].getItem() == ModItems.designator || entity.slots[1].getItem() == ModItems.designator_range || entity.slots[1].getItem() == ModItems.designator_manual) && entity.slots[1].stackTagCompound != null)
        	{
        		int xCoord = entity.slots[1].stackTagCompound.getInteger("xCoord");
        		int zCoord = entity.slots[1].stackTagCompound.getInteger("zCoord");
        		
        		if(xCoord == entity.xCoord && zCoord == entity.zCoord)
        		{
        			xCoord += 1;
        		}
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_at_1 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileAT missile = new EntityMissileAT(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_he_1 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileGeneric missile = new EntityMissileGeneric(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_he_2 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileStrong missile = new EntityMissileStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_he_3 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileBurst missile = new EntityMissileBurst(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		/////////////////////////////////////////////////////////////////////////////////////////////////////
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_incendiary_1 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileIncendiary missile = new EntityMissileIncendiary(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_incendiary_2 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileIncendiaryStrong missile = new EntityMissileIncendiaryStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_incendiary_3 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileInferno missile = new EntityMissileInferno(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		/////////////////////////////////////////////////////////////////////////////////////////////////////

        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_emp && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileEMPStrong missile = new EntityMissileEMPStrong(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_decoy && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileDecoy missile = new EntityMissileDecoy(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_nuclear && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileNuclear missile = new EntityMissileNuclear(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		
        		/////////////////////////////////////////////////////////////////////////////////////////////////////

        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_devon_1 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileDevon1 missile = new EntityMissileDevon1(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_devon_2 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileDevon2 missile = new EntityMissileDevon2(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_devon_3 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileDevon3 missile = new EntityMissileDevon3(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        		if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_cruise_at_1 && entity.storage.getEnergyStored() >= re)
        		{
            		EntityMissileCruise1 missile = new EntityMissileCruise1(p_149695_1_, x + 0.5F, y + 2F, z + 0.5F, xCoord, zCoord);
            		if (!p_149695_1_.isRemote)
            			p_149695_1_.spawnEntityInWorld(missile);
            		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
            	
            		entity.slots[0] = null;
            		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
        		}
        	}
        	
        	if(entity.slots[0] != null && entity.slots[0].getItem() == ModItems.missile_ab && entity.storage.getEnergyStored() >= re)
    		{
    			EntityMissileAntiBallistic missile = new EntityMissileAntiBallistic(p_149695_1_);
    			missile.posX = x + 0.5F;
    			missile.posY = y + 0.5F;
    			missile.posZ = z + 0.5F;
    			
        		if (!p_149695_1_.isRemote)
        			p_149695_1_.spawnEntityInWorld(missile);

        		entity.storage.setEnergyStored(entity.storage.getEnergyStored() - re);
        		
        		entity.slots[0] = null;
        		p_149695_1_.playSoundEffect(x, y, z, "hfr:weapon.missileTakeOff", 2.0F, 1.0F);
    		}
        }
    }

}
