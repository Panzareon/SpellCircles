package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class SpellPartBreakBlock extends SpellPart
{
    private float AuraUse = 50f;

    @Override
    public String getSpellName()
    {
        return "HADFJI";
    }

    @Override
    public String getSpellId()
    {
        return "break_block";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        SpellReturnTypes[] ret = {SpellReturnTypes.ACTION};
        return ret;
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getBlockLength();
        if(nr > 0)
        {
            Block block;
            BlockPos blockPos;
            IBlockState blockState;
            float blockHardness;
            EntityLivingBase player = environ.getCaster();
            float auraAdd;
            World world = player.getEntityWorld();

            for(int i = 0; i < nr; i++)
            {
                blockPos = childValues[0].getBlock(i);
                if(world.isAirBlock(blockPos))
                    continue;
                blockState = world.getBlockState(blockPos);
                block = blockState.getBlock();
                blockHardness = block.getBlockHardness(world,blockPos);
                auraAdd = (float) player.getDistanceSq(blockPos);
                if(environ.useAura((int) ((AuraUse + auraAdd)*blockHardness)))
                {
                    world.destroyBlock(blockPos,true);
                }
                else
                {
                    throw new MissingAuraException(this);
                }
            }
        }
        return new SpellPartValue();
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
