package com.panzareon.spellcircles.client.renderer;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.reference.SpellRune;
import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
    private double runeRadius = 0.43;

    private final ResourceLocation spellCircle1x1 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/spell_circle.png");
    private final ResourceLocation spellRunes = new ResourceLocation(Reference.MOD_ID, "textures/gui/spell_runes.png");
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        //Render main Spell Circle from top
        WorldRenderer wr = Tessellator.getInstance().getWorldRenderer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(spellCircle1x1);
        wr.begin(7 , DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);

        wr.pos(0.0, 0.01, 1.0).tex(0.0, 1.0).normal(0,0,-1).endVertex();
        wr.pos(1.0, 0.01, 1.0).tex(1.0, 1.0).normal(0,0,-1).endVertex();
        wr.pos(1.0, 0.01, 0.0).tex(1.0, 0.0).normal(0,0,-1).endVertex();
        wr.pos(0.0, 0.01, 0.0).tex(0.0, 0.0).normal(0,0,-1).endVertex();

        Tessellator.getInstance().draw();
        Minecraft.getMinecraft().getTextureManager().bindTexture(spellRunes);
        wr.begin(7 , DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL);
        //Render spell of Spell circle
        String spell = ((TileEntitySpellCircle) te).spellText;
        SpellRune.uvCoords uv;
        double u1, u2, v1, v2;
        double x1, x2, x3, x4 , z1, z2, z3, z4;
        float rad = 0.0f;

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

                wr.pos(x1, 0.01, z1).tex(u1, v2).normal(0,0,-1).endVertex();
                wr.pos(x4, 0.01, z4).tex(u2, v2).normal(0,0,-1).endVertex();
                wr.pos(x3, 0.01, z3).tex(u2, v1).normal(0,0,-1).endVertex();
                wr.pos(x2, 0.01, z2).tex(u1, v1).normal(0,0,-1).endVertex();
            }
            rad += runeScale / runeRadius;
        }
        Tessellator.getInstance().draw();

        GlStateManager.popMatrix();
    }
}
