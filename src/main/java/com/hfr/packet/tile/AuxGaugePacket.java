package com.hfr.packet.tile;

import com.hfr.main.MainRegistry;
import com.hfr.tileentity.machine.*;
import com.hfr.tileentity.weapon.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

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
				
				if (te != null && te instanceof TileEntityMachineRefinery) {
						
					TileEntityMachineRefinery gen = (TileEntityMachineRefinery) te;

					if(m.meta == 0)
						gen.oil = m.value;
					if(m.meta == 1)
						gen.heavy = m.value;
					if(m.meta == 2)
						gen.naph = m.value;
					if(m.meta == 3)
						gen.light = m.value;
					if(m.meta == 4)
						gen.petro = m.value;
				}
				
				if (te != null && te instanceof TileEntityTank) {
						
					TileEntityTank gen = (TileEntityTank) te;

					if(m.meta == 0)
						gen.fill = m.value;
					if(m.meta == 1)
						gen.type = m.value;
				}
				
				if (te != null && te instanceof TileEntityRailgun) {
						
					TileEntityRailgun gen = (TileEntityRailgun) te;
					
					if(m.meta == 0) {
						Vec3 vec = Vec3.createVectorHelper(5.5, 0, 0);
						vec.rotateAroundZ((float) (gen.pitch * Math.PI / 180D));
						vec.rotateAroundY((float) (gen.yaw * Math.PI / 180D));
	
						double fX = gen.xCoord + 0.5 + vec.xCoord;
						double fY = gen.yCoord + 1 + vec.yCoord;
						double fZ = gen.zCoord + 0.5 + vec.zCoord;
						
						MainRegistry.proxy.spawnSFX(gen.getWorldObj(), fX, fY, fZ, 0, vec.normalize());
					}
				}
				
				if (te != null && te instanceof TileEntityNaval) {
						
					TileEntityNaval gen = (TileEntityNaval) te;

					if(m.meta == 0)
						gen.powder = m.value;
				}
				
				if (te != null && te instanceof TileEntityMachineNet) {
						
					TileEntityMachineNet gen = (TileEntityMachineNet) te;

					gen.isJammed = m.value == 1;
				}
				
				if (te != null && te instanceof TileEntityMachineBuilder) {
						
					TileEntityMachineBuilder gen = (TileEntityMachineBuilder) te;

					gen.preview = null;
				}
				
				if(te instanceof TileEntityMachineBase) {
					((TileEntityMachineBase)te).processGauge(m.value, m.meta);
				}
				
			} catch (Exception x) { }
			return null;
		}
	}
}
