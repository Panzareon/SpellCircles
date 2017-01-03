package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
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

    public static void initPlayer(EntityPlayer player)
    {
        NBTTagCompound nbt = player.getEntityData();
        if(!nbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNBT = new NBTTagCompound();
            scNBT.setInteger("Aura", ConfigurationHandler.maxAura);
            NBTTagList learnedSpells = new NBTTagList();
            scNBT.setTag("learnedSpells", learnedSpells);
            nbt.setTag(Reference.MOD_ID, scNBT);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            initPlayer(player);
        }
    }
    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            NBTTagCompound nbt = player.getEntityData();
            if(nbt.hasKey(Reference.MOD_ID))
            {
                NBTTagCompound scNBT = (NBTTagCompound) nbt.getTag(Reference.MOD_ID);
                int newAura;
                if(player.isPotionActive(ModPotions.auraExhaust))
                {
                    newAura = 0;
                }
                else
                {
                    newAura = scNBT.getInteger("Aura");
                    newAura += ConfigurationHandler.auraRegen;
                    if(newAura > ConfigurationHandler.maxAura)
                        newAura = ConfigurationHandler.maxAura;
                }
                scNBT.setInteger("Aura", newAura);
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        Entity sourceEntity = event.getSource().getEntity();
        if(sourceEntity != null)
        {
            if(sourceEntity instanceof EntityLivingBase)
            {
                EntityLivingBase livingEntity = (EntityLivingBase) sourceEntity;
                if(livingEntity.isPotionActive(ModPotions.enhanceBareFist))
                {
                    if(livingEntity.getActiveItemStack() == null && livingEntity.getDistanceToEntity(sourceEntity) < 5.0f)
                    {
                        PotionEffect effect = livingEntity.getActivePotionEffect(ModPotions.enhanceBareFist);
                        event.setAmount(event.getAmount() + effect.getAmplifier());
                        Vec3d hitDirectionNormalized;
                        if(livingEntity instanceof EntityPlayer)
                        {
                            hitDirectionNormalized = livingEntity.getLookVec();
                        }
                        else
                        {
                            Vec3d pos = livingEntity.getPositionVector();
                            Vec3d posEntity1 = new Vec3d(pos.xCoord, pos.yCoord + livingEntity.getEyeHeight()/2, pos.zCoord);
                            pos = event.getEntityLiving().getPositionVector();
                            Vec3d posEntity2 = new Vec3d(pos.xCoord, pos.yCoord + event.getEntityLiving().getEyeHeight()/2, pos.zCoord);
                            hitDirectionNormalized = posEntity2.subtract(posEntity1);
                        }
                        hitDirectionNormalized.normalize();
                        Vec3d hitDirection = VectorUtil.multiplyVector(hitDirectionNormalized, event.getAmount() / 2.0f);
                        event.getEntityLiving().addVelocity(hitDirection.xCoord, hitDirection.yCoord, hitDirection.zCoord);
                        livingEntity.removePotionEffect(ModPotions.enhanceBareFist);
                        //Todo: add get hit particles
                    }
                }
            }
        }
        else if(event.getSource().damageType.equals("fall"))
        {
            if(event.getEntityLiving().isPotionActive(ModPotions.enhanceFoot))
            {
                if(event.getAmount() <= 2)
                {
                    event.setCanceled(true);
                }
                else
                {
                    event.setAmount(event.getAmount() - 1);
                }
            }
        }
        if(event.getEntityLiving().isPotionActive(ModPotions.overload))
        {
            if(event.getSource().damageType.equals("fall"))
            {
                if(event.getAmount() <= 3)
                {
                    event.setCanceled(true);
                }
                else
                {
                    event.setAmount(event.getAmount() - 3);
                }
            }
            if(event.getAmount() <= 1)
            {
                event.setCanceled(true);
            }
            else
            {
                event.setAmount(event.getAmount() / 2);
            }
        }
    }

    @SubscribeEvent
    public void onEntityJump(LivingEvent.LivingJumpEvent event)
    {
        if(event.getEntityLiving().isPotionActive(ModPotions.enhanceFoot))
        {
            event.getEntityLiving().addVelocity(0.0, 0.2, 0.0);
        }
        else if(event.getEntityLiving().isPotionActive(ModPotions.overload))
        {
            event.getEntityLiving().addVelocity(0.0, 0.3, 0.0);
        }
    }
}
