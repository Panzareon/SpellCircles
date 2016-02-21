package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class SpellPartStopMotionInDirection extends SpellPart
{
    private float AuraUse = 500f;

    @Override
    public String getSpellName()
    {
        return "KIDVKTGFDSV";
    }

    @Override
    public String getSpellId()
    {
        return "stop_motion_directional";
    }

    @Override
    public int getNrOfChildren()
    {
        return 2;
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
        if(nr > 0 && nr2 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            Vec3 castPos = environ.getCastPosition();
            Vec3 dir;
            double lenght;
            Vec3 movement;
            Entity entity;
            float auraAdd;
            for(int i = 0; i < nr; i++)
            {
                entity = childValues[0].getEntity(i);
                dir = childValues[1].getDirection(i);
                if(entity == null || dir == null)
                    continue;
                movement = new Vec3(entity.motionX, entity.motionY, entity.motionZ);
                lenght = movement.dotProduct(dir);
                dir = dir.normalize();
                movement = movement.subtract(dir.xCoord * lenght, dir.yCoord * lenght, dir.zCoord * lenght);
                if(lenght < 0)
                    lenght *= -1;
                auraAdd = (float) castPos.squareDistanceTo(entity.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraAdd * 20)*((float) lenght))))
                {
                    entity.setVelocity(movement.xCoord, movement.yCoord, movement.zCoord);
                    if(movement.yCoord >= -0.001f)
                        entity.fallDistance = 0.0f;
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
        if(childId == 1)
        {
            return SpellReturnTypes.DIRECTION;
        }
        return super.getChildType(childId);
    }
}
