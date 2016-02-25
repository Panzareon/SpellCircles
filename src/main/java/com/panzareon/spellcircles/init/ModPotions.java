package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.Potion.PotionAuraExhaust;
import com.panzareon.spellcircles.Potion.PotionEnhancer;
import com.panzareon.spellcircles.Potion.PotionOverload;
import com.panzareon.spellcircles.Potion.PotionOverloadExhaust;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;


public class ModPotions
{
    public static Potion enhanceBareFist = new PotionEnhancer(new ResourceLocation(Reference.MOD_ID,"enhance_bare_fist"), false, 2445989, PotionEnhancer.EnhanceTypes.BARE_FIST);
    public static Potion enhanceFoot = new PotionEnhancer(new ResourceLocation(Reference.MOD_ID,"enhance_foot"), false, 2435979, PotionEnhancer.EnhanceTypes.FOOT);
    public static Potion overload = new PotionOverload(new ResourceLocation(Reference.MOD_ID,"overload"), false, 2435979);
    public static Potion auraExhaust = new PotionAuraExhaust(new ResourceLocation(Reference.MOD_ID,"aura_exhaust"), true, 2435979);
    public static Potion overloadExhaust = new PotionOverloadExhaust(new ResourceLocation(Reference.MOD_ID,"overload_exhaust"), true, 2435979);
}
