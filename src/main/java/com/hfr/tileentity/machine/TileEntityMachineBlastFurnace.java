package com.hfr.tileentity.machine;

import java.util.List;

import com.hfr.blocks.BlockDummyable;
import com.hfr.blocks.BlockSpeedy;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Ownership;
import com.hfr.clowder.ClowderTerritory.Zone;
import com.hfr.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineBlastFurnace extends TileEntityMachineBase {
	
	public Clowder owner = null;
	
	public int progress = 0;
	public static final int maxProgress = 600;
	public int fuel = 0;
	public static final int coalValue = 4;
	public static final int maxFuel = 64 * coalValue;
	
	public float meter;

	public TileEntityMachineBlastFurnace() {
		super(7);
	}

	@Override
	public String getName() {
		return "container.blastFurnace";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			while(slots[6] != null && slots[6].getItem() == Items.coal && fuel <= maxFuel - coalValue) {
				this.decrStackSize(6, 1);
				fuel += coalValue;
			}
			while(slots[6] != null && slots[6].getItem() == Item.getItemFromBlock(Blocks.coal_block) && fuel <= maxFuel - coalValue * 10) {
				this.decrStackSize(6, 1);
				fuel += coalValue * 10;
			}
			
			if(operational()) {
				
				Ownership o = ClowderTerritory.getOwnerFromInts(xCoord, zCoord);
				
				if(owner != o.owner) {
					
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
				
				if(hasIron() && hasSpace() && fuel > 0) {
					
					progress += 2;
					
					if(progress >= maxProgress) {
						
						remIron();
						addSteel();
						progress = 0;
						fuel--;
						this.markDirty();
					}
					
					this.updateGauge(Float.floatToIntBits(getMeter()), 2, 250);
					
				} else {

					progress = 0;
					this.updateGauge(Float.floatToIntBits(0F), 2, 250);
				}
				
			} else {

				progress = 0;
				this.updateGauge(Float.floatToIntBits(0F), 2, 250);
			}

			this.updateGauge(fuel, 0, 50);
			this.updateGauge(progress, 1, 50);
		} else {
			
			if(meter > 0) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				
				if(dir != ForgeDirection.UNKNOWN) {

	                worldObj.spawnParticle("flame", xCoord + 0.5D + dir.offsetX * 0.75D, yCoord + 0.35D, zCoord + 0.5D + dir.offsetZ * 0.75D, 0.0D, 0.0D, 0.0D);
	                worldObj.spawnParticle("smoke", xCoord + 0.5D + dir.offsetX * 0.85D, yCoord + 0.5D, zCoord + 0.5D + dir.offsetZ * 0.85D, 0.0D, 0.0D, 0.0D);
	                //worldObj.spawnParticle("smoke", xCoord + 0.5D + dir.offsetX * 0.85D, yCoord + 1.35D, zCoord + 0.5D + dir.offsetZ * 0.85D, dir.offsetX * 0.1D, 0.0D, dir.offsetZ * 0.1D);
				}
				
				float y = 0.5F + meter * 4F;
                worldObj.spawnParticle("smoke", xCoord + 0.5D + this.worldObj.rand.nextGaussian() * 0.25D, yCoord + y, zCoord + 0.5D + this.worldObj.rand.nextGaussian() * 0.25D, 0.0D, 0.0D, 0.0D);
			}
		}
		
		if(operational()) {
			float meter = getMeter();
			
			if(worldObj.isRemote)
				meter = this.meter;
			
			List<Entity> mobs = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 0.5 + meter * 4, zCoord + 1));
			
			for(Entity e : mobs) {
				e.setInWeb();
				e.setFire(5);
				e.attackEntityFrom(DamageSource.lava, 2.0F);
			}
		}
	}
	
	public void processGauge(int val, int id) {
		
		switch(id) {
		case 0: fuel = val; break;
		case 1: progress = val; break;
		case 2: meter = Float.intBitsToFloat(val); break;
		}
	}
	
	public boolean operational() {
		
		for(int x = -1; x <= 1; x++)
			for(int z = -1; z <= 1; z++)
				if(!(worldObj.getBlock(xCoord + x, yCoord - 1, zCoord + z) instanceof BlockSpeedy))
					return false;
		
		return true;
	}
	
	public int getProgressScaled(int i) {
		
		return progress * i / maxProgress;
	}
	
	public int getFuelScaled(int i) {
		return fuel * i / maxFuel;
	}
	
	public float getMeter() {
		
		if(fuel > 0) {
			
			float content = 0F;
			
			for(int i = 0; i < 3; i++) {
				
				if((slots[i] != null && (slots[i].getItem() == Items.iron_ingot || slots[i].getItem() == Item.getItemFromBlock(Blocks.iron_ore))))
					content += slots[i].stackSize;
			}
			
			return content / (64F * 3F);
		}
		
		return 0F;
	}
	
	public boolean hasIron() {
		
		for(int i = 0; i < 3; i++) {
			
			if((slots[i] != null && (slots[i].getItem() == Items.iron_ingot || slots[i].getItem() == Item.getItemFromBlock(Blocks.iron_ore))))
				return true;
		}
		
		return false;
	}
	
	public void remIron() {
		
		for(int i = 0; i < 3; i++) {
			
			if((slots[i] != null && (slots[i].getItem() == Items.iron_ingot || slots[i].getItem() == Item.getItemFromBlock(Blocks.iron_ore)))) {
				this.decrStackSize(i, 1);
				return;
			}
		}
	}
	
	public boolean hasSpace() {
		
		for(int i = 3; i < 6; i++) {
			
			if((slots[i] == null || (slots[i] != null && slots[i].getItem() == ModItems.ingot_tap_steel && slots[i].stackSize < slots[i].getMaxStackSize())))
				return true;
		}
		
		return false;
	}
	
	public void addSteel() {
		
		for(int i = 3; i < 6; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.ingot_tap_steel && slots[i].stackSize < slots[i].getMaxStackSize()) {
				slots[i].stackSize++;
				return;
			} else if(slots[i] == null) {
				slots[i] = new ItemStack(ModItems.ingot_tap_steel);
				return;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		owner = Clowder.getClowderFromName("owner");
		progress = nbt.getInteger("progress");
		fuel = nbt.getInteger("fuel");
		
		NBTTagList list = nbt.getTagList("items", 10);
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(owner != null)
			nbt.setString("owner", owner.name);
		nbt.setInteger("progress", progress);
		nbt.setInteger("fuel", fuel);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
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
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
