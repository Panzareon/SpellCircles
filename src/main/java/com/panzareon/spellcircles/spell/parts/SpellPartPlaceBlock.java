package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpellPartPlaceBlock extends SpellPart
{
    private final float AuraUse = 50f;

    @Override
    public String getSpellName()
    {
        return "YKGB";
    }

    @Override
    public String getSpellId()
    {
        return "place_block";
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
            BlockPos blockPos;
            EntityPlayer player = environ.getCaster();
            Vec3d castPos = environ.getCastPosition();
            float auraAdd;
            World world = player.getEntityWorld();

            for(int i = 0; i < nr; i++)
            {
                blockPos = childValues[0].getBlock(i);
                if(blockPos == null)
                    continue;
                if(world.canBlockBePlaced(ModBlocks.placingDefault, blockPos, true, EnumFacing.UP, player, null))
                {
                    auraAdd = (float) castPos.squareDistanceTo(new Vec3d(blockPos));
                    if(environ.useAura((int) (AuraUse + auraAdd), environ.strength))
                    {
                        world.setBlockState(blockPos, ModBlocks.placingDefault.getDefaultState());
                    }
                    else
                    {
                        throw new MissingAuraException(this);
                    }
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
