package com.panzareon.spellcircles.spell;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class SpellPartValue
{
    Entity entity[] = null;
    BlockPos block[] = null;
    Vec3 direction[] = null;
    float number[] = null;
    boolean bool[] = null;
    Vec3 position[] = null;
    int dimension[] = null;

    //setter
    public  void setEntity(Entity[] e)
    {
        entity = e;
    }
    public  void setBlock(BlockPos[] b)
    {
        block = b;
    }
    public void setDirection(Vec3[] dir)
    {
        direction = dir;
    }
    public void setNumber(float[] f)
    {
        number = f;
    }
    public void setBool(boolean[] b) {
        bool = b;
    }
    public void setPosition(Vec3[] pos)
    {
        position = pos;
    }
    public void setDimension(int[] d)
    {
        dimension = d;
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
        if(entity != null)
            return true;
        return false;
    }
    public boolean issetDirection()
    {
        return direction != null;
    }
    public boolean issetNumber()
    {
        return number != null;
    }
    public boolean issetBool()
    {
        return bool != null;
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
        return dimension != null;
    }


    //getter
    public Entity[] getEntity()
    {
        return entity;
    }
    public BlockPos[] getBlock()
    {
        if(block != null)
            return block;
        if(position != null)
        {
            BlockPos[] ret = new BlockPos[position.length];
            for(int i = 0; i < position.length; i++)
            {
                ret[i] = new BlockPos(position[i]);
            }
            return ret;
        }
        if(entity != null)
        {
            BlockPos[] ret = new BlockPos[entity.length];
            for (int i = 0; i < entity.length; i++) {
                ret[i] = entity[i].getPosition();
            }
            return ret;
        }
        return null;
    }
    public Vec3[] getDirection()
    {
        return direction;
    }
    public float[] getNumber()
    {
        return number;
    }
    public boolean[] getBool()
    {
        return bool;
    }
    public Vec3[] getPosition()
    {
        if(position != null)
            return position;
        if(block != null)
        {
            Vec3[] ret = new Vec3[block.length];
            for(int i = 0; i < block.length; i++)
            {
                ret[i] = new Vec3(block[i]);
            }
            return ret;
        }
        if(entity != null)
        {
            Vec3[] ret = new Vec3[entity.length];
            for (int i = 0; i < entity.length; i++) {
                ret[i] = entity[i].getPositionVector();
            }
            return ret;
        }
        return null;
    }
    public int[] getDimension()
    {
        return dimension;
    }
}
