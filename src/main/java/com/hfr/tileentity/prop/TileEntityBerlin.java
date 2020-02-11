package com.hfr.tileentity.prop;

import java.util.List;

import com.hfr.blocks.ModBlocks;
import com.hfr.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBerlin extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		/// BARBED WIRE ///
		if(this.getBlockType() == ModBlocks.berlin_wall) {
			
			boolean rot = this.getBlockMetadata() == 12 || this.getBlockMetadata() == 13;
			
			List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(
					xCoord - (rot ? 1 : 0),
					yCoord + 6,
					zCoord - (rot ? 0 : 1),
					xCoord + (rot ? 2 : 1),
					yCoord + 7,
					zCoord + (rot ? 1 : 2)
			));
			
			for(Entity entity : entities) {
				entity.setInWeb();
				entity.attackEntityFrom(MainRegistry.wire, 2.5F);
			}
		}
	}

}
