package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

    public void cast()
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
            }
        }
    }

    public EntityPlayer getCaster()
    {
        return caster;
    }

    public boolean useAura(int amount)
    {
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
        if(nbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNBT = nbt.getCompoundTag(Reference.MOD_ID);
            int Aura = scNBT.getInteger("Aura");
            if(Aura >= amount)
            {
                scNBT.setInteger("Aura", Aura - amount);
                return true;
            }
            else
            {
                scNBT.setInteger("Aura", 0);
                return false;
            }
        }
        return false;
    }

    public boolean isFinished()
    {
        return spells.size() != 0 && spells.get(nextSpaceIndex).isFinished();
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
            return caster.getPositionVector();
        }
        return castPos;
    }
}
