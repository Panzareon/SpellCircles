package com.panzareon.spellcircles.potion;


import net.minecraft.entity.SharedMonsterAttributes;

public class PotionEnhancer extends PotionSpellCircles
{
    private EnhanceTypes type;
    public PotionEnhancer(boolean badEffect, int potionColor, EnhanceTypes t)
    {
        super(badEffect, potionColor);
        type = t;
        switch (type)
        {
            case BARE_FIST:
                setPotionName("enhance_bare_fist");
                break;
            case FOOT:
                setPotionName("enhance_foot");
                registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7b72a3d5-860a-4e22-8ffd-7341ea8d8f19", 0.5, 2);
                break;
        }
    }

    public EnhanceTypes getEnhanceType()
    {
        return type;
    }

    public enum EnhanceTypes
    {
        BARE_FIST, FOOT
    }
}
