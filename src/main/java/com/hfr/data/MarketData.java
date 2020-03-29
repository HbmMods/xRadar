package com.hfr.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class MarketData extends WorldSavedData {
	
	public List<ItemStack[]> offers = new ArrayList();

	public MarketData() {
		super("hfr_market");
	}

	public MarketData(String name) {
		super(name);
	}
	
	public static MarketData getData(World worldObj) {

		MarketData data = (MarketData)worldObj.perWorldStorage.loadData(MarketData.class, "hfr_market");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hfr_market", new MarketData());
	        
	        data = (MarketData)worldObj.perWorldStorage.loadData(MarketData.class, "hfr_market");
	    }
	    
	    return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		int count = nbt.getInteger("count");
		
		for(int i = 0; i < count; i++) {
			
			ItemStack[] slots = new ItemStack[4];
			NBTTagList list = nbt.getTagList("items" + i, 10);

			for (int j = 0; j < list.tagCount(); j++) {
				NBTTagCompound nbt1 = list.getCompoundTagAt(j);
				byte b0 = nbt1.getByte("slot");
				if (b0 >= 0 && b0 < slots.length) {
					slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
				}
			}
			
			offers.add(slots);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("count", offers.size());
		int index = 0;
		
		for(ItemStack[] offer : offers) {

			NBTTagList list = new NBTTagList();

			for (int i = 0; i < offer.length; i++) {
				if (offer[i] != null) {
					NBTTagCompound nbt1 = new NBTTagCompound();
					nbt1.setByte("slot", (byte) i);
					offer[i].writeToNBT(nbt1);
					list.appendTag(nbt1);
				}
			}
			nbt.setTag("items" + index, list);
			index++;
		}
	}

}
