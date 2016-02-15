package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.util.Vec3;

public class SpellPartDirectionUp extends SpellPart
{
    @Override
    public String getSpellName()
    {
        return "DU";
    }

    @Override
    public String getSpellId()
    {
        return "direction_up";
    }

    @Override
    public int getNrOfChildren()
    {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        SpellReturnTypes[] ret = {SpellReturnTypes.DIRECTION};
        return ret;
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        SpellPartValue ret = new SpellPartValue();
        Vec3[] dirs = {new Vec3(0.0f, 1.0f, 0.0f)};
        ret.setDirection(dirs);
        return ret;
    }
}
