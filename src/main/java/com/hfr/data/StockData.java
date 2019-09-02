package com.hfr.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hfr.main.MainRegistry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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
	
	public Stock getStockByShort(String shortname) {
		
		for(Stock stock : stocks) {
			if(stock.shortname.equals(shortname))
				return stock;
		}
		
		return null;
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
	
	public enum EnumStockPhase {
		U2,	//up 2, strong boost
		U1,	//up 1, mild boost
		N,	//neutral, recession
		D1,	//down 1, mild fall
		D2	//down 2, strong fall
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
		//client-only for how many shares the player has
		public int shares;
		//server-only for what phase the stock currently goes through
		public EnumStockPhase phase = EnumStockPhase.N;
		
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
		
		//ctor for client information
		public Stock(String name, String shortname, float value[], int shares) {
			this.name = name;
			this.shortname = shortname;
			this.value = value;
			this.shares = shares;
		}
		
		public void writeToNBT(NBTTagCompound nbt, int index) {

			for(int i = 0; i < 15; i++)
				nbt.setFloat(index + "_" + i + "_value", value[i]);
		}
		
		public void readFromNBT(NBTTagCompound nbt, int index) {

			for(int i = 0; i < 15; i++)
				value[i] = nbt.getFloat(index + "_" + i + "_value");
		}

		Random rand = new Random();
		public void rollTheDice() {

			if(value[14] < 5) {
				phase = EnumStockPhase.U2;
				return;
			}
			if(value[14] > 95) {
				phase = EnumStockPhase.D2;
				return;
			}
			
			//default back to recession
			if(phase != EnumStockPhase.N && rand.nextFloat() * 100 < nchance) {
				phase = EnumStockPhase.N;
				return;
			}
			
			//select new phase is recessing
			if(phase == EnumStockPhase.N) {
				
				switch(rand.nextInt(4)) {
				case 0:
					if(rand.nextFloat() * 100 < u1chance) {
						phase = EnumStockPhase.U1;
						
						if(MainRegistry.u1en && !MainRegistry.u1.isEmpty())
							sendMsg(MainRegistry.u1.get(rand.nextInt(MainRegistry.u1.size())));
						return;
					} break;

				case 1:
					if(rand.nextFloat() * 100 < d1chance) {
						phase = EnumStockPhase.D1;
						
						if(MainRegistry.d1en && !MainRegistry.d1.isEmpty())
							sendMsg(MainRegistry.d1.get(rand.nextInt(MainRegistry.d1.size())));
						return;
					} break;

				case 2:
					if(rand.nextFloat() * 100 < u2chance) {
						phase = EnumStockPhase.U2;
						
						if(MainRegistry.u2en && !MainRegistry.u2.isEmpty())
							sendMsg(MainRegistry.u2.get(rand.nextInt(MainRegistry.u2.size())));
						return;
					} break;

				case 3:
					if(rand.nextFloat() * 100 < d2chance) {
						phase = EnumStockPhase.D2;
						
						if(MainRegistry.d2en && !MainRegistry.d2.isEmpty())
							sendMsg(MainRegistry.d2.get(rand.nextInt(MainRegistry.d2.size())));
						return;
					} break;
				}
			}
		}
		
		private void sendMsg(String s) {

			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(
					EnumChatFormatting.GOLD + "[" + EnumChatFormatting.BLUE + "Stock Market" +
					EnumChatFormatting.GOLD + "] " + s.replace("%s", shortname)));
		}
		
		public void update() {
			
			switch(phase) {
			case U2:
				value[14] = value[14] + rand.nextFloat() * 1.75F * 2 - 0.0F;
				break;
			case U1:
				value[14] = value[14] + rand.nextFloat() * 1.5F * 2 - 0.5F;
				break;
			case D1:
				value[14] = value[14] - rand.nextFloat() * 1.5F * 2 - 0.5F;
				break;
			case D2:
				value[14] = value[14] - rand.nextFloat() * 1.75F * 2 - 0.0F;
				break;
			case N:
				value[14] = value[14] + rand.nextFloat() * 1.25F * 2 - 1.25F;
				break;
			}
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
