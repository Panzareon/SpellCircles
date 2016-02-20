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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSpellCircle extends SpellCirclesBlock implements ITileEntityProvider
{
    public BlockSpellCircle()
    {
        super();
        this.setUnlocalizedName("spell_circle");
        this.setBlockBounds(0.0f, 0.0f, 0.0f , 1.0f, 0.015f, 1.0f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
    {
        TileEntitySpellCircle te = (TileEntitySpellCircle) worldIn.getTileEntity(pos);
        return new AxisAlignedBB((double)pos.getX() + this.minX - te.radius + 1, (double)pos.getY() + this.minY, (double)pos.getZ() + this.minZ - te.radius + 1, (double)pos.getX() + this.maxX + te.radius - 1, (double)pos.getY() + this.maxY, (double)pos.getZ() + this.maxZ + te.radius - 1);
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
    public int getRenderType() {
        return -1;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!worldIn.isRemote)
        {
            if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()))
            {
                breakBlock(worldIn, pos, state);
            }
        }
    }
}
