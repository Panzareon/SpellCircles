package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;

public class SpellPartCaster extends SpellPart
{

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues)
    {
        //No Spell cost

        //Get Value
        SpellPartValue ret = new SpellPartValue();
        Entity[] caster;
        caster = new Entity[]{environ.getCaster()};
        ret.setEntity(caster);
        return ret;
    }
    @Override
    public String getSpellName() {
        return "KCG";
    }

    @Override
    public String getSpellId() {
        return "Caster";
    }

    @Override
    public int getNrOfChildren() {
        return 0;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        SpellReturnTypes[] ret = {SpellReturnTypes.ENTITY,SpellReturnTypes.ACTION};
        return ret;
    }


}
