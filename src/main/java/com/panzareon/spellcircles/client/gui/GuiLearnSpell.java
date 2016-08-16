package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.init.ModNetwork;
import com.panzareon.spellcircles.network.SpellCircleMessage;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.reference.SpellRune;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiLearnSpell extends GuiScreen
{
    private GuiButton okButton;

    private final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/learn_spell.png");
    private final ResourceLocation spelLRuneTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/spell_runes.png");


    private final int guiWidth = 240;
    private final int guiHeight = 169;

    private final int topHeight = 19;

    private final int leftSideWidth = 120;
    private final int leftSideHeight = guiHeight - topHeight;

    private final int rightSideWidth = 120;
    private final int rightSideHeight = guiHeight;

    private String markedDesc = "";


    public GuiLearnSpell()
    {
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(guiTexture);
        //Draw Main Gui
        drawTexturedModalRect(guiX, guiY,0,0,guiWidth, guiHeight);
        if(markedDesc != "")
        {
            fontRendererObj.drawSplitString(markedDesc, guiX + leftSideWidth + 2, guiY + topHeight + 2,rightSideWidth,0xFFFFFF);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        buttonList.clear();
        okButton = new GuiButton(0, guiX + 25, guiY + guiHeight - 23, 50, 20, "Learn");
        okButton.enabled = false;
        buttonList.add(okButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == okButton) {
            //TODO: learn marked spell
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        //TODO: check if spell is clicked
    }

}
