package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.block.BlockSpellCircle;
import com.panzareon.spellcircles.init.ModBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemChalk extends SpellCirclesItem
{
    public ItemChalk()
    {
        super();
        this.setUnlocalizedName("chalk");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(side == EnumFacing.UP)
        {
            worldIn.setBlockState(pos.up(), ModBlocks.spellCircle.getDefaultState());
        }
        return false;
    }
}
