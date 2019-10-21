package com.hfr.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class AntiMobData extends WorldSavedData {
	
	public List<int[]> list = new ArrayList();

	public AntiMobData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {

		int count = nbt.getInteger("count");
		
		for(int i = 0; i < count; i++) {
			addArea(
					nbt.getInteger(i + "_x1"),
					nbt.getInteger(i + "_z1"),
					nbt.getInteger(i + "_x2"),
					nbt.getInteger(i + "_z2")
			);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		nbt.setInteger("count", list.size());
		
		for(int i = 0; i < list.size(); i++) {
			nbt.setInteger(i + "_x1", list.get(i)[0]);
			nbt.setInteger(i + "_z1", list.get(i)[1]);
			nbt.setInteger(i + "_x2", list.get(i)[2]);
			nbt.setInteger(i + "_z2", list.get(i)[3]);
		}
	}
	
	public void addArea(int x1, int z1, int x2, int z2) {
		
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
	
	public static AntiMobData getData(World worldObj) {

		AntiMobData data = (AntiMobData)worldObj.perWorldStorage.loadData(AntiMobData.class, "hfr_antimob");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("hfr_antimob", new AntiMobData("hfr_antimob"));
	        
	        data = (AntiMobData)worldObj.perWorldStorage.loadData(AntiMobData.class, "hfr_antimob");
	    }
	    
	    return data;
	}

}
