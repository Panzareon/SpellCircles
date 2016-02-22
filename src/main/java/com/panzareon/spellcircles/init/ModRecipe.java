package com.panzareon.spellcircles.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipe
{

    public static void init()
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.chalk,"dyeWhite", Items.paper, Items.clay_ball));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.spellRune,
                " S ",
                "SCS",
                " S ",
                'S', "stone", 'C', new ItemStack(ModItems.chalk,1, OreDictionary.WILDCARD_VALUE)));
    }
}
