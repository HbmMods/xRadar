package com.hfr.tileentity.clowder;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.List;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderFlag;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
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

public class TileEntityConquerer extends TileEntityMachineBase {
	
	public Clowder owner;
	public float height = 0.0F;
	public static final float speed = 1.0F / (20F * 90F);
	
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
			}
			
			if(owner == null) {
				worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
				return;
			}
			
			List<EntityPlayer> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 4, yCoord - 1, zCoord - 4, xCoord + 5, yCoord + 2, zCoord + 5));
			
			boolean canRaise = false;
			
			for(EntityPlayer player : entities) {
				
				Clowder clow = Clowder.getClowderFromPlayer(player);
				
				if(clow != null) {
					
					if(clow == owner) {
						canRaise = true;
					} else {
						canRaise = false;
						break;
					}
				}
			}
			
			if(!canRaise) {
				height -= speed;
			} else {
				height += speed;
			}
			
			if(height < 0)
				height = 0;
			
			if(height >= 1) {
				
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hfr:block.flagCapture", 100.0F, 1.0F);
				conquer();
				worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
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
	
	private void conquer() {

		tryOverride(xCoord + 16, zCoord + 16);
		tryOverride(xCoord - 16, zCoord + 16);
		tryOverride(xCoord + 16, zCoord - 16);
		tryOverride(xCoord - 16, zCoord - 16);
	}
	
	private void tryOverride(int x, int z) {
		
		CoordPair loc = ClowderTerritory.getCoordPair(x, z);
		TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(loc);
		
		if(meta != null && meta.owner.zone == Zone.FACTION && meta.owner.owner != this.owner && meta.owner.owner.isRaidable())
			ClowderTerritory.removeZoneForCoord(worldObj, loc);
	}
	
	public boolean canSeeSky() {

		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				
				if(worldObj.getBlock(xCoord + i, yCoord, zCoord + j).isNormalCube() && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord, zCoord + j) ||
					!worldObj.getBlock(xCoord + i, yCoord - 1, zCoord + j).isSideSolid(worldObj, xCoord + i, yCoord - 1, zCoord + j, UP))
					return false;
		
		if(yCoord < 45)
			return false;
		if(yCoord > 100)
			return false;
		
		return true;
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
}
