// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WordListPrims.java


class WordListPrims extends Primitives
{

    WordListPrims()
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
            return prim_first(aobj[0], lcontext);

        case 1: // '\001'
            return prim_last(aobj[0], lcontext);

        case 2: // '\002'
            return prim_word(aobj, lcontext);

        case 3: // '\003'
        case 4: // '\004'
            return prim_butfirst(aobj[0], lcontext);

        case 5: // '\005'
        case 6: // '\006'
            return prim_butlast(aobj[0], lcontext);

        case 7: // '\007'
            return prim_fput(aobj[0], aobj[1], lcontext);

        case 8: // '\b'
            return prim_lput(aobj[0], aobj[1], lcontext);

        case 9: // '\t'
            return prim_item(aobj[0], aobj[1], lcontext);

        case 10: // '\n'
            return prim_nth(aobj[0], aobj[1], lcontext);

        case 11: // '\013'
            return prim_emptyp(aobj[0], lcontext);

        case 12: // '\f'
            return prim_count(aobj[0], lcontext);

        case 13: // '\r'
            return prim_wordp(aobj[0], lcontext);

        case 14: // '\016'
            return prim_listp(aobj[0], lcontext);

        case 15: // '\017'
            return prim_memberp(aobj[0], aobj[1], lcontext);

        case 16: // '\020'
            return prim_itempos(aobj[0], aobj[1], lcontext);

        case 17: // '\021'
            return prim_setitem(aobj[0], aobj[1], aobj[2], lcontext);

        case 18: // '\022'
            return prim_setnth(aobj[0], aobj[1], aobj[2], lcontext);

        case 19: // '\023'
            return prim_removeitem(aobj[0], aobj[1], lcontext);

        case 20: // '\024'
            return prim_removeitempos(aobj[0], aobj[1], lcontext);

        case 21: // '\025'
        case 22: // '\026'
            return prim_sentence(aobj, lcontext);

        case 23: // '\027'
            return prim_list(aobj, lcontext);

        case 24: // '\030'
            return prim_makelist(aobj[0], lcontext);

        case 25: // '\031'
            return prim_copylist(aobj[0], lcontext);

        case 26: // '\032'
            return prim_parse(aobj[0], lcontext);

        case 27: // '\033'
            return prim_char(aobj[0], lcontext);

        case 28: // '\034'
            return prim_ascii(aobj[0], lcontext);

        case 29: // '\035'
            return prim_reverse(aobj[0], lcontext);

        case 30: // '\036'
            return prim_substring(aobj[0], aobj[1], aobj[2], lcontext);

        case 31: // '\037'
            return prim_ucase(aobj[0], lcontext);

