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
import net.minecraft.util.ITickable;

public class TileEntitySpellCircle extends TileEntity implements ITickable
{
    private SpellEnviron environ = null;
    public String spellText = "";
    public int radius = 2;
    public float spellRotation = 0.0f;
    public float spellRotationStep;
    public float circleRotation = 0.0f;
    public float circleRotationStep;
    public boolean isCrafting = false;

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
        spellText = environ.getSpellString();
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
        spellText = environ.getSpellString();
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
                spellText = nbt.getString("spell");
                radius = nbt.getInteger("radius");
                environ = new SpellEnviron(spellText);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(environ != null)
        {
            //String spell = environ.getSpellString();
            if(spellText != null && !spellText.isEmpty())
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("spell", spellText);
                nbt.setInteger("radius", radius);
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

    @Override
    public void update()
    {
        if(worldObj.isRemote)
        {
            if(!isCrafting)
            {
                spellRotationStep = 0.001f;
                circleRotationStep = 0.0f;
                spellRotation += spellRotationStep;
            }
            else
            {
                spellRotationStep = 0.03f;
                circleRotationStep = 0.2f;
                spellRotation += spellRotationStep;
                circleRotation += circleRotationStep;
            }

        }
    }
}

