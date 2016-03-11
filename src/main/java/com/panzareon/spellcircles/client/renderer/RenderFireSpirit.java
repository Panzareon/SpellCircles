package com.panzareon.spellcircles.client.renderer;


import com.panzareon.spellcircles.client.model.ModelFireSpirit;
import com.panzareon.spellcircles.entity.EntityFireSpirit;
import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFireSpirit extends RenderLiving<EntityFireSpirit>
{
    private static final ResourceLocation spiritTextures = new ResourceLocation(Reference.MOD_ID, "textures/entity/fire_spirit.png");

    public RenderFireSpirit(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelFireSpirit(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFireSpirit entity)
    {
        return spiritTextures;
    }
}
