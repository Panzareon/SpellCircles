package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class SpellEnviron
{
    protected EntityLivingBase caster;

    protected ArrayList<SpellPart> spells;
    int nextSpaceIndex = 0;

    public SpellEnviron()
    {
        spells = new ArrayList<SpellPart>();
    }
    public SpellEnviron(String spell)
    {
        spells = new ArrayList<SpellPart>();
        if(spell != null && !spell.isEmpty())
            addSpellPart(spell);
    }
    public SpellEnviron(SpellPart spell)
    {
        spells = new ArrayList<SpellPart>();
        if(spell != null)
            addSpellPart(spell);
    }

    public void setCaster(EntityLivingBase casterEntity)
    {
        caster = casterEntity;
    }
    public void addSpellPart(String spell)
    {
        String[] SpellPartsString = spell.split(" ");
        for(int i = 0; i < SpellPartsString.length; i++)
        {
            SpellPart part = SpellList.getSpellPart(SpellPartsString[i]);
            if(part != null)
            {
                part.setEnviron(this);
                while (true) {
                    if (spells.size() <= nextSpaceIndex) {
                        spells.add(nextSpaceIndex, part);
                        break;
                    }
                    if (spells.get(nextSpaceIndex).addChild(part)) {
                        break;
                    }
                    nextSpaceIndex++;
                }
            }
            else
            {
                LogHelper.warn("No Spellpart with Name " + SpellPartsString[i]);
            }
        }
    }
    public void addSpellPart(SpellPart part)
    {
        part.setEnviron(this);
        while(true)
        {
            if(spells.size() <= nextSpaceIndex)
            {
                spells.add(nextSpaceIndex, part);
                break;
            }
            if(spells.get(nextSpaceIndex).addChild(part))
            {
                break;
            }
            nextSpaceIndex++;
        }
    }

    public void cast()
    {
        try {
            for (SpellPart spell : spells) {
                spell.cast();
            }
        }
        catch (MissingAuraException ex)
        {
            LogHelper.info("Not enough Aura for Spellpart: " + ex.spellPart.getSpellId());
        }
    }

    public EntityLivingBase getCaster()
    {
        return caster;
    }

    public boolean useAura(int amount)
    {
        NBTTagCompound nbt = caster.getEntityData();
        if(nbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNBT = nbt.getCompoundTag(Reference.MOD_ID);
            int Aura = scNBT.getInteger("Aura");
            if(Aura >= amount)
            {
                scNBT.setInteger("Aura", Aura - amount);
                return true;
            }
            else
            {
                scNBT.setInteger("Aura", 0);
                return false;
            }
        }
        return false;
    }

    public boolean isFinished()
    {
        if(spells.size() == 0)
            return false;
        return spells.get(nextSpaceIndex).isFinished();
    }
    public String getSpellString()
    {
        String ret = null;
        for(SpellPart spell: spells)
        {
            if(ret == null)
                ret = spell.getSpellString();
            else
                ret += " " + spell.getSpellString();
        }
        return ret;
    }

    public SpellPart getLastNodeWithSpace()
    {
        if(spells.size() == 0)
            return null;
        SpellPart lastSpell = spells.get(spells.size()-1);
        if(lastSpell == null)
            return null;
        return lastSpell.getLastNodeWithSpace();
    }
}
