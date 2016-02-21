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
        if(e.length > 0)
            entity = e;
    }
    public  void setBlock(BlockPos[] b)
    {
        if(b.length > 0)
            block = b;
    }
    public void setDirection(Vec3[] dir)
    {
        if(dir.length > 0)
            direction = dir;
    }
    public void setNumber(float[] f)
    {
        if(f.length > 0)
            number = f;
    }
    public void setBool(boolean[] b)
    {
        if(b.length > 0)
            bool = b;
    }
    public void setPosition(Vec3[] pos)
    {
        if(pos.length > 0)
            position = pos;
    }
    public void setDimension(int[] d)
    {
        if(d.length > 0)
            dimension = d;
    }


    //getter
    public Entity getEntity(int index)
    {
        if(entity == null)
            return null;
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
        if(number == null)
            return 0.0f;
        index = index % number.length;
        return number[index];
    }
    public boolean getBool(int index)
    {
        if(bool == null)
            return false;
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
        if(dimension == null)
            return 0;
        index = index % dimension.length;
        return dimension[index];
    }
}
