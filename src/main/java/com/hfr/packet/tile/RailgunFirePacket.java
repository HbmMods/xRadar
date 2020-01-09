package com.hfr.packet.tile;

import com.hfr.tileentity.weapon.TileEntityRailgun;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class RailgunFirePacket implements IMessage {

	int x;
	int y;
	int z;

	public RailgunFirePacket() { }

	public RailgunFirePacket(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<RailgunFirePacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RailgunFirePacket m, MessageContext ctx) {
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityRailgun) {
						
					TileEntityRailgun gun = (TileEntityRailgun) te;
					
					gun.fireTime = System.currentTimeMillis();
				}
				
			} catch (Exception x) { }
			return null;
		}
	}
}
