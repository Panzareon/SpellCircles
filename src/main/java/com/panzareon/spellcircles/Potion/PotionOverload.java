package com.panzareon.spellcircles.Potion;


import com.panzareon.spellcircles.SpellCircles;
import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.settings.GameSettings;
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

public class PotionOverload extends PotionSpellCircles
{
    public PotionOverload(ResourceLocation location, boolean badEffect, int potionColor)
    {
        super(location, badEffect, potionColor);
        setPotionName("overload");
        registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "56c46591-f1d2-45a2-ad65-2308a91fb0dd", 1.0, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "132d3943-93a3-49d5-8b78-f1d88de21991", 2.0, 2);
        registerPotionAttributeModifier(SharedMonsterAttributes.knockbackResistance, "cfcb323f-e0dc-49e5-8287-cf6a43f1944d", 1.5, 2);
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
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap p_111187_2_, int amplifier)
    {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, p_111187_2_, amplifier);

        //add exhaust Potion effect
        PotionEffect overloadEffect = new PotionEffect(ModPotions.overloadExhaust.getId(), 1200, 0);
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

                PotionEffect effect = new PotionEffect(ModPotions.auraExhaust.getId(), 1200, 0);
                effect.setCurativeItems(new ArrayList<ItemStack>());
                caster.addPotionEffect(effect);
            }
        }
    }
}
