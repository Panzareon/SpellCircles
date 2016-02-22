package com.panzareon.spellcircles.block;

import net.minecraft.block.material.Material;


public class BlockPocketDimWall extends SpellCirclesBlock
{
    public BlockPocketDimWall()
    {
        super(Material.barrier);
        this.setUnlocalizedName("pocket_dim_wall");
        this.setBlockUnbreakable();
    }


}
