package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.client.gui.GuiOverlay;
import com.panzareon.spellcircles.item.ItemSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayHandler
{
    private GuiOverlay gui = new GuiOverlay();


    public static void init()
    {
        OverlayHandler event = new OverlayHandler();
        MinecraftForge.EVENT_BUS.register(event);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderGameOverlay(RenderGameOverlayEvent event)
    {
        if(event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE)
        {
            return;
        }
        if(ConfigurationHandler.showAura)
        {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if(player == null)
                return;

            gui.renderAura(player);
        }
    }
}
