package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.block.BlockSpellCircle;
import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircleGag;
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
        this.setHasSubtypes(true);
        this.setMaxDamage(64);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(side == EnumFacing.UP)
        {
            stack.damageItem(1, playerIn);
            int y = pos.getY() + 1;
            //Todo: check max space / size
            int radius = 1;


            TileEntitySpellCircleGag teGag;
            for(int x = pos.getX() - radius; x <= pos.getX() + radius; x++)
            {
                for(int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++)
                {
                    BlockPos posAt = new BlockPos(x,y,z);
                    if(x != pos.getX() || z != pos.getZ())
                    {
                        worldIn.setBlockState(posAt, ModBlocks.spellCircleGag.getDefaultState());
                        teGag = (TileEntitySpellCircleGag) worldIn.getTileEntity(posAt);
                        teGag.primaryPos = pos.up();
                    }
                    else
                    {
                        worldIn.setBlockState(posAt, ModBlocks.spellCircle.getDefaultState());
                        TileEntitySpellCircle te = (TileEntitySpellCircle) worldIn.getTileEntity(posAt);
                        te.radius = radius + 1;
                    }
                }
            }

        }
        return false;
    }
}
