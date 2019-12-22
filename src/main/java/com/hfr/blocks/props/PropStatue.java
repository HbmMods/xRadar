package com.hfr.blocks.props;

import com.hfr.blocks.BlockDummyable;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.TileEntityStatue;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PropStatue extends BlockDummyable {

	public PropStatue(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= ForgeDirection.UNKNOWN.ordinal())
			return new TileEntityStatue();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return MultiblockHandler.statue;
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote) {
			
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return true;
			
			TileEntityStatue cap = (TileEntityStatue)world.getTileEntity(pos[0], pos[1], pos[2]);
			
			for(int i = 0; i < cap.slots.length; i++) {
				
				if(cap.slots[i] != null) {
					
					int stack = cap.slots[i].stackSize;
					
					for(int j = 0; j < stack; j++) {
						
						if(!player.inventory.addItemStackToInventory(new ItemStack(cap.slots[i].getItem()))) {
							player.dropPlayerItemWithRandomChoice(new ItemStack(cap.slots[i].getItem(), stack - j), false);
							cap.decrStackSize(i, stack - j);
							break;
						}
						
						cap.decrStackSize(i, 1);
					}
				}
			}
			
			player.inventoryContainer.detectAndSendChanges();
			
			return false;
		}
		
		return true;
	}
}
