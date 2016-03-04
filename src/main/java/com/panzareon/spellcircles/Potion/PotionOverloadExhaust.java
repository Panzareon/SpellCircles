package com.panzareon.spellcircles.Potion;


import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;


public class PotionOverloadExhaust extends PotionSpellCircles
{
    public PotionOverloadExhaust(ResourceLocation location, boolean badEffect, int potionColor)
    {
        super(location, badEffect, potionColor);
        setPotionName("overload_exhaust");
        registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "2d58f387-1f0b-495c-a041-922ccc98f8bb", -0.4, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "c3d4b955-779a-4b67-925d-6054804c8291", -0.7, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.knockbackResistance, "6e192ccf-e529-4207-94e2-7085d395f720", -0.8, 2);
    }

}
