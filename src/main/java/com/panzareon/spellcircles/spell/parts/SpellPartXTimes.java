package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;

public class SpellPartXTimes extends SpellPart
{
    private final float chargeAuraMult = 10;

    @Override
    public String getSpellName() {
        return "DLBT;";
    }

    @Override
    public String getSpellId() {
        return "x_times";
    }

    @Override
    public int getNrOfChildren() {
        return 3;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    public SpellPartValue cast() throws MissingAuraException
    {
        SpellPartValue[] childValues = new SpellPartValue[2];
        if(environ.numberOfCasts > 0)
        {
            childValues[0] = new SpellPartValue();
            childValues[0].setNumber(new float[]{environ.numberOfCasts});
        }
        else
        {
            childValues[0] = children[0].cast();
        }
        childValues[1] = children[1].cast();

        return cast(childValues);
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getNumberLength();
        int nr2 = childValues[1].getNumberLength();
        if(nr > 0 && nr2 > 0)
        {
            float time = 0.0f;
            float v;
            int number = 0;
            for(int i = 0; i < nr; i++)
            {
                v = childValues[0].getNumber(i);
                if(v > 0)
                {
                    number += v;
                }
            }
            for(int i = 0; i < nr2; i++)
            {
                v = childValues[1].getNumber(i);
                if(v > 0)
                {
                    time += v;
                }
            }
            float timeTicks = time * 20;
            if(timeTicks < 1.0f)
                timeTicks = 1.0f;
            if(number >= 1)
            {
                children[2].cast();
            }
            if(number > 1)
            {
                SpellEnviron environ1 = new SpellEnviron(this.getSpellString());
                environ.setEnvironVariables(environ1);
                environ1.numberOfCasts = number - 1;
                environ1.chargedAura = (int)(chargeAuraMult * Math.sqrt(timeTicks));
                environ.castWith.setSpellToCall(environ1,(int)timeTicks);
            }
        }
        return new SpellPartValue();
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.NUMBER;
        }
        else if(childId == 1)
        {
            return SpellReturnTypes.NUMBER;
        }
        else if(childId == 2)
        {
            return SpellReturnTypes.ACTION;
        }
        return super.getChildType(childId);
    }
}
