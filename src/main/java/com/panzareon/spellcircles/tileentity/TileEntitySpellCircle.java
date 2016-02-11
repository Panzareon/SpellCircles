package com.panzareon.spellcircles.tileentity;

import com.panzareon.spellcircles.spell.SpellEnviron;
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

    public SpellEnviron getEnviron()
    {
        return environ;
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

