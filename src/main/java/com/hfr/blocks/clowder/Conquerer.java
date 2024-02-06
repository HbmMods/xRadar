package com.hfr.blocks.clowder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.tileentity.clowder.TileEntityConquerer;

public class Conquerer extends BlockContainer {

    public Conquerer(Material mat) {
        super(mat);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityConquerer();
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

        int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (i == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (i == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (i == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (i == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (player instanceof EntityPlayer && !world.isRemote) {
            TileEntityConquerer flag = (TileEntityConquerer) world.getTileEntity(x, y, z);

            Clowder clowder = Clowder.getClowderFromPlayer((EntityPlayer) player);
            flag.owner = clowder;

            if (clowder != null && flag.checkBorder(x, z) && flag.canSeeSky() && noProximity(world, x, y, z)) {
                flag.owner.addPrestigeReq(0.2F, world);
                flag.markDirty();
            } else {
                flag.owner = null;
                ((EntityPlayer) player).addChatMessage(
                    new ChatComponentText(
                        EnumChatFormatting.RED + "You won't be able to raise this flag. This may be due to:"));
                ((EntityPlayer) player)
                    .addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-You not being in any faction"));
                ((EntityPlayer) player)
                    .addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "-The flag not having sky access"));
                ((EntityPlayer) player).addChatMessage(
                    new ChatComponentText(EnumChatFormatting.RED + "-The flag not being in a foreign border chunk"));
                ((EntityPlayer) player).addChatMessage(
                    new ChatComponentText(
                        EnumChatFormatting.RED + "-The enemy faction or your faction not being raidable"));
                ((EntityPlayer) player).addChatMessage(
                    new ChatComponentText(
                        EnumChatFormatting.RED + "-The flag being too close to another conquest flag"));
            }
        }

        super.onBlockPlacedBy(world, x, y, z, player, itemStack);
    }

    public boolean noProximity(World world, int x, int y, int z) {

        int range = 4;

        for (int ix = x - range; ix <= x + range; ix++) {
            for (int iy = y - 3; iy <= y + 3; iy++) {
                for (int iz = z - range; iz <= z + range; iz++) {

                    if (ix == x && iy == y && iz == z) continue;

                    if (world.getBlock(ix, iy, iz) == ModBlocks.clowder_conquerer) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block b, int i) {
        TileEntityConquerer flag = (TileEntityConquerer) world.getTileEntity(x, y, z);
        if (flag != null && flag.owner != null) {
            flag.owner.addPrestigeReq(-0.2F, world);
        }

        super.breakBlock(world, x, y, z, b, i);
    }
}
