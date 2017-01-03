package com.panzareon.spellcircles.block;

import com.panzareon.spellcircles.SpellCircles;
import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSpellCircle extends SpellCirclesBlock implements ITileEntityProvider
{
    AxisAlignedBB selectedBB;

    public BlockSpellCircle()
    {
        super();
        this.setUnlocalizedName("spell_circle");
        selectedBB = new AxisAlignedBB(0.0f, 0.0f, 0.0f , 1.0f, 0.015f, 1.0f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        TileEntitySpellCircle te = (TileEntitySpellCircle) worldIn.getTileEntity(pos);
        return new AxisAlignedBB((double)pos.getX() + selectedBB.minX - te.radius + 1, (double)pos.getY() + selectedBB.minY, (double)pos.getZ() + selectedBB.minZ - te.radius + 1, (double)pos.getX() + selectedBB.maxX + te.radius - 1, (double)pos.getY() + selectedBB.maxY, (double)pos.getZ() + selectedBB.maxZ + te.radius - 1);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
        ItemStack stack = playerIn.getHeldItemMainhand();
        boolean openGui = true;
        if (stack != null)
        {
            Item item = stack.getItem();
            if (item instanceof ItemSpell)
            {
                TileEntitySpellCircle tileEntity = (TileEntitySpellCircle) worldIn.getTileEntity(pos);
                tileEntity.craft(playerIn);
                openGui = false;
            }
        }
        if (openGui)
        {
            if(worldIn.isRemote)
            {
                playerIn.openGui(SpellCircles.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySpellCircle();
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!worldIn.isRemote)
        {
            if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP))
            {
                worldIn.destroyBlock(pos, false);
            }
        }
    }
}
