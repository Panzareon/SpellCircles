package com.panzareon.spellcircles.potion;


import com.panzareon.spellcircles.SpellCircles;
import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.UUID;

public class PotionOverload extends PotionSpellCircles
{
    public PotionOverload(boolean badEffect, int potionColor)
    {
        super( badEffect, potionColor);
        setPotionName("overload");
        registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "56c46591-f1d2-45a2-ad65-2308a91fb0dd", 1.0, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "132d3943-93a3-49d5-8b78-f1d88de21991", 2.0, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, "cfcb323f-e0dc-49e5-8287-cf6a43f1944d", 1.5, 2);
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

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);

        //add exhaust potion effect
        PotionEffect overloadEffect = new PotionEffect(ModPotions.overloadExhaust, 1200, 0);
        overloadEffect.setCurativeItems(new ArrayList<ItemStack>());
        entityLivingBaseIn.addPotionEffect(overloadEffect);

        NBTTagCompound nbt = entityLivingBaseIn.getEntityData();
        if(nbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNBT = nbt.getCompoundTag(Reference.MOD_ID);
            if(scNBT.hasKey("overloadCaster"))
            {
                NBTTagCompound casterId = scNBT.getCompoundTag("overloadCaster");
                long casterIdLS = casterId.getLong("casterLS");
                long casterIdMS = casterId.getLong("casterMS");
                UUID casterUUID = new UUID(casterIdMS, casterIdLS);

                EntityPlayer caster = entityLivingBaseIn.worldObj.getPlayerEntityByUUID(casterUUID);

                PotionEffect effect = new PotionEffect(ModPotions.auraExhaust, 1200, 0);
                effect.setCurativeItems(new ArrayList<ItemStack>());
                caster.addPotionEffect(effect);
            }
        }
    }
}
