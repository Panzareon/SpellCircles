package com.panzareon.spellcircles.handler;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerHandler
{
    public static void init()
    {
        PlayerHandler events = new PlayerHandler();
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

}
