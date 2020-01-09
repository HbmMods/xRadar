package com.hfr.packet.tile;

import com.hfr.tileentity.machine.TileEntityMachineBuilder;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class BuilderPacket implements IMessage {

	int x;
	int y;
	int z;
	int ox;
	int oz;

	public BuilderPacket() {

	}

	public BuilderPacket(int x, int y, int z, int ox, int oz) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.ox = ox;
		this.oz = oz;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		ox = buf.readInt();
		oz = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(ox);
		buf.writeInt(oz);
	}

	public static class Handler implements IMessageHandler<BuilderPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(BuilderPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
				if (te != null && te instanceof TileEntityMachineBuilder) {

					TileEntityMachineBuilder builder = (TileEntityMachineBuilder) te;
					builder.ox = m.ox;
					builder.oz = m.oz;
				}
			} catch (Exception x) {
			}
			return null;
		}
	}
}
