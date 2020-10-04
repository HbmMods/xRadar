package com.hfr.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ResourceData extends WorldSavedData {

	public List<int[]> iron = new ArrayList();
	public List<int[]> coal = new ArrayList();

	public ResourceData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		int ironcount = nbt.getInteger("ironcount");
		
		for(int i = 0; i < ironcount; i++) {
			addArea(
					nbt.getInteger(i + "_x1iron"),
					nbt.getInteger(i + "_z1iron"),
					nbt.getInteger(i + "_x2iron"),
					nbt.getInteger(i + "_z2iron"),
					iron
			);
		}

		int coalcount = nbt.getInteger("coalcount");
		
		for(int i = 0; i < coalcount; i++) {
			addArea(
					nbt.getInteger(i + "_x1coal"),
					nbt.getInteger(i + "_z1coal"),
					nbt.getInteger(i + "_x2coal"),
					nbt.getInteger(i + "_z2coal"),
					coal
			);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		nbt.setInteger("ironcount", iron.size());
		
		for(int i = 0; i < iron.size(); i++) {
			nbt.setInteger(i + "_x1iron", iron.get(i)[0]);
			nbt.setInteger(i + "_z1iron", iron.get(i)[1]);
			nbt.setInteger(i + "_x2iron", iron.get(i)[2]);
			nbt.setInteger(i + "_z2iron", iron.get(i)[3]);
		}

		nbt.setInteger("coalcount", coal.size());
		
		for(int i = 0; i < coal.size(); i++) {
			nbt.setInteger(i + "_x1coal", coal.get(i)[0]);
			nbt.setInteger(i + "_z1coal", coal.get(i)[1]);
			nbt.setInteger(i + "_x2coal", coal.get(i)[2]);
			nbt.setInteger(i + "_z2coal", coal.get(i)[3]);
		}
	}
	
	public void addArea(int x1, int z1, int x2, int z2, List<int[]> list) {
		
		//because AABBs only work with specific rules, X1<X2 and Z1<Z2
		
		int xa = x1;
		int xb = x2;
		
		if(x1 > x2) {
			xa = x2;
			xb = x1;
		}
		
		int za = z1;
		int zb = z2;
		
		if(z1 > z2) {
			za = z2;
			zb = z1;
		}
		
		list.add(new int[] {xa, za, xb, zb} );
		this.markDirty();
	}
	
	public boolean isInArea(int x, int z, List<int[]> list) {
		
		for(int[] coord : list) {
			
			if(x >= coord[0] && z >= coord[1] && x <= coord[2] && z <= coord[3])
				return true;
		}
		
		return false;
	}
	
	public static ResourceData getData(World worldObj) {

		ResourceData data = (ResourceData)worldObj.perWorldStorage.loadData(ResourceData.class, "hfr_ore");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hfr_ore", new ResourceData("hfr_ore"));
	        
	        data = (ResourceData)worldObj.perWorldStorage.loadData(ResourceData.class, "hfr_ore");
	    }
	    
	    return data;
	}

}
