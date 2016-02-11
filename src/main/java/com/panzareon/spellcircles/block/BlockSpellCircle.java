package com.panzareon.spellcircles.block;

import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockSpellCircle extends SpellCirclesBlock implements ITileEntityProvider
{
    public BlockSpellCircle()
    {
        super();
        this.setUnlocalizedName("spell_circle");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getCurrentEquippedItem();
        if(stack != null)
        {
            Item item = stack.getItem();
            if(item instanceof ItemSpell)
            {
                TileEntitySpellCircle tileEntity = (TileEntitySpellCircle) worldIn.getTileEntity(pos);
                ((ItemSpell) item).setSpellString(stack, tileEntity.getEnviron().getSpellString());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySpellCircle();
    }
}
