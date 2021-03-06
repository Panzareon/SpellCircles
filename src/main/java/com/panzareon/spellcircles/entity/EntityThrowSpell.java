package com.panzareon.spellcircles.entity;


import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityThrowSpell extends EntityThrowable
{
    private SpellEnviron environ;

    public EntityThrowSpell(World worldIn, SpellEnviron spellEnviron)
    {
        super(worldIn);
        environ = spellEnviron;
    }

    public EntityThrowSpell(World worldIn, EntityLivingBase entity, SpellEnviron spellEnviron)
    {
        super(worldIn, entity);
        environ = spellEnviron;
    }

    public EntityThrowSpell(World worldIn, double x, double y, double z, SpellEnviron spellEnviron)
    {
        super(worldIn, x, y, z);
        environ = spellEnviron;
    }


    @Override
    protected void onImpact(RayTraceResult mop)
    {
        if(!worldObj.isRemote)
        {
            if(mop.typeOfHit == RayTraceResult.Type.ENTITY)
            {
                environ.entityHit = mop.entityHit;
            }
            else if(mop.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                environ.blockHit = mop.getBlockPos();
            }
            EntitySpellCast spellCast = new EntitySpellCast(worldObj, environ);
            spellCast.setPosition(posX, posY, posZ);
            worldObj.spawnEntityInWorld(spellCast);
        }
        setDead();
    }
}
