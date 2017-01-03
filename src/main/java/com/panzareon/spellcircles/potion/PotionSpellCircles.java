package com.panzareon.spellcircles.potion;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.potion.Potion;

public class PotionSpellCircles extends Potion
{
    protected PotionSpellCircles(boolean badEffect, int potionColor)
    {
        super(badEffect, potionColor);
    }

    @Override
    public Potion setPotionName(String nameIn)
    {
        return super.setPotionName(Reference.MOD_ID.toLowerCase() + ":potion." + nameIn);
    }
}
