package com.panzareon.spellcircles.utility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class MagicDamageSource extends DamageSource
{
    protected Entity damageSourceEntity;

    public MagicDamageSource(Entity damageSourceEntityIn)
    {
        super("magic");
        this.damageSourceEntity = damageSourceEntityIn;
    }

    public Entity getEntity()
    {
        return this.damageSourceEntity;
    }

    public boolean isDifficultyScaled()
    {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer);
    }
}
