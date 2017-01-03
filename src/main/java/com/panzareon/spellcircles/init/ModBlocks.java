package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.block.*;
import com.panzareon.spellcircles.client.renderer.TileEntitySpellCircleRenderer;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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
        ResourceLocation placingDefaultLocation = new ResourceLocation(Reference.MOD_ID, "placing_default");
        placingDefault.setRegistryName(placingDefaultLocation);
        GameRegistry.register(placingDefault);
        ItemBlock placingDefaultItem = new ItemBlock(placingDefault);
        placingDefaultItem.setRegistryName(placingDefaultLocation);
        GameRegistry.register(placingDefaultItem);

        ResourceLocation spellCircleLocation = new ResourceLocation(Reference.MOD_ID, "spell_circle");
        spellCircle.setRegistryName(spellCircleLocation);
        GameRegistry.register(spellCircle);
        ItemBlock spellCircleItem = new ItemBlock(spellCircle);
        spellCircleItem.setRegistryName(spellCircleLocation);
        GameRegistry.register(spellCircleItem);

        ResourceLocation spellCircleGagLocation = new ResourceLocation(Reference.MOD_ID, "spell_circle_gag");
        spellCircleGag.setRegistryName(spellCircleGagLocation);
        GameRegistry.register(spellCircleGag);
        ItemBlock spellCircleGagItem = new ItemBlock(spellCircleGag);
        spellCircleGagItem.setRegistryName(spellCircleGagLocation);
        GameRegistry.register(spellCircleGagItem);

        ResourceLocation discovererLocation = new ResourceLocation(Reference.MOD_ID, "discoverer");
        discoverer.setRegistryName(discovererLocation);
        GameRegistry.register(discoverer);
        ItemBlock discovererItem = new ItemBlock(discoverer);
        discovererItem.setRegistryName(discovererLocation);
        GameRegistry.register(discovererItem);

        ResourceLocation pocketDimWallLocation = new ResourceLocation(Reference.MOD_ID, "pocket_dim_wall");
        pocketDimWall.setRegistryName(pocketDimWallLocation);
        GameRegistry.register(pocketDimWall);
        ItemBlock pocketDimWallItem = new ItemBlock(pocketDimWall);
        pocketDimWallItem.setRegistryName(pocketDimWallLocation);
        GameRegistry.register(pocketDimWallItem);
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
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
                item,
                0,
                new ModelResourceLocation(
                        unlocalizedName.substring(unlocalizedName.indexOf(".") + 1),
                        "inventory"));
    }
}
