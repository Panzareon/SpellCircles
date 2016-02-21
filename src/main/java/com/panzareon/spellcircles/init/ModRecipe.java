package com.panzareon.spellcircles.init;

import net.minecraft.init.Items;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipe
{

    public static void init()
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.chalk,"dyeWhite", Items.paper, Items.clay_ball));
    }
}
