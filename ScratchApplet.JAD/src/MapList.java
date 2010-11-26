// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java


class MapList
{

    MapList(Object aobj[])
    {
        offset = 0;
        tokens = aobj;
    }

    Object next()
    {
        return tokens[offset++];
    }

    Object peek()
    {
        return tokens[offset];
    }

    boolean eof()
    {
        return offset == tokens.length;
    }

    Object tokens[];
    int offset;
}
