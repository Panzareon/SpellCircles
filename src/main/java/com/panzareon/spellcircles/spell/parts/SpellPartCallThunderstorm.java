package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;

public class SpellPartCallThunderstorm extends SpellPart
{
    private final int AuraUse = 1100;

    @Override
    public String getSpellName()
    {
        return "TBHJJKZFX";
    }

    @Override
    public String getSpellId()
    {
        return "call_thunderstorm";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        if(environ.useAura(AuraUse))
        {
            environ.getCaster().worldObj.getWorldInfo().setThundering(true);
        }
        return new SpellPartValue();
    }
}
