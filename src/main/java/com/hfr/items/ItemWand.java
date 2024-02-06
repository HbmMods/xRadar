package com.hfr.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.hfr.data.AntiMobData;

public class ItemWand extends Item {

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (world.getBlock(x, y, z) == Blocks.redstone_block) {

            if (!world.isRemote) {
                AntiMobData.getData(world).list.clear();
                AntiMobData.getData(world)
                    .markDirty();
            }

            world.playSoundAtEntity(player, "hfr:item.toggle", 0.25F, 0.5F);

            return true;
        }

        // has no compound, add POS 1
        if (!stack.hasTagCompound()) {

            stack.stackTagCompound = new NBTTagCompound();
            stack.stackTagCompound.setInteger("xCoord", x);
            stack.stackTagCompound.setInteger("zCoord", z);

            world.playSoundAtEntity(player, "hfr:item.techBoop", 1.0F, 1.0F);

            // POS 1 already loaded, create area and delete compound
        } else {

            if (!world.isRemote) AntiMobData.getData(world)
                .addArea(
                    stack.stackTagCompound.getInteger("xCoord"),
                    stack.stackTagCompound.getInteger("zCoord"),
                    x,
                    z);

            world.playSoundAtEntity(player, "hfr:item.techBleep", 1.0F, 1.0F);

            stack.stackTagCompound = null;
        }

        return true;
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
        if (itemstack.stackTagCompound != null) {
            list.add("POS:");
            list.add("X: " + String.valueOf(itemstack.stackTagCompound.getInteger("xCoord")));
            list.add("Z: " + String.valueOf(itemstack.stackTagCompound.getInteger("zCoord")));
        } else {
            list.add("Please select a starting position.");
        }
    }

}
