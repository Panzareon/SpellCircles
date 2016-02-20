package com.panzareon.spellcircles.item;


import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class ItemSpell extends SpellCirclesItem
{

    ItemSpell()
    {
        super();
    }

    public void resetSpellCasting(ItemStack stack)
    {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if(itemNbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNbt = itemNbt.getCompoundTag(Reference.MOD_ID);
            if(scNbt.hasKey("toCastTime"))
            {
                scNbt.removeTag("toCastTime");
                scNbt.removeTag("toCast");
                scNbt.removeTag("castQueue");
            }
        }
    }

    public void setSpellToCall(ItemStack stack, SpellEnviron se, int nrOfTicks)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        int caster = se.getCaster().getEntityId();
        nbt.setInteger("caster", caster);
        if(se.castPos != null)
        {
            Vec3 castPos = se.castPos;
            NBTTagCompound pos = new NBTTagCompound();
            pos.setDouble("xPos", castPos.xCoord);
            pos.setDouble("yPos", castPos.yCoord);
            pos.setDouble("zPos", castPos.zCoord);
            nbt.setTag("castPos", pos);
        }
        nbt.setString("spell", se.getSpellString());
        nbt.setInteger("chargedAura", se.chargedAura);
        NBTTagCompound itemNbt = stack.getTagCompound();
        if(!itemNbt.hasKey(Reference.MOD_ID))
        {
            itemNbt.setTag(Reference.MOD_ID, new NBTTagCompound());
        }
        NBTTagCompound scNbt = itemNbt.getCompoundTag(Reference.MOD_ID);
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

    public void callAdditionalSpell(ItemStack stack, World world)
    {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if(itemNbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNbt = itemNbt.getCompoundTag(Reference.MOD_ID);
            if(scNbt.hasKey("toCast"))
            {
                NBTTagCompound nbt = scNbt.getCompoundTag("toCast");
                String spell = nbt.getString("spell");
                SpellEnviron environ = new SpellEnviron(spell);
                if(nbt.hasKey("castPos"))
                {
                    NBTTagCompound pos = nbt.getCompoundTag("castPos");
                    double posX = pos.getDouble("xPos");
                    double posY = pos.getDouble("yPos");
                    double posZ = pos.getDouble("zPos");
                    Vec3 castPos = new Vec3(posX, posY, posZ);
                    environ.castPos = castPos;
                }
                int casterId = nbt.getInteger("caster");
                Entity caster = world.getEntityByID(casterId);
                environ.setCaster((EntityLivingBase) caster);
                int chargedAura = nbt.getInteger("chargedAura");
                environ.chargedAura = chargedAura;
                environ.castItem = stack;
                scNbt.removeTag("toCast");
                scNbt.removeTag("toCastTime");
                checkQueue(scNbt);
                environ.cast();
            }
        }
    }

    private void checkQueue(NBTTagCompound scNbt)
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

    public void setSpellString(ItemStack itemStackIn, String spell)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("spell", spell);
        NBTTagCompound compound = itemStackIn.getTagCompound();
        if(compound == null)
        {
            compound = new NBTTagCompound();
        }
        compound.setTag(Reference.MOD_ID, nbt);
        itemStackIn.setTagCompound(compound);
    }

    protected SpellEnviron getEnvironFromNBT(ItemStack itemStackIn)
    {

        if(itemStackIn.getTagCompound() != null)
        {
            if(itemStackIn.getTagCompound().hasKey(Reference.MOD_ID))
            {
                //get spell from nbt
                NBTTagCompound nbt = (NBTTagCompound) itemStackIn.getTagCompound().getTag(Reference.MOD_ID);
                if(nbt.hasKey("spell"))
                {
                    String spell = nbt.getString("spell");
                    return new SpellEnviron(spell);
                }
            }
        }
        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound itemNBT = stack.getTagCompound();
            if(itemNBT.hasKey(Reference.MOD_ID))
            {
                NBTTagCompound scNBT = itemNBT.getCompoundTag(Reference.MOD_ID);
                if(scNBT.hasKey("toCastTime"))
                {
                    if(!isSelected)
                    {
                        resetSpellCasting(stack);
                    }
                    else
                    {
                        int time = scNBT.getInteger("toCastTime");
                        time--;
                        scNBT.setInteger("toCastTime",time);
                        if(time <= 0)
                        {
                            callAdditionalSpell(stack, worldIn);
                        }
                    }
                }
            }
        }
    }

    
}
