// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Logo.java

import java.io.PrintStream;

class LogoCommandRunner
    implements Runnable
{

    static void startLogoThread(String s, LContext lcontext)
    {
        stopLogoThread(lcontext);
        lcontext.logoThread = new Thread(new LogoCommandRunner(s, lcontext, true), "Logo");
        lcontext.logoThread.start();
    }

    static void stopLogoThread(LContext lcontext)
    {
        if(lcontext.logoThread == null)
            return;
        lcontext.timeToStop = true;
        try
        {
            lcontext.logoThread.join();
        }
        catch(InterruptedException interruptedexception) { }
        lcontext.logoThread = null;
    }

    LogoCommandRunner(String s, LContext lcontext, boolean flag)
    {
        listtorun = Logo.parse(s, lcontext);
        context = lcontext;
        silent = flag;
    }

    public void run()
    {
label0:
        {
            synchronized(context)
            {
                if(context.logoThread == null)
                    context.logoThread = Thread.currentThread();
                if(context.logoThread == Thread.currentThread())
                    break label0;
            }
            return;
        }
        String s = Logo.runToplevel(listtorun, context);
        if(context.tyo != null && !context.timeToStop)
        {
            if(s != null)
            {
                context.tyo.println(s);
                context.errormessage = s;
            }
            if(!silent)
                context.tyo.println("ok");
        }
        context.logoThread = null;
        lcontext;
        JVM INSTR monitorexit ;
          goto _L1
        exception;
        throw exception;
_L1:
    }

    Object listtorun[];
    LContext context;
    boolean silent;
}
