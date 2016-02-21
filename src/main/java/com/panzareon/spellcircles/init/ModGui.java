package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.SpellCircles;
import com.panzareon.spellcircles.handler.ModGuiHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ModGui
{
    public static final IGuiHandler GUI_HANDLER = new ModGuiHandler();

    public static void init()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(SpellCircles.instance, GUI_HANDLER);
    }
}
