package com.panzareon.spellcircles.proxy;

import com.panzareon.spellcircles.client.effects.EntityOverloadFX;
import com.panzareon.spellcircles.handler.OverlayHandler;
import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.init.ModEntity;
import com.panzareon.spellcircles.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy
{
    private float angleBase;

    @Override
    public void preInit()
    {
        ModEntity.registerRenderer();
    }

    @Override
    public void registerRenders()
    {
        ModItems.registerRender();
        ModBlocks.registerRender();

        OverlayHandler.init();
    }

    @Override
    public void showOverloadParticles(Entity entity)
    {
        int particleSetting = Minecraft.getMinecraft().gameSettings.particleSetting;
        if(particleSetting == 1 && Math.random() > .5)
            return;
        else if(particleSetting == 2 && Math.random() > .1)
            return;

        World world = entity.worldObj;

        Vec3 pos = entity.getPositionVector();
        angleBase -= Math.random() * 0.02;
        double angle = angleBase + Math.random() * 0.5;
        Vec3 newPos;
        for(int i = 0; i < 4; i++)
        {
            angle += Math.PI / 2;
            newPos = new Vec3(pos.xCoord + Math.cos(angle) * 2.0, pos.yCoord + 0.5, pos.zCoord + Math.sin(angle) * 2.0);
            EntityOverloadFX effect = new EntityOverloadFX(world,newPos.xCoord, newPos.yCoord, newPos.zCoord, entity, new Vec3(0.0, 1.0, 0.0));
            Minecraft.getMinecraft().effectRenderer.addEffect(effect);
        }

    }
}
