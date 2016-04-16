package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.creativetab.CreativeTabSpellCircles;
import com.panzareon.spellcircles.item.ItemChalk;
import com.panzareon.spellcircles.item.ItemSpellRune;
import com.panzareon.spellcircles.item.SpellCirclesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModItems
{
    public static final SpellCirclesItem chalk = new ItemChalk();
    public static final SpellCirclesItem spellRune = new ItemSpellRune();

    public static void init() {
        GameRegistry.registerItem(chalk, "chalk");
        GameRegistry.registerItem(spellRune, "spell_rune");
    }

    public static void registerRender()
    {
        registerRender(chalk);
        registerRender(spellRune);
    }

    public static void registerRender(Item item)
    {
        ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
        item.getSubItems(item, CreativeTabSpellCircles.SPELL_CIRCLES_TAB,subItems);
        for(ItemStack stack : subItems)
        {
            String unlocalizedName = item.getUnlocalizedName(stack);
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
                    item,
                    stack.getItemDamage(),
                    new ModelResourceLocation(
                            unlocalizedName.substring(unlocalizedName.indexOf(".") + 1),
                            "inventory"));
        }
    }
}
