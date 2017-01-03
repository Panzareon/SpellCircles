package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.util.math.Vec3d;
import java.util.HashSet;

public class SpellPartExpandPositionList extends SpellPart
{
    @Override
    public String getSpellName()
    {
        return "GSMN";
    }

    @Override
    public String getSpellId()
    {
        return "expand_position_list";
    }

    @Override
    public int getNrOfChildren()
    {
        return 3;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.POSITION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        HashSet<Vec3d> positions = new HashSet<Vec3d>();
        int nr = childValues[0].getPositionLength();
        int nr2 = childValues[1].getDirectionLength();
        int nr3 = childValues[2].getNumberLength();
        for(int i = 0; i < nr; i++)
        {
            positions.add(childValues[0].getPosition(i));
        }
        if(nr < nr2)
            nr = nr2;
        if(nr < nr3)
            nr = nr3;
        Vec3d addVector;
        Vec3d pos;
        Vec3d direction;

        for(int i = 0; i < nr; i++)
        {
            pos = childValues[0].getPosition(i);
            direction = childValues[1].getDirection(i);
            if(pos == null || direction == null)
                continue;
            addVector = VectorUtil.multiplyVector(direction, childValues[2].getNumber(i));
            positions.add(pos.add(addVector));
        }

        SpellPartValue ret = new SpellPartValue();
        ret.setPosition(positions.toArray(new Vec3d[positions.size()]));
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
