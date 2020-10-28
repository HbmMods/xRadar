package com.hfr.tileentity.clowder;

import java.util.HashSet;
import java.util.List;

import com.hfr.blocks.ModBlocks;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderFlag;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.data.ClowderData;
import com.hfr.items.ModItems;
import com.hfr.tileentity.machine.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityFlagBig extends TileEntityMachineBase implements ITerritoryProvider {

	public Clowder owner;
	public boolean isClaimed = true;
	public HashSet<CoordPair> claim = new HashSet();
	public String name = "";
	
	@SideOnly(Side.CLIENT)
	public ClowderFlag flag;
	@SideOnly(Side.CLIENT)
	public int color;

	public TileEntityFlagBig() {
		super(2);
		isClaimed = false;
	}

	@Override
	public String getName() {
		return "container.flagPole";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(Clowder.clowders.size() == 0)
				ClowderData.getData(worldObj);
			
			//remove disbanded clowders
			if(!Clowder.clowders.contains(owner))
				owner = null;
			
			/// CAPTURE START ///
			if(owner == null) {
				
				List<EntityPlayer> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 4, yCoord - 1, zCoord - 4, xCoord + 5, yCoord + 2, zCoord + 5));
				
				Clowder capturer = null;
				for(EntityPlayer player : entities) {
					
					Clowder clow = Clowder.getClowderFromPlayer(player);
					
					if(clow != null) {
						capturer = clow;
						break;
					}
				}
				
				if(capturer != null) {
					
					owner = capturer;
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.flagChange", 3.0F, 1.0F);
					this.isClaimed = true;
					this.generateClaim();
				}
			} else {
				
				if(slots[1] != null) {
					
					if(worldObj.rand.nextInt(60 * 20) == 0) {
						
						if(slots[0] == null) {
							slots[0] = slots[1].copy();
						} else if(slots[0].getItem() == slots[1].getItem() && slots[0].stackSize < slots[0].getMaxStackSize()) {
							slots[0].stackSize++;
						}
					}
				}
			}
			
			/// CAPTURE END ///
			
			if(owner != null) {
				this.updateGauge(owner.flag.ordinal(), 0, 100);
				this.updateGauge(owner.color, 1, 100);
			} else {
				this.updateGauge(ClowderFlag.NONE.ordinal(), 0, 100);
				this.updateGauge(0xFFFFFF, 1, 100);
			}
			
		} else {
			
			isClaimed = this.flag != ClowderFlag.NONE;
				

			if(isClaimed) {
				double x = xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5D;
				double y = yCoord + 0.125 + worldObj.rand.nextDouble() * 0.5D;
				double z = zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.5D;

			    float r = Math.max(((color & 0xFF0000) >> 16) / 256F, 0.01F);
			    float g = Math.max(((color & 0xFF00) >> 8) / 256F, 0.01F);
			    float b = Math.max((color & 0xFF) / 256F, 0.01F);
				
				worldObj.spawnParticle("reddust", x, y, z, r, g, b);
			}
		}
	}
	
	public void processGauge(int val, int id) {
		
		switch(id) {
		case 0: flag = ClowderFlag.values()[val]; break;
		case 1: color = val; break;
		}
	}

	@Override
	public int getRadius() {
		
		return 1000;
	}

	@Override
	public Clowder getOwner() {
		return owner;
	}
	
	public void addClaim(int x1, int z1, int x2, int z2) {

		int minX = Math.min(x1, x2);
		int minZ = Math.min(z1, z2);
		int maxX = Math.max(x1, x2);
		int maxZ = Math.max(z1, z2);
		
		for(int x = minX; x <= maxX; x += 16) {
			
			for(int z = minZ; z <= maxZ; z += 16) {
				
				CoordPair coord = ClowderTerritory.getCoordPair(x, z);
				this.claim.add(coord);
			}
		}

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:item.techBoop", 3.0F, 1.0F);
		this.markDirty();
		
		this.generateClaim();
	}
	
	public void generateClaim() {
		
		for(CoordPair coords : this.claim) {
			
			TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(coords);
			
			//destroy unwanted conquest flags
			if(meta != null && meta.owner != null && meta.owner.zone == Zone.FACTION) {
				int x = meta.flagX;
				int y = meta.flagY;
				int z = meta.flagZ;
				
				if(worldObj.getBlock(x, y, z) == ModBlocks.clowder_conquerer) {
					worldObj.func_147480_a(x, y, z, false);
				}
			}
			
			if(this.owner != null)
				ClowderTerritory.setOwnerForCoord(worldObj, coords, owner, xCoord, yCoord, zCoord, name);
			else
				ClowderTerritory.removeZoneForCoord(worldObj, coords);
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0 };
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.owner = Clowder.getClowderFromName(nbt.getString("owner"));
		this.isClaimed = nbt.getBoolean("isClaimed");
		this.name = nbt.getString("name");
		
		int length = nbt.getInteger("len");
		
		for(int i = 0; i < length; i++) {
			this.claim.add(new CoordPair(
					nbt.getInteger("x" + i),
					nbt.getInteger("z" + i)
					));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(owner != null)
			nbt.setString("owner", owner.name);
		nbt.setBoolean("isClaimed", isClaimed);
		nbt.setString("name", name);
		
		nbt.setInteger("len", this.claim.size());

		int i = 0;
		
		for(CoordPair coords : this.claim) {

			nbt.setInteger("x" + i, coords.x);
			nbt.setInteger("z" + i, coords.z);
			
			i++;
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public String getClaimName() {
		return name;
	}

	@Override
	public void setClaimName(String name) {
		this.name = name;
	}
}
