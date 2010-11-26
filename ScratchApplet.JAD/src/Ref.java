// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObjReader.java


class Ref
{

    Ref(int i)
    {
        index = i - 1;
    }

    public String toString()
    {
        return "Ref(" + index + ")";
    }

    int index;
}
