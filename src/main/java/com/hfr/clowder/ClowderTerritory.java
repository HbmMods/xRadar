package com.hfr.clowder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hfr.data.ClowderData;
import com.hfr.main.MainRegistry;
import com.hfr.tileentity.clowder.ITerritoryProvider;
import com.hfr.tileentity.clowder.TileEntityConquerer;
import com.hfr.tileentity.clowder.TileEntityFlag;
import com.hfr.tileentity.clowder.TileEntityFlagBig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ClowderTerritory {

	//pre-made instances prevent pointless clutter
	//saves RAM, saves nerves
	public static final Ownership SAFEZONE = new Ownership(Zone.SAFEZONE);
	public static final Ownership WARZONE = new Ownership(Zone.WARZONE);
	public static final Ownership WILDERNESS = new Ownership(Zone.WILDERNESS);

	public static final int SAFEZONE_COLOR = 0xFF8000;
	public static final int WARZONE_COLOR = 0xFF0000;
	public static final int WILDERNESS_COLOR = 0xFFFFFF;
	
	public static HashMap<Long, TerritoryMeta> territories = new HashMap();
	
	//the chunk coords in the CodePair wrapper class
	public static CoordPair getCoordPair(int x, int z) {
		
		//why is this necessary? idk but it is
		x += 1;
		z += 1;
		
		return new CoordPair(x / 16, z / 16);
	}
	
	//sets the owner of a chunk to a clowder
	public static void setOwnerForCoord(World world, CoordPair coords, Clowder owner, int fX, int fY, int fZ, String name) {
		
		setOwnerForInts(world, coords.x, coords.z, owner, fX, fY, fZ, name);
	}
	
	//sets the owner of a chunk to a clowder
	public static void setOwnerForInts(World world, int x, int z, Clowder owner, int fX, int fY, int fZ, String name) {
		
		long code = intsToCode(x, z);
		TerritoryMeta old = territories.get(code);
		
		territories.remove(code);
		
		Ownership o = new Ownership(Zone.FACTION, owner);
		TerritoryMeta metadata = new TerritoryMeta(o, fX, fY, fZ);
		TileEntity flag = world.getTileEntity(fX, fY, fZ);
		if(flag != null) {
			if(flag instanceof TileEntityFlagBig)
				((TileEntityFlagBig)flag).provinceName = name;
			else if(flag instanceof TileEntityConquerer)
				((TileEntityConquerer)flag).name = name;
		}
		
		territories.put(code, metadata);
		ClowderData.getData(world).markDirty();
	}
	
	public static void setOilProvinceForCoord(World world, CoordPair coords, Clowder owner, int fX, int fY, int fZ, String name, boolean oil) {
		long code = intsToCode(coords.x, coords.z);
		if(territories.containsKey(code))
			setOilProvinceForCode(world, code, oil);
	}
	
	public static void setOilProvinceForCode(World world, long code, boolean oil) {
		TerritoryMeta meta = territories.get(code);
		TileEntityFlagBig flag = (TileEntityFlagBig) world.getTileEntity(meta.flagX, meta.flagY, meta.flagZ);
		if(flag != null)
			flag.oilProvince = oil;
		ClowderData.getData(world).markDirty();
	}
	
	//sets the owner of a chunk back to wilderness and keeps the region name
	public static void setWildernessForCoord(World world, CoordPair coords) {
		setWildernessForInts(coords.x, coords.z);
	}
	
	//sets the owner of a chunk back to wilderness and keeps the region name
	public static void setWildernessForInts(int x, int z) {
		long code = intsToCode(x, z);
		
		territories.get(code).owner = new Ownership(Zone.WILDERNESS);
	}
	
	//sets the owner of a chunk to a special zone
	public static void setZoneForCoord(World world, CoordPair coords, Zone zone) {
		
		setZoneForInts(world, coords.x, coords.z, zone);
	}
	
	//sets the owner of a chunk to a special zone
	public static void setZoneForInts(World world, int x, int z, Zone zone) {
		
		long code = intsToCode(x, z);
		
		//do not create wilderness k thx
		if(zone != Zone.WILDERNESS) {
			Ownership o = new Ownership(zone, null);
			TerritoryMeta metadata = new TerritoryMeta(o);
			
			territories.put(code, metadata);
		}
		ClowderData.getData(world).markDirty();
	}
	
	//removes territory metadata
	public static void removeZoneForCoord(World world, CoordPair coords) {

		removeZoneForInts(world, coords.x, coords.z);
	}

	//removes territory metadata
	public static void removeZoneForInts(World world, int x, int z) {

		long code = intsToCode(x, z);
		territories.remove(code);
		
		ClowderData.getData(world).markDirty();
	}
	
	//returns the ownership information of the chunk
	public static Ownership getOwnerFromCoords(CoordPair coords) {
		
		return getOwner(coords.x, coords.z);
	}
	
	//returns the ownership information of the chunk
	public static Ownership getOwnerFromInts(int x, int z) {

		z += 1;
		
		return getOwner(x / 16, z / 16);
	}
	
	//returns the ownership information of the chunk
	public static Ownership getOwner(int x, int z) {
		
		long code = intsToCode(x, z);
		
		TerritoryMeta meta = territories.get(code);
		
		if(meta == null)
			return WILDERNESS;
		
		Ownership owner = meta.owner;
		
		if(owner.zone == Zone.FACTION && owner.owner == null)
			return WILDERNESS;
		
		return owner == null ? WILDERNESS : owner;
	}
	
	//returns true if a player is in a clowder and standing in his home territory
	public static boolean isPlayerHome(EntityPlayer player) {
		
		Clowder clowder = Clowder.getClowderFromPlayer(player);
		
		if(clowder == null)
			return false;
		
		Ownership owner = getOwnerFromInts((int)player.posX, (int)player.posZ);
		
		if(owner != null && owner.zone == Zone.FACTION && owner.owner == clowder)
			return true;
		
		return false;
		
	}
	
	//returns the ownership information of the chunk
	public static TerritoryMeta getMetaFromIntCoords(int x, int z) {

		z += 1;
		
		return getMetaFromInts(x / 16, z / 16);
	}
	
	//returns the ownership information of the chunk
	public static TerritoryMeta getMetaFromCoords(CoordPair coords) {
		
		return getMetaFromInts(coords.x, coords.z);
	}
	
	//returns the ownership information of the chunk
	public static TerritoryMeta getMetaFromInts(int x, int z) {
		
		long code = intsToCode(x, z);
		
		TerritoryMeta meta = territories.get(code);
		
		if(meta != null && meta.owner.zone == Zone.FACTION && meta.owner.owner == null)
			meta.owner = WILDERNESS;
		
		if(meta != null && meta.owner == WILDERNESS)
			return null;
		
		return meta;
	}

	//converts the UUID long code into a CoordPair instance
	public static CoordPair codeToCoords(long code) {

		return new CoordPair((int)(code >> 32), (int)code);
	}

	//converts a CoordPair instance into the UUID long code
	public static long coordsToCode(CoordPair coord) {
		
		return intsToCode(coord.x, coord.z);
	}

	public static long intsToCode(int x, int z) {
		
        return ((long)x & 0xFFFFFFFFL) << 32 | ((long)z & 0xFFFFFFFFL);
	}
	
	public static class Ownership {
		
		public Zone zone;
		public Clowder owner;
		
		public Ownership(Zone zone, Clowder owner) {
			this.zone = zone;
			
			if(zone == Zone.FACTION)
				this.owner = owner;
		}
		
		public Ownership(Zone zone) {
			this.zone = zone;
		}
		
		public void writeToNBT(NBTTagCompound nbt, String code) {
			
			if(zone == Zone.FACTION && owner == null)
				return;
			
			nbt.setInteger("ownership_" + code + "_zone", zone.ordinal());
			
			if(zone == Zone.FACTION)
				nbt.setString("ownership_" + code + "_owner", owner.name);
		}
		
		public static Ownership readFromNBT(NBTTagCompound nbt, String code) {
			
			Zone zone = Zone.values()[nbt.getInteger("ownership_" + code + "_zone")];
			
			Clowder clowder = null;
			
			if(zone == Zone.FACTION) {
				clowder = Clowder.getClowderFromName(nbt.getString("ownership_" + code + "_owner"));
			}
			
			if(zone == Zone.FACTION && clowder == null)
				return WILDERNESS;
			
			Ownership ownership = new Ownership (zone, clowder);
			
			return ownership;
		}
		
		public int getColor() {
				
			switch(zone) {
			case FACTION:
				return owner.color;
			case SAFEZONE:
				return SAFEZONE_COLOR;
			case WARZONE:
				return WARZONE_COLOR;
			case WILDERNESS:
				return WILDERNESS_COLOR;
			}
			return 0x000000;
		}
	}
	
	public static enum Zone {
		//no building, no pvp
		SAFEZONE,
		//no building
		WARZONE,
		//no restrictions
		WILDERNESS,
		//only the owning team can edit this terrain
		//pvp is disabled for team mates (?)
		FACTION
	}
	
	//it's just two integers in a wrapper
	//don't judge me vanilla minecraft does it too since 1.8 just with 3 integers
	public static class CoordPair {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + z;
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CoordPair other = (CoordPair) obj;
			if (x != other.x)
				return false;
			if (z != other.z)
				return false;
			return true;
		}

		public int x;
		public int z;
		
		public CoordPair(int x, int z) {
			this.x = x;
			this.z = z;
		}
	}
	
	public static class TerritoryMeta {
		
		public Ownership owner;
		public int flagX;
		public int flagY;
		public int flagZ;
		
		public TerritoryMeta(Ownership owner, int flagX, int flagY, int flagZ) {
			this.owner = owner;
			this.flagX = flagX;
			this.flagY = flagY;
			this.flagZ = flagZ;
		}
		
		public TerritoryMeta(Ownership owner, int flagX, int flagY, int flagZ, World world, String name) {
			this.owner = owner;
			this.flagX = flagX;
			this.flagY = flagY;
			this.flagZ = flagZ;
			TileEntityFlagBig flag = (TileEntityFlagBig) world.getTileEntity(flagX, flagY, flagZ);
			if(flag != null)
				flag.provinceName = name;
		}
		
		public TerritoryMeta(Ownership owner) {
			this(owner, -1, -1, -1);
		}
		
		public void writeToNBT(NBTTagCompound nbt, String code) {
			owner.writeToNBT(nbt, code);
			nbt.setInteger(code + "X",flagX);
			nbt.setInteger(code + "Y",flagY);
			nbt.setInteger(code + "Z",flagZ);
		}
		
		public static TerritoryMeta readFromNBT(NBTTagCompound nbt, String code) {
			
			TerritoryMeta meta = new TerritoryMeta(
					Ownership.readFromNBT(nbt, code),
					nbt.getInteger(code + "X"),
					nbt.getInteger(code + "Y"),
					nbt.getInteger(code + "Z")
			);
			
			return meta;
		}
		
		public int getColor() {
			
			if(owner != null) {
				return owner.getColor();
			}
			
			return 0x000000;
		}
		
		//chunks will persist if there's an operational flag within its bounds or if the supposedly flag-bearing chunk is not loaded
		public boolean checkPersistence(World world, CoordPair claim) {
			
			if(owner.zone != Zone.FACTION)
				return true;
			
			if(flagY < 0)
				return false;
			
			Clowder own = owner.owner;
			CoordPair origin = getCoordPair(flagX, flagZ);
			
			if(world == null || world.getChunkProvider() == null)
				return true;
			
			Chunk chunk = world.getChunkProvider().provideChunk(flagX >> 4, flagZ >> 4);
			
			if(chunk != null) {
				
				TileEntity te = world.getTileEntity(flagX, flagY, flagZ);
				
				if(te instanceof ITerritoryProvider) {
					
					ITerritoryProvider flag = (ITerritoryProvider)te;
					
					int r = flag.getRadius();
					
					double dist = Math.sqrt(Math.pow(origin.x - claim.x, 2) + Math.pow(origin.z - claim.z, 2));
					
					if(flag.getOwner() != own) {
						return false;
					} else if(dist >= r) {
						
						if(flag instanceof TileEntityFlag && ((TileEntityFlag)flag).height > 0 && ((TileEntityFlag)flag).height < 1.0F)
							return true;
						
						if(flag instanceof TileEntityConquerer && ((TileEntityConquerer)flag).height > 0)
							return true;
						
						return false;
					} else {
						return true;
					}
				} else {
					return false;
				}
			}
			
			//return true is the chunk does not exist, i.e. is not loaded to prevent spontaneous claim-decay
			return true;
		}
	}
	
	//checks a part of the clowder claim data for persistence, will delete non-persistent ones
	public static void checkPersistence(World world, int cycle, int index) {
		
		List<Long> BOW = new ArrayList(territories.keySet());
		
		for(int i = index; i < BOW.size(); i += cycle) {
			
			long code = BOW.get(i);
			TerritoryMeta meta = territories.get(code);
			
			//code will be deleted IF
			// -the code has no value assigned to it (null-value)
			// -the code refers to a claim that is deemed non-persistent
			if(meta != null) {
				
				if(!meta.checkPersistence(world, codeToCoords(code))) {
					territories.remove(code);
					i--;
				}
				
			} else {
				territories.remove(code);
				i--;
			}
		}
	}
	
	private static final int cycle = MainRegistry.territoryAmount;
	private static int ptr = 0;
	
	//i called it an automaton because it mindlessly iterates through the persistence checks without the common handler having to do anything in addition
	public static void persistenceAutomaton(World world) {
		
		ptr++;
		ptr = ptr % cycle;
		
		checkPersistence(world, cycle, ptr);
	}
	
	public static void readFromNBT(NBTTagCompound nbt) {
		
		territories.clear();
		int count = nbt.getInteger("territory_count");
		
		for(int i = 0; i < count; i++) {
			
			long code = nbt.getLong("code_" + i);
			TerritoryMeta meta = TerritoryMeta.readFromNBT(nbt, "meta_" + i);
			if(meta != null) {
				territories.put(code, meta);
			}
		}
	}
	
	public static void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("territory_count", territories.size());
		int index = 0;
		
		for(long code : territories.keySet()) {
			
			TerritoryMeta meta = territories.get(code);
			//we only have regions as wilderness the rest will be warzone
			nbt.setLong("code_" + index, code);
			meta.writeToNBT(nbt, "meta_" + index);
			
			index++;
		}
	}
}
