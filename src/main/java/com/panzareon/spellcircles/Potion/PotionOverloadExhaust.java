package com.panzareon.spellcircles.Potion;


import com.panzareon.spellcircles.SpellCircles;
import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.UUID;

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
