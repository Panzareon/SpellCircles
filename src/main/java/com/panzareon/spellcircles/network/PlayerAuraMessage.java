package com.panzareon.spellcircles.network;

import com.panzareon.spellcircles.reference.Reference;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerAuraMessage implements IMessage
{
    private int newAura;

    public PlayerAuraMessage(){}
    public PlayerAuraMessage(int aura)
    {
        newAura = aura;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        NBTTagCompound nbt = ByteBufUtils.readTag(buf);
        newAura = nbt.getInteger("aura");
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("aura", newAura);
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<PlayerAuraMessage, IMessage>
    {

        @Override
        public IMessage onMessage(final PlayerAuraMessage message, final MessageContext ctx)
        {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    NBTTagCompound nbt = Minecraft.getMinecraft().thePlayer.getEntityData();
                    if(nbt.hasKey(Reference.MOD_ID))
                    {
                        NBTTagCompound scNbt = nbt.getCompoundTag(Reference.MOD_ID);
                        scNbt.setInteger("Aura", message.newAura);
                    }
                }
            });
            return null;
        }
    }
}
