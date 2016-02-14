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
        return "Damage";
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
        if(childValues[0].issetEntity() && childValues[1].issetNumber())
        {
            Entity[] target = childValues[0].getEntity();
            EntityLivingBase player = environ.getCaster();
            float auraMultiplier;
            float dmg = childValues[1].getNumber()[0];
            for(Entity e : target)
            {
                //calculate Aura expense
                auraMultiplier = (float) player.getDistanceSqToEntity(e);
                if(environ.useAura((int) ((AuraUse + auraMultiplier)*dmg)))
                {
                    e.attackEntityFrom(DamageSource.magic,dmg);
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
