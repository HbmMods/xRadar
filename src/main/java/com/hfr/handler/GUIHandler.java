package com.hfr.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hfr.blocks.ModBlocks;
import com.hfr.inventory.*;
import com.hfr.items.ModItems;
import com.hfr.tileentity.*;

import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		switch(ID)
		{
			case ModBlocks.guiID_siren:
			{
				if(entity instanceof TileEntityMachineSiren)
				{
					return new ContainerMachineSiren(player.inventory, (TileEntityMachineSiren) entity);
				}
				return null;
			}

			case ModBlocks.guiID_radar:
			{
				if(entity instanceof TileEntityMachineRadar)
				{
					return new ContainerMachineRadar(player.inventory, (TileEntityMachineRadar) entity);
				}
				return null;
			}

			case ModBlocks.guiID_forcefield:
			{
				if(entity instanceof TileEntityForceField)
				{
					return new ContainerForceField(player.inventory, (TileEntityForceField) entity);
				}
				return null;
			}

			case ModBlocks.guiID_launchpad:
			{
				if(entity instanceof TileEntityLaunchPad)
				{
					return new ContainerLaunchPad(player.inventory, (TileEntityLaunchPad) entity);
				}
				return null;
			}

			case ModBlocks.guiID_derrick:
			{
				if(entity instanceof TileEntityMachineDerrick)
				{
					return new ContainerMachineOilWell(player.inventory, (TileEntityMachineDerrick) entity);
				}
				return null;
			}

			case ModBlocks.guiID_refinery:
			{
				if(entity instanceof TileEntityMachineRefinery)
				{
					return new ContainerMachineRefinery(player.inventory, (TileEntityMachineRefinery) entity);
				}
				return null;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity != null)
		{
			switch(ID)
			{
				case ModBlocks.guiID_siren:
				{
					if(entity instanceof TileEntityMachineSiren)
					{
						return new GUIMachineSiren(player.inventory, (TileEntityMachineSiren) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_radar:
				{
					if(entity instanceof TileEntityMachineRadar)
					{
						return new GUIMachineRadar(player.inventory, (TileEntityMachineRadar) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_forcefield:
				{
					if(entity instanceof TileEntityForceField)
					{
						return new GUIForceField(player.inventory, (TileEntityForceField) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_launchpad:
				{
					if(entity instanceof TileEntityLaunchPad)
					{
						return new GUILaunchPad(player.inventory, (TileEntityLaunchPad) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_derrick:
				{
					if(entity instanceof TileEntityMachineDerrick)
					{
						return new GUIMachineOilWell(player.inventory, (TileEntityMachineDerrick) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_refinery:
				{
					if(entity instanceof TileEntityMachineRefinery)
					{
						return new GUIMachineRefinery(player.inventory, (TileEntityMachineRefinery) entity);
					}
					return null;
				}
			}
		} else {
			
			switch(ID)
			{
				case ModItems.guiID_desingator:
				{
					return new GUIScreenDesignator(player);
				}
			}
		}
		return null;
	}

}
