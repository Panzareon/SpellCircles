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
        if(itemStackIn.getTagCompound() != null)
        {
            if(itemStackIn.getTagCompound().hasKey("spellcircles"))
            {
                NBTTagCompound nbt = (NBTTagCompound) itemStackIn.getTagCompound().getTag("spellcircles");
                if(nbt.hasKey("spell"))
                {
                    String spell = nbt.getString("spell");
                    SpellEnviron environ = new SpellEnviron(spell);
                    environ.cast();
                }
            }
            //get spell from nbt
        }
        return itemStackIn;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {

    }
}
