package com.hfr.blocks;

import com.hfr.handler.FluidHandler;
import com.hfr.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockSteam extends Block {

	protected BlockSteam(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {

		if(this == ModBlocks.steam) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":steam_still");
			FluidHandler.STEAM.setStillIcon(reg.registerIcon(RefStrings.MODID + ":steam_still"));
			FluidHandler.STEAM.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":steam_flow"));
		}
		if(this == ModBlocks.oil) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":oil_still");
			FluidHandler.OIL.setStillIcon(reg.registerIcon(RefStrings.MODID + ":oil_still"));
			FluidHandler.OIL.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":oil_flow"));
		}
		if(this == ModBlocks.gas) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":gas_still");
			FluidHandler.GAS.setStillIcon(reg.registerIcon(RefStrings.MODID + ":gas_still"));
			FluidHandler.GAS.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":gas_flow"));
		}
		if(this == ModBlocks.petroil) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":petroil_still");
			FluidHandler.PETROIL.setStillIcon(reg.registerIcon(RefStrings.MODID + ":petroil_still"));
			FluidHandler.PETROIL.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":petroil_flow"));
		}
		if(this == ModBlocks.diesel) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":diesel_still");
			FluidHandler.DIESEL.setStillIcon(reg.registerIcon(RefStrings.MODID + ":diesel_still"));
			FluidHandler.DIESEL.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":diesel_flow"));
		}
		if(this == ModBlocks.kerosene) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":kerosene_still");
			FluidHandler.KEROSENE.setStillIcon(reg.registerIcon(RefStrings.MODID + ":kerosene_still"));
			FluidHandler.KEROSENE.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":kerosene_flow"));
		}
		if(this == ModBlocks.petroleum) {
			this.blockIcon = reg.registerIcon(RefStrings.MODID + ":petroleum_still");
			FluidHandler.PETROLEUM.setStillIcon(reg.registerIcon(RefStrings.MODID + ":petroleum_still"));
			FluidHandler.PETROLEUM.setFlowingIcon(reg.registerIcon(RefStrings.MODID + ":petroleum_flow"));
		}
	}

}
