package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpellRune extends ItemSpell
{
    public ItemSpellRune()
    {
        super();
        this.setUnlocalizedName("spell_rune");
    }


    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        SpellEnviron environ = super.getEnvironFromNBT(itemStackIn);
        if(environ != null)
        {
            environ.setCaster(playerIn);
            environ.cast();
        }
        return itemStackIn;
    }
}
