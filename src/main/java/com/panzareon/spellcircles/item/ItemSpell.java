package com.panzareon.spellcircles.item;


import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellCastWith;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.utility.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class ItemSpell extends SpellCirclesItem
{

    ItemSpell()
    {
        super();
    }

    public void setSpellToCall(ItemStack stack, SpellEnviron se, int nrOfTicks)
    {
        NBTTagCompound itemNbt = stack.getTagCompound();
        SpellHelper.setSpellToCall(itemNbt, se, nrOfTicks);
    }

    public void setSpellString(ItemStack itemStackIn, String spell)
    {
        if(!itemStackIn.hasTagCompound())
        {
            itemStackIn.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound compound = itemStackIn.getTagCompound();
        SpellHelper.setSpellString(compound, spell);
    }

    protected SpellEnviron getEnvironFromNBT(ItemStack itemStackIn, World world)
    {
        NBTTagCompound mainNBT = itemStackIn.getTagCompound();
        if(mainNBT != null && mainNBT.hasKey(Reference.MOD_ID))
            return SpellHelper.getEnvironFromNBT(mainNBT.getCompoundTag(Reference.MOD_ID), world);
        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(stack.hasTagCompound())
        {
            try
            {
                SpellHelper.onUpdate(stack.getTagCompound(), !isSelected, new SpellCastWith(stack), worldIn);
            }
            catch(MissingAuraException ex)
            {
                SpellHelper.resetSpellCasting(stack.getTagCompound(), worldIn);
            }
        }
    }

    
}
