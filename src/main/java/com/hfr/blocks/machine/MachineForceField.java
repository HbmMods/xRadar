package com.hfr.blocks.machine;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hfr.blocks.ModBlocks;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityForceField;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MachineForceField extends BlockContainer {

    public MachineForceField(Material p_i45386_1_) {
        super(p_i45386_1_);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityForceField();
    }

    @Override
    public int getRenderType() {
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else if (!player.isSneaking()) {
            FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_forcefield, world, x, y, z);
            return true;
        } else {
            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        TileEntityForceField te = (TileEntityForceField) world.getTileEntity(x, y, z);

        if (te.isOn && te.cooldown == 0 && te.storage.getEnergyStored() > 0) {
            for (int i = 0; i < 4; i++) {
                float f = x;
                float f1 = y + 2F;
                float f2 = z;
                float f3 = 0.52F;
                float f4 = rand.nextFloat();
                float f5 = rand.nextFloat();

                if (te.color == 0xFF0000) world.spawnParticle("lava", f + f4, f1, f2 + f5, 0.0D, 0.0D, 0.0D);
                else world.spawnParticle("reddust", f + f4, f1, f2 + f5, 0.0D, 0.0D, 0.0D);
            }
        } else if (te.cooldown > 0) {
            for (int i = 0; i < 4; i++) {
                float f = x;
                float f1 = y + 2F;
                float f2 = z;
                float f3 = 0.52F;
                float f4 = rand.nextFloat();
                float f5 = rand.nextFloat();

                world.spawnParticle("smoke", f + f4, f1, f2 + f5, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private final Random field_149933_a = new Random();
    private static boolean keepInventory;

    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_,
        int p_149749_6_) {
        if (!keepInventory) {
            ISidedInventory tileentityfurnace = (ISidedInventory) p_149749_1_
                .getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
                            EntityItem entityitem = new EntityItem(
                                p_149749_1_,
                                p_149749_2_ + f,
                                p_149749_3_ + f1,
                                p_149749_4_ + f2,
                                new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound()) {
                                entityitem.getEntityItem()
                                    .setTagCompound(
                                        (NBTTagCompound) itemstack.getTagCompound()
                                            .copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float) this.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float) this.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float) this.field_149933_a.nextGaussian() * f3;
                            p_149749_1_.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

}
