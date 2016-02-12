package com.panzareon.spellcircles.tileentity;

import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySpellCircle extends TileEntity
{
    private SpellEnviron environ = null;

    public void addSpellPart(String name)
    {
        if(environ == null)
        {
            environ = new SpellEnviron(name);
        }
        else
        {
            environ.addSpellPart(name);
        }
    }

    public void addSpellPart(SpellPart part)
    {
        if(environ == null)
        {
            environ = new SpellEnviron(part);
        }
        else
        {
            environ.addSpellPart(part);
        }
    }

    public SpellEnviron getEnviron()
    {
        if(environ == null)
            environ = new SpellEnviron();
        return environ;
    }

    public SpellPart[] getPossibleNextSpellParts()
    {
        SpellReturnTypes needed = SpellReturnTypes.ACTION;
        SpellPart lastNode = getEnviron().getLastNodeWithSpace();
        if(lastNode != null)
        {
            needed = lastNode.getChildType(lastNode.getNrOfSetChildren());
        }


        return SpellList.getSpellWithReturnType(needed);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("spellcircles"))
        {
            NBTTagCompound nbt = (NBTTagCompound) compound.getTag("spellcircles");
            if(nbt.hasKey("spell"))
            {
                String spell = nbt.getString("spell");
                environ = new SpellEnviron(spell);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(environ != null)
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("spell", environ.getSpellString());
            compound.setTag("spellcircles", nbt);
        }
    }
}

