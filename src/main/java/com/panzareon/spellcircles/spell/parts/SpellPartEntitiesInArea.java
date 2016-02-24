package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;

public class SpellPartEntitiesInArea extends SpellPart
{

    @Override
    public String getSpellName() {
        return "ZTFGHJKLDF";
    }

    @Override
    public String getSpellId() {
        return "entities_in_area";
    }

    @Override
    public int getNrOfChildren() {
        return 2;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        return new SpellReturnTypes[]{SpellReturnTypes.ENTITY};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues)
    {
        //Get Value
        HashSet<Entity> match = new HashSet<Entity>();
        int nr = childValues[0].getPositionLength();
        int nr2 = childValues[1].getNumberLength();
        if(nr > 0 && nr2 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            Vec3 pos;
            float distance;
            AxisAlignedBB bb;
            World world = environ.getCaster().worldObj;

            for(int i = 0; i < nr; i++)
            {
                pos = childValues[0].getPosition(i);
                distance = childValues[1].getNumber(i);
                bb = new AxisAlignedBB(pos.xCoord - distance, pos.yCoord - distance, pos.zCoord - distance, pos.xCoord + distance, pos.yCoord + distance, pos.zCoord + distance);

                match.addAll(world.getEntitiesWithinAABB(Entity.class, bb));
            }
        }

        SpellPartValue ret = new SpellPartValue();
        ret.setEntity(match.toArray(new Entity[match.size()]));
        return ret;
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.POSITION;
        }
        else if(childId == 1)
        {
            return SpellReturnTypes.NUMBER;
        }
        return super.getChildType(childId);
    }
}
