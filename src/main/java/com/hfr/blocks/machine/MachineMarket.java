package com.hfr.blocks.machine;

import com.hfr.blocks.ModBlocks;
import com.hfr.data.MarketData;
import com.hfr.lib.RefStrings;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.tile.OfferPacket;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineMarket extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":market_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":market_bottom");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":market_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	public MachineMarket(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			
			NBTTagCompound nbt = new NBTTagCompound();
			MarketData data = MarketData.getData(world);
			data.writeToNBT(nbt);
			PacketDispatcher.wrapper.sendTo(new OfferPacket(nbt), (EntityPlayerMP)player);
			return true;
			
		} else if(!player.isSneaking()) {
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_market, world, 0, 0, 0);
			return true;
			
		} else {
			return false;
		}
	}
}
