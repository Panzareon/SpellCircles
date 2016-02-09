package com.panzareon.spellcircles.spell;

public abstract class SpellPart
{
    protected SpellPart[] children;

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


}
