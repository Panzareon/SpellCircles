package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class SpellPartMotion extends SpellPart
{
    private float AuraUse = 500f;

    @Override
    public String getSpellName()
    {
        return "DKRP";
    }

    @Override
    public String getSpellId()
    {
        return "add_motion";
    }

    @Override
    public int getNrOfChildren()
    {
        return 3;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        SpellReturnTypes[] ret = {SpellReturnTypes.ACTION};
        return ret;
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getEntityLength();
        int nr2 = childValues[1].getDirectionLength();
        int nr3 = childValues[2].getNumberLength();
        if(nr > 0 && nr2 > 0 && nr3 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            if(nr < nr3)
                nr = nr3;
            Vec3 castPos = environ.getCastPosition();
            Vec3 dir;
            float speed;
            Vec3 dirMultiplied;
            float auraAdd;
            Entity entity;
            for(int i = 0; i < nr; i++)
            {
                entity = childValues[0].getEntity(i);
                dir = childValues[1].getDirection(i);
                speed = childValues[2].getNumber(i);
                if(entity == null || dir == null)
                    continue;
                dir = dir.normalize();
                dirMultiplied = new Vec3(dir.xCoord * speed, dir.yCoord *speed, dir.zCoord * speed);
                auraAdd = (float) castPos.squareDistanceTo(entity.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraAdd * 20)*speed)))
                {
                    entity.addVelocity(dirMultiplied.xCoord, dirMultiplied.yCoord, dirMultiplied.zCoord);
                }
                else
                {
                    throw new MissingAuraException(this);
                }
            }
        }
        return new SpellPartValue();
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.ENTITY;
        }
        else if(childId == 1)
        {
            return SpellReturnTypes.DIRECTION;
        }
        else if(childId == 2)
        {
            return SpellReturnTypes.NUMBER;
        }
        return super.getChildType(childId);
    }
}
