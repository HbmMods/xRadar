package com.hfr.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class MarketData extends WorldSavedData {
	
	public HashMap<String, List<ItemStack[]>> offers = new HashMap();

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
		
		readMarkets(nbt, count);
	}
	
	public void readMarkets(NBTTagCompound nbt, int count) {
		
		for(int index = 0; index < count; index++) {
			
			String name = nbt.getString("market_" + index);
			int offerCount = nbt.getInteger("offercount_" + index);
			
			for(int off = 0; off < offerCount; off++)
				readOffers(nbt, name, off);
		}
	}
	
	public void readMarketFromPacket(NBTTagCompound nbt) {
			
		String name = nbt.getString("market");
		int offerCount = nbt.getInteger("offercount");

		for (int off = 0; off < offerCount; off++)
			readOffers(nbt, name, off);
	}
	
	public void readOffers(NBTTagCompound nbt, String name, int index) {

		ItemStack[] slots = new ItemStack[4];
		NBTTagList list = nbt.getTagList("items" + name + index, 10);
		
		for (int j = 0; j < list.tagCount(); j++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(j);
			byte b0 = nbt1.getByte("slot" + index);
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		
		List<ItemStack[]> offers = this.offers.get(name);
		
		if(offers == null)
			offers = new ArrayList();
		
		offers.add(slots);
		this.offers.put(name, offers);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("count", offers.size());
		
		writeMarkets(nbt);
	}

	public void writeMarkets(NBTTagCompound nbt) {
		
		int index = 0;
		
		for(Entry<String, List<ItemStack[]>> entry : offers.entrySet()) {
			
			nbt.setString("market_" + index, entry.getKey());
			nbt.setInteger("offercount_" + index, entry.getValue().size());
			
			writeOffers(nbt, entry.getKey(), entry.getValue());
			
			index++;
		}
	}

	public void writeMarketFromName(NBTTagCompound nbt, String name) {
		
		List<ItemStack[]> market = this.offers.get(name);
		
		if(market == null)
			return;
			
		nbt.setString("market", name);
		nbt.setInteger("offercount", market.size());
		
		writeOffers(nbt, name, market);
	}
	
	public void writeOffers(NBTTagCompound nbt, String name, List<ItemStack[]> offers) {
		
		for(int index = 0; index < offers.size(); index++) {
			
			NBTTagList list = new NBTTagList();
			ItemStack[] offer = offers.get(index);

			for (int i = 0; i < offer.length; i++) {
				if (offer[i] != null) {
					NBTTagCompound nbt1 = new NBTTagCompound();
					nbt1.setByte("slot" + index, (byte) i);
					offer[i].writeToNBT(nbt1);
					list.appendTag(nbt1);
				}
			}
			
			nbt.setTag("items" + name + index, list);
		}
	}
}
