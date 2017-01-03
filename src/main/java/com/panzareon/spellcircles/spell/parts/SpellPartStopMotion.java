package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class SpellPartStopMotion extends SpellPart
{
    private final float AuraUse = 500f;

    @Override
    public String getSpellName()
    {
        return "SDKVHNF";
    }

    @Override
    public String getSpellId()
    {
        return "stop_motion";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getEntityLength();
        if(nr > 0)
        {
            Vec3d castPos = environ.getCastPosition();
            Vec3d movement;
            Entity entity;
            float auraAdd;
            float speed;
            for(int i = 0; i < nr; i++)
            {
                entity = childValues[0].getEntity(i);
                if(entity == null)
                    continue;
                movement = new Vec3d(entity.motionX, entity.motionY, entity.motionZ);
                speed = (float)movement.lengthVector();
                auraAdd = (float) castPos.squareDistanceTo(entity.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraAdd * 20)*speed), environ.strength))
                {
                    entity.setVelocity(0.0, 0.0, 0.0);
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
        return super.getChildType(childId);
    }
}
