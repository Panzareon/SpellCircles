package com.panzareon.spellcircles.block;

import com.panzareon.spellcircles.creativetab.CreativeTabSpellCircles;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class SpellCirclesBlock extends Block
{
    public SpellCirclesBlock(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTabSpellCircles.SPELL_CIRCLES_TAB);
    }
    public SpellCirclesBlock()
    {
        this(Material.rock);
    }


    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s:%s", Reference.MOD_ID.toLowerCase(),getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }


    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
