package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;

public class SpellPartEnhanceBareFist extends SpellPart
{
    private final float AuraUse = 100f;
    private final int effectDuration = 200;

    @Override
    public String getSpellName() {
        return "LBGFNM";
    }

    @Override
    public String getSpellId() {
        return "enhance_bare_fist";
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
            EntityLivingBase target;
            for(int i = 0; i < nr; i++)
            {
                dmg = (float) Math.floor(childValues[1].getNumber(i));
                target = (EntityLivingBase) childValues[0].getEntity(i);
                if(target == null || dmg <= 0)
                    continue;
                //calculate Aura expense
                auraMultiplier = (float) castPos.squareDistanceTo(target.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraMultiplier)*dmg), environ.strength))
                {
                    target.addPotionEffect(new PotionEffect(ModPotions.enhanceBareFist, effectDuration, (int) dmg));
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
