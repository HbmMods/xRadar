package com.hfr.packet.effect;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class VRadarDestructorPacket implements IMessage {
	
	boolean sufficient;

	public VRadarDestructorPacket() { }

	public VRadarDestructorPacket(boolean sufficient) {
		this.sufficient = sufficient;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		sufficient = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(sufficient);
	}

	public static class Handler implements IMessageHandler<VRadarDestructorPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(VRadarDestructorPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.clearBlips(m.sufficient, true, 0, 250);
			
			return null;
		}
	}
}
