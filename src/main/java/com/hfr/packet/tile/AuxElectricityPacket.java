package com.hfr.packet.tile;

import com.hfr.tileentity.machine.TileEntityHydro;
import com.hfr.tileentity.machine.TileEntityMachineDerrick;
import com.hfr.tileentity.machine.TileEntityMachineEMP;
import com.hfr.tileentity.machine.TileEntityMachineRadar;
import com.hfr.tileentity.machine.TileEntityMachineRefinery;
import com.hfr.tileentity.weapon.TileEntityLaunchPad;
import com.hfr.tileentity.weapon.TileEntityRailgun;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class AuxElectricityPacket implements IMessage {

	int x;
	int y;
	int z;
	int charge;

	public AuxElectricityPacket()
	{
		
	}

	public AuxElectricityPacket(int x, int y, int z, int charge)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.charge = charge;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		charge = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(charge);
	}

	public static class Handler implements IMessageHandler<AuxElectricityPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AuxElectricityPacket m, MessageContext ctx) {
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityMachineRadar) {
						
					TileEntityMachineRadar gen = (TileEntityMachineRadar) te;
					gen.storage.setEnergyStored(m.charge);
				}
				if (te != null && te instanceof TileEntityLaunchPad) {
						
					TileEntityLaunchPad gen = (TileEntityLaunchPad) te;
					gen.storage.setEnergyStored(m.charge);
				}
				if (te != null && te instanceof TileEntityMachineDerrick) {
						
					TileEntityMachineDerrick gen = (TileEntityMachineDerrick) te;
					gen.storage.setEnergyStored(m.charge);
				}
				if (te != null && te instanceof TileEntityMachineRefinery) {
						
					TileEntityMachineRefinery gen = (TileEntityMachineRefinery) te;
					gen.storage.setEnergyStored(m.charge);
				}
				if (te != null && te instanceof TileEntityRailgun) {
						
					TileEntityRailgun gen = (TileEntityRailgun) te;
					gen.storage.setEnergyStored(m.charge);
				}
				if (te != null && te instanceof TileEntityHydro) {
						
					TileEntityHydro gen = (TileEntityHydro) te;
					gen.power = m.charge;
				}
				if (te != null && te instanceof TileEntityMachineEMP) {
						
					TileEntityMachineEMP gen = (TileEntityMachineEMP) te;
					gen.storage.setEnergyStored(m.charge);
				}
			} catch (Exception x) { }
			return null;
		}
	}
}
