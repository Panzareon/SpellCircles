package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class SpellPartStopMotion extends SpellPart
{
    private float AuraUse = 500f;

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
        SpellReturnTypes[] ret = {SpellReturnTypes.ACTION};
        return ret;
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getEntityLength();
        if(nr > 0)
        {
            EntityLivingBase player = environ.getCaster();
            Vec3 movement;
            Entity entity;
            float auraAdd;
            float speed;
            for(int i = 0; i < nr; i++)
            {
                entity = childValues[0].getEntity(i);
                movement = new Vec3(entity.motionX, entity.motionY, entity.motionZ);
                speed = (float)movement.lengthVector();
                auraAdd = (float) player.getDistanceSqToEntity(entity);
                if(environ.useAura((int) ((AuraUse + auraAdd * 20)*speed)))
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