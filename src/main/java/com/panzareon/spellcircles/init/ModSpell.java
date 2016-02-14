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

        //Operations
        SpellList.registerSpell(new SpellPartLookingDirection());

        //Actions
        SpellList.registerSpell(new SpellPartDamage());
        SpellList.registerSpell(new SpellPartMotion());
    }
}
