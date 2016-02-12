package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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
    private int listTextPosX = 2;
    private int listTextPosY = 1;

    private int rightSideWidth = 100;
    private int rightSideHeight = 80;

    private TileEntitySpellCircle spellCircle;

    private SpellPart spellPartToAdd;

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
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/add_spell_parts.png"));
        drawTexturedModalRect(guiX, guiY,0,0,guiWidth, guiHeight);
        this.drawDefaultBackground();
        int xPos = guiX + listPosX + listTextPosX;
        int yPos = guiY + listPosY + listTextPosY;
        for(SpellPart part : possibleSpellParts)
        {
            fontRendererObj.drawString(part.getSpellId(),xPos, yPos, 0xFFFFFF);
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
            if(spellPartToAdd != null)
            {
                spellCircle.addSpellPart(spellPartToAdd);
            }
            updateSpells();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
