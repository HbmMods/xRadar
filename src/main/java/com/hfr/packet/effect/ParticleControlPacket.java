package com.hfr.packet.effect;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class ParticleControlPacket implements IMessage {

	double x;
	double y;
	double z;
	int type;

	public ParticleControlPacket()
	{
		
	}

	public ParticleControlPacket(double x, double y, double z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<ParticleControlPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ParticleControlPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.howDoIUseTheZOMG(Minecraft.getMinecraft().theWorld, m.x, m.y, m.z, m.type);
			
			return null;
		}
	}
}
