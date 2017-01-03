package com.panzareon.spellcircles.dimension;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class WorldData extends WorldSavedData
{
    public static WorldData forWorld(World world)
    {
        MapStorage storage = world.getPerWorldStorage();
        WorldData result = (WorldData)storage.getOrLoadData(WorldData.class, Reference.MOD_ID);
        if (result == null) {
            result = new WorldData(Reference.MOD_ID);
            storage.setData(Reference.MOD_ID, result);
        }
        return result;
    }

    private NBTTagCompound data = new NBTTagCompound();

    public WorldData(String tagName)
    {
        super(tagName);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        data = compound.getCompoundTag(Reference.MOD_ID);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        compound.setTag(Reference.MOD_ID, data);
        return compound;
    }

    public NBTTagCompound getData()
    {
        return data;
    }
}