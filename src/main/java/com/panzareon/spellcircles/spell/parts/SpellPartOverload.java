package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.init.ModPotions;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;

import java.util.UUID;

public class SpellPartOverload extends SpellPart
{
    private final float AuraUse = 500f;
    private final int effectDuration = 1200;

    @Override
    public String getSpellName() {
        return "RTZUIFGHUIEK";
    }

    @Override
    public String getSpellId() {
        return "overload";
    }

    @Override
    public int getNrOfChildren() {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes() {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException {
        int nr = childValues[0].getEntityLength();
        if(nr > 0)
        {
            Vec3 castPos = environ.getCastPosition();
            float auraMultiplier;
            EntityLivingBase target;
            for(int i = 0; i < nr; i++)
            {
                target = (EntityLivingBase) childValues[0].getEntity(i);
                if(target == null || target.isPotionActive(ModPotions.overload))
                    continue;
                //calculate Aura expense
                auraMultiplier = (float) castPos.squareDistanceTo(target.getPositionVector());
                if(environ.useAura((int) (AuraUse + auraMultiplier)))
                {
                    target.addPotionEffect(new PotionEffect(ModPotions.overload.getId(), effectDuration, 0));


                    UUID casterUUID = environ.getCaster().getPersistentID();
                    NBTTagCompound casterId = new NBTTagCompound();
                    casterId.setLong("casterLS", casterUUID.getLeastSignificantBits());
                    casterId.setLong("casterMS", casterUUID.getMostSignificantBits());
                    NBTTagCompound nbt = target.getEntityData();
                    if(nbt.hasKey(Reference.MOD_ID))
                    {
                        NBTTagCompound scNBT = nbt.getCompoundTag(Reference.MOD_ID);
                        scNBT.setTag("overloadCaster", casterId);
                    }
                    else
                    {
                        NBTTagCompound scNBT = new NBTTagCompound();
                        scNBT.setTag("overloadCaster", casterId);
                        nbt.setTag(Reference.MOD_ID, scNBT);
                    }
                }
                else
                {
                    throw new MissingAuraException(this);
                }
            }
        }
        return new SpellPartValue();
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.ENTITY;
        }
        return super.getChildType(childId);
    }


}
