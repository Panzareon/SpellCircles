package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.util.Vec3;

import java.util.HashSet;

public class SpellPartMovePositions extends SpellPart
{
    @Override
    public String getSpellName()
    {
        return "FMN";
    }

    @Override
    public String getSpellId()
    {
        return "move_positions";
    }

    @Override
    public int getNrOfChildren()
    {
        return 3;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        SpellReturnTypes[] ret = {SpellReturnTypes.POSITION};
        return ret;
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        HashSet<Vec3> positions = new HashSet<Vec3>();
        int nr = childValues[0].getPositionLength();
        int nr2 = childValues[1].getDirectionLength();
        int nr3 = childValues[2].getNumberLength();
        if(nr < nr2)
            nr = nr2;
        if(nr < nr3)
            nr = nr3;
        Vec3 addVector;

        for(int i = 0; i < nr; i++)
        {
            addVector = VectorUtil.multiplyVector(childValues[1].getDirection(i),childValues[2].getNumber(i));
            positions.add(childValues[0].getPosition(i).add(addVector));
        }

        SpellPartValue ret = new SpellPartValue();
        ret.setPosition(positions.toArray(new Vec3[positions.size()]));
        return ret;
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.POSITION;
        }
        if(childId == 1)
        {
            return SpellReturnTypes.DIRECTION;
        }
        if(childId == 2)
        {
            return SpellReturnTypes.NUMBER;
        }
        return super.getChildType(childId);
    }
}
