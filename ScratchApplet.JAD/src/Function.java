// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java


class Function
{

    Function(Primitives primitives, int i, int j)
    {
        this(primitives, i, j, false);
    }

    Function(Primitives primitives, int i, int j, boolean flag)
    {
        instance = primitives;
        nargs = i;
        dispatchOffset = j;
        ipm = flag;
    }

    Primitives instance;
    int dispatchOffset;
    int nargs;
    boolean ipm;
}
