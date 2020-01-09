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

public class TESRadarPacket implements IMessage {

	RadarEntry[] blips;
	int x;
	int y;
	int z;
	int mode;

	public TESRadarPacket() { }

	public TESRadarPacket(RadarEntry[] blips, int x, int y, int z, int mode) {
		
		if(blips == null)
			blips = new RadarEntry[0];
		
		this.blips = blips;
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		mode = buf.readInt();
		
		blips = new RadarEntry[buf.readInt()];
		
		for(int i = 0; i < blips.length; i++) {
			blips[i] = new RadarEntry(buf.readInt(), buf.readInt(), buf.readInt(), ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(mode);
		
		buf.writeInt(blips.length);
		
		for(int i = 0; i < blips.length; i++) {
			buf.writeInt(blips[i].posX);
			buf.writeInt(blips[i].posY);
			buf.writeInt(blips[i].posZ);
			ByteBufUtils.writeUTF8String(buf, blips[i].name);
		}
	}

	public static class Handler implements IMessageHandler<TESRadarPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TESRadarPacket m, MessageContext ctx) {

			TileEntityMachineRadar te = (TileEntityMachineRadar)Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
			
			try {
				te.nearbyMissiles.clear();
				te.mode = m.mode;
				
				if(m.blips != null) {
					for(RadarEntry blip : m.blips) {
						te.nearbyMissiles.add(new RadarEntry(blip.posX, blip.posY, blip.posZ, blip.name));
					}
				}
				
			} catch(Exception ex) { }
			
			return null;
		}
	}
}
