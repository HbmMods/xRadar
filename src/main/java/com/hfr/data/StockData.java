package com.hfr.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class StockData extends WorldSavedData {
	
	public static List<Stock> stocks = new ArrayList();
	public static List<StockEntry> entries = new ArrayList();

	public StockData() {
		super("hfr_stocks");
	}

	public StockData(String name) {
		super(name);
	}
	
	public int getShares(String player, Stock stock) {
		
		for(StockEntry entry : entries) {
			if(entry.player.equals(player) && entry.stock == stock)
				return entry.shares;
		}
		
		return 0;
	}
	
	public void setShares(String player, Stock stock, int shares) {

		markDirty();
		
		for(StockEntry entry : entries) {
			if(entry.player.equals(player) && entry.stock == stock) {
				
				entry.shares = shares;
				return;
			}
		}
		
		entries.add(new StockEntry(stock, player, shares));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		for(int i = 0; i < stocks.size(); i++)
			stocks.get(i).readFromNBT(nbt, i);
		
		int count = nbt.getInteger("count");
		
		for(int i = 0; i < count; i++) {
			entries.add(new StockEntry(
				stocks.get(nbt.getInteger(i + "_stock")),
				nbt.getString(i + "_player"),
				nbt.getInteger(i + "_shares")
			));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		for(int i = 0; i < stocks.size(); i++)
			stocks.get(i).writeToNBT(nbt, i);
		
		nbt.setInteger("count", entries.size());
		
		for(int i = 0; i < entries.size(); i++) {
			nbt.setInteger(i + "_stock", stocks.indexOf(entries.get(i).stock));
			nbt.setString(i + "_player", entries.get(i).player);
			nbt.setInteger(i + "_shares", entries.get(i).shares);
		}
	}
	
	public static StockData getData(World worldObj) {

		StockData data = (StockData)worldObj.perWorldStorage.loadData(StockData.class, "hfr_stocks");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hfr_stocks", new StockData());
	        
	        data = (StockData)worldObj.perWorldStorage.loadData(StockData.class, "hfr_stocks");
	    }
	    
	    return data;
	}
	
	public Stock getByInt(int i) {
		
		i = i % stocks.size();
		
		return stocks.get(i);
	}
	
	public static class Stock {
		
		//name of the stock
		public String name;
		//the 3,4 letter short name
		public String shortname;
		//starting value
		public float[] value;
		//chance of a boost per n-tick
		public float u2chance;
		//chance of a rise per n-tick
		public float u1chance;
		//chance of returning to n-state
		public float nchance;
		//chance of a fall per n-tick
		public float d1chance;
		//chance of a rapid fall per n-tick
		public float d2chance;
		
		public Stock(String name, String shortname, float value, float u2chance, float u1chance, float nchance, float d1chance, float d2chance) {
			this.name = name;
			this.shortname = shortname;
			this.value = new float[15];
			this.u2chance = u2chance;
			this.u1chance = u1chance;
			this.nchance = nchance;
			this.d1chance = d1chance;
			this.d2chance = d2chance;
			
			for(int i = 0; i < 15; i++)
				this.value[i] = value;
		}
		
		public void writeToNBT(NBTTagCompound nbt, int index) {

			for(int i = 0; i < 15; i++)
				nbt.setFloat(index + "_" + i + "_value", value[i]);
		}
		
		public void readFromNBT(NBTTagCompound nbt, int index) {

			for(int i = 0; i < 15; i++)
				value[i] = nbt.getFloat(index + "_" + i + "_value");
		}
	}

	public static class StockEntry {
		
		public Stock stock;
		public String player;
		public int shares;
		
		public StockEntry(Stock stock, String player, int shares) {
			this.stock = stock;
			this.player = player;
			this.shares = shares;
		}
	}
}
