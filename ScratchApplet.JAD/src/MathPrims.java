// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MathPrims.java


class MathPrims extends Primitives
{

    MathPrims()
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
            return prim_sum(aobj, lcontext);

        case 1: // '\001'
            return prim_remainder(aobj[0], aobj[1], lcontext);

        case 2: // '\002'
        case 3: // '\003'
            return prim_diff(aobj[0], aobj[1], lcontext);

        case 4: // '\004'
            return prim_product(aobj, lcontext);

        case 5: // '\005'
            return prim_quotient(aobj[0], aobj[1], lcontext);

        case 6: // '\006'
            return prim_greaterp(aobj[0], aobj[1], lcontext);

        case 7: // '\007'
            return prim_lessp(aobj[0], aobj[1], lcontext);

        case 8: // '\b'
            return prim_int(aobj[0], lcontext);

        case 9: // '\t'
            return prim_minus(aobj[0], lcontext);

        case 10: // '\n'
            return prim_round(aobj[0], lcontext);

        case 11: // '\013'
            return prim_sqrt(aobj[0], lcontext);

        case 12: // '\f'
            return prim_sin(aobj[0], lcontext);

        case 13: // '\r'
            return prim_cos(aobj[0], lcontext);

        case 14: // '\016'
            return prim_tan(aobj[0], lcontext);

        case 15: // '\017'
            return prim_abs(aobj[0], lcontext);

        case 16: // '\020'
            return prim_power(aobj[0], aobj[1], lcontext);

        case 17: // '\021'
            return prim_arctan(aobj[0], lcontext);

        case 18: // '\022'
            return prim_pi(lcontext);

        case 19: // '\023'
            return prim_exp(aobj[0], lcontext);

        case 20: // '\024'
            return prim_arctan2(aobj[0], aobj[1], lcontext);

        case 21: // '\025'
            return prim_ln(aobj[0], lcontext);

        case 22: // '\026'
            return prim_logand(aobj[0], aobj[1], lcontext);

        case 23: // '\027'
            return prim_logior(aobj[0], aobj[1], lcontext);

        case 24: // '\030'
            return prim_logxor(aobj[0], aobj[1], lcontext);

        case 25: // '\031'
            return prim_lsh(aobj[0], aobj[1], lcontext);

        case 26: // '\032'
            return prim_and(aobj, lcontext);

        case 27: // '\033'
            return prim_or(aobj, lcontext);

        case 28: // '\034'
            return prim_not(aobj[0], lcontext);

        case 29: // '\035'
            return prim_random(aobj[0], lcontext);

        case 30: // '\036'
            return prim_min(aobj, lcontext);

        case 31: // '\037'
            return prim_max(aobj, lcontext);

        case 32: // ' '
            return prim_numberp(aobj[0], lcontext);

        case 33: // '!'
            return prim_sum(aobj, lcontext);

        case 34: // '"'
            return prim_diff(aobj[0], aobj[1], lcontext);

        case 35: // '#'
            return prim_product(aobj, lcontext);

        case 36: // '$'
            return prim_quotient(aobj[0], aobj[1], lcontext);

        case 37: // '%'
            return prim_lessp(aobj[0], aobj[1], lcontext);

        case 38: // '&'
            return prim_greaterp(aobj[0], aobj[1], lcontext);

        case 39: // '\''
            return prim_equalp(aobj, lcontext);

        case 40: // '('
            return prim_equalp(aobj, lcontext);

        case 41: // ')'
            return prim_remainder(aobj[0], aobj[1], lcontext);

        case 42: // '*'
            return new Double(Math.random());

        case 43: // '+'
            return prim_strequ(aobj[0], aobj[1], lcontext);

        case 44: // ','
            return prim_arcsin(aobj[0], lcontext);

        case 45: // '-'
            return prim_arccos(aobj[0], lcontext);

