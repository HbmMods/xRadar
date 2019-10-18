package com.hfr.packet.effect;

import com.hfr.handler.SLBMHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SLBMOfferPacket implements IMessage {

	int type;
	int warhead;

	public SLBMOfferPacket()
	{
		
	}

	public SLBMOfferPacket(int type, int warhead)
	{
		this.type = type;
		this.warhead = warhead;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
		warhead = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		buf.writeInt(warhead);
	}

	public static class Handler implements IMessageHandler<SLBMOfferPacket, IMessage> {
		
		@Override
		public IMessage onMessage(SLBMOfferPacket m, MessageContext ctx) {
			
    		SLBMHandler.flight = m.type;
    		SLBMHandler.warhead = m.warhead;
			
			return null;
		}
	}
}
