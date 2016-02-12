package com.panzareon.spellcircles.spell;

import net.minecraft.entity.EntityLivingBase;

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
        addSpellPart(spell);
    }
    public SpellEnviron(SpellPart spell)
    {
        spells = new ArrayList<SpellPart>();
        addSpellPart(spell);
    }

    public void addSpellPart(String spell)
    {
        String[] SpellPartsString = spell.split(" ");
        for(int i = 0; SpellPartsString.length > 0; i++)
        {
            SpellPart part = SpellList.getSpellPart(SpellPartsString[i]);
            part.setEnviron(this);
            while(true)
            {
                if(spells.size() <= nextSpaceIndex)
                {
                    spells.set(nextSpaceIndex, part);
                    break;
                }
                if(spells.get(nextSpaceIndex).addChild(part))
                {
                    break;
                }
                nextSpaceIndex++;
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
                spells.set(nextSpaceIndex, part);
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
        for (SpellPart spell : spells) {
            spell.cast();
        }
    }

    public EntityLivingBase getCaster()
    {
        return caster;
    }

    public boolean useAura(float amount)
    {
        //Todo: remove aura from Player and return true if he had enough
        return true;
    }

    public boolean isFinished()
    {
        if(spells.size() == 0)
            return false;
        return spells.get(nextSpaceIndex).isFinished();
    }
    public String getSpellString()
    {
        String ret = "";
        for(SpellPart spell: spells)
        {
            ret += spell.getSpellString() + " ";
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
