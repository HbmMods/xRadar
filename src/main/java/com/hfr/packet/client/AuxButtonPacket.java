package com.hfr.packet.client;

import com.hfr.clowder.Clowder;
import com.hfr.data.StockData;
import com.hfr.main.MainRegistry;
import com.hfr.packet.PacketDispatcher;
import com.hfr.packet.effect.ParticleControlPacket;
import com.hfr.packet.tile.AuxGaugePacket;
import com.hfr.packet.tile.RailgunCallbackPacket;
import com.hfr.packet.tile.RailgunFirePacket;
import com.hfr.packet.tile.SchematicPreviewPacket;
import com.hfr.potion.HFRPotion;
import com.hfr.schematic.Schematic;
import com.hfr.tileentity.clowder.TileEntityFlag;
import com.hfr.tileentity.machine.TileEntityForceField;
import com.hfr.tileentity.machine.TileEntityMachineBuilder;
import com.hfr.tileentity.machine.TileEntityMachineEMP;
import com.hfr.tileentity.machine.TileEntityMachineMarket;
import com.hfr.tileentity.machine.TileEntityMachineRadar;
import com.hfr.tileentity.weapon.TileEntityNaval;
import com.hfr.tileentity.weapon.TileEntityRailgun;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;

public class AuxButtonPacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int id;

	public AuxButtonPacket()
	{
		
	}

	public AuxButtonPacket(int x, int y, int z, int value, int id)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readInt();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(value);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<AuxButtonPacket, IMessage> {

		@Override
		public IMessage onMessage(AuxButtonPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;

			try {
				TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);

				if (te instanceof TileEntityForceField) {
					TileEntityForceField field = (TileEntityForceField)te;
					field.isOn = !field.isOn;
				}
				if (te instanceof TileEntityMachineRadar) {
					TileEntityMachineRadar field = (TileEntityMachineRadar)te;
					field.mode++;
					
					if(field.mode > 3)
						field.mode -= 4;
				}
				
				if (te instanceof TileEntityRailgun) {
					TileEntityRailgun gun = (TileEntityRailgun)te;
					
					if(m.id == 0) {
						if(gun.setAngles(p.isPotionActive(HFRPotion.emp))) {
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonYes", 1.0F, 1.0F);
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.railgunOrientation", 1.0F, 1.0F);
							PacketDispatcher.wrapper.sendToAll(new RailgunCallbackPacket(m.x, m.y, m.z, gun.pitch, gun.yaw));
						} else {
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonNo", 1.0F, 1.0F);
						}
					}
					
					if(m.id == 1) {
						if(gun.canFire()) {
							gun.fireDelay = gun.cooldownDurationTicks;
							PacketDispatcher.wrapper.sendToAll(new RailgunFirePacket(m.x, m.y, m.z));
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonYes", 1.0F, 1.0F);
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.railgunCharge", 10.0F, 1.0F);
						} else {
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonNo", 1.0F, 1.0F);
						}
					}
				}
				
				if (te instanceof TileEntityNaval) {
					TileEntityNaval gun = (TileEntityNaval)te;
					
					if(m.id == 0) {
						if(gun.setAngles(p.isPotionActive(HFRPotion.emp))) {
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonYes", 1.0F, 1.0F);
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.railgunOrientation", 1.0F, 1.0F);
							PacketDispatcher.wrapper.sendToAll(new RailgunCallbackPacket(m.x, m.y, m.z, gun.pitch, gun.yaw));
						} else {
							p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonNo", 1.0F, 1.0F);
						}
					}
					
					if(m.id == 1) {
						if(gun.canFire()) {
							
							Vec3 vec = Vec3.createVectorHelper(6, 0, 0);
							vec.rotateAroundZ((float) (gun.pitch * Math.PI / 180D));
							vec.rotateAroundY((float) (gun.yaw * Math.PI / 180D));

							double fX = gun.xCoord + 0.5 + vec.xCoord;
							double fY = gun.yCoord + 1 + vec.yCoord;
							double fZ = gun.zCoord + 0.5 + vec.zCoord;
							
							PacketDispatcher.wrapper.sendToAll(new ParticleControlPacket(fX, fY, fZ, 4));
						}
						
						gun.tryFire();
					}
				}
				
				if (te instanceof TileEntityMachineMarket) {
					TileEntityMachineMarket market = (TileEntityMachineMarket)te;
					
					StockData data = StockData.getData(market.getWorldObj());

					if(m.id == 0)
						market.buyStock(data.getByInt(m.value), p.getDisplayName());
					if(m.id == 1)
						market.sellStock(data.getByInt(m.value), p.getDisplayName());
				}
				
				if (te instanceof TileEntityMachineBuilder) {
					TileEntityMachineBuilder builder = (TileEntityMachineBuilder)te;
					
					if(m.id == 0) {
						
						if(builder.preview == null) {
							builder.preview = MainRegistry.schems.get(m.value);
							PacketDispatcher.wrapper.sendToAll(new SchematicPreviewPacket(m.x, m.y, m.z, MainRegistry.schems.get(m.value)));
						} else {
							builder.preview = null;
							PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(m.x, m.y, m.z, 0, 0));
						}
					}
					
					if(m.id == 1) {
						Schematic schem = MainRegistry.schems.get(m.value);
						builder.construct(schem);
					}
					
					if(m.id == 2) {
						Schematic schem = MainRegistry.schems.get(m.value);
						builder.deconstruct(schem);
					}
				}
				
				if (te instanceof TileEntityMachineEMP) {
					TileEntityMachineEMP emp = (TileEntityMachineEMP)te;
					
					if(m.id == 0) {
						emp.isOn = !emp.isOn;
					}
					
					if(m.id == 1) {
						emp.range = m.value;
					}
				}
				
				if (te instanceof TileEntityFlag) {
					TileEntityFlag flag = (TileEntityFlag)te;

					if(flag.isClaimed) {

						if(m.id == 0) {
							
							if(flag.mode != 1)
								flag.setMode(1);
							else
								flag.setMode(0);
						}

						if(m.id == 1) {
							
							if(flag.mode != 2)
								flag.setMode(2);
							else
								flag.setMode(0);
						}

						if(m.id == 2) {
							
							if(flag.mode != 3)
								flag.setMode(3);
							else
								flag.setMode(0);
						}
						
						if(flag.mode > 0)
							flag.generateClaim();
					}
					
					if(m.id == 3) {
						
						Clowder clow = Clowder.getClowderFromPlayer(p);
						
						if(clow != flag.owner && clow != null) {
							
							if(flag.bordersWilderness()) {
								flag.isClaimed = false;
							} else {
								p.worldObj.playSoundEffect(m.x, m.y, m.z, "hfr:block.buttonNo", 1.0F, 1.0F);
								p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "This claim is protected by surrounding claims."));
								p.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Capture outer claims before proceeding to this one!"));
							}
						}
					}
				}
				
			} catch (Exception x) { }
			
			return null;
		}
	}
}
