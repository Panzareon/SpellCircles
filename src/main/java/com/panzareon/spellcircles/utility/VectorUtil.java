package com.panzareon.spellcircles.utility;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;


public class VectorUtil
{
    public static RayTraceResult raycast(World worldIn, Vec3d start, Vec3d end)
    {
        double additionalCheckSpace = 5.0;
        RayTraceResult mop = worldIn.rayTraceBlocks(start, end);
        double closestHit = 0.0;
        if(mop != null)
            closestHit = mop.hitVec.distanceTo(start);
        int minX, minY, minZ, maxX, maxY, maxZ;
        minX = (int) Math.floor(Math.min(start.xCoord, end.xCoord) - additionalCheckSpace);
        maxX = (int) Math.floor(Math.max(start.xCoord, end.xCoord) + additionalCheckSpace);
        minY = (int) Math.floor(Math.min(start.yCoord, end.yCoord) - additionalCheckSpace);
        maxY = (int) Math.floor(Math.max(start.yCoord, end.yCoord) + additionalCheckSpace);
        minZ = (int) Math.floor(Math.min(start.zCoord, end.zCoord) - additionalCheckSpace);
        maxZ = (int) Math.floor(Math.max(start.zCoord, end.zCoord) + additionalCheckSpace);
        AxisAlignedBB bb = new AxisAlignedBB(minX,minY,minZ,maxX,maxY,maxZ);
        List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class, bb);

        AxisAlignedBB entityBb;
        RayTraceResult mopTest;
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
                            mop.typeOfHit = RayTraceResult.Type.ENTITY;
                            mop.entityHit = ent;
                        }
                    }
                }
            }
        }
        return mop;
    }
    public static Vec3d multiplyVector(Vec3d vec, double l)
    {
        return new Vec3d(vec.xCoord * l, vec.yCoord * l, vec.zCoord * l);
    }
}
