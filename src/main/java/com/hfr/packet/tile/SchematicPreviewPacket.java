package com.hfr.packet.tile;

import com.hfr.schematic.Schematic;
import com.hfr.schematic.SchematicPayload;
import com.hfr.tileentity.machine.TileEntityMachineBuilder;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class SchematicPreviewPacket implements IMessage {

	int x;
	int y;
	int z;
	SchematicPayload payload;

	public SchematicPreviewPacket() { }

	public SchematicPreviewPacket(int x, int y, int z, Schematic schem) {
		this.x = x;
		this.y = y;
		this.z = z;
		payload = new SchematicPayload(schem);
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		payload = new SchematicPayload(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		payload.toBytes(buf);
	}

	public static class Handler implements IMessageHandler<SchematicPreviewPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SchematicPreviewPacket m, MessageContext ctx) {
			
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityMachineBuilder) {
						
					TileEntityMachineBuilder builder = (TileEntityMachineBuilder)te;
					builder.preview = m.payload.deserialize();
				}
				
			} catch(Exception e) { }
			
			return null;
		}
	}
}
