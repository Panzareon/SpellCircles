package com.panzareon.spellcircles.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderPocketDim extends WorldProvider
{
    public Biome BiomePocketDim;

    public WorldProviderPocketDim(){
        BiomeProperties properties = new BiomeProperties("pocket_dimension");
        properties.setRainDisabled().setTemperature(0.0f);
        BiomePocketDim = new BiomeGenPocketDim(properties);
    }

    @Override
    public DimensionType getDimensionType(){
        //TODO: register Dimension type
        return DimensionType.OVERWORLD;
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkProviderPocketDim(worldObj, getSeed());
    }

    @Override
    protected void generateLightBrightnessTable()
    {
        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 60.0F;
            this.lightBrightnessTable[i] = f1;
        }
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }

    @Override
    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }

    @Override
    public Biome getBiomeForCoords(BlockPos pos)
    {
        return BiomePocketDim;
    }
}
