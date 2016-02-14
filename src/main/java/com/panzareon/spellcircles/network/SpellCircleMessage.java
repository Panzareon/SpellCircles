package com.panzareon.spellcircles.network;

import com.panzareon.spellcircles.tileentity.TileEntitySpellCircle;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpellCircleMessage implements IMessage
{
    private String newSpellText;
    private BlockPos spellCirclePos;

    public SpellCircleMessage(){}
    public SpellCircleMessage(String spell, BlockPos spellCircle)
    {
        newSpellText = spell;
        spellCirclePos = spellCircle;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        newSpellText = ByteBufUtils.readUTF8String(buf);
        int x,y,z;
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        spellCirclePos = new BlockPos(x,y,z);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf,newSpellText);
        buf.writeInt(spellCirclePos.getX());
        buf.writeInt(spellCirclePos.getY());
        buf.writeInt(spellCirclePos.getZ());
    }

    public static class Handler implements IMessageHandler<SpellCircleMessage, IMessage>
    {

        @Override
        public IMessage onMessage(final SpellCircleMessage message, final MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    TileEntitySpellCircle spellCircle = (TileEntitySpellCircle) ((WorldServer) ctx.getServerHandler().playerEntity.worldObj).getTileEntity(message.spellCirclePos);
                    spellCircle.addSpellPart(message.newSpellText);
                }
            });
            return null;
        }
    }
}
