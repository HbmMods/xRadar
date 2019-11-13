package com.hfr.clowder;

import java.util.HashMap;

public class ClowderTerritory {

	//pre-made instances prevent pointless clutter
	//saves ram, saves nerves
	public static final Ownership SAFEZONE = new Ownership(Zone.SAFEZONE, null);
	public static final Ownership WARZONE = new Ownership(Zone.WARZONE, null);
	public static final Ownership WILDERNESS = new Ownership(Zone.WILDERNESS, null);
	
	public static HashMap<Long, Ownership> territories = new HashMap();
	
	//the chunk coords in the CodePair wrapper class
	public static CoordPair getCoordPair(int x, int z) {
		
		z += 1;
		
		return new CoordPair(x / 16, z / 16);
	}
	
	//sets the owner of a chunk to a clowder
	public static void setOwnerForCoord(CoordPair coords, Clowder owner) {
		
		long code = coordsToCode(coords);
		
		territories.remove(coords);
		territories.put(code, new Ownership(Zone.FACTION, owner));
	}
	
	//sets the owner of a chunk to a special zone
	public static void setZoneForCoord(CoordPair coords, Zone zone) {
		
		long code = coordsToCode(coords);
		
		territories.remove(coords);
		territories.put(code, new Ownership(zone, null));
	}
	
	//returns the ownership information of the chunk
	public static Ownership getOwnerFromCoords(CoordPair coords) {
		
		long code = coordsToCode(coords);
		
		Ownership owner = territories.get(code);
		
		return owner != null ? owner : WILDERNESS;
	}

	//converts the UUID long code into a CoordPair instance
	public static CoordPair codeToCoords(long code) {

		int x = (int) ((code & 0xFFFFFFFF00000000L) >> 32);
		int z = (int) (code & 0xFFFFFFFFL);
		
		return new CoordPair(x, z);
	}

	//converts a CoordPair instance into the UUID long code
	public static long coordsToCode(CoordPair coord) {
		
		long code = coord.z;
		code |= (((long)coord.x) << 32);
		
		return code;
	}
	
	public static class Ownership {
		
		public Zone zone;
		public Clowder owner;
		
		public Ownership(Zone zone, Clowder owner) {
			this.zone = zone;
			this.owner = owner;
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
		//pvp is disabled for team mates
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
	
	//TODO
	public static class TerritoryMeta {
		
		public Ownership owner;
		public int flagX;
		public int flagY;
		public int flagZ;
	}
}
