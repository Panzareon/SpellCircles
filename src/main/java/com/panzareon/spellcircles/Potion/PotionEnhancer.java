package com.panzareon.spellcircles.Potion;


import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

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
        }
    }

    public EnhanceTypes getEnhanceType()
    {
        return type;
    }

    public enum EnhanceTypes
    {
        BARE_FIST
    }
}
