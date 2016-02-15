package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.parts.*;

public class ModSpell
{
    public static void init()
    {
        //Constants
        SpellList.registerSpell(new SpellPartCaster());
        SpellList.registerSpell(new SpellPartConstant());
        SpellList.registerSpell(new SpellPartDirectionSouth());
        SpellList.registerSpell(new SpellPartDirectionDown());
        SpellList.registerSpell(new SpellPartDirectionEast());
        SpellList.registerSpell(new SpellPartDirectionNorth());
        SpellList.registerSpell(new SpellPartDirectionUp());
        SpellList.registerSpell(new SpellPartDirectionWest());

        //Operations

        //Actions
        SpellList.registerSpell(new SpellPartDamage());
        SpellList.registerSpell(new SpellPartMotion());
        SpellList.registerSpell(new SpellPartStopMotion());
        SpellList.registerSpell(new SpellPartStopMotionInDirection());
    }
}
