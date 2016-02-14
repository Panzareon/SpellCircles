package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.init.ModNetwork;
import com.panzareon.spellcircles.network.SpellCircleMessage;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import sun.rmi.runtime.Log;

import java.io.IOException;

public class GuiAddSpellPart extends GuiScreen
{
    private GuiButton okButton;

    private int selectedTextfield = -1;
    private boolean hasSelectedElementAdditionalValues = false;


    private String search = "";
    private String edit = "";

    private int guiWidth = 200;
    private int guiHeight = 150;


    private int leftSideWidth = 100;
    private int leftSideHeight = guiHeight;
    private int listPosX = 5;
    private int listPosY = 15;
    private int listWidth = 90;
    private int listHeight = 90;
    private int listElementHeight = 9;
    private int nrOfListElements = listHeight / listElementHeight;
    private int listTextPosX = 2;
    private int listTextPosY = 1;
    private int scrollPosition = 0;

    private int selectedElementU = 0;
    private int selectedElementV = 150;

    private int searchBarPosX = 5;
    private int searchBarPosY = 3;
    private int searchBarWidth = listWidth;
    private int searchBarHeight = 9;

    private int editBarPosX = 5;
    private int editBarPosY = 115;
    private int editBarWidth = listWidth;
    private int editBarHeight = 9;

    private int editBarDisabledU = 0;
    private int editBarDisabledV = 159;

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

    private void AddSpellPart()
    {
        try
        {
            SpellPart part = possibleSpellParts[spellPartToAdd].getClass().newInstance();
            if(hasSelectedElementAdditionalValues)
            {
                part.additionalValues(edit);
            }
            spellCircle.addSpellPart(part);
            String sendMsg = part.getSpellString();
            ModNetwork.network.sendToServer(new SpellCircleMessage(sendMsg,spellCircle.getPos()));
            edit = "";
        }
        catch(Exception ex)
        {
            LogHelper.error(ex);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        GL11.glColor4f(1, 1, 1, 1);
        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/add_spell_parts.png"));
        //Draw Main Gui
        drawTexturedModalRect(guiX, guiY,0,0,guiWidth, guiHeight);
        //Draw Selected SpellPart Background
        if(spellPartToAdd >= scrollPosition && spellPartToAdd < possibleSpellParts.length && spellPartToAdd < nrOfListElements + scrollPosition)
        {
            drawTexturedModalRect(guiX + listPosX, guiY +  listPosY + (spellPartToAdd - scrollPosition) * listElementHeight, selectedElementU, selectedElementV, listWidth, listElementHeight);
        }
        //Draw Disabled Edit Field
        if(!hasSelectedElementAdditionalValues)
        {
            drawTexturedModalRect(guiX + editBarPosX, guiY + editBarPosY, editBarDisabledU, editBarDisabledV, editBarWidth, editBarHeight);
        }
        else
        {
            //Draw Edit String
            fontRendererObj.drawString(edit, guiX + editBarPosX + 1, guiY + editBarPosY + 1, 0xFFFFFF);
        }
        //Draw Search String
        fontRendererObj.drawString(search, guiX + searchBarPosX + 1, guiY + searchBarPosY + 1, 0xFFFFFF);
        //Draw Possible Spell Names
        int xPos = guiX + listPosX + listTextPosX;
        int yPos = guiY + listPosY + listTextPosY;
        int color;
        String SpellId;
        for(int i = scrollPosition; i < possibleSpellParts.length && i < nrOfListElements + scrollPosition; i++)
        {
            SpellId = possibleSpellParts[i].getSpellId();
            if(SpellId.toLowerCase().contains(search.toLowerCase()))
                color = 0xFFFFFF;
            else
                color = 0xAAAAAA;
            fontRendererObj.drawString(SpellId,xPos, yPos, color);
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
                AddSpellPart();
            }
            spellPartToAdd = -1;
            updateSpells();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int guiX = (width - guiWidth) / 2;
        int guiY = (height - guiHeight) /2;
        selectedTextfield = -1;
        if(guiX + listPosX <= mouseX && mouseX <= guiX + listPosX + listWidth && guiY + listPosY <= mouseY && mouseY <= guiY + listPosY + listHeight)
        {
            //clicked in List
            int element = scrollPosition + (mouseY - guiY - listPosY) / listElementHeight;
            if(possibleSpellParts.length > element)
            {
                spellPartToAdd = element;
                hasSelectedElementAdditionalValues = possibleSpellParts[spellPartToAdd].needAdditionalValues();
            }
        }
        else if(guiX + searchBarPosX <= mouseX && mouseX <= guiX + searchBarPosX + searchBarWidth && guiY + searchBarPosY <= mouseY && mouseY <= guiY + searchBarPosY + searchBarHeight)
        {
            selectedTextfield = 0;
        }
        else if(guiX + editBarPosX <= mouseX && mouseX <= guiX + editBarPosX + editBarWidth && guiY + editBarPosY <= mouseY && mouseY <= guiY + editBarPosY + editBarHeight)
        {
            if(hasSelectedElementAdditionalValues)
                selectedTextfield = 1;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if(selectedTextfield != -1)
        {
            String toEdit = "";
            if (selectedTextfield == 0)
            {
                toEdit = search;
            }
            else if (selectedTextfield == 1)
            {
                toEdit = edit;
            }
            if(keyCode == Keyboard.KEY_BACK)
            {
                if(toEdit.length() > 0)
                {
                    toEdit = toEdit.substring(0, toEdit.length() - 1);
                }
            }
            else
            {
                toEdit += typedChar;
            }
            if (selectedTextfield == 0)
            {
                search = toEdit;
            }
            else if (selectedTextfield == 1)
            {
                edit = toEdit;
            }
        }
    }
}
