package com.hfr.packet;

import com.hfr.tileentity.TileEntityLaunchPad;
import com.hfr.tileentity.TileEntityMachineDerrick;
import com.hfr.tileentity.TileEntityMachineRadar;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class AuxGaugePacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int meta;

	public AuxGaugePacket()
	{
		
	}

	public AuxGaugePacket(int x, int y, int z, int value, int meta)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.meta = meta;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readInt();
		meta = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(value);
		buf.writeInt(meta);
	}

	public static class Handler implements IMessageHandler<AuxGaugePacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AuxGaugePacket m, MessageContext ctx) {
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
				
				if (te != null && te instanceof TileEntityMachineDerrick) {
						
					TileEntityMachineDerrick gen = (TileEntityMachineDerrick) te;

					if(m.meta == 0)
						gen.oil = m.value;
					if(m.meta == 1)
						gen.gas = m.value;
				}
				
			} catch (Exception x) { }
			return null;
		}
	}
}
