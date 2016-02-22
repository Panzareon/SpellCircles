package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.init.ModDimension;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraftforge.common.DimensionManager;

public class SpellPartOverworld extends SpellPart
{


    @Override
    public String getSpellName()
    {
        return "LBG";
    }

    @Override
    public String getSpellId() {
        return "overworld";
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
        ret.setDimension(new int[]{0});
        return ret;
    }
}
