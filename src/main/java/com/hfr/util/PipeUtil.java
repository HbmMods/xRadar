package com.hfr.util;

import java.util.HashSet;
import java.util.Set;

import com.hfr.blocks.ModBlocks;
import com.hfr.tileentity.machine.TileEntityDuct;
import com.hfr.tileentity.machine.TileEntityMachineDerrick;
import com.hfr.tileentity.machine.TileEntityMachineRefinery;
import com.hfr.tileentity.machine.TileEntityTank;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PipeUtil {
	
	private static Set<TileEntityDuct> tiles = new HashSet();
	
	private static void distributeOil(TileEntityMachineDerrick source, World world, int x, int y, int z) {
		
		Block b = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityMachineRefinery) {
			TileEntityMachineRefinery ref = (TileEntityMachineRefinery)te;
			
			int fill = Math.min(ref.maxOil - ref.oil, source.oil);
			source.oil -= fill;
			ref.oil += fill;
			
		} else if(te instanceof TileEntityTank) {
			TileEntityTank tank = (TileEntityTank)te;
			
			if(tank.fill == 0 || tank.type == 0) {

				int fill = Math.min(tank.maxFill - tank.fill, source.oil);
				source.oil -= fill;
				tank.fill += fill;
				tank.type = 0;
			}
		} else if(b == ModBlocks.oil_duct && !tiles.contains(te)) {
			
			tiles.add((TileEntityDuct) te);

			distributeOil(source, world, x + 1, y, z);
			distributeOil(source, world, x - 1, y, z);
			distributeOil(source, world, x, y + 1, z);
			distributeOil(source, world, x, y - 1, z);
			distributeOil(source, world, x, y, z + 1);
			distributeOil(source, world, x, y, z - 1);
		}
	}
	
	public static void initOil(TileEntityMachineDerrick source, World world, int x, int y, int z) {
		distributeOil(source, world, x + 1, y, z);
		distributeOil(source, world, x - 1, y, z);
		distributeOil(source, world, x, y, z + 1);
		distributeOil(source, world, x, y, z - 1);
		tiles.clear();
	}
	
	private static void distributeGas(TileEntityMachineDerrick source, World world, int x, int y, int z) {
		
		Block b = world.getBlock(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityTank) {
			TileEntityTank tank = (TileEntityTank)te;
			
			if(tank.fill == 0 || tank.type == 1) {

				int fill = Math.min(tank.maxFill - tank.fill, source.gas);
				source.gas -= fill;
				tank.fill += fill;
				tank.type = 1;
			}
		} else if(b == ModBlocks.gas_duct && !tiles.contains(te)) {
			
			tiles.add((TileEntityDuct) te);

			distributeGas(source, world, x + 1, y, z);
			distributeGas(source, world, x - 1, y, z);
			distributeGas(source, world, x, y + 1, z);
			distributeGas(source, world, x, y - 1, z);
			distributeGas(source, world, x, y, z + 1);
			distributeGas(source, world, x, y, z - 1);
		}
	}
	
	public static void initGas(TileEntityMachineDerrick source, World world, int x, int y, int z) {
		distributeGas(source, world, x + 1, y, z);
		distributeGas(source, world, x - 1, y, z);
		distributeGas(source, world, x, y, z + 1);
		distributeGas(source, world, x, y, z - 1);
		tiles.clear();
	}

}
