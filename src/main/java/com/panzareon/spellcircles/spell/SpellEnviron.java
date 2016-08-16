package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.entity.EntitySpellCast;
import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.handler.EntityEventHandler;
import com.panzareon.spellcircles.init.ModNetwork;
import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.network.PlayerAuraMessage;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.LogHelper;
import com.panzareon.spellcircles.utility.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public class SpellEnviron
{
    public Vec3 castPos;
    protected EntityPlayer caster;
    public SpellCastWith castWith;
    public Entity entityHit;
    public BlockPos blockHit;
    public int numberOfCasts;
    public float strength;

    public int chargedAura = 0;
    protected ArrayList<SpellPart> spells;
    int nextSpaceIndex = 0;

    public SpellEnviron()
    {
        spells = new ArrayList<SpellPart>();
    }
    public SpellEnviron(String spell)
    {
        spells = new ArrayList<SpellPart>();
        if(spell != null && !spell.isEmpty())
            addSpellPart(spell);
    }
    public SpellEnviron(SpellPart spell)
    {
        spells = new ArrayList<SpellPart>();
        if(spell != null)
            addSpellPart(spell);
    }

    public void setEnvironVariables(SpellEnviron se)
    {
        se.entityHit = entityHit;
        se.blockHit = blockHit;
        se.castWith = castWith;
        se.caster = caster;
        se.castPos = castPos;
        se.chargedAura = chargedAura;
    }

    public void setCaster(EntityPlayer casterEntity)
    {
        caster = casterEntity;
    }
    public void addSpellPart(String spell)
    {
        String[] SpellPartsString = spell.split(" ");
        for (String aSpellPartsString : SpellPartsString)
        {
            SpellPart part = SpellList.getSpellPart(aSpellPartsString);
            if (part != null)
            {
                part.setEnviron(this);
                while (true)
                {
                    if (spells.size() <= nextSpaceIndex)
                    {
                        spells.add(nextSpaceIndex, part);
                        break;
                    }
                    if (spells.get(nextSpaceIndex).addChild(part))
                    {
                        break;
                    }
                    nextSpaceIndex++;
                }
            } else
            {
                LogHelper.warn("No Spellpart with Name " + aSpellPartsString);
            }
        }
    }
    public void addSpellPart(SpellPart part)
    {
        part.setEnviron(this);
        while(true)
        {
            if(spells.size() <= nextSpaceIndex)
            {
                spells.add(nextSpaceIndex, part);
                break;
            }
            if(spells.get(nextSpaceIndex).addChild(part))
            {
                break;
            }
            nextSpaceIndex++;
        }
    }

    public void cast() throws MissingAuraException
    {
        if(caster != null)
        {
            try {
                for (SpellPart spell : spells) {
                    spell.cast();
                }
            }
            catch (MissingAuraException ex)
            {
                LogHelper.info("Not enough Aura for Spellpart: " + ex.spellPart.getSpellId());
                throw ex;
            }
        }
    }

    public EntityPlayer getCaster()
    {
        return caster;
    }

    public boolean useAura(int amount, float strength)
    {
        strength += 9;
        amount *= 10 / strength;
        if(chargedAura >= amount)
        {
            chargedAura -= amount;
            return true;
        }
        else
        {
            amount -= chargedAura;
            chargedAura = 0;
        }
        NBTTagCompound nbt = caster.getEntityData();
        if(!nbt.hasKey(Reference.MOD_ID))
        {
            EntityEventHandler.initPlayer(caster);
        }
        NBTTagCompound scNBT = nbt.getCompoundTag(Reference.MOD_ID);
        int Aura = scNBT.getInteger("Aura");
        if(Aura >= amount)
        {
            scNBT.setInteger("Aura", Aura - amount);
            if(!caster.worldObj.isRemote)
            {
                ModNetwork.network.sendTo(new PlayerAuraMessage(Aura - amount), (EntityPlayerMP) caster);
            }
            return true;
        }
        else
        {
            scNBT.setInteger("Aura", 0);
            if(!caster.worldObj.isRemote)
            {
                ModNetwork.network.sendTo(new PlayerAuraMessage(0), (EntityPlayerMP) caster);
            }
            return false;
        }
    }

    public boolean isFinished()
    {
        if(spells.size() == 0)
            return false;
        return spells.get(nextSpaceIndex).isFinished();
    }
    public String getSpellString()
    {
        String ret = null;
        for(SpellPart spell: spells)
        {
            if(ret == null)
                ret = spell.getSpellString();
            else
                ret += " " + spell.getSpellString();
        }
        return ret;
    }

    public SpellPart getLastNodeWithSpace()
    {
        if(spells.size() == 0)
            return null;
        SpellPart lastSpell = spells.get(spells.size()-1);
        if(lastSpell == null)
            return null;
        return lastSpell.getLastNodeWithSpace();
    }

    public Vec3 getCastPosition()
    {
        if(castPos == null)
        {
            if(castWith.isEntity())
            {
                return castWith.getEntity().getPositionVector();
            }
            else
            {
                return caster.getPositionVector();
            }
        }
        return castPos;
    }

    public void removeFromCastOrigin(EntitySpellCast e)
    {
        //Todo: if not created from equipped item of caster: remove from other origin
        ItemStack stack = caster.getCurrentEquippedItem();
        if(stack.getItem() instanceof ItemSpell)
        {
            if(stack.hasTagCompound())
            {
                if(stack.getTagCompound().hasKey(Reference.MOD_ID))
                {
                    SpellHelper.removeFromEntityList(stack.getTagCompound().getCompoundTag(Reference.MOD_ID), e);
                }
            }
        }
    }
    public void addToCastOrigin(EntitySpellCast e)
    {
        //Todo: if not created from equipped item of caster: remove from other origin
        ItemStack stack = caster.getCurrentEquippedItem();
        if(stack.getItem() instanceof ItemSpell)
        {
            if(stack.hasTagCompound())
            {
                if(stack.getTagCompound().hasKey(Reference.MOD_ID))
                {
                    SpellHelper.addToEntityList(stack.getTagCompound().getCompoundTag(Reference.MOD_ID), e);
                }
            }
        }
    }

    public boolean originStillCasting(EntitySpellCast entitySpellCast)
    {
        //Todo: if not created from equipped item of caster: remove from other origin
        ItemStack stack = caster.getCurrentEquippedItem();
        if(stack.getItem() instanceof ItemSpell)
        {
            if (stack.hasTagCompound())
            {
                if (stack.getTagCompound().hasKey(Reference.MOD_ID))
                {
                    return SpellHelper.isStillCasting(stack.getTagCompound().getCompoundTag(Reference.MOD_ID), entitySpellCast);
                }
            }
        }
        return false;
    }
}
