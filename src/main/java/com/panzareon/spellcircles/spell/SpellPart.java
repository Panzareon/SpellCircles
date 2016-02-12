package com.panzareon.spellcircles.spell;

import com.panzareon.spellcircles.utility.LogHelper;

public abstract class SpellPart
{
    protected SpellPart[] children;
    protected SpellEnviron environ;

    protected String additionalValuesString = null;

    public SpellPart()
    {
        children = new SpellPart[getNrOfChildren()];
    }

    public abstract String getSpellName();
    public abstract String getSpellId();

    public abstract int getNrOfChildren();
    public abstract SpellReturnTypes[] getReturnValueTypes();
    public SpellReturnTypes getChildType(int childId) {
        LogHelper.warn(getSpellId() + " doesn't have a child to get Type of");
        return null;
    }



    protected abstract SpellPartValue cast(SpellPartValue[] childValues);

    public void setEnviron(SpellEnviron env)
    {
        environ = env;
    }

    public void additionalValues(String value)
    {
        additionalValuesString = value;
    }

    public int getNrOfSetChildren()
    {
        for(int i = 0; i < children.length; i++)
        {
            if(children[i] == null)
                return i;
        }
        return children.length;
    }


    //Traversing Tree Methods
    public SpellPartValue cast()
    {
        SpellPartValue[] childValues = new SpellPartValue[children.length];

        for(int i = 0; i < children.length; i++)
        {
            childValues[i] = children[i].cast();
        }

        return cast(childValues);
    }

    //returns true if Child was added, false if there was no space
    public boolean addChild(SpellPart child)
    {
        for(int i = 0; i < getNrOfChildren(); i++)
        {
            if(children[i] == null)
            {
                children[i] = child;
                return true;
            }
            else if(children[i].addChild(child))
            {
                return true;
            }
        }
        return false;
    }


    public boolean isFinished()
    {
        if(children[children.length - 1] == null)
        {
            return false;
        }
        for(SpellPart child: children)
        {
            if(!child.isFinished())
            {
                return false;
            }
        }
        return true;
    }

    public String getSpellString()
    {
        String ret = getSpellName();
        if(additionalValuesString != null)
        {
            ret += ":" + additionalValuesString;
        }
        for(SpellPart child: children)
        {
            if(child != null)
            {
                ret += " " + child.getSpellString();
            }
        }
        return ret;
    }
    public SpellPart getLastNodeWithSpace()
    {
        //if this needs no children it doesn't have space for one
        if(children.length == 0)
            return null;
        //get last children that is actually set
        int i;
        for(i = children.length - 1; i >= 0; i--)
        {
            if(children[i] != null)
                break;
        }
        //if none are set i == -1
        if(i < 0)
            return this;
        //if last children has Space return it
        SpellPart child = children[i].getLastNodeWithSpace();
        if(child != null)
            return child;

        //if there is space for more children return this
        if (i < children.length - 1)
            return this;

        //else this and the last child are full -> no Space
        return null;
    }
}
