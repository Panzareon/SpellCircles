package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpellPartBreakBlock extends SpellPart
{
    private final float AuraUse = 15f;

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
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
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
            EntityPlayer player = environ.getCaster();
            Vec3d castPos = environ.getCastPosition();
            float auraAdd;
            World world = player.getEntityWorld();

            for(int i = 0; i < nr; i++)
            {
                blockPos = childValues[0].getBlock(i);
                if(blockPos == null || world.isAirBlock(blockPos))
                    continue;
                blockState = world.getBlockState(blockPos);
                block = blockState.getBlock();
                blockHardness = block.getBlockHardness(blockState, world,blockPos);

                if(block.getHarvestLevel(blockState) >= environ.strength)
                {
                    auraAdd = (float) castPos.distanceTo(new Vec3d(blockPos));
                    if (environ.useAura((int) ((AuraUse + auraAdd) * blockHardness), 1))
                    {
                        world.destroyBlock(blockPos, true);
                    }
                    else
                    {
                        throw new MissingAuraException(this);
                    }
                }
                else
                {
                    //TODO: sound?
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
