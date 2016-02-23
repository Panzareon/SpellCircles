package com.panzareon.spellcircles.entity;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellCastWith;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.utility.LogHelper;
import com.panzareon.spellcircles.utility.SpellHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;


public class EntitySpellCast extends Entity implements IEntityAdditionalSpawnData
{
    SpellEnviron environ;
    public EntitySpellCast(World worldIn)
    {
        super(worldIn);
    }

    public EntitySpellCast(World worldIn, SpellEnviron spellEnviron)
    {
        super(worldIn);
        environ = spellEnviron;
    }

    @Override
    protected void entityInit()
    {
    }


    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readFromNBT(tagCompund);
        if(tagCompund.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound nbt = (NBTTagCompound) tagCompund.getTag(Reference.MOD_ID);
            environ = SpellHelper.getEnvironFromNBT(nbt, worldObj);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        NBTTagCompound nbt = new NBTTagCompound();
        SpellHelper.writeEnvironToNBT(nbt, environ);
        tagCompound.setTag(Reference.MOD_ID, nbt);

    }


    public void setSpellToCall(SpellEnviron environ, int ticks)
    {
        SpellHelper.setSpellToCall(getEntityData(), environ, ticks);
    }

    @Override
    public void onUpdate()
    {
        if(environ != null)
        {
            environ.castWith = new SpellCastWith(this);
            try
            {
                environ.cast();
            }
            catch(MissingAuraException ex)
            {
                this.setDead();
            }
            environ = null;
        }
        try
        {
            SpellHelper.onUpdate(getEntityData(), false, new SpellCastWith(this), worldObj);
        }
        catch(MissingAuraException ex)
        {
            this.setDead();
        }
        if(!worldObj.isRemote)
            checkStillCasting();
    }

    public void checkStillCasting()
    {
        if(!getEntityData().hasKey(Reference.MOD_ID) || !getEntityData().getCompoundTag(Reference.MOD_ID).hasKey("toCast"))
        {
            setDead();
        }
    }


    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        if(environ != null)
        {
            NBTTagCompound nbt = new NBTTagCompound();
            SpellHelper.writeEnvironToNBT(nbt, environ);
            new PacketBuffer(buffer).writeNBTTagCompoundToBuffer(nbt);
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
        try
        {
            environ = SpellHelper.getEnvironFromNBT(new PacketBuffer(additionalData).readNBTTagCompoundFromBuffer(), worldObj);
        }
        catch (Exception ex)
        {
            LogHelper.error(ex);
        }
    }
}
