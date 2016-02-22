package com.panzareon.spellcircles.dimension;

import com.panzareon.spellcircles.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkProviderPocketDim implements IChunkProvider
{
    private World worldObj;
    private Random random;

    public ChunkProviderPocketDim(World worldIn, long seed)
    {
        this.worldObj = worldIn;
        this.random = new Random(seed);
    }

    @Override
    public boolean chunkExists(int x, int z)
    {
        return true;
    }

    @Override
    public Chunk provideChunk(int x, int z)
    {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        IBlockState iblockstate = ModBlocks.pocketDimWall.getDefaultState();

        if (iblockstate != null)
        {
            for (int i = 0; i < worldObj.getHeight(); i++)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        if(i == 0 || j == 0 || k == 0)
                            chunkprimer.setBlockState(j, i, k, iblockstate);
                    }
                }
            }

        }
        return new Chunk(this.worldObj, chunkprimer, x, z);
    }

    @Override
    public Chunk provideChunk(BlockPos blockPosIn)
    {
        return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
    }

    @Override
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
    {
        //NOOP
    }

    @Override
    public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_)
    {
        return false;
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate progressCallback)
    {
        return true;
    }

    @Override
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    @Override
    public boolean canSave()
    {
        return true;
    }

    @Override
    public String makeString()
    {
        return "PocketDimension";
    }

    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return new ArrayList<BiomeGenBase.SpawnListEntry>();
    }

    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
    {
        return null;
    }

    @Override
    public int getLoadedChunkCount()
    {
        return 0;
    }

    @Override
    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_)
    {
        //NOOP
    }

    @Override
    public void saveExtraData()
    {
        //NOOP
    }
}
