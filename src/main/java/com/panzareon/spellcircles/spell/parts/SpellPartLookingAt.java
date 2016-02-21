package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public class SpellPartLookingAt extends SpellPart
{
    private final float AuraUse = 50f;

    @Override
    public String getSpellName()
    {
        return "AZDSAIJSRE";
    }

    @Override
    public String getSpellId()
    {
        return "looking_at";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.BLOCK, SpellReturnTypes.ENTITY};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        ArrayList<Entity> entity = new ArrayList<Entity>();
        ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        EntityPlayer player = environ.getCaster();

        Entity e;
        Vec3 start;
        Vec3 look;
        Vec3 end;
        double maxLength = 30;
        int nr = childValues[0].getEntityLength();
        float distance;

        for(int i = 0; i < nr; i++)
        {
            e = childValues[0].getEntity(i);
            if(e == null)
                continue;
            start = e.getPositionEyes(1.0f);
            look = e.getLookVec();
            look = VectorUtil.multiplyVector(look, maxLength);
            end = start.add(look);
            MovingObjectPosition mop = VectorUtil.raycast(player.worldObj, start, end);
            if(mop != null)
            {
                distance = (float) mop.hitVec.squareDistanceTo(player.getPositionVector());
                if (environ.useAura((int) (AuraUse + distance)))
                {
                    if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                    {
                        blocks.add(mop.getBlockPos());
                    }
                    else
                    {
                        entity.add(mop.entityHit);
                    }
                } else
                {
                    throw new MissingAuraException(this);
                }
            }
        }

        SpellPartValue ret = new SpellPartValue();
        ret.setEntity(entity.toArray(new Entity[entity.size()]));
        ret.setBlock(blocks.toArray(new BlockPos[blocks.size()]));
        return ret;
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.ENTITY;
        }
        return super.getChildType(childId);
    }
}
