package com.hfr.packet.client;

import com.hfr.handler.SLBMHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SLBMCommandPacket implements IMessage {
	
	int xCoord;
	int zCoord;

	public SLBMCommandPacket() { }

	public SLBMCommandPacket(int xCoord, int zCoord)
	{
		this.xCoord = xCoord;
		this.zCoord = zCoord;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		xCoord = buf.readInt();
		zCoord = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xCoord);
		buf.writeInt(zCoord);
	}

	public static class Handler implements IMessageHandler<SLBMCommandPacket, IMessage> {
		
		@Override
		public IMessage onMessage(SLBMCommandPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			SLBMHandler.tryLaunch(p.worldObj, p, m.xCoord, m.zCoord);
			
			return null;
		}
	}
}
