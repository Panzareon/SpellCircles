package com.panzareon.spellcircles.Potion;


import com.panzareon.spellcircles.SpellCircles;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

public class PotionOverload extends PotionSpellCircles
{
    public PotionOverload(ResourceLocation location, boolean badEffect, int potionColor)
    {
        super(location, badEffect, potionColor);
        setPotionName("overload");
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_)
    {
        SpellCircles.proxy.showOverloadParticles(entityLivingBaseIn);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_)
    {
        return p_76397_1_ % 2 == 0;
    }
}
