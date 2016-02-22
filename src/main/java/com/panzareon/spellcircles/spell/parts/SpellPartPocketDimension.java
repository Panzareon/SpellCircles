package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.init.ModDimension;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;

public class SpellPartPocketDimension extends SpellPart
{


    @Override
    public String getSpellName()
    {
        return "LGFHJM";
    }

    @Override
    public String getSpellId() {
        return "pocket_dimension";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        return new SpellReturnTypes[]{SpellReturnTypes.DIMENSION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues)
    {
        SpellPartValue ret = new SpellPartValue();
        ret.setDimension(new int[]{ModDimension.pocketDimensionId});
        return ret;
    }
}
