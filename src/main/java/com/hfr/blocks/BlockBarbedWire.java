package com.hfr.blocks;

import com.hfr.main.MainRegistry;
import com.hfr.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBarbedWire extends Block {
	
	public BlockBarbedWire(Material mat) {
		
        super(mat);
    }
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		
		if(!world.isRemote) {
			if(ent.getClass().getCanonicalName().equals("com.flansmod.common.driveables.EntityWheel")) {
				world.func_147480_a(x, y, z, false);
				return;
			}
			if(ent.getClass().getCanonicalName().equals("com.flansmod.common.driveables.EntityVehicle")) {
				world.func_147480_a(x, y, z, false);
				return;
			}
			if(ent.getClass().getCanonicalName().equals("com.flansmod.common.driveables.EntitySeat")) {
				world.func_147480_a(x, y, z, false);
				return;
			}
		}
		
    	ent.setInWeb();
        ent.attackEntityFrom(MainRegistry.wire, 2.0F);
    }

    public boolean isOpaqueCube() {
    	
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	
        return null;
    }

    public int getRenderType() {
    	
        return ResourceManager.id_barbed;
    }

    public boolean renderAsNormalBlock() {
    	
        return false;
    }

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
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
	}
}
