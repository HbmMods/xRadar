package com.hfr.data;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ClowderData extends WorldSavedData {

	public ClowderData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		Clowder.readFromNBT(nbt);
		ClowderTerritory.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		Clowder.writeToNBT(nbt);
		ClowderTerritory.writeToNBT(nbt);
	}
	
	static ClowderData data = null;
	
	public static ClowderData getData(World worldObj) {
		
		if(worldObj.provider.dimensionId == 0) {
	
			data = (ClowderData)worldObj.perWorldStorage.loadData(ClowderData.class, "hfr_clowder");
		    if(data == null) {
		        worldObj.perWorldStorage.setData("hfr_clowder", new ClowderData("hfr_clowder"));
		        
		        data = (ClowderData)worldObj.perWorldStorage.loadData(ClowderData.class, "hfr_clowder");
		    }
		    
		    return data;
		}
		
	    return null;
	}

}
