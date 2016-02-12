package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.rmi.runtime.Log;

import java.io.IOException;

public class GuiAddSpellPart extends GuiScreen
{
    private GuiButton okButton;

    private int guiWidth = 200;
    private int guiHeight = 150;


    private int leftSideWidth = 100;
    private int leftSideHeight = guiHeight;
    private int listPosX = 5;
    private int listPosY = 10;
    private int listWidth = 90;
    private int listHeight = 113;
    private int listElementHeight = 9;
    private int nrOfListElements = listHeight / listElementHeight;
    private int listTextPosX = 2;
    private int listTextPosY = 1;
    private int scrollPosition = 0;

    private int selectedElementU = 0;
    private int selectedElementV = 150;

    private int rightSideWidth = 100;
    private int rightSideHeight = 80;

    private TileEntitySpellCircle spellCircle;

    private int spellPartToAdd = -1;

    private SpellPart[] possibleSpellParts;
    private boolean isFinished;


    public GuiAddSpellPart(TileEntitySpellCircle t)
    {
        spellCircle = t;
        updateSpells();
    }

    private void updateSpells()
    {
        possibleSpellParts = spellCircle.getPossibleNextSpellParts();
        isFinished = spellCircle.getEnviron().isFinished();
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/add_spell_parts.png"));
        drawTexturedModalRect(guiX, guiY,0,0,guiWidth, guiHeight);
        if(spellPartToAdd >= scrollPosition && spellPartToAdd < possibleSpellParts.length && spellPartToAdd < nrOfListElements + scrollPosition)
        {
            drawTexturedModalRect(guiX + listPosX, guiY +  listPosY + (spellPartToAdd - scrollPosition) * listElementHeight, selectedElementU, selectedElementV, listWidth, listElementHeight);
        }
        int xPos = guiX + listPosX + listTextPosX;
        int yPos = guiY + listPosY + listTextPosY;
        for(int i = scrollPosition; i < possibleSpellParts.length && i < nrOfListElements + scrollPosition; i++)
        {
            fontRendererObj.drawString(possibleSpellParts[i].getSpellId(),xPos, yPos, 0xFFFFFF);
            yPos += listElementHeight;
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
        okButton = new GuiButton(0, guiX + 25, guiY + guiHeight - 23, 50, 20, "Add");
        buttonList.add(okButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == okButton) {
            if(spellPartToAdd != -1)
            {
                spellCircle.addSpellPart(possibleSpellParts[spellPartToAdd]);
            }
            updateSpells();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(guiX + listPosX <= mouseX && mouseX <= guiX + listPosX + listWidth && guiY + listPosY <= mouseY && mouseY <= guiY + listPosY + listHeight)
        {
            //clicked in List
            int element = scrollPosition + (mouseY - guiY - listPosY) / listElementHeight;
            if(possibleSpellParts.length > element)
            {
                spellPartToAdd = element;
            }
        }
    }

}
