package com.panzareon.spellcircles.client.renderer;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.reference.SpellRune;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.utility.LogHelper;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntitySpellCircleRenderer extends TileEntitySpecialRenderer
{
    private double textureSize = 256.0;
    private double runeScale = 1.0 / 16.0;


    private final ResourceLocation spellCircle1x1 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/spell_circle1x1.png");
    private final ResourceLocation spellCircle3x3 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/spell_circle3x3.png");
    private final ResourceLocation spellRunes = new ResourceLocation(Reference.MOD_ID, "textures/gui/spell_runes.png");
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        Tessellator tessellator = Tessellator.getInstance();
        //This will make your block brightness dependent from surroundings lighting.
        float f = te.getWorld().getLightBrightness(te.getPos());

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        TileEntitySpellCircle teSC = (TileEntitySpellCircle)te;
        int radiusTE = teSC.radius;

        if(teSC.circleRotation != 0.0f)
        {
            GlStateManager.translate(0.5, 0.0, 0.5);
            GlStateManager.rotate(teSC.circleRotation + teSC.circleRotationStep * partialTicks, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5, 0.0, -0.5);
        }

        double posChange = radiusTE - 1;

        //Render main Spell Circle from top
        WorldRenderer wr = tessellator.getWorldRenderer();
        double runeRadius;
        if(radiusTE == 1)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(spellCircle1x1);
            runeRadius = 0.43;
        }
        else
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(spellCircle3x3);
            runeRadius = 1.43;
        }

        wr.begin(7 , DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

        wr.pos(0.0 - posChange, 0.01, 1.0 + posChange).tex(0.0, 1.0).color(f,f,f,1.0f).normal(0, 0, -1).endVertex();
        wr.pos(1.0 + posChange, 0.01, 1.0 + posChange).tex(1.0, 1.0).color(f,f,f,1.0f).normal(0, 0, -1).endVertex();
        wr.pos(1.0 + posChange, 0.01, 0.0 - posChange).tex(1.0, 0.0).color(f,f,f,1.0f).normal(0, 0, -1).endVertex();
        wr.pos(0.0 - posChange, 0.01, 0.0 - posChange).tex(0.0, 0.0).color(f,f,f,1.0f).normal(0, 0, -1).endVertex();

        tessellator.draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(spellRunes);
        wr.begin(7 , DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        //Render spell of Spell circle
        String spell = teSC.spellText;
        SpellRune.uvCoords uv;
        double u1, u2, v1, v2;
        double x1, x2, x3, x4 , z1, z2, z3, z4;
        float rad = teSC.spellRotation + teSC.spellRotationStep * partialTicks;

        for(int i = 0; i < spell.length(); i++)
        {
            uv = SpellRune.getUV(spell.charAt(i));
            if(uv != null)
            {
                u1 = ((double) uv.u) / textureSize;
                u2 = ((double) uv.u + 7) / textureSize;
                v1 = ((double) uv.v) / textureSize;
                v2 = ((double) uv.v + 7) / textureSize;
                x1 = Math.cos(rad) * runeRadius + 0.5;
                z1 = Math.sin(rad) * runeRadius + 0.5;
                x2 = x1 - Math.cos(rad) * runeScale;
                z2 = z1 - Math.sin(rad) * runeScale;
                x3 = x2 + Math.cos(rad - Math.PI / 2.0) * runeScale;
                z3 = z2 + Math.sin(rad - Math.PI / 2.0) * runeScale;
                x4 = x3 + Math.cos(rad) * runeScale;
                z4 = z3 + Math.sin(rad) * runeScale;

                wr.pos(x1, 0.01, z1).tex(u1, v2).color(f,f,f,1.0f).normal(0,0,-1).endVertex();
                wr.pos(x4, 0.01, z4).tex(u2, v2).color(f,f,f,1.0f).normal(0,0,-1).endVertex();
                wr.pos(x3, 0.01, z3).tex(u2, v1).color(f,f,f,1.0f).normal(0,0,-1).endVertex();
                wr.pos(x2, 0.01, z2).tex(u1, v1).color(f,f,f,1.0f).normal(0,0,-1).endVertex();
            }
            rad += runeScale / runeRadius;
        }
        tessellator.draw();

        GlStateManager.popMatrix();
    }

}
