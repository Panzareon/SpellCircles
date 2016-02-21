package com.panzareon.spellcircles.tileentity;

import com.panzareon.spellcircles.reference.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class TileEntitySpellCircleGag extends TileEntity
{
    //The coordinates of our primary block will be stored in these variables.
    public BlockPos primaryPos;

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagCompound scNBT = new NBTTagCompound();
        scNBT.setInteger("px", primaryPos.getX());
        scNBT.setInteger("py", primaryPos.getY());
        scNBT.setInteger("pz", primaryPos.getZ());
        par1NBTTagCompound.setTag(Reference.MOD_ID, scNBT);
    }
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagCompound scNBT = par1NBTTagCompound.getCompoundTag(Reference.MOD_ID);
        primaryPos = new BlockPos(scNBT.getInteger("px"), scNBT.getInteger("py"), scNBT.getInteger("pz"));
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
