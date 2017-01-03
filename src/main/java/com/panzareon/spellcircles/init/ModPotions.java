package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.potion.PotionAuraExhaust;
import com.panzareon.spellcircles.potion.PotionEnhancer;
import com.panzareon.spellcircles.potion.PotionOverload;
import com.panzareon.spellcircles.potion.PotionOverloadExhaust;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;


public class ModPotions
{
    public static Potion enhanceBareFist = new PotionEnhancer(false, 2445989, PotionEnhancer.EnhanceTypes.BARE_FIST);
    public static Potion enhanceFoot = new PotionEnhancer(false, 2435979, PotionEnhancer.EnhanceTypes.FOOT);
    public static Potion overload = new PotionOverload(false, 2435979);
    public static Potion auraExhaust = new PotionAuraExhaust(true, 2435979);
    public static Potion overloadExhaust = new PotionOverloadExhaust(true, 2435979);
}
