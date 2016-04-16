package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;

public class SpellPartSetNight extends SpellPart
{
    private final int AuraUse = 1100;

    @Override
    public String getSpellName()
    {
        return "FKLDN";
    }

    @Override
    public String getSpellId()
    {
        return "set_night";
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
        if(environ.useAura(AuraUse, environ.strength))
        {
            environ.getCaster().worldObj.setWorldTime(15000);
        }
        return new SpellPartValue();
    }
}
