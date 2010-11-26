// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefiningPrims.java

import java.util.*;

class DefiningPrims extends Primitives
{

    DefiningPrims()
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
            return prim_make(aobj[0], aobj[1], lcontext);

        case 1: // '\001'
            return prim_define(aobj[0], aobj[1], aobj[2], lcontext);

        case 2: // '\002'
            return prim_let(aobj[0], lcontext);

        case 3: // '\003'
            return prim_thing(aobj[0], lcontext);

        case 4: // '\004'
            return prim_put(aobj[0], aobj[1], aobj[2], lcontext);

        case 5: // '\005'
            return prim_get(aobj[0], aobj[1], lcontext);

        case 6: // '\006'
            return prim_get(aobj[0], aobj[1], lcontext);

        case 7: // '\007'
            return prim_plist(aobj[0], lcontext);

        case 8: // '\b'
            return prim_erplist(aobj[0], lcontext);

        case 9: // '\t'
            return prim_namep(aobj[0], lcontext);

        case 10: // '\n'
            return prim_definedp(aobj[0], lcontext);

        case 11: // '\013'
            return prim_clearname(aobj[0], lcontext);

        case 12: // '\f'
            return prim_quote(aobj[0], lcontext);

        case 13: // '\r'
            return prim_intern(aobj[0], lcontext);
        }
        return null;
    }

    Object prim_make(Object obj, Object obj1, LContext lcontext)
    {
        Logo.setValue(Logo.aSymbol(obj, lcontext), obj1, lcontext);
        return null;
    }

    Object prim_clearname(Object obj, LContext lcontext)
    {
        Logo.setValue(Logo.aSymbol(obj, lcontext), null, lcontext);
        return null;
    }

    Object prim_define(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        Symbol symbol = Logo.aSymbol(obj, lcontext);
        Object aobj[] = Logo.aList(obj1, lcontext);
        Object aobj1[] = Logo.aList(obj2, lcontext);
        Ufun ufun = new Ufun(aobj, aobj1);
        symbol.fcn = new Function(ufun, aobj.length, 0);
        return null;
    }

    Object prim_let(Object obj, LContext lcontext)
    {
        Vector vector = new Vector();
        if(lcontext.locals != null)
        {
            for(int i = 0; i < lcontext.locals.length; i++)
                vector.addElement(lcontext.locals[i]);

        }
        Symbol symbol;
        for(MapList maplist = new MapList(Logo.aList(obj, lcontext)); !maplist.eof(); Logo.setValue(symbol, Logo.evalOneArg(maplist, lcontext), lcontext))
        {
            symbol = Logo.aSymbol(maplist.next(), lcontext);
            vector.addElement(symbol);
            vector.addElement(symbol.value);
        }

        lcontext.locals = new Object[vector.size()];
        vector.copyInto(lcontext.locals);
        return null;
    }

    Object prim_thing(Object obj, LContext lcontext)
    {
        return Logo.getValue(Logo.aSymbol(obj, lcontext), lcontext);
    }

    Object prim_put(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        Hashtable hashtable = (Hashtable)lcontext.props.get(obj);
        if(hashtable == null)
        {
            hashtable = new Hashtable();
            lcontext.props.put(obj, hashtable);
        }
        hashtable.put(obj1, obj2);
        return null;
    }

    Object prim_get(Object obj, Object obj1, LContext lcontext)
    {
        Hashtable hashtable = (Hashtable)lcontext.props.get(obj);
        if(hashtable == null)
            return ((Object) (new Object[0]));
        Object obj2 = hashtable.get(obj1);
        if(obj2 == null)
            return ((Object) (new Object[0]));
        else
            return obj2;
    }

    Object prim_plist(Object obj, LContext lcontext)
    {
        Hashtable hashtable = (Hashtable)lcontext.props.get(obj);
        if(hashtable == null)
            return ((Object) (new Object[0]));
        Vector vector = new Vector();
        Object obj1;
        for(Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements(); vector.add(hashtable.get(obj1)))
        {
            obj1 = enumeration.nextElement();
            vector.add(obj1);
        }

        Object aobj[] = new Object[vector.size()];
        vector.copyInto(aobj);
        return ((Object) (aobj));
    }

    Object prim_erplist(Object obj, LContext lcontext)
    {
        lcontext.props.remove(obj);
        return null;
    }

    Object prim_namep(Object obj, LContext lcontext)
    {
        return new Boolean(Logo.aSymbol(obj, lcontext).value != null);
    }

    Object prim_definedp(Object obj, LContext lcontext)
    {
        return new Boolean(Logo.aSymbol(obj, lcontext).fcn != null);
    }

    Object prim_quote(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
            return obj;
        else
            return new QuotedSymbol(Logo.aSymbol(obj, lcontext));
    }

    Object prim_intern(Object obj, LContext lcontext)
    {
        return Logo.aSymbol(obj, lcontext);
    }

    static String primlist[] = {
        "make", "2", "define", "3", "let", "1", "thing", "1", "put", "3", 
        "get", "2", "getp", "2", "plist", "1", "erplist", "1", "name?", "1", 
        "defined?", "1", "clearname", "1", "quote", "1", "intern", "1"
    };

}
