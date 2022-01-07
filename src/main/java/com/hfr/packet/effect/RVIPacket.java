package com.hfr.packet.effect;

import java.util.Arrays;

import com.flansmod.common.driveables.EntityDriveable;
import com.hfr.main.MainRegistry;
import com.hfr.rvi.RVICommon.Indicator;
import com.hfr.rvi.RVICommon.RVIType;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class RVIPacket implements IMessage {

	Indicator[] indicators;

	public RVIPacket() { }

	public RVIPacket(Indicator[] indicators) {
		
		if(indicators == null)
			indicators = new Indicator[0];
		
		this.indicators = indicators;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		indicators = new Indicator[buf.readInt()];
		
		for(int i = 0; i < indicators.length; i++) {
			// before fuckery  indicators[i] = new Indicator(buf.readDouble(), buf.readDouble(), buf.readDouble(), RVIType.values()[buf.readInt()]);
			indicators[i] = new Indicator(buf.readDouble(), buf.readDouble(), buf.readDouble(), RVIType.values()[buf.readInt()], null );
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(indicators.length);
		
		for(int i = 0; i < indicators.length; i++) {
			buf.writeDouble(indicators[i].x);
			buf.writeDouble(indicators[i].y);
			buf.writeDouble(indicators[i].z);
			buf.writeInt(indicators[i].type.ordinal());
		}
	}

	public static class Handler implements IMessageHandler<RVIPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RVIPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.addRVIs(Arrays.asList(m.indicators));
			
			return null;
		}
	}
}
