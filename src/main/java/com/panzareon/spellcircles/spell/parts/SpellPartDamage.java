package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class SpellPartDamage extends SpellPart
{
    private float AuraUse = 100f;

    @Override
    public String getSpellName() {
        return "SKFZ";
    }

    @Override
    public String getSpellId() {
        return "damage";
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
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException {
        int nr = childValues[0].getEntityLength();
        int nr2 = childValues[1].getNumberLength();
        if(nr > 0 && nr2 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            EntityLivingBase player = environ.getCaster();
            float auraMultiplier;
            float dmg;
            Entity target;
            for(int i = 0; i < nr; i++)
            {
                dmg = childValues[1].getNumber(i);
                target = childValues[0].getEntity(i);
                //calculate Aura expense
                auraMultiplier = (float) player.getDistanceSqToEntity(target);
                if(environ.useAura((int) ((AuraUse + auraMultiplier)*dmg)))
                {
                    target.attackEntityFrom(DamageSource.magic,dmg);
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
            return SpellReturnTypes.NUMBER;
        }
        return super.getChildType(childId);
    }
}