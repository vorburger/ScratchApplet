// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScratchApplet.java

import java.net.URL;
import javax.swing.JApplet;

public class ScratchApplet extends JApplet
{

    public ScratchApplet()
    {
    }

    public static void setSensorValue(int i, int j)
    {
        if(i < 0 || i > 15)
        {
            return;
        } else
        {
            PlayerPrims.sensorValues[i] = j;
            return;
        }
    }

    public static int getSensorValue(int i)
    {
        if(i < 0 || i > 15)
            return 0;
        else
            return PlayerPrims.sensorValues[i];
    }

    public void init()
    {
        String s = getCodeBase().toString();
        String s1 = getParameter("project");
        String s2;
        s2 = s1 == null ? null : (s2 = s + s1);
        String s3 = getParameter("autostart");
        boolean flag = true;
        if(s3 != null)
        {
            if(s3.equalsIgnoreCase("false"))
                flag = false;
            if(s3.equalsIgnoreCase("no"))
                flag = false;
        }
        try
        {
            Thread.sleep(50L);
        }
        catch(InterruptedException interruptedexception) { }
        lc = PlayerPrims.startup(s, s2, getContentPane(), flag);
        lc.tyo = System.out;
    }

    public void destroy()
    {
        PlayerPrims.shutdown(lc);
    }

    LContext lc;
}
