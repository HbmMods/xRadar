package com.hfr.util;

public class ThreeInts implements Comparable {

	//fuck my tight asshole
	
	public int x;
	public int y;
	public int z;
	
	public ThreeInts(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof ThreeInts) {
			ThreeInts ints = (ThreeInts)o;

			return x == ints.x && y == ints.y && z == ints.z;
		}
		
		return false;
	}

	@Override
	public int compareTo(Object o) {
		
		return equals(o) ? 0 : 1;
	}
}
