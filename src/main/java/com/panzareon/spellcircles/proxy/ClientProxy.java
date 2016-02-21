package com.panzareon.spellcircles.proxy;

import com.panzareon.spellcircles.handler.OverlayHandler;
import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.init.ModItems;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenders()
    {
        ModItems.registerRender();
        ModBlocks.registerRender();

        OverlayHandler.init();
    }
}
