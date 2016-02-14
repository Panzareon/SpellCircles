package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.handler.ConfigurationHandler;
import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiOverlay extends Gui
{
    private int auraBackgroundU = 0;
    private int auraBackgroundV = 0;
    private int auraBackgroundWidth = 23;
    private int auraBackgroundHeight = 102;

    private int auraBarU = 23;
    private int auraBarV = 0;
    private int auraBarPosX = 1;
    private int auraBarPosY = 1;
    private int auraBarWidth = 21;
    private int auraBarHeight = 100;

    private ResourceLocation overlayBar = new ResourceLocation(Reference.MOD_ID, "textures/gui/overlay.png");

    public void renderAura(EntityPlayerSP player)
    {
        boolean spellInHotbar = false;
        int firstHotbarSlot = 0;
        int lastHotbarSlot = firstHotbarSlot + player.inventory.getHotbarSize();
        for(int i = firstHotbarSlot; i < lastHotbarSlot; i++)
        {
            ItemStack item = player.inventory.getStackInSlot(i);
            if(item != null && item.getItem() instanceof ItemSpell)
            {
                spellInHotbar = true;
                break;
            }
        }
        if(spellInHotbar)
        {
            if(player.getEntityData().hasKey(Reference.MOD_ID))
            {
                Minecraft mc = Minecraft.getMinecraft();
                mc.renderEngine.bindTexture(overlayBar);
                drawTexturedModalRect(ConfigurationHandler.overlayAuraX, ConfigurationHandler.overlayAuraY, auraBackgroundU, auraBackgroundV, auraBackgroundWidth, auraBackgroundHeight);
                //Calculate Percentage of Aura
                NBTTagCompound nbt = (NBTTagCompound) player.getEntityData().getTag(Reference.MOD_ID);
                int aura = nbt.getInteger("Aura");
                aura *= auraBarHeight;
                aura /= ConfigurationHandler.maxAura;
                drawTexturedModalRect(ConfigurationHandler.overlayAuraX + auraBarPosX, ConfigurationHandler.overlayAuraY + auraBarPosY + (auraBarHeight - aura), auraBarU, auraBarV, auraBarWidth, aura );

            }
        }
    }
}
