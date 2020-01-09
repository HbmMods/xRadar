package com.hfr.packet.tile;

import com.hfr.tileentity.machine.TileEntityMachineBuilder;
import com.hfr.tileentity.machine.TileEntityMachineBuilder.SchemOffer;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class SchemOfferPacket implements IMessage {

	String[] offers;

	public SchemOfferPacket() {

	}

	public SchemOfferPacket(String[] offers) {
		this.offers = offers;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int count = buf.readInt();
		offers = new String[count];
		
		for(int i = 0; i < count; i++)
			offers[i] = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(offers.length);
		
		for(int i = 0; i < offers.length; i++)
			ByteBufUtils.writeUTF8String(buf, offers[i]);
	}

	public static class Handler implements IMessageHandler<SchemOfferPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SchemOfferPacket m, MessageContext ctx) {

			try {

				TileEntityMachineBuilder.offers.clear();
				
				for(String s : m.offers) {
					
					String name = s.split("_")[0];
					int value = Integer.parseInt(s.split("_")[1]);
					
					TileEntityMachineBuilder.offers.add(new SchemOffer(name, value));
				}
				
			} catch (Exception x) {
			}
			return null;
		}
	}
}
