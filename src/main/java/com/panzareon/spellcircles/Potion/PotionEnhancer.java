package com.panzareon.spellcircles.Potion;


import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class PotionEnhancer extends PotionSpellCircles
{
    private EnhanceTypes type;
    public PotionEnhancer(ResourceLocation location, boolean badEffect, int potionColor, EnhanceTypes t)
    {
        super(location, badEffect, potionColor);
        type = t;
        switch (type)
        {
            case BARE_FIST:
                setPotionName("enhanceBareFist");
                break;
            case FOOT:
                setPotionName("enhanceFoot");
                registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "7b72a3d5-860a-4e22-8ffd-7341ea8d8f19", 0.5, 2);
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
