package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.creativetab.CreativeTabSpellCircles;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpellCirclesItem extends Item
{
    public SpellCirclesItem()
    {
        super();
        this.setCreativeTab(CreativeTabSpellCircles.SPELL_CIRCLES_TAB);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(),getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s:%s", Reference.MOD_ID.toLowerCase(),getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
