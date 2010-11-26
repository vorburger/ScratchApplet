// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FilePrims.java

import java.io.*;

class FilePrims extends Primitives
{

    FilePrims()
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
            return prim_filetostring(aobj[0], lcontext);

        case 1: // '\001'
            return prim_resourcetostring(aobj[0], lcontext);

        case 2: // '\002'
            return prim_load(aobj[0], lcontext);

        case 3: // '\003'
            return prim_stringtofile(aobj[0], aobj[1], lcontext);

        case 4: // '\004'
            return prim_filep(aobj[0], lcontext);

        case 5: // '\005'
            return prim_setread(aobj[0], lcontext);

        case 6: // '\006'
            return prim_readline(lcontext);

        case 7: // '\007'
            return prim_eot(lcontext);

        case 8: // '\b'
            return prim_lineback(lcontext);

        case 9: // '\t'
            return prim_filenamefrompath(aobj[0], lcontext);

        case 10: // '\n'
            return prim_dirnamefrompath(aobj[0], lcontext);

        case 11: // '\013'
            return prim_dir(aobj[0], lcontext);
        }
        return null;
    }

    Object prim_filetostring(Object obj, LContext lcontext)
    {
        String s = Logo.prs(obj);
        return fileToString(s, lcontext);
    }

    Object prim_resourcetostring(Object obj, LContext lcontext)
    {
        String s = Logo.prs(obj);
        return resourceToString(s, lcontext);
    }

    Object prim_load(Object obj, LContext lcontext)
    {
        String s = Logo.prs(obj);
        String s1 = System.getProperty("file.separator");
        LContext _tmp = lcontext;
        String s2 = resourceToString(s + ".logo", lcontext);
        Logo.readAllFunctions(s2, lcontext);
        return null;
    }

    String resourceToString(String s, LContext lcontext)
    {
        BufferedReader bufferedreader;
        StringWriter stringwriter;
        PrintWriter printwriter;
        java.io.InputStream inputstream = (FilePrims.class).getResourceAsStream(s);
        bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
        stringwriter = new StringWriter();
        printwriter = new PrintWriter(new BufferedWriter(stringwriter), true);
        String s2;
        String s1;
        while((s1 = bufferedreader.readLine()) != null) 
            printwriter.println(s1);
        s2 = stringwriter.toString();
        return s2;
        IOException ioexception;
        ioexception;
        Logo.error("Can't open file " + s, lcontext);
        return null;
    }

    String fileToString(String s, LContext lcontext)
    {
        byte abyte0[] = null;
        Object obj = null;
        try
        {
            File file = new File(s);
            int i = (int)file.length();
            FileInputStream fileinputstream = new FileInputStream(file);
            DataInputStream datainputstream = new DataInputStream(fileinputstream);
            abyte0 = new byte[i];
            datainputstream.readFully(abyte0);
            fileinputstream.close();
        }
        catch(IOException ioexception)
        {
            Logo.error("Can't open file " + s, lcontext);
        }
        return new String(abyte0);
    }

    Object prim_stringtofile(Object obj, Object obj1, LContext lcontext)
    {
        String s = Logo.prs(obj);
        String s1 = Logo.prs(obj1);
        try
        {
            FileWriter filewriter = new FileWriter(s);
            filewriter.write(s1, 0, s1.length());
            filewriter.close();
        }
        catch(IOException ioexception)
        {
            Logo.error("Can't write file " + s, lcontext);
        }
        return null;
    }

    Object prim_filep(Object obj, LContext lcontext)
    {
        String s = Logo.prs(obj);
        return new Boolean((new File(s)).exists());
    }

    Object prim_setread(Object obj, LContext lcontext)
    {
        readtext = Logo.prs(obj);
        textoffset = 0;
        return null;
    }

    Object prim_readline(LContext lcontext)
    {
        String s = "";
        int i = readtext.indexOf("\n", textoffset);
        if(i == -1)
        {
            if(textoffset < readtext.length())
            {
                s = readtext.substring(textoffset, readtext.length());
                textoffset = readtext.length();
            }
        } else
        {
            s = readtext.substring(textoffset, i);
            textoffset = i + 1;
        }
        if(s.length() == 0)
            return s;
        if(s.charAt(s.length() - 1) == '\r')
            s = s.substring(0, s.length() - 1);
        return s;
    }

    Object prim_eot(LContext lcontext)
    {
        return new Boolean(textoffset >= readtext.length());
    }

    Object prim_lineback(LContext lcontext)
    {
        int i = readtext.lastIndexOf("\n", textoffset - 2);
        if(i < 0)
            textoffset = 0;
        else
            textoffset = i + 1;
        return null;
    }

    Object prim_filenamefrompath(Object obj, LContext lcontext)
    {
        return (new File(Logo.prs(obj))).getName();
    }

    Object prim_dirnamefrompath(Object obj, LContext lcontext)
    {
        File file = new File(Logo.prs(obj));
        if(file.isDirectory())
            return file.getPath();
        else
            return file.getParent();
    }

    Object prim_dir(Object obj, LContext lcontext)
    {
        String as[] = (new File(Logo.prs(obj))).list();
        if(as == null)
            return ((Object) (new Object[0]));
        else
            return as;
    }

    String readtext;
    int textoffset;
    static String primlist[] = {
        "filetostring", "1", "resourcetostring", "1", "load", "1", "stringtofile", "2", "file?", "1", 
        "setread", "1", "readline", "0", "eot?", "0", "lineback", "0", "filenamefrompath", "1", 
        "dirnamefrompath", "1", "dir", "1"
    };

}
