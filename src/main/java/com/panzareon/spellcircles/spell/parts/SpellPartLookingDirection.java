package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class SpellPartLookingDirection extends SpellPart
{
    @Override
    public String getSpellName()
    {
        return "M";
    }

    @Override
    public String getSpellId()
    {
        return "Looking Direction";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        SpellReturnTypes[] ret = {SpellReturnTypes.DIRECTION};
        return ret;
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        //No Spell cost
        SpellPartValue ret = new SpellPartValue();
        if(childValues[0].issetEntity())
        {
            //Get Value
            Vec3[] dir = {childValues[0].getEntity()[0].getLookVec()};
            ret.setDirection(dir);
        }
        return ret;
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
            return SpellReturnTypes.ENTITY;
        return super.getChildType(childId);
    }
}
