package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.parts.*;

public class ModSpell
{
    public static void init()
    {
        //Constants
        SpellList.registerSpell(new SpellPartCaster());
        SpellList.registerSpell(new SpellPartGetTarget());
        SpellList.registerSpell(new SpellPartConstant());
        SpellList.registerSpell(new SpellPartDirectionSouth());
        SpellList.registerSpell(new SpellPartDirectionDown());
        SpellList.registerSpell(new SpellPartDirectionEast());
        SpellList.registerSpell(new SpellPartDirectionNorth());
        SpellList.registerSpell(new SpellPartDirectionUp());
        SpellList.registerSpell(new SpellPartDirectionWest());

        //Operations
        SpellList.registerSpell(new SpellPartLookingAt());
        SpellList.registerSpell(new SpellPartExpandPositionList());
        SpellList.registerSpell(new SpellPartMovePositions());

        //Actions
        SpellList.registerSpell(new SpellPartDamage());
        SpellList.registerSpell(new SpellPartMotion());
        SpellList.registerSpell(new SpellPartStopMotion());
        SpellList.registerSpell(new SpellPartStopMotionInDirection());
        SpellList.registerSpell(new SpellPartBreakBlock());
        SpellList.registerSpell(new SpellPartPlaceBlock());

        SpellList.registerSpell(new SpellPartCharge());
        SpellList.registerSpell(new SpellPartThrowSpell());
    }
}
