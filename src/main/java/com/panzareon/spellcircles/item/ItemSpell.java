package com.panzareon.spellcircles.item;


import com.panzareon.spellcircles.reference.Reference;
import com.panzareon.spellcircles.spell.SpellEnviron;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class ItemSpell extends SpellCirclesItem
{

    ItemSpell()
    {
        super();
    }

    public void resetSpellCasting(ItemStack stack)
    {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if(itemNbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNbt = itemNbt.getCompoundTag(Reference.MOD_ID);
            if(scNbt.hasKey("toCast"))
            {
                scNbt.removeTag("toCast");
            }
        }
    }

    public void setSpellToCall(ItemStack stack, SpellEnviron se, int nrOfTicks)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        int caster = se.getCaster().getEntityId();
        nbt.setInteger("caster", caster);
        if(se.castPos != null)
        {
            Vec3 castPos = se.castPos;
            NBTTagCompound pos = new NBTTagCompound();
            pos.setDouble("xPos", castPos.xCoord);
            pos.setDouble("yPos", castPos.yCoord);
            pos.setDouble("zPos", castPos.zCoord);
            nbt.setTag("castPos", pos);
        }
        nbt.setString("spell", se.getSpellString());
        nbt.setInteger("chargedAura", se.chargedAura);
        NBTTagCompound itemNbt = stack.getTagCompound();
        if(!itemNbt.hasKey(Reference.MOD_ID))
        {
            itemNbt.setTag(Reference.MOD_ID, new NBTTagCompound());
        }
        NBTTagCompound scNbt = itemNbt.getCompoundTag(Reference.MOD_ID);
        scNbt.setTag("toCast", nbt);
        stack.setItemDamage(nrOfTicks);
    }

    public void callAdditionalSpell(ItemStack stack, World world)
    {
        NBTTagCompound itemNbt = stack.getTagCompound();
        if(itemNbt.hasKey(Reference.MOD_ID))
        {
            NBTTagCompound scNbt = itemNbt.getCompoundTag(Reference.MOD_ID);
            if(scNbt.hasKey("toCast"))
            {
                NBTTagCompound nbt = scNbt.getCompoundTag("toCast");
                String spell = nbt.getString("spell");
                SpellEnviron environ = new SpellEnviron(spell);
                if(nbt.hasKey("castPos"))
                {
                    NBTTagCompound pos = nbt.getCompoundTag("castPos");
                    double posX = pos.getDouble("xPos");
                    double posY = pos.getDouble("yPos");
                    double posZ = pos.getDouble("zPos");
                    Vec3 castPos = new Vec3(posX, posY, posZ);
                    environ.castPos = castPos;
                }
                int casterId = nbt.getInteger("caster");
                Entity caster = world.getEntityByID(casterId);
                environ.setCaster((EntityLivingBase) caster);
                int chargedAura = nbt.getInteger("chargedAura");
                environ.chargedAura = chargedAura;
                environ.castItem = stack;
                scNbt.removeTag("toCast");
                environ.cast();
            }
        }
    }

    public void setSpellString(ItemStack itemStackIn, String spell)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("spell", spell);
        NBTTagCompound compound = itemStackIn.getTagCompound();
        if(compound == null)
        {
            compound = new NBTTagCompound();
        }
        compound.setTag(Reference.MOD_ID, nbt);
        itemStackIn.setTagCompound(compound);
    }

    protected SpellEnviron getEnvironFromNBT(ItemStack itemStackIn)
    {

        if(itemStackIn.getTagCompound() != null)
        {
            if(itemStackIn.getTagCompound().hasKey(Reference.MOD_ID))
            {
                //get spell from nbt
                NBTTagCompound nbt = (NBTTagCompound) itemStackIn.getTagCompound().getTag(Reference.MOD_ID);
                if(nbt.hasKey("spell"))
                {
                    String spell = nbt.getString("spell");
                    return new SpellEnviron(spell);
                }
            }
        }
        return null;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(stack.getMetadata() > 0)
        {
            if(!isSelected)
            {
                resetSpellCasting(stack);
            }
            int time = stack.getMetadata();
            time--;
            stack.setItemDamage(time);
            if(time <= 0)
            {
                callAdditionalSpell(stack, worldIn);
            }
        }
    }

    
}
