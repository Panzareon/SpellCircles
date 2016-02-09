package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;
    public static boolean value;

    public static void init(File configFile) {
        if(configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent event)
    {
        if(event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            //Resync configs
            loadConfiguration();
        }
    }

    public static void loadConfiguration()
    {
        //Load Config File
        configuration.load();

        //Read Config File
        //testing
        value = configuration.get(Configuration.CATEGORY_GENERAL, "value", true, "Testing").getBoolean();
        if(configuration.hasChanged())
        {
            configuration.save();
        }
    }

}
