package com.hfr.pon4;

import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

public class CachedWorld implements IBlockAccess
{

	public final World world;
	public int dimension;
	ConcurrentHashMap<Long, Chunk> chunks = new ConcurrentHashMap<Long, Chunk>();
	
	public CachedWorld(World world_)
	{
		world = world_;
		dimension = world.provider.dimensionId;
	}
	
	void addChunk(Chunk chunk)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition);
		chunks.put(key, chunk);
	}
	
	void removeChunk(Chunk chunk)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(chunk.xPosition, chunk.zPosition);
		chunks.remove(key);
	}
	
	public Chunk getChunk(int chunkX, int chunkZ)
	{
		long key = ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ);
		return chunks.get(key);
	}

	@Override
	public Block getBlock(int x, int y, int z)
	{
        Block block = Blocks.air;

        if (y >= 0 && y < getHeight())
        {
            int l = (x >> 4);
            int i1 = (z >> 4);
            long key = ChunkCoordIntPair.chunkXZ2Int(l, i1);

        	Chunk chunk = chunks.get(key);

            if (chunk != null)
            {
                block = chunk.getBlock(x & 15, y, z & 15);
            }
        }
        
        return block;
	}

	@Override
	public TileEntity getTileEntity(int x, int y, int z)
	{
        int l = (x >> 4);
        int i1 = (z >> 4);
        long key = ChunkCoordIntPair.chunkXZ2Int(l, i1);
        Chunk chunk = chunks.get(key);
        if (chunk == null) return null;
        return chunk.func_150806_e(x & 15, y, z & 15);
	}

	@Override
	public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_)
	{
		return 0;
	}

	@Override
	public int getBlockMetadata(int x, int y, int z)
	{
        if (y >= 0 && y < getHeight())
        {
            int l = (x >> 4);
            int i1 = (z >> 4);
            long key = ChunkCoordIntPair.chunkXZ2Int(l, i1);

        	Chunk chunk = chunks.get(key);

            if (chunk != null)
            {
                return chunk.getBlockMetadata(x & 15, y, z & 15);
            }
        }
		return 0;
	}

	@Override
	public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_)
	{
		return 0;
	}

	@Override
	public boolean isAirBlock(int x, int y, int z)
	{
		return getBlock(x, y, z).isAir(this, x, y, z);
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int x, int z)
	{
		return null;
	}

	@Override
	public int getHeight()
	{
		return world.provider.getActualHeight();
	}

	@Override
	public boolean extendedLevelsInChunkCache()
	{
		return false;
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default)
	{
        if (x < -30000000 || z < -30000000 || x >= 30000000 || z >= 30000000)
        {
            return _default;
        }

        return getBlock(x, y, z).isSideSolid(this, x, y, z, side);
	}
}
