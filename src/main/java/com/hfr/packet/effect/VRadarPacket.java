package com.hfr.packet.effect;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class VRadarPacket implements IMessage {

	//floats: additional accuracy for only half the price!
	float x;
	float y;
	float z;
	float posX;
	float posZ;

	public VRadarPacket() { }

	public VRadarPacket(float x, float y, float z, float posX, float posZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.posX = posX;
		this.posZ = posZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
		posX = buf.readFloat();
		posZ = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(posX);
		buf.writeFloat(posZ);
	}

	public static class Handler implements IMessageHandler<VRadarPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(VRadarPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.addBlip(m.x, m.y, m.z, m.posX, m.posZ, 0);
			
			return null;
		}
	}
}
