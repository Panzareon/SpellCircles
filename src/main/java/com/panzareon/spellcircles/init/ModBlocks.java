package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.block.BlockPlacingDefault;
import com.panzareon.spellcircles.block.BlockSpellCircles;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static final BlockSpellCircles placingDefault = new BlockPlacingDefault();

    public static void init() {
        GameRegistry.registerBlock(placingDefault, "placing_default");
    }

    public static void registerRender()
    {
        registerRender(placingDefault);
    }

    public static void registerRender(Block block)
    {
        Item item = Item.getItemFromBlock(block);
        String unlocalizedName = item.getUnlocalizedName();
        LogHelper.info(unlocalizedName.substring(unlocalizedName.indexOf(".") + 1));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
                item,
                0,
                new ModelResourceLocation(
                        unlocalizedName.substring(unlocalizedName.indexOf(".") + 1),
                        "inventory"));
    }
}
