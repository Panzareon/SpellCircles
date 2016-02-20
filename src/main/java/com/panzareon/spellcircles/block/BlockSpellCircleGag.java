package com.panzareon.spellcircles.block;

import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircleGag;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSpellCircleGag extends  SpellCirclesBlock implements ITileEntityProvider
{
    public BlockSpellCircleGag(){
        this.setUnlocalizedName("spell_circle");
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.015f, 1.0f);
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
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
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
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)     {
        TileEntitySpellCircleGag te = (TileEntitySpellCircleGag) worldIn.getTileEntity(pos);
        if(te != null && te.primaryPos != null)
        {
            TileEntitySpellCircle primaryTe = (TileEntitySpellCircle) worldIn.getTileEntity(te.primaryPos);
            if(primaryTe != null)
            {
                return ModBlocks.spellCircle.getSelectedBoundingBox(worldIn, primaryTe.getPos());
            }
        }
        return super.getSelectedBoundingBox(worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
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
                return ModBlocks.spellCircle.onBlockActivated(worldIn, primaryPos, state, playerIn, side, hitX, hitY, hitZ);
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean isFullCube()
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
