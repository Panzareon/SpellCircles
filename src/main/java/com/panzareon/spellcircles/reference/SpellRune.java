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
        if(c >= '0' && c <= '9')
        {
            return new uvCoords((c - '0') * 7, 0);
        }
        if(c == ':')
        {
            return new uvCoords(0, 14);
        }
        return null;
    }
}
