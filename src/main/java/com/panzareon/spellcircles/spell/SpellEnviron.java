package com.panzareon.spellcircles.spell;

import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;

public class SpellEnviron
{
    protected EntityLivingBase caster;

    protected ArrayList<SpellPart> spells;

    public SpellEnviron(String spell)
    {
        String[] SpellPartsString = spell.split(" ");
        int j = 0;
        for(int i = 0; SpellPartsString.length > 0; i++)
        {
            SpellPart part = SpellList.getSpellPart(SpellPartsString[i]);
            while(true)
            {
                if(spells.size() <= j)
                {
                    spells.set(j, part);
                    break;
                }
                if(spells.get(j).addChild(part))
                {
                    break;
                }
                j++;
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

}
