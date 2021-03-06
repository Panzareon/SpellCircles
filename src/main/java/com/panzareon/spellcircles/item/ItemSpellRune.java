package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellCastWith;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.utility.SpellHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class ItemSpellRune extends ItemSpell
{
    public ItemSpellRune()
    {
        super();
        this.setUnlocalizedName("spell_rune");
        this.setHasSubtypes(true);
        this.maxStackSize = 1;
        this.setMaxDamage(0);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        SpellEnviron environ = super.getEnvironFromNBT(stack, worldIn);
        if(environ != null)
        {
            //set strength for rune types
            environ.strength = 1 + stack.getItemDamage();
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
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(itemStackIn.hasTagCompound() && itemStackIn.getTagCompound().hasKey(Reference.MOD_ID))
        {
            if (!SpellHelper.isStillCasting(itemStackIn.getTagCompound().getCompoundTag(Reference.MOD_ID)))
            {
                SpellEnviron environ = super.getEnvironFromNBT(itemStackIn, worldIn);
                if (environ != null)
                {
                    environ.strength = 1 + itemStackIn.getItemDamage();
                    environ.setCaster(playerIn);
                    environ.castWith = new SpellCastWith(itemStackIn);
                    try
                    {
                        environ.cast();
                    } catch (MissingAuraException ex)
                    {
                        //NOOP
                    }
                }
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS,itemStackIn);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
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

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for(int i = 0; i < 3; i++)
        {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return this.getUnlocalizedName() + "_" + itemStack.getItemDamage();
    }


}
