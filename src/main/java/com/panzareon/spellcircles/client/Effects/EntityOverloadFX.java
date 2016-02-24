package com.panzareon.spellcircles.client.Effects;

import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityOverloadFX extends EntityFX
{
    private Entity targetEntity;
    private Vec3 localPos;
    private Vec3 localSphereCenter;

    private double jitter;

    private boolean disapear = false;

    public EntityOverloadFX(World worldIn, double posXIn, double posYIn, double posZIn, Entity target, Vec3 sphereCenter)
    {
        super(worldIn, posXIn, posYIn, posZIn, 0.0, 0.0, 0.0);
        targetEntity = target;
        localSphereCenter = sphereCenter;
        localPos = new Vec3(posXIn, posYIn, posZIn).subtract(target.getPositionVector());
        jitter = (Math.random() - 0.5) * 0.01;
        noClip = true;

        this.particleRed = 0.1f;
        this.particleGreen = 0.1f;
        this.particleBlue = 0.1f;

        this.particleMaxAge = (int)(100.0F / (this.rand.nextFloat() * 0.2F + 0.8F));
        this.particleScale *= 0.2;
        this.setParticleTextureIndex(7);
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
        if(targetEntity == null)
        {
            this.setDead();
            return;
        }


        Vec3 targetPos = targetEntity.getPositionVector().addVector(0.0,0.05, 0.0);

        Vec3 particleToCenter = localSphereCenter.subtract(localPos);

        Vec3 toMove = particleToCenter.crossProduct(localPos);
        if(!disapear || localPos.yCoord < 1.5)
        {
            if(disapear || localPos.xCoord * localPos.xCoord + localPos.zCoord * localPos.zCoord < 0.2)
            {
                disapear = true;
                toMove = VectorUtil.multiplyVector(toMove, 0.1 / toMove.lengthVector());
                toMove = toMove.addVector(0, 0.1, 0);
                this.particleScale *= 0.95;
                this.particleAlpha *= 0.95;
            }
            else if(localPos.lengthVector() > 1.0)
            {
                toMove = VectorUtil.multiplyVector(toMove, 0.1  / toMove.lengthVector());
                toMove = toMove.subtract(VectorUtil.multiplyVector(localPos.add(toMove), 0.02 + jitter));
                this.particleScale *= 1.034;
            }
            else
            {
                toMove = VectorUtil.multiplyVector(toMove, 0.03 / toMove.lengthVector());
                toMove = toMove.subtract(VectorUtil.multiplyVector(localPos.add(toMove), 0.05 + jitter));
            }

            localPos = localPos.add(toMove);

            this.moveEntity(targetPos.xCoord + localPos.xCoord - prevPosX, targetPos.yCoord + localPos.yCoord - prevPosY, targetPos.zCoord + localPos.zCoord - prevPosZ);
        }
        else
        {
            this.moveEntity(0.0, 0.1, 0.0);
            this.particleScale *= 0.95;
            this.particleAlpha *= 0.95;
        }
    }
}
