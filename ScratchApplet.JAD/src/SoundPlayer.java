// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerPrims.java

import java.util.Iterator;
import java.util.Vector;

class SoundPlayer
    implements Runnable
{

    SoundPlayer()
    {
    }

    static synchronized Object startSound(Object obj, Sprite sprite, LContext lcontext)
    {
        if(!(obj instanceof ScratchSound))
        {
            Logo.error("argument of startSound must be a ScratchSound", lcontext);
            return ((Object) (new Object[0]));
        }
        Object aobj[] = activeSounds.toArray();
        for(int i = 0; i < aobj.length; i++)
        {
            PlayingSound playingsound1 = (PlayingSound)aobj[i];
            if(playingsound1.snd == (ScratchSound)obj && playingsound1.sprite == sprite)
            {
                playingsound1.closeLine();
                activeSounds.remove(playingsound1);
            }
        }

        PlayingSound playingsound = new PlayingSound((ScratchSound)obj, sprite);
        playingsound.startPlaying(lcontext);
        activeSounds.add(playingsound);
        return playingsound;
    }

    static synchronized boolean isSoundPlaying(Object obj)
    {
        if(!(obj instanceof PlayingSound))
            return false;
        else
            return ((PlayingSound)obj).isPlaying();
    }

    static synchronized void stopSound(Object obj)
    {
        if(!(obj instanceof PlayingSound))
        {
            return;
        } else
        {
            ((PlayingSound)obj).closeLine();
            activeSounds.remove(obj);
            return;
        }
    }

    static synchronized void stopSoundsForApplet(LContext lcontext)
    {
        PlayerPrims.stopMIDINotes();
        Vector vector = new Vector();
        for(Iterator iterator = activeSounds.iterator(); iterator.hasNext();)
        {
            PlayingSound playingsound = (PlayingSound)iterator.next();
            if(playingsound.owner == lcontext)
                playingsound.closeLine();
            else
                vector.addElement(playingsound);
        }

        activeSounds = vector;
    }

    static synchronized void updateActiveSounds()
    {
        Vector vector = new Vector();
        for(Iterator iterator = activeSounds.iterator(); iterator.hasNext();)
        {
            PlayingSound playingsound = (PlayingSound)iterator.next();
            if(playingsound.isPlaying())
            {
                playingsound.writeSomeSamples();
                vector.addElement(playingsound);
            } else
            {
                playingsound.closeLine();
            }
        }

        activeSounds = vector;
    }

    static synchronized void startPlayer()
    {
        sndThread = new Thread(new SoundPlayer(), "SoundPlayer");
        sndThread.setPriority(10);
        sndThread.start();
    }

    public void run()
    {
        for(Thread thread = Thread.currentThread(); sndThread == thread;)
        {
            updateActiveSounds();
            try
            {
                Thread.sleep(10L);
            }
            catch(InterruptedException interruptedexception) { }
        }

    }

    static Vector activeSounds = new Vector();
    static Thread sndThread;

}
