package com.hfr.packet.effect;

import java.util.HashMap;

import com.hfr.main.EventHandlerClient;
import com.hfr.main.MainRegistry;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerDataPacket implements IMessage {
	
	public static HashMap<String, Integer> vals = new HashMap();
	
	String key;
	int value;

	public PlayerDataPacket() { }

	public PlayerDataPacket(String key, int value) {
		
		this.key = key;
		this.value = value;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		key = ByteBufUtils.readUTF8String(buf);
		value = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		ByteBufUtils.writeUTF8String(buf, key);
		buf.writeInt(value);
	}

	public static class Handler implements IMessageHandler<PlayerDataPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PlayerDataPacket m, MessageContext ctx) {
			
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			vals.put(m.key, m.value);
			
			return null;
		}
	}
}
