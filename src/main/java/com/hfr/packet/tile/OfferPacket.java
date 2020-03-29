package com.hfr.packet.tile;

import java.io.IOException;

import com.hfr.data.MarketData;
import com.hfr.inventory.gui.GUIMachineMarket;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class OfferPacket implements IMessage {

	PacketBuffer buffer;

	public OfferPacket() { }

	public OfferPacket(NBTTagCompound nbt) {
		
		this.buffer = new PacketBuffer(Unpooled.buffer());
		
		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<OfferPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(OfferPacket m, MessageContext ctx) {
			
			try {
				
				MarketData data = MarketData.getData(Minecraft.getMinecraft().theWorld);
				
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				data.offers.clear();
				data.readFromNBT(nbt);
				GUIMachineMarket.offers = data.offers;
				
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			return null;
		}
	}
}
