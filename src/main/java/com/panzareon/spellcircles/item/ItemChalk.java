package com.panzareon.spellcircles.item;

import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircleGag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
            int y = pos.getY() + 1;
            int yDown = pos.getY();
            //Todo: check max space / size
            IBlockState state;
            int radius = 0;
            boolean increase = true;
            Block lapis = GameRegistry.findBlock("minecraft", "lapis_block");
            Block stone = GameRegistry.findBlock("minecraft", "stonebrick");
            //check if 3x3 fits
            for(int x = pos.getX() - 1; x <= pos.getX() + 1; x++)
            {
                for(int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++)
                {
                    if(!worldIn.isAirBlock(new BlockPos(x, y, z)))
                    {
                        increase = false;
                    }
                    state = worldIn.getBlockState(new BlockPos(x, yDown, z));
                    boolean xSame = x == pos.getX();
                    boolean zSame = z == pos.getZ();

                    if(xSame ^ zSame)
                    {
                        if(state.getBlock() != lapis)
                        {
                            increase = false;
                        }
                    }
                    if(!xSame && !zSame)
                    {
                        if(state.getBlock() != stone || state.getValue(BlockStoneBrick.VARIANT) != BlockStoneBrick.EnumType.CHISELED)
                        {
                            increase = false;
                        }
                    }
                }
            }
            if(increase)
            {
                radius = 1;
                //Todo: check if bigger Spellcircle possible

            }

            int damage = radius * 2 + 1;
            damage = damage * damage;
            stack.damageItem(damage, playerIn);

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
