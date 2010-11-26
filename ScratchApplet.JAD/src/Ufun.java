// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java


class Ufun extends Primitives
{

    Ufun(Object aobj[], Object aobj1[])
    {
        arglist = aobj;
        body = aobj1;
    }

    public Object dispatch(int i, Object aobj[], LContext lcontext)
    {
        Object obj = null;
        Object aobj1[] = new Object[arglist.length];
        Symbol symbol = lcontext.ufun;
        lcontext.ufun = lcontext.cfun;
        Object aobj2[] = lcontext.locals;
        lcontext.locals = null;
        for(int j = 0; j < arglist.length; j++)
        {
            aobj1[j] = ((Symbol)arglist[j]).value;
            ((Symbol)arglist[j]).value = aobj[j];
        }

        try
        {
            Logo.runCommand(body, lcontext);
            if(lcontext.ufunresult != null && lcontext.ufunresult != lcontext.juststop)
                obj = lcontext.ufunresult;
        }
        finally
        {
            lcontext.ufun = symbol;
            for(int k = 0; k < arglist.length; k++)
                ((Symbol)arglist[k]).value = aobj1[k];

            if(lcontext.locals != null)
            {
                for(int l = 0; l < lcontext.locals.length; l += 2)
                    ((Symbol)lcontext.locals[l]).value = lcontext.locals[l + 1];

            }
            lcontext.locals = aobj2;
            lcontext.ufunresult = null;
        }
        return obj;
    }

    Object arglist[];
    Object body[];
}
