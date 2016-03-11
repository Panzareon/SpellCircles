package com.panzareon.spellcircles.tileentity;

import com.panzareon.spellcircles.init.ModBlocks;
import com.panzareon.spellcircles.item.ItemSpell;
import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellEnviron;
import com.panzareon.spellcircles.spell.SpellList;
import com.panzareon.spellcircles.spell.SpellPart;
import com.panzareon.spellcircles.spell.SpellReturnTypes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileEntitySpellCircle extends TileEntity implements ITickable
{
    private final int craftingTime = 200;
    private ItemStack craftingItem;
    private SpellEnviron environ = null;
    public String spellText = "";
    public int radius;
    public float spellRotation = 0.0f;
    public float spellRotationStep = 0.001f;
    public float circleRotation = 0.0f;
    public float circleRotationStep;
    public boolean isCrafting = false;
    public int craftingTick = 0;

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

    public boolean addSpellPart(SpellPart part)
    {
        int spellSize = 0;
        if(environ != null && environ.getSpellString() != null)
        {
            spellSize = environ.getSpellString().length();
        }
        spellSize += part.getSpellString().length();

        if(spellSize > getMaxSpellSize())
            return false;
        if(environ == null)
        {
            environ = new SpellEnviron(part);
        }
        else
        {
            environ.addSpellPart(part);
        }
        spellText = environ.getSpellString();
        return true;
    }

    private int getMaxSpellSize()
    {
        if(radius == 1)
        {
            return 43;
        }
        else
        {
            return 141;
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


    public void isPlaced()
    {
        if(canCraftBlock(pos.down()))
        {
            isCrafting = true;
            craftingTick = craftingTime;
            startCraftingAnimation();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
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
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        //String spell = environ.getSpellString();
        if(spellText == null)
        {
            spellText = "";
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("spell", spellText);
        nbt.setInteger("radius", radius);
        compound.setTag(Reference.MOD_ID, nbt);
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
                spellRotation += spellRotationStep;
                if(circleRotation != 0.0f)
                {
                    circleRotation += circleRotationStep;
                    if(circleRotation <= 0.0f || circleRotation >= 360.0f)
                    {
                        circleRotationStep = 0.0f;
                        circleRotation = 0.0f;
                    }
                }
            }
            else
            {
                spellRotation += spellRotationStep;
                circleRotation += circleRotationStep;
            }

        }

        if(isCrafting)
        {
            craftingTick--;
            if(craftingTick <= 0)
            {
                isCrafting = false;
                craftingTick = 0;
                spellRotationStep = 0.001f;
                circleRotationStep /= -2;
                if(!worldObj.isRemote)
                {
                    if(craftingItem != null)
                    {
                        String spell = getEnviron().getSpellString();
                        if (spell != null)
                        {
                            ((ItemSpell) craftingItem.getItem()).setSpellString(craftingItem, spell);
                        }
                        EntityItem itemDrop = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), craftingItem);
                        itemDrop.setDefaultPickupDelay();
                        worldObj.spawnEntityInWorld(itemDrop);
                        craftingItem = null;
                    }
                    else
                    {
                        craftingComplete(pos.down());
                    }
                }
            }
        }
    }

    public void craft(EntityPlayer player)
    {
        if(!isCrafting)
        {
            craftingItem = player.inventory.decrStackSize(player.inventory.currentItem, 1);
            isCrafting = true;
            craftingTick = craftingTime;
            startCraftingAnimation();
        }
    }
    private void startCraftingAnimation()
    {
        spellRotationStep = 0.03f;
        circleRotationStep = 0.2f;
    }

    private void craftingComplete(BlockPos pos)
    {
        if(worldObj.getBlockState(pos).getBlock() == Blocks.bookshelf)
        {
            worldObj.setBlockState(pos, ModBlocks.discoverer.getDefaultState());
            worldObj.setBlockToAir(pos.up());
        }
    }

    private boolean canCraftBlock(BlockPos pos)
    {
        if(worldObj.getBlockState(pos).getBlock() == Blocks.bookshelf)
        {
            return true;
        }
        return false;
    }
}

