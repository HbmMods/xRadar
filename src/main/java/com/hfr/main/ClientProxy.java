 package com.hfr.main;

import net.minecraft.item.Item;

import com.hfr.render.*;
import com.hfr.tileentity.*;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends ServerProxy
{
	@Override
	public void registerRenderInfo()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceField.class, new RenderMachineForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
	}
	
	@Override
	public void registerTileEntitySpecialRenderer() {
		
	}
}

