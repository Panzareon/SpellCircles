package com.panzareon.spellcircles.exception;

import com.panzareon.spellcircles.spell.SpellPart;

public class MissingAuraException extends Exception
{
    public SpellPart spellPart;
    public MissingAuraException(SpellPart sp)
    {
        spellPart = sp;
    }
}
