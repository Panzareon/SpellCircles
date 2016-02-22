package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.block.*;
import com.panzareon.spellcircles.client.renderer.TileEntitySpellCircleRenderer;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static final SpellCirclesBlock placingDefault = new BlockPlacingDefault();
    public static final SpellCirclesBlock spellCircle = new BlockSpellCircle();
    public static final SpellCirclesBlock spellCircleGag = new BlockSpellCircleGag();
    public static final SpellCirclesBlock discoverer = new BlockDiscoverer();
    public static final SpellCirclesBlock pocketDimWall = new BlockPocketDimWall();

    public static void init() {
        GameRegistry.registerBlock(placingDefault, "placing_default");
        GameRegistry.registerBlock(spellCircle, "spell_circle");
        GameRegistry.registerBlock(spellCircleGag, "spell_circle_gag");
        GameRegistry.registerBlock(discoverer, "discoverer");
        GameRegistry.registerBlock(pocketDimWall, "pocket_dim_wall");
    }

    public static void registerRender()
    {
        registerRender(placingDefault);
        registerRender(spellCircle);
        registerRender(spellCircleGag);
        registerRender(discoverer);
        registerRender(pocketDimWall);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpellCircle.class, new TileEntitySpellCircleRenderer());
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
