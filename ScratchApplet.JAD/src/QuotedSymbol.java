// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java


class QuotedSymbol
{

    QuotedSymbol(Symbol symbol)
    {
        sym = symbol;
    }

    public String toString()
    {
        return "\"" + sym.toString();
    }

    Symbol sym;
}
