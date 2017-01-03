package com.panzareon.spellcircles.block;

import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircleGag;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSpellCircleGag extends  SpellCirclesBlock implements ITileEntityProvider
{
    public BlockSpellCircleGag(){
        this.setUnlocalizedName("spell_circle");
    }

    //This block is called when block is broken and destroys the primary block.

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        //Reading the gag's tile entity.
        TileEntitySpellCircleGag tileEntity = (TileEntitySpellCircleGag)worldIn.getTileEntity(pos);
        //If not make this check, the game may crash if there's no tile entity at i, j, k.
        if (tileEntity != null){
            //Actually destroys primary block.
            worldIn.destroyBlock(tileEntity.primaryPos, false);
            //Forces removing tile entity from primary block coordinates,
            //cause sometimes minecraft forgets to do that.
            worldIn.removeTileEntity(tileEntity.primaryPos);
        }
        //Same as above, but for the gag block tile entity.
        worldIn.removeTileEntity(pos);
    }

    //This method checks if primary block exists.

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!worldIn.isRemote)
        {
            if (!worldIn.isSideSolid(pos.down(),EnumFacing.UP,true))
            {
                breakBlock(worldIn, pos, state);
            }
        }
        TileEntitySpellCircleGag tileEntity = (TileEntitySpellCircleGag)worldIn.getTileEntity(pos);
        if (tileEntity != null){
            //No need to check if block's Id matches the Id of our primary block,
            //because if a player want to change a block, he needs to brake it first,
            //and in this case block will be set to Air (Id = 0)
            if(worldIn.isAirBlock(tileEntity.primaryPos)){
                worldIn.destroyBlock(pos, false);
                worldIn.removeTileEntity(pos);
            }
        }
    }

    //This makes gag invisible.
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return getBoundingBox(state, worldIn, pos);
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        TileEntitySpellCircleGag te = (TileEntitySpellCircleGag) source.getTileEntity(pos);
        if(te != null && te.primaryPos != null)
        {
            TileEntitySpellCircle primaryTe = (TileEntitySpellCircle) source.getTileEntity(te.primaryPos);
            if(primaryTe != null)
            {
                return ModBlocks.spellCircle.getBoundingBox(getDefaultState(),source, primaryTe.getPos());
            }
        }
        return super.getBoundingBox(getDefaultState(), source, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntitySpellCircleGag te = (TileEntitySpellCircleGag) worldIn.getTileEntity(pos);
        if(te != null && te.primaryPos != null)
        {
            TileEntitySpellCircle primaryTe = (TileEntitySpellCircle) worldIn.getTileEntity(te.primaryPos);
            if(primaryTe != null)
            {
                BlockPos primaryPos = primaryTe.getPos();
                hitX += primaryPos.getX() - pos.getX();
                hitZ += primaryPos.getZ() - pos.getZ();
                return ModBlocks.spellCircle.onBlockActivated(worldIn, primaryPos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntitySpellCircleGag();
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }
}
