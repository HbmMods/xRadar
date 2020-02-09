package com.hfr.handler;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MultiblockHandler {
	
	//when looking north
	//											U  D  N  S  W  E
	public static int[] uni = 		new int[] { 3, 0, 4, 4, 4, 4 };
	public static int[] hesco = 	new int[] { 2, 0, 1, 0, 1, 0 };
	public static int[] wall = 		new int[] { 3, 0, 0, 0, 1, 1 };
	public static int[] berlin = 	new int[] { 5, 0, 0, 0, 1, 1 };
	public static int[] tent = 		new int[] { 2, 0, 2, 2, 2, 2 };
	public static int[] statue = 	new int[] { 3, 0, 1, 1, 1, 1 };
	public static int[] mill = 		new int[] { 5, 0, 1, 1, 1, 1 };
	public static int[] furnace = 	new int[] { 4, 0, 1, 1, 1, 1 };
	public static int[] mine = 		new int[] { 3, 0, 2, 2, 1, 1 };
	public static int[] windmill = 	new int[] { 31, 0, 1, 1, 1, 1 };
	public static int[] turbine = 	new int[] { 1, 0, 3, 1, 1, 1 };
	
	public static boolean checkSpace(World world, int x, int y, int z, int[] dim, int ox, int oy, int oz, ForgeDirection dir) {
		
		if(dim == null || dim.length != 6)
			return false;
		
		int count = 0;
		
		int[] rot = rotate(dim, dir);

		for(int a = x - rot[4]; a <= x + rot[5]; a++) {
			for(int b = y - rot[1]; b <= y + rot[0]; b++) {
				for(int c = z - rot[2]; c <= z + rot[3]; c++) {
					
					//if the position matches the just placed block, the space counts as unoccupied
					if(a == ox && b == oy && c == oz)
						continue;
					
					if(!world.getBlock(a, b, c).canPlaceBlockAt(world, a, b, c)) {
						return false;
					}
					
					count++;
					
					if(count > 2000) {
						System.out.println("checkspace: ded " + a + " " + b + " " + c + " " + x + " " + y + " " + z);
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static void fillSpace(World world, int x, int y, int z, int[] dim, Block block, ForgeDirection dir) {
		
		if(dim == null || dim.length != 6)
			return;
		
		int count = 0;
		
		int[] rot = rotate(dim, dir);

		for(int a = x - rot[4]; a <= x + rot[5]; a++) {
			for(int b = y - rot[1]; b <= y + rot[0]; b++) {
				for(int c = z - rot[2]; c <= z + rot[3]; c++) {
					
					int meta = 0;
					
					if(b < y) {
						meta = ForgeDirection.DOWN.ordinal();
					} else if(b > y) {
						meta = ForgeDirection.UP.ordinal();
					} else if(a < x) {
						meta = ForgeDirection.WEST.ordinal();
					} else if(a > x) {
						meta = ForgeDirection.EAST.ordinal();
					} else if(c < z) {
						meta = ForgeDirection.NORTH.ordinal();
					} else if(c > z) {
						meta = ForgeDirection.SOUTH.ordinal();
					} else {
						continue;
					}
					
					world.setBlock(a, b, c, block, meta, 3);
					
					count++;
					
					if(count > 2000) {
						System.out.println("fillspace: ded " + a + " " + b + " " + c + " " + x + " " + y + " " + z);
						return;
					}
				}
			}
		}
	}
	
	@Deprecated
	public static void emptySpace(World world, int x, int y, int z, int[] dim, Block block, ForgeDirection dir) {
		
		if(dim == null || dim.length != 6)
			return;

		int count = 0;
		
		System.out.println("emptyspace is deprecated and shouldn't even be executed");
		
		int[] rot = rotate(dim, dir);

		for(int a = x - rot[4]; a <= x + rot[5]; a++) {
			for(int b = y - rot[1]; b <= y + rot[0]; b++) {
				for(int c = z - rot[2]; c <= z + rot[3]; c++) {
					
					if(world.getBlock(a, b, c) == block)
						world.setBlockToAir(a, b, c);
					
					count++;
					
					if(count > 2000) {
						System.out.println("emptyspace: ded " + a + " " + b + " " + c);
						return;
					}
				}
			}
		}
	}
	
	public static int[] rotate(int[] dim, ForgeDirection dir) {
		
		if(dim == null)
			return null;
		
		if(dir == ForgeDirection.SOUTH)
			return dim;
		
		if(dir == ForgeDirection.NORTH) {
			//                 U       D       N       S       W       E
			return new int[] { dim[0], dim[1], dim[3], dim[2], dim[5], dim[4] };
		}
		
		if(dir == ForgeDirection.EAST) {
			//                 U       D       N       S       W       E
			return new int[] { dim[0], dim[1], dim[5], dim[4], dim[2], dim[3] };
		}
		
		if(dir == ForgeDirection.WEST) {
			//                 U       D       N       S       W       E
			return new int[] { dim[0], dim[1], dim[4], dim[5], dim[3], dim[2] };
		}
		
		return dim;
	}

}
