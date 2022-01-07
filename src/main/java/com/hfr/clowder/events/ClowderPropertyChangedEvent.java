package com.hfr.clowder.events;

import com.hfr.clowder.Clowder;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.world.World;

public class ClowderPropertyChangedEvent extends Event{
	
	public World world;
	public Clowder clowder;
	public String oldName;
	public String newName;
	public int oldColour;
	public int newColour;
	
	private ClowderPropertyChangedEvent(Clowder clowder) {
		super();
		this.clowder = clowder;
		oldName = clowder.name;
		newName = oldName;
		oldColour = clowder.color;
		newColour = oldColour;
	}
	
	public ClowderPropertyChangedEvent(World world, Clowder clowder, String from, String to) {
		this(clowder);
		this.world = world;
		this.oldName = from;
		this.newName = to;
	}
	
	public ClowderPropertyChangedEvent(World world, Clowder clowder, int from, int to) {
		this(clowder);
		this.world = world;
		this.oldColour = from;
		this.newColour = to;
	}
}
