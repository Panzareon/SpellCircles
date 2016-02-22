package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.world.storage.WorldInfo;

public class SpellPartClearWeather extends SpellPart
{
    private final int AuraUse = 1100;

    @Override
    public String getSpellName()
    {
        return "FLFBNI";
    }

    @Override
    public String getSpellId()
    {
        return "clear_weather";
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
            WorldInfo worldInfo = environ.getCaster().worldObj.getWorldInfo();
            worldInfo.setRaining(false);
            worldInfo.setThundering(false);
        }
        return new SpellPartValue();
    }
}
