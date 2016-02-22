package com.panzareon.spellcircles.spell.parts;

import com.panzareon.spellcircles.entity.EntityThrowSpell;
import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.spell.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpellPartThrowSpell extends SpellPart
{

    @Override
    public String getSpellName() {
        return "DMEB";
    }

    @Override
    public String getSpellId() {
        return "throw_spell";
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
    public SpellPartValue cast() throws MissingAuraException
    {
        SpellPartValue[] childValues = new SpellPartValue[0];

        return cast(childValues);
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        EntityPlayer player = environ.getCaster();
        World world = player.worldObj;

        SpellEnviron environ1 = new SpellEnviron(children[0].getSpellString());
        environ.setEnvironVariables(environ1);

        EntityThrowSpell throwSpell = new EntityThrowSpell(world, player, environ1);
        world.spawnEntityInWorld(throwSpell);
        return new SpellPartValue();
    }

    @Override
    public SpellReturnTypes getChildType(int childId)
    {
        if(childId == 0)
        {
            return SpellReturnTypes.ACTION;
        }
        return super.getChildType(childId);
    }
}
