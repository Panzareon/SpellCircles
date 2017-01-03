package com.panzareon.spellcircles.entity.ai;

import com.panzareon.spellcircles.utility.VectorUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIFlyToOwner extends EntityAIBase
{
    private EntityTameable thePet;
    private EntityLivingBase theOwner;
    World theWorld;
    private double followSpeed;
    private int field_75343_h;
    float maxDist;
    float minDist;

    public EntityAIFlyToOwner(EntityTameable thePetIn, double followSpeedIn, float minDistIn, float maxDistIn)
    {
        this.thePet = thePetIn;
        this.theWorld = thePetIn.worldObj;
        this.followSpeed = followSpeedIn;
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setMutexBits(3);

        if (!(thePetIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.thePet.getOwner();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).isSpectator())
        {
            return false;
        }
        else if (this.thePet.isSitting())
        {
            return false;
        }
        else if (this.thePet.getDistanceSqToEntity(entitylivingbase) < (double)(this.minDist * this.minDist))
        {
            return false;
        }
        else
        {
            this.theOwner = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.thePet.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && !this.thePet.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_75343_h = 0;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theOwner = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {

        if (!this.thePet.isSitting())
        {
            if (--this.field_75343_h <= 0)
            {
                this.field_75343_h = 10;

                Vec3d posPet = thePet.getPositionVector();
                Vec3d posOwner = theOwner.getPositionVector();

                Vec3d petToOwner = posOwner.subtract(posPet);
                petToOwner.normalize();
                petToOwner = VectorUtil.multiplyVector(petToOwner, followSpeed);
                thePet.motionX = petToOwner.xCoord;
                thePet.motionY = petToOwner.yCoord;
                thePet.motionZ = petToOwner.zCoord;

            }
        }
    }
}