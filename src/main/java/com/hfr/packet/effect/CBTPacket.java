package com.hfr.packet.effect;

import com.hfr.main.EventHandlerClient;
import com.hfr.main.MainRegistry;
import com.hfr.render.hud.RenderRadarScreen.Blip;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class CBTPacket implements IMessage {
	
	boolean fps;
	boolean tilt;

	public CBTPacket() { }

	public CBTPacket(boolean fps, boolean tilt) {
		
		this.fps = fps;
		this.tilt = tilt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		fps = buf.readBoolean();
		tilt = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeBoolean(fps);
		buf.writeBoolean(tilt);
	}

	public static class Handler implements IMessageHandler<CBTPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(CBTPacket m, MessageContext ctx) {
			
			EventHandlerClient.fps = m.fps;
			EventHandlerClient.tilt = m.tilt;
			
			return null;
		}
	}
}
