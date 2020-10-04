package com.hfr.tileentity.prop;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.BlockSpeedy;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.handler.MultiblockHandler;
import com.hfr.tileentity.machine.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStatue extends TileEntityMachineBase {
	
	public Clowder owner = null;

	public TileEntityStatue() {
		super(0);
	}

	@Override
	public String getName() {
		return "statue";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.operational()) {
				
				Ownership o = ClowderTerritory.getOwnerFromInts(xCoord, zCoord);
				
				if(owner != o.owner) {
					
					if(o != null && o.zone == Zone.FACTION) {
						if(owner != null)
							owner.addPrestigeGen(-Clowder.statueRate, worldObj);
						owner = o.owner;
						owner.addPrestigeGen(Clowder.statueRate, worldObj);
						
					} else {
						
						if(owner != null)
							owner.addPrestigeGen(-Clowder.statueRate, worldObj);
						
						owner = null;
					}
					
					this.markDirty();
				}
			} else {
				
				if(owner != null) {
					owner.addPrestigeGen(-Clowder.statueRate, worldObj);
					owner = null;
					
					this.markDirty();
				}
			}
		}
	}
	
	public boolean operational() {
		
		Block b = this.getBlockType();
		
		if(!(b instanceof BlockDummyable))
			return false;
		
		BlockDummyable dummy = (BlockDummyable) b;
		
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		owner = Clowder.getClowderFromName(nbt.getString("owner"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(owner != null)
			nbt.setString("owner", owner.name);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
