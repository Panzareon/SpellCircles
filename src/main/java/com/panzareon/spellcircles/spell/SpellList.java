package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.utility.LogHelper;

import java.util.HashMap;

public class SpellList
{
    private static HashMap<String, SpellPart> spells = new HashMap<String, SpellPart>();

    public static void registerSpell(SpellPart part)
    {
        String name = part.getSpellName();
        spells.put(name, part);
    }

    public static SpellPart getSpellPart(String name)
    {
        String[] nameParts = name.split(":");
        SpellPart ret = null;
        try
        {
            ret = spells.get(nameParts[0]).getClass().newInstance();
        }
        catch (Exception e)
        {
            LogHelper.error(e);
        }
        if(nameParts.length > 1)
        {
            ret.additionalValues(nameParts[1]);
        }
        return ret;
    }
}
