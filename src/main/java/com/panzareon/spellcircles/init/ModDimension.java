package com.panzareon.spellcircles.init;


import com.panzareon.spellcircles.dimension.WorldProviderPocketDim;
import net.minecraftforge.common.DimensionManager;

public class ModDimension
{
    public static int pocketDimensionId;

    public static void init()
    {
        pocketDimensionId = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(++pocketDimensionId, WorldProviderPocketDim.class, false);
        DimensionManager.registerDimension(pocketDimensionId, pocketDimensionId);
    }

}
