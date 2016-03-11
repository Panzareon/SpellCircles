package com.panzareon.spellcircles;

import com.panzareon.spellcircles.handler.ConfigurationHandler;
import com.panzareon.spellcircles.handler.EntityEventHandler;
import com.panzareon.spellcircles.init.*;
import com.panzareon.spellcircles.proxy.IProxy;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class SpellCircles
{
    @Mod.Instance(Reference.MOD_ID)
    public static SpellCircles instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Loading Config File
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        ModItems.init();
        ModBlocks.init();
        ModTileEntity.init();
        ModNetwork.init();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModGui.init();
        ModSpell.init();
        EntityEventHandler.init();
        ModRecipe.init();
        ModEntity.init();
        proxy.registerRenders();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ModDimension.init();

    }
}
