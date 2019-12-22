package com.hfr.tileentity;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.BlockSpeedy;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.handler.MultiblockHandler;
import com.hfr.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStatue extends TileEntityMachineBase {

	public TileEntityStatue() {
		super(5);
	}

	@Override
	public String getName() {
		return "statue";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			Ownership owner = ClowderTerritory.getOwnerFromCoords(ClowderTerritory.getCoordPair(xCoord, zCoord));
			if(operational() && owner != null && owner.zone == Zone.FACTION) {
				
				if(worldObj.rand.nextInt(500) == 0) {
					
					for(int i = 0; i < slots.length; i++) {
						
						if(slots[i] == null) {
							slots[i] = new ItemStack(ModItems.province_point);
							break;
						} else if(slots[i].getItem() == ModItems.province_point && slots[i].stackSize < 64) {
							slots[i].stackSize++;
							break;
						}
					}
				}
			}
		}
	}
	
	public boolean operational() {
		
		BlockDummyable dummy = (BlockDummyable) this.getBlockType();
		
		int[] dim = MultiblockHandler.rotate(dummy.getDimensions(), ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset));
		int n = dim[2];
		int s = dim[3];
		int w = dim[4];
		int e = dim[5];

		for(int i = -w; i <= e; i++)
			for(int j = -n; j <= s; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 4, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 4, zCoord + j))
					return false;

		for(int x = -w; x <= e; x++)
			for(int z = -n; z <= s; z++)
				if(!(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) instanceof BlockSpeedy))
					return false;
		
		return true;
	}

}
