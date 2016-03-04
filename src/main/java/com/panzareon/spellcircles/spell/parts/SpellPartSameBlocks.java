package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
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
        World world = environ.getCaster().worldObj;
        IBlockState compareTo;
        BlockPos posAtm;

        for(int i = 0; i < nr; i++)
        {
            pos = childValues[0].getBlock(i);
            compareTo = world.getBlockState(pos);
            if (pos == null)
                continue;
            //Did it this way to output from the first specified Block outwards
            for (int z = 0; z < 3; z++)
            {
                for (int y = 0; y < 9; y++)
                {
                    posAtm = pos;
                    if(y > 0)
                    {
                        if (y % 4 == 1 || y == 7)
                        {
                            posAtm = posAtm.east();
                        }
                        else if (y % 4 == 2 || y == 8)
                        {
                            posAtm =  posAtm.west();
                        }

                        if (y % 4 == 3 || y == 6)
                        {
                            posAtm = posAtm.north();
                        }
                        else if (y % 4 == 0 || y == 5)
                        {
                            posAtm = posAtm.south();
                        }
                    }
                    if (z == 1)
                    {
                        posAtm = posAtm.up();
                    }
                    else if (z == 2)
                    {
                        posAtm = posAtm.down();
                    }

                    if (world.getBlockState(posAtm) == compareTo)
                    {
                        positions.add(posAtm);
                    }
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
