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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
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
                if(livingEntity.getHeldItem() == null)
                {
                    if(livingEntity.isPotionActive(ModPotions.enhanceBareFist))
                    {
                        PotionEffect effect = livingEntity.getActivePotionEffect(ModPotions.enhanceBareFist);
                        event.ammount += effect.getAmplifier();
                        Vec3 hitDirection;
                        if(livingEntity instanceof EntityPlayer)
                        {
                            hitDirection = livingEntity.getLookVec();
                        }
                        else
                        {
                            BlockPos pos = livingEntity.getPosition();
                            Vec3 posEntity1 = new Vec3(pos.getX(), pos.getY() + livingEntity.getEyeHeight()/2, pos.getZ());
                            pos = event.entityLiving.getPosition();
                            Vec3 posEntity2 = new Vec3(pos.getX(), pos.getY() + event.entityLiving.getEyeHeight()/2, pos.getZ());
                            hitDirection = posEntity2.subtract(posEntity1);
                        }
                        hitDirection.normalize();
                        hitDirection = VectorUtil.multiplyVector(hitDirection, event.ammount / 2.0f);
                        event.entityLiving.addVelocity(hitDirection.xCoord, hitDirection.yCoord, hitDirection.zCoord);
                        livingEntity.removePotionEffect(ModPotions.enhanceBareFist.getId());
                        //Todo: add get hit particles
                    }
                }
            }
        }
    }
}
