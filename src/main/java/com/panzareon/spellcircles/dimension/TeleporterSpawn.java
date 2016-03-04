package com.panzareon.spellcircles.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterSpawn extends Teleporter
{
    WorldServer worldObj;
    public TeleporterSpawn(WorldServer worldIn)
    {
        super(worldIn);
        worldObj = worldIn;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {
        BlockPos pos = worldObj.provider.getRandomizedSpawnPoint();
        entityIn.setLocationAndAngles((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), entityIn.rotationYaw, 0.0F);
        entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
    }
}
