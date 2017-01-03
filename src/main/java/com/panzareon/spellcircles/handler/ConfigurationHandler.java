package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
    public static final String CATEGORY_GUI = "gui";
    public static Configuration configuration;
    public static int maxAura;
    public static int auraRegen;

    public static boolean showAura;
    public static int overlayAuraX;
    public static int overlayAuraY;

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
        if(event.getModID().equalsIgnoreCase(Reference.MOD_ID))
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
        maxAura = configuration.get(Configuration.CATEGORY_GENERAL, "maxAura", 1000, "Maximum Aura that Player starts with").getInt();
        auraRegen = configuration.get(Configuration.CATEGORY_GENERAL, "auraRegen", 5, "Aura regeneration per Tick").getInt();
        showAura = configuration.get(CATEGORY_GUI, "showAura", true, "Show Aura-Meter when holding Spell in Hotbar").getBoolean();
        overlayAuraX = configuration.get(CATEGORY_GUI, "auraOverlayX", 2, "X Position of Aura Overlay").getInt();
        overlayAuraY = configuration.get(CATEGORY_GUI, "auraOverlayX", 2, "Y Position of Aura Overlay").getInt();
        if(configuration.hasChanged())
        {
            configuration.save();
        }
    }

}
