package com.hfr.packet.tile;

import com.hfr.data.StockData.Stock;
import com.hfr.tileentity.machine.TileEntityMachineMarket;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class StockPacket implements IMessage {
	
	String name;
	String shortname;
	float values[];
	int shares;
	int index;

	public StockPacket() { }

	public StockPacket(String name, String shortname, float values[], int shares, int index) {
		this.name = name;
		this.shortname = shortname;
		this.values = values;
		this.shares = shares;
		this.index = index;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		name = ByteBufUtils.readUTF8String(buf);
		shortname = ByteBufUtils.readUTF8String(buf);
		
		values = new float[15];
		
		for(int i = 0; i < 15; i++)
			values[i] = buf.readFloat();
		
		shares = buf.readInt();
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		ByteBufUtils.writeUTF8String(buf, name);
		ByteBufUtils.writeUTF8String(buf, shortname);
		
		for(int i = 0; i < 15; i++)
			buf.writeFloat(values[i]);
		
		buf.writeInt(shares);
		buf.writeInt(index);
	}

	public static class Handler implements IMessageHandler<StockPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(StockPacket m, MessageContext ctx) {
			
			while(TileEntityMachineMarket.stocks.size() <= m.index)
				TileEntityMachineMarket.stocks.add(null);
			
			TileEntityMachineMarket.stocks.set(m.index, new Stock(m.name, m.shortname, m.values, m.shares));
			
			return null;
		}
	}
}
