package com.hfr.blocks.machine;

import java.util.List;

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
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MachineMarket extends BlockContainer {

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
	
    public static String name = "";
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			
			TileEntityMarket market = (TileEntityMarket) world.getTileEntity(x, y, z);
			
			if(market == null)
				return false;
			
			NBTTagCompound nbt = new NBTTagCompound();
			MarketData data = MarketData.getData(world);
			data.writeMarketFromName(nbt, market.name);
			
			PacketDispatcher.wrapper.sendTo(new OfferPacket(market.name, nbt), (EntityPlayerMP)player);
			
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.name_tag && player.getHeldItem().hasDisplayName()) {
				market.name = player.getHeldItem().getDisplayName();
				market.markDirty();
				return true;
			}
			
			return true;
			
		} else if(!player.isSneaking()) {
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_market, world, x, y, z);
			return true;
			
		} else {
			return false;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMarket();
	}
	
	public static class TileEntityMarket extends TileEntity {
		
		public String name = "";

	    public void readFromNBT(NBTTagCompound nbt) {
	    	super.readFromNBT(nbt);
	    	name = nbt.getString("name");
	    }

	    public void writeToNBT(NBTTagCompound nbt) {
	    	super.writeToNBT(nbt);
	    	nbt.setString("name", name);
	    }
	}
}