        case 32: // ' '
            return prim_insert(aobj[0], aobj[1], aobj[2], lcontext);
        }
        return null;
    }

    Object copyList(Object aobj[], int i, int j)
    {
        Object aobj1[] = new Object[j];
        for(int k = 0; k < j; k++)
            aobj1[k] = aobj[i++];

        return ((Object) (aobj1));
    }

    Object addToList(Object aobj[], Object obj)
    {
        if(!(obj instanceof Object[]))
            return lput(obj, aobj);
        Object aobj1[] = (Object[])obj;
        Object aobj2[] = new Object[aobj.length + aobj1.length];
        for(int i = 0; i < aobj.length; i++)
            aobj2[i] = aobj[i];

        for(int j = 0; j < aobj1.length; j++)
            aobj2[j + aobj.length] = aobj1[j];

        return ((Object) (aobj2));
    }

    Object removeItem(Object aobj[], int i)
    {
        Object aobj1[] = new Object[aobj.length - 1];
        int j = 0;
        for(int k = 0; k < aobj.length; k++)
            if(k != i - 1)
                aobj1[j++] = aobj[k];

        return ((Object) (aobj1));
    }

    static Object lput(Object obj, Object aobj[])
    {
        Object aobj1[] = new Object[aobj.length + 1];
        for(int i = 0; i < aobj.length; i++)
            aobj1[i] = aobj[i];

        aobj1[aobj.length] = obj;
        return ((Object) (aobj1));
    }

    static int memberp(Object obj, Object obj1)
    {
        if(obj1 instanceof Object[])
        {
            Object aobj[] = (Object[])obj1;
            for(int i = 0; i < aobj.length; i++)
                if(Logo.prs(obj).equals(Logo.prs(aobj[i])))
                    return i + 1;

            return 0;
        }
        if(obj instanceof Object[])
            return 0;
        String s = Logo.prs(obj);
        String s1 = Logo.prs(obj1);
        for(int j = 0; j < s1.length(); j++)
            if(s.regionMatches(true, 0, s1, j, s.length()))
                return j + 1;

        return 0;
    }

    Object prim_first(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
            return ((Object[])obj)[0];
        else
            return Logo.prs(obj).substring(0, 1);
    }

    Object prim_last(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
        {
            Object aobj[] = (Object[])obj;
            return aobj[aobj.length - 1];
        } else
        {
            String s = Logo.prs(obj);
            return s.substring(s.length() - 1, s.length());
        }
    }

    Object prim_word(Object aobj[], LContext lcontext)
    {
        String s = "";
        for(int i = 0; i < aobj.length; i++)
            s = s + Logo.prs(aobj[i]);

        return s;
    }

    Object prim_butfirst(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
        {
            Object aobj[] = (Object[])obj;
            return copyList(aobj, 1, aobj.length - 1);
        } else
        {
            String s = Logo.prs(obj);
            return s.substring(1, s.length());
        }
    }

    Object prim_butlast(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
        {
            Object aobj[] = (Object[])obj;
            return copyList(aobj, 0, aobj.length - 1);
        } else
        {
            String s = Logo.prs(obj);
            return s.substring(0, s.length() - 1);
        }
    }

    Object prim_fput(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[] = (Object[])Logo.aList(obj1, lcontext);
        Object aobj1[] = new Object[aobj.length + 1];
        aobj1[0] = obj;
        for(int i = 0; i < aobj.length; i++)
            aobj1[i + 1] = aobj[i];

        return ((Object) (aobj1));
    }

    Object prim_lput(Object obj, Object obj1, LContext lcontext)
    {
        return lput(obj, Logo.aList(obj1, lcontext));
    }

    Object prim_item(Object obj, Object obj1, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext) - 1;
        return (obj1 instanceof Object[]) ? ((Object[])obj1)[i] : Logo.prs(obj1).substring(i, i + 1);
    }

    Object prim_nth(Object obj, Object obj1, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        return (obj1 instanceof Object[]) ? ((Object[])obj1)[i] : Logo.prs(obj1).substring(i, i + 1);
    }

    Object prim_emptyp(Object obj, LContext lcontext)
    {
        return new Boolean((obj instanceof Object[]) ? ((Object[])obj).length == 0 : Logo.prs(obj).length() == 0);
    }

    Object prim_count(Object obj, LContext lcontext)
    {
        return new Long((obj instanceof Object[]) ? ((Object[])obj).length : Logo.prs(obj).length());
    }

    Object prim_wordp(Object obj, LContext lcontext)
    {
        return new Boolean(!(obj instanceof Object[]));
    }

    Object prim_listp(Object obj, LContext lcontext)
    {
        return new Boolean(obj instanceof Object[]);
    }

    Object prim_memberp(Object obj, Object obj1, LContext lcontext)
    {
        return new Boolean(memberp(obj, obj1) != 0);
    }

    Object prim_itempos(Object obj, Object obj1, LContext lcontext)
    {
        int i = memberp(obj, obj1);
        if(i != 0)
        {
            return new Long(i);
        } else
        {
            Logo.error(lcontext.cfun + " doesn't like " + Logo.prs(obj) + " as input", lcontext);
            return null;
        }
    }

    Object prim_setitem(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        ((Object[])Logo.aList(obj1, lcontext))[Logo.anInt(obj, lcontext) - 1] = obj2;
        return null;
    }

    Object prim_setnth(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        ((Object[])Logo.aList(obj1, lcontext))[Logo.anInt(obj, lcontext)] = obj2;
        return null;
    }

    Object prim_removeitem(Object obj, Object obj1, LContext lcontext)
    {
        Object aobj[] = Logo.aList(obj1, lcontext);
        return removeItem(aobj, memberp(obj, ((Object) (aobj))));
    }

    Object prim_removeitempos(Object obj, Object obj1, LContext lcontext)
    {
        return removeItem(Logo.aList(obj1, lcontext), Logo.anInt(obj, lcontext));
    }

    Object prim_sentence(Object aobj[], LContext lcontext)
    {
        Object aobj1[] = new Object[0];
        for(int i = 0; i < aobj.length; i++)
            aobj1 = (Object[])addToList(aobj1, aobj[i]);

        return ((Object) (aobj1));
    }

    Object prim_list(Object aobj[], LContext lcontext)
    {
        Object aobj1[] = new Object[aobj.length];
        for(int i = 0; i < aobj.length; i++)
            aobj1[i] = aobj[i];

        return ((Object) (aobj1));
    }

    Object prim_makelist(Object obj, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        Object aobj[] = new Object[i];
        for(int j = 0; j < i; j++)
            aobj[j] = ((Object) (new Object[0]));

        return ((Object) (aobj));
    }

    Object prim_copylist(Object obj, LContext lcontext)
    {
        Object aobj[] = Logo.aList(obj, lcontext);
        return copyList(aobj, 0, aobj.length);
    }

    Object prim_parse(Object obj, LContext lcontext)
    {
        return ((Object) (Logo.parse(Logo.aString(obj, lcontext), lcontext)));
    }

    Object prim_char(Object obj, LContext lcontext)
    {
        char ac[] = new char[1];
        ac[0] = (char)Logo.anInt(obj, lcontext);
        return new String(ac);
    }

    Object prim_ascii(Object obj, LContext lcontext)
    {
        return new Long(Logo.aString(obj, lcontext).charAt(0));
    }

    Object prim_reverse(Object obj, LContext lcontext)
    {
        Object aobj[] = Logo.aList(obj, lcontext);
        Object aobj1[] = new Object[aobj.length];
        for(int i = 0; i < aobj.length; i++)
            aobj1[i] = aobj[aobj.length - i - 1];

        return ((Object) (aobj1));
    }

    Object prim_substring(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        String s = Logo.prs(obj);
        int i = Logo.anInt(obj1, lcontext);
        int j = Logo.anInt(obj2, lcontext);
        if(i == -1)
            return s.substring(s.length() - j, s.length());
        if(j == -1)
            return s.substring(i, s.length());
        else
            return s.substring(i, i + j);
    }

    Object prim_ucase(Object obj, LContext lcontext)
    {
        return Logo.prs(obj).toUpperCase();
    }

    Object prim_insert(Object obj, Object obj1, Object obj2, LContext lcontext)
    {
        Object aobj[] = Logo.aList(obj, lcontext);
        int i = Logo.anInt(obj1, lcontext) - 1;
        if(0 > i || i > aobj.length)
            Logo.error(lcontext.cfun + " doesn't like " + Logo.prs(obj1) + " as input", lcontext);
        Object aobj1[] = new Object[aobj.length + 1];
        int j = 0;
        for(int k = 0; k < aobj.length; k++)
        {
            if(k == i)
                aobj1[j++] = obj2;
            aobj1[j++] = aobj[k];
        }

        if(i == aobj.length)
            aobj1[j] = obj2;
        return ((Object) (aobj1));
    }

    static String primlist[] = {
        "first", "1", "last", "1", "word", "i2", "butfirst", "1", "bf", "1", 
        "butlast", "1", "bl", "1", "fput", "2", "lput", "2", "item", "2", 
        "nth", "2", "empty?", "1", "count", "1", "word?", "1", "list?", "1", 
        "member?", "2", "itempos", "2", "setitem", "3", "setnth", "3", "removeitem", "2", 
        "removeitempos", "2", "sentence", "2", "se", "i2", "list", "i2", "makelist", "1", 
        "copylist", "1", "parse", "1", "char", "1", "ascii", "1", "reverse", "1", 
        "substring", "3", "ucase", "1", "insert", "3"
    };

}
