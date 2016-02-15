package com.panzareon.spellcircles.block;

import java.util.Random;

public class BlockPlacingDefault extends SpellCirclesBlock
{
    public BlockPlacingDefault()
    {
        super();
        this.setUnlocalizedName("placing_default");
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

}
