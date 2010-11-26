// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScratchApplet.java

import java.io.PrintStream;
import java.util.Hashtable;

class LContext
{

    LContext()
    {
        oblist = new Hashtable();
        props = new Hashtable();
        juststop = new Object();
        priority = 0;
    }

    static final boolean isApplet = true;
    Hashtable oblist;
    Hashtable props;
    MapList iline;
    Symbol cfun;
    Symbol ufun;
    Object ufunresult;
    Object juststop;
    boolean mustOutput;
    boolean timeToStop;
    int priority;
    Object locals[];
    String errormessage;
    String codeBase;
    String projectURL;
    PlayerCanvas canvas;
    Sprite who;
    Thread logoThread;
    PrintStream tyo;
    boolean autostart;
}
