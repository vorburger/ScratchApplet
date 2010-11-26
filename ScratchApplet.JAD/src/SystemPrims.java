// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SystemPrims.java

import java.io.PrintStream;
import java.util.Vector;

class SystemPrims extends Primitives
{

    SystemPrims()
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
            return prim_resett(lcontext);

        case 1: // '\001'
            return prim_timer(lcontext);

        case 2: // '\002'
            return prim_wait(aobj[0], lcontext);

        case 3: // '\003'
            return prim_mwait(aobj[0], lcontext);

        case 4: // '\004'
            return prim_eq(aobj[0], aobj[1], lcontext);

        case 5: // '\005'
            return prim_parleft(lcontext);

        case 6: // '\006'
            return prim_parright(lcontext);

        case 7: // '\007'
            return new Boolean(true);

        case 8: // '\b'
            return new Boolean(false);

        case 9: // '\t'
            return prim_hexw(aobj[0], aobj[1], lcontext);

        case 10: // '\n'
            return "\t";

        case 11: // '\013'
            return aobj[0].getClass();

        case 12: // '\f'
            return prim_class(aobj[0], lcontext);

        case 13: // '\r'
            return prstring(aobj[0]);

        case 14: // '\016'
            lcontext.tyo.println(Logo.prs(aobj[0]));
            return null;

        case 15: // '\017'
            lcontext.tyo.print(Logo.prs(aobj[0]));
            return null;

        case 16: // '\020'
            return null;
        }
        return null;
    }

    Object prim_resett(LContext lcontext)
    {
        Logo.starttime = System.currentTimeMillis();
        return null;
    }

    Object prim_timer(LContext lcontext)
    {
        return new Double(System.currentTimeMillis() - Logo.starttime);
    }

    Object prim_wait(Object obj, LContext lcontext)
    {
        int i = (int)(100D * Logo.aDouble(obj, lcontext));
        sleepForMSecs(i, lcontext);
        return null;
    }

    Object prim_mwait(Object obj, LContext lcontext)
    {
        int i = Logo.anInt(obj, lcontext);
        sleepForMSecs(i, lcontext);
        return null;
    }

    void sleepForMSecs(int i, LContext lcontext)
    {
        if(i <= 0)
            return;
        long l = System.currentTimeMillis();
        for(long l1 = System.currentTimeMillis() - l; l1 >= 0L && l1 < (long)i; l1 = System.currentTimeMillis() - l)
        {
            if(lcontext.timeToStop)
                Logo.error("Stopped!!!", lcontext);
            try
            {
                Thread.sleep(Math.min((long)i - l1, 10L));
            }
            catch(InterruptedException interruptedexception) { }
        }

    }

    Object prim_eq(Object obj, Object obj1, LContext lcontext)
    {
        return new Boolean(obj.equals(obj1));
    }

    Object prim_parright(LContext lcontext)
    {
        Logo.error("Missing \"(\"", lcontext);
        return null;
    }

    Object prim_parleft(LContext lcontext)
    {
        if(ipmnext(lcontext.iline))
            return ipmcall(lcontext);
        Object obj = Logo.eval(lcontext);
        Object obj1 = lcontext.iline.next();
        if((obj1 instanceof Symbol) && ((Symbol)obj1).pname.equals(")"))
        {
            return obj;
        } else
        {
            Logo.error("Missing \")\"", lcontext);
            return null;
        }
    }

    boolean ipmnext(MapList maplist)
    {
        return ((Symbol)maplist.peek()).fcn.ipm;
        Exception exception;
        exception;
        return false;
    }

    Object ipmcall(LContext lcontext)
    {
        Vector vector = new Vector();
        lcontext.cfun = (Symbol)lcontext.iline.next();
        for(; !finIpm(lcontext.iline); vector.addElement(Logo.evalOneArg(lcontext.iline, lcontext)));
        Object aobj[] = new Object[vector.size()];
        vector.copyInto(aobj);
        return Logo.evalSym(lcontext.cfun, aobj, lcontext);
    }

    boolean finIpm(MapList maplist)
    {
        if(maplist.eof())
            return true;
        Object obj = maplist.peek();
        if((obj instanceof Symbol) && ((Symbol)obj).pname.equals(")"))
        {
            maplist.next();
            return true;
        } else
        {
            return false;
        }
    }

    Object prim_hexw(Object obj, Object obj1, LContext lcontext)
    {
        Logo.anInt(obj, lcontext);
        String s = Logo.prs(obj, 16);
        int i = Logo.anInt(obj1, lcontext);
        String s1 = "00000000".substring((8 - i) + s.length());
        return s1 + s;
    }

    Object prim_class(Object obj, LContext lcontext)
    {
        return Class.forName(Logo.prs(obj));
        Object obj1;
        obj1;
        break MISSING_BLOCK_LABEL_13;
        obj1;
        return "";
    }

    String prstring(Object obj)
    {
        if((obj instanceof Number) && Logo.isInt((Number)obj))
            return Long.toString(((Number)obj).longValue(), 10);
        if(obj instanceof String)
            return "|" + (String)obj + "|";
        if(obj instanceof Object[])
        {
            String s = "";
            Object aobj[] = (Object[])obj;
            for(int i = 0; i < aobj.length; i++)
            {
                if(aobj[i] instanceof Object[])
                    s = s + "[";
                s = s + prstring(aobj[i]);
                if(aobj[i] instanceof Object[])
                    s = s + "]";
                if(i != aobj.length - 1)
                    s = s + " ";
            }

            return s;
        } else
        {
            return obj.toString();
        }
    }

    static String primlist[] = {
        "resett", "0", "%timer", "0", "wait", "1", "mwait", "1", "eq", "2", 
        "(", "0", ")", "0", "true", "0", "false", "0", "hexw", "2", 
        "tab", "0", "classof", "1", "class", "1", "string", "1", "print", "1", 
        "prs", "1", "ignore", "1"
    };

}
