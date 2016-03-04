package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.network.PlayerAuraMessage;
import com.panzareon.spellcircles.network.SpellCircleMessage;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork
{
    public static SimpleNetworkWrapper network;

    public static void init()
    {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        network.registerMessage(SpellCircleMessage.Handler.class,SpellCircleMessage.class,0, Side.SERVER);


        network.registerMessage(PlayerAuraMessage.Handler.class,PlayerAuraMessage.class,1, Side.CLIENT);
    }
}
