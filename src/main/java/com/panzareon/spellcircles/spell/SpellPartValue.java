package com.panzareon.spellcircles.spell;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class SpellPartValue
{
    Entity entity = null;
    BlockPos block = null;
    Vec3 direction = null;
    boolean isset_Number = false;
    float number;
    boolean isset_Bool = false;
    boolean bool;
    Vec3 position = null;
    boolean isset_Dimension = false;
    int dimension;

    //setter
    public  void setEntity(Entity e)
    {
        entity = e;
    }
    public  void setBlock(BlockPos b)
    {
        block = b;
    }
    public void setDirection(Vec3 dir)
    {
        direction = dir;
    }
    public void setNumber(float f)
    {
        number = f;
        isset_Number = true;
    }
    public void setBool(boolean b)
    {
        bool = b;
        isset_Bool = true;
    }
    public void setPosition(Vec3 pos)
    {
        position = pos;
    }
    public void setDimension(int d)
    {
        dimension = d;
        isset_Dimension = true;
    }

    //check if set
    public boolean issetEntity()
    {
        return entity != null;
    }
    public boolean issetBlock()
    {
        if(block != null)
            return true;
        if(position != null)
            return true;
        return false;
    }
    public boolean issetDirection()
    {
        return direction != null;
    }
    public boolean issetNumber()
    {
        return isset_Number;
    }
    public boolean issetBool()
    {
        return isset_Bool;
    }
    public boolean issetPosition()
    {
        if(position != null)
            return true;
        if(block != null)
            return true;
        if(entity != null)
            return true;
        return false;
    }
    public boolean issetDimension()
    {
        return isset_Dimension;
    }


    //getter
    public Entity getEntity()
    {
        return entity;
    }
    public BlockPos getBlock()
    {
        if(block != null)
            return block;
        if(position != null)
            return new BlockPos(position);
        return null;
    }
    public Vec3 getDirection()
    {
        return direction;
    }
    public float getNumber()
    {
        if(isset_Number)
            return number;
        else
        {
            //maybe throw exception?
            return 0.0f;
        }
    }
    public boolean getBool()
    {
        if(isset_Bool)
            return bool;
        else
        {
            //maybe throw exception?
            return false;
        }
    }
    public Vec3 getPosition()
    {
        if(position != null)
            return position;
        if(block != null)
            return new Vec3(block);
        if(entity != null)
            return entity.getPositionVector();
        return null;
    }
    public int getDimension()
    {
        if(isset_Dimension)
            return dimension;
        else
        {
            //maybe throw exception?
            return 0;
        }
    }
}
