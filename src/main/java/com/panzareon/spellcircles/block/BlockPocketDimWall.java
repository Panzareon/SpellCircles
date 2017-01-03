package com.panzareon.spellcircles.block;

import net.minecraft.block.material.Material;


public class BlockPocketDimWall extends SpellCirclesBlock
{
    public BlockPocketDimWall()
    {
        super(Material.BARRIER);
        this.setUnlocalizedName("pocket_dim_wall");
        this.setBlockUnbreakable();
    }


}
