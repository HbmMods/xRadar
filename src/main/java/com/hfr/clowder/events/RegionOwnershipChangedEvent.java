package com.hfr.clowder.events;
import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderTerritory.Ownership;

import cpw.mods.fml.common.eventhandler.Event;

public class RegionOwnershipChangedEvent extends Event {

	public Ownership from;
	public Ownership to;
	public String provinceName;
	
	public RegionOwnershipChangedEvent(Ownership from, Ownership to, String provinceName) {
		super();
		this.from = from;
		this.to = to;
		this.provinceName = provinceName;
	}
	
}
