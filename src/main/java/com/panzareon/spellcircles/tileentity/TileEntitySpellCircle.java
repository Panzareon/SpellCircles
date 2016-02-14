package com.panzareon.spellcircles.tileentity;

import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySpellCircle extends TileEntity
{
    private SpellEnviron environ = null;

    public void addSpellPart(String name)
    {
        if(environ == null)
        {
            environ = new SpellEnviron(name);
        }
        else
        {
            environ.addSpellPart(name);
        }
    }

    public void addSpellPart(SpellPart part)
    {
        if(environ == null)
        {
            environ = new SpellEnviron(part);
        }
        else
        {
            environ.addSpellPart(part);
        }
    }

    public SpellEnviron getEnviron()
    {
        if(environ == null)
            environ = new SpellEnviron();
        return environ;
    }

    public SpellPart[] getPossibleNextSpellParts()
    {
        SpellReturnTypes needed = SpellReturnTypes.ACTION;
        SpellPart lastNode = getEnviron().getLastNodeWithSpace();
        if(lastNode != null)
        {
            needed = lastNode.getChildType(lastNode.getNrOfSetChildren());
        }


        return SpellList.getSpellWithReturnType(needed);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound nbt = (NBTTagCompound) compound.getTag(Reference.MOD_ID);
            if(nbt.hasKey("spell"))
            {
                String spell = nbt.getString("spell");
                environ = new SpellEnviron(spell);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(environ != null)
        {
            String spell = environ.getSpellString();
            if(spell != null && !spell.isEmpty())
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("spell", spell);
                compound.setTag(Reference.MOD_ID, nbt);
            }
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.getPos(),1 ,syncData);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }
}

