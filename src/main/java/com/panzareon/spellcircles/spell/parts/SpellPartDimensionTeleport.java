package com.panzareon.spellcircles.spell.parts;


import com.panzareon.spellcircles.dimension.TeleporterPocketDim;
import com.panzareon.spellcircles.dimension.TeleporterSpawn;
import com.panzareon.spellcircles.exception.MissingAuraException;
import com.panzareon.spellcircles.init.ModDimension;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellPartValue;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class SpellPartDimensionTeleport extends SpellPart
{
    private int AuraUse = 1000;
    @Override
    public String getSpellName()
    {
        return "MDNMKLPOIJHG";
    }

    @Override
    public String getSpellId()
    {
        return "dimension_teleport";
    }

    @Override
    public int getNrOfChildren()
    {
        return 2;
    }

    @Override
    public SpellReturnTypes[] getReturnValueTypes()
    {
        return new SpellReturnTypes[]{SpellReturnTypes.ACTION};
    }

    @Override
    protected SpellPartValue cast(SpellPartValue[] childValues) throws MissingAuraException
    {
        int nr = childValues[0].getEntityLength();
        int nr2 = childValues[1].getDimensionLength();
        if(nr > 0 && nr2 > 0)
        {
            if(nr < nr2)
                nr = nr2;
            int dimensionId;
            for(int i = 0; i < nr; i++)
            {
                dimensionId = childValues[1].getDimension(i);
                if(environ.useAura(AuraUse, environ.strength))
                {
                    World world = environ.getCaster().worldObj;
                    if(!world.isRemote)
                    {
                        WorldServer worldTo = DimensionManager.getWorld(dimensionId);
                        ServerConfigurationManager manager = ((WorldServer) world).getMinecraftServer().getConfigurationManager();
                        Entity e = childValues[0].getEntity(i);
                        if(e instanceof EntityPlayer)
                        {
                            if(dimensionId == ModDimension.pocketDimensionId)
                            {
                                manager.transferPlayerToDimension((EntityPlayerMP) e,dimensionId, new TeleporterPocketDim(worldTo, environ.getCaster()));
                            }
                            else
                                manager.transferPlayerToDimension((EntityPlayerMP) e,dimensionId, new TeleporterSpawn(worldTo));
                        }
                        else
                        {
                            if(dimensionId == ModDimension.pocketDimensionId)
                            {
                                manager.transferEntityToWorld(e, dimensionId, (WorldServer) world, worldTo, new TeleporterPocketDim(worldTo, environ.getCaster()));
                            }
                            else
                                manager.transferEntityToWorld(e, dimensionId, (WorldServer) world, worldTo, new TeleporterSpawn(worldTo));
                        }
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
            return SpellReturnTypes.ENTITY;
        }
        else if(childId == 1)
        {
            return SpellReturnTypes.DIMENSION;
        }
        return super.getChildType(childId);
    }
}
