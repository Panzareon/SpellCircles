package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import com.panzareon.spellcircles.utility.MagicDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class SpellPartDamage extends SpellPart
{
    private final float AuraUse = 100f;

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
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException {
        int nr = childValues[0].getEntityLength();
        int nr2 = childValues[1].getNumberLength();
        if(nr > 0 && nr2 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            Vec3d castPos = environ.getCastPosition();
            float auraMultiplier;
            float dmg;
            Entity target;
            for(int i = 0; i < nr; i++)
            {
                dmg = childValues[1].getNumber(i) * 2;
                target = childValues[0].getEntity(i);
                if(target == null || dmg <= 0)
                    continue;
                //calculate Aura expense
                auraMultiplier = (float) castPos.squareDistanceTo(target.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraMultiplier)*dmg), 1))
                {
                    target.attackEntityFrom(new MagicDamageSource(environ.getCaster()),dmg * (4 + environ.strength) / 5);
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
