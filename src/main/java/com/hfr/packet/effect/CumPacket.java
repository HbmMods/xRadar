package com.hfr.packet.effect;

import com.hfr.main.EventHandlerClient;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class CumPacket implements IMessage {
	
	String url;

	public CumPacket() { }

	public CumPacket(String url) {
		
		this.url = url;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		url = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		ByteBufUtils.writeUTF8String(buf, url);
	}

	public static class Handler implements IMessageHandler<CumPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(CumPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.openURL(m.url);
			
			return null;
		}
	}
}
