package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.utility.LogHelper;

import java.util.*;

public class SpellList
{
    private static HashMap<String, SpellPart> spells = new HashMap<String, SpellPart>();
    private static HashMap<String, String> spellRequirements = new HashMap<String, String>();
    private static EnumMap<SpellReturnTypes, HashSet<SpellPart>> spellsByReturnType = new EnumMap<SpellReturnTypes, HashSet<SpellPart>>(SpellReturnTypes.class);

    static {
        for(SpellReturnTypes type: SpellReturnTypes.values())
        {
            spellsByReturnType.put(type,new HashSet<SpellPart>());
        }
    }

    public static void registerSpell(SpellPart part, SpellPart requiredSpell)
    {
        registerSpell(part);
        String name = part.getSpellName();
        spellRequirements.put(name, requiredSpell.getSpellName());
    }

    public static void registerSpell(SpellPart part)
    {
        String name = part.getSpellName();
        if(spells.containsKey(name))
            throw new IllegalArgumentException("Spell with this Name already registered");
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

    //returns copy of Spell Part with given Name
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
    //returns List of Spell Parts which have specific Return types
    public static SpellPart[] getSpellWithReturnType(SpellReturnTypes[] types)
    {
        HashSet<SpellPart> set = new HashSet<SpellPart>();
        for(SpellReturnTypes type: types)
        {
            set.addAll(spellsByReturnType.get(type));
        }
        ArrayList<SpellPart> ret = new ArrayList<SpellPart>(set);
        Collections.sort(ret);
        return ret.toArray(new SpellPart[ret.size()]);
    }
    //returns List of Spell Parts which have specific Return type
    public static SpellPart[] getSpellWithReturnType(SpellReturnTypes type)
    {
        ArrayList<SpellPart> ret = new ArrayList<SpellPart>(spellsByReturnType.get(type));
        Collections.sort(ret);
        return ret.toArray(new SpellPart[ret.size()]);
    }

    //returns Name of Spell needed to learn the given Spell, or null if no other Spell is needed
    public static String getRequiredSpell(String spell)
    {
        return spellRequirements.get(spell);
    }
    //returns Name of Spells witch require
}
