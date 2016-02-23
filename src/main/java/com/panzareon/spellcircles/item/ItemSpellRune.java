package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellCastWith;
import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSpellRune extends ItemSpell
{
    public ItemSpellRune()
    {
        super();
        this.setUnlocalizedName("spell_rune");
        this.maxStackSize = 1;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        SpellEnviron environ = super.getEnvironFromNBT(stack, worldIn);
        if(environ != null)
        {
            environ.setCaster(playerIn);
            environ.castWith = new SpellCastWith(stack);
            environ.blockHit = pos;
            try
            {
                environ.cast();
            }
            catch(MissingAuraException ex)
            {
                //NOOP
            }
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        SpellEnviron environ = super.getEnvironFromNBT(itemStackIn, worldIn);
        if(environ != null)
        {
            environ.setCaster(playerIn);
            environ.castWith = new SpellCastWith(itemStackIn);
            try
            {
                environ.cast();
            }
            catch(MissingAuraException ex)
            {
                //NOOP
            }
        }
        return itemStackIn;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
    {
        SpellEnviron environ = super.getEnvironFromNBT(stack, playerIn.worldObj);
        if(environ != null)
        {
            environ.setCaster(playerIn);
            environ.castWith = new SpellCastWith(stack);
            environ.entityHit = target;
            try
            {
                environ.cast();
            }
            catch(MissingAuraException ex)
            {
                //NOOP
            }
        }
        return true;
    }
}
