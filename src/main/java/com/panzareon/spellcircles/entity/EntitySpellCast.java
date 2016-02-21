package com.panzareon.spellcircles.entity;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellCastWith;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.utility.SpellHelper;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class EntitySpellCast extends Entity
{
    SpellEnviron environ;
    public EntitySpellCast(World worldIn)
    {
        super(worldIn);
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
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagCompound nbt = new NBTTagCompound();
        SpellHelper.writeEnvironToNBT(nbt, environ);
        environ.castWith = new SpellCastWith(this);
        tagCompound.setTag(Reference.MOD_ID, nbt);

    }

    public void setSpellToCall(SpellEnviron environ, int ticks)
    {
        SpellHelper.setSpellToCall(getNBTTagCompound(), environ, ticks);
    }
}
