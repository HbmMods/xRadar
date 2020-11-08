package com.hfr.tileentity.clowder;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.List;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderFlag;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityConquerer extends TileEntityMachineBase implements ITerritoryProvider {
	
	public Clowder owner;
	public float height = 0.0F;
	public static final float speed = 1.0F / (20F * 40F);
	public String name = "";
	
	@SideOnly(Side.CLIENT)
	public ClowderFlag flag;
	@SideOnly(Side.CLIENT)
	public int color;

	public TileEntityConquerer() {
		super(0);
	}

	@Override
	public String getName() {
		return "undefined";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(!Clowder.clowders.contains(owner) && owner != null) {
				MainRegistry.logger.info("Deleting clowder from conquerer " + xCoord + " " + yCoord + " " + zCoord + " due to clowder not being in the clowder list! (disband?)");
				owner = null;
				return;
			}
			
			if(owner == null) {
				worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				return;
			}
			
			int range = 32;
			List<EntityPlayer> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class,
					AxisAlignedBB.getBoundingBox(
							xCoord + 0.5 - range,
							yCoord - 2,
							zCoord + 0.5 - range,
							xCoord + 0.5 + range,
							yCoord + 4,
							zCoord + 0.5 + range));
			
			boolean canRaise = false;
			
			for(EntityPlayer player : entities) {
				
				Clowder clow = Clowder.getClowderFromPlayer(player);
				
				if(clow != null) {
					
					if(clow == owner) {
						canRaise = true;
					}
				}
			}
			
			double prev = height;
			
			if((!canRaise && height < 1)) {
				height -= speed;
			} else if(height < 1) {
				height += speed;
			}
			
			if(height < 0)
				height = 0;
			
			if(height > 1)
				height = 1;
			
			if(height >= 1 && prev < 1) {
				
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.flagCapture", 100.0F, 1.0F);
				conquer();
			}
			
			this.updateGauge(owner.flag.ordinal(), 0, 250);
			this.updateGauge(owner.color, 1, 250);
			this.updateGauge((int) (height * 100F), 2, 100);
		}
	}
	
	public void processGauge(int val, int id) {
		
		switch(id) {
		case 0: flag = ClowderFlag.values()[val]; break;
		case 1: color = val; break;
		case 2: height = val * 0.01F; break;
		}
	}
	
	public boolean canSeeSky() {
		
		if(!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
			return false;
		/*if(yCoord < 45)
			return false;
		if(yCoord > 100)
			return false;*/
		
		return true;
	}
	
	private void conquer() {
		
		CoordPair loc = ClowderTerritory.getCoordPair(xCoord, zCoord);
		TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(loc);
		
		if(meta != null && meta.owner.zone == Zone.FACTION && meta.owner.owner != this.owner/* && meta.owner.owner.isRaidable()*/) {

			CoordPair loc2 = ClowderTerritory.getCoordPair(meta.flagX, meta.flagZ);
			
			TileEntity te = worldObj.getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
			
			if(loc.equals(loc2)) {
				if(te instanceof TileEntityFlagBig) {
					((TileEntityFlagBig)te).owner = this.owner;
					((TileEntityFlagBig)te).generateClaim();
					te.markDirty();
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					
				} else if(te instanceof TileEntityFlag) {
					((TileEntityFlag)te).setOwner(owner);
					worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
					
				} else if(te instanceof TileEntityConquerer) {
					worldObj.func_147480_a(meta.flagX, meta.flagY, meta.flagZ, false);
					ClowderTerritory.setOwnerForCoord(worldObj, loc, owner, xCoord, yCoord, zCoord, name);
				}
			} else {
				ClowderTerritory.setOwnerForCoord(worldObj, loc, owner, xCoord, yCoord, zCoord, name);
			}
			
		} else {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
		}
	}
	
	public boolean checkBorder(int x, int z) {

		CoordPair loc = ClowderTerritory.getCoordPair(x, z);
		Ownership owner = ClowderTerritory.getOwnerFromCoords(loc);
		if(owner.zone != Zone.FACTION || owner.owner == this.owner)
			return false;
		
		CoordPair loc1 = ClowderTerritory.getCoordPair(x + 16, z);
		Ownership owner1 = ClowderTerritory.getOwnerFromCoords(loc1);
		if(owner1.zone == Zone.WILDERNESS || owner1.owner != owner.owner)
			return true;
		
		CoordPair loc2 = ClowderTerritory.getCoordPair(x - 16, z);
		Ownership owner2 = ClowderTerritory.getOwnerFromCoords(loc2);
		if(owner2.zone == Zone.WILDERNESS || owner2.owner != owner.owner)
			return true;
		
		CoordPair loc3 = ClowderTerritory.getCoordPair(x, z + 16);
		Ownership owner3 = ClowderTerritory.getOwnerFromCoords(loc3);
		if(owner3.zone == Zone.WILDERNESS || owner3.owner != owner.owner)
			return true;
		
		CoordPair loc4 = ClowderTerritory.getCoordPair(x, z - 16);
		Ownership owner4 = ClowderTerritory.getOwnerFromCoords(loc4);
		if(owner4.zone == Zone.WILDERNESS || owner4.owner != owner.owner)
			return true;
		
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		String own = nbt.getString("owner");
		this.owner = Clowder.getClowderFromName(own);
		boolean isNull = nbt.getBoolean("isNull");
		
		if(owner == null) {

			String id = nbt.getString("clow_uuid");
			this.owner = Clowder.getClowderFromUUID(id);
			
			if(!isNull) {
				
				if(owner == null) {
					MainRegistry.logger.info("Owner (" + id + ") of flag " + xCoord + " " + yCoord + " " + zCoord + " was saved NN but finalized as null!");
				}
			}
			
			if(owner == null && !id.isEmpty())
				MainRegistry.logger.info("Owner (" + id + ") of flag " + xCoord + " " + yCoord + " " + zCoord + " was set in NBT but not found in te clowder list!");
		}
		
		this.height = nbt.getFloat("height");
		this.name = nbt.getString("name");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(owner != null) {
			nbt.setBoolean("isNull", false);
			nbt.setString("clow_uuid", owner.uuid);
		} else {
			nbt.setBoolean("isNull", true);
		}
		
		nbt.setFloat("height", height);
		nbt.setString("name", name);
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
	public int getRadius() {
		return 0;
	}

	@Override
	public Clowder getOwner() {
		return this.owner;
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
