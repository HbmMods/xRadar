package com.hfr.packet.effect;

import com.hfr.clowder.Clowder;
import com.hfr.clowder.ClowderFlag;
import com.hfr.clowder.ClowderTerritory;
import com.hfr.clowder.ClowderTerritory.Zone;
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

	public ClowderFlagPacket(String special) {

		if(special.equals(Zone.WILDERNESS.toString())) {
			this.flag = -1;
			this.color = ClowderTerritory.WILDERNESS_COLOR;
			this.name = "Wilderness";
		}
		if(special.equals(Zone.SAFEZONE.toString())) {
			this.flag = -2;
			this.color = ClowderTerritory.SAFEZONE_COLOR;
			this.name = "Safe Zone";
		}
		if(special.equals(Zone.WARZONE.toString())) {
			this.flag = -3;
			this.color = ClowderTerritory.WARZONE_COLOR;
			this.name = "War Zone";
		}
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
			
			if(m.flag > 0) {
				ClowderFlag flag = ClowderFlag.values()[m.flag];
				MainRegistry.proxy.updateFlag(flag.getFlag(), flag.getFlagOverlay(), m.color, m.name);
			} else {
				MainRegistry.proxy.updateFlag(null, m.flag == -3 ? ClowderFlag.WARZONE : m.flag == -2 ? ClowderFlag.SAFEZONE : ClowderFlag.WILDERNESS, m.color, m.name);
			}
			
			return null;
		}
	}
}
