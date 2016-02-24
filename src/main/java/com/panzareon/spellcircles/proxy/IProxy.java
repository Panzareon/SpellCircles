package com.panzareon.spellcircles.proxy;

import net.minecraft.entity.Entity;

public interface IProxy
{
    void registerRenders();


    void showOverloadParticles(Entity entity);
}
