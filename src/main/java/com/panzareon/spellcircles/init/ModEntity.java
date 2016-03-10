package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.SpellCircles;
import com.panzareon.spellcircles.entity.EntityFireSpirit;
import com.panzareon.spellcircles.entity.EntitySpellCast;
import com.panzareon.spellcircles.entity.EntityThrowSpell;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntity
{
    public static void init()
    {
        EntityRegistry.registerModEntity(EntityThrowSpell.class, "throw_spell", 1, SpellCircles.instance, 32, 10, true);
        EntityRegistry.registerModEntity(EntitySpellCast.class, "spell_cast", 2, SpellCircles.instance, 32, 10, false);
        EntityRegistry.registerModEntity(EntityFireSpirit.class, "fire_spirit", 3, SpellCircles.instance, 32, 10, false);
    }
}
