package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.Potion.PotionEnhancer;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class ModPotions
{
    public static Potion enhanceBareFist = new PotionEnhancer(new ResourceLocation(Reference.MOD_ID,"enhance_bare_fist"), false, 2445989, PotionEnhancer.EnhanceTypes.BARE_FIST);
}
