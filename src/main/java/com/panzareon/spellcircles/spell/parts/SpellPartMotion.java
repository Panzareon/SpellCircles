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
        return "Add Motion";
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
        if(childValues[0].issetEntity() && childValues[1].issetDirection() && childValues[2].issetNumber())
        {
            EntityLivingBase player = environ.getCaster();
            Vec3 dir = childValues[1].getDirection()[0];
            dir = dir.normalize();
            float speed = childValues[2].getNumber()[0];
            Vec3 dirMultiplied = new Vec3(dir.xCoord * speed, dir.yCoord *speed, dir.zCoord * speed);
            float auraAdd;
            for(Entity e : childValues[0].getEntity())
            {
                auraAdd = (float) player.getDistanceSqToEntity(e);
                if(environ.useAura((int) ((AuraUse + auraAdd * 20)*speed)))
                {
                    e.addVelocity(dirMultiplied.xCoord, dirMultiplied.yCoord, dirMultiplied.zCoord);
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
