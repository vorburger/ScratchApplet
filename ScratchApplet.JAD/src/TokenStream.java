// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java

import java.util.Vector;

class TokenStream
{

    TokenStream(String s)
    {
        offset = 0;
        str = s;
        skipSpace();
    }

    Object[] readList(LContext lcontext)
    {
        Vector vector = new Vector();
        Object obj;
        for(; !eof() && (obj = readToken(lcontext)) != null; vector.addElement(obj));
        Object aobj[] = new Object[vector.size()];
        vector.copyInto(aobj);
        return aobj;
    }

    Object readToken(LContext lcontext)
    {
        String s = next();
        if(s.length() > 2 && s.charAt(0) == '0' && s.charAt(1) == 'x')
            return new Double(Long.parseLong(s.substring(2), 16));
        break MISSING_BLOCK_LABEL_57;
        NumberFormatException numberformatexception;
        numberformatexception;
        if(s.length() > 1 && s.charAt(0) == '$')
            return new Double(Long.parseLong(s.substring(1), 16));
        break MISSING_BLOCK_LABEL_99;
        numberformatexception;
        if(s.length() > 1 && s.charAt(0) == '0')
            return new Double(Long.parseLong(s.substring(1), 8));
        break MISSING_BLOCK_LABEL_141;
        numberformatexception;
        if(s.equals("]"))
            return null;
        if(!Logo.aValidNumber(s))
            break MISSING_BLOCK_LABEL_168;
        Double double1 = Double.valueOf(s);
        return double1;
        numberformatexception;
        if(s.charAt(0) == '"')
            return new QuotedSymbol(Logo.intern(s.substring(1), lcontext));
        if(s.charAt(0) == ':')
            return new DottedSymbol(Logo.intern(s.substring(1), lcontext));
        if(s.equals("["))
            return ((Object) (readList(lcontext)));
        if(s.charAt(0) == '|')
            return s.substring(1);
        else
            return Logo.intern(s, lcontext);
    }

    boolean startsWith(String s)
    {
        return str.startsWith(s, offset);
    }

    void skipToNextLine()
    {
        for(; !eof() && "\n\r".indexOf(str.charAt(offset)) == -1; offset++);
        skipSpace();
    }

    void skipSpace()
    {
        while(!eof() && " ;,\t\r\n".indexOf(str.charAt(offset)) != -1) 
            if(peekChar().equals(";"))
                while(!eof() && "\n\r".indexOf(str.charAt(offset)) == -1) 
                    offset++;
            else
                offset++;
    }

    String nextLine()
    {
        String s;
        for(s = ""; !eof() && ";\n\r".indexOf(peekChar()) == -1; s = s + nextChar());
        skipSpace();
        return s;
    }

    String next()
    {
        String s = "";
        if(!delim(peekChar()))
            while(!eof() && !delim(peekChar())) 
            {
                if(peekChar().equals("|"))
                {
                    s = s + "|" + getVbarString();
                    skipSpace();
                    return s;
                }
                s = s + nextChar();
            }
        else
            s = nextChar();
        skipSpace();
        return s;
    }

    String getVbarString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        nextChar();
        do
        {
            if(eof())
                break;
            if(peekChar().equals("|"))
            {
                nextChar();
                break;
            }
            stringbuffer.append(nextChar());
        } while(true);
        return stringbuffer.toString();
    }

    boolean delim(String s)
    {
        char c = s.charAt(0);
        return "()[] ,\t\r\n".indexOf(c) != -1;
    }

    String peekChar()
    {
        return String.valueOf(str.charAt(offset));
    }

    String nextChar()
    {
        return String.valueOf(str.charAt(offset++));
    }

    boolean eof()
    {
        return str.length() == offset;
    }

    String str;
    int offset;
}
