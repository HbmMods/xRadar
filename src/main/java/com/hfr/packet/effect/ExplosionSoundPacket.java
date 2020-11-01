package com.hfr.packet.effect;

import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ExplosionSoundPacket implements IMessage {

	int x;
	int y;
	int z;
	float pow;

	public ExplosionSoundPacket()
	{
		
	}

	public ExplosionSoundPacket(int x, int y, int z, float pow)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.pow = pow;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		pow = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(pow);
	}

	public static class Handler implements IMessageHandler<ExplosionSoundPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ExplosionSoundPacket m, MessageContext ctx) {
			
			MainRegistry.proxy.spawnEFX(m.x, m.y, m.z, m.pow);
			
			return null;
		}
	}
}
