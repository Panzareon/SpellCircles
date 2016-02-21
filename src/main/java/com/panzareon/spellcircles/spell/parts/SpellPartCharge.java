package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.spell.*;
import net.minecraft.item.ItemStack;

public class SpellPartCharge extends SpellPart
{
    float chargeAuraMult = 10;

    @Override
    public String getSpellName() {
        return "UAHDK";
    }

    @Override
    public String getSpellId() {
        return "charge";
    }

    @Override
    public int getNrOfChildren() {
        return 2;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        SpellReturnTypes[] ret = {SpellReturnTypes.ACTION};
        return ret;
    }

    @Override
    public SpellPartValue cast() throws MissingAuraException
    {
        SpellPartValue[] childValues = new SpellPartValue[1];
        childValues[0] = children[0].cast();

        return cast(childValues);
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getNumberLength();
        if(nr > 0)
        {
            float time = 0.0f;
            float number;
            for(int i = 0; i < nr; i++)
            {
                number = childValues[0].getNumber(i);
                if(number > 0)
                {
                    time += number;
                }
            }
            float timeTicks = time * 20;
            if(timeTicks >= 1)
            {
                SpellCastWith castWith = environ.castWith;
                SpellEnviron environ1 = new SpellEnviron(children[1].getSpellString());
                environ1.castWith = castWith;
                environ1.setCaster(environ.getCaster());
                environ1.castPos = environ.castPos;
                environ1.chargedAura = (int)(chargeAuraMult * Math.sqrt(timeTicks));
                castWith.setSpellToCall(environ1,(int)timeTicks);
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
            return SpellReturnTypes.ACTION;
        }
        return super.getChildType(childId);
    }
}
