package com.hfr.packet.client;

import com.hfr.items.ModItems;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ReseatRequestPacket implements IMessage {

	public ReseatRequestPacket() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	public static class Handler implements IMessageHandler<ReseatRequestPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ReseatRequestPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			if(p == null)
				return null;
			
			Entity ridden = p.ridingEntity;
			
			if(ridden == null)
				return null;
			
			p.dismountEntity(ridden);
			p.mountEntity(ridden);
			
			return null;
		}
	}
}
