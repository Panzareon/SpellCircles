package com.panzareon.spellcircles.dimension;

import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderPocketDim extends WorldProvider
{
    public BiomeGenBase BiomePocketDim = new BiomeGenPocketDim(1,false).setBiomeName("pocket_dimension").setDisableRain().setTemperatureRainfall(1.0f, 0.0f);
    @Override
    public String getDimensionName()
    {
        return "pocket_dimension";
    }

    @Override
    public String getInternalNameSuffix()
    {
        return "_pocket";
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
    public IChunkProvider createChunkGenerator()
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
    public IRenderHandler getWeatherRenderer()
    {
        return super.getWeatherRenderer();
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(BlockPos pos)
    {
        return BiomePocketDim;
    }
}
