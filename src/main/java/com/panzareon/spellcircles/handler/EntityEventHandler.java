package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.Potion.PotionEnhancer;
import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityEventHandler
{
    public static void init()
    {
        EntityEventHandler events = new EntityEventHandler();
        MinecraftForge.EVENT_BUS.register(events);
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
        if(event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound nbt = player.getEntityData();
            if(!nbt.hasKey(Reference.MOD_ID))
            {
                NBTTagCompound scNBT = new NBTTagCompound();
                scNBT.setInteger("Aura", ConfigurationHandler.maxAura);
                nbt.setTag(Reference.MOD_ID, scNBT);
            }
        }
    }
    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if(event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound nbt = player.getEntityData();
            if(nbt.hasKey(Reference.MOD_ID))
            {
                NBTTagCompound scNBT = (NBTTagCompound) nbt.getTag(Reference.MOD_ID);
                int newAura = scNBT.getInteger("Aura");
                newAura += ConfigurationHandler.auraRegen;
                if(newAura > ConfigurationHandler.maxAura)
                    newAura = ConfigurationHandler.maxAura;
                scNBT.setInteger("Aura", newAura);
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        Entity sourceEntity = event.source.getEntity();
        if(sourceEntity != null)
        {
            if(sourceEntity instanceof EntityLivingBase)
            {
                EntityLivingBase livingEntity = (EntityLivingBase) sourceEntity;
                if(livingEntity.getHeldItem() == null && livingEntity.getDistanceToEntity(sourceEntity) < 5.0f)
                {
                    if(livingEntity.isPotionActive(ModPotions.enhanceBareFist))
                    {
                        PotionEffect effect = livingEntity.getActivePotionEffect(ModPotions.enhanceBareFist);
                        event.ammount += effect.getAmplifier();
                        Vec3 hitDirectionNormalized;
                        if(livingEntity instanceof EntityPlayer)
                        {
                            hitDirectionNormalized = livingEntity.getLookVec();
                        }
                        else
                        {
                            Vec3 pos = livingEntity.getPositionVector();
                            Vec3 posEntity1 = new Vec3(pos.xCoord, pos.yCoord + livingEntity.getEyeHeight()/2, pos.zCoord);
                            pos = event.entityLiving.getPositionVector();
                            Vec3 posEntity2 = new Vec3(pos.xCoord, pos.yCoord + event.entityLiving.getEyeHeight()/2, pos.zCoord);
                            hitDirectionNormalized = posEntity2.subtract(posEntity1);
                        }
                        hitDirectionNormalized.normalize();
                        Vec3 hitDirection = VectorUtil.multiplyVector(hitDirectionNormalized, event.ammount / 2.0f);
                        event.entityLiving.addVelocity(hitDirection.xCoord, hitDirection.yCoord, hitDirection.zCoord);
                        livingEntity.removePotionEffect(ModPotions.enhanceBareFist.getId());
                        //Todo: add get hit particles
                    }
                }
            }
        }
        else if(event.source.damageType == "fall")
        {
            if(event.entityLiving.isPotionActive(ModPotions.enhanceFoot))
            {
                if(event.ammount <= 2)
                {
                    event.setCanceled(true);
                }
                else
                {
                    event.ammount -= 2;
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJump(LivingEvent.LivingJumpEvent event)
    {
        if(event.entityLiving.isPotionActive(ModPotions.enhanceFoot))
        {
            event.entityLiving.addVelocity(0.0, 0.2, 0.0);
        }
    }
}
