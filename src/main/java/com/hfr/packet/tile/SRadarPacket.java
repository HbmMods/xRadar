package com.hfr.packet.tile;

import com.hfr.main.MainRegistry;
import com.hfr.render.hud.RenderRadarScreen.Blip;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class SRadarPacket implements IMessage {

	Blip[] blips;
	boolean sufficient;
	boolean enabled;
	int offset;
	int range;

	public SRadarPacket() { }

	public SRadarPacket(Blip[] blips, boolean sufficient, boolean enabled, int offset, int range) {
		
		if(blips == null)
			blips = new Blip[0];
		
		this.blips = blips;
		this.sufficient = sufficient;
		this.enabled = enabled;
		this.offset = offset;
		this.range = range;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		sufficient = buf.readBoolean();
		enabled = buf.readBoolean();
		offset = buf.readInt();
		range = buf.readInt();
		
		blips = new Blip[buf.readInt()];
		
		for(int i = 0; i < blips.length; i++) {
			blips[i] = new Blip(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readShort());
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(sufficient);
		buf.writeBoolean(enabled);
		buf.writeInt(offset);
		buf.writeInt(range);
		
		buf.writeInt(blips.length);
		
		for(int i = 0; i < blips.length; i++) {
			buf.writeFloat(blips[i].x);
			buf.writeFloat(blips[i].y);
			buf.writeFloat(blips[i].z);
			buf.writeFloat(blips[i].posX);
			buf.writeFloat(blips[i].posZ);
			buf.writeShort(blips[i].type);
		}
	}

	public static class Handler implements IMessageHandler<SRadarPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SRadarPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.clearBlips(m.sufficient, m.enabled, m.offset, m.range);
			
			if(m.blips != null)
				for(Blip blip : m.blips)
					MainRegistry.proxy.addBlip(blip.x, blip.y, blip.z, blip.posX, blip.posZ, blip.type);
			
			return null;
		}
	}
}
