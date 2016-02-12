package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.parts.SpellPartCaster;
import com.panzareon.spellcircles.spell.parts.SpellPartConstant;

public class ModSpell
{
    public static final SpellPart SPELL_PART_CASTER = new SpellPartCaster();
    public static final SpellPart SPELL_PART_CONSTANT = new SpellPartConstant();

    public static void init()
    {
        SpellList.registerSpell(SPELL_PART_CASTER);
        SpellList.registerSpell(SPELL_PART_CONSTANT);
    }
}
