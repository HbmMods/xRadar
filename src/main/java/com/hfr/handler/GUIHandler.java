package com.hfr.handler;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hfr.blocks.ModBlocks;
import com.hfr.blocks.machine.MachineMarket.TileEntityMarket;
import com.hfr.inventory.container.ContainerAlloy;
import com.hfr.inventory.container.ContainerBattery;
import com.hfr.inventory.container.ContainerBlastFurnace;
import com.hfr.inventory.container.ContainerBox;
import com.hfr.inventory.container.ContainerCoalGen;
import com.hfr.inventory.container.ContainerCoalMine;
import com.hfr.inventory.container.ContainerCrusher;
import com.hfr.inventory.container.ContainerDieselGen;
import com.hfr.inventory.container.ContainerDistillery;
import com.hfr.inventory.container.ContainerEFurnace;
import com.hfr.inventory.container.ContainerFactory;
import com.hfr.inventory.container.ContainerFlag;
import com.hfr.inventory.container.ContainerFlagBig;
import com.hfr.inventory.container.ContainerForceField;
import com.hfr.inventory.container.ContainerFoundry;
import com.hfr.inventory.container.ContainerGrainMill;
import com.hfr.inventory.container.ContainerHydro;
import com.hfr.inventory.container.ContainerLaunchPad;
import com.hfr.inventory.container.ContainerMachineBuilder;
import com.hfr.inventory.container.ContainerMachineEMP;
import com.hfr.inventory.container.ContainerMachineNet;
import com.hfr.inventory.container.ContainerMachineOilWell;
import com.hfr.inventory.container.ContainerMachineRadar;
import com.hfr.inventory.container.ContainerMachineRefinery;
import com.hfr.inventory.container.ContainerMachineSiren;
import com.hfr.inventory.container.ContainerMachineUni;
import com.hfr.inventory.container.ContainerNaval;
import com.hfr.inventory.container.ContainerRBMKElement;
import com.hfr.inventory.container.ContainerRailgun;
import com.hfr.inventory.container.ContainerSawmill;
import com.hfr.inventory.container.ContainerTank;
import com.hfr.inventory.container.ContainerTemple;
import com.hfr.inventory.container.ContainerTurbine;
import com.hfr.inventory.gui.GUIAlloy;
import com.hfr.inventory.gui.GUIBattery;
import com.hfr.inventory.gui.GUIBlastFurnace;
import com.hfr.inventory.gui.GUIBox;
import com.hfr.inventory.gui.GUICoalGen;
import com.hfr.inventory.gui.GUICoalMine;
import com.hfr.inventory.gui.GUICrusher;
import com.hfr.inventory.gui.GUIDieselGen;
import com.hfr.inventory.gui.GUIDistillery;
import com.hfr.inventory.gui.GUIEFurnace;
import com.hfr.inventory.gui.GUIFactory;
import com.hfr.inventory.gui.GUIFlag;
import com.hfr.inventory.gui.GUIForceField;
import com.hfr.inventory.gui.GUIFoundry;
import com.hfr.inventory.gui.GUIGrainmill;
import com.hfr.inventory.gui.GUIHydro;
import com.hfr.inventory.gui.GUILaunchPad;
import com.hfr.inventory.gui.GUIMachineBuilder;
import com.hfr.inventory.gui.GUIMachineEMP;
import com.hfr.inventory.gui.GUIMachineMarket;
import com.hfr.inventory.gui.GUIMachineNet;
import com.hfr.inventory.gui.GUIMachineOilWell;
import com.hfr.inventory.gui.GUIMachineRadar;
import com.hfr.inventory.gui.GUIMachineRefinery;
import com.hfr.inventory.gui.GUIMachineSiren;
import com.hfr.inventory.gui.GUIMachineUni;
import com.hfr.inventory.gui.GUINaval;
import com.hfr.inventory.gui.GUIRBMKElement;
import com.hfr.inventory.gui.GUIRailgun;
import com.hfr.inventory.gui.GUISawmill;
import com.hfr.inventory.gui.GUIScreenDesignator;
import com.hfr.inventory.gui.GUIScreenSLBM;
import com.hfr.inventory.gui.GUITank;
import com.hfr.inventory.gui.GUITemple;
import com.hfr.inventory.gui.GUITurbine;
import com.hfr.inventory.gui.GuiFlagBig;
import com.hfr.items.ModItems;
import com.hfr.tileentity.clowder.*;
import com.hfr.tileentity.machine.*;
import com.hfr.tileentity.weapon.*;

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

			case ModBlocks.guiID_builder:
			{
				if(entity instanceof TileEntityMachineBuilder)
				{
					return new ContainerMachineBuilder(player.inventory, (TileEntityMachineBuilder) entity);
				}
				return null;
			}

			case ModBlocks.guiID_uni:
			{
				if(entity instanceof TileEntityMachineUni)
				{
					return new ContainerMachineUni(player.inventory, (TileEntityMachineUni) entity);
				}
				return null;
			}

			case ModBlocks.guiID_rbmk:
			{
				if(entity instanceof TileEntityRBMKElement)
				{
					return new ContainerRBMKElement(player.inventory, (TileEntityRBMKElement) entity);
				}
				return null;
			}

			case ModBlocks.guiID_emp:
			{
				if(entity instanceof TileEntityMachineEMP)
				{
					return new ContainerMachineEMP(player.inventory, (TileEntityMachineEMP) entity);
				}
				return null;
			}

			case ModBlocks.guiID_flag:
			{
				if(entity instanceof TileEntityFlag)
				{
					return new ContainerFlag(player.inventory, (TileEntityFlag) entity);
				}
				return null;
			}

			case ModBlocks.guiID_grainmill:
			{
				if(entity instanceof TileEntityMachineGrainmill)
				{
					return new ContainerGrainMill(player.inventory, (TileEntityMachineGrainmill) entity);
				}
				return null;
			}

			case ModBlocks.guiID_blastfurnace:
			{
				if(entity instanceof TileEntityMachineBlastFurnace)
				{
					return new ContainerBlastFurnace(player.inventory, (TileEntityMachineBlastFurnace) entity);
				}
				return null;
			}

			case ModBlocks.guiID_box:
			{
				if(entity instanceof TileEntityBox)
				{
					return new ContainerBox(player.inventory, (TileEntityBox) entity);
				}
				return null;
			}

			case ModBlocks.guiID_coalmine:
			{
				if(entity instanceof TileEntityMachineCoalMine)
				{
					return new ContainerCoalMine(player.inventory, (TileEntityMachineCoalMine) entity);
				}
				return null;
			}

			case ModBlocks.guiID_coalgen:
			{
				if(entity instanceof TileEntityCoalGen)
				{
					return new ContainerCoalGen(player.inventory, (TileEntityCoalGen) entity);
				}
				return null;
			}

			case ModBlocks.guiID_factory:
			{
				if(entity instanceof TileEntityMachineFactory)
				{
					return new ContainerFactory(player.inventory, (TileEntityMachineFactory) entity);
				}
				return null;
			}

			case ModBlocks.guiID_battery:
			{
				if(entity instanceof TileEntityBattery)
				{
					return new ContainerBattery(player.inventory, (TileEntityBattery) entity);
				}
				return null;
			}

			case ModBlocks.guiID_diesel:
			{
				if(entity instanceof TileEntityDieselGen)
				{
					return new ContainerDieselGen(player.inventory, (TileEntityDieselGen) entity);
				}
				return null;
			}

			case ModBlocks.guiID_turbine:
			{
				if(entity instanceof TileEntityMachineTurbine)
				{
					return new ContainerTurbine(player.inventory, (TileEntityMachineTurbine) entity);
				}
				return null;
			}

			case ModBlocks.guiID_temple:
			{
				if(entity instanceof TileEntityMachineTemple)
				{
					return new ContainerTemple(player.inventory, (TileEntityMachineTemple) entity);
				}
				return null;
			}

			case ModBlocks.guiID_alloy:
			{
				if(entity instanceof TileEntityMachineAlloy)
				{
					return new ContainerAlloy(player.inventory, (TileEntityMachineAlloy) entity);
				}
				return null;
			}

			case ModBlocks.guiID_sawmill:
			{
				if(entity instanceof TileEntityMachineSawmill)
				{
					return new ContainerSawmill(player.inventory, (TileEntityMachineSawmill) entity);
				}
				return null;
			}

			case ModBlocks.guiID_crusher:
			{
				if(entity instanceof TileEntityMachineCrusher)
				{
					return new ContainerCrusher(player.inventory, (TileEntityMachineCrusher) entity);
				}
				return null;
			}

			case ModBlocks.guiID_efurnace:
			{
				if(entity instanceof TileEntityMachineEFurnace)
				{
					return new ContainerEFurnace(player.inventory, (TileEntityMachineEFurnace) entity);
				}
				return null;
			}

			case ModBlocks.guiID_distillery:
			{
				if(entity instanceof TileEntityMachineDistillery)
				{
					return new ContainerDistillery(player.inventory, (TileEntityMachineDistillery) entity);
				}
				return null;
			}

			case ModBlocks.guiID_chest:
			{
				if(entity instanceof TileEntityOfficerChest)
				{
					return new ContainerChest(player.inventory, (TileEntityOfficerChest) entity);
				}
				return null;
			}

			case ModBlocks.guiID_flag_big:
			{
				if(entity instanceof TileEntityFlagBig)
				{
					return new ContainerFlagBig(player.inventory, (TileEntityFlagBig) entity);
				}
				return null;
			}

			case ModBlocks.guiID_foundry:
			{
				if(entity instanceof TileEntityFoundry)
				{
					return new ContainerFoundry(player.inventory, (TileEntityFoundry) entity);
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
				
				case ModBlocks.guiID_builder:
				{
					if(entity instanceof TileEntityMachineBuilder)
					{
						return new GUIMachineBuilder(player.inventory, (TileEntityMachineBuilder) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_uni:
				{
					if(entity instanceof TileEntityMachineUni)
					{
						return new GUIMachineUni(player.inventory, (TileEntityMachineUni) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_rbmk:
				{
					if(entity instanceof TileEntityRBMKElement)
					{
						return new GUIRBMKElement(player.inventory, (TileEntityRBMKElement) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_emp:
				{
					if(entity instanceof TileEntityMachineEMP)
					{
						return new GUIMachineEMP(player.inventory, (TileEntityMachineEMP) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_flag:
				{
					if(entity instanceof TileEntityFlag)
					{
						return new GUIFlag(player.inventory, (TileEntityFlag) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_grainmill:
				{
					if(entity instanceof TileEntityMachineGrainmill)
					{
						return new GUIGrainmill(player.inventory, (TileEntityMachineGrainmill) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_blastfurnace:
				{
					if(entity instanceof TileEntityMachineBlastFurnace)
					{
						return new GUIBlastFurnace(player.inventory, (TileEntityMachineBlastFurnace) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_box:
				{
					if(entity instanceof TileEntityBox)
					{
						return new GUIBox(player.inventory, (TileEntityBox) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_coalmine:
				{
					if(entity instanceof TileEntityMachineCoalMine)
					{
						return new GUICoalMine(player.inventory, (TileEntityMachineCoalMine) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_coalgen:
				{
					if(entity instanceof TileEntityCoalGen)
					{
						return new GUICoalGen(player.inventory, (TileEntityCoalGen) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_factory:
				{
					if(entity instanceof TileEntityMachineFactory)
					{
						return new GUIFactory(player.inventory, (TileEntityMachineFactory) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_battery:
				{
					if(entity instanceof TileEntityBattery)
					{
						return new GUIBattery(player.inventory, (TileEntityBattery) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_diesel:
				{
					if(entity instanceof TileEntityDieselGen)
					{
						return new GUIDieselGen(player.inventory, (TileEntityDieselGen) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_turbine:
				{
					if(entity instanceof TileEntityMachineTurbine)
					{
						return new GUITurbine(player.inventory, (TileEntityMachineTurbine) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_temple:
				{
					if(entity instanceof TileEntityMachineTemple)
					{
						return new GUITemple(player.inventory, (TileEntityMachineTemple) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_alloy:
				{
					if(entity instanceof TileEntityMachineAlloy)
					{
						return new GUIAlloy(player.inventory, (TileEntityMachineAlloy) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_sawmill:
				{
					if(entity instanceof TileEntityMachineSawmill)
					{
						return new GUISawmill(player.inventory, (TileEntityMachineSawmill) entity);
					}
					return null;
				}

				case ModBlocks.guiID_chest:
				{
					if(entity instanceof TileEntityOfficerChest)
					{
						return new GuiChest(player.inventory, (TileEntityOfficerChest) entity);
					}
					return null;
				}

				case ModBlocks.guiID_flag_big:
				{
					if(entity instanceof TileEntityFlagBig)
					{
						return new GuiFlagBig(player.inventory, (TileEntityFlagBig) entity);
					}
					return null;
				}
				
				/*case ModBlocks.guiID_crusher:
				{
					if(entity instanceof TileEntityMachineCrusher)
					{
						return new GUICrusher(player.inventory, (TileEntityMachineCrusher) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_distillery:
				{
					if(entity instanceof TileEntityMachineDistillery)
					{
						return new GUIDistillery(player.inventory, (TileEntityMachineDistillery) entity);
					}
					return null;
				}*/
				
				case ModBlocks.guiID_market:
				{
					if(entity instanceof TileEntityMarket) {
						return new GUIMachineMarket(player, (TileEntityMarket) entity);
					}
				}
				
				case ModBlocks.guiID_efurnace:
				{
					if(entity instanceof TileEntityMachineEFurnace)
					{
						return new GUIEFurnace(player.inventory, (TileEntityMachineEFurnace) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_foundry:
				{
					if(entity instanceof TileEntityFoundry) {
						return new GUIFoundry(player.inventory, (TileEntityFoundry) entity);
					}
				}
			}
		} else {
			
			switch(ID) {
				case ModItems.guiID_desingator:
				{
					return new GUIScreenDesignator(player);
				}
				case ModItems.guiID_slbm:
				{
					return new GUIScreenSLBM(player);
				}
			}
		}
		return null;
	}

}
