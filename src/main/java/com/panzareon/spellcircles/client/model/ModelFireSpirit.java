package com.panzareon.spellcircles.client.model;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFireSpirit extends ModelBase
{
    private ModelRenderer spiritHead;
    private ModelRenderer spiritRods[];

    public ModelFireSpirit()
    {
        spiritHead = new ModelRenderer(this, 0,0);
        spiritHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);

        spiritRods = new ModelRenderer[5];
        for(int i = 0; i < spiritRods.length; i++)
        {
            spiritRods[i] = new ModelRenderer(this, 0,0);
            spiritHead.addChild(spiritRods[i]);
            spiritRods[i].addBox(0.0f, 0.0f, 0.0f, 1,6,1);
            spiritRods[i].offsetY = 0.6f;
        }
    }

    @Override
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
    {
        setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);

        spiritHead.render(scale);
    }

    @Override
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn)
    {
        spiritHead.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        spiritHead.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);

        for(int i = 0; i < spiritRods.length; i++)
        {
            float rotBase = ((float)i / spiritRods.length) * 2.0f * (float)Math.PI;
            float rot = rotBase + (entityIn.ticksExisted % 50)/ 25.0f * (float)Math.PI;
            float rot2 = rotBase * 2.0f + ((entityIn.ticksExisted + 25) % 50)/ 25.0f * (float)Math.PI;
            spiritRods[i].offsetX = (float) Math.cos(rot) * 0.2f;
            spiritRods[i].offsetY = (float) Math.cos(rot2) * 0.2f + 0.6f;
            spiritRods[i].offsetZ = (float) Math.sin(rot) * 0.2f;
        }
    }
}
