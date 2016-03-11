package com.panzareon.spellcircles.client.renderer;

import com.panzareon.spellcircles.entity.EntityFireSpirit;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFactoryFireSpirit implements IRenderFactory<EntityFireSpirit>
{
    @Override
    public Render<? super EntityFireSpirit> createRenderFor(RenderManager manager)
    {
        return new RenderFireSpirit(manager);
    }
}
