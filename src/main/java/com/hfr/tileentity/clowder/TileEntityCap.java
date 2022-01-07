package com.hfr.tileentity.clowder;

import java.util.List;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.CoordPair;
import com.hfr.clowder.ClowderTerritory.TerritoryMeta;
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

public class TileEntityCap extends TileEntityMachineBase implements ITerritoryProvider {

	public Clowder owner;
	public int progress;
	public static final int maxProgress = 200;
	public String name = "";
	
	@SideOnly(Side.CLIENT)
	public int color;
	
	public TileEntityCap() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.capPoint";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(Clowder.clowders.size() == 0)
				ClowderData.getData(worldObj);

			if(!Clowder.clowders.contains(owner))
				owner = null;
			
			/// CAPTURE START ///
			
			List<EntityPlayer> entities = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 4, yCoord - 1, zCoord - 4, xCoord + 5, yCoord + 2, zCoord + 5));
			
			Clowder capturer = null;
			for(EntityPlayer player : entities) {
				
				Clowder clow = Clowder.getClowderFromPlayer(player);
				
			}
			
			progress = 0;
			
			if(progress == 0 && owner != null) {
				
				if(worldObj.rand.nextInt(100) == 0) {
					
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
			
			if(!canSeeSky()) {
				progress = 0;
				owner = null;
			} else if(owner != null) {
				//generateClaim();
			}
			
			/// CAPTURE END ///
			
			if(owner != null)
				this.updateGauge(owner.color, 0, 100);
			else
				this.updateGauge(0xFFFFFF, 0, 100);
		}
	}

	@Override
	public int getRadius() {
		
		return 20;
	}

	@Override
	public Clowder getOwner() {
		return owner;
	}
	
	public void generateClaim() {
		
		int rad = getRadius();
		
		for(int x = -rad; x <= rad; x++) {
			for(int z = -rad; z <= rad; z++) {

				int posX = xCoord + x * 16;
				int posZ = zCoord + z * 16;
				CoordPair loc = ClowderTerritory.getCoordPair(posX, posZ);
				
				TerritoryMeta meta = ClowderTerritory.getMetaFromCoords(loc);
				
				if(meta == null || !meta.checkPersistence(worldObj, loc))
					if(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) < rad)
						ClowderTerritory.setOwnerForCoord(worldObj, loc, owner, xCoord, yCoord, zCoord, name);
			}
		}
	}
	
	public boolean canSeeSky() {

		for(int i = -2; i <= 2; i++)
			for(int j = -2; j <= 2; j++)
				if(worldObj.getBlock(xCoord + i, yCoord + 1, zCoord + j).getMaterial() != Material.air && !(i == 0 && j == 0) ||
					!worldObj.canBlockSeeTheSky(xCoord + i, yCoord + 1, zCoord + j))
					return false;
		
		return true;
	}
	
	public void processGauge(int val, int id) {
		
		if(id == 0)
			color = val;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 1, 2, 3, 4 };
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.owner = Clowder.getClowderFromName(nbt.getString("owner"));
		this.progress = nbt.getInteger("progress");
		this.name = nbt.getString("name");
		
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		
		if(owner != null)
			generateClaim();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if(owner != null)
			nbt.setString("owner", owner.name);
		nbt.setInteger("progress", progress);
		nbt.setString("name", name);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
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
