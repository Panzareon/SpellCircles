package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.entity.EntityFireSpirit;
import com.panzareon.spellcircles.entity.EntityThrowSpell;
import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SpellPartSpawnFireSpirit extends SpellPart
{
    private final float AuraUse = 0.05f;
    private final int AuraUseBase = 100;

    @Override
    public String getSpellName() {
        return "DRUFGHJKH";
    }

    @Override
    public String getSpellId() {
        return "spawn_fire_spirit";
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
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getNumberLength();
        if(nr > 0)
        {
            EntityPlayer player = environ.getCaster();
            World world = player.worldObj;
            Vec3 castPos = environ.getCastPosition();

            float auraMultiplier;
            int effectDuration;
            for(int i = 0; i < nr; i++)
            {
                effectDuration = (int)(childValues[0].getNumber(i) * 20);
                if(effectDuration < 20)
                    continue;

                auraMultiplier = (float) castPos.squareDistanceTo(player.getPositionVector());
                if(environ.useAura((int) ((AuraUse + auraMultiplier)*effectDuration) + AuraUseBase, environ.strength))
                {
                    if(!world.isRemote)
                    {
                        EntityFireSpirit spirit = new EntityFireSpirit(world);
                        spirit.setOwnerId(player.getUniqueID().toString());
                        spirit.setGrowingAge(effectDuration);
                        spirit.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F);
                        world.spawnEntityInWorld(spirit);
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
            return SpellReturnTypes.NUMBER;
        }
        return super.getChildType(childId);
    }
}
