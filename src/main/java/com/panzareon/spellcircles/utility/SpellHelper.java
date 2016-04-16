package com.panzareon.spellcircles.utility;

import com.panzareon.spellcircles.entity.EntitySpellCast;
import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellCastWith;
import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.UUID;

public class SpellHelper
{

    public static void resetSpellCasting(NBTTagCompound mainNBT, World worldIn)
    {
        if(mainNBT.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNbt = mainNBT.getCompoundTag(Reference.MOD_ID);
            if(scNbt.hasKey("toCastTime"))
            {
                scNbt.removeTag("toCastTime");
                scNbt.removeTag("toCast");
                scNbt.removeTag("castQueue");
            }
            if(scNbt.hasKey("entityList"))
            {
                if(!worldIn.isRemote)
                {
                    NBTTagList entityList = scNbt.getTagList("entityList", 10);
                    //Iterate backwards because entities are deleted
                    for (int i = entityList.tagCount() - 1; i >= 0; i--)
                    {
                        NBTTagCompound entity = entityList.getCompoundTagAt(i);
                        UUID entityId = new UUID(entity.getLong("IdMS"), entity.getLong("IdLS"));
                        Entity e = MinecraftServer.getServer().getEntityFromUuid(entityId);
                        if (e instanceof EntitySpellCast)
                            e.setDead();
                    }
                }
                scNbt.removeTag("entityList");
            }
        }
    }

