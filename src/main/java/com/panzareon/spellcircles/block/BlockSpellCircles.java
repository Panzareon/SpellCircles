package com.panzareon.spellcircles.block;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockSpellCircles extends Block
{
    public BlockSpellCircles(Material material)
    {
        super(material);
    }
    public BlockSpellCircles()
    {
        super(Material.rock);
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
