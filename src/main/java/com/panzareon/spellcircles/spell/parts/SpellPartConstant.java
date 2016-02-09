package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;

public class SpellPartConstant extends SpellPart
{
    protected float[] fValue;

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues)
    {
        SpellPartValue ret = new SpellPartValue();
        ret.setNumber(fValue);
        return ret;
    }

    @Override
    public String getSpellName()
    {
        return "C";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    public void additionalValues(String value)
    {
        String[] valueParts = value.split(":");
        fValue = new float[valueParts.length];
        for(int i = 0; i < valueParts.length; i++)
        {
            fValue[i] = Float.parseFloat(valueParts[i]);
        }
    }

}
