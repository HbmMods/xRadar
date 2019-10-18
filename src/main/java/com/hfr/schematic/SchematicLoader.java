package com.hfr.schematic;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class SchematicLoader {
	
	private static final FMLControlledNamespacedRegistry<Block> BLOCK_REGISTRY = GameData.getBlockRegistry();
	
	public static Schematic readFromFile(File file) {
		
		NBTTagCompound nbt;
		
		try {
			nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));
			Schematic schem = readFromNBT(nbt);
			schem.name = file.getName().replace(".schematic", "").split("_")[0];
			return schem;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Schematic readFromNBT(NBTTagCompound tagCompound) {

        byte localBlocks[] = tagCompound.getByteArray("Blocks");
        byte localMetadata[] = tagCompound.getByteArray("Data");

        boolean extra = false;
        byte extraBlocks[] = null;
        byte extraBlocksNibble[] = null;
        if (tagCompound.hasKey("AddBlocks")) {
            extra = true;
            extraBlocksNibble = tagCompound.getByteArray("AddBlocks");
            extraBlocks = new byte[extraBlocksNibble.length * 2];
            for (int i = 0; i < extraBlocksNibble.length; i++) {
                extraBlocks[i * 2 + 0] = (byte) ((extraBlocksNibble[i] >> 4) & 0xF);
                extraBlocks[i * 2 + 1] = (byte) (extraBlocksNibble[i] & 0xF);
            }
        } else if (tagCompound.hasKey("Add")) {
            extra = true;
            extraBlocks = tagCompound.getByteArray("Add");
        }

        short width = tagCompound.getShort("Width");
        short length = tagCompound.getShort("Length");
        short height = tagCompound.getShort("Height");

        Short id = null;
        
        Map<Short, Short> oldToNew = new HashMap<Short, Short>();
        
        if (tagCompound.hasKey("SchematicaMapping")) {
            NBTTagCompound mapping = tagCompound.getCompoundTag("SchematicaMapping");
            Set<String> names = mapping.func_150296_c();
            for (String name : names) {
                oldToNew.put(mapping.getShort(name), (short) BLOCK_REGISTRY.getId(name));
            }
        }

        Schematic schematic = new Schematic(width, height, length);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    int index = x + (y * length + z) * width;
                    int blockID = (localBlocks[index] & 0xFF) | (extra ? ((extraBlocks[index] & 0xFF) << 8) : 0);
                    int meta = localMetadata[index] & 0xFF;

                    if ((id = oldToNew.get((short) blockID)) != null) {
                        blockID = id;
                    }

                    schematic.setBlock(x, y, z, BLOCK_REGISTRY.getObjectById(blockID), meta);
                }
            }
        }

        /*NBTTagList tileEntitiesList = tagCompound.getTagList(Names.NBT.TILE_ENTITIES, Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < tileEntitiesList.tagCount(); i++) {
            try {
                TileEntity tileEntity = NBTHelper.readTileEntityFromCompound(tileEntitiesList.getCompoundTagAt(i));
                if (tileEntity != null) {
                    schematic.setTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity);
                }
            } catch (Exception e) {
                Reference.logger.error("TileEntity failed to load properly!", e);
            }
        }*/

        return schematic;
    }

}
