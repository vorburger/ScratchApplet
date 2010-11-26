// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Hashtable;

public class Logo
{

    public Logo()
    {
    }

    static String runToplevel(Object aobj[], LContext lcontext)
    {
        lcontext.iline = new MapList(aobj);
        lcontext.timeToStop = false;
        try
        {
            evLine(lcontext);
        }
        catch(LogoError logoerror)
        {
            if(logoerror.getMessage() != null)
                return logoerror.getMessage();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return exception.toString();
        }
        catch(Error error1)
        {
            return error1.toString();
        }
        return null;
    }

    static void evLine(LContext lcontext)
    {
        do
        {
            if(lcontext.iline.eof() || lcontext.ufunresult != null)
                break;
            Object obj;
            if((obj = eval(lcontext)) != null)
                error("You don't say what to do with " + prs(obj), lcontext);
        } while(true);
    }

    static Object eval(LContext lcontext)
    {
        Object obj;
        for(obj = evalToken(lcontext); infixNext(lcontext.iline, lcontext); obj = evalInfix(obj, lcontext))
            if(obj instanceof Nothing)
                error(lcontext.iline.peek() + " needs more inputs", lcontext);

        return obj;
    }

    static Object evalToken(LContext lcontext)
    {
        Object obj = lcontext.iline.next();
        if(obj instanceof QuotedSymbol)
            return ((QuotedSymbol)obj).sym;
        if(obj instanceof DottedSymbol)
            return getValue(((DottedSymbol)obj).sym, lcontext);
        if(obj instanceof Symbol)
            return evalSym((Symbol)obj, null, lcontext);
        if(obj instanceof String)
            return evalSym(intern((String)obj, lcontext), null, lcontext);
        else
            return obj;
    }

    static Object evalSym(Symbol symbol, Object aobj[], LContext lcontext)
    {
        Symbol symbol1;
        int i;
        Object obj;
        if(lcontext.timeToStop)
            error("Stopped!!!", lcontext);
        if(symbol.fcn == null)
            error("I don't know how to " + symbol, lcontext);
        symbol1 = lcontext.cfun;
        lcontext.cfun = symbol;
        i = lcontext.priority;
        lcontext.priority = 0;
        obj = null;
        Function function = symbol.fcn;
        int j = function.nargs;
        if(aobj == null)
            aobj = evalArgs(j, lcontext);
        obj = function.instance.dispatch(function.dispatchOffset, aobj, lcontext);
        lcontext.cfun = symbol1;
        lcontext.priority = i;
        break MISSING_BLOCK_LABEL_162;
        RuntimeException runtimeexception;
        runtimeexception;
        errorHandler(symbol, aobj, runtimeexception, lcontext);
        lcontext.cfun = symbol1;
        lcontext.priority = i;
        break MISSING_BLOCK_LABEL_162;
        Exception exception;
        exception;
        lcontext.cfun = symbol1;
        lcontext.priority = i;
        throw exception;
        if(lcontext.mustOutput && obj == null)
            error(symbol + " didn't output to " + lcontext.cfun, lcontext);
        return obj;
    }

    static Object[] evalArgs(int i, LContext lcontext)
    {
        boolean flag;
        Object aobj[];
        flag = lcontext.mustOutput;
        lcontext.mustOutput = true;
        aobj = new Object[i];
        for(int j = 0; j < i; j++)
        {
            if(lcontext.iline.eof())
                error(lcontext.cfun + " needs more inputs", lcontext);
            aobj[j] = eval(lcontext);
            if(aobj[j] instanceof Nothing)
                error(lcontext.cfun + " needs more inputs", lcontext);
        }

        lcontext.mustOutput = flag;
        break MISSING_BLOCK_LABEL_128;
        Exception exception;
        exception;
        lcontext.mustOutput = flag;
        throw exception;
        return aobj;
    }

    static void runCommand(Object aobj[], LContext lcontext)
    {
        boolean flag;
        flag = lcontext.mustOutput;
        lcontext.mustOutput = false;
        runList(aobj, lcontext);
        lcontext.mustOutput = flag;
        break MISSING_BLOCK_LABEL_32;
        Exception exception;
        exception;
        lcontext.mustOutput = flag;
        throw exception;
    }

