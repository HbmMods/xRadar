package com.hfr.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockBarbedWire extends Block {
	
	public BlockBarbedWire(Material mat) {
		
        super(mat);
    }
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		
    	ent.setInWeb();
        ent.attackEntityFrom(DamageSource.cactus, 2.0F);
    }

    public boolean isOpaqueCube() {
    	
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    	
        return null;
    }

    public int getRenderType() {
    	
        return 1;
    }

    public boolean renderAsNormalBlock() {
    	
        return false;
    }
}
