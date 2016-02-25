package com.panzareon.spellcircles.Potion;


import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

public class PotionAuraExhaust extends PotionSpellCircles
{
    public PotionAuraExhaust(ResourceLocation location, boolean badEffect, int potionColor)
    {
        super(location, badEffect, potionColor);
        setPotionName("aura_exhaust");
    }
}
