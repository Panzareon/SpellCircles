package com.panzareon.spellcircles.creativetab;

import com.panzareon.spellcircles.init.ModItems;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabSpellCircles
{
    public static final CreativeTabs SPELL_CIRCLES_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem() {
            return ModItems.spellRune;
        }
    };
}
