package com.panzareon.spellcircles.dimension;


import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterToPosition extends Teleporter
{

    Vec3d targetPosition;
    WorldServer worldObj;
    public TeleporterToPosition(WorldServer worldIn, Vec3d pos)
    {
        super(worldIn);
        worldObj = worldIn;
        targetPosition = pos;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        entityIn.setLocationAndAngles(targetPosition.xCoord, targetPosition.yCoord, targetPosition.zCoord, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
    }
}
