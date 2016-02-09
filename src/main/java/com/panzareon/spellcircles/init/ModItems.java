package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.item.ItemChalk;
import com.panzareon.spellcircles.item.ItemSpellCircles;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
    public static final ItemSpellCircles chalk = new ItemChalk();

    public static void init() {
        GameRegistry.registerItem(chalk, "chalk");
    }

    public static void registerRender()
    {
        registerRender(chalk);
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
