package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.item.ItemChalk;
import com.panzareon.spellcircles.item.ItemSpellRune;
import com.panzareon.spellcircles.item.SpellCirclesItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
        String unlocalizedName = item.getUnlocalizedName();
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
                item,
                0,
                new ModelResourceLocation(
                        unlocalizedName.substring(unlocalizedName.indexOf(".") + 1),
                        "inventory"));
    }
}
