package com.panzareon.spellcircles.entity;

import com.panzareon.spellcircles.entity.ai.EntityAIFlyToOwner;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFireSpirit extends EntityTameable
{
    public EntityFireSpirit(World worldIn)
    {
        super(worldIn);
        this.isImmuneToFire = true;
        this.setSize(0.6F, 1.4F);
        this.tasks.addTask(4, new EntityFireSpirit.AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIFlyToOwner(this, 0.1D, 5.0F, 2.0F));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
        getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.8D);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        EntityFireSpirit ret = new EntityFireSpirit(worldObj);
        ret.setOwnerId((getOwner()).getUniqueID().toString());
        return ret;
    }

    @Override
    public void setTamed(boolean tamed)
    {
        //NOOP
    }

    @Override
    public boolean isTamed()
    {
        return true;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        return false;
    }


    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    @Override
    public void onLivingUpdate()
    {
        if(this.motionY < 0.0D)
        {
            this.motionY += (0.3 - this.motionY) * 0.3;
        }
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }
        if(growingAge == 0 && !worldObj.isRemote)
        {
            setDead();
        }

        super.onLivingUpdate();
    }


    @Override
    protected void updateAITasks()
    {
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        super.updateAITasks();
    }


    @Override
    public void fall(float distance, float damageMultiplier)
    {
        //NOOP
    }

    static class AIFireballAttack extends EntityAIBase
    {
        private EntityFireSpirit spirit;
        private int timeToNextAttack;

        public AIFireballAttack(EntityFireSpirit p_i45846_1_)
        {
            this.spirit = p_i45846_1_;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.spirit.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }


        /**
         * Updates the task
         */
        public void updateTask()
        {
            --this.timeToNextAttack;
            EntityLivingBase entitylivingbase = this.spirit.getAttackTarget();
            double d0 = this.spirit.getDistanceSqToEntity(entitylivingbase);

            if (d0 < 256.0D)
            {
                if (this.timeToNextAttack <= 0)
                {
                    double fireballMotionX = entitylivingbase.posX - this.spirit.posX;
                    double fireballMotionY = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.spirit.posY + (double)(this.spirit.height / 2.0F));
                    double fireballMotionZ = entitylivingbase.posZ - this.spirit.posZ;


                    this.timeToNextAttack = 50;
                    float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
                    this.spirit.worldObj.playAuxSFXAtEntity(null, 1009, new BlockPos((int)this.spirit.posX, (int)this.spirit.posY, (int)this.spirit.posZ), 0);

                    for (int i = 0; i < 1; ++i)
                    {
                        EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.spirit.worldObj, this.spirit, fireballMotionX + this.spirit.getRNG().nextGaussian() * (double)f, fireballMotionY, fireballMotionZ + this.spirit.getRNG().nextGaussian() * (double)f);
                        entitysmallfireball.posY = this.spirit.posY + (double)(this.spirit.height / 2.0F) + 0.5D;
                        this.spirit.worldObj.spawnEntityInWorld(entitysmallfireball);
                    }
                }

                this.spirit.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else
            {
                this.spirit.getNavigator().clearPathEntity();
                this.spirit.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }

            super.updateTask();
        }
    }

}
