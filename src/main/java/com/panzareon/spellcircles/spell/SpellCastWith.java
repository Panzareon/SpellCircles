package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.entity.EntitySpellCast;
import com.panzareon.spellcircles.item.ItemSpell;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class SpellCastWith
{
    private ItemStack item;
    private Entity entity;

    public SpellCastWith(ItemStack i)
    {
        item = i;
    }
    public SpellCastWith(Entity e)
    {
        entity = e;
    }
    public boolean isItem()
    {
        return item != null;
    }

    public boolean isEntity()
    {
        return entity != null;
    }

    public Entity getEntity()
    {
        return entity;
    }
    public ItemStack getItem()
    {
        return item;
    }

    public void setSpellToCall(SpellEnviron environ, int ticks)
    {
        if(item != null)
        {
            ((ItemSpell)item.getItem()).setSpellToCall(item,environ, ticks);
        }
        if(entity != null)
        {
            ((EntitySpellCast)entity).setSpellToCall(environ, ticks);
        }
    }
}
