package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.utility.LogHelper;

import java.util.*;

public class SpellList
{
    private static HashMap<String, SpellPart> spells = new HashMap<String, SpellPart>();
    private static EnumMap<SpellReturnTypes, HashSet<SpellPart>> spellsByReturnType = new EnumMap<SpellReturnTypes, HashSet<SpellPart>>(SpellReturnTypes.class);

    static {
        for(SpellReturnTypes type: SpellReturnTypes.values())
        {
            spellsByReturnType.put(type,new HashSet<SpellPart>());
        }
    }

    public static void registerSpell(SpellPart part)
    {
        String name = part.getSpellName();
        spells.put(name, part);
        SpellReturnTypes[] types = part.getReturnValueTypes();
        for(SpellReturnTypes type: types)
        {
            spellsByReturnType.get(type).add(part);
            if(type == SpellReturnTypes.BLOCK)
            {
                spellsByReturnType.get(SpellReturnTypes.POSITION).add(part);
            }
            else if(type == SpellReturnTypes.ENTITY)
            {
                spellsByReturnType.get(SpellReturnTypes.POSITION).add(part);
                spellsByReturnType.get(SpellReturnTypes.BLOCK).add(part);
                spellsByReturnType.get(SpellReturnTypes.DIRECTION).add(part);
            }
            else if(type == SpellReturnTypes.POSITION)
            {
                spellsByReturnType.get(SpellReturnTypes.BLOCK).add(part);
            }
        }
    }

    public static SpellPart getSpellPart(String name)
    {
        String[] nameParts = name.split(":");
        SpellPart ret = null;
        try
        {
            if(spells.get(nameParts[0]) == null)
                return null;
            ret = spells.get(nameParts[0]).getClass().newInstance();
            if(nameParts.length > 1)
            {
                ret.additionalValues(nameParts[1]);
            }
        }
        catch (Exception e)
        {
            LogHelper.error(e);
        }
        return ret;
    }
    public static SpellPart[] getSpellWithReturnType(SpellReturnTypes[] types)
    {
        HashSet<SpellPart> ret = new HashSet<SpellPart>();
        for(SpellReturnTypes type: types)
        {
            ret.addAll(spellsByReturnType.get(type));
        }
        return ret.toArray(new SpellPart[ret.size()]);
    }
    public static SpellPart[] getSpellWithReturnType(SpellReturnTypes type)
    {
        return  spellsByReturnType.get(type).toArray(new SpellPart[spellsByReturnType.get(type).size()]);
    }
}
