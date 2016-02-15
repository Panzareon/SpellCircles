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



    //check if set
    public int getEntityLength()
    {
        if(entity != null)
            return entity.length;
        else
            return 0;
    }
    public int getBlockLength()
    {
        if(block != null)
            return block.length;
        else if(position != null)
            return position.length;
        else if(entity != null)
            return entity.length;
        else
            return 0;
    }
    public int getDirectionLength()
    {
        if(direction != null)
            return direction.length;
        else if(entity != null)
            return entity.length;
        else
            return 0;
    }
    public int getNumberLength()
    {
        if(number != null)
            return number.length;
        else
            return 0;
    }
    public int getBoolLength()
    {
        if(bool != null)
            return bool.length;
        else
            return 0;
    }
    public int getPositionLength()
    {
        if(position != null)
            return position.length;
        else if(block != null)
            return block.length;
        else if(entity != null)
            return entity.length;
        else
            return 0;
    }
    public int getDimensionLength()
    {
        if(dimension != null)
            return dimension.length;
        else
            return 0;
    }


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


    //getter
    public Entity getEntity(int index)
    {
        index = index % entity.length;
        return entity[index];
    }
    public BlockPos getBlock(int index)
    {
        if(block != null)
        {
            index = index % block.length;
            return block[index];
        }
        if(position != null)
        {
            index = index % position.length;
            return new BlockPos(position[index]);
        }
        if(entity != null)
        {
            index = index % entity.length;
            return entity[index].getPosition();
        }
        return null;
    }
    public Vec3 getDirection(int index)
    {
        if(direction != null)
        {
            index = index % direction.length;
            return direction[index];
        }
        if(entity != null)
        {
            index = index % entity.length;
            return entity[index].getLookVec();
        }
        return null;
    }
    public float getNumber(int index)
    {
        index = index % number.length;
        return number[index];
    }
    public boolean getBool(int index)
    {
        index = index % bool.length;
        return bool[index];
    }
    public Vec3 getPosition(int index)
    {
        if(position != null)
        {
            index = index % position.length;
            return position[index];
        }
        if(block != null)
        {
            index = index % block.length;
            return new Vec3(block[index]);
        }
        if(entity != null)
        {
            index = index % entity.length;
            return entity[index].getPositionVector();
        }
        return null;
    }
    public int getDimension(int index)
    {
        index = index % dimension.length;
        return dimension[index];
    }
}
