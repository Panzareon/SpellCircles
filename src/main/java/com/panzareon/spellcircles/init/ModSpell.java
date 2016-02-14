package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.parts.SpellPartCaster;
import com.panzareon.spellcircles.spell.parts.SpellPartConstant;
import com.panzareon.spellcircles.spell.parts.SpellPartDamage;

public class ModSpell
{
    public static final SpellPart SPELL_PART_CASTER = new SpellPartCaster();
    public static final SpellPart SPELL_PART_CONSTANT = new SpellPartConstant();


    public static final SpellPart SPELL_PART_DAMGAGE = new SpellPartDamage();

    public static void init()
    {
        SpellList.registerSpell(SPELL_PART_CASTER);
        SpellList.registerSpell(SPELL_PART_CONSTANT);


        SpellList.registerSpell(SPELL_PART_DAMGAGE);
    }
}
