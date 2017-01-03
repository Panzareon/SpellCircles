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
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModItems.chalk,"dyeWhite", Items.PAPER, Items.CLAY_BALL));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.spellRune, 1, 0),
                " S ",
                "SCS",
                " S ",
                'S', "stone", 'C', new ItemStack(ModItems.chalk,1, OreDictionary.WILDCARD_VALUE)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.spellRune, 1, 1),
                " S ",
                "SCS",
                " S ",
                'S', "ingotGold", 'C', new ItemStack(ModItems.chalk,1, OreDictionary.WILDCARD_VALUE)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.spellRune, 1, 2),
                " S ",
                "SCS",
                " S ",
                'S', "gemDiamond", 'C', new ItemStack(ModItems.chalk,1, OreDictionary.WILDCARD_VALUE)));
    }
}
