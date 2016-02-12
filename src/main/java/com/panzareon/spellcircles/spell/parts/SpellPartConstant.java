package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import com.panzareon.spellcircles.utility.LogHelper;

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
        return "Constant";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        SpellReturnTypes[] ret = {SpellReturnTypes.NUMBER,SpellReturnTypes.ACTION};
        return ret;
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
