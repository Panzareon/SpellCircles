package com.panzareon.spellcircles.block;


import com.panzareon.spellcircles.SpellCircles;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDiscoverer extends SpellCirclesBlock
{
    public BlockDiscoverer()
    {
        super();
        this.setUnlocalizedName("discoverer");
        this.setHardness(2.0F);
        this.setResistance(2.0F);
        this.setHarvestLevel("axe",-1);
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
        {
            playerIn.openGui(SpellCircles.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }


}
