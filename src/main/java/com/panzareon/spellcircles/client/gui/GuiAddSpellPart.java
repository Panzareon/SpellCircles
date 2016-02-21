package com.panzareon.spellcircles.client.gui;

import com.panzareon.spellcircles.init.ModNetwork;
import com.panzareon.spellcircles.network.SpellCircleMessage;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.reference.SpellRune;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiAddSpellPart extends GuiScreen
{
    private GuiButton okButton;

    private final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/add_spell_parts.png");
    private final ResourceLocation spelLRuneTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/spell_runes.png");

    private int selectedTextfield = -1;
    private boolean hasSelectedElementAdditionalValues = false;


    private String search = "";
    private String edit = "";

    private final int guiWidth = 240;
    private final int guiHeight = 169;

    private final int topHeight = 19;

    private final int leftSideWidth = 120;
    private final int leftSideHeight = guiHeight - topHeight;
    private final int listPosX = 5;
    private final int listPosY = 15 + topHeight;
    private final int listWidth = 110;
    private final int listHeight = 90;
    private final int listElementHeight = 9;
    private final int nrOfListElements = listHeight / listElementHeight;
    private final int listTextPosX = 2;
    private final int listTextPosY = 1;
    private final int scrollPosition = 0;

    private final int selectedElementU = 0;
    private final int selectedElementV = 169;

    private final int searchBarPosX = 5;
    private final int searchBarPosY = 3 + topHeight;
    private final int searchBarWidth = listWidth;
    private final int searchBarHeight = 9;

    private final int editBarPosX = 5;
    private final int editBarPosY = 115 + topHeight;
    private final int editBarWidth = listWidth;
    private final int editBarHeight = 9;

    private final int editBarDisabledU = 0;
    private final int editBarDisabledV = 178;

    private final int rightSideWidth = 120;
    private final int rightSideHeight = guiHeight;

    private TileEntitySpellCircle spellCircle;
    private String fullSpellText = "";

    private int spellPartToAdd = -1;
    private String toAddDesc = "";

    private SpellPart[] possibleSpellParts;
    private String nextSpellPartDesc = "";
    private String[] possibleSpellPartsNames;
    private boolean isFinished;


    public GuiAddSpellPart(TileEntitySpellCircle t)
    {
        spellCircle = t;
        updateSpells();
    }

    private void updateSpells()
    {
        possibleSpellParts = spellCircle.getPossibleNextSpellParts();
        possibleSpellPartsNames = new String[possibleSpellParts.length];
        for(int i = 0; i < possibleSpellPartsNames.length; i++)
        {
            possibleSpellPartsNames[i] = StatCollector.translateToLocal("spell." + Reference.MOD_ID.toLowerCase() + ":" + possibleSpellParts[i].getSpellId() + ".name");
        }
        isFinished = spellCircle.getEnviron().isFinished();
        fullSpellText = spellCircle.getEnviron().getSpellString();
        SpellPart lastSpell = spellCircle.getEnviron().getLastNodeWithSpace();
        if(lastSpell != null)
        {
            String prefix = "spell." + Reference.MOD_ID.toLowerCase() + ":" + lastSpell.getSpellId();
            nextSpellPartDesc = StatCollector.translateToLocal(prefix + ".name") + ": ";
            int i = lastSpell.getNrOfSetChildren() + 1;
            nextSpellPartDesc += StatCollector.translateToLocal(prefix + ".child" + String.valueOf(i));
        }
        else
        {
            nextSpellPartDesc = StatCollector.translateToLocal("spellDesc." + Reference.MOD_ID.toLowerCase() + ":addAction.name");
        }
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
        mc.renderEngine.bindTexture(guiTexture);
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
        fontRendererObj.drawSplitString(nextSpellPartDesc, guiX + 2, guiY + 2, guiWidth - 4, 0xFFFFFF);
        //Draw Search String
        fontRendererObj.drawString(search, guiX + searchBarPosX + 1, guiY + searchBarPosY + 1, 0xFFFFFF);
        //Draw Possible Spell Names
        int xPos = guiX + listPosX + listTextPosX;
        int yPos = guiY + listPosY + listTextPosY;
        int color;
        String SpellId;
        for(int i = scrollPosition; i < possibleSpellParts.length && i < nrOfListElements + scrollPosition; i++)
        {
            SpellId = possibleSpellPartsNames[i];
            if(SpellId.toLowerCase().contains(search.toLowerCase()))
                color = 0xFFFFFF;
            else
                color = 0xAAAAAA;
            fontRendererObj.drawString(SpellId, xPos, yPos, color);
            yPos += listElementHeight;
        }
        if(spellPartToAdd != -1)
        {
            fontRendererObj.drawSplitString(toAddDesc, guiX + leftSideWidth + 2, guiY + topHeight + 2,rightSideWidth,0xFFFFFF);
        }
        if(fullSpellText != null)
        {
            mc.renderEngine.bindTexture(spelLRuneTexture);
            xPos = guiX;
            yPos = guiY + guiHeight + 3;
            for (int i = 0; i < fullSpellText.length(); i++)
            {
                SpellRune.uvCoords uv = SpellRune.getUV(fullSpellText.charAt(i));
                if(uv != null)
                    drawTexturedModalRect(xPos, yPos, uv.u, uv.v, 7, 7);
                xPos += 7;
                if (xPos > guiX + guiWidth)
                {
                    xPos = guiX;
                    yPos += 7;
                }
            }
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
        okButton.enabled = false;
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
            okButton.enabled = false;
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
                okButton.enabled = !hasSelectedElementAdditionalValues;
                toAddDesc = StatCollector.translateToLocal("spellDesc." + Reference.MOD_ID.toLowerCase() + ":aurause.name") + " ";
                String prefix = "spell." + Reference.MOD_ID.toLowerCase() + ":" + possibleSpellParts[spellPartToAdd].getSpellId();
                toAddDesc += StatCollector.translateToLocal(prefix + ".aurause") + "\n";
                toAddDesc += StatCollector.translateToLocal(prefix + ".desc") + "\n";
                for(int i = 1; i <= possibleSpellParts[spellPartToAdd].getNrOfChildren(); i++)
                {
                    toAddDesc += StatCollector.translateToLocalFormatted("spellDesc." + Reference.MOD_ID.toLowerCase() + ":child.name", i) + " ";
                    toAddDesc += StatCollector.translateToLocal(prefix + ".child" + String.valueOf(i)) + "\n";
                }
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
                if(edit.length() > 0)
                {
                    okButton.enabled = true;
                }
            }
        }
    }
}
