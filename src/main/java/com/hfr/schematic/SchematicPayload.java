package com.hfr.schematic;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;

public class SchematicPayload {

    private static final FMLControlledNamespacedRegistry<Block> BLOCK_REGISTRY = GameData.getBlockRegistry();
	
	public short[] blocks;
	public byte[] metas;
	
	public short width;
	public short height;
	public short length;
	
	public SchematicPayload(Schematic schem) {
		
		int count = schem.width * schem.height * schem.length;

		width = (short) schem.width;
		height = (short) schem.height;
		length = (short) schem.length;
		
		blocks = new short[count];
		metas = new byte[count];
		
		serialize(schem);
	}
	
	public SchematicPayload(ByteBuf buf) {
		
		fromBytes(buf);
	}
	
	//cromples a 3D array of blocks and metadata from a Schematic instance into 1D
	private void serialize(Schematic schem) {
		
		for(int dx = 0; dx < schem.width; dx++) {
			for(int dy = 0; dy < schem.height; dy++) {
				for(int dz = 0; dz < schem.length; dz++) {
					
					short b = schem.blocks[dx][dy][dz];
					byte meta = schem.metadata[dx][dy][dz];
					
					int index = (dx * schem.height * schem.length) + (dy * schem.length) + dz;
					
					blocks[index] = b;
					metas[index] = meta;
				}
			}
		}
	}
	
	//uncromples the serialized data into a schematic
	public Schematic deserialize() {
		
		Schematic schem = new Schematic(width, height, length);
		
		for(int i = 0; i < blocks.length; i++) {
			
			int z = (i) % length;
			int y = (i / length) % height;
			int x = (i / length / height) % width;
			
			schem.blocks[x][y][z] = blocks[i];
			schem.metadata[x][y][z] = metas[i];
		}
		
		return schem;
	}
	
	public void fromBytes(ByteBuf buf) {
		
		int count = buf.readInt();

		width = buf.readShort();
		height = buf.readShort();
		length = buf.readShort();
		
		blocks = new short[count];
		metas = new byte[count];
		
		for(int i = 0; i < count; i++) {
			blocks[i] = buf.readShort();
			metas[i] = buf.readByte();
		}
	}
	
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(blocks.length);
		
		buf.writeShort(width);
		buf.writeShort(height);
		buf.writeShort(length);
		
		for(int i = 0; i < blocks.length; i++) {
			buf.writeShort(blocks[i]);
			buf.writeByte(metas[i]);
		}
	}
}
