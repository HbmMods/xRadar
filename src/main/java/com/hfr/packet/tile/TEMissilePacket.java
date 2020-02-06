package com.hfr.packet.tile;

import com.hfr.items.ModItems;
import com.hfr.tileentity.weapon.TileEntityLaunchPad;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TEMissilePacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEMissilePacket()
	{
		
	}

	public TEMissilePacket(int x, int y, int z, ItemStack stack)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = 0;
		if(stack != null) {
			
			if(stack.getItem() == ModItems.missile_he_1)
				type = 1;
			if(stack.getItem() == ModItems.missile_he_2)
				type = 2;
			if(stack.getItem() == ModItems.missile_he_3)
				type = 3;
			if(stack.getItem() == ModItems.missile_incendiary_1)
				type = 4;
			if(stack.getItem() == ModItems.missile_incendiary_2)
				type = 5;
			if(stack.getItem() == ModItems.missile_incendiary_3)
				type = 6;
			if(stack.getItem() == ModItems.missile_emp)
				type = 7;
			if(stack.getItem() == ModItems.missile_ab)
				type = 8;
			if(stack.getItem() == ModItems.missile_decoy)
				type = 9;
			if(stack.getItem() == ModItems.missile_nuclear)
				type = 10;
			if(stack.getItem() == ModItems.missile_devon_1)
				type = 11;
			if(stack.getItem() == ModItems.missile_devon_2)
				type = 12;
			if(stack.getItem() == ModItems.missile_devon_3)
				type = 13;
			if(stack.getItem() == ModItems.missile_at_1)
				type = 14;
			if(stack.getItem() == ModItems.missile_cruise_at_1)
				type = 15;
			
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<TEMissilePacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEMissilePacket m, MessageContext ctx) {
			
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
	
				if (te != null && te instanceof TileEntityLaunchPad) {
						
					TileEntityLaunchPad gen = (TileEntityLaunchPad)te;
					gen.state = m.type;
				}
			} catch(Exception e) { }
			
			return null;
		}
	}
}
