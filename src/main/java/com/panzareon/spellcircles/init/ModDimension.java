package com.panzareon.spellcircles.init;


import com.panzareon.spellcircles.dimension.WorldProviderPocketDim;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimension
{
    public static int pocketDimensionId;
    public static DimensionType pocketDimension;

    public static void init()
    {
        pocketDimensionId = DimensionManager.getNextFreeDimId();
        pocketDimension = DimensionType.register("pocket_dimension", "_pocket", pocketDimensionId, WorldProviderPocketDim.class, true);
        DimensionManager.registerDimension(pocketDimensionId, pocketDimension);
    }

}
