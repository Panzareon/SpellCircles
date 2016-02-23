package com.panzareon.spellcircles.Potion;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionSpellCircles extends Potion
{
    protected PotionSpellCircles(ResourceLocation location, boolean badEffect, int potionColor)
    {
        super(location, badEffect, potionColor);
    }

    @Override
    public Potion setPotionName(String nameIn)
    {
        return super.setPotionName(Reference.MOD_ID.toLowerCase() + ":potion." + nameIn);
    }
}
