package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class SpellPartSameBlocks extends SpellPart
{

    @Override
    public String getSpellName()
    {
        return "FGHGVBM";
    }

    @Override
    public String getSpellId()
    {
        return "same_blocks";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.BLOCK};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        LinkedHashSet<BlockPos> positions = new LinkedHashSet<BlockPos>();
        int nr = childValues[0].getBlockLength();
        BlockPos pos;
        IBlockState state;
        World world = environ.getCaster().worldObj;
        Block compareTo;
        Block block;
        BlockPos posAtm;
        int distance;

        for(int i = 0; i < nr; i++)
        {
            pos = childValues[0].getBlock(i);
            compareTo = world.getBlockState(pos).getBlock();
            if(pos == null)
                continue;
            //Did it this way to output from the first specified Block outwards
            for(int y = 0; y < 7; y++)
            {
                posAtm = pos;
                if(y == 1)
                {
                    posAtm.east();
                }
                else if(y == 2)
                {
                    posAtm.west();
                }
                if(y == 3)
                {
                    posAtm.north();
                }
                else if(y == 4)
                {
                    posAtm.south();
                }
                if(y == 5)
                {
                    posAtm.up();
                }
                else if(y == 6)
                {
                    posAtm.down();
                }

                state = world.getBlockState(posAtm);
                if(state.getBlock() == compareTo)
                {
                    positions.add(posAtm);
                }
            }
        }

        SpellPartValue ret = new SpellPartValue();
        ret.setBlock(positions.toArray(new BlockPos[positions.size()]));
        return ret;
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.BLOCK;
        }
        return super.getChildType(childId);
    }
}
