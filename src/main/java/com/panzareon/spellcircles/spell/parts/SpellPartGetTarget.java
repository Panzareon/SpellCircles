package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

public class SpellPartGetTarget extends SpellPart
{

    @Override
    public String getSpellName()
    {
        return "FL";
    }

    @Override
    public String getSpellId()
    {
        return "get_target";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.BLOCK, SpellReturnTypes.ENTITY};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        SpellPartValue ret = new SpellPartValue();
        if(environ.blockHit != null)
        {
            BlockPos[] pos = {environ.blockHit};
            ret.setBlock(pos);
        }
        if(environ.entityHit != null)
        {
            Entity[] entities = {environ.entityHit};
            ret.setEntity(entities);
        }
        return ret;
    }

}