    static Object runList(Object aobj[], LContext lcontext)
    {
        MapList maplist;
        Object obj;
        maplist = lcontext.iline;
        lcontext.iline = new MapList(aobj);
        obj = null;
        if(lcontext.mustOutput)
            obj = eval(lcontext);
        else
            evLine(lcontext);
        checkListEmpty(lcontext.iline, lcontext);
        lcontext.iline = maplist;
        break MISSING_BLOCK_LABEL_64;
        Exception exception;
        exception;
        lcontext.iline = maplist;
        throw exception;
        return obj;
    }

    static Object evalOneArg(MapList maplist, LContext lcontext)
    {
        boolean flag;
        MapList maplist1;
        flag = lcontext.mustOutput;
        lcontext.mustOutput = true;
        maplist1 = lcontext.iline;
        lcontext.iline = maplist;
        Object obj = eval(lcontext);
        lcontext.iline = maplist1;
        lcontext.mustOutput = flag;
        return obj;
        Exception exception;
        exception;
        lcontext.iline = maplist1;
        lcontext.mustOutput = flag;
        throw exception;
    }

    static boolean infixNext(MapList maplist, LContext lcontext)
    {
        Object obj = null;
        Function function = null;
        return !maplist.eof() && ((obj = maplist.peek()) instanceof Symbol) && (function = ((Symbol)obj).fcn) != null && function.nargs < lcontext.priority;
    }

    static Object evalInfix(Object obj, LContext lcontext)
    {
        Symbol symbol;
        Function function;
        Symbol symbol1;
        int i;
        Object obj1;
        Object aobj[];
        symbol = (Symbol)lcontext.iline.next();
        function = symbol.fcn;
        symbol1 = lcontext.cfun;
        lcontext.cfun = symbol;
        i = lcontext.priority;
        lcontext.priority = function.nargs;
        obj1 = null;
        aobj = new Object[2];
        aobj[0] = obj;
        Object aobj1[] = evalArgs(1, lcontext);
        aobj[1] = aobj1[0];
        obj1 = function.instance.dispatch(function.dispatchOffset, aobj, lcontext);
        lcontext.cfun = symbol1;
        lcontext.priority = i;
        break MISSING_BLOCK_LABEL_144;
        RuntimeException runtimeexception;
        runtimeexception;
        errorHandler(symbol, aobj, runtimeexception, lcontext);
        lcontext.cfun = symbol1;
        lcontext.priority = i;
        break MISSING_BLOCK_LABEL_144;
        Exception exception;
        exception;
        lcontext.cfun = symbol1;
        lcontext.priority = i;
        throw exception;
        if(lcontext.mustOutput && obj1 == null)
            error(symbol + " didn't output to " + lcontext.cfun, lcontext);
        return obj1;
    }

    static Symbol intern(String s, LContext lcontext)
    {
        String s1;
        if(s.length() == 0)
            s1 = s;
        else
        if(s.charAt(0) == '|')
            s1 = s = s.substring(1);
        else
            s1 = s;
        Symbol symbol = (Symbol)lcontext.oblist.get(s1);
        if(symbol == null)
            lcontext.oblist.put(s1, symbol = new Symbol(s));
        return symbol;
    }

    static Object[] parse(String s, LContext lcontext)
    {
        TokenStream tokenstream = new TokenStream(s);
        return tokenstream.readList(lcontext);
    }

    static String prs(Object obj)
    {
        return prs(obj, 10);
    }

    static String prs(Object obj, int i)
    {
        return prs(obj, i, new HashSet());
    }

    static String prs(Object obj, int i, HashSet hashset)
    {
        if((obj instanceof Number) && i == 16)
            return Long.toString(((Number)obj).longValue(), 16).toUpperCase();
        if((obj instanceof Number) && i == 8)
            return Long.toString(((Number)obj).longValue(), 8);
        if((obj instanceof Number) && isInt((Number)obj))
            return Long.toString(((Number)obj).longValue(), 10);
        if(obj instanceof Object[])
        {
            Object aobj[] = (Object[])obj;
            if(aobj.length > 0 && hashset.contains(obj))
                return "...";
            if(aobj.length > 0)
                hashset.add(obj);
            String s = "";
            for(int j = 0; j < aobj.length; j++)
            {
                if(aobj[j] instanceof Object[])
                    s = s + "[";
                s = s + prs(aobj[j], i, hashset);
                if(aobj[j] instanceof Object[])
                    s = s + "]";
                if(j != aobj.length - 1)
                    s = s + " ";
            }

            return s;
        } else
        {
            return obj.toString();
        }
    }

