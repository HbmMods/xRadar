package com.hfr.packet.tile;

import com.hfr.tileentity.machine.TileEntityMachineRadar;
import com.hfr.tileentity.machine.TileEntityMachineRadar.RadarEntry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TERadarPacket implements IMessage {

	int x;
	int y;
	int z;
	int conX;
	int conY;
	int conZ;
	String d;

	public TERadarPacket() {

	}

	public TERadarPacket(int x, int y, int z, int conX, int conY, int conZ, String d) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.conX = conX;
		this.conY = conY;
		this.conZ = conZ;
		this.d = d;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		conX = buf.readInt();
		conY = buf.readInt();
		conZ = buf.readInt();
		d = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(conX);
		buf.writeInt(conY);
		buf.writeInt(conZ);
		ByteBufUtils.writeUTF8String(buf, d);
	}

	public static class Handler implements IMessageHandler<TERadarPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TERadarPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
				if (te != null && te instanceof TileEntityMachineRadar) {

					TileEntityMachineRadar radar = (TileEntityMachineRadar) te;
					radar.nearbyMissiles.add(new RadarEntry(m.conX, m.conY, m.conZ, m.d));
				}
			} catch (Exception x) {
			}
			return null;
		}
	}
}