    public static void setSpellToCall(NBTTagCompound mainNBT, SpellEnviron se, int nrOfTicks)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        SpellHelper.writeEnvironToNBT(nbt, se);
        if(!mainNBT.hasKey(Reference.MOD_ID))
        {
            mainNBT.setTag(Reference.MOD_ID, new NBTTagCompound());
        }
        NBTTagCompound scNbt = mainNBT.getCompoundTag(Reference.MOD_ID);
        //Does this item already have a spell waiting
        if(scNbt.hasKey("toCastTime"))
        {
            int otherCastTime = scNbt.getInteger("toCastTime");
            NBTTagCompound toAdd = new NBTTagCompound();
            //Does the new spell come first
            if(otherCastTime > nrOfTicks)
            {
                otherCastTime -= nrOfTicks;
                toAdd.setInteger("toCastTime", otherCastTime);
                toAdd.setTag("toCast", scNbt.getCompoundTag("toCast"));
                scNbt.setInteger("toCastTime", nrOfTicks);
                scNbt.setTag("toCast", nbt);
            }
            else
            {
                nrOfTicks -= otherCastTime;
                toAdd.setInteger("toCastTime", nrOfTicks);
                toAdd.setTag("toCast",nbt);
            }
            //create new cast Queue?
            if(scNbt.hasKey("castQueue"))
            {
                NBTTagList queue = scNbt.getTagList("castQueue", 10);
                queue.appendTag(toAdd);
            }
            else
            {
                NBTTagList queue = new NBTTagList();
                queue.appendTag(toAdd);
                scNbt.setTag("castQueue", queue);
            }
        }
        else
        {
            scNbt.setTag("toCast", nbt);
            scNbt.setInteger("toCastTime", nrOfTicks);
        }
    }

    public static void callAdditionalSpell(NBTTagCompound mainNBT, SpellCastWith castWith, World world) throws MissingAuraException
    {
        if(mainNBT.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNbt = mainNBT.getCompoundTag(Reference.MOD_ID);
            if(scNbt.hasKey("toCast"))
            {
                NBTTagCompound nbt = scNbt.getCompoundTag("toCast");
                SpellEnviron environ = SpellHelper.getEnvironFromNBT(nbt, world);
                environ.castWith = castWith;
                scNbt.removeTag("toCast");
                scNbt.removeTag("toCastTime");
                checkQueue(scNbt);
                environ.cast();
            }
        }
    }
    private static void checkQueue(NBTTagCompound scNbt)
    {
        if(scNbt.hasKey("castQueue"))
        {
            NBTTagList queue = scNbt.getTagList("castQueue", 10);
            int minQueueTime = -1;
            int queueTime;
            int minTagId = 0;
            NBTTagCompound queueTag;
            for(int i = 0; i < queue.tagCount(); i++)
            {
                queueTag = queue.getCompoundTagAt(i);
                queueTime = queueTag.getInteger("toCastTime");
                if(minQueueTime == -1 || queueTime < minQueueTime)
                {
                    minTagId = i;
                    minQueueTime = queueTime;
                }
            }
            NBTTagCompound toCast = queue.getCompoundTagAt(minTagId);
            scNbt.setInteger("toCastTime", minQueueTime);
            scNbt.setTag("toCast", toCast.getCompoundTag("toCast"));


            queue.removeTag(minTagId);
            if(queue.hasNoTags())
            {
                scNbt.removeTag("castQueue");
            }
            else
            {
                for(int i = 0; i < queue.tagCount(); i++)
                {
                    queueTag = queue.getCompoundTagAt(i);
                    queueTime = queueTag.getInteger("toCastTime") - minQueueTime;
                    queueTag.setInteger("toCastTime", queueTime);
                }
            }
        }
    }
    public static void setSpellString(NBTTagCompound mainNBT, String spell)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("spell", spell);
        if(mainNBT == null)
        {
            mainNBT = new NBTTagCompound();
        }
        mainNBT.setTag(Reference.MOD_ID, nbt);
    }

    public static SpellEnviron getEnvironFromNBT(NBTTagCompound nbt, World world)
    {
        if(nbt.hasKey("spell"))
        {
            String spell = nbt.getString("spell");
            SpellEnviron environ = new SpellEnviron(spell);
            if(nbt.hasKey("castPos"))
            {
                NBTTagCompound pos = nbt.getCompoundTag("castPos");
                double posX = pos.getDouble("xPos");
                double posY = pos.getDouble("yPos");
                double posZ = pos.getDouble("zPos");
                environ.castPos = new Vec3(posX, posY, posZ);
            }
            if(nbt.hasKey("blockHit"))
            {
                NBTTagCompound pos = nbt.getCompoundTag("blockHit");
                int posX = pos.getInteger("xPos");
                int posY = pos.getInteger("yPos");
                int posZ = pos.getInteger("zPos");
                environ.blockHit = new BlockPos(posX, posY, posZ);
            }
            if(nbt.hasKey("entityHit"))
            {
                NBTTagCompound entityHit = nbt.getCompoundTag("entityHit");
                boolean isPlayer = entityHit.getBoolean("isPlayer");
                long casterIdLS = entityHit.getLong("IdLS");
                long casterIdMS = entityHit.getLong("IdMS");
                UUID entityHitId = new UUID(casterIdMS, casterIdLS);
                if(isPlayer)
                    environ.entityHit = world.getPlayerEntityByUUID(entityHitId);
                else
                    environ.entityHit = MinecraftServer.getServer().getEntityFromUuid(entityHitId);
            }
            if(nbt.hasKey("numberOfCasts"))
            {
                environ.numberOfCasts = nbt.getInteger("numberOfCasts");
            }
            if(nbt.hasKey("casterLS"))
            {
                long casterIdLS = nbt.getLong("casterLS");
                long casterIdMS = nbt.getLong("casterMS");
                UUID casterId = new UUID(casterIdMS, casterIdLS);
                EntityPlayer caster = world.getPlayerEntityByUUID(casterId);
                environ.setCaster(caster);
            }
            if(nbt.hasKey("stregth"))
            {
                environ.strength = nbt.getFloat("strength");
            }
            environ.chargedAura = nbt.getInteger("chargedAura");
            return environ;
        }
        return null;
    }

    public static void writeEnvironToNBT(NBTTagCompound nbt, SpellEnviron environ)
    {
        String spell = environ.getSpellString();
        nbt.setString("spell", spell);
        if(environ.castPos != null)
        {
            NBTTagCompound pos = new NBTTagCompound();
            double posX = environ.castPos.xCoord;
            double posY = environ.castPos.yCoord;
            double posZ = environ.castPos.zCoord;
            pos.setDouble("xPos", posX);
            pos.setDouble("yPos", posY);
            pos.setDouble("zPos", posZ);
            nbt.setTag("castPos", pos);
        }
        UUID casterId = environ.getCaster().getPersistentID();
        nbt.setLong("casterLS", casterId.getLeastSignificantBits());
        nbt.setLong("casterMS", casterId.getMostSignificantBits());
        if(environ.blockHit != null)
        {
            NBTTagCompound pos = new NBTTagCompound();
            int posX = environ.blockHit.getX();
            int posY = environ.blockHit.getY();
            int posZ = environ.blockHit.getZ();
            pos.setInteger("xPos", posX);
            pos.setInteger("yPos", posY);
            pos.setInteger("zPos", posZ);
            nbt.setTag("blockHit", pos);
        }
        if(environ.entityHit != null)
        {
            NBTTagCompound entityHit = new NBTTagCompound();
            boolean isPlayer = environ.entityHit instanceof EntityPlayer;
            UUID entityHitId = environ.entityHit.getPersistentID();
            entityHit.setBoolean("isPlayer", isPlayer);
            entityHit.setLong("IdLS", entityHitId.getLeastSignificantBits());
            entityHit.setLong("IdMS", entityHitId.getMostSignificantBits());
            nbt.setTag("entityHit", entityHit);
        }
        if(environ.numberOfCasts != 0)
        {
            nbt.setInteger("numberOfCasts", environ.numberOfCasts);
        }
        if(environ.strength != 0.0f)
        {
            nbt.setFloat("strength", environ.strength);
        }
        nbt.setInteger("chargedAura", environ.chargedAura);
    }

    public static void onUpdate(NBTTagCompound mainNBT, boolean reset, SpellCastWith castWith, World world) throws MissingAuraException
    {
        if(mainNBT != null && mainNBT.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNBT = mainNBT.getCompoundTag(Reference.MOD_ID);
            if(scNBT.hasKey("toCastTime"))
            {
                if(reset)
                {
                    resetSpellCasting(mainNBT, world);
                }
                else
                {
                    int time = scNBT.getInteger("toCastTime");
                    time--;
                    scNBT.setInteger("toCastTime",time);
                    if(time <= 0)
                    {
                        callAdditionalSpell(mainNBT, castWith, world);
                    }
                }
            }
        }
    }

    public static void removeFromEntityList(NBTTagCompound nbt, EntitySpellCast e)
    {
        UUID entityId = e.getPersistentID();
        long ls = entityId.getLeastSignificantBits();
        long ms = entityId.getMostSignificantBits();
        if(nbt.hasKey("entityList"))
        {
            NBTTagList entityList = nbt.getTagList("entityList", 10);
            for(int i = 0; i < entityList.tagCount(); i++)
            {
                NBTTagCompound entity = entityList.getCompoundTagAt(i);
                if(entity.getLong("IdLS") == ls && entity.getLong("IdMS") == ms)
                {
                    entityList.removeTag(i);
                }
            }
            if(entityList.tagCount() == 0)
            {
                nbt.removeTag("entityList");
            }
        }
    }

    public static void addToEntityList(NBTTagCompound nbt, EntitySpellCast e)
    {
        UUID entityId = e.getPersistentID();
        long ls = entityId.getLeastSignificantBits();
        long ms = entityId.getMostSignificantBits();
        NBTTagList entityList;
        if(!nbt.hasKey("entityList"))
        {
            entityList = new NBTTagList();
            nbt.setTag("entityList", entityList);
        }
        else
        {
            entityList = nbt.getTagList("entityList", 10);
        }
        NBTTagCompound entity = new NBTTagCompound();
        entity.setLong("IdLS", ls);
        entity.setLong("IdMS", ms);
        entityList.appendTag(entity);
    }

    public static boolean isStillCasting(NBTTagCompound nbt)
    {
        return nbt.hasKey("toCast") || nbt.hasKey("entityList");
    }

    public static boolean isStillCasting(NBTTagCompound nbt, EntitySpellCast e)
    {
        UUID entityId = e.getPersistentID();
        long ls = entityId.getLeastSignificantBits();
        long ms = entityId.getMostSignificantBits();
        if(nbt.hasKey("entityList"))
        {
            NBTTagList entityList = nbt.getTagList("entityList", 10);
            for(int i = 0; i < entityList.tagCount(); i++)
            {
                NBTTagCompound entity = entityList.getCompoundTagAt(i);
                if(entity.getLong("IdLS") == ls && entity.getLong("IdMS") == ms)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
