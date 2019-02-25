 package com.hfr.main;

import net.minecraft.item.Item;
import net.minecraftforge.client.model.AdvancedModelLoader;

import com.hfr.entity.*;
import com.hfr.loader.HmfModelLoader;
import com.hfr.render.*;
import com.hfr.tileentity.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy
{
	@Override
	public void registerRenderInfo()
	{
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadar.class, new RenderRadar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForceField.class, new RenderMachineForceField());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunch());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileGeneric.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiary.class, new RenderMissileGeneric());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileAntiBallistic.class, new RenderMissileGeneric());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileStrong.class, new RenderMissileStrong());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiaryStrong.class, new RenderMissileStrong());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMPStrong.class, new RenderMissileStrong());

		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBurst.class, new RenderMissileHuge());
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileInferno.class, new RenderMissileHuge());

		RenderingRegistry.registerEntityRenderingHandler(EntitySmokeFX.class, new MultiCloudRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK3());
	}
	
	@Override
	public void registerTileEntitySpecialRenderer() { }
}

