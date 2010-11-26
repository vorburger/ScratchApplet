// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ControlPrims.java


class ControlPrims extends Primitives
{

    ControlPrims()
    {
    }

    public String[] primlist()
    {
        return primlist;
    }

    public Object dispatch(int i, Object aobj[], LContext lcontext)
    {
        switch(i)
        {
        case 0: // '\0'
            return prim_repeat(aobj[0], aobj[1], lcontext);

        case 1: // '\001'
            return prim_if(aobj[0], aobj[1], lcontext);

        case 2: // '\002'
            return prim_ifelse(aobj[0], aobj[1], aobj[2], lcontext);

        case 3: // '\003'
            return prim_stop(lcontext);

        case 4: // '\004'
            return prim_output(aobj[0], lcontext);

        case 5: // '\005'
            return prim_dotimes(aobj[0], aobj[1], lcontext);

        case 6: // '\006'
            return prim_dolist(aobj[0], aobj[1], lcontext);

        case 7: // '\007'
            return prim_carefully(aobj[0], aobj[1], lcontext);

        case 8: // '\b'
            return lcontext.errormessage;

        case 9: // '\t'
            return prim_unwindprotect(aobj[0], aobj[1], lcontext);

        case 10: // '\n'
            return prim_error(aobj[0], lcontext);

        case 11: // '\013'
            return prim_dispatch(aobj[0], aobj[1], lcontext);

        case 12: // '\f'
            return prim_run(aobj[0], lcontext);

        case 13: // '\r'
            return prim_loop(aobj[0], lcontext);

        case 14: // '\016'
            return prim_loop(aobj[0], lcontext);

        case 15: // '\017'
            return prim_selectq(aobj[0], aobj[1], lcontext);

        case 16: // '\020'
            return prim_stopme(lcontext);
        }
        return null;
    }

    Object prim_repeat(Object obj, Object obj1, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        Object aobj[] = Logo.aList(obj1, lcontext);
        for(int j = 0; j < i; j++)
        {
            Logo.runCommand(aobj, lcontext);
            if(lcontext.ufunresult != null)
                return null;
        }

        return null;
    }

    Object prim_if(Object obj, Object obj1, LContext lcontext)
    {
        if(Logo.aBoolean(obj, lcontext))
            Logo.runCommand(Logo.aList(obj1, lcontext), lcontext);
        return null;
    }

    Object prim_ifelse(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        boolean flag = Logo.aBoolean(obj, lcontext);
        Object aobj[] = Logo.aList(obj1, lcontext);
        Object aobj1[] = Logo.aList(obj2, lcontext);
        return flag ? Logo.runList(aobj, lcontext) : Logo.runList(aobj1, lcontext);
    }

    Object prim_stop(LContext lcontext)
    {
        lcontext.ufunresult = lcontext.juststop;
        return null;
    }

    Object prim_output(Object obj, LContext lcontext)
    {
        lcontext.ufunresult = obj;
        return null;
    }

    Object prim_dotimes(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[];
        Symbol symbol;
        int i;
        Object obj2;
        MapList maplist = new MapList(Logo.aList(obj, lcontext));
        aobj = Logo.aList(obj1, lcontext);
        symbol = Logo.aSymbol(maplist.next(), lcontext);
        i = Logo.anInt(Logo.evalOneArg(maplist, lcontext), lcontext);
        Logo.checkListEmpty(maplist, lcontext);
        obj2 = symbol.value;
        Object obj3;
        for(int j = 0; j < i; j++)
        {
            symbol.value = new Double(j);
            Logo.runCommand(aobj, lcontext);
        }

        if(lcontext.ufunresult == null)
            break MISSING_BLOCK_LABEL_114;
        obj3 = null;
        symbol.value = obj2;
        return obj3;
        symbol.value = obj2;
        break MISSING_BLOCK_LABEL_136;
        Exception exception;
        exception;
        symbol.value = obj2;
        throw exception;
        return null;
    }

    Object prim_dolist(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[];
        Symbol symbol;
        Object aobj1[];
        Object obj2;
        MapList maplist = new MapList(Logo.aList(obj, lcontext));
        aobj = Logo.aList(obj1, lcontext);
        symbol = Logo.aSymbol(maplist.next(), lcontext);
        aobj1 = Logo.aList(Logo.evalOneArg(maplist, lcontext), lcontext);
        Logo.checkListEmpty(maplist, lcontext);
        obj2 = symbol.value;
        int i = 0;
_L1:
        Object obj3;
        if(i >= aobj1.length)
            break MISSING_BLOCK_LABEL_110;
        symbol.value = aobj1[i];
        Logo.runCommand(aobj, lcontext);
        if(lcontext.ufunresult == null)
            break MISSING_BLOCK_LABEL_104;
        obj3 = null;
        symbol.value = obj2;
        return obj3;
        i++;
          goto _L1
        symbol.value = obj2;
        break MISSING_BLOCK_LABEL_132;
        Exception exception;
        exception;
        symbol.value = obj2;
        throw exception;
        return null;
    }

    Object prim_carefully(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[];
        Object aobj1[];
        aobj = Logo.aList(obj, lcontext);
        aobj1 = Logo.aList(obj1, lcontext);
        return Logo.runList(aobj, lcontext);
        Exception exception;
        exception;
        lcontext.errormessage = exception.getMessage();
        return Logo.runList(aobj1, lcontext);
    }

    Object prim_unwindprotect(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[];
        Object aobj1[];
        aobj = Logo.aList(obj, lcontext);
        aobj1 = Logo.aList(obj1, lcontext);
        Logo.runCommand(aobj, lcontext);
        Logo.runCommand(aobj1, lcontext);
        break MISSING_BLOCK_LABEL_40;
        Exception exception;
        exception;
        Logo.runCommand(aobj1, lcontext);
        throw exception;
        return null;
    }

    Object prim_error(Object obj, LContext lcontext)
    {
        Logo.error(Logo.prs(obj), lcontext);
        return null;
    }

    Object prim_dispatch(Object obj, Object obj1, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        Object aobj[] = Logo.aList(obj1, lcontext);
        Object aobj1[] = Logo.aList(aobj[i], lcontext);
        return Logo.runList(aobj1, lcontext);
    }

    Object prim_run(Object obj, LContext lcontext)
    {
        return Logo.runList(Logo.aList(obj, lcontext), lcontext);
    }

    Object prim_loop(Object obj, LContext lcontext)
    {
        Object aobj[] = Logo.aList(obj, lcontext);
        do
            Logo.runCommand(aobj, lcontext);
        while(lcontext.ufunresult == null);
        return null;
    }

    Object prim_selectq(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[] = Logo.aList(obj1, lcontext);
        for(int i = 0; i < aobj.length; i += 2)
            if((aobj[i] instanceof DottedSymbol) ? Logo.getValue(((DottedSymbol)aobj[i]).sym, lcontext).equals(obj) : aobj[i].equals(obj))
                return Logo.runList((Object[])aobj[i + 1], lcontext);

        return null;
    }

    Object prim_stopme(LContext lcontext)
    {
        Logo.error("", lcontext);
        return null;
    }

    static String primlist[] = {
        "repeat", "2", "if", "2", "ifelse", "3", "stop", "0", "output", "1", 
        "dotimes", "2", "dolist", "2", "carefully", "2", "errormessage", "0", "unwind-protect", "2", 
        "error", "1", "dispatch", "2", "run", "1", "loop", "1", "forever", "1", 
        "selectq", "2", "stopme", "0"
    };

}
