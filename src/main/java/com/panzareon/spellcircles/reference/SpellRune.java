package com.panzareon.spellcircles.reference;

public class SpellRune
{
    public static class uvCoords
    {
        public int u;
        public int v;
        public uvCoords(int u, int v)
        {
            this.u = u;
            this.v = v;
        }
    }
    public static uvCoords getUV(char c)
    {
        if(c >= 'A' && c <= 'Z')
        {
            return new uvCoords((c - 'A') * 7, 7);
        }
        if(c >= 'a' && c <= 'z')
        {
            return new uvCoords((c - 'a') * 7, 7);
        }
        if(c >= '1' && c <= '0')
        {
            return new uvCoords((c - '1') * 7, 0);
        }
        if(c == ':')
        {
            return new uvCoords(0, 14);
        }
        return null;
    }
}
