package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.client.gui.GuiAddSpellPart;
import com.panzareon.spellcircles.client.gui.GuiLearnSpell;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler
{
    public static final int ADD_SPELL_PART = 0;
    public static final int LEARN_SPELL_PART = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == ADD_SPELL_PART)
        {
            TileEntitySpellCircle spellCircle = (TileEntitySpellCircle) world.getTileEntity(new BlockPos(x,y,z));
            return new GuiAddSpellPart(spellCircle);
        }
        if(ID == LEARN_SPELL_PART)
        {
            return new GuiLearnSpell();
        }
        return null;
    }
}
