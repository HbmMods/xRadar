package com.hfr.blocks;

import java.util.HashSet;
import java.util.Set;

import com.hfr.handler.MultiblockHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockDummyable extends BlockContainer {

	public BlockDummyable(Material mat) {
		super(mat);
	}
	
	/// BLOCK METADATA ///
	
	//0-5 		dummy rotation 		(none unused)
	//6-9 		extra 				(4 rotations with flag)
	//10-15 	block rotation 		(2 unused)
	
	public static final int offset = 10;
	
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	super.onNeighborBlockChange(world, x, y, z, block);
    	
    	if(world.isRemote)
    		return;
    	
    	ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();
    	Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    	
    	if(b != this) {
    		world.setBlockToAir(x, y, z);
    	}
    }
    
    public int[] findCore(World world, int x, int y, int z) {
    	positions.clear();
    	return findCoreRec(world, x, y, z);
    }
    
    Set<int[]> positions = new HashSet();
    public int[] findCoreRec(World world, int x, int y, int z) {
    	
    	int[] pos = new int[] { x, y, z };
    	
    	//if the block matches and the orientation is "UNKNOWN", it's the core
    	if(world.getBlock(x, y, z) == this && ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)) == ForgeDirection.UNKNOWN)
    		return pos;
    	
    	if(positions.contains(pos))
    		return null;

    	ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)).getOpposite();
    	Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    	
    	if(b != this) {
    		return null;
    	}
    	
    	positions.add(pos);
    	
    	return findCoreRec(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int o = -getOffset();
		
		ForgeDirection dir = ForgeDirection.NORTH;
		
		if(i == 0)
		{
			//2
			dir = ForgeDirection.getOrientation(2);
		}
		if(i == 1)
		{
			//5
			dir = ForgeDirection.getOrientation(5);
		}
		if(i == 2)
		{
			//3
			dir = ForgeDirection.getOrientation(3);
		}
		if(i == 3)
		{
			//4
			dir = ForgeDirection.getOrientation(4);
		}
		
		if(!MultiblockHandler.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir)) {
			world.func_147480_a(x, y, z, true);
			return;
		}
		
		world.setBlock(x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, this, dir.ordinal() + offset, 3);
		MultiblockHandler.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), this, dir);

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i)
    {
		System.out.println("" + i);
		
		if(i >= ForgeDirection.UNKNOWN.ordinal()) {
			ForgeDirection d = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) - offset);
			MultiblockHandler.emptySpace(world, x, y, z, getDimensions(), this, d);
			System.out.println("core!");
		} else {

	    	ForgeDirection dir = ForgeDirection.getOrientation(i).getOpposite();
			int[] pos = findCore(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(pos != null) {

				ForgeDirection d = ForgeDirection.getOrientation(world.getBlockMetadata(pos[0], pos[1], pos[2]) - offset);
				System.out.println("found core!");
				world.setBlockToAir(pos[0], pos[1], pos[2]);
				//MultiblockHandler.emptySpace(world, pos[0], pos[1], pos[2], getDimensions(), this, d);
			}
		}
		
		
		super.breakBlock(world, x, y, z, b, i);
    }

	public abstract int[] getDimensions();
	public abstract int getOffset();

}
