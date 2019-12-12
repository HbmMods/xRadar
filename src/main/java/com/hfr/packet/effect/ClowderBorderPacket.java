package com.hfr.packet.effect;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class ClowderBorderPacket implements IMessage {

	int x1;
	int z1;
	int x2;
	int z2;
	int color;

	public ClowderBorderPacket()
	{
		
	}

	public ClowderBorderPacket(int x1, int z1, int x2, int z2, int color)
	{
		this.x1 = x1;
		this.z1 = z1;
		this.x2 = x2;
		this.z2 = z2;
		this.color = color;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x1 = buf.readInt();
		z1 = buf.readInt();
		x2 = buf.readInt();
		z2 = buf.readInt();
		color = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x1);
		buf.writeInt(z1);
		buf.writeInt(x2);
		buf.writeInt(z2);
		buf.writeInt(color);
	}

	public static class Handler implements IMessageHandler<ClowderBorderPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ClowderBorderPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.spawnSFX(Minecraft.getMinecraft().theWorld, 0, 0, 0, MainRegistry.proxy.SFX_BORDER, new int[] { m.x1, m.z1, m.x2, m.z2, m.color });
			
			return null;
		}
	}
}
