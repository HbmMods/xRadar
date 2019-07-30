package com.hfr.main;

import net.minecraft.world.World;

public class ServerProxy
{
	public void registerRenderInfo() { }
	
	public void registerTileEntitySpecialRenderer() { }
	
	public void howDoIUseTheZOMG(World world, double posX, double posY, double posZ, int type) { }

	public void addBlip(float x, float y, float z, int type) { }

	public void clearBlips(boolean sufficient, boolean enabled, int offset, int range) { }
	
	public boolean isPressed(int id) { return false; }

	public void spawnSFX(World world, double posX, double posY, double posZ, int type, Object payload) { }
}