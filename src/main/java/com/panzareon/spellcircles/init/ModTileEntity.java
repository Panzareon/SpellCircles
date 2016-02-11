package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntity
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntitySpellCircle.class, "spellcirclesSpellCircle");
    }
}
