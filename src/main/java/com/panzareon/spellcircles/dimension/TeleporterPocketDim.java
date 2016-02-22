package com.panzareon.spellcircles.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterPocketDim extends Teleporter
{
    WorldServer worldObj;
    EntityPlayer caster;
    public TeleporterPocketDim(WorldServer worldIn, EntityPlayer c)
    {
        super(worldIn);
        worldObj = worldIn;
        caster = c;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw)
    {

        int posX = 0, posY = 2 , posZ = 0;

        WorldData data = WorldData.forWorld(worldObj);
        NBTTagCompound nbt = data.getData();
        String UUIDString = caster.getPersistentID().toString();
        if(nbt.hasKey("positionList"))
        {
            NBTTagCompound positionList = nbt.getCompoundTag("positionList");
            if(nbt.hasKey(UUIDString))
            {
                NBTTagCompound pos = positionList.getCompoundTag(UUIDString);
                posX = pos.getInteger("posX");
                posZ = pos.getInteger("posZ");
            }
            else
            {
                NBTTagCompound next = nbt.getCompoundTag("nextPosition");
                posX = next.getInteger("posX");
                posZ = next.getInteger("posZ");
                next.setInteger("posX", posX + 1);
                NBTTagCompound pos = new NBTTagCompound();
                pos.setInteger("posX", posX);
                pos.setInteger("posZ", posZ);
                positionList.setTag(UUIDString, pos);
            }
        }
        else
        {
            NBTTagCompound positionList = new NBTTagCompound();
            NBTTagCompound pos = new NBTTagCompound();
            pos.setInteger("posX", posX);
            pos.setInteger("posZ", posZ);
            positionList.setTag(UUIDString, pos);
            NBTTagCompound next = new NBTTagCompound();
            next.setInteger("posX", posX + 1);
            next.setInteger("posZ", posZ);
            nbt.setTag("nextPosition", next);
            nbt.setTag("positionList", positionList);
        }

        posX = posX * 16 + 8;
        posZ = posZ * 16 + 8;
        entityIn.setLocationAndAngles((double)posX, (double)posY, (double)posZ, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
    }
}
