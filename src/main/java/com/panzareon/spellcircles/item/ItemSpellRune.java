package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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
            environ.cast();
        }
        return itemStackIn;
    }
}
