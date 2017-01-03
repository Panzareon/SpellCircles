package com.panzareon.spellcircles.dimension;

import com.panzareon.spellcircles.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChunkProviderPocketDim implements IChunkGenerator
{
    private World worldObj;
    private Random random;

    public ChunkProviderPocketDim(World worldIn, long seed)
    {
        this.worldObj = worldIn;
        this.random = new Random(seed);
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
    public void populate(int x, int z)
    {
        //NOOP
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return new ArrayList<Biome.SpawnListEntry>();
    }

    @Override
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
    {
        return null;
    }

    @Override
    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_)
    {
        //NOOP
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z){
        //NOOP
        return true;
    }


}
