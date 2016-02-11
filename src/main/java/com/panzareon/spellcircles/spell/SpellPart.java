package com.panzareon.spellcircles.spell;

public abstract class SpellPart
{
    protected SpellPart[] children;
    protected SpellEnviron environ;

    protected String additionalValuesString = null;

    public abstract String getSpellName();

    public abstract int getNrOfChildren();

    public SpellPartValue cast()
    {
        SpellPartValue[] childValues = new SpellPartValue[children.length];

        for(int i = 0; i < children.length; i++)
        {
            childValues[i] = children[i].cast();
        }

        return cast(childValues);
    }
    protected abstract SpellPartValue cast(SpellPartValue[] childValues);

    public void setEnviron(SpellEnviron env)
    {
        environ = env;
    }

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

    public void additionalValues(String value)
    {
        additionalValuesString = value;
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
}
