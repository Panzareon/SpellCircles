package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.handler.ConfigurationHandler;
import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiOverlay extends Gui
{
    private final int auraBackgroundU = 0;
    private final int auraBackgroundV = 0;
    private final int auraBackgroundWidth = 23;
    private final int auraBackgroundHeight = 102;

    private final int auraBarU = 23;
    private final int auraBarV = 0;
    private final int auraBarPosX = 1;
    private final int auraBarPosY = 1;
    private final int auraBarWidth = 21;
    private final int auraBarHeight = 100;

    private final ResourceLocation overlayBar = new ResourceLocation(Reference.MOD_ID, "textures/gui/overlay.png");

    public void renderAura(EntityPlayerSP player)
    {
        boolean spellInHotbar = false;
        int firstHotbarSlot = 0;
        int lastHotbarSlot = firstHotbarSlot + InventoryPlayer.getHotbarSize();
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
