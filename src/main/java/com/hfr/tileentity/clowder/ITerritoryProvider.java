package com.hfr.tileentity.clowder;

import com.hfr.clowder.Clowder;

public interface ITerritoryProvider {

	public int getRadius();
	public Clowder getOwner();
	public String getClaimName();
	public void setClaimName(String name);
}
