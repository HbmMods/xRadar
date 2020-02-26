package com.hfr.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class CBTData extends WorldSavedData {
	
	public List<CBTEntry> entries = new ArrayList();

	public CBTData(String name) {
		super(name);
	}
	
	public static CBTData getData(World worldObj) {

		CBTData data = (CBTData)worldObj.perWorldStorage.loadData(CBTData.class, "hfr_cbt");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hfr_cbt", new CBTData("hfr_cbt"));
	        
	        data = (CBTData)worldObj.perWorldStorage.loadData(CBTData.class, "hfr_cbt");
	    }
	    
	    return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		int size = nbt.getInteger("size");
		
		for(int i = 0; i < size; i++) {
			
			entries.add(new CBTEntry(
				nbt.getString(i + "_name"),
				nbt.getBoolean(i + "_fps"),
				nbt.getBoolean(i + "_tilt"),
				nbt.getBoolean(i + "_shader")
			));
		}
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("size", entries.size());
		
		for(int i = 0; i < entries.size(); i++) {

			nbt.setString(i + "_name", entries.get(i).player);
			nbt.setBoolean(i + "_fps", entries.get(i).fps);
			nbt.setBoolean(i + "_tilt", entries.get(i).tilt);
			nbt.setBoolean(i + "_shader", entries.get(i).shader);
		}
	}
	
	public CBTEntry getEntryByPlayerName(String player) {
		
		for(CBTEntry entry : entries) {
			if(entry.player.equals(player)) {
				return entry;
			}
		}
		
		return null;
	}
	
	public static class CBTEntry {
		
		public String player;
		public boolean fps;
		public boolean tilt;
		public boolean shader;
		
		public CBTEntry(String player){
			this.player = player;
		}
		
		public CBTEntry(String player, boolean fps, boolean tilt, boolean shader){
			this.player = player;
			this.fps = fps;
			this.tilt = tilt;
			this.shader = shader;
		}
	}

}
