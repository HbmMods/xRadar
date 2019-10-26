package com.hfr.packet.effect;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderFlag;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ClowderFlagPacket implements IMessage {

	int flag;
	int color;
	String name;

	public ClowderFlagPacket()
	{
		
	}

	public ClowderFlagPacket(Clowder clowder) {
		this.flag = clowder.flag.ordinal();
		this.color = clowder.color;
		this.name = clowder.name;
	}

	public ClowderFlagPacket(ClowderFlag flag, int color, String name) {
		this.flag = flag.ordinal();
		this.color = color;
		this.name = name;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		flag = buf.readInt();
		color = buf.readInt();
		name = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(flag);
		buf.writeInt(color);
		ByteBufUtils.writeUTF8String(buf, name);
	}

	public static class Handler implements IMessageHandler<ClowderFlagPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ClowderFlagPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.updateFlag(ClowderFlag.values()[m.flag], m.color, m.name);
			
			return null;
		}
	}
}
