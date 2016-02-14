package com.panzareon.spellcircles.item;


import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSpell extends SpellCirclesItem
{

    ItemSpell()
    {
        super();
    }

    public void setSpellString(ItemStack itemStackIn, String spell)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("spell", spell);
        NBTTagCompound compound = itemStackIn.getTagCompound();
        if(compound == null)
        {
            compound = new NBTTagCompound();
        }
        compound.setTag(Reference.MOD_ID, nbt);
        itemStackIn.setTagCompound(compound);
    }

    protected SpellEnviron getEnvironFromNBT(ItemStack itemStackIn)
    {

        if(itemStackIn.getTagCompound() != null)
        {
            if(itemStackIn.getTagCompound().hasKey(Reference.MOD_ID))
            {
                //get spell from nbt
                NBTTagCompound nbt = (NBTTagCompound) itemStackIn.getTagCompound().getTag(Reference.MOD_ID);
                if(nbt.hasKey("spell"))
                {
                    String spell = nbt.getString("spell");
                    return new SpellEnviron(spell);
                }
            }
        }
        return null;
    }
}
