package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellPartLightningBolt extends SpellPart
{
    private final float AuraUse = 750f;

    @Override
    public String getSpellName()
    {
        return "DFGHLKNBVDFT";
    }

    @Override
    public String getSpellId()
    {
        return "lightning_bolt";
    }

    @Override
    public int getNrOfChildren()
    {
        return 1;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getPositionLength();
        if(nr > 0)
        {
            Vec3 pos;
            EntityPlayer player = environ.getCaster();
            Vec3 castPos = environ.getCastPosition();
            float auraAdd;
            World world = player.getEntityWorld();

            for(int i = 0; i < nr; i++)
            {
                pos = childValues[0].getPosition(i);
                if(pos == null)
                    continue;
                if(world.canBlockSeeSky(new BlockPos(pos).up()) && world.isRaining() && world.isThundering())
                {
                    auraAdd = (float) castPos.squareDistanceTo(pos);
                    if(environ.useAura((int) (AuraUse + auraAdd)))
                    {
                        if(!world.isRemote)
                        {
                            world.addWeatherEffect(new EntityLightningBolt(world, pos.xCoord, pos.yCoord, pos.zCoord));
                        }
                    }
                    else
                    {
                        throw new MissingAuraException(this);
                    }
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
            return SpellReturnTypes.POSITION;
        }
        return super.getChildType(childId);
    }
}
