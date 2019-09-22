package com.hfr.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hfr.blocks.ModBlocks;
import com.hfr.inventory.*;
import com.hfr.inventory.container.ContainerForceField;
import com.hfr.inventory.container.ContainerHydro;
import com.hfr.inventory.container.ContainerLaunchPad;
import com.hfr.inventory.container.ContainerMachineBuilder;
import com.hfr.inventory.container.ContainerMachineMarket;
import com.hfr.inventory.container.ContainerMachineNet;
import com.hfr.inventory.container.ContainerMachineOilWell;
import com.hfr.inventory.container.ContainerMachineRadar;
import com.hfr.inventory.container.ContainerMachineRefinery;
import com.hfr.inventory.container.ContainerMachineSiren;
import com.hfr.inventory.container.ContainerNaval;
import com.hfr.inventory.container.ContainerRailgun;
import com.hfr.inventory.container.ContainerTank;
import com.hfr.inventory.gui.GUIForceField;
import com.hfr.inventory.gui.GUIHydro;
import com.hfr.inventory.gui.GUILaunchPad;
import com.hfr.inventory.gui.GUIMachineBuilder;
import com.hfr.inventory.gui.GUIMachineMarket;
import com.hfr.inventory.gui.GUIMachineNet;
import com.hfr.inventory.gui.GUIMachineOilWell;
import com.hfr.inventory.gui.GUIMachineRadar;
import com.hfr.inventory.gui.GUIMachineRefinery;
import com.hfr.inventory.gui.GUIMachineSiren;
import com.hfr.inventory.gui.GUINaval;
import com.hfr.inventory.gui.GUIRailgun;
import com.hfr.inventory.gui.GUIScreenDesignator;
import com.hfr.inventory.gui.GUITank;
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

			case ModBlocks.guiID_railgun:
			{
				if(entity instanceof TileEntityRailgun)
				{
					return new ContainerRailgun(player.inventory, (TileEntityRailgun) entity);
				}
				return null;
			}

			case ModBlocks.guiID_tank:
			{
				if(entity instanceof TileEntityTank)
				{
					return new ContainerTank(player.inventory, (TileEntityTank) entity);
				}
				return null;
			}

			case ModBlocks.guiID_naval:
			{
				if(entity instanceof TileEntityNaval)
				{
					return new ContainerNaval(player.inventory, (TileEntityNaval) entity);
				}
				return null;
			}

			case ModBlocks.guiID_hydro:
			{
				if(entity instanceof TileEntityHydro)
				{
					return new ContainerHydro(player.inventory, (TileEntityHydro) entity);
				}
				return null;
			}

			case ModBlocks.guiID_net:
			{
				if(entity instanceof TileEntityMachineNet)
				{
					return new ContainerMachineNet(player.inventory, (TileEntityMachineNet) entity);
				}
				return null;
			}

			case ModBlocks.guiID_market:
			{
				if(entity instanceof TileEntityMachineMarket)
				{
					return new ContainerMachineMarket(player.inventory, (TileEntityMachineMarket) entity);
				}
				return null;
			}

			case ModBlocks.guiID_builder:
			{
				if(entity instanceof TileEntityMachineBuilder)
				{
					return new ContainerMachineBuilder(player.inventory, (TileEntityMachineBuilder) entity);
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
				
				case ModBlocks.guiID_railgun:
				{
					if(entity instanceof TileEntityRailgun)
					{
						return new GUIRailgun(player.inventory, (TileEntityRailgun) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_tank:
				{
					if(entity instanceof TileEntityTank)
					{
						return new GUITank(player.inventory, (TileEntityTank) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_naval:
				{
					if(entity instanceof TileEntityNaval)
					{
						return new GUINaval(player.inventory, (TileEntityNaval) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_hydro:
				{
					if(entity instanceof TileEntityHydro)
					{
						return new GUIHydro(player.inventory, (TileEntityHydro) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_net:
				{
					if(entity instanceof TileEntityMachineNet)
					{
						return new GUIMachineNet(player.inventory, (TileEntityMachineNet) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_market:
				{
					if(entity instanceof TileEntityMachineMarket)
					{
						return new GUIMachineMarket(player.inventory, (TileEntityMachineMarket) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_builder:
				{
					if(entity instanceof TileEntityMachineBuilder)
					{
						return new GUIMachineBuilder(player.inventory, (TileEntityMachineBuilder) entity);
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