        case 46: // '.'
            return prim_strcmp(aobj[0], aobj[1], lcontext);
        }
        return null;
    }

    Object prim_sum(Object aobj[], LContext lcontext)
    {
        double d = 0.0D;
        for(int i = 0; i < aobj.length; i++)
            d += Logo.aDouble(aobj[i], lcontext);

        return new Double(d);
    }

    Object prim_remainder(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Logo.aDouble(obj, lcontext) % Logo.aDouble(obj1, lcontext));
    }

    Object prim_diff(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Logo.aDouble(obj, lcontext) - Logo.aDouble(obj1, lcontext));
    }

    Object prim_product(Object aobj[], LContext lcontext)
    {
        double d = 1.0D;
        for(int i = 0; i < aobj.length; i++)
            d *= Logo.aDouble(aobj[i], lcontext);

        return new Double(d);
    }

    Object prim_quotient(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Logo.aDouble(obj, lcontext) / Logo.aDouble(obj1, lcontext));
    }

    Object prim_greaterp(Object obj, Object obj1, LContext lcontext)
    {
        return new Boolean(Logo.aDouble(obj, lcontext) > Logo.aDouble(obj1, lcontext));
    }

    Object prim_lessp(Object obj, Object obj1, LContext lcontext)
    {
        return new Boolean(Logo.aDouble(obj, lcontext) < Logo.aDouble(obj1, lcontext));
    }

    Object prim_int(Object obj, LContext lcontext)
    {
        return new Double((new Double(Logo.aDouble(obj, lcontext))).intValue());
    }

    Object prim_minus(Object obj, LContext lcontext)
    {
        return new Double(0.0D - Logo.aDouble(obj, lcontext));
    }

    Object prim_round(Object obj, LContext lcontext)
    {
        return new Double(Math.round(Logo.aDouble(obj, lcontext)));
    }

    Object prim_sqrt(Object obj, LContext lcontext)
    {
        return new Double(Math.sqrt(Logo.aDouble(obj, lcontext)));
    }

    Object prim_sin(Object obj, LContext lcontext)
    {
        return new Double(Math.sin(Logo.aDouble(obj, lcontext) / 57.295779513082323D));
    }

    Object prim_cos(Object obj, LContext lcontext)
    {
        return new Double(Math.cos(Logo.aDouble(obj, lcontext) / 57.295779513082323D));
    }

    Object prim_tan(Object obj, LContext lcontext)
    {
        return new Double(Math.tan(Logo.aDouble(obj, lcontext) / 57.295779513082323D));
    }

    Object prim_abs(Object obj, LContext lcontext)
    {
        return new Double(Math.abs(Logo.aDouble(obj, lcontext)));
    }

    Object prim_power(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Math.pow(Logo.aDouble(obj, lcontext), Logo.aDouble(obj1, lcontext)));
    }

    Object prim_arcsin(Object obj, LContext lcontext)
    {
        return new Double(57.295779513082323D * Math.asin(Logo.aDouble(obj, lcontext)));
    }

    Object prim_arccos(Object obj, LContext lcontext)
    {
        return new Double(57.295779513082323D * Math.acos(Logo.aDouble(obj, lcontext)));
    }

    Object prim_arctan(Object obj, LContext lcontext)
    {
        return new Double(57.295779513082323D * Math.atan(Logo.aDouble(obj, lcontext)));
    }

    Object prim_pi(LContext lcontext)
    {
        return new Double(3.1415926535897931D);
    }

    Object prim_exp(Object obj, LContext lcontext)
    {
        return new Double(Math.exp(Logo.aDouble(obj, lcontext)));
    }

    Object prim_arctan2(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(57.295779513082323D * Math.atan2(Logo.aDouble(obj, lcontext), Logo.aDouble(obj1, lcontext)));
    }

    Object prim_ln(Object obj, LContext lcontext)
    {
        return new Double(Math.log(Logo.aDouble(obj, lcontext)));
    }

    Object prim_logand(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Logo.anInt(obj, lcontext) & Logo.anInt(obj1, lcontext));
    }

    Object prim_logior(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Logo.anInt(obj, lcontext) | Logo.anInt(obj1, lcontext));
    }

    Object prim_logxor(Object obj, Object obj1, LContext lcontext)
    {
        return new Double(Logo.anInt(obj, lcontext) ^ Logo.anInt(obj1, lcontext));
    }

    Object prim_lsh(Object obj, Object obj1, LContext lcontext)
    {
        int i = Logo.anInt(obj1, lcontext);
        int j = Logo.anInt(obj, lcontext);
        return i <= 0 ? new Double(j >> -i) : new Double(j << i);
    }

    Object prim_and(Object aobj[], LContext lcontext)
    {
        boolean flag = true;
        for(int i = 0; i < aobj.length; i++)
            flag &= Logo.aBoolean(aobj[i], lcontext);

        return new Boolean(flag);
    }

    Object prim_or(Object aobj[], LContext lcontext)
    {
        boolean flag = false;
        for(int i = 0; i < aobj.length; i++)
            flag |= Logo.aBoolean(aobj[i], lcontext);

        return new Boolean(flag);
    }

    Object prim_not(Object obj, LContext lcontext)
    {
        return new Boolean(!Logo.aBoolean(obj, lcontext));
    }

    Object prim_random(Object obj, LContext lcontext)
    {
        return new Double(Math.floor(Math.random() * (double)Logo.anInt(obj, lcontext)));
    }

    Object prim_min(Object aobj[], LContext lcontext)
    {
        if(aobj.length == 0)
            Logo.error("Min needs at least one input", lcontext);
        double d = Logo.aDouble(aobj[0], lcontext);
        for(int i = 1; i < aobj.length; i++)
            d = Math.min(d, Logo.aDouble(aobj[i], lcontext));

        return new Double(d);
    }

    Object prim_max(Object aobj[], LContext lcontext)
    {
        if(aobj.length == 0)
            Logo.error("Max needs at least one input", lcontext);
        double d = Logo.aDouble(aobj[0], lcontext);
        for(int i = 1; i < aobj.length; i++)
            d = Math.max(d, Logo.aDouble(aobj[i], lcontext));

        return new Double(d);
    }

    Object prim_numberp(Object obj, LContext lcontext)
    {
        return new Boolean(obj instanceof Number);
    }

    Object prim_equalp(Object aobj[], LContext lcontext)
    {
        if(aobj.length == 0)
            Logo.error("Equal needs at least one input", lcontext);
        Object obj = aobj[0];
        for(int i = 1; i < aobj.length; i++)
            if(obj != aobj[i] && !Logo.prs(obj).equals(Logo.prs(aobj[i])))
                return new Boolean(false);

        return new Boolean(true);
    }

    Object prim_strequ(Object obj, Object obj1, LContext lcontext)
    {
        String s = convertToString(obj);
        String s1 = convertToString(obj1);
        if(s == null || s1 == null)
            return new Boolean(false);
        else
            return new Boolean(s.equalsIgnoreCase(s1));
    }

    Object prim_strcmp(Object obj, Object obj1, LContext lcontext)
    {
        String s = convertToString(obj);
        String s1 = convertToString(obj1);
        if(s == null || s1 == null)
            return new Boolean(false);
        else
            return new Double(s.compareToIgnoreCase(s1));
    }

    String convertToString(Object obj)
    {
        if(obj instanceof String)
            return (String)obj;
        if(obj instanceof Symbol)
            return ((Symbol)obj).pname;
        if(obj instanceof QuotedSymbol)
            return ((QuotedSymbol)obj).sym.pname;
        else
            return null;
    }

    static String primlist[] = {
        "sum", "i2", "remainder", "2", "difference", "2", "diff", "2", "product", "i2", 
        "quotient", "2", "greater?", "2", "less?", "2", "int", "1", "minus", "1", 
        "round", "1", "sqrt", "1", "sin", "1", "cos", "1", "tan", "1", 
        "abs", "1", "power", "2", "arctan", "1", "pi", "0", "exp", "1", 
        "arctan2", "2", "ln", "1", "logand", "2", "logior", "2", "logxor", "2", 
        "lsh", "2", "and", "i2", "or", "i2", "not", "1", "random", "1", 
        "min", "i2", "max", "i2", "number?", "1", "+", "-2", "-", "-2", 
        "*", "-3", "/", "-3", "<", "-1", ">", "-1", "=", "-1", 
        "equal?", "i2", "%", "-3", "rand", "0", "strequ", "2", "arcsin", "1", 
        "arccos", "1", "strcmp", "2"
    };
    static final double degtor = 57.295779513082323D;

}
