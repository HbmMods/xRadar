package com.hfr.packet.tile;

import com.hfr.data.StockData.Stock;
import com.hfr.tileentity.machine.TileEntityDisplay;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class StockDisplayPacket implements IMessage {

	int x;
	int y;
	int z;
	String name;
	String shortname;
	float values[];
	int index;

	public StockDisplayPacket() { }

	public StockDisplayPacket(int x, int y, int z, String name, String shortname, float values[], int index) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
		this.shortname = shortname;
		this.values = values;
		this.index = index;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();

		name = ByteBufUtils.readUTF8String(buf);
		shortname = ByteBufUtils.readUTF8String(buf);
		
		values = new float[15];
		
		for(int i = 0; i < 15; i++)
			values[i] = buf.readFloat();
		
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {

		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);

		ByteBufUtils.writeUTF8String(buf, name);
		ByteBufUtils.writeUTF8String(buf, shortname);
		
		for(int i = 0; i < 15; i++)
			buf.writeFloat(values[i]);
		
		buf.writeInt(index);
	}

	public static class Handler implements IMessageHandler<StockDisplayPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(StockDisplayPacket m, MessageContext ctx) {
			
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityDisplay) {
						
					TileEntityDisplay display = (TileEntityDisplay)te;
					display.stock = new Stock(m.name, m.shortname, m.values, 0);
				}
			} catch(Exception e) { }
			
			return null;
		}
	}
}