    static boolean isInt(Number number)
    {
        return number.doubleValue() == (new Integer(number.intValue())).doubleValue();
    }

    static boolean aValidNumber(String s)
    {
        if(s.length() == 1 && "0123456789".indexOf(s.charAt(0)) == -1)
            return false;
        if("eE.+-0123456789".indexOf(s.charAt(0)) == -1)
            return false;
        for(int i = 1; i < s.length(); i++)
            if("eE.0123456789".indexOf(s.charAt(i)) == -1)
                return false;

        return true;
    }

    static Object getValue(Symbol symbol, LContext lcontext)
    {
        Object obj;
        if((obj = symbol.value) != null)
        {
            return obj;
        } else
        {
            error(symbol + " has no value", lcontext);
            return null;
        }
    }

    static void setValue(Symbol symbol, Object obj, LContext lcontext)
    {
        symbol.value = obj;
    }

    static double aDouble(Object obj, LContext lcontext)
    {
        if(obj instanceof Double)
            return ((Double)obj).doubleValue();
        String s = prs(obj);
        if(s.length() > 0 && aValidNumber(s))
        {
            return Double.valueOf(s).doubleValue();
        } else
        {
            error(lcontext.cfun + " doesn't like " + prs(obj) + " as input", lcontext);
            return 0.0D;
        }
    }

    static int anInt(Object obj, LContext lcontext)
    {
        if(obj instanceof Double)
            return ((Double)obj).intValue();
        String s = prs(obj);
        if(aValidNumber(s))
        {
            return Double.valueOf(s).intValue();
        } else
        {
            error(lcontext.cfun + " doesn't like " + s + " as input", lcontext);
            return 0;
        }
    }

    static long aLong(Object obj, LContext lcontext)
    {
        if(obj instanceof Double)
            return ((Double)obj).longValue();
        String s = prs(obj);
        if(aValidNumber(s))
        {
            return Double.valueOf(s).longValue();
        } else
        {
            error(lcontext.cfun + " doesn't like " + s + " as input", lcontext);
            return 0L;
        }
    }

    static boolean aBoolean(Object obj, LContext lcontext)
    {
        if(obj instanceof Boolean)
            return ((Boolean)obj).booleanValue();
        if(obj instanceof Symbol)
        {
            return ((Symbol)obj).pname.equals("true");
        } else
        {
            error(lcontext.cfun + " doesn't like " + prs(obj) + " as input", lcontext);
            return false;
        }
    }

    static Object[] aList2Double(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
        {
            if(((Object[])obj).length == 2 && (((Object[])obj)[0] instanceof Double) && (((Object[])obj)[1] instanceof Double))
                return (Object[])obj;
            error(lcontext.cfun + " doesn't like " + prs(obj) + " as input", lcontext);
        }
        return null;
    }

    static Object[] aList(Object obj, LContext lcontext)
    {
        if(obj instanceof Object[])
        {
            return (Object[])obj;
        } else
        {
            error(lcontext.cfun + " doesn't like " + prs(obj) + " as input", lcontext);
            return null;
        }
    }

    static Symbol aSymbol(Object obj, LContext lcontext)
    {
        if(obj instanceof Symbol)
            return (Symbol)obj;
        if(obj instanceof String)
            return intern((String)obj, lcontext);
        if(obj instanceof Number)
        {
            String s = String.valueOf(((Number)obj).longValue());
            return intern(s, lcontext);
        } else
        {
            error(lcontext.cfun + " doesn't like " + prs(obj) + " as input", lcontext);
            return null;
        }
    }

    static String aString(Object obj, LContext lcontext)
    {
        if(obj instanceof String)
            return (String)obj;
        if(obj instanceof Symbol)
        {
            return ((Symbol)obj).toString();
        } else
        {
            error(lcontext.cfun + " doesn't like " + prs(obj) + " as input", lcontext);
            return null;
        }
    }

