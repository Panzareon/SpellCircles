package com.panzareon.spellcircles.spell;

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

    public void setSpellToCall(SpellEnviron environ, int ticks)
    {
        if(item != null)
        {
            ((ItemSpell)item.getItem()).setSpellToCall(item,environ, ticks);
        }
        if(entity != null)
        {
            ((ItemSpell)item.getItem()).setSpellToCall(item,environ, ticks);
        }
    }
}
