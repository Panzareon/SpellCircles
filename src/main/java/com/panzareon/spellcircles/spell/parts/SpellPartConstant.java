package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;

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
    public String getSpellId() {
        return "constant";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        return new SpellReturnTypes[]{SpellReturnTypes.NUMBER};
    }

    @Override
    public boolean needAdditionalValues()
    {
        return true;
    }

    @Override
    public void additionalValues(String value)
    {
        super.additionalValues(value);
        String[] valueParts = value.split(":");
        fValue = new float[valueParts.length];
        for(int i = 0; i < valueParts.length; i++)
        {
            fValue[i] = Float.parseFloat(valueParts[i]);
        }
    }

}