    static void setupPrims(String as[], LContext lcontext)
    {
        for(int i = 0; i < as.length; i++)
            setupPrims(as[i], lcontext);

    }

    static void setupPrims(String s, LContext lcontext)
    {
        try
        {
            Class class1 = Class.forName(s);
            Primitives primitives = (Primitives)class1.newInstance();
            String as[] = primitives.primlist();
            for(int i = 0; i < as.length; i += 2)
            {
                String s1 = as[i + 1];
                boolean flag = s1.startsWith("i");
                if(flag)
                    s1 = s1.substring(1);
                Symbol symbol = intern(as[i], lcontext);
                symbol.fcn = new Function(primitives, Integer.parseInt(s1), i / 2, flag);
            }

        }
        catch(Exception exception)
        {
            System.out.println(exception.toString());
        }
    }

    static void checkListEmpty(MapList maplist, LContext lcontext)
    {
        if(!maplist.eof() && lcontext.ufunresult == null)
            error("You don't say what to do with " + prs(maplist.next()), lcontext);
    }

    static void errorHandler(Symbol symbol, Object aobj[], RuntimeException runtimeexception, LContext lcontext)
    {
        if((runtimeexception instanceof ArrayIndexOutOfBoundsException) || (runtimeexception instanceof StringIndexOutOfBoundsException) || (runtimeexception instanceof NegativeArraySizeException))
            error(symbol + " doesn't like " + prs(aobj[0]) + " as input", lcontext);
        else
            throw runtimeexception;
    }

    static void error(String s, LContext lcontext)
    {
        if(s.equals(""))
        {
            throw new LogoError(null);
        } else
        {
            s = s + (lcontext.ufun != null ? " in " + lcontext.ufun : "");
            throw new LogoError(s);
        }
    }

    static void readAllFunctions(String s, LContext lcontext)
    {
        TokenStream tokenstream = new TokenStream(s);
        do
            switch(findKeyWord(tokenstream))
            {
            case 0: // '\0'
                return;

            case 1: // '\001'
                doDefine(tokenstream, lcontext);
                break;

            case 2: // '\002'
                doTo(tokenstream, lcontext);
                break;
            }
        while(true);
    }

    static int findKeyWord(TokenStream tokenstream)
    {
        do
        {
            if(tokenstream.eof())
                return 0;
            if(tokenstream.startsWith("define "))
                return 1;
            if(tokenstream.startsWith("to "))
                return 2;
            tokenstream.skipToNextLine();
        } while(true);
    }

    static void doDefine(TokenStream tokenstream, LContext lcontext)
    {
        tokenstream.readToken(lcontext);
        Symbol symbol = aSymbol(tokenstream.readToken(lcontext), lcontext);
        Object aobj[] = aList(tokenstream.readToken(lcontext), lcontext);
        Object aobj1[] = aList(tokenstream.readToken(lcontext), lcontext);
        Ufun ufun = new Ufun(aobj, aobj1);
        symbol.fcn = new Function(ufun, aobj.length, 0);
    }

    static void doTo(TokenStream tokenstream, LContext lcontext)
    {
        Object aobj[] = parse(tokenstream.nextLine(), lcontext);
        Object aobj1[] = parse(readBody(tokenstream, lcontext), lcontext);
        Object aobj2[] = getArglistFromTitle(aobj);
        Symbol symbol = aSymbol(aobj[1], lcontext);
        Ufun ufun = new Ufun(aobj2, aobj1);
        symbol.fcn = new Function(ufun, aobj2.length, 0);
    }

    static String readBody(TokenStream tokenstream, LContext lcontext)
    {
        String s = "";
        do
        {
            if(tokenstream.eof())
                return s;
            String s1 = tokenstream.nextLine();
            if(s1.startsWith("end") && "end".equals(((Symbol)parse(s1, lcontext)[0]).pname))
                return s;
            s = s + " " + s1;
        } while(true);
    }

    static Object[] getArglistFromTitle(Object aobj[])
    {
        Object aobj1[] = new Object[aobj.length - 2];
        for(int i = 0; i < aobj1.length; i++)
            aobj1[i] = ((DottedSymbol)aobj[i + 2]).sym;

        return aobj1;
    }

    static long starttime = System.currentTimeMillis();

}
