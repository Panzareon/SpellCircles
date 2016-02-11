package com.panzareon.spellcircles.spell;

import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;

public class SpellEnviron
{
    protected EntityLivingBase caster;

    protected ArrayList<SpellPart> spells;
    int nextSpaceIndex = 0;

    public SpellEnviron(String spell)
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

}
