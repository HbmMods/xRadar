package com.hfr.clowder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hfr.data.ClowderData;
import com.hfr.tileentity.ITerritoryProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClowderTerritory {

	//pre-made instances prevent pointless clutter
	//saves RAM, saves nerves
	public static final Ownership SAFEZONE = new Ownership(Zone.SAFEZONE);
	public static final Ownership WARZONE = new Ownership(Zone.WARZONE);
	public static final Ownership WILDERNESS = new Ownership(Zone.WILDERNESS);

	public static final int SAFEZONE_COLOR = 0xFF8000;
	public static final int WARZONE_COLOR = 0xFF0000;
	public static final int WILDERNESS_COLOR = 0x008000;
	
	public static HashMap<Long, TerritoryMeta> territories = new HashMap();
	
	//the chunk coords in the CodePair wrapper class
	public static CoordPair getCoordPair(int x, int z) {
		
		//why is this necessary? idk but it is
		z += 1;
		
		return new CoordPair(x / 16, z / 16);
	}
	
	//sets the owner of a chunk to a clowder
	public static void setOwnerForCoord(World world, CoordPair coords, Clowder owner, int fX, int fY, int fZ) {
		
		long code = coordsToCode(coords);
		
		territories.remove(code);
		
		Ownership o = new Ownership(Zone.FACTION, owner);
		TerritoryMeta metadata = new TerritoryMeta(o, fX, fY, fZ);
		
		territories.put(code, metadata);
		ClowderData.getData(world).markDirty();
	}
	
	//sets the owner of a chunk to a special zone
	public static void setZoneForCoord(World world, CoordPair coords, Zone zone) {
		
		long code = coordsToCode(coords);
		
		territories.remove(code);
		
		//do not create wilderness k thx
		if(zone != Zone.WILDERNESS) {
			Ownership o = new Ownership(zone, null);
			TerritoryMeta metadata = new TerritoryMeta(o);
			
			territories.put(code, metadata);
		}
		ClowderData.getData(world).markDirty();
	}
	
	//sets the owner of a chunk to a special zone
	public static void removeZoneForCoord(World world, CoordPair coords) {

		long code = coordsToCode(coords);
		territories.remove(code);
		
		ClowderData.getData(world).markDirty();
	}
	
	//returns the ownership information of the chunk
	public static Ownership getOwnerFromCoords(CoordPair coords) {
		
		long code = coordsToCode(coords);
		
		TerritoryMeta meta = territories.get(code);
		
		if(meta == null)
			return WILDERNESS;
		
		Ownership owner = meta.owner;
		
		if(owner.zone == Zone.FACTION && owner.owner == null)
			return WILDERNESS;
		
		return owner == null ? WILDERNESS : owner;
	}
	
	//returns the ownership information of the chunk
	public static Ownership getOwnerFromCoords(int x, int z) {
		
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
		
		Ownership owner = getOwnerFromCoords(getCoordPair((int)player.posX, (int)player.posZ));
		
		if(owner != null && owner.zone == Zone.FACTION && owner.owner == clowder)
			return true;
		
		return false;
		
	}
	
	//returns the ownership information of the chunk
	public static TerritoryMeta getMetaFromCoords(CoordPair coords) {
		
		long code = coordsToCode(coords);
		
		TerritoryMeta meta = territories.get(code);
		
		if(meta != null && meta.owner.zone == Zone.FACTION && meta.owner.owner == null)
			meta.owner = WILDERNESS;
		
		if(meta != null && meta.owner == WILDERNESS)
			return null;
		
		return meta;
	}

	//converts the UUID long code into a CoordPair instance
	public static CoordPair codeToCoords(long code) {

		int x = (int) ((code & 0xFFFFFFFF00000000L) >> 32);
		int z = (int) (code & 0xFFFFFFFFL);

		if((x & (0x1 << 31)) != 0) {
			x &= ~(0x1 << 31);
			x *= -1;
		}
		
		if((z & (0x1 << 31)) != 0) {
			z &= ~(0x1 << 31);
			z *= -1;
		}
		
		return new CoordPair(x, z);
	}

	//converts a CoordPair instance into the UUID long code
	public static long coordsToCode(CoordPair coord) {
		
		int upper = Math.abs(coord.x);
		int lower = Math.abs(coord.z);
		
		//so basically
		//instead of engaging in cock and ball torture to compensate for the fact that negative values
		//of int and long datatypes have different bits, the coord code only stores positive values,
		//indicating whether the original is negative by adding a 1 on the very first bit. this could
		//cause issues with ridiculously large numbers, but in order for this to be an issue, there
		//has to be a claimed chunk so far away from 0/0 that the distance from 0/0 to the claim would
		//exceed the distance to the farlands by a factor of 16.
		
		if(coord.x < 0)
			upper |= (0x1 << 31);
		if(coord.z < 0)
			lower |= (0x1 << 31);

		long shift = (((long)upper) << 32);
		long trunk = ((long)lower) & 0xFFFFFFFFL;
		long code = shift | trunk;
		
		return code;
	}

	public static long intsToCode(int x, int z) {
		
		int upper = Math.abs(x);
		int lower = Math.abs(z);
		
		if(x < 0)
			upper |= (0x1 << 31);
		if(z < 0)
			lower |= (0x1 << 31);

		long shift = (((long)upper) << 32);
		long trunk = ((long)lower) & 0xFFFFFFFFL;
		long code = shift | trunk;
		
		return code;
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
		
		public TerritoryMeta(Ownership owner) {
			this(owner, -1, -1, -1);
		}
		
		public void writeToNBT(NBTTagCompound nbt, String code) {

			owner.writeToNBT(nbt, code);
			nbt.setInteger("terr_" + code + "_flagX", flagX);
			nbt.setInteger("terr_" + code + "_flagY", flagY);
			nbt.setInteger("terr_" + code + "_flagZ", flagZ);
		}
		
		public static TerritoryMeta readFromNBT(NBTTagCompound nbt, String code) {
			
			TerritoryMeta meta = new TerritoryMeta(
					Ownership.readFromNBT(nbt, code),
					nbt.getInteger("terr_" + code + "_flagX"),
					nbt.getInteger("terr_" + code + "_flagY"),
					nbt.getInteger("terr_" + code + "_flagZ")
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
			
			if(world.blockExists(flagX, flagY, flagZ)) {
				
				TileEntity te = world.getTileEntity(flagX, flagY, flagZ);
				
				if(te instanceof ITerritoryProvider) {
					
					ITerritoryProvider flag = (ITerritoryProvider)te;
					
					int r = flag.getRadius();
					
					double dist = Math.sqrt(Math.pow(origin.x - claim.x, 2) + Math.pow(origin.z - claim.z, 2));
					
					if(flag.getOwner() != own) {
						return false;
					} else if(dist >= r) {
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
	
	private static final int cycle = 100;
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
			
			if(meta != null && meta.owner.zone != Zone.WILDERNESS)
				territories.put(code, meta);
		}
	}
	
	public static void writeToNBT(NBTTagCompound nbt) {
		
		nbt.setInteger("territory_count", territories.size());
		int index = 0;
		
		for(long code : territories.keySet()) {
			
			TerritoryMeta meta = territories.get(code);
			
			//do not save wilderness
			if(meta.owner.zone != Zone.WILDERNESS) {
				nbt.setLong("code_" + index, code);
				meta.writeToNBT(nbt, "meta_" + index);
			}
			
			index++;
		}
	}
}
