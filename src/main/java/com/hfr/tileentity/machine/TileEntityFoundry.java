package com.hfr.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.hfr.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityFoundry extends TileEntityMachineBase {

	@SideOnly(Side.CLIENT)
	public float tilt;
	@SideOnly(Side.CLIENT)
	public float lastTilt;
	
	public float steel;
	public static final float maxSteel = 32;
	public int heat;
	public static final int maxHeat = 4;
	public int smeltTimer;
	public int progress;
	public static final int castTime = 100;
	public int index = 0;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.steel = nbt.getFloat("steel");
		this.index = nbt.getInteger("index");
		this.smeltTimer = nbt.getInteger("smeltTimer");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setFloat("steel", steel);
		nbt.setInteger("index", index);
		nbt.setInteger("smeltTimer", smeltTimer);
		nbt.setInteger("progress", progress);
	}
	
	public static HashMap<String, Float> recipes = new HashMap();
	public static List<Item> options = new ArrayList();
	
	static {
		recipes.put(ModItems.ingot_tap_steel.getUnlocalizedName(), 1F);
		recipes.put(ModItems.part_spring.getUnlocalizedName(), 0.5F);
		recipes.put(ModItems.part_rod.getUnlocalizedName(), 0.5F);
		recipes.put(ModItems.part_rifled_barrel.getUnlocalizedName(), 2F);
		recipes.put(ModItems.part_smoothbore_barrel.getUnlocalizedName(), 2F);
		recipes.put(ModItems.part_gear.getUnlocalizedName(), 2.5F);
		recipes.put(ModItems.part_plate.getUnlocalizedName(), 3F);
		recipes.put(ModItems.part_frame.getUnlocalizedName(), 5F);
		recipes.put(ModItems.part_grate.getUnlocalizedName(), 3F);
		recipes.put(ModItems.part_suspension.getUnlocalizedName(), 3F);
		recipes.put(ModItems.part_plating_1.getUnlocalizedName(), 5F);
		recipes.put(ModItems.part_hull_1.getUnlocalizedName(), 15F);
		recipes.put(ModItems.part_mechanism_1.getUnlocalizedName(), 4F);
		recipes.put(ModItems.part_steel_wheel.getUnlocalizedName(), 3F);
		recipes.put(ModItems.part_sawblade.getUnlocalizedName(), 2.5F);
		recipes.put(ModItems.part_track.getUnlocalizedName(), 2F);

		options.add(ModItems.ingot_tap_steel);
		options.add(ModItems.part_spring);
		options.add(ModItems.part_rod);
		options.add(ModItems.part_rifled_barrel);
		options.add(ModItems.part_smoothbore_barrel);
		options.add(ModItems.part_gear);
		options.add(ModItems.part_plate);
		options.add(ModItems.part_frame);
		options.add(ModItems.part_grate);
		options.add(ModItems.part_suspension);
		options.add(ModItems.part_plating_1);
		options.add(ModItems.part_hull_1);
		options.add(ModItems.part_mechanism_1);
		options.add(ModItems.part_steel_wheel);
		options.add(ModItems.part_sawblade);
		options.add(ModItems.part_track);
	}

	public TileEntityFoundry() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.foundry";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			/// FIRE OVEN START ///
			if(heat == 0 && slots[1] != null && slots[1].getItem() == Items.coal) {
				this.decrStackSize(1, 1);
				heat = maxHeat;
				this.markDirty();
			}
			/// FIRE OVEN END ///
			
			/// SMELT DOWN STEEL START ///
			float steelContent = slots[0] != null ? getSteel(slots[0].getItem()) : 0.0F;
			
			if(heat > 0 && steelContent > 0 && steel + steelContent <= maxSteel) {
				smeltTimer++;
				
				if(smeltTimer > 20) {
					smeltTimer = 0;
					steel += steelContent;
					this.decrStackSize(0, 1);
					heat--;
					this.markDirty();
				}
			} else {
				smeltTimer = 0;
			}
			/// SMELT DOWN STEEL END ///

			/// RECTIFY INDEX START ///
			if(index < 0)
				index = 0;
			
			if(index >= options.size())
				index = 0;
			/// RECTIFY INDEX END ///

			/// CAST START ///
			Item target = options.get(index);
			float cost = recipes.get(target.getUnlocalizedName());
			
			if(canProcess(target, cost)) {
				
				progress++;
				
				if(progress >= castTime) {
					
					steel -= cost;
					progress = 0;
					
					if(slots[2] == null) {
						slots[2] = new ItemStack(target);
					} else {
						slots[2].stackSize++;
					}
					
					this.markDirty();
				}
			} else {
				progress = 0;
			}
			/// CAST END ///

			this.updateGauge((int) Math.round(steel * 10), 0, 50);
			this.updateGauge(heat, 1, 50);
			this.updateGauge(progress, 2, 150);
			this.updateGauge(index, 3, 50);
			
		} else {
			
			lastTilt = tilt;
			
			if(progress > 0) {
				
				if(tilt < 30)
					tilt++;
			} else {
				
				if(tilt > 0)
					tilt--;
			}
		}
	}
	
	public void processGauge(int val, int id) {
		
		switch(id) {
		case 0: steel = val / 10F; break;
		case 1: heat = val; break;
		case 2: progress = val; break;
		case 3: index = val; break;
		}
	}
	
	private boolean canProcess(Item target, float cost) {
		
		if(steel < cost)
			return false;
		
		if(slots[2] == null)
			return true;
		
		if(slots[2].getItem() == target && slots[2].stackSize < slots[2].getMaxStackSize())
			return true;
		
		return false;
	}
	
	private float getSteel(Item item) {
		
		if(item == null)
			return 0.0F;
		
		Float steel = recipes.get(item.getUnlocalizedName());
		
		if(steel == null)
			return 0.0F;
		
		return steel;
	}
	
	public void increment() {
		index++;
		
		if(index >= recipes.size())
			index = 0;
		
		this.markDirty();
	}
	
	public void decrement() {
		index--;
		
		if(index < 0)
			index = recipes.size() - 1;
		
		this.markDirty();
	}
	
	public int getSteelScaled(int i) {
		return (int)(steel * i / maxSteel);
	}
	
	public int getHeatScaled(int i) {
		return (int)(heat * i / maxHeat);
	}
	
	public int getProgressScaled(int i) {
		return (int)(progress * i / castTime);
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
