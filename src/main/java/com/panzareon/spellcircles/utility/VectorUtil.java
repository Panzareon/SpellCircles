package com.panzareon.spellcircles.utility;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;


public class VectorUtil
{
    public static MovingObjectPosition raycast(World worldIn, Vec3 start, Vec3 end)
    {
        MovingObjectPosition mop = worldIn.rayTraceBlocks(start, end);
        double closestHit = 0.0;
        if(mop != null)
            closestHit = mop.hitVec.distanceTo(start);
        int minX, minY, minZ, maxX, maxY, maxZ;
        minX = (int) Math.floor(Math.min(start.xCoord, end.xCoord));
        maxX = (int) Math.floor(Math.max(start.xCoord, end.xCoord));
        minY = (int) Math.floor(Math.min(start.yCoord, end.yCoord));
        maxY = (int) Math.floor(Math.max(start.yCoord, end.yCoord));
        minZ = (int) Math.floor(Math.min(start.zCoord, end.zCoord));
        maxZ = (int) Math.floor(Math.max(start.zCoord, end.zCoord));
        AxisAlignedBB bb = new AxisAlignedBB(minX,minY,minZ,maxX,maxY,maxZ);
        List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class, bb);

        AxisAlignedBB entityBb;
        MovingObjectPosition mopTest;
        double currentHit;
        for(Entity ent:entities)
        {
            if(ent.canBeCollidedWith())
            {
                entityBb = ent.getEntityBoundingBox();
                if(!entityBb.isVecInside(start))
                {
                    mopTest = entityBb.calculateIntercept(start, end);
                    if (mopTest != null)
                    {
                        currentHit = mopTest.hitVec.distanceTo(start);
                        if (mop == null || currentHit < closestHit)
                        {
                            closestHit = currentHit;
                            mop = mopTest;
                        }
                    }
                }
            }
        }
        return mop;
    }
    public static Vec3 multiplyVector(Vec3 vec, double l)
    {
        return new Vec3(vec.xCoord * l, vec.yCoord * l, vec.zCoord * l);
    }
}
