package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;

public class SpellPartSlowness extends SpellPart
{
    private final float AuraUse = 1.0f;

    @Override
    public String getSpellName() {
        return "SNDGB";
    }

    @Override
    public String getSpellId() {
        return "slowness";
    }

    @Override
    public int getNrOfChildren() {
        return 3;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException {
        int nr = childValues[0].getEntityLength();
        int nr2 = childValues[1].getNumberLength();
        int nr3 = childValues[2].getNumberLength();
        if(nr > 0 && nr2 > 0 && nr3 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            if(nr < nr3)
                nr = nr3;
            Vec3 castPos = environ.getCastPosition();
            float auraMultiplier;
            float slowStrength;
            EntityLivingBase target;
            int effectDuration;
            for(int i = 0; i < nr; i++)
            {
                slowStrength = (float) Math.floor(childValues[1].getNumber(i));
                target = (EntityLivingBase) childValues[0].getEntity(i);
                effectDuration = (int)(childValues[2].getNumber(i) * 20);
                if(target == null || slowStrength <= 0 || effectDuration < 20)
                    continue;
                if(slowStrength > 5)
                    slowStrength = 5;
                //calculate Aura expense
                auraMultiplier = (float) castPos.squareDistanceTo(target.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraMultiplier)*slowStrength*effectDuration)))
                {
                    target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), effectDuration, (int) slowStrength));
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
        else if(childId == 2)
        {
            return SpellReturnTypes.NUMBER;
        }
        return super.getChildType(childId);
    }
}
