package com.hfr.packet.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.hfr.blocks.machine.MachineMarket;
import com.hfr.data.MarketData;
import com.hfr.inventory.gui.GUIMachineMarket;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class OfferPacket implements IMessage {

	String name;
	PacketBuffer buffer;

	public OfferPacket() { }

	public OfferPacket(String name, NBTTagCompound nbt) {
		
		this.name = name;
		this.buffer = new PacketBuffer(Unpooled.buffer());
		
		try {
			buffer.writeNBTTagCompoundToBuffer(nbt);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		this.name = ByteBufUtils.readUTF8String(buf);
		
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		ByteBufUtils.writeUTF8String(buf, name);

		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<OfferPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(OfferPacket m, MessageContext ctx) {
			
			try {
				
				MarketData data = MarketData.getData(Minecraft.getMinecraft().theWorld);
				
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				data.offers.clear();
				data.readMarketFromPacket(nbt);
				MachineMarket.name = m.name;
				List<ItemStack[]> offers = data.offers.get(m.name);
				
				if(offers == null)
					offers = new ArrayList();
				
				GUIMachineMarket.offers = offers;
				
				/*System.out.println("Offers: " + data.offers.size());
				
				for(Entry<String, List<ItemStack[]>> entry : data.offers.entrySet()) {
					
					System.out.println(entry.getKey() + ": " + entry.getValue().size());
					
					for(ItemStack[] offer : entry.getValue()) {
						
						System.out.println(" Offer:");
						
						for(ItemStack stack : offer) {
							
							if(stack == null)
								System.out.println("  NULL");
							else
								System.out.println("  " + stack.getDisplayName());
						}
					}
				}*/
				
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			return null;
		}
	}
}
