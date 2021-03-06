package com.panzareon.spellcircles.init;

import com.panzareon.spellcircles.creativetab.CreativeTabSpellCircles;
import com.panzareon.spellcircles.item.ItemChalk;
import com.panzareon.spellcircles.item.ItemSpellRune;
import com.panzareon.spellcircles.item.SpellCirclesItem;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModItems
{
    public static final SpellCirclesItem chalk = new ItemChalk();
    public static final SpellCirclesItem spellRune = new ItemSpellRune();

    public static void init() {
        ResourceLocation chalkLocation = new ResourceLocation(Reference.MOD_ID, "chalk");
        chalk.setRegistryName(chalkLocation);
        GameRegistry.register(chalk);

        ResourceLocation spellRuneLocation = new ResourceLocation(Reference.MOD_ID, "spell_rune");
        spellRune.setRegistryName(spellRuneLocation);
        GameRegistry.register(spellRune);
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
            ModelResourceLocation location = new ModelResourceLocation(
                    unlocalizedName.substring(unlocalizedName.indexOf(".") + 1),
                    "inventory");
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
                    item,
                    stack.getItemDamage(),
                    location);
        }
    }


    public static void preInit()
    {
        setCustomModelResourceLocation(spellRune);
    }
    public static void setCustomModelResourceLocation(Item item)
    {
        ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
        item.getSubItems(item, CreativeTabSpellCircles.SPELL_CIRCLES_TAB,subItems);
        for(ItemStack stack : subItems)
        {
            String unlocalizedName = item.getUnlocalizedName(stack);
            ModelResourceLocation location = new ModelResourceLocation(
                    unlocalizedName.substring(unlocalizedName.indexOf(".") + 1),
                    "inventory");
            ModelLoader.setCustomModelResourceLocation(spellRune, stack.getItemDamage(), location);
        }
    }
}
