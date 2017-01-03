package com.panzareon.spellcircles.potion;


import net.minecraft.entity.SharedMonsterAttributes;


public class PotionOverloadExhaust extends PotionSpellCircles
{
    public PotionOverloadExhaust(boolean badEffect, int potionColor)
    {
        super(badEffect, potionColor);
        setPotionName("overload_exhaust");
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "2d58f387-1f0b-495c-a041-922ccc98f8bb", -0.4, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "c3d4b955-779a-4b67-925d-6054804c8291", -0.7, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, "6e192ccf-e529-4207-94e2-7085d395f720", -0.8, 2);
    }

}
