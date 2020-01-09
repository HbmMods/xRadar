package com.hfr.tileentity.prop;

import java.util.List;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.BlockSpeedy;
import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.handler.MultiblockHandler;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityProp extends TileEntity {
	
	public String warp = "";
	public Clowder owner = null;
	
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
		
		if(!worldObj.isRemote) {

			/// HEALING AURA ///
			if(this.getBlockType() == ModBlocks.med_tent && operational()) {
				
				int radius = 5;

				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
						xCoord + 0.5 - radius,
						yCoord,
						zCoord + 0.5 - radius,
						xCoord + 0.5 + radius,
						yCoord + 3,
						zCoord + 0.5 + radius
				));
				
				for(EntityLivingBase entity : entities) {
					entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 5 * 20, 2));
				}
			}
			
			Ownership o = ClowderTerritory.getOwnerFromInts(xCoord, zCoord);
			
			/// WARP UPDATE ///
			if(!warp.isEmpty()) {
				
				if(!operational() || o == null || o.zone != Zone.FACTION || !o.owner.warps.containsKey(warp)) {
					
					if(owner != null)
						owner.warps.remove(warp);
					
					warp = "";
					this.markDirty();
				}
			}

			/// PRESTIGE GEENERATOR ///
			if(this.getBlockType() == ModBlocks.tp_tent || this.getBlockType() == ModBlocks.med_tent) {
				
				if(this.operational()) {
					
					if(owner != o.owner) {
						
						if(owner != null) {
							
							//doesn't need a call of the save method because it will always save in the statements below
							owner.warps.remove(warp);
							warp = "";
						}
						
						if(o != null && o.zone == Zone.FACTION) {
							if(owner != null)
								owner.addPrestigeGen(-Clowder.tentRate, worldObj);
							owner = o.owner;
							owner.addPrestigeGen(Clowder.tentRate, worldObj);
							
						} else {
							
							if(owner != null)
								owner.addPrestigeGen(-Clowder.tentRate, worldObj);
							
							owner = null;
						}
						
						this.markDirty();
					}
				} else {
					
					if(owner != null) {
						owner.addPrestigeGen(-Clowder.tentRate, worldObj);
						owner = null;
						
						this.markDirty();
					}
				}
			}
		}
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		owner = Clowder.getClowderFromName(nbt.getString("owner"));
		warp = nbt.getString("warp");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(owner != null)
			nbt.setString("owner", owner.name);
		
		if(!warp.isEmpty())
			nbt.setString("warp", warp);
	}

}
